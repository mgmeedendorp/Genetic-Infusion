package seremis.geninfusion.soul.traits

import net.minecraft.entity.{Entity, EntityLiving}
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.util.{DamageSource, MathHelper}
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.soul.lib.Genes

class TraitFire extends Trait {

    override def firstTick(entity: IEntitySoulCustom) {
        entity.setBoolean("isImmuneToFire", SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_IMMUNE_TO_FIRE))
    }

    override def onUpdate(entity: IEntitySoulCustom) {
        val living = entity.asInstanceOf[EntityLiving]

        val burnsInDayLight: Boolean = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_BURNS_IN_DAYLIGHT)
        val childrenBurnInDaylight: Boolean = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_CHILDREN_BURN_IN_DAYLIGHT)

        val posX = entity.getDouble("posX")
        val posY = entity.getDouble("posY")
        val posZ = entity.getDouble("posZ")

        val isImmuneToFire = entity.getBoolean("isImmuneToFire")

        if(burnsInDayLight && !isImmuneToFire) {
            if(entity.getWorld.isDaytime && !entity.getWorld.isRemote && (!living.isChild || childrenBurnInDaylight)) {
                val brightness = living.getBrightness(1.0F)

                if(brightness > 0.5F && entity.getRandom.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F && entity.getWorld.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) {
                    var flag = true
                    val helmet = living.getEquipmentInSlot(4)

                    if(helmet != null) {
                        if(helmet.isItemStackDamageable) {
                            helmet.setItemDamage(helmet.getItemDamageForDisplay + entity.getRandom.nextInt(2))

                            if(helmet.getItemDamageForDisplay >= helmet.getMaxDamage) {
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

        var fire = entity.getInteger("fire")

        if(entity.getWorld.isRemote) {
            fire = 0
        } else if(fire > 0) {
            if(isImmuneToFire) {
                fire -= 4

                if(fire < 0) {
                    fire = 0
                }
            } else {
                if(fire % 20 == 0) {
                    entity.attackEntityFrom(DamageSource.onFire, 1.0F)
                }

                fire -= 1
            }
        }

        if(entity.asInstanceOf[EntityLiving].handleLavaMovement()) {
            entity.setOnFireFromLava
            entity.setFloat("fallDistance", entity.getFloat("fallDistance") * 0.5F)
        }

        if(entity.getDouble("posY") < -64.0D) {
            living.setDead()
        }

        if(!entity.getWorld.isRemote) {
            entity.setFlag(0, fire > 0)
        }

        if(living.isEntityAlive && living.isWet) {
            living.extinguish()
        }
    }

    override def attackEntityAsMob(entity: IEntitySoulCustom, entityToAttack: Entity): Boolean = {
        val living = entity.asInstanceOf[EntityLiving]
        val setEntitiesOnFire: Boolean = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_SET_ON_FIRE_FROM_ATTACK)
        val difficulty = entity.getWorld.difficultySetting.getDifficultyId

        if(setEntitiesOnFire && living.getEquipmentInSlot(0) == null && living.isBurning && entity.getRandom.nextFloat() < difficulty.toFloat * 0.3F) {
            entityToAttack.setFire(2 * difficulty)
        }
        true
    }

    override def setOnFireFromLava(entity: IEntitySoulCustom) {
        if(!entity.getBoolean("isImmuneToFire")) {
            entity.attackEntityFrom(DamageSource.lava, 4.0F)
            entity.asInstanceOf[EntityLiving].setFire(15)
        }
    }

    override def onStruckByLightning(entity: IEntitySoulCustom, lightningBolt: EntityLightningBolt) {
        val living = entity.asInstanceOf[EntityLiving]

        entity.dealFireDamage(5)
        entity.setInteger("fire", entity.getInteger("fire") + 1)

        if(entity.getInteger("fire") == 0) {
            living.setFire(8)
        }

        val canBeCharged = SoulHelper.geneRegistry.getValueFromAllele[Boolean](entity, Genes.GENE_CAN_BE_CHARGED)
        if(canBeCharged) {
            //TODO charged creeper
        }
    }
}