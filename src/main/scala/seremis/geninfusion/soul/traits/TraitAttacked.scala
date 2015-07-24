package seremis.geninfusion.soul.traits

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.item.EntityXPOrb
import net.minecraft.entity.passive.EntityTameable
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.entity.{EntityLiving, EntityLivingBase}
import net.minecraft.potion.Potion
import net.minecraft.util.DamageSource
import net.minecraftforge.common.ForgeHooks
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitAttacked extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        entity.setBoolean(EntityInvulnerable, SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneInvulnerable))
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        var attackTime = entity.getInteger(EntityAttackTime)
        var hurtTime = entity.getInteger(EntityHurtTime)
        var hurtResistantTime = entity.getInteger(EntityHurtResistantTime)
        var recentlyHit = entity.getInteger(EntityRecentlyHit)
        val ticksExisted = entity.getInteger(EntityTicksExisted)
        var lastAttacker = entity.getObject(EntityLastAttacker).asInstanceOf[EntityLivingBase]
        val entityLivingToAttack = entity.getObject(EntityEntityLivingToAttack).asInstanceOf[EntityLivingBase]

        if(attackTime > 0) {
            attackTime -= 1
        }
        if(hurtTime > 0) {
            hurtTime -= 1
        }
        if(hurtResistantTime > 0 && !entity.isInstanceOf[EntityPlayerMP]) {
            hurtResistantTime -= 1
        }
        if(entity.asInstanceOf[EntityLiving].getHealth <= 0.0F) {
            entity.onDeathUpdate_I
        }
        if(recentlyHit > 0) {
            recentlyHit -= 1
        } else {
            entity.setObject(EntityAttackingPlayer, null)
        }

        if(lastAttacker != null && !lastAttacker.isEntityAlive) {
            lastAttacker = null
        }

        if(entityLivingToAttack != null) {
            if(!entityLivingToAttack.isEntityAlive) {
                living.setRevengeTarget(null)
            } else if(ticksExisted - entity.getInteger(EntityRevengeTimer) > 100) {
                living.setRevengeTarget(null)
            }
        }

        entity.setInteger(EntityAttackTime, attackTime)
        entity.setInteger(EntityHurtTime, hurtTime)
        entity.setInteger(EntityHurtResistantTime, hurtResistantTime)
        entity.setInteger(EntityRecentlyHit, recentlyHit)
        entity.setInteger(EntityTicksExisted, ticksExisted)
        entity.setObject(EntityLastAttacker, lastAttacker)
        entity.setObject(EntityEntityLivingToAttack, entityLivingToAttack)

        if(!entity.getWorld_I.isRemote) {
            val arrowCount = living.getArrowCountInEntity
            if(arrowCount > 0) {
                if(living.arrowHitTimer <= 0) {
                    living.arrowHitTimer = 20 * (30 - arrowCount)
                }

                living.arrowHitTimer -= 1

                if(living.arrowHitTimer <= 0) {
                    living.setArrowCountInEntity(arrowCount - 1)
                }
            }

            if(living.ticksExisted % 20 == 0) {
                living.getCombatTracker.func_94549_h()
            }
        }
    }

    override def attackEntityFrom(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        var dealtDamage = damage

        if(entity.getBoolean(EntityInvulnerable)) {
            false
        } else if(entity.getWorld_I.isRemote) {
            false
        } else {
            entity.setInteger(EntityEntityAge, 0)

            if(living.getHealth <= 0.0F) {
                false
            } else if(source.isFireDamage && entity.asInstanceOf[EntityLiving].isPotionActive(Potion.fireResistance)) {
                false
            } else {
                if((source == DamageSource.anvil || source == DamageSource.fallingBlock) && entity.asInstanceOf[EntityLiving].getEquipmentInSlot(4) != null) {
                    entity.asInstanceOf[EntityLiving].getEquipmentInSlot(4).damageItem((dealtDamage * 4.0F + entity.getRandom_I.nextFloat() * dealtDamage * 2.0F).toInt, entity.asInstanceOf[EntityLivingBase])
                    dealtDamage *= 0.75F
                }

                entity.setFloat(EntityLimbSwingAmount, 1.5F)
                var flag = true

                var hurtResistantTime = entity.getInteger(EntityHurtResistantTime)
                val maxHurtResistantTime = entity.getInteger(EntityMaxHurtResistantTime)
                var recentlyHit = entity.getInteger(EntityRecentlyHit)
                var hurtTime = entity.getInteger(EntityHurtTime)
                var maxHurtTime = entity.getInteger(EntityMaxHurtTime)
                var attackedAtYaw = 0.0f
                var lastDamage = entity.getFloat(EntityLastDamage)
                var attackingPlayer = entity.getObject(EntityAttackingPlayer).asInstanceOf[EntityPlayer]

                if(hurtResistantTime.toFloat > maxHurtResistantTime.toFloat / 2.0F) {
                    if(dealtDamage <= lastDamage) {
                        return false
                    }

                    entity.damageEntity_I(source, dealtDamage - lastDamage)
                    lastDamage = dealtDamage
                    flag = false
                } else {
                    lastDamage = dealtDamage
                    entity.setFloat(EntityPrevHealth, living.getHealth)
                    hurtResistantTime = maxHurtResistantTime
                    entity.damageEntity_I(source, dealtDamage)

                    hurtTime = 10
                    maxHurtTime = 10
                }

                attackedAtYaw = 0.0F
                val attacker = source.getEntity

                if(attacker != null) {
                    if(attacker.isInstanceOf[EntityLivingBase]) {
                        entity.asInstanceOf[EntityLiving].setRevengeTarget(attacker.asInstanceOf[EntityLivingBase])
                    }

                    if(attacker.isInstanceOf[EntityPlayer]) {
                        recentlyHit = 100
                        attackingPlayer = attacker.asInstanceOf[EntityPlayer]
                    } else if(attacker.isInstanceOf[EntityTameable]) {
                        val tameable = attacker.asInstanceOf[EntityTameable]

                        if(tameable.isTamed) {
                            recentlyHit = 100
                            attackingPlayer = null
                        }
                    } else if(attacker.isInstanceOf[IEntitySoulCustom] && SoulHelper.geneRegistry.getValueFromAllele[Boolean](attacker.asInstanceOf[IEntitySoulCustom], Genes.GeneIsTameable)) {
                        val attackerCustom = entity.asInstanceOf[IEntitySoulCustom]

                        if(attackerCustom.isTamed_I) {
                            recentlyHit = 100
                            attackingPlayer = null
                        }
                    }
                }

                if(flag) {
                    entity.getWorld_I.setEntityState(living, 2)

                    if(source != DamageSource.drown) {
                        entity.setBeenAttacked_I
                    }

                    if(attacker != null) {
                        var dx = attacker.posX - entity.getDouble(EntityPosX)
                        var dz = attacker.posZ - entity.getDouble(EntityPosZ)

                        while(dx * dx + dz * dz < 1.0E-4D) {
                            dx = (Math.random() - Math.random()) * 0.01D
                            dz = (Math.random() - Math.random()) * 0.01D
                        }

                        attackedAtYaw = (Math.atan2(dz, dx) * 180.0D / Math.PI).toFloat - entity.getFloat(EntityRotationYaw)
                        entity.asInstanceOf[EntityLiving].knockBack(attacker, dealtDamage, dx, dz)
                    } else {
                        attackedAtYaw = ((Math.random() * 2.0D).toInt * 180).toFloat
                    }
                }

                entity.setInteger(EntityHurtResistantTime, hurtResistantTime)
                entity.setInteger(EntityMaxHurtResistantTime, maxHurtResistantTime)
                entity.setInteger(EntityRecentlyHit, recentlyHit)
                entity.setInteger(EntityHurtTime, hurtTime)
                entity.setInteger(EntityMaxHurtTime, maxHurtTime)
                entity.setFloat(EntityAttackedAtYaw, attackedAtYaw)
                entity.setFloat(EntityLastDamage, lastDamage)
                entity.setObject(EntityAttackingPlayer, attackingPlayer)

                var sound: String = null

                if(living.getHealth <= 0.0F) {
                    sound = entity.getDeathSound_I

                    if(flag && sound != null) {
                        entity.playSound_I(sound, entity.getSoundVolume_I, entity.getSoundPitch_I)
                    }

                    living.onDeath(source)
                } else {
                    sound = entity.getHurtSound_I

                    if(flag && sound != null) {
                        entity.playSound_I(sound, entity.getSoundVolume_I, entity.getSoundPitch_I)
                    }
                }

                if(living.riddenByEntity != entity && living.ridingEntity != entity) {
                    if(attacker != entity) {
                        entity.setObject(EntityEntityToAttack, attacker)
                    }
                    true
                } else {
                    true
                }
            }
        }
    }

    override def damageEntity(entity: IEntitySoulCustom, source: DamageSource, damage: Float) {
        val living = entity.asInstanceOf[EntityLiving]

        if(!entity.getBoolean(EntityInvulnerable)) {
            var damageDealt = ForgeHooks.onLivingHurt(living, source, damage)

            if(damageDealt <= 0)
                return

            damageDealt = entity.applyArmorCalculations_I(source, damageDealt)
            damageDealt = entity.applyPotionDamageCalculations_I(source, damageDealt)

            val f1 = damageDealt

            damageDealt = Math.max(damageDealt - living.getAbsorptionAmount, 0.0F)

            living.setAbsorptionAmount(living.getAbsorptionAmount - (f1 - damageDealt))

            if(damageDealt != 0.0F) {
                val health = living.getHealth

                living.setHealth(health - damageDealt)
                living.getCombatTracker.func_94547_a(source, health, damageDealt)
                living.setAbsorptionAmount(living.getAbsorptionAmount - damageDealt)
            }
        }
    }

    override def applyArmorCalculations(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Float = {
        var damageDealt = damage

        if(!source.isUnblockable) {
            val armorDamageMultiplier = 25 - entity.asInstanceOf[EntityLiving].getTotalArmorValue
            val armorDamage = damageDealt * armorDamageMultiplier.toFloat

            entity.damageArmor_I(damageDealt)
            damageDealt = armorDamage / 25.0F
        }
        damageDealt
    }

    override def applyPotionDamageCalculations(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Float = {
        val living = entity.asInstanceOf[EntityLiving]
        var damageDealt = damage

        if(source.isDamageAbsolute) {
            damageDealt
        } else {
            var damageModifier = 0
            var j = 0
            var f1 = 0.0f

            if(living.isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld) {
                damageModifier = (living.getActivePotionEffect(Potion.resistance).getAmplifier + 1) * 5
                j = 25 - damageModifier
                f1 = damageDealt * j.toFloat
                damageDealt = f1 / 25.0F
            }
            if(damageDealt <= 0.0F) {
                0.0F
            } else {
                damageModifier = EnchantmentHelper.getEnchantmentModifierDamage(living.getInventory, source)
                if(damageModifier > 20) {
                    damageModifier = 20
                }
                if(damageModifier > 0 && damageModifier <= 20) {
                    j = 25 - damageModifier
                    f1 = damageDealt * j.toFloat
                    damageDealt = f1 / 25.0F
                }
                damageDealt
            }
        }
    }

    override def onDeath(entity: IEntitySoulCustom, source: DamageSource) {
        val living = entity.asInstanceOf[EntityLiving]

        val attacker = source.getEntity
        val attackTarget = living.func_94060_bK()

        if(entity.getInteger(EntityScoreValue) >= 0 && attackTarget != null) {
            attackTarget.addToPlayerScore(living, entity.getInteger(EntityScoreValue))
        }

        if(attacker != null) {
            attacker.onKillEntity(living)
        }

        entity.setBoolean(EntityDead, true)
        living.getCombatTracker.func_94549_h()
    }

    override def setDead(entity: IEntitySoulCustom) {
        entity.setBoolean(EntityIsDead, true)
    }

    override def onDeathUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val deathTime = entity.getInteger(EntityDeathTime) + 1

        if(deathTime >= 20) {
            var experiencePoints = 0

            if(!entity.getWorld_I.isRemote && (entity.getInteger(EntityRecentlyHit) > 0 || entity.isInstanceOf[EntityPlayer]) && !living.isChild && entity.getWorld_I.getGameRules.getGameRuleBooleanValue("doMobLoot")) {
                experiencePoints = entity.getExperiencePoints_I(entity.getObject(EntityAttackingPlayer).asInstanceOf[EntityPlayer])

                while(experiencePoints > 0) {
                    val xpSplit = EntityXPOrb.getXPSplit(experiencePoints)
                    experiencePoints -= xpSplit
                    entity.getWorld_I.spawnEntityInWorld(new EntityXPOrb(entity.getWorld_I, living.posX, living.posY, living.posZ, xpSplit))
                }
            }

            living.setDead()

            for(i <- 0 until 20) {
                val posX = living.posX + (entity.getRandom_I.nextFloat * living.width * 2.0F) - living.width
                val posY = living.posY + entity.getRandom_I.nextFloat * living.height
                val posZ = living.posZ + (entity.getRandom_I.nextFloat * living.width * 2.0F) - living.width

                val velX = entity.getRandom_I.nextGaussian * 0.02D
                val velY = entity.getRandom_I.nextGaussian * 0.02D
                val velZ = entity.getRandom_I.nextGaussian * 0.02D

                entity.getWorld_I.spawnParticle("explode", posX, posY, posZ, velX, velY, velZ)
            }

        }

        entity.setInteger(EntityDeathTime, deathTime)
    }

    override def getExperiencePoints(entity: IEntitySoulCustom, player: EntityPlayer): Int = {
        val living = entity.asInstanceOf[EntityLiving]

        var experienceValue = entity.getInteger(EntityExperienceValue)

        if(living.isChild) {
            val childXPModifier = SoulHelper.geneRegistry.getValueFromAllele[Float](entity, Genes.GeneChildXPModifier)

            experienceValue = (experienceValue * childXPModifier).toInt
        }

        if(entity.getInteger(EntityExperienceValue) > 0) {
            val inventory = living.getInventory
            val equipmentDropChances = entity.getFloatArray(EntityEquipmentDropChances)

            for(i <- 0 until inventory.length) {
                if(inventory(i) != null && equipmentDropChances(i) <= 1.0F) {
                    experienceValue += 1 + entity.getRandom_I.nextInt(3)
                }
            }

            experienceValue
        } else {
            entity.getInteger(EntityExperienceValue)
        }
    }
}