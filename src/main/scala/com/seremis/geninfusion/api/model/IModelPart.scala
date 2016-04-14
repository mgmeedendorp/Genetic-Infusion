package com.seremis.geninfusion.api.model

import com.seremis.geninfusion.api.util.INBTTagable

trait IModelPart extends INBTTagable {

    def render()

    def setRotationPoints(x: Float, y: Float, z: Float)
    def moveRotationPoints(dX: Float, dY: Float, dZ: Float)

    def setRotateAngles(angleX: Float, angleY: Float, angleZ: Float)
    def addRotateAngles(dX: Float, dY: Float, dZ: Float)

    def getRotationPointX: Float
    def getRotationPointY: Float
    def getRotationPointZ: Float

    def getRotateAngleX: Float
    def getRotateAngleY: Float
    def getRotateAngleZ: Float

    def getCuboids: Array[ICuboid]

    def resetInitialValues()

    def mutate(): IModelPart

    def getAttachmentPoints: Array[IAttachmentPoint]
    def getPartType: IModelPartType
}
