package seremis.geninfusion.soul.traits

import net.minecraft.enchantment.{Enchantment, EnchantmentHelper}
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.{Entity, EntityLiving, EntityLivingBase, SharedMonsterAttributes}
import net.minecraft.item.EnumAction
import net.minecraft.util.{DamageSource, MathHelper}
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitAttack extends Trait {

    override def attackEntity(entity: IEntitySoulCustom, entityToAttack: Entity, distance: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        val jumpAtAttackTarget = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneJumpAtAttackTarget)
        val minAttackBrightness = SoulHelper.geneRegistry.getValueFromAllele[Float](entity, Genes.GeneMinAttackBrightness)
        val maxAttackBrightness = SoulHelper.geneRegistry.getValueFromAllele[Float](entity, Genes.GeneMaxAttackBrightness)

        val brightness = living.getBrightness(1.0F)

        if(brightness < minAttackBrightness && brightness > maxAttackBrightness) {
            entity.setObject(EntityEntityToAttack, null)
        } else {
            if(jumpAtAttackTarget && distance > 2.0F && distance < 6.0F && entity.getRandom_I.nextInt(10) == 0) {
                if(living.onGround) {
                    val d0 = entityToAttack.posX - living.posX
                    val d1 = entityToAttack.posZ - living.posZ
                    val f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1)
                    living.motionX = d0 / f2.toDouble * 0.5D * 0.800000011920929D + living.motionX * 0.20000000298023224D
                    living.motionZ = d1 / f2.toDouble * 0.5D * 0.800000011920929D + living.motionZ * 0.20000000298023224D
                    living.motionY = 0.4000000059604645D
                }
            } else {
                var attackTime = entity.getInteger(EntityAttackTime)

                if(attackTime <= 0 && distance < 2.0F && entityToAttack.boundingBox.maxY > entity.getBoundingBox_I.minY && entityToAttack.boundingBox.minY < entity.getBoundingBox_I.maxY) {
                    attackTime = 20
                    entity.attackEntityAsMob_I(entityToAttack)
                }
                entity.setInteger(EntityAttackTime, attackTime)
            }
        }
    }

    override def attackEntityAsMob(entity: IEntitySoulCustom, entityToAttack: Entity): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        if(SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneAIAttackOnCollide)) {
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

            entityarrow.setDamage((distanceModified * 2.0F).toDouble + entity.getRandom_I.nextGaussian() * 0.25D + (entity.getWorld_I.difficultySetting.getDifficultyId.toFloat * 0.11F).toDouble)

            if(powerLevel > 0) {
                entityarrow.setDamage(entityarrow.getDamage + powerLevel.toDouble * 0.5D + 0.5D)
            }

            if(punchLevel > 0) {
                entityarrow.setKnockbackStrength(punchLevel)
            }

            if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, living.getHeldItem) > 0) {
                entityarrow.setFire(100)
            }

            entity.playSound_I("random.bow", 1.0F, 1.0F / (entity.getRandom_I.nextFloat() * 0.4F + 0.8F))
            entity.getWorld_I.spawnEntityInWorld(entityarrow)
        }
    }
}