package seremis.geninfusion.soul.methods

import net.minecraft.entity.{DataWatcher, Entity}
import seremis.geninfusion.api.lib.reflection.VariableLib
import seremis.geninfusion.api.soul.{IEntityMethod, IEntitySoulCustom}

object EntityMethodsEntity {

    class MethodGetEntityId extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = getEntityId(entity)

        def getEntityId(entity: IEntitySoulCustom): Int = {
            entity.getInteger(VariableLib.EntityEntityId)
        }
    }

    class MethodSetEntityId extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setEntityId(entity, args(0).asInstanceOf[Int])

        def setEntityId(entity: IEntitySoulCustom, id: Int): Unit = {
            entity.setInteger(VariableLib.EntityEntityId, id)
        }
    }

    class MethodEntityInit extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = entityInit(entity)

        def entityInit(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodEntityInit extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = entityInit(entity)

        def entityInit(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodGetDataWatcher extends IEntityMethod[DataWatcher] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): DataWatcher = getDataWatcher(entity)

        def getDataWatcher(entity: IEntitySoulCustom): DataWatcher = {

        }
    }

    class MethodEquals extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = equals(entity)

        def equals(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodHashCode extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = hashCode(entity)

        def hashCode(entity: IEntitySoulCustom): Int = {

        }
    }

    class MethodPreparePlayerToSpawn extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = preparePlayerToSpawn(entity)

        def preparePlayerToSpawn(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodSetDead extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setDead(entity)

        def setDead(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodSetSize extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setSize(entity, args(0).asInstanceOf[Float], args(1).asInstanceOf[Float])

        def setSize(entity: IEntitySoulCustom, width: Float, height: Float): Unit = {

        }
    }

    class MethodSetRotation extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setRotation(entity, args(0).asInstanceOf[Float], args(1).asInstanceOf[Float])

        def setRotation(entity: IEntitySoulCustom, yaw: Float, pitch: Float): Unit = {

        }
    }

    class MethodSetPosition extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setPosition(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def setPosition(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Unit = {

        }
    }

    class MethodSetAngles extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setAngles(entity, args(0).asInstanceOf[Float], args(1).asInstanceOf[Float])

        def setAngles(entity: IEntitySoulCustom, yaw: Float, pitch: Float): Unit = {

        }
    }

    class MethodOnUpdate extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = onUpdate(entity)

        def onUpdate(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodOnEntityUpdate extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = onEntityUpdate(entity)

        def onEntityUpdate(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodGetMaxInPortalTime extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = getMaxInPortalTime(entity)

        def getMaxInPortalTime(entity: IEntitySoulCustom): Int = {

        }
    }

    class MethodSetOnFireFromLava extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setOnFireFromLava(entity)

        def setOnFireFromLava(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodSetFire extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setFire(entity, args(0).asInstanceOf[Int])

        def setFire(entity: IEntitySoulCustom, seconds: Int): Unit = {

        }
    }

    class MethodExtinguish extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = extinguish(entity)

        def extinguish(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodKill extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = kill(entity)

        def kill(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodIsOffsetPositionInLiquid extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isOffsetPositionInLiquid(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def isOffsetPositionInLiquid(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Boolean = {

        }
    }

    class MethodMoveEntity extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = moveEntity(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def moveEntity(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Unit = {

        }
    }

    class MethodGetSwimSound extends IEntityMethod[String] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): String = getSwimSound(entity)

        def getSwimSound(entity: IEntitySoulCustom): String = {

        }
    }

    class MethodDoBlockCollisions extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = doBlockCollisions(entity)

        def doBlockCollisions(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodPlayStepSound extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = playStepSound(entity, args(0).asInstanceOf[Int], args(1).asInstanceOf[Int], args(2).asInstanceOf[Int])

        def playStepSound(entity: IEntitySoulCustom, x: Int, y: Int, z: Int): Unit = {

        }
    }

    class MethodPlaySound extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = playSound(entity, args(0).asInstanceOf[String], args(1).asInstanceOf[Float], args(2).asInstanceOf[Float])

        def playSound(entity: IEntitySoulCustom, name: String, volume: Float, pitch: Float): Unit = {

        }
    }

    class MethodCanTriggerWalking extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = canTriggerWalking(entity)

        def canTriggerWalking(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodUpdateFallState extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = updateFallState(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Boolean])

        def updateFallState(entity: IEntitySoulCustom, distanceFallenThisTick: Double, isOnGround: Boolean): Unit = {

        }
    }

    class MethodGetBoundingBox extends IEntityMethod[AxisAlignedBB] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): AxisAlignedBB = getBoundingBox(entity)

        def getBoundingBox(entity: IEntitySoulCustom): AxisAlignedBB = {

        }
    }

    class MethodDealFireDamage extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = dealFireDamage(entity, args(0).asInstanceOf[Int])

        def dealFireDamage(entity: IEntitySoulCustom, amount: Int): Unit = {

        }
    }

    class MethodIsImmuneToFire extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isImmuneToFire(entity)

        def isImmuneToFire(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodFall extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = fall(entity, args(0).asInstanceOf[Float])

        def fall(entity: IEntitySoulCustom, distance: Float): Unit = {

        }
    }

    class MethodIsWet extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isWet(entity)

        def isWet(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodIsInWater extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isInWater(entity)

        def isInWater(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodHandleWaterMovement extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = handleWaterMovement(entity)

        def handleWaterMovement(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodGetSplashSound extends IEntityMethod[String] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): String = getSplashSound(entity)

        def getSplashSound(entity: IEntitySoulCustom): String = {

        }
    }

    class MethodIsInsideOfMaterial extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isInsideOfMaterial(entity)

        def isInsideOfMaterial(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodGetEyeHeight extends IEntityMethod[Float] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Float = getEyeHeight(entity)

        def getEyeHeight(entity: IEntitySoulCustom): Float = {

        }
    }

    class MethodHandleLavaMovement extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = handleLavaMovement(entity)

        def handleLavaMovement(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodMoveFlying extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = moveFlying(entity, args(0).asInstanceOf[Float], args(1).asInstanceOf[Float], args(2).asInstanceOf[Float])

        def moveFlying(entity: IEntitySoulCustom, strafe: Float, forward: Float, friction: Float): Unit = {

        }
    }

    class MethodGetBrightnessForRender extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = getBrightnessForRender(entity, args(0).asInstanceOf[Float])

        def getBrightnessForRender(entity: IEntitySoulCustom, p_70070_1_: Float): Int = {

        }
    }

    class MethodGetBrightness extends IEntityMethod[Float] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Float = getBrightness(entity, args(0).asInstanceOf[Float])

        def getBrightness(entity: IEntitySoulCustom, p_70013_1_: Float): Float = {

        }
    }

    class MethodSetWorld extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setWorld(entity, args(0).asInstanceOf[World])

        def setWorld(entity: IEntitySoulCustom, worldIn: World): Unit = {

        }
    }

    class MethodSetPositionAndRotation extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setPositionAndRotation(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double], args(3).asInstanceOf[Float], args(4).asInstanceOf[Float])

        def setPositionAndRotation(entity: IEntitySoulCustom, x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Unit = {

        }
    }

    class MethodSetLocationAndAngles extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setLocationAndAngles(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double], args(3).asInstanceOf[Float], args(4).asInstanceOf[Float])

        def setLocationAndAngles(entity: IEntitySoulCustom, x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Unit = {

        }
    }

    class MethodGetDistanceToEntity extends IEntityMethod[Float] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Float = getDistanceToEntity(entity, args(0).asInstanceOf[Entity])

        def getDistanceToEntity(entity: IEntitySoulCustom, entityIn: Entity): Float = {

        }
    }

    class MethodGetDistanceSq extends IEntityMethod[Double] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Double = getDistanceSq(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def getDistanceSq(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Double = {

        }
    }

    class MethodGetDistance extends IEntityMethod[Double] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Double = getDistance(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def getDistance(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Double = {

        }
    }

    class MethodGetDistanceSqToEntity extends IEntityMethod[Double] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Double = getDistanceSqToEntity(entity, args(0).asInstanceOf[Entity])

        def getDistanceSqToEntity(entity: IEntitySoulCustom, entityIn: Entity): Double = {

        }
    }

    class MethodOnCollideWithPlayer extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = onCollideWithPlayer(entity)

        def onCollideWithPlayer(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodApplyEntityCollision extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = applyEntityCollision(entity, args(0).asInstanceOf[Entity])

        def applyEntityCollision(entity: IEntitySoulCustom, entityIn: Entity): Unit = {

        }
    }

    class MethodAddVelocity extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = addVelocity(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def addVelocity(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Unit = {

        }
    }

    class MethodSetBeenAttacked extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setBeenAttacked(entity)

        def setBeenAttacked(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodAttackEntityFrom extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = attackEntityFrom(entity, args(0).asInstanceOf[Float])

        def attackEntityFrom(entity: IEntitySoulCustom, amount: Float): Boolean = {

        }
    }

    class MethodCanBeCollidedWith extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = canBeCollidedWith(entity)

        def canBeCollidedWith(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodCanBePushed extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = canBePushed(entity)

        def canBePushed(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodAddToPlayerScore extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = addToPlayerScore(entity, args(0).asInstanceOf[Entity], args(1).asInstanceOf[Int])

        def addToPlayerScore(entity: IEntitySoulCustom, entityIn: Entity, amount: Int): Unit = {

        }
    }

    class MethodIsInRangeToRender3d extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isInRangeToRender3d(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def isInRangeToRender3d(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Boolean = {

        }
    }

    class MethodIsInRangeToRenderDist extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isInRangeToRenderDist(entity, args(0).asInstanceOf[Double])

        def isInRangeToRenderDist(entity: IEntitySoulCustom, distance: Double): Boolean = {

        }
    }

    class MethodWriteMountToNBT extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = writeMountToNBT(entity, args(0).asInstanceOf[NBTTagCompound])

        def writeMountToNBT(entity: IEntitySoulCustom, tagCompund: NBTTagCompound): Boolean = {

        }
    }

    class MethodWriteToNBTOptional extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = writeToNBTOptional(entity, args(0).asInstanceOf[NBTTagCompound])

        def writeToNBTOptional(entity: IEntitySoulCustom, tagCompund: NBTTagCompound): Boolean = {

        }
    }

    class MethodWriteToNBT extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = writeToNBT(entity, args(0).asInstanceOf[NBTTagCompound])

        def writeToNBT(entity: IEntitySoulCustom, tagCompund: NBTTagCompound): Unit = {

        }
    }

    class MethodReadFromNBT extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = readFromNBT(entity, args(0).asInstanceOf[NBTTagCompound])

        def readFromNBT(entity: IEntitySoulCustom, tagCompund: NBTTagCompound): Unit = {

        }
    }

    class MethodShouldSetPosAfterLoading extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = shouldSetPosAfterLoading(entity)

        def shouldSetPosAfterLoading(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodGetEntityString extends IEntityMethod[String] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): String = getEntityString(entity)

        def getEntityString(entity: IEntitySoulCustom): String = {

        }
    }

    class MethodReadEntityFromNBT extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = readEntityFromNBT(entity, args(0).asInstanceOf[NBTTagCompound])

        def readEntityFromNBT(entity: IEntitySoulCustom, tagCompund: NBTTagCompound): Unit = {

        }
    }

    class MethodWriteEntityToNBT extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = writeEntityToNBT(entity, args(0).asInstanceOf[NBTTagCompound])

        def writeEntityToNBT(entity: IEntitySoulCustom, tagCompound: NBTTagCompound): Unit = {

        }
    }

    class MethodOnChunkLoad extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = onChunkLoad(entity)

        def onChunkLoad(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodNewDoubleNBTList extends IEntityMethod[NBTTagList] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): NBTTagList = newDoubleNBTList(entity, args(0).asInstanceOf[Double])

        def newDoubleNBTList(entity: IEntitySoulCustom, numbers: Double): NBTTagList = {

        }
    }

    class MethodNewFloatNBTList extends IEntityMethod[NBTTagList] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): NBTTagList = newFloatNBTList(entity, args(0).asInstanceOf[Float])

        def newFloatNBTList(entity: IEntitySoulCustom, numbers: Float): NBTTagList = {

        }
    }

    class MethodDropItem extends IEntityMethod[EntityItem] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): EntityItem = dropItem(entity, args(0).asInstanceOf[Int])

        def dropItem(entity: IEntitySoulCustom, size: Int): EntityItem = {

        }
    }

    class MethodDropItemWithOffset extends IEntityMethod[EntityItem] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): EntityItem = dropItemWithOffset(entity, args(0).asInstanceOf[Int], args(1).asInstanceOf[Float])

        def dropItemWithOffset(entity: IEntitySoulCustom, size: Int, p_145778_3_: Float): EntityItem = {

        }
    }

    class MethodEntityDropItem extends IEntityMethod[EntityItem] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): EntityItem = entityDropItem(entity, args(0).asInstanceOf[Float])

        def entityDropItem(entity: IEntitySoulCustom, offsetY: Float): EntityItem = {

        }
    }

    class MethodGetShadowSize extends IEntityMethod[Float] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Float = getShadowSize(entity)

        def getShadowSize(entity: IEntitySoulCustom): Float = {

        }
    }

    class MethodIsEntityAlive extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isEntityAlive(entity)

        def isEntityAlive(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodIsEntityInsideOpaqueBlock extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isEntityInsideOpaqueBlock(entity)

        def isEntityInsideOpaqueBlock(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodInteractFirst extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = interactFirst(entity)

        def interactFirst(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodGetCollisionBox extends IEntityMethod[AxisAlignedBB] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): AxisAlignedBB = getCollisionBox(entity, args(0).asInstanceOf[Entity])

        def getCollisionBox(entity: IEntitySoulCustom, entityIn: Entity): AxisAlignedBB = {

        }
    }

    class MethodUpdateRidden extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = updateRidden(entity)

        def updateRidden(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodUpdateRiderPosition extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = updateRiderPosition(entity)

        def updateRiderPosition(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodGetYOffset extends IEntityMethod[Double] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Double = getYOffset(entity)

        def getYOffset(entity: IEntitySoulCustom): Double = {

        }
    }

    class MethodGetMountedYOffset extends IEntityMethod[Double] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Double = getMountedYOffset(entity)

        def getMountedYOffset(entity: IEntitySoulCustom): Double = {

        }
    }

    class MethodMountEntity extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = mountEntity(entity, args(0).asInstanceOf[Entity])

        def mountEntity(entity: IEntitySoulCustom, entityIn: Entity): Unit = {

        }
    }

    class MethodSetPositionAndRotation2 extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setPositionAndRotation2(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double], args(3).asInstanceOf[Float], args(4).asInstanceOf[Float], args(5).asInstanceOf[Int])

        def setPositionAndRotation2(entity: IEntitySoulCustom, x: Double, y: Double, z: Double, yaw: Float, pitch: Float, rotationIncrements: Int): Unit = {

        }
    }

    class MethodGetCollisionBorderSize extends IEntityMethod[Float] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Float = getCollisionBorderSize(entity)

        def getCollisionBorderSize(entity: IEntitySoulCustom): Float = {

        }
    }

    class MethodSetInPortal extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setInPortal(entity)

        def setInPortal(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodGetPortalCooldown extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = getPortalCooldown(entity)

        def getPortalCooldown(entity: IEntitySoulCustom): Int = {

        }
    }

    class MethodSetVelocity extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setVelocity(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def setVelocity(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Unit = {

        }
    }

    class MethodHandleHealthUpdate extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = handleHealthUpdate(entity, args(0).asInstanceOf[Byte])

        def handleHealthUpdate(entity: IEntitySoulCustom, p_70103_1_: Byte): Unit = {

        }
    }

    class MethodPerformHurtAnimation extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = performHurtAnimation(entity)

        def performHurtAnimation(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodSetCurrentItemOrArmor extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setCurrentItemOrArmor(entity, args(0).asInstanceOf[Int])

        def setCurrentItemOrArmor(entity: IEntitySoulCustom, slotIn: Int): Unit = {

        }
    }

    class MethodIsBurning extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isBurning(entity)

        def isBurning(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodIsRiding extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isRiding(entity)

        def isRiding(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodIsSneaking extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isSneaking(entity)

        def isSneaking(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodSetSneaking extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setSneaking(entity, args(0).asInstanceOf[Boolean])

        def setSneaking(entity: IEntitySoulCustom, sneaking: Boolean): Unit = {

        }
    }

    class MethodIsSprinting extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isSprinting(entity)

        def isSprinting(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodSetSprinting extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setSprinting(entity, args(0).asInstanceOf[Boolean])

        def setSprinting(entity: IEntitySoulCustom, sprinting: Boolean): Unit = {

        }
    }

    class MethodIsInvisible extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isInvisible(entity)

        def isInvisible(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodIsInvisibleToPlayer extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isInvisibleToPlayer(entity)

        def isInvisibleToPlayer(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodSetInvisible extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setInvisible(entity, args(0).asInstanceOf[Boolean])

        def setInvisible(entity: IEntitySoulCustom, invisible: Boolean): Unit = {

        }
    }

    class MethodIsEating extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isEating(entity)

        def isEating(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodSetEating extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setEating(entity, args(0).asInstanceOf[Boolean])

        def setEating(entity: IEntitySoulCustom, eating: Boolean): Unit = {

        }
    }

    class MethodGetFlag extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = getFlag(entity, args(0).asInstanceOf[Int])

        def getFlag(entity: IEntitySoulCustom, flag: Int): Boolean = {

        }
    }

    class MethodSetFlag extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setFlag(entity, args(0).asInstanceOf[Int], args(1).asInstanceOf[Boolean])

        def setFlag(entity: IEntitySoulCustom, flag: Int, set: Boolean): Unit = {

        }
    }

    class MethodGetAir extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = getAir(entity)

        def getAir(entity: IEntitySoulCustom): Int = {

        }
    }

    class MethodSetAir extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setAir(entity, args(0).asInstanceOf[Int])

        def setAir(entity: IEntitySoulCustom, air: Int): Unit = {

        }
    }

    class MethodOnStruckByLightning extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = onStruckByLightning(entity)

        def onStruckByLightning(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodOnKillEntity extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = onKillEntity(entity)

        def onKillEntity(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodPushOutOfBlocks extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = pushOutOfBlocks(entity, args(0).asInstanceOf[Double], args(1).asInstanceOf[Double], args(2).asInstanceOf[Double])

        def pushOutOfBlocks(entity: IEntitySoulCustom, x: Double, y: Double, z: Double): Boolean = {

        }
    }

    class MethodSetInWeb extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setInWeb(entity)

        def setInWeb(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodGetCommandSenderName extends IEntityMethod[String] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): String = getCommandSenderName(entity)

        def getCommandSenderName(entity: IEntitySoulCustom): String = {

        }
    }

    class MethodIsEntityEqual extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isEntityEqual(entity, args(0).asInstanceOf[Entity])

        def isEntityEqual(entity: IEntitySoulCustom, entityIn: Entity): Boolean = {

        }
    }

    class MethodGetRotationYawHead extends IEntityMethod[Float] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Float = getRotationYawHead(entity)

        def getRotationYawHead(entity: IEntitySoulCustom): Float = {

        }
    }

    class MethodSetRotationYawHead extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = setRotationYawHead(entity, args(0).asInstanceOf[Float])

        def setRotationYawHead(entity: IEntitySoulCustom, rotation: Float): Unit = {

        }
    }

    class MethodCanAttackWithItem extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = canAttackWithItem(entity)

        def canAttackWithItem(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodHitByEntity extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = hitByEntity(entity, args(0).asInstanceOf[Entity])

        def hitByEntity(entity: IEntitySoulCustom, entityIn: Entity): Boolean = {

        }
    }

    class MethodToString extends IEntityMethod[String] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): String = toString(entity)

        def toString(entity: IEntitySoulCustom): String = {

        }
    }

    class MethodIsEntityInvulnerable extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isEntityInvulnerable(entity)

        def isEntityInvulnerable(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodCopyLocationAndAnglesFrom extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = copyLocationAndAnglesFrom(entity, args(0).asInstanceOf[Entity])

        def copyLocationAndAnglesFrom(entity: IEntitySoulCustom, entityIn: Entity): Unit = {

        }
    }

    class MethodCopyDataFrom extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = copyDataFrom(entity, args(0).asInstanceOf[Entity], args(1).asInstanceOf[Boolean])

        def copyDataFrom(entity: IEntitySoulCustom, entityIn: Entity, unused: Boolean): Unit = {

        }
    }

    class MethodTravelToDimension extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = travelToDimension(entity, args(0).asInstanceOf[Int])

        def travelToDimension(entity: IEntitySoulCustom, dimensionId: Int): Unit = {

        }
    }

    class MethodGetExplosionResistance extends IEntityMethod[Float] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Float = getExplosionResistance(entity, args(0).asInstanceOf[World], args(1).asInstanceOf[Int], args(2).asInstanceOf[Int], args(3).asInstanceOf[Int])

        def getExplosionResistance(entity: IEntitySoulCustom, worldIn: World, x: Int, y: Int, z: Int): Float = {

        }
    }

    class MethodFunc_145774_a extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = func_145774_a(entity, args(0).asInstanceOf[World], args(1).asInstanceOf[Int], args(2).asInstanceOf[Int], args(3).asInstanceOf[Int], args(4).asInstanceOf[Float])

        def func_145774_a(entity: IEntitySoulCustom, worldIn: World, x: Int, y: Int, z: Int, unused: Float): Boolean = {

        }
    }

    class MethodGetMaxFallHeight extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = getMaxFallHeight(entity)

        def getMaxFallHeight(entity: IEntitySoulCustom): Int = {

        }
    }

    class MethodGetTeleportDirection extends IEntityMethod[Int] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Int = getTeleportDirection(entity)

        def getTeleportDirection(entity: IEntitySoulCustom): Int = {

        }
    }

    class MethodDoesEntityNotTriggerPressurePlate extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = doesEntityNotTriggerPressurePlate(entity)

        def doesEntityNotTriggerPressurePlate(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodAddEntityCrashInfo extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = addEntityCrashInfo(entity)

        def addEntityCrashInfo(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodCanRenderOnFire extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = canRenderOnFire(entity)

        def canRenderOnFire(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodGetUniqueID extends IEntityMethod[UUID] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): UUID = getUniqueID(entity)

        def getUniqueID(entity: IEntitySoulCustom): UUID = {

        }
    }

    class MethodIsPushedByWater extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isPushedByWater(entity)

        def isPushedByWater(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodFunc_145781_i extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = func_145781_i(entity, args(0).asInstanceOf[Int])

        def func_145781_i(entity: IEntitySoulCustom, p_145781_1_: Int): Unit = {

        }
    }

    class MethodGetEntityData extends IEntityMethod[NBTTagCompound] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): NBTTagCompound = getEntityData(entity)

        def getEntityData(entity: IEntitySoulCustom): NBTTagCompound = {

        }
    }

    class MethodShouldRiderSit extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = shouldRiderSit(entity)

        def shouldRiderSit(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodGetPersistentID extends IEntityMethod[UUID] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): UUID = getPersistentID(entity)

        def getPersistentID(entity: IEntitySoulCustom): UUID = {

        }
    }

    class MethodResetEntityId extends IEntityMethod[Unit] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Unit = resetEntityId(entity)

        def resetEntityId(entity: IEntitySoulCustom): Unit = {

        }
    }

    class MethodShouldRenderInPass extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = shouldRenderInPass(entity, args(0).asInstanceOf[Int])

        def shouldRenderInPass(entity: IEntitySoulCustom, pass: Int): Boolean = {

        }
    }

    class MethodIsCreatureType extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = isCreatureType(entity, args(0).asInstanceOf[Boolean])

        def isCreatureType(entity: IEntitySoulCustom, forSpawnCount: Boolean): Boolean = {

        }
    }

    class MethodRegisterExtendedProperties extends IEntityMethod[String] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): String = registerExtendedProperties(entity, args(0).asInstanceOf[String])

        def registerExtendedProperties(entity: IEntitySoulCustom, identifier: String): String = {

        }
    }

    class MethodCanRiderInteract extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = canRiderInteract(entity)

        def canRiderInteract(entity: IEntitySoulCustom): Boolean = {

        }
    }

    class MethodShouldDismountInWater extends IEntityMethod[Boolean] {
        override def callMethod(entity: IEntitySoulCustom, args: Any*): Boolean = shouldDismountInWater(entity, args(0).asInstanceOf[Entity])

        def shouldDismountInWater(entity: IEntitySoulCustom, rider: Entity): Boolean = {

        }
    }
}
