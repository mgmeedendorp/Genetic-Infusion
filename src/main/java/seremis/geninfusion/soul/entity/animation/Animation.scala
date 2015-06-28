package seremis.geninfusion.soul.entity.animation

import seremis.geninfusion.api.soul.{IAnimation, IEntitySoulCustom}
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.ModelPart

abstract class Animation extends IAnimation {

    final val PI = Math.PI.asInstanceOf[Float]

    def getModel(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModel(entity)

    def getModelLeftLegs(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelLeftLegs(entity)
    def getModelRightLegs(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelRightLegs(entity)
    
    def getModelLegs(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelLegs(entity)

    def getModelLeftArms(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelLeftArms(entity)
    def getModelRightArms(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelRightArms(entity)

    def getModelArms(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelArms(entity)

    def armsHorizontal(entity: IEntitySoulCustom): Boolean = AnimationCache.armsHorizontal(entity)

    def getModelBody(entity: IEntitySoulCustom): ModelPart = AnimationCache.getModelBody(entity)

    def getModelHead(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelHead(entity)

    def getModelWings(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelWings(entity)
}


