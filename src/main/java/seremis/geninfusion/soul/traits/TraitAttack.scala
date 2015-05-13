package seremis.geninfusion.soul.traits

import net.minecraft.enchantment.{Enchantment, EnchantmentHelper}
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.{Entity, EntityLiving, EntityLivingBase, SharedMonsterAttributes}
import net.minecraft.item.EnumAction
import net.minecraft.util.{DamageSource, MathHelper}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitAttack extends Trait {

    override def attackEntity(entity: IEntitySoulCustom, entityToAttack: Entity, distance: Float) {
        var attackTime = entity.getInteger(ENTITY_ATTACK_TIME)

        if(attackTime <= 0 && distance < 2.0F && entityToAttack.boundingBox.maxY > entity.getBoundingBox.minY && entityToAttack.boundingBox.minY < entity.getBoundingBox.maxY) {
            attackTime = 20
            entity.attackEntityAsMob(entityToAttack)
        }
        entity.setInteger(ENTITY_ATTACK_TIME, attackTime)
    }

    override def attackEntityAsMob(entity: IEntitySoulCustom, entityToAttack: Entity): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        if(SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_AI_ATTACK_ON_COLLIDE)) {
            var attackDamage = living.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue.toFloat
            var knockbackLevel = 0

            if(entityToAttack.isInstanceOf[EntityLivingBase]) {
                attackDamage += EnchantmentHelper.getEnchantmentModifierLiving(living, entityToAttack.asInstanceOf[EntityLivingBase])
                knockbackLevel += EnchantmentHelper.getKnockbackModifier(living, entityToAttack.asInstanceOf[EntityLivingBase])
            }

            val hasAttacked = entityToAttack.attackEntityFrom(DamageSource.causeMobDamage(living), attackDamage)

            if(hasAttacked) {
                if(knockbackLevel > 0) {
                    entityToAttack.addVelocity((-MathHelper.sin(living.rotationYaw * Math.PI.toFloat / 180.0F) * knockbackLevel.toFloat * 0.5F).toDouble, 0.1D, (MathHelper.cos(living.rotationYaw * Math.PI.toFloat / 180.0F) * knockbackLevel.toFloat * 0.5F).toDouble)
                    living.motionX *= 0.6D
                    living.motionZ *= 0.6D
                }

                val j = EnchantmentHelper.getFireAspectModifier(living)

                if(j > 0) {
                    entityToAttack.setFire(j * 4)
                }

                if(entityToAttack.isInstanceOf[EntityLivingBase]) {
                    EnchantmentHelper.func_151384_a(entityToAttack.asInstanceOf[EntityLivingBase], living)
                }
                EnchantmentHelper.func_151385_b(living, entityToAttack)
            }
            return hasAttacked
        }
        living.setLastAttacker(entityToAttack)
        false
    }

    override def attackEntityWithRangedAttack(entity: IEntitySoulCustom, target: EntityLivingBase, distanceModified: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        if(living.getHeldItem != null && living.getHeldItem.getItemUseAction == EnumAction.bow) {
            val entityarrow = new EntityArrow(living.worldObj, living, target, 1.6F, (14 - living.worldObj.difficultySetting.getDifficultyId * 4).toFloat)
            val powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, living.getHeldItem)
            val punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, living.getHeldItem)

            entityarrow.setDamage((distanceModified * 2.0F).toDouble + entity.getRandom.nextGaussian() * 0.25D + (entity.getWorld.difficultySetting.getDifficultyId.toFloat * 0.11F).toDouble)

            if(powerLevel > 0) {
                entityarrow.setDamage(entityarrow.getDamage + powerLevel.toDouble * 0.5D + 0.5D)
            }

            if(punchLevel > 0) {
                entityarrow.setKnockbackStrength(punchLevel)
            }

            if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, living.getHeldItem) > 0) {
                entityarrow.setFire(100)
            }

            entity.playSound("random.bow", 1.0F, 1.0F / (entity.getRandom.nextFloat() * 0.4F + 0.8F))
            entity.getWorld.spawnEntityInWorld(entityarrow)
        }
    }
}