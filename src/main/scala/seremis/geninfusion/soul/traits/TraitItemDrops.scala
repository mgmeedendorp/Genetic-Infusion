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
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitItemDrops extends Trait {

    override def onDeath(entity: IEntitySoulCustom, source: DamageSource) {
        val living = entity.asInstanceOf[EntityLiving]

        val killer = source.getEntity
        val attackTarget = living.func_94060_bK()

        val scoreValue = entity.getInteger(EntityScoreValue)

        if(scoreValue >= 0 && attackTarget != null) {
            attackTarget.addToPlayerScore(living, scoreValue)
        }

        if(killer != null) {
            killer.onKillEntity(living)
        }

        if(GeneticInfusion.commonProxy.isServerWorld(entity.getWorld_I)) {
            var lootingLevel = 0

            if(entity.isInstanceOf[EntityPlayer]) {
                lootingLevel = EnchantmentHelper.getLootingModifier(killer.asInstanceOf[EntityLivingBase])
            }

            entity.setBoolean(EntityCaptureDrops, true)

            val capturedDrops = entity.getObject(EntityCapturedDrops).asInstanceOf[util.ArrayList[EntityItem]]
            capturedDrops.clear()
            entity.setObject(EntityCapturedDrops, capturedDrops)

            var rareDropValue = 0
            val recentlyHit = entity.getInteger(EntityRecentlyHit)
            if(!entity.asInstanceOf[EntityLiving].isChild &&
                entity.getWorld_I.getGameRules.getGameRuleBooleanValue("doMobLoot")) {
                this.dropFewItems(entity, recentlyHit > 0, lootingLevel)
                this.dropEquipment(entity, recentlyHit > 0, lootingLevel)
                if(recentlyHit > 0) {
                    rareDropValue = entity.getRandom_I.nextInt(200) - lootingLevel
                    if(rareDropValue < 5) {
                        this.dropRareDrop(entity, rareDropValue <= 0)
                    }
                }
            }

            entity.setBoolean(EntityCaptureDrops, false)

            if(!ForgeHooks.onLivingDrops(entity.asInstanceOf[EntityLiving], source, capturedDrops, lootingLevel, recentlyHit > 0, rareDropValue)) {
                for(item <- capturedDrops.toArray(new Array[EntityItem](capturedDrops.size()))) {
                    entity.getWorld_I.spawnEntityInWorld(item)
                }
            }
        }

        val dropsWhenKilledBySpecificEntity = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneDropsItemWhenKilledBySpecificEntity)

        if(dropsWhenKilledBySpecificEntity) {
            val specificEntityClass = SoulHelper.geneRegistry.getValueFromAllele[Class[_]](entity, Genes.GeneKilledBySpecificEntityEntity)
            val drops = SoulHelper.geneRegistry.getValueFromAllele[Array[ItemStack]](entity, Genes.GeneKilledBySpecificEntityDrops)

            if(specificEntityClass != null && drops != null && source.getEntity != null && source.getEntity.getClass == specificEntityClass) {
                living.entityDropItem(drops(entity.getRandom_I.nextInt(drops.length)), 0.0F)
            }
        }
    }

    private def dropFewItems(entity: IEntitySoulCustom, recentlyHit: Boolean, lootingLevel: Int) {
        val drops = SoulHelper.geneRegistry.getValueFromAllele[Array[ItemStack]](entity, Genes.GeneItemDrops)

        if(drops.length != 0) {
            var amount = entity.getRandom_I.nextInt(3)

            if(lootingLevel > 0) {
                amount += entity.getRandom_I.nextInt(lootingLevel + 1)
            }

            for(k <- 0 until amount; stack <- drops) {
                entity.asInstanceOf[EntityLiving].entityDropItem(stack, 0.0F)
            }
        }
    }

    private def dropEquipment(entity: IEntitySoulCustom, recentlyHit: Boolean, lootingLevel: Int) {
        val equipmentDropChances = entity.getFloatArray(EntityEquipmentDropChances)

        for(j <- 0 until 5) {
            val itemstack = if(entity.getItemStackArray(EntityEquipment) != null) entity.getItemStackArray(EntityEquipment)(j) else null

            val dropChance = equipmentDropChances(j)
            val flag1 = dropChance > 1.0F

            if(itemstack != null && itemstack.getItem != null && (recentlyHit || flag1) && entity.getRandom_I.nextFloat() - lootingLevel.toFloat * 0.01F < dropChance) {
                if(!flag1 && itemstack.isItemStackDamageable) {
                    val maxStackDamage = Math.max(itemstack.getMaxDurability - 25, 1)
                    var stackDamage = itemstack.getMaxDurability - entity.getRandom_I.nextInt(entity.getRandom_I.nextInt(maxStackDamage) + 1)

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
        val drops = SoulHelper.geneRegistry.getValueFromAllele[Array[ItemStack]](entity, Genes.GeneRareItemDrops)
        val dropChances = entity.getFloatArray(EntityEquipmentDropChances)

        if(drops != null && drops.length != 0) {
            for(i <- 0 until drops.length if entity.getRandom_I.nextInt((dropChances(i) * 100F).toInt) == 0) {
                entity.asInstanceOf[EntityLiving].entityDropItem(drops(i), 0.0F)
            }
        }
    }
}