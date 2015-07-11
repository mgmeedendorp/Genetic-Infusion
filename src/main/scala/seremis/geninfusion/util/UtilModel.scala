package seremis.geninfusion.util

import net.minecraft.util.Vec3
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}

import scala.collection.mutable.ListBuffer

object UtilModel {

    def morphModel(modelFrom: Model, modelTo: Model, maxIndex: Int, index: Int): Model = {
        val result: ListBuffer[ModelPart] = ListBuffer()

        morphModelPartArray(modelFrom.leftArms, modelTo.leftArms, maxIndex, index).foreach(part => result += part)
        morphModelPartArray(modelFrom.rightArms, modelTo.rightArms, maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.leftLegs), Some(modelTo.leftLegs), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.rightLegs), Some(modelTo.rightLegs), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.leftWings), Some(modelTo.leftWings), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.rightWings), Some(modelTo.rightWings), maxIndex, index).foreach(part => result += part)
        morphModelPartArray(Some(modelFrom.head), Some(modelTo.head), maxIndex, index).foreach(part => result += part)
        result += morphModelPart(Some(modelFrom.body), Some(modelTo.body), maxIndex, index)

        morphModelPartArray(Some(modelFrom.unrecognized), None, maxIndex, index).foreach(part => result += part)
        morphModelPartArray(None, Some(modelTo.unrecognized), maxIndex, index).foreach(part => result += part)

        new Model(result.to[Array])
    }

    def morphModelPartArray(partsFrom: Option[Array[ModelPart]], partsTo: Option[Array[ModelPart]], maxIndex: Int, index: Int): Array[ModelPart] = {
        val parts: ListBuffer[ModelPart] = ListBuffer()

        if(partsFrom.nonEmpty && partsTo.nonEmpty) {
            for((from, to) <- partsFrom.get zip partsTo.get) {
                parts += morphModelPart(Some(from), Some(to), maxIndex, index)
            }

            if(partsFrom.get.length > partsTo.get.length) {
                val remaining = partsFrom.get.drop(partsTo.get.length - 1)

                remaining.foreach(p => parts += morphModelPart(Some(p), None, maxIndex, index))
            } else if(partsFrom.get.length < partsTo.get.length) {
                val remaining = partsTo.get.drop(partsTo.get.length - 1)

                remaining.foreach(p => parts += morphModelPart(None, Some(p), maxIndex, index))
            }
        } else if(partsFrom.nonEmpty && partsTo.isEmpty) {
            partsFrom.get.foreach(p => parts += morphModelPart(Some(p), None, maxIndex, index))
        } else if(partsFrom.isEmpty && partsTo.nonEmpty) {
            partsTo.get.foreach(p => parts += morphModelPart(None, Some(p), maxIndex, index))
        }

        parts.to[Array]
    }

    def morphModelPart(partFrom: Option[ModelPart], partTo: Option[ModelPart], maxIndex: Int, index: Int): ModelPart = {
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

            if(partFrom.get.getBoxList.length > partTo.get.getBoxList.length) {
                for(from <- partFrom.get.getBoxList.drop(partTo.get.getBoxList.length - 1)) {
                    val boxFrom = AnimationCache.getPartBoxCoordinates(partFrom.get, from)
                    val boxTo = (Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(0, 0, 0))

                    val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                    val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                    val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                    val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                    val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                    val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                    part.addBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toInt, (y2 - y1).toInt, (z2 - z1).toInt)
                }
            } else if(partTo.get.getBoxList.length > partFrom.get.getBoxList.length) {
                for(to <- partTo.get.getBoxList.drop(partFrom.get.getBoxList.length - 1)) {
                    val boxFrom = (Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(0, 0, 0))
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

            part.rotateAngleX = partFrom.get.rotateAngleX + ((partTo.get.rotateAngleX - partFrom.get.rotateAngleX) / maxIndex) * index
            part.rotateAngleY = partFrom.get.rotateAngleY + ((partTo.get.rotateAngleY - partFrom.get.rotateAngleY) / maxIndex) * index
            part.rotateAngleZ = partFrom.get.rotateAngleZ + ((partTo.get.rotateAngleZ - partFrom.get.rotateAngleZ) / maxIndex) * index
        }

        if(partFrom.nonEmpty) {
            if(partTo.isEmpty) {
                for(from <- partFrom.get.getBoxList) {
                    val boxFrom = AnimationCache.getPartBoxCoordinates(partFrom.get, from)
                    val boxTo = (Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(0, 0, 0))

                    val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                    val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                    val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                    val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                    val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                    val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                    part.addBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toInt, (y2 - y1).toInt, (z2 - z1).toInt)
                }
            }
            part.rotateAngleX = partFrom.get.rotateAngleX + (partFrom.get.rotateAngleX / maxIndex) * index
            part.rotateAngleY = partFrom.get.rotateAngleY + (partFrom.get.rotateAngleY / maxIndex) * index
            part.rotateAngleZ = partFrom.get.rotateAngleZ + (partFrom.get.rotateAngleZ / maxIndex) * index
        } else {
            if(partTo.nonEmpty) {
                for(to <- partTo.get.getBoxList) {
                    val boxTo = AnimationCache.getPartBoxCoordinates(partTo.get, to)
                    val boxFrom = (Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(0, 0, 0))

                    val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                    val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                    val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                    val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                    val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                    val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                    part.addBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toInt, (y2 - y1).toInt, (z2 - z1).toInt)
                }
            }
            part.rotateAngleX = (partTo.get.rotateAngleX / maxIndex) * index
            part.rotateAngleY = (partTo.get.rotateAngleY / maxIndex) * index
            part.rotateAngleZ = (partTo.get.rotateAngleZ / maxIndex) * index
        }
        part
    }
}