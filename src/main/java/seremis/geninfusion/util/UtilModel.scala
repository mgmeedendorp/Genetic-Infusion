package seremis.geninfusion.util

import net.minecraft.client.model.ModelBox
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}

import scala.collection.mutable.ListBuffer

object UtilModel {

    def morphModel(modelFrom: Model, modelTo: Model, maxIndex: Int, index: Int): Model = {
        val result: ListBuffer[ModelPart] = ListBuffer()

        morphModelPartArray(Some(modelFrom.leftArms), Some(modelTo.leftArms), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.rightArms), Some(modelTo.rightArms), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.leftLegs), Some(modelTo.leftLegs), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.rightLegs), Some(modelTo.rightLegs), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.leftWings), Some(modelTo.leftWings), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.rightWings), Some(modelTo.rightWings), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.head), Some(modelTo.head), maxIndex, index).foreach(part => result += part)
        result += morphModelParts(Some(modelFrom.body), Some(modelTo.body), maxIndex, index)

        morphModelPartArray(Some(modelFrom.unrecognized), None, maxIndex, index).foreach(part => result += part)
        morphModelPartArray(None, Some(modelTo.unrecognized), maxIndex, index).foreach(part => result += part)

        println(index)
        result.foreach(r => println(r))

        new Model(result.to[Array])
    }

    def morphModelPartArray(partsFrom: Option[Array[ModelPart]], partsTo: Option[Array[ModelPart]], maxIndex: Int, index: Int): Array[ModelPart] = {
        val parts: ListBuffer[ModelPart] = ListBuffer()

        if(partsFrom.nonEmpty && partsTo.nonEmpty) {
            for((from, to) <- partsFrom.get zip partsTo.get) {
                parts += morphModelParts(Some(from), Some(to), maxIndex, index)
            }
        }

        var remaining: Option[Array[ModelPart]] = None

        if(partsFrom.nonEmpty && partsTo.nonEmpty) {
            remaining = if(partsFrom.get.length > partsTo.get.length) Some(partsFrom.get.drop(partsTo.get.length)) else if(partsFrom.get.length < partsTo.get.length) Some(partsTo.get.drop(partsFrom.get.length)) else None
        } else if(partsFrom.nonEmpty) {
            remaining = partsFrom
        } else {
            remaining = partsTo
        }

        remaining.foreach(remParts => remParts.foreach(part => {
            parts += morphModelParts(None, Some(part), maxIndex, index)
        }))

        parts.to[Array]
    }

    def morphModelParts(partFrom: Option[ModelPart], partTo: Option[ModelPart], maxIndex: Int, index: Int): ModelPart = {
        val part = new ModelPart(if(partTo.nonEmpty) partTo.get.boxName else partFrom.get.boxName)

        if(partFrom.nonEmpty && partTo.nonEmpty) {
            for((from, to) <- partFrom.get.getBoxList zip partTo.get.getBoxList) {
                val boxFrom = AnimationCache.getPartBoxCoordinates(partFrom.get, from)
                val boxTo = AnimationCache.getPartBoxCoordinates(partTo.get, to)

                val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                part.addBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toInt, (y2 - y1).toInt, (z2 - z1).toInt)
            }
        }

        var remaining: Option[Array[ModelBox]] = None

        if(partFrom.nonEmpty && partTo.nonEmpty) {
            remaining = if(partFrom.get.getBoxList.length > partTo.get.getBoxList.length) Some(partFrom.get.getBoxList.drop(partTo.get.getBoxList.length)) else if(partFrom.get.getBoxList.length < partTo.get.getBoxList.length) Some(partTo.get.getBoxList.drop(partFrom.get.getBoxList.length)) else None
        } else if(partFrom.nonEmpty) {
            remaining = Some(partFrom.get.getBoxList)
        } else {
            remaining = Some(partTo.get.getBoxList)
        }

        remaining.foreach(boxes => boxes.foreach(box => {
            val outerBox = AnimationCache.getPartBoxCoordinates(if(partFrom.nonEmpty) partFrom.get else partTo.get, box)

            val x1 = (outerBox._1.xCoord / maxIndex) * index
            val x2 = (outerBox._2.xCoord / maxIndex) * index
            val y1 = (outerBox._1.yCoord / maxIndex) * index
            val y2 = (outerBox._2.yCoord / maxIndex) * index
            val z1 = (outerBox._1.zCoord / maxIndex) * index
            val z2 = (outerBox._2.zCoord / maxIndex) * index

            part.addBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toInt, (y2 - y1).toInt, (z2 - z1).toInt)
        }))
        part.resetDisplayList()

        part
    }
}
