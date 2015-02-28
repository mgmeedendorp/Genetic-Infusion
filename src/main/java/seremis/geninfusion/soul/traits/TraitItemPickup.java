package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.WorldServer;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;

import java.util.List;
import java.util.Random;

public class TraitItemPickup extends Trait {

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        entity.getWorld().theProfiler.startSection("looting");

        boolean canPickUpItems = SoulHelper.geneRegistry.getValueBoolean(entity, Genes.GENE_PICKS_UP_ITEMS);
        boolean isDead = entity.getBoolean("isDead");

        if(GeneticInfusion.serverProxy().isServerWorld(entity.getWorld()) && canPickUpItems && !isDead && entity.getWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            List list = entity.getWorld().getEntitiesWithinAABB(EntityItem.class, entity.getBoundingBox().expand(1.0D, 0.0D, 1.0D));

            for(Object aList : list) {
                EntityItem entityitem = (EntityItem) aList;

                if(!entityitem.isDead && entityitem.getEntityItem() != null) {
                    ItemStack itemstack = entityitem.getEntityItem();
                    int i = getArmorImportance(itemstack);

                    if(i > -1) {
                        boolean flag = true;
                        ItemStack itemstack1 = UtilSoulEntity.getEquipmentInSlot(entity, i);

                        if(itemstack1 != null) {
                            if(i == 0) {
                                if(itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword)) {
                                    flag = true;
                                } else if(itemstack.getItem() instanceof ItemSword) {
                                    ItemSword itemsword = (ItemSword) itemstack.getItem();
                                    ItemSword itemsword1 = (ItemSword) itemstack1.getItem();

                                    if(itemsword.func_150931_i() == itemsword1.func_150931_i()) {
                                        flag = itemstack.getItemDamage() > itemstack1.getItemDamage() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                                    } else {
                                        flag = itemsword.func_150931_i() > itemsword1.func_150931_i();
                                    }
                                } else {
                                    flag = false;
                                }
                            } else if(itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor)) {
                                flag = true;
                            } else if(itemstack.getItem() instanceof ItemArmor) {
                                ItemArmor itemarmor = (ItemArmor) itemstack.getItem();
                                ItemArmor itemarmor1 = (ItemArmor) itemstack1.getItem();

                                if(itemarmor.damageReduceAmount == itemarmor1.damageReduceAmount) {
                                    flag = itemstack.getItemDamage() > itemstack1.getItemDamage() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                                } else {
                                    flag = itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount;
                                }
                            } else {
                                flag = false;
                            }
                        }

                        if(flag) {
                            float[] equipmentDropChances = entity.getFloatArray("equipmentDropChances");
                            float dropChance = equipmentDropChances[i];

                            if(itemstack1 != null && new Random().nextFloat() - 0.1F < dropChance) {
                                UtilSoulEntity.dropItem(entity, itemstack1, 0.0F);
                            }

                            if(itemstack.getItem() == Items.diamond && entityitem.func_145800_j() != null) {
                                EntityPlayer entityplayer = entity.getWorld().getPlayerEntityByName(entityitem.func_145800_j());

                                if(entityplayer != null) {
                                    entityplayer.triggerAchievement(AchievementList.field_150966_x);
                                }
                            }

                            UtilSoulEntity.setEquipmentInSlot(entity, i, itemstack);
                            equipmentDropChances[i] = 2.0F;
                            entity.setFloatArray("equipmentDropChances", equipmentDropChances);
                            entity.setBoolean("persistenceRequired", true);
                            onItemPickup(entity, entityitem, 1);
                            entityitem.setDead();
                        }
                    }
                }
            }
        }

        if(GeneticInfusion.serverProxy().isServerWorld(entity.getWorld())) {
            EntityLiving living = (EntityLiving) entity;

            int arrowCount = living.getArrowCountInEntity();

            if(arrowCount > 0) {
                if(living.arrowHitTimer <= 0) {
                    living.arrowHitTimer = 20 * (30 - arrowCount);
                }

                --living.arrowHitTimer;

                if(living.arrowHitTimer <= 0) {
                    living.setArrowCountInEntity(arrowCount - 1);
                }
            }

            for(int j = 0; j < 5; ++j) {
                ItemStack[] previousEquipment = entity.getItemStackArray("previousEquipment");
                ItemStack prevStack = previousEquipment[j];
                ItemStack currStack = living.getEquipmentInSlot(j);

                if(!ItemStack.areItemStacksEqual(currStack, prevStack)) {
                    ((WorldServer) entity.getWorld()).getEntityTracker().func_151247_a(living, new S04PacketEntityEquipment(entity.getEntityId(), j, currStack));

                    if(prevStack != null) {
                        living.getAttributeMap().removeAttributeModifiers(prevStack.getAttributeModifiers());
                    }

                    if(currStack != null) {
                        living.getAttributeMap().applyAttributeModifiers(currStack.getAttributeModifiers());
                    }

                    previousEquipment[j] = currStack == null ? null : currStack.copy();

                    entity.setItemStackArray("previousEquipment", previousEquipment);
                }
            }

            if(living.ticksExisted % 20 == 0) {
                living.func_110142_aN().func_94549_h();
            }
        }

        entity.getWorld().theProfiler.endSection();
    }

    public static int getArmorImportance(ItemStack stack) {
        if(stack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && stack.getItem() != Items.skull) {
            if(stack.getItem() instanceof ItemArmor) {

                switch(((ItemArmor) stack.getItem()).armorType) {
                    case 0:
                        return 4;
                    case 1:
                        return 3;
                    case 2:
                        return 2;
                    case 3:
                        return 1;
                }
            }
            return 0;

        } else {
            return 4;
        }
    }

    public void onItemPickup(IEntitySoulCustom entity, Entity ent, int stackSize) {
        if(!ent.isDead && GeneticInfusion.serverProxy().isServerWorld(entity.getWorld())) {
            EntityTracker entitytracker = ((WorldServer) entity.getWorld()).getEntityTracker();

            entitytracker.func_151247_a(ent, new S0DPacketCollectItem(ent.getEntityId(), entity.getEntityId()));
        }
    }
}
