package com.seremis.geninfusion.api.model

import com.seremis.geninfusion.api.util.INBTTagable

trait IModelPartType extends INBTTagable {

    def getTags: Array[String]
    def similarity(partType: IModelPartType): Float
}
