package com.seremis.geninfusion.api.model.animation

import com.seremis.geninfusion.api.model.IModelPartType
import com.seremis.geninfusion.api.soulentity.ISoulEntity
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm

trait IAnimation {

    def getProgression: PolynomialFunctionLagrangeForm

    def getAnimationStates: Array[ITransformation]

    def doesInterfereWith(animation: IAnimation): Boolean

    def getAffectedModelPartTypes: Array[IModelPartType]

    def doesLoop: Boolean
    def canBeInterrupted: Boolean

    def animate(entity: ISoulEntity)
    def interrupt(entity: ISoulEntity, partType: IModelPartType): Boolean

    def isApplicable(entity: ISoulEntity): Boolean


}
