package seremis.geninfusion.soul.traits

import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.{Entity, EntityLiving}
import net.minecraft.util.{DamageSource, MathHelper}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.lib.VariableLib._
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

class TraitFire extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        entity.setBoolean(EntityIsImmuneToFire, SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GeneImmuneToFire))
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val burnsInDayLight: Boolean = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneBurnsInDaylight)
        val childrenBurnInDaylight: Boolean = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneChildrenBurnInDaylight)

        val posX = entity.getDouble(EntityPosX)
        val posY = entity.getDouble(EntityPosY)
        val posZ = entity.getDouble(EntityPosZ)

        val isImmuneToFire = entity.getBoolean(EntityIsImmuneToFire)

        if(burnsInDayLight && !isImmuneToFire) {
            if(entity.getWorld_I.isDaytime && !entity.getWorld_I.isRemote && (!living.isChild || childrenBurnInDaylight)) {
                val brightness = living.getBrightness(1.0F)

                if(brightness > 0.5F && entity.getRandom_I.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F && entity.getWorld_I.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) {
                    var flag = true
                    val helmet = living.getEquipmentInSlot(4)

                    if(helmet != null) {
                        if(helmet.isItemStackDamageable) {
                            helmet.setMetadata(helmet.getCurrentDurability + entity.getRandom_I.nextInt(2))

                            if(helmet.getCurrentDurability >= helmet.getMaxDurability) {
                                living.renderBrokenItemStack(helmet)
                                living.setCurrentItemOrArmor(4, null)
                            }
                        }

                        flag = false
                    }

                    if(flag) {
                        living.setFire(8)
                    }
                }
            }
        }

        var fire = entity.getInteger(EntityFire)

        if(entity.getWorld_I.isRemote) {
            fire = 0
        } else if(fire > 0) {
            if(isImmuneToFire) {
                fire -= 4

                if(fire < 0) {
                    fire = 0
                }
            } else {
                if(fire % 20 == 0) {
                    entity.attackEntityFrom_I(DamageSource.onFire, 1.0F)
                }

                fire -= 1
            }
        }
        entity.setInteger(EntityFire, fire)

        if(entity.asInstanceOf[EntityLiving].handleLavaMovement()) {
            entity.setOnFireFromLava_I
            entity.setFloat(EntityFallDistance, entity.getFloat(EntityFallDistance) * 0.5F)
        }

        if(entity.getDouble(EntityPosY) < -64.0D) {
            living.setDead()
        }

        if(!entity.getWorld_I.isRemote) {
            entity.setFlag_I(0, fire > 0)
        }

        if(living.isEntityAlive && living.isWet) {
            living.extinguish()
        }
    }

    override def attackEntityAsMob(entity: IEntitySoulCustom, entityToAttack: Entity): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]
        val setEntitiesOnFire: Boolean = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneSetOnFireFromAttack)
        val difficulty = entity.getWorld_I.difficultySetting.getDifficultyId

        if(setEntitiesOnFire && living.getEquipmentInSlot(0) == null && living.isBurning && entity.getRandom_I.nextFloat() < difficulty.toFloat * 0.3F) {
            entityToAttack.setFire(2 * difficulty)
        }
        true
    }

    override def setOnFireFromLava(entity: IEntitySoulCustom) {
        if(!entity.getBoolean(EntityIsImmuneToFire)) {
            entity.attackEntityFrom_I(DamageSource.lava, 4.0F)
            entity.asInstanceOf[EntityLiving].setFire(15)
        }
    }
    override def onStruckByLightning(entity: IEntitySoulCustom, lightningBolt: EntityLightningBolt) {
        val living = entity.asInstanceOf[EntityLiving]

        entity.dealFireDamage_I(5)
        entity.setInteger(EntityFire, entity.getInteger(EntityFire) + 1)

        if(entity.getInteger(EntityFire) == 0) {
            living.setFire(8)
        }
    }
}