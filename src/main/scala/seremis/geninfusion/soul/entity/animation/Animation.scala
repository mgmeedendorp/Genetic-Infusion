package seremis.geninfusion.soul.entity.animation

import seremis.geninfusion.api.soul.lib.ModelPartTypes
import seremis.geninfusion.api.soul.{IAnimation, IEntitySoulCustom}
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}

abstract class Animation extends IAnimation {

    final val PI = Math.PI.asInstanceOf[Float]

    def getModel(entity: IEntitySoulCustom): Model = AnimationCache.getModel(entity)

    def getModelLeftLegs(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.LegsLeft)
    def getModelRightLegs(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.LegsRight)

    def getModelLegs(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.LegsLeft, ModelPartTypes.LegsRight)

    def getModelLeftArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.ArmsLeft)
    def getModelRightArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.ArmsRight)

    def getModelArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.ArmsLeft, ModelPartTypes.ArmsRight)

    def armsHorizontal(entity: IEntitySoulCustom): Boolean = AnimationCache.armsHorizontal(entity)

    def getModelBody(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.Body)

    def getModelHead(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.Head)

    def getModelLeftWings(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.WingsLeft)
    def getModelRightWings(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.WingsRight)

    def getModelWings(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.WingsLeft, ModelPartTypes.WingsRight)
}


