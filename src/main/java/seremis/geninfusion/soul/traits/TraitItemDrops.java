package seremis.geninfusion.soul.traits;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeHooks;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;

import java.util.ArrayList;

public class TraitItemDrops extends Trait {

    @Override
    public void onDeath(IEntitySoulCustom entity, DamageSource source) {
        CombatTracker combatTracker = (CombatTracker) entity.getObject("_combatTracker");

        Entity ent = source.getEntity();
        EntityPlayer attackingPlayer = (EntityPlayer) entity.getObject("attackingPlayer");
        EntityLivingBase entityLivingToAttack = (EntityLivingBase) entity.getObject("entityLivingToAttack");
        EntityLivingBase entitylivingbase = combatTracker.func_94550_c() != null ? combatTracker.func_94550_c() : (attackingPlayer != null ? attackingPlayer : (entityLivingToAttack != null ? entityLivingToAttack : null));

        int scoreValue = entity.getInteger("scoreValue");

        if(scoreValue >= 0 && entitylivingbase != null) {
            entitylivingbase.addToPlayerScore((Entity) entity, scoreValue);
        }

        if(ent != null) {
            ent.onKillEntity((EntityLivingBase) entity);
        }

        if(GeneticInfusion.serverProxy().isServerWorld(entity.getWorld())) {
            int i = 0;

            if(entity instanceof EntityPlayer) {
                i = EnchantmentHelper.getLootingModifier((EntityLivingBase) entity);
            }

            entity.setBoolean("captureDrops", true);

            ArrayList<EntityItem> capturedDrops = (ArrayList<EntityItem>) entity.getObject("capturedDrops");

            capturedDrops.clear();
            entity.setObject("capturedDrops", capturedDrops);

            int j = 0;

            int recentlyHit = entity.getInteger("recentlyHit");

            if(!((EntityLiving) entity).isChild() && entity.getWorld().getGameRules().getGameRuleBooleanValue("doMobLoot")) {

                this.dropFewItems(entity, recentlyHit > 0, i);
                this.dropEquipment(entity, recentlyHit > 0, i);

                if(recentlyHit > 0) {
                    j = entity.getRandom().nextInt(200) - i;

                    if(j < 5) {
                        this.dropRareDrop(entity, j <= 0);
                    }
                }
            }

            entity.setBoolean("captureDrops", false);

            if(!ForgeHooks.onLivingDrops((EntityLiving) entity, source, capturedDrops, i, recentlyHit > 0, j)) {
                for(EntityItem item : capturedDrops) {
                    entity.getWorld().spawnEntityInWorld(item);
                }
            }
        }
    }

    private void dropFewItems(IEntitySoulCustom entity, boolean recentlyHit, int lootingLevel) {
        ItemStack[] drops = SoulHelper.geneRegistry.getValueItemStackArray(entity, Genes.GENE_ITEM_DROPS);

        if(drops.length != 0) {
            int j = entity.getRandom().nextInt(3);

            if(lootingLevel > 0) {
                j += entity.getRandom().nextInt(lootingLevel + 1);
            }

            for(int k = 0; k < j; ++k) {
                for(ItemStack stack : drops) {
                    ((EntityLiving) entity).entityDropItem(stack, 0.0F);
                }
            }
        }
    }

    private void dropEquipment(IEntitySoulCustom entity, boolean recentlyHit, int lootingLevel) {
        float[] equipmentDropChances = SoulHelper.geneRegistry.getValueFloatArray(entity, Genes.GENE_EQUIPMENT_DROP_CHANCES);

        for(int j = 0; j < 5; ++j) {
            ItemStack itemstack = entity.getItemStackArray("equipment") != null ? entity.getItemStackArray("equipment")[j] : null;

            float dropChance = equipmentDropChances[j];

            boolean flag1 = dropChance > 1.0F;

            if(itemstack != null && itemstack.getItem() != null && (recentlyHit || flag1) && entity.getRandom().nextFloat() - (float) lootingLevel * 0.01F < dropChance) {
                if(!flag1 && itemstack.isItemStackDamageable()) {
                    int k = Math.max(itemstack.getMaxDamage() - 25, 1);
                    int l = itemstack.getMaxDamage() - entity.getRandom().nextInt(entity.getRandom().nextInt(k) + 1);

                    if(l > k) {
                        l = k;
                    }

                    if(l < 1) {
                        l = 1;
                    }

                    itemstack.setItemDamage(l);
                }
                ((EntityLiving) entity).entityDropItem(itemstack, 0.0F);
            }
        }
    }

    private void dropRareDrop(IEntitySoulCustom entity, boolean reallyRandomThingy) {
        ItemStack[] drops = SoulHelper.geneRegistry.getValueItemStackArray(entity, Genes.GENE_RARE_ITEM_DROPS);
        float[] dropChances = SoulHelper.geneRegistry.getValueFloatArray(entity, Genes.GENE_RARE_ITEM_DROP_CHANCES);

        for(int i = 0; i < drops.length; i++) {
            if(entity.getRandom().nextInt((int) (dropChances[i] * 100F)) == 0) {
                ((EntityLiving) entity).entityDropItem(drops[i], 0.0F);
            }
        }
    }
}