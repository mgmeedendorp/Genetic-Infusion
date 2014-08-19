package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.WorldServer;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.UtilSoulEntity;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.soul.allele.AlleleBoolean;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TraitItemPickup extends Trait {
	
	@Override
	public void onUpdate(IEntitySoulCustom entity) {
		entity.getWorld().theProfiler.startSection("looting");
		
		boolean canPickUpItems = ((AlleleBoolean) SoulHelper.geneRegistry.getActiveFor(entity, Genes.GENE_PICKS_UP_ITEMS)).value;
		boolean isDead = entity.getPersistentBoolean("isDead");

        if (CommonProxy.instance.isServerWorld(entity.getWorld()) && canPickUpItems && !isDead && entity.getWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            List list = entity.getWorld().getEntitiesWithinAABB(EntityItem.class, entity.getBoundingBox().expand(1.0D, 0.0D, 1.0D));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityItem entityitem = (EntityItem)iterator.next();

                if (!entityitem.isDead && entityitem.getEntityItem() != null) {
                    ItemStack itemstack = entityitem.getEntityItem();
                    int i = getArmorPosition(itemstack);
                    
                    if (i > -1) {
                        boolean flag = true;
                        ItemStack itemstack1 = UtilSoulEntity.getEquipmentInSlot(entity, i);
                        
                        if (itemstack1 != null) {
                            if (i == 0) {
                                if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword)) {
                                    flag = true;
                                } else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword) {
                                    ItemSword itemsword = (ItemSword)itemstack.getItem();
                                    ItemSword itemsword1 = (ItemSword)itemstack1.getItem();

                                    if (itemsword.func_150931_i() == itemsword1.func_150931_i()) {
                                        flag = itemstack.getItemDamage() > itemstack1.getItemDamage() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                                    } else {
                                        flag = itemsword.func_150931_i() > itemsword1.func_150931_i();
                                    }
                                } else {
                                    flag = false;
                                }
                            } else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor)) {
                                flag = true;
                            } else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor) {
                                ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
                                ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();

                                if (itemarmor.damageReduceAmount == itemarmor1.damageReduceAmount) {
                                    flag = itemstack.getItemDamage() > itemstack1.getItemDamage() || itemstack.hasTagCompound() && !itemstack1.hasTagCompound();
                                } else {
                                    flag = itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount;
                                }
                            } else {
                                flag = false;
                            }
                        }

                        if (flag) {
                        	float equipmentDropChance = entity.getPersistentFloat("equipmentDropChances."+i);
                        	
                            if (itemstack1 != null && new Random().nextFloat() - 0.1F < equipmentDropChance) {
                                UtilSoulEntity.dropItem(entity, itemstack1, 0.0F);
                            }

                            if (itemstack.getItem() == Items.diamond && entityitem.func_145800_j() != null) {
                                EntityPlayer entityplayer = entity.getWorld().getPlayerEntityByName(entityitem.func_145800_j());

                                if (entityplayer != null) {
                                    entityplayer.triggerAchievement(AchievementList.field_150966_x);
                                }
                            }

                            UtilSoulEntity.setEquipmentInSlot(entity, i, itemstack);
                            entity.setPersistentVariable("equipmentDropChances."+i, 2.0F);
                            entity.setPersistentVariable("persistenceRequired", true);
                            onItemPickup(entity, entityitem, 1);
                            entityitem.setDead();
                        }
                    }
                }
            }
        }

        entity.getWorld().theProfiler.endSection();
	}
	
	public static int getArmorPosition(ItemStack par0ItemStack) {
        if (par0ItemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && par0ItemStack.getItem() != Items.skull) {
            if (par0ItemStack.getItem() instanceof ItemArmor) {
                switch (((ItemArmor)par0ItemStack.getItem()).armorType) {
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
        if (!ent.isDead && CommonProxy.instance.isServerWorld(entity.getWorld())) {
            EntityTracker entitytracker = ((WorldServer)entity.getWorld()).getEntityTracker();

            if (ent instanceof EntityItem) {
                entitytracker.func_151247_a(ent, new S0DPacketCollectItem(ent.getEntityId(), entity.getEntityId()));
            }

            if (ent instanceof EntityArrow) {
                entitytracker.func_151247_a(ent, new S0DPacketCollectItem(ent.getEntityId(), entity.getEntityId()));
            }

            if (ent instanceof EntityXPOrb) {
                entitytracker.func_151247_a(ent, new S0DPacketCollectItem(ent.getEntityId(), entity.getEntityId()));
            }
        }
    }
}
