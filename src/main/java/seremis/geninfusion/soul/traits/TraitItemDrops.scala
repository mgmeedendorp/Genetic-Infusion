package seremis.geninfusion.soul.traits

import java.util

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{EntityLiving, EntityLivingBase}
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraftforge.common.ForgeHooks
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitItemDrops extends Trait {

    override def onDeath(entity: IEntitySoulCustom, source: DamageSource) {
        val living = entity.asInstanceOf[EntityLiving]

        val killer = source.getEntity
        val attackTarget = living.func_94060_bK()

        val scoreValue = entity.getInteger(ENTITY_SCORE_VALUE)

        if(scoreValue >= 0 && attackTarget != null) {
            attackTarget.addToPlayerScore(living, scoreValue)
        }

        if(killer != null) {
            killer.onKillEntity(living)
        }

        if(GeneticInfusion.commonProxy.isServerWorld(entity.getWorld)) {
            var lootingLevel = 0

            if(entity.isInstanceOf[EntityPlayer]) {
                lootingLevel = EnchantmentHelper.getLootingModifier(killer.asInstanceOf[EntityLivingBase])
            }

            entity.setBoolean(ENTITY_CAPTURE_DROPS, true)

            val capturedDrops = entity.getObject(ENTITY_CAPTURED_DROPS).asInstanceOf[util.ArrayList[EntityItem]]
            capturedDrops.clear()
            entity.setObject(ENTITY_CAPTURED_DROPS, capturedDrops)

            var rareDropValue = 0
            val recentlyHit = entity.getInteger(ENTITY_RECENTLY_HIT)
            if(!entity.asInstanceOf[EntityLiving].isChild &&
                entity.getWorld.getGameRules.getGameRuleBooleanValue("doMobLoot")) {
                this.dropFewItems(entity, recentlyHit > 0, lootingLevel)
                this.dropEquipment(entity, recentlyHit > 0, lootingLevel)
                if(recentlyHit > 0) {
                    rareDropValue = entity.getRandom.nextInt(200) - lootingLevel
                    if(rareDropValue < 5) {
                        this.dropRareDrop(entity, rareDropValue <= 0)
                    }
                }
            }

            entity.setBoolean(ENTITY_CAPTURE_DROPS, false)

            if(!ForgeHooks.onLivingDrops(entity.asInstanceOf[EntityLiving], source, capturedDrops, lootingLevel, recentlyHit > 0, rareDropValue)) {
                for(item <- capturedDrops.toArray(new Array[EntityItem](capturedDrops.size()))) {
                    entity.getWorld.spawnEntityInWorld(item)
                }
            }
        }

        val dropsWhenKilledBySpecificEntity = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_DROPS_ITEM_WHEN_KILLED_BY_SPECIFIC_ENTITY)

        if(dropsWhenKilledBySpecificEntity) {
            val specificEntityClass = SoulHelper.geneRegistry.getValueFromAllele[Class[_]](entity, Genes.GENE_KILLED_BY_SPECIFIC_ENTITY_ENTITY)
            val drops = SoulHelper.geneRegistry.getValueFromAllele[Array[ItemStack]](entity, Genes.GENE_KILLED_BY_SPECIFIC_ENTITY_DROPS)

            if(specificEntityClass != null && drops != null && source.getEntity != null && source.getEntity.getClass == specificEntityClass) {
                living.entityDropItem(drops(entity.getRandom.nextInt(drops.length)), 0.0F)
            }
        }
    }

    private def dropFewItems(entity: IEntitySoulCustom, recentlyHit: Boolean, lootingLevel: Int) {
        val drops = SoulHelper.geneRegistry.getValueFromAllele[Array[ItemStack]](entity, Genes.GENE_ITEM_DROPS)

        if(drops.length != 0) {
            var amount = entity.getRandom.nextInt(3)

            if(lootingLevel > 0) {
                amount += entity.getRandom.nextInt(lootingLevel + 1)
            }

            for(k <- 0 until amount; stack <- drops) {
                entity.asInstanceOf[EntityLiving].entityDropItem(stack, 0.0F)
            }
        }
    }

    private def dropEquipment(entity: IEntitySoulCustom, recentlyHit: Boolean, lootingLevel: Int) {
        val equipmentDropChances = entity.getFloatArray(ENTITY_EQUIPMENT_DROP_CHANCES)

        for(j <- 0 until 5) {
            val itemstack = if(entity.getItemStackArray(ENTITY_EQUIPMENT) != null) entity.getItemStackArray(ENTITY_EQUIPMENT)(j) else null

            val dropChance = equipmentDropChances(j)
            val flag1 = dropChance > 1.0F

            if(itemstack != null && itemstack.getItem != null && (recentlyHit || flag1) && entity.getRandom.nextFloat() - lootingLevel.toFloat * 0.01F < dropChance) {
                if(!flag1 && itemstack.isItemStackDamageable) {
                    val maxStackDamage = Math.max(itemstack.getMaxDurability - 25, 1)
                    var stackDamage = itemstack.getMaxDurability - entity.getRandom.nextInt(entity.getRandom.nextInt(maxStackDamage) + 1)

                    if(stackDamage > maxStackDamage) {
                        stackDamage = maxStackDamage
                    }

                    if(stackDamage < 1) {
                        stackDamage = 1
                    }
                    itemstack.setMetadata(stackDamage)
                }
                entity.asInstanceOf[EntityLiving].entityDropItem(itemstack, 0.0F)
            }
        }
    }

    private def dropRareDrop(entity: IEntitySoulCustom, reallyRandomThingy: Boolean) {
        val drops = SoulHelper.geneRegistry.getValueFromAllele[Array[ItemStack]](entity, Genes.GENE_RARE_ITEM_DROPS)
        val dropChances = entity.getFloatArray(ENTITY_EQUIPMENT_DROP_CHANCES)

        if(drops != null && drops.length != 0) {
            for(i <- 0 until drops.length if entity.getRandom.nextInt((dropChances(i) * 100F).toInt) == 0) {
                entity.asInstanceOf[EntityLiving].entityDropItem(drops(i), 0.0F)
            }
        }
    }
}