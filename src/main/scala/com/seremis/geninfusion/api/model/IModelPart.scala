package com.seremis.geninfusion.api.model

import com.seremis.geninfusion.api.util.INBTTagable

trait IModelPart extends INBTTagable {

    def render()

    def setRotationPoints(x: Float, y: Float, z: Float)
    def moveRotationPoints(dX: Float, dY: Float, dZ: Float)

    def setRotateAngles(angleX: Float, angleY: Float, angleZ: Float)
    def addRotateAngles(dX: Float, dY: Float, dZ: Float)

    def setScales(scaleX: Float, scaleY: Float, scaleZ: Float)

    def getRotationPointX: Float
    def getRotationPointY: Float
    def getRotationPointZ: Float

    def getRotateAngleX: Float
    def getRotateAngleY: Float
    def getRotateAngleZ: Float

    def getScaleX: Float
    def getScaleY: Float
    def getScaleZ: Float

    def getCuboids: Array[ICuboid]

    def resetInitialValues()

    def mutate(): IModelPart

    def getAttachmentPoints: Array[IAttachmentPoint]
    def getPartType: IModelPartType

    def getChildParts: Array[IModelPart]
}
