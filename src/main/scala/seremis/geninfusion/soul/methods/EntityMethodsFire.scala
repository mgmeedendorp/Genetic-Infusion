package seremis.geninfusion.soul.methods

import java.util.Random

import net.minecraft.util.{DamageSource, MathHelper}
import seremis.geninfusion.api.lib.Genes._
import seremis.geninfusion.api.lib.reflection.FunctionLib._
import seremis.geninfusion.api.lib.reflection.VariableLib._
import seremis.geninfusion.api.soul.{IEntityMethod, IEntitySoulCustom}
import seremis.geninfusion.api.util.EntityMethodHelper._

object EntityMethodsFire {

    class MethodFirstTick extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, preventSuperCall: () => Unit, args: Any*): Option[Unit] = firstTick(entity, preventSuperCall)

        def firstTick(implicit entity: IEntitySoulCustom, preventSuperCall: () => Unit): Option[Unit] = {
            FuncEntityIsImmuneToFire.boolean(GeneImmuneToFire.booleanGene)
            None
        }
    }

    class MethodOnUpdate extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, preventSuperCall: () => Unit, args: Any*): Option[Unit] = onUpdate(entity, preventSuperCall)

        def onUpdate(implicit entity: IEntitySoulCustom, preventSuperCall: () => Unit): Option[Unit] = {
            val burnsInDaylight = GeneBurnsInDaylight.booleanGene
            val childrenBurnInDaylight = GeneChildrenBurnInDaylight.booleanGene
            val isImmuneToFire = entity.isImmuneToFire

            if(burnsInDaylight && !isImmuneToFire) {
                if(entity.worldObj.isDaytime && !entity.worldObj.isRemote && (!entity.isChild || childrenBurnInDaylight)) {
                    val brightness = entity.getBrightness(1.0F)

                    val blockX = MathHelper.floor_double(entity.posX)
                    val blockY = MathHelper.floor_double(entity.posY)
                    val blockZ = MathHelper.floor_double(entity.posZ)

                    if(brightness > 0.5F && VarEntityRand.obj[Random].nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F && entity.worldObj.canBlockSeeTheSky(blockX, blockY, blockZ)) {
                        var burnFlag = true
                        val helmet = entity.getEquipmentInSlot(4)

                        if(helmet != null) {
                            if(helmet.isItemStackDamageable) {
                                helmet.setMetadata(helmet.getCurrentDurability + VarEntityRand.obj[Random].nextInt(2))

                                if(helmet.getCurrentDurability >= helmet.getMaxDurability) {
                                    entity.renderBrokenItemStack(helmet)
                                    entity.setCurrentItemOrArmor(4, null)
                                }
                            }

                            burnFlag = false
                        }

                        if(burnFlag) {
                            entity.setFire(8)
                        }
                    }
                }
            }

            if(entity.worldObj.isRemote) {
                VarEntityFire.int(0)
            } else if(VarEntityFire.int > 0) {
                if(isImmuneToFire) {
                    VarEntityFire.int(VarEntityFire.int + 4)

                    if(VarEntityFire.int < 0) {
                        VarEntityFire.int(0)
                    }
                } else {
                    if(VarEntityFire.int % 20 == 0) {
                        entity.attackEntityFrom(DamageSource.onFire, 1.0F)
                    }

                    VarEntityFire.int_--
                }
            }

            if(entity.handleLavaMovement()) {
                FuncEntitySetOnFireFromLava.method()
                VarEntityFallDistance.float(VarEntityFallDistance.float * 0.5F)
            }

            if(entity.posY < -64.0D) {
                entity.setDead()
            }

            if(!entity.worldObj.isRemote) {
                FuncEntitySetFlag.method(0, VarEntityFire.int > 0)
            }

            if(entity.isEntityAlive && entity.isWet) {
                entity.extinguish()
            }

            None
        }
    }
}
