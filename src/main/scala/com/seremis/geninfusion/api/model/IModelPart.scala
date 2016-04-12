package com.seremis.geninfusion.api.model

trait IModelPart {

    def render() = render(1F/16F)
    def render(scale: Float)

    def setRotationPoint(x: Float, y: Float, z: Float)
    def setRotateAngles(angleX: Float, angleY: Float, angleZ: Float)

    def getCuboids: Array[ICuboid]

    def resetInitialValues()

    def mutate(): IModelPart

    def getAttachmentPoints: Array[IAttachmentPoint]
    def getPartType: IModelPartType
}
