package seremis.geninfusion.util

import net.minecraft.util.Vec3
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}

import scala.collection.mutable.ListBuffer

object UtilModel {

    def morphModel(modelFrom: Model, modelTo: Model, maxIndex: Int, index: Int): Model = {
        val result: ListBuffer[ModelPart] = ListBuffer()

        SoulHelper.modelPartTypeRegistry.getModelPartTypes.foreach(name => {
            morphModelPartArray(modelFrom.getParts(name), modelTo.getParts(name), maxIndex, index).foreach(part => result += part)
        })

        new Model(result.to[Array])
    }

    def morphModelPartArray(partsFrom: Option[Array[ModelPart]], partsTo: Option[Array[ModelPart]], maxIndex: Int, index: Int): Array[ModelPart] = {
        val parts: ListBuffer[ModelPart] = ListBuffer()

        if(partsFrom.nonEmpty && partsTo.nonEmpty) {
            for((from, to) <- partsFrom.get zip partsTo.get) {
                parts += morphModelPart(Some(from), Some(to), maxIndex, index)
            }

            if(partsFrom.get.length > partsTo.get.length) {
                val remaining = partsFrom.get.drop(partsTo.get.length)

                remaining.foreach(p => parts += morphModelPart(Some(p), None, maxIndex, index))
            } else if(partsFrom.get.length < partsTo.get.length) {
                val remaining = partsTo.get.drop(partsFrom.get.length)

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
        val part = new ModelPart(if(partTo.nonEmpty) partTo.get.boxName else partFrom.get.boxName, if(partTo.nonEmpty) partTo.get.modelPartType else partFrom.get.modelPartType)

        if(partFrom.nonEmpty && partTo.nonEmpty) {
            for((from, to) <- partFrom.get.getBoxList zip partTo.get.getBoxList) {
                val boxFrom = AnimationCache.getPartBoxCoordinatesWithoutRotation(partFrom.get, from)
                val boxTo = AnimationCache.getPartBoxCoordinatesWithoutRotation(partTo.get, to)

                val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                part.addGIBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toFloat, (y2 - y1).toFloat, (z2 - z1).toFloat)
            }

            if(partFrom.get.getBoxList.length > partTo.get.getBoxList.length) {
                for(from <- partFrom.get.getBoxList.drop(partTo.get.getBoxList.length)) {
                    val boxFrom = AnimationCache.getPartBoxCoordinatesWithoutRotation(partFrom.get, from)
                    val coordTo = Vec3.createVectorHelper(boxFrom._1.xCoord + (boxFrom._2.xCoord - boxFrom._1.xCoord) / 2, boxFrom._1.yCoord + (boxFrom._2.yCoord - boxFrom._1.yCoord) / 2, boxFrom._1.zCoord + (boxFrom._2.zCoord - boxFrom._1.zCoord) / 2)
                    val boxTo = (coordTo, coordTo)

                    val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                    val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                    val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                    val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                    val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                    val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                    part.addGIBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toFloat, (y2 - y1).toFloat, (z2 - z1).toFloat)
                }
            } else if(partTo.get.getBoxList.length > partFrom.get.getBoxList.length) {
                for(to <- partTo.get.getBoxList.drop(partFrom.get.getBoxList.length)) {
                    val boxTo = AnimationCache.getPartBoxCoordinatesWithoutRotation(partTo.get, to)
                    val coordFrom = Vec3.createVectorHelper(boxTo._1.xCoord + (boxTo._2.xCoord - boxTo._1.xCoord) / 2, boxTo._1.yCoord + (boxTo._2.yCoord - boxTo._1.yCoord) / 2, boxTo._1.zCoord + (boxTo._2.zCoord - boxTo._1.zCoord) / 2)
                    val boxFrom = (coordFrom, coordFrom)

                    val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                    val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                    val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                    val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                    val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                    val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                    part.addGIBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toFloat, (y2 - y1).toFloat, (z2 - z1).toFloat)
                }
            }
            part.offsetX = partFrom.get.offsetX + ((partTo.get.offsetX - partFrom.get.offsetX) / maxIndex) * index
            part.offsetY = partFrom.get.offsetY + ((partTo.get.offsetY - partFrom.get.offsetY) / maxIndex) * index
            part.offsetZ = partFrom.get.offsetZ + ((partTo.get.offsetZ - partFrom.get.offsetZ) / maxIndex) * index
            
            part.rotationPointX = partFrom.get.rotationPointX + ((partTo.get.rotationPointX - partFrom.get.rotationPointX) / maxIndex) * index
            part.rotationPointY = partFrom.get.rotationPointY + ((partTo.get.rotationPointY - partFrom.get.rotationPointY) / maxIndex) * index
            part.rotationPointZ = partFrom.get.rotationPointZ + ((partTo.get.rotationPointZ - partFrom.get.rotationPointZ) / maxIndex) * index

            part.rotateAngleX = partFrom.get.rotateAngleX + ((partTo.get.rotateAngleX - partFrom.get.rotateAngleX) / maxIndex) * index
            part.rotateAngleY = partFrom.get.rotateAngleY + ((partTo.get.rotateAngleY - partFrom.get.rotateAngleY) / maxIndex) * index
            part.rotateAngleZ = partFrom.get.rotateAngleZ + ((partTo.get.rotateAngleZ - partFrom.get.rotateAngleZ) / maxIndex) * index
        }

        if(partFrom.nonEmpty) {
            if(partTo.isEmpty) {
                for(from <- partFrom.get.getBoxList) {
                    val boxFrom = AnimationCache.getPartBoxCoordinatesWithoutRotation(partFrom.get, from)
                    val coordTo = Vec3.createVectorHelper(boxFrom._1.xCoord + (boxFrom._2.xCoord - boxFrom._1.xCoord) / 2, boxFrom._1.yCoord + (boxFrom._2.yCoord - boxFrom._1.yCoord) / 2, boxFrom._1.zCoord + (boxFrom._2.zCoord - boxFrom._1.zCoord) / 2)
                    val boxTo = (coordTo, coordTo)

                    val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                    val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                    val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                    val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                    val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                    val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                    part.addGIBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toFloat, (y2 - y1).toFloat, (z2 - z1).toFloat)
                }
                part.offsetX = partFrom.get.offsetX + (partFrom.get.offsetX / maxIndex) * index
                part.offsetY = partFrom.get.offsetY + (partFrom.get.offsetY / maxIndex) * index
                part.offsetZ = partFrom.get.offsetZ + (partFrom.get.offsetZ / maxIndex) * index

                part.rotationPointX = partFrom.get.rotationPointX + (partFrom.get.rotationPointX / maxIndex) * index
                part.rotationPointY = partFrom.get.rotationPointY + (partFrom.get.rotationPointY / maxIndex) * index
                part.rotationPointZ = partFrom.get.rotationPointZ + (partFrom.get.rotationPointZ / maxIndex) * index

                part.rotateAngleX = partFrom.get.rotateAngleX + (partFrom.get.rotateAngleX / maxIndex) * index
                part.rotateAngleY = partFrom.get.rotateAngleY + (partFrom.get.rotateAngleY / maxIndex) * index
                part.rotateAngleZ = partFrom.get.rotateAngleZ + (partFrom.get.rotateAngleZ / maxIndex) * index
            }
        } else if(partFrom.isEmpty) {
            if(partTo.nonEmpty) {
                for(to <- partTo.get.getBoxList) {
                    val boxTo = AnimationCache.getPartBoxCoordinatesWithoutRotation(partTo.get, to)
                    val coordFrom = Vec3.createVectorHelper(boxTo._1.xCoord + (boxTo._2.xCoord - boxTo._1.xCoord) / 2, boxTo._1.yCoord + (boxTo._2.yCoord - boxTo._1.yCoord) / 2, boxTo._1.zCoord + (boxTo._2.zCoord - boxTo._1.zCoord) / 2)
                    val boxFrom = (coordFrom, coordFrom)

                    val x1 = boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index
                    val x2 = boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index
                    val y1 = boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index
                    val y2 = boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index
                    val z1 = boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index
                    val z2 = boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index

                    part.addGIBox(x1.toFloat, y1.toFloat, z1.toFloat, (x2 - x1).toFloat, (y2 - y1).toFloat, (z2 - z1).toFloat)
                }
                part.offsetX = (partTo.get.offsetX / maxIndex) * index
                part.offsetY = (partTo.get.offsetY / maxIndex) * index
                part.offsetZ = (partTo.get.offsetZ / maxIndex) * index

                part.rotationPointX = (partTo.get.rotationPointX / maxIndex) * index
                part.rotationPointY = (partTo.get.rotationPointY / maxIndex) * index
                part.rotationPointZ = (partTo.get.rotationPointZ / maxIndex) * index

                part.rotateAngleX = (partTo.get.rotateAngleX / maxIndex) * index
                part.rotateAngleY = (partTo.get.rotateAngleY / maxIndex) * index
                part.rotateAngleZ = (partTo.get.rotateAngleZ / maxIndex) * index
            }
        }
        part
    }
}