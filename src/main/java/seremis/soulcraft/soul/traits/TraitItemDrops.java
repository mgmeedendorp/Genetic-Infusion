package seremis.soulcraft.soul.traits;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeHooks;
import seremis.soulcraft.api.soul.GeneRegistry;
import seremis.soulcraft.api.soul.IEntitySoulCustom;
import seremis.soulcraft.api.soul.lib.Genes;
import seremis.soulcraft.api.soul.util.UtilSoulEntity;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.soul.Trait;
import seremis.soulcraft.soul.allele.AlleleFloatArray;
import seremis.soulcraft.soul.allele.AlleleInventory;

import java.util.ArrayList;

public class TraitItemDrops extends Trait {

	@Override
	public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        Entity ent = source.getEntity();
        EntityPlayer attackingPlayer = entity.getInteger("attackingPlayerID") != 0 ? (EntityPlayer) entity.getWorld().getEntityByID(entity.getInteger("attackingPlayerID")) : null;
        EntityLivingBase entityLivingToAttack = entity.getInteger("entityLivingToAttack") != 0 ? (EntityLivingBase) entity.getWorld().getEntityByID(entity.getInteger("entityLivingToAttackID")) : null;
        EntityLivingBase entitylivingbase = entity.getCombatTracker().func_94550_c() != null ? entity.getCombatTracker().func_94550_c() : (attackingPlayer != null ? attackingPlayer : (entityLivingToAttack != null ? entityLivingToAttack : null));

        int scoreValue = entity.getInteger("scoreValue");

        if (scoreValue >= 0 && entitylivingbase != null) {
            entitylivingbase.addToPlayerScore((Entity) entity, scoreValue);
        }

        if (ent != null) {
            ent.onKillEntity((EntityLivingBase)entity);
        }

        if (CommonProxy.instance.isServerWorld(entity.getWorld())) {
            int i = 0;

            if (entity instanceof EntityPlayer) {
                i = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
            }
            
            entity.setVariable("captureDrops", true);

            int capturedDropsSize = entity.getPersistentInteger("capturedDrops.size");
            for(int k = 0; k < capturedDropsSize; k++) {
            	entity.setPersistentVariable("capturedDrops."+k, (ItemStack)null);
            }
           
            int j = 0;

        	int recentlyHit = entity.getInteger("recentlyHit");
            
            if (!((EntityLiving)entity).isChild() && entity.getWorld().getGameRules().getGameRuleBooleanValue("doMobLoot")) {

                this.dropFewItems(entity, recentlyHit > 0, i);
                this.dropEquipment(entity, recentlyHit > 0, i);

                if (recentlyHit > 0) {
                    j = entity.getRandom().nextInt(200) - i;

                    if (j < 5) {
                        this.dropRareDrop(entity, j <= 0);
                    }
                }
            }
            
            entity.setVariable("captureDrops", false);

            ArrayList<EntityItem> capturedDrops = new ArrayList<EntityItem>();
            double posX = entity.getPersistentDouble("posX");
            double posY = entity.getPersistentDouble("posY");
            double posZ = entity.getPersistentDouble("posZ");

            capturedDropsSize = entity.getPersistentInteger("capturedDrops.size");
            
            for(int k = 0; k < capturedDropsSize; k++) {
            	if(entity.getPersistentItemStack("capturedDrops."+k) != null)
            		capturedDrops.add(new EntityItem(entity.getWorld(), posX, posY, posZ, entity.getPersistentItemStack("capturedDrops."+k)));
            }
            
            if (!ForgeHooks.onLivingDrops((EntityLiving)entity, source, capturedDrops, i, recentlyHit > 0, j)) {
                for (EntityItem item : capturedDrops) {
                	entity.getWorld().spawnEntityInWorld(item);
                }
            }
        }
	}

	private void dropFewItems(IEntitySoulCustom entity, boolean recentlyHit, int lootingLevel) {
		ItemStack[] drops = ((AlleleInventory)GeneRegistry.getActiveFor(entity, Genes.GENE_ITEM_DROPS)).inventory.getItemStacks();
		
        if (drops.length != 0) {
            int j = entity.getRandom().nextInt(3);

            if (lootingLevel > 0) {
                j += entity.getRandom().nextInt(lootingLevel + 1);
            }

            for (int k = 0; k < j; ++k) {
            	for(ItemStack stack : drops) {
            		UtilSoulEntity.dropItem(entity, stack, 0.0F);
            	}
            }
        }
	}
	
	private void dropEquipment(IEntitySoulCustom entity, boolean recentlyHit, int lootingLevel) {		
		for (int j = 0; j < 5; ++j) {
            ItemStack itemstack = UtilSoulEntity.getEquipmentInSlot(entity, j);
            
            float dropChance = entity.getPersistentFloat("equipmentDropChances." + j);
            
            boolean flag1 = dropChance > 1.0F;

            if (itemstack != null && itemstack.getItem() != null && (recentlyHit || flag1) && entity.getRandom().nextFloat() - (float)lootingLevel * 0.01F < dropChance) {
                if (!flag1 && itemstack.isItemStackDamageable()) {
                    int k = Math.max(itemstack.getMaxDamage() - 25, 1);
                    int l = itemstack.getMaxDamage() - entity.getRandom().nextInt(entity.getRandom().nextInt(k) + 1);

                    if (l > k) {
                        l = k;
                    }

                    if (l < 1) {
                        l = 1;
                    }

                    itemstack.setItemDamage(l);
                }
                UtilSoulEntity.dropItem(entity, itemstack, 0.0F);
            }
        }
	}
	
	private void dropRareDrop(IEntitySoulCustom entity, boolean reallyRandomThingy) {
		ItemStack[] drops = ((AlleleInventory)GeneRegistry.getActiveFor(entity, Genes.GENE_RARE_ITEM_DROPS)).inventory.getItemStacks();
		float[] dropChances = ((AlleleFloatArray)GeneRegistry.getActiveFor(entity, Genes.GENE_RARE_ITEM_DROP_CHANCES)).value;
		
		for(int i = 0; i < drops.length; i++) {
			if(entity.getRandom().nextInt((int)(dropChances[i]*100F)) == 0) {
				UtilSoulEntity.dropItem(entity, drops[i], 0.0F);
			}
		}
	}
}