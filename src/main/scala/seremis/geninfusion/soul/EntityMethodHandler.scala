package seremis.geninfusion.soul

import seremis.geninfusion.api.lib.reflection.FunctionLib._
import seremis.geninfusion.api.soul.{IEntityMethod, IEntitySoulCustom, SoulHelper}

import scala.collection.mutable.ListBuffer

object EntityMethodHandler {

    val registry = SoulHelper.entityMethodRegistry

    def handleMethodCall[T](entity: IEntitySoulCustom, srgName: String, superMethod: () => T, args: Any*): T = {
        val methods = registry.getMethodsForSrgName(srgName)

        if(methods.nonEmpty) {
            var result: Any = Unit

            srgName match {
                case EntityGetEntityId | EntitySetEntityId | EntitySetEntityId | EntityGetDataWatcher | EntityHashCode | EntitySetDead | EntitySetSize |
                     EntitySetRotation | EntitySetPosition | EntitySetAngles | EntityGetMaxInPortalTime | EntitySetOnFireFromLava | EntitySetFire |
                     EntityExtinguish | EntityGetSwimSound | EntityGetBoundingBox | EntityIsInWater | EntityGetSplashSound | EntityGetEyeHeight |
                     EntitySetWorld | EntitySetBeenAttacked | EntitySetPositionAndRotation | EntitySetPositionAndRotation2 | EntitySetLocationAndAngles |
                     EntityGetLookVec | EntityGetPortalCooldown | EntitySetVelocity | EntityGetInventory | EntitySetCurrentItemOrArmor | EntityIsBurning |
                     EntityIsRiding | EntityIsSneaking | EntitySetSneaking | EntityIsSprinting | EntitySetSprinting | EntityIsInvisible | EntityIsInvisibleToPlayer |
                     EntitySetInvisible | EntitySetEating | EntityIsEating | EntitySetFlag | EntityGetFlag | EntityGetAir | EntitySetAir | EntitySetInWeb |
                     EntityGetCommandSenderName
                    => result = unitWithoutSuper(entity, cast(methods), cast(superMethod), args)
                case EntityEquals | EntityCanTriggerWalking | EntityCanBeCollidedWith | EntityCanBePushed | EntityIsEntityAlive | EntityPushOutOfBlocks

                    => result = booleanBaseTrue(entity, cast(methods), cast(superMethod), args)
                case EntityIsOffsetPositionInLiquid | EntityIsWet | EntityHandleWaterMovement | EntityHandleLavaMovement | EntityIsInsideOfMaterial |
                     EntityAttackEntityFrom | EntityIsInRangeToRender3d | EntityIsInRangeToRenderDist | EntityWriteMountToNBT | EntityWriteToNBTOptional |
                     EntityShouldSetPosAfterLoading | EntityIsEntityInsideOpaqueBlock
                    => result = booleanBaseFalse(entity, cast(methods), cast(superMethod), args)
                case EntityGetBrightness | EntityGetBrightnessForRender
                    => result = integerAveraged(entity, cast(methods), cast(superMethod), args)
                case EntityGetDistance | EntityGetShadowSize | EntityGetMountedYOffset | EntityGetCollisionBorderSize
                    => result = floatAveraged(entity, cast(methods), cast(superMethod), args)
                case EntityGetDistanceSq | EntityGetDistanceSqToEntity | EntityGetDistanceToEntity
                    => result = doubleAveraged(entity, cast(methods), cast(superMethod), args)
                case _
                    => result = unitWithSuper(entity, cast(methods), cast(superMethod), args)
            }

            if(!result.isInstanceOf[T]) {
                throw new ClassCastException("Method Handler for " + srgName + " has the wrong return type or is missing!")
            }

            result.asInstanceOf[T]
        } else {
            superMethod()
        }
    }

    def cast[T](lst: Option[ListBuffer[IEntityMethod[_]]]): ListBuffer[IEntityMethod[T]] = lst.get.asInstanceOf[ListBuffer[IEntityMethod[T]]
    def cast[T](fnc: () => _): () => T = fnc.asInstanceOf[() => T]

    def unitWithoutSuper(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[Unit]], superMethod: () => _, args: Any*): Unit = {
        for(method <- methods) {
            method.callMethod(entity, () => Unit, args)
        }
    }

    def unitWithSuper(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[Unit]], superMethod: () => _, args: Any*): Unit = {
        var callSuper = true
        val preventSuperCall = () => callSuper = false

        for(method <- methods) {
            method.callMethod(entity, preventSuperCall, args)
        }

        if(callSuper) {
            superMethod()
        }
    }

    def booleanBaseFalse(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[Boolean]], superMethod: () => Boolean, args: Any*): Boolean = {
        var callSuper = true
        val preventSuperCall = () => callSuper = false

        var flag = false
        for(method <- methods) {
            val option = method.callMethod(entity, preventSuperCall, args)

            if(option.nonEmpty && option.get) {
                flag = true
            }
        }

        if(callSuper) {
            if(superMethod()) {
                flag = true
            }
        }

        flag
    }

    def booleanBaseTrue(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[Boolean]], superMethod: () => Boolean, args: Any*): Boolean = {
        var callSuper = true
        val preventSuperCall = () => callSuper = false

        var flag = true
        for(method <- methods) {
            val option = method.callMethod(entity, preventSuperCall, args)

            if(option.nonEmpty && !option.get) {
                flag = false
            }
        }

        if(callSuper) {
            if(!superMethod()) {
                flag = false
            }
        }

        flag
    }

    def integerAveraged(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[Int]], superMethod: () => Int, args: Any*): Int = {
        var callSuper = true
        val preventSuperCall = () => callSuper = false

        var summed = 0
        var count = 0
        for(method <- methods) {
            val option = method.callMethod(entity, preventSuperCall, args)

            if(option.nonEmpty) {
                summed += option.get
                count += 1
            }
        }

        if(callSuper) {
            summed += superMethod()
            count += 1
        }

        (summed.toFloat / count.toFloat).toInt
    }

    def floatAveraged(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[Float]], superMethod: () => Float, args: Any*): Float = {
        var callSuper = true
        val preventSuperCall = () => callSuper = false

        var summed = 0.0F
        var count = 0
        for(method <- methods) {
            val option = method.callMethod(entity, preventSuperCall, args)

            if(option.nonEmpty) {
                summed += option.get
                count += 1
            }
        }

        if(callSuper) {
            summed += superMethod()
            count += 1
        }

        summed / count.toFloat
    }

    def doubleAveraged(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[Double]], superMethod: () => Double, args: Any*): Double = {
        var callSuper = true
        val preventSuperCall = () => callSuper = false

        var summed = 0.0D
        var count = 0
        for(method <- methods) {
            val option = method.callMethod(entity, preventSuperCall, args)

            if(option.nonEmpty) {
                summed += option.get
                count += 1
            }
        }

        if(callSuper) {
            summed += superMethod()
            count += 1
        }

        summed / count.toDouble
    }
}
