package seremis.geninfusion.soul

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
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
                case FuncEntityGetEntityId | FuncEntitySetEntityId | FuncEntitySetEntityId | FuncEntityGetDataWatcher | FuncEntityHashCode | FuncEntitySetDead | FuncEntitySetSize |
                     FuncEntitySetRotation | FuncEntitySetPosition | FuncEntitySetAngles | FuncEntityGetMaxInPortalTime | FuncEntitySetOnFireFromLava | FuncEntitySetFire |
                     FuncEntityExtinguish | FuncEntityGetSwimSound | FuncEntityGetBoundingBox | FuncEntityIsInWater | FuncEntityGetSplashSound | FuncEntityGetEyeHeight |
                     FuncEntitySetWorld | FuncEntitySetBeenAttacked | FuncEntitySetPositionAndRotation | FuncEntitySetPositionAndRotation2 | FuncEntitySetLocationAndAngles |
                     FuncEntityGetLookVec | FuncEntityGetPortalCooldown | FuncEntitySetVelocity | FuncEntityGetInventory | FuncEntitySetCurrentItemOrArmor | FuncEntityIsBurning |
                     FuncEntityIsRiding | FuncEntityIsSneaking | FuncEntitySetSneaking | FuncEntityIsSprinting | FuncEntitySetSprinting | FuncEntityIsInvisible | FuncEntityIsInvisibleToPlayer |
                     FuncEntitySetInvisible | FuncEntitySetEating | FuncEntityIsEating | FuncEntitySetFlag | FuncEntityGetFlag | FuncEntityGetAir | FuncEntitySetAir | FuncEntitySetInWeb |
                     FuncEntityGetCommandSenderName | FuncEntityGetRotationYawHead | FuncEntityIsEntityInvulnerable | FuncEntityGetTeleportDirection | FuncEntityCanRenderOnFire |
                     FuncEntityGetUniqueID | FuncEntityGetFormattedCommandSenderName | FuncEntityGetPersistentID
                    => result = unitWithoutSuper(entity, cast(methods), cast(superMethod), args)
                case FuncEntityEquals | FuncEntityCanTriggerWalking | FuncEntityCanBeCollidedWith | FuncEntityCanBePushed | FuncEntityIsEntityAlive | FuncEntityPushOutOfBlocks |
                     FuncEntityCanAttackWithItem | FuncEntityFunc_145774_a | FuncEntityShouldDismountInWater
                    => result = booleanBaseTrue(entity, cast(methods), cast(superMethod), args)
                case FuncEntityIsOffsetPositionInLiquid | FuncEntityIsWet | FuncEntityHandleWaterMovement | FuncEntityHandleLavaMovement | FuncEntityIsInsideOfMaterial |
                     FuncEntityAttackEntityFrom | FuncEntityIsInRangeToRender3d | FuncEntityIsInRangeToRenderDist | FuncEntityWriteMountToNBT | FuncEntityWriteToNBTOptional |
                     FuncEntityShouldSetPosAfterLoading | FuncEntityIsEntityInsideOpaqueBlock | FuncEntityIsEntityEqual | FuncEntityHitByEntity | FuncEntityDoesEntityNotTriggerPressurePlate |
                     FuncEntityShouldRiderSit | FuncEntityShouldRenderInPass | FuncEntityIsCreatureType | FuncEntityCanRiderInteract
                    => result = booleanBaseFalse(entity, cast(methods), cast(superMethod), args)
                case FuncEntityGetBrightness | FuncEntityGetBrightnessForRender | FuncEntityGetMaxFallHeight
                    => result = integerAveraged(entity, cast(methods), cast(superMethod), args)
                case FuncEntityGetDistance | FuncEntityGetShadowSize | FuncEntityGetMountedYOffset | FuncEntityGetCollisionBorderSize | FuncEntityGetExplosionResistance
                    => result = floatAveraged(entity, cast(methods), cast(superMethod), args)
                case FuncEntityGetDistanceSq | FuncEntityGetDistanceSqToEntity | FuncEntityGetDistanceToEntity
                    => result = doubleAveraged(entity, cast(methods), cast(superMethod), args)
                case FuncEntityGetParts
                    => result = arraysAdded(entity, cast(methods), cast(superMethod), args)
                case FuncEntityToString | FuncEntityAddEntityCrashInfo | FuncEntityRegisterExtendedProperties
                    => result = stringsConcatenated(entity, cast(methods), cast(superMethod), args)
                case FuncEntityGetEntityData
                    => result = NBTCombined(entity, cast(methods), cast(superMethod), args)
                case _
                    => result = unitWithSuper(entity, cast(methods), cast(superMethod), args)
            }

//            if(!result.isInstanceOf[T]) {
//                throw new ClassCastException("Method Handler for " + srgName + " has the wrong return type or is missing!")
//            }

            result.asInstanceOf[T]
        } else {
            superMethod()
        }
    }

    def cast[T](lst: Option[ListBuffer[IEntityMethod[_]]]): ListBuffer[IEntityMethod[T]] = lst.get.asInstanceOf[ListBuffer[IEntityMethod[T]]]
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

    def arraysAdded(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[Array[_]]], superMethod: () => Array[_], args: Any*): Array[_] = {
        var callSuper = true
        val preventSuperCall = () => callSuper = false

        val listBuffer: ListBuffer[Any] = ListBuffer()

        for(method <- methods) {
            val option = method.callMethod(entity, preventSuperCall, args)

            if(option.nonEmpty) {
                for(element <- option.get) {
                    listBuffer += element
                }
            }
        }

        if(callSuper) {
            val superResult = superMethod()

            if(superResult != null) {
                for(element <- superResult) {
                    listBuffer += element
                }
            }
        }

        listBuffer.to[Array]
    }

    def stringsConcatenated(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[String]], superMethod: () => String, args: Any*): String = {
        var result = ""

        var callSuper = true
        val preventSuperCall = () => callSuper = false

        for(method <- methods) {
            val option = method.callMethod(entity, preventSuperCall, args)

            if(option.nonEmpty) {
                result += option.get
            }
        }

        if(callSuper) {
            result = superMethod() + result
        }

        result
    }

    def NBTCombined(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[NBTTagCompound]], superMethod: () => NBTTagCompound, args: Any*): NBTTagCompound = {
        var callSuper = true
        val preventSuperCall = () => callSuper = false

        for(method <- methods) {
            method.callMethod(entity, preventSuperCall, args)
        }

        if(callSuper) {
            //TODO does this work?
            superMethod()
        }

        args(0).asInstanceOf[NBTTagCompound]
    }

    def stackFirst(entity: IEntitySoulCustom, methods: ListBuffer[IEntityMethod[ItemStack]], superMethod: () => ItemStack, args: Any*): ItemStack = {
        val preventSuperCall = () => ()

        for(method <- methods) {
            val option = method.callMethod(entity, preventSuperCall, args)

            if(option.nonEmpty) {
                return option.get
            }
        }

        superMethod()
    }
}
