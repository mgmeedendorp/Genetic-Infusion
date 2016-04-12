package com.seremis.geninfusion.model

import com.seremis.geninfusion.api.model.{IAttachmentPoint, ICuboid, IModelPart, IModelPartType}

class ModelPart(cuboids: Array[ICuboid], partType: IModelPartType, attachmentPoints: Array[IAttachmentPoint]) extends IModelPart {

    var rotationPointX = 0.0F
    var rotationPointY = 0.0F
    var rotationPointZ = 0.0F

    var rotateAngleX = 0.0F
    var rotateAngleY = 0.0F
    var rotateAngleZ = 0.0F

    var initialRotationPointX = 0.0F
    var initialRotationPointY = 0.0F
    var initialRotationPointZ = 0.0F

    var initialRotateAngleX = 0.0F
    var initialRotateAngleY = 0.0F
    var initialRotateAngleZ = 0.0F

    var firstRender = true

    def render(scale: Float) {
        if(firstRender) {
            initialRotationPointX = rotationPointX
            initialRotationPointY = rotationPointY
            initialRotationPointZ = rotationPointZ

            initialRotateAngleX = rotateAngleX
            initialRotateAngleY = rotateAngleY
            initialRotateAngleZ = rotateAngleZ
        }

    }

    def setRotationPoint(x: Float, y: Float, z: Float) = {
        //handle special rotation and movement
    }

    def setRotateAngles(angleX: Float, angleY: Float, angleZ: Float) = {
        //handle special rotation and movement + animations
    }

    def getCuboids: Array[ICuboid] = cuboids

    def resetInitialValues() {
        rotationPointX = initialRotationPointX
        rotationPointY = initialRotationPointY
        rotationPointZ = initialRotationPointZ

        rotateAngleX = initialRotateAngleX
        rotateAngleY = initialRotateAngleY
        rotateAngleZ = initialRotateAngleZ
    }

    def mutate(): IModelPart = ???

    def getAttachmentPoints: Array[IAttachmentPoint] = attachmentPoints

    def getPartType: IModelPartType = partType
}
