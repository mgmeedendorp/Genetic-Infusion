package seremis.geninfusion.soul.traits

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.passive.EntityTameable
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.entity.{EntityLiving, EntityLivingBase}
import net.minecraft.potion.Potion
import net.minecraft.util.DamageSource
import net.minecraftforge.common.ForgeHooks
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitAttacked extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        entity.setBoolean("invulnerable", SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_INVULNERABLE))
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        var attackTime = entity.getInteger("attackTime")
        var hurtTime = entity.getInteger("hurtTime")
        var hurtResistantTime = entity.getInteger("hurtResistantTime")
        var recentlyHit = entity.getInteger("recentlyHit")
        val ticksExisted = entity.getInteger("ticksExisted")
        var lastAttacker = entity.getObject("lastAttacker").asInstanceOf[EntityLivingBase]
        val entityLivingToAttack = entity.getObject("entityLivingToAttack").asInstanceOf[EntityLivingBase]

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
            entity.onDeathUpdate
        }
        if(recentlyHit > 0) {
            recentlyHit -= 1
        } else {
            entity.setObject("attackingPlayer", null)
        }

        if(lastAttacker != null && !lastAttacker.isEntityAlive) {
            lastAttacker = null
        }

        if(entityLivingToAttack != null) {
            if(!entityLivingToAttack.isEntityAlive) {
                living.setRevengeTarget(null)
            } else if(ticksExisted - entity.getInteger("revengeTimer") > 100) {
                living.setRevengeTarget(null)
            }
        }

        entity.setInteger("attackTime", attackTime)
        entity.setInteger("hurtTime", hurtTime)
        entity.setInteger("hurtResistantTime", hurtResistantTime)
        entity.setInteger("recentlyHit", recentlyHit)
        entity.setInteger("ticksExisted", ticksExisted)
        entity.setObject("lastAttacker", lastAttacker)
        entity.setObject("entityLivingToAttack", entityLivingToAttack)

        if(!entity.getWorld.isRemote) {
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
                living.func_110142_aN().func_94549_h()
            }
        }
    }

    override def attackEntityFrom(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]

        var dealtDamage = damage

        if(ForgeHooks.onLivingAttack(entity.asInstanceOf[EntityLivingBase], source, dealtDamage))
            return false

        if(entity.getBoolean("invulnerable")) {
            false
        } else if(entity.getWorld.isRemote) {
            false
        } else {
            entity.setInteger("entityAge", 0)

            if(living.getHealth <= 0.0F) {
                false
            } else if(source.isFireDamage && entity.asInstanceOf[EntityLiving].isPotionActive(Potion.fireResistance)) {
                false
            } else {
                if((source == DamageSource.anvil || source == DamageSource.fallingBlock) && entity.asInstanceOf[EntityLiving].getEquipmentInSlot(4) != null) {
                    entity.asInstanceOf[EntityLiving].getEquipmentInSlot(4).damageItem((dealtDamage * 4.0F + entity.getRandom.nextFloat() * dealtDamage * 2.0F).toInt, entity.asInstanceOf[EntityLivingBase])
                    dealtDamage *= 0.75F
                }

                entity.setFloat("limbSwingAmount", 1.5F)
                var flag = true

                var hurtResistantTime = entity.getInteger("hurtResistantTime")
                val maxHurtResistantTime = entity.getInteger("maxHurtResistantTime")
                var recentlyHit = entity.getInteger("recentlyHit")
                var hurtTime = entity.getInteger("hurtTime")
                var maxHurtTime = entity.getInteger("maxHurtTime")
                var attackedAtYaw = 0.0f
                var lastDamage = entity.getFloat("lastDamage")
                var attackingPlayer = entity.getObject("attackingPlayer").asInstanceOf[EntityPlayer]

                if(hurtResistantTime.toFloat > maxHurtResistantTime.toFloat / 2.0F) {
                    if(dealtDamage <= lastDamage) {
                        return false
                    }

                    entity.damageEntity(source, dealtDamage - lastDamage)
                    lastDamage = dealtDamage
                    flag = false
                } else {
                    lastDamage = dealtDamage
                    entity.setFloat("prevHealth", living.getHealth)
                    hurtResistantTime = maxHurtResistantTime
                    entity.damageEntity(source, dealtDamage)

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
                    } else if(attacker.isInstanceOf[IEntitySoulCustom] && SoulHelper.geneRegistry.getValueFromAllele[Boolean](attacker.asInstanceOf[IEntitySoulCustom], Genes.GENE_IS_TAMEABLE)) {
                        val attackerCustom = entity.asInstanceOf[IEntitySoulCustom]

                        if(attackerCustom.isTamed) {
                            recentlyHit = 100
                            attackingPlayer = null
                        }
                    }
                }

                if(flag) {
                    entity.getWorld.setEntityState(living, 2)

                    if(source != DamageSource.drown) {
                        entity.setBeenAttacked
                    }

                    if(attacker != null) {
                        var dx = attacker.posX - entity.getDouble("posX")
                        var dz = attacker.posZ - entity.getDouble("posZ")

                        while(dx * dx + dz * dz < 1.0E-4D) {
                            dx = (Math.random() - Math.random()) * 0.01D
                            dz = (Math.random() - Math.random()) * 0.01D
                        }

                        attackedAtYaw = (Math.atan2(dz, dx) * 180.0D / Math.PI).toFloat - entity.getFloat("rotationYaw")
                        entity.asInstanceOf[EntityLiving].knockBack(attacker, dealtDamage, dx, dz)
                    } else {
                        attackedAtYaw = ((Math.random() * 2.0D).toInt * 180).toFloat
                    }
                }

                entity.setInteger("hurtResistantTime", hurtResistantTime)
                entity.setInteger("maxHurtResistantTime", maxHurtResistantTime)
                entity.setInteger("recentlyHit", recentlyHit)
                entity.setInteger("hurtTime", hurtTime)
                entity.setInteger("maxHurtTime", maxHurtTime)
                entity.setFloat("attackedAtYaw", attackedAtYaw)
                entity.setFloat("lastDamage", lastDamage)
                entity.setObject("attackingPlayer", attackingPlayer)

                var sound: String = null

                if(living.getHealth <= 0.0F) {
                    sound = entity.getDeathSound

                    if(flag && sound != null) {
                        entity.playSound(sound, entity.getSoundVolume, entity.getSoundPitch)
                    }

                    living.onDeath(source)
                } else {
                    sound = entity.getHurtSound

                    if(flag && sound != null) {
                        entity.playSound(sound, entity.getSoundVolume, entity.getSoundPitch)
                    }
                }

                if(living.riddenByEntity != entity && living.ridingEntity != entity) {
                    if(attacker != entity) {
                        entity.setObject("entityToAttack", attacker)
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

        if(!entity.getBoolean("invulnerable")) {
            var damageDealt = ForgeHooks.onLivingHurt(living, source, damage)

            if(damageDealt <= 0)
                return

            damageDealt = entity.applyArmorCalculations(source, damageDealt)
            damageDealt = entity.applyPotionDamageCalculations(source, damageDealt)

            val f1 = damageDealt

            damageDealt = Math.max(damageDealt - living.getAbsorptionAmount, 0.0F)

            living.setAbsorptionAmount(living.getAbsorptionAmount - (f1 - damageDealt))

            if(damageDealt != 0.0F) {
                val health = living.getHealth

                living.setHealth(health - damageDealt)
                living.func_110142_aN().func_94547_a(source, health, damageDealt)
                living.setAbsorptionAmount(living.getAbsorptionAmount - damageDealt)
            }
        }
    }

    override def applyArmorCalculations(entity: IEntitySoulCustom, source: DamageSource, damage: Float): Float = {
        var damageDealt = damage

        if(!source.isUnblockable) {
            val armorDamageMultiplier = 25 - entity.asInstanceOf[EntityLiving].getTotalArmorValue
            val armorDamage = damageDealt * armorDamageMultiplier.toFloat

            entity.damageArmor(damageDealt)
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
                damageModifier = EnchantmentHelper.getEnchantmentModifierDamage(entity.asInstanceOf[EntityLiving].getLastActiveItems, source)
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

        if(ForgeHooks.onLivingDeath(entity.asInstanceOf[EntityLivingBase], source))
            return

        val attacker = source.getEntity
        val attackTarget = living.func_94060_bK()

        if(entity.getInteger("scoreValue") >= 0 && attackTarget != null) {
            attackTarget.addToPlayerScore(living, entity.getInteger("scoreValue"))
        }

        if(attacker != null) {
            attacker.onKillEntity(living)
        }

        entity.setBoolean("dead", true)
        living.func_110142_aN().func_94549_h()
    }
}