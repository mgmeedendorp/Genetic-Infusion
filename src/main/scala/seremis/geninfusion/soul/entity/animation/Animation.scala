package seremis.geninfusion.soul.entity.animation

import seremis.geninfusion.api.lib.ModelPartTypes
import seremis.geninfusion.api.soul.{IAnimation, IEntitySoulCustom}
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}

abstract class Animation extends IAnimation {

    final val PI = Math.PI.asInstanceOf[Float]

    def getModel(entity: IEntitySoulCustom): Model = AnimationCache.getModel(entity)

    def getModelLeftLegs(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getPartsWithTag(ModelPartTypes.Names.Leg, ModelPartTypes.Tags.Left)
    def getModelRightLegs(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getPartsWithTag(ModelPartTypes.Names.Leg, ModelPartTypes.Tags.Right)

    def getModelLegs(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.Names.Leg)

    def getModelLeftArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getPartsWithTag(ModelPartTypes.Names.Arm, ModelPartTypes.Tags.Left)
    def getModelRightArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getPartsWithTag(ModelPartTypes.Names.Arm, ModelPartTypes.Tags.Right)

    def getModelArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.Names.Arm)

    def armsHorizontal(entity: IEntitySoulCustom): Boolean = AnimationCache.armsHorizontal(entity)

    def getModelBody(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.Names.Body)

    def getModelHead(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.Names.Head)

    def getModelLeftWings(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getPartsWithTag(ModelPartTypes.Names.Wing, ModelPartTypes.Tags.Left)
    def getModelRightWings(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getPartsWithTag(ModelPartTypes.Names.Wing, ModelPartTypes.Tags.Right)

    def getModelWings(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModel(entity).getParts(ModelPartTypes.Names.Wing)
}


