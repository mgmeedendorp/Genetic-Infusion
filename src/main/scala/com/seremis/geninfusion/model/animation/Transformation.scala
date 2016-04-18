package com.seremis.geninfusion.model.animation

import com.seremis.geninfusion.api.model.animation.ITransformation
import com.seremis.geninfusion.api.model.{ICuboid, IModelPart}

import scala.collection.mutable.ArrayBuffer

class Transformation(length: Int, pointX: Float, pointY: Float, pointZ: Float, rotX: Float, rotY: Float, rotZ: Float, scaleX: Float, scaleY: Float, scaleZ: Float) extends ITransformation {

    override def transformPart(part: IModelPart, time: Int): IModelPart = {
        val multiplier = time / getLength

        val dPointX = pointX * multiplier
        val dPointY = pointY * multiplier
        val dPointZ = pointZ * multiplier

        val dRotX = rotX * multiplier
        val dRotY = rotY * multiplier
        val dRotZ = rotZ * multiplier

        val dScaleX = scaleX * multiplier
        val dScaleY = scaleY * multiplier
        val dScaleZ = scaleZ * multiplier

        val x = part.getRotationPointX + dPointX
        val y = part.getRotationPointY + dPointY
        val z = part.getRotationPointZ + dPointZ

        val rX = part.getRotationPointX + dRotX
        val rY = part.getRotationPointY + dRotY
        val rZ = part.getRotationPointZ + dRotZ

        val cuboids: ArrayBuffer[ICuboid] = ArrayBuffer()

        //TODO figure this out.
    }

    override def getLength: Int = length

    override def getPointDX = pointX
    override def getPointDY = pointY
    override def getPointDZ = pointZ

    override def getRotDX = rotX
    override def getRotDY = rotY
    override def getRotDZ = rotZ

    override def getScaleX = scaleX
    override def getScaleY = scaleY
    override def getScaleZ = scaleZ
}
