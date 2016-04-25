package com.seremis.geninfusion.api.model.animation

import com.seremis.geninfusion.api.model.IModelPart
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm

trait ITransformation {

    def getProgression: PolynomialFunctionLagrangeForm

    def transformPart(part: IModelPart, time: Int): IModelPart

    def getLength: Int

    def getPointDX: Float
    def getPointDY: Float
    def getPointDZ: Float

    def getRotDX: Float
    def getRotDY: Float
    def getRotDZ: Float

    def getScaleX: Float
    def getScaleY: Float
    def getScaleZ: Float
}
