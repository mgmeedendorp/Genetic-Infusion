package com.seremis.geninfusion.api.model.animation

import com.seremis.geninfusion.api.model.IModelPartType
import com.seremis.geninfusion.api.soulentity.ISoulEntity

trait IAnimation {

    def isApplicable(entity: ISoulEntity): Boolean

    def doesLoop(entity: ISoulEntity): Boolean

    def getAffectedModelPartTypes(entity: ISoulEntity): Array[IModelPartType]

    def canBeInterrupted(entity: ISoulEntity): Boolean
    def interrupt(entity: ISoulEntity, partTypes: Array[IModelPartType])

    def animate(entity: ISoulEntity, renderTickSinceStart: Int): Boolean
}
