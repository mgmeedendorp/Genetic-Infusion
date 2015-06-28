package seremis.geninfusion.api.util.render.model

import seremis.geninfusion.api.util.render.animation.AnimationCache

import scala.collection.mutable.ListBuffer

class Model(modelParts: Array[ModelPart]) {

    lazy val leftArms = AnimationCache.getModelLeftArms(modelParts)
    lazy val rightArms = AnimationCache.getModelRightArms(modelParts)
    lazy val arms = leftArms ++ rightArms

    lazy val leftLegs = AnimationCache.getModelLeftLegs(modelParts)
    lazy val rightLegs = AnimationCache.getModelRightLegs(modelParts)
    lazy val legs = leftLegs ++ rightLegs

    lazy val leftWings = AnimationCache.getModelLeftWings(modelParts)
    lazy val rightWings = AnimationCache.getModelRightWings(modelParts)
    lazy val wings = leftWings ++ rightWings

    lazy val head = AnimationCache.getModelHead(modelParts)

    lazy val body = AnimationCache.getModelBody(modelParts)

    private var unrecognizedList: ListBuffer[ModelPart] = ListBuffer()

//    for(part <- modelParts if !arms.contains(part) && !legs.contains(part) && !wings.contains(part) && !head.contains(part) && body != part) {
//        unrecognizedList += part
//    }

    lazy val unrecognized: Array[ModelPart] = unrecognizedList.to[Array]

    def render(scale: Float) {
        for(part <- modelParts)
            part.render(scale)
    }

    def render() {
        render(0.0625F)
    }
}
