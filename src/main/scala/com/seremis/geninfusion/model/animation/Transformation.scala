package com.seremis.geninfusion.model.animation

import com.seremis.geninfusion.api.model.IModelPart
import com.seremis.geninfusion.api.model.animation.ITransformation
import org.apache.commons.math3.analysis.UnivariateFunction
import org.apache.commons.math3.analysis.function.Identity

class Transformation(length: Int, pointX: Float, pointY: Float, pointZ: Float, rotX: Float, rotY: Float, rotZ: Float, scaleX: Float, scaleY: Float, scaleZ: Float, progression: UnivariateFunction = new Identity()) extends ITransformation {

    override def getProgression: UnivariateFunction = progression

    override def transformPart(part: IModelPart, time: Int): IModelPart = {
        val multiplier = getProgression.value(time / getLength).toFloat

        val dPointX = pointX * multiplier
        val dPointY = pointY * multiplier
        val dPointZ = pointZ * multiplier

        val dRotX = rotX * multiplier
        val dRotY = rotY * multiplier
        val dRotZ = rotZ * multiplier

        val dScaleX = scaleX * multiplier
        val dScaleY = scaleY * multiplier
        val dScaleZ = scaleZ * multiplier

        val sX = part.getScaleX + dScaleX
        val sY = part.getScaleY + dScaleY
        val sZ = part.getScaleZ + dScaleZ

        part.moveRotationPoints(dPointX, dPointY, dPointZ)
        part.addRotateAngles(dRotX, dRotY, dRotZ)
        part.setScales(sX, sY, sZ)

        part
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
