package com.seremis.geninfusion.model

import com.seremis.geninfusion.api.model.IModelPartType

import scala.util.Sorting

class ModelPartType(tags: Array[String]) extends IModelPartType {

    Sorting.quickSort(tags)

    override def getTags: Array[String] = tags

    override def similarity(partType: IModelPartType): Float = {
        val tags1 = tags
        val tags2 = partType.getTags

        var weight = 0.0F
        var deltaWeight = 1.0F / (tags1.length + tags2.length).toFloat

        var i1 = 0
        var i2 = 0

        while(i1 < tags1.length && i2 < tags2.length) {
            if(tags1(i1) == tags2(i2)) {
                weight += deltaWeight
                i1 += 1
                i2 += 1
            } else if(tags1(i1) > tags2(i2)) {
                i2 += 1
            } else {
                i1 += 1
            }
        }
        weight
    }
}
