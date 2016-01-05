package seremis.geninfusion.util

import java.awt.geom.Rectangle2D.Double
import java.awt.image.BufferedImage

import net.minecraft.client.model.ModelBox
import net.minecraft.util.Vec3
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.{Model, ModelPart, ModelPartAttachmentPoint, ModelPartType}
import seremis.geninfusion.helper.GITextureHelper

import scala.collection.immutable.HashMap
import scala.collection.mutable.{ListBuffer, WeakHashMap}
import scala.util.Random

object UtilModel {

    var cachedCoords: WeakHashMap[(ModelPart, ModelBox), (Vec3, Vec3)] = WeakHashMap()
    var cachedCoordsWithoutRotation: WeakHashMap[(ModelPart, ModelBox), (Vec3, Vec3)] = WeakHashMap()
    var cachedOuterBox: WeakHashMap[ModelPart, (Vec3, Vec3)] = WeakHashMap()
    var cachedWidth: WeakHashMap[Model, Float] = WeakHashMap()
    var cachedHeight: WeakHashMap[Model, Float] = WeakHashMap()

    def morphModel(modelFrom: Model, modelTo: Model, maxIndex: Int, index: Int): Model = {
        val result: ListBuffer[ModelPart] = ListBuffer()

        val partsFrom = modelFrom.getAllParts
        val partsTo = modelTo.getAllParts.to[ListBuffer]

        partsFrom.foreach(partFrom => {
            val partTo = modelTo.getMostResemblingPart(partFrom)
            if(partTo.nonEmpty)
                partsTo -= partTo.get

            result += morphModelPart(Some(partFrom), partTo, maxIndex, index)
        })

        partsTo.foreach(partTo => {
            result += morphModelPart(None, Some(partTo), maxIndex, index)
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
        val part = new ModelPart(if(partTo.nonEmpty) partTo.get.boxName else partFrom.get.boxName, if(partTo.nonEmpty) partTo.get.modelPartType else partFrom.get.modelPartType,  if(partTo.nonEmpty) partTo.get.attachmentPoints else partFrom.get.attachmentPoints)

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

    def getPartBoxCoordinates(part: ModelPart, box: ModelBox): (Vec3, Vec3) = {
        if(!cachedCoords.contains((part, box))) {
            var p1 = Vec3.createVectorHelper(box.posX1, box.posY1, box.posZ1)
            var p2 = Vec3.createVectorHelper(box.posX1, box.posY1, box.posZ2)
            var p3 = Vec3.createVectorHelper(box.posX1, box.posY2, box.posZ1)
            var p4 = Vec3.createVectorHelper(box.posX1, box.posY2, box.posZ2)
            var p5 = Vec3.createVectorHelper(box.posX2, box.posY1, box.posZ1)
            var p6 = Vec3.createVectorHelper(box.posX2, box.posY1, box.posZ2)
            var p7 = Vec3.createVectorHelper(box.posX2, box.posY2, box.posZ1)
            var p8 = Vec3.createVectorHelper(box.posX2, box.posY2, box.posZ2)

            p1 = getRotatedAndOffsetVector(part, p1)
            p2 = getRotatedAndOffsetVector(part, p2)
            p3 = getRotatedAndOffsetVector(part, p3)
            p4 = getRotatedAndOffsetVector(part, p4)
            p5 = getRotatedAndOffsetVector(part, p5)
            p6 = getRotatedAndOffsetVector(part, p6)
            p7 = getRotatedAndOffsetVector(part, p7)
            p8 = getRotatedAndOffsetVector(part, p8)

            val nearX = Math.min(p1.xCoord, Math.min(p2.xCoord, Math.min(p3.xCoord, Math.min(p4.xCoord, Math.min(p5.xCoord, Math.min(p6.xCoord, Math.min(p7.xCoord, p8.xCoord)))))))
            val nearY = Math.min(p1.yCoord, Math.min(p2.yCoord, Math.min(p3.yCoord, Math.min(p4.yCoord, Math.min(p5.yCoord, Math.min(p6.yCoord, Math.min(p7.yCoord, p8.yCoord)))))))
            val nearZ = Math.min(p1.zCoord, Math.min(p2.zCoord, Math.min(p3.zCoord, Math.min(p4.zCoord, Math.min(p5.zCoord, Math.min(p6.zCoord, Math.min(p7.zCoord, p8.zCoord)))))))
            val farX = Math.max(p1.xCoord, Math.max(p2.xCoord, Math.max(p3.xCoord, Math.max(p4.xCoord, Math.max(p5.xCoord, Math.max(p6.xCoord, Math.max(p7.xCoord, p8.xCoord)))))))
            val farY = Math.max(p1.yCoord, Math.max(p2.yCoord, Math.max(p3.yCoord, Math.max(p4.yCoord, Math.max(p5.yCoord, Math.max(p6.yCoord, Math.max(p7.yCoord, p8.yCoord)))))))
            val farZ = Math.max(p1.zCoord, Math.max(p2.zCoord, Math.max(p3.zCoord, Math.max(p4.zCoord, Math.max(p5.zCoord, Math.max(p6.zCoord, Math.max(p7.zCoord, p8.zCoord)))))))

            cachedCoords += ((part, box) ->(Vec3.createVectorHelper(nearX, nearY, nearZ), Vec3.createVectorHelper(farX, farY, farZ)))
        }
        cachedCoords.get((part, box)).get
    }

    def getPartBoxCoordinatesWithoutRotation(part: ModelPart, box: ModelBox): (Vec3, Vec3) = {
        if(!cachedCoordsWithoutRotation.contains(part -> box)) {
            var p1 = Vec3.createVectorHelper(box.posX1, box.posY1, box.posZ1)
            var p2 = Vec3.createVectorHelper(box.posX1, box.posY1, box.posZ2)
            var p3 = Vec3.createVectorHelper(box.posX1, box.posY2, box.posZ1)
            var p4 = Vec3.createVectorHelper(box.posX1, box.posY2, box.posZ2)
            var p5 = Vec3.createVectorHelper(box.posX2, box.posY1, box.posZ1)
            var p6 = Vec3.createVectorHelper(box.posX2, box.posY1, box.posZ2)
            var p7 = Vec3.createVectorHelper(box.posX2, box.posY2, box.posZ1)
            var p8 = Vec3.createVectorHelper(box.posX2, box.posY2, box.posZ2)

            p1 = p1.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)
            p2 = p2.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)
            p3 = p3.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)
            p4 = p4.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)
            p5 = p5.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)
            p6 = p6.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)
            p7 = p7.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)
            p8 = p8.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)

            val nearX = Math.min(p1.xCoord, Math.min(p2.xCoord, Math.min(p3.xCoord, Math.min(p4.xCoord, Math.min(p5.xCoord, Math.min(p6.xCoord, Math.min(p7.xCoord, p8.xCoord)))))))
            val nearY = Math.min(p1.yCoord, Math.min(p2.yCoord, Math.min(p3.yCoord, Math.min(p4.yCoord, Math.min(p5.yCoord, Math.min(p6.yCoord, Math.min(p7.yCoord, p8.yCoord)))))))
            val nearZ = Math.min(p1.zCoord, Math.min(p2.zCoord, Math.min(p3.zCoord, Math.min(p4.zCoord, Math.min(p5.zCoord, Math.min(p6.zCoord, Math.min(p7.zCoord, p8.zCoord)))))))
            val farX = Math.max(p1.xCoord, Math.max(p2.xCoord, Math.max(p3.xCoord, Math.max(p4.xCoord, Math.max(p5.xCoord, Math.max(p6.xCoord, Math.max(p7.xCoord, p8.xCoord)))))))
            val farY = Math.max(p1.yCoord, Math.max(p2.yCoord, Math.max(p3.yCoord, Math.max(p4.yCoord, Math.max(p5.yCoord, Math.max(p6.yCoord, Math.max(p7.yCoord, p8.yCoord)))))))
            val farZ = Math.max(p1.zCoord, Math.max(p2.zCoord, Math.max(p3.zCoord, Math.max(p4.zCoord, Math.max(p5.zCoord, Math.max(p6.zCoord, Math.max(p7.zCoord, p8.zCoord)))))))

            cachedCoordsWithoutRotation += ((part, box) -> (Vec3.createVectorHelper(nearX, nearY, nearZ), Vec3.createVectorHelper(farX, farY, farZ)))
        }
        cachedCoordsWithoutRotation.get((part, box)).get
    }

    def getModelWidth(entity: IEntitySoulCustom): Float = getModelWidth(getModel(entity))

    def getModelWidth(model: Model): Float = {
        if(!cachedWidth.contains(model)) {
            var minX, minZ = Float.PositiveInfinity
            var maxX, maxZ = Float.NegativeInfinity

            for(part <- model.getAllParts) {
                val outerBox = getModelPartOuterBox(part)

                if(outerBox._1.xCoord < minX)
                    minX = outerBox._1.xCoord.toFloat
                if(outerBox._1.zCoord < minZ)
                    minZ = outerBox._1.zCoord.toFloat
                if(outerBox._2.xCoord > maxX)
                    maxX = outerBox._2.xCoord.toFloat
                if(outerBox._2.zCoord > maxZ)
                    maxZ = outerBox._2.zCoord.toFloat
            }

            val dX = maxX - minX
            val dZ = maxZ - minZ

            var width = 0.0F

            if(dX > dZ * 1.3F || dZ > dX * 1.3F) {
                width = (dX + dZ) / 2
            } else {
                width = Math.max(dX, dZ)
            }

            cachedWidth += (model -> width / 16)
        }
        cachedWidth.get(model).get
    }

    def getModelHeight(entity: IEntitySoulCustom): Float = getModelHeight(getModel(entity))

    def getModelHeight(model: Model): Float = {
        if(!cachedHeight.contains(model)) {
            var minY = Float.PositiveInfinity
            var maxY = Float.NegativeInfinity

            for(part <- model.getAllParts) {
                val outerBox = getModelPartOuterBox(part)

                if(outerBox._1.yCoord < minY)
                    minY = outerBox._1.yCoord.toFloat
                if(outerBox._2.yCoord > maxY)
                    maxY = outerBox._2.yCoord.toFloat
            }
            cachedHeight += (model -> (maxY - minY) / 16)
        }
        cachedHeight.get(model).get
    }

    def getModel(entity: IEntitySoulCustom): Model = {
        SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneModel)
    }

    def modelChanged(model: Model) {
        model.getAllParts.foreach(p => p.getBoxList.foreach(b => {
            if(cachedCoords.contains(p -> b)) cachedCoords -= (p -> b)
            if(cachedCoordsWithoutRotation.contains(p -> b)) cachedCoordsWithoutRotation -= (p -> b)
        }))
        if(cachedHeight.contains(model)) cachedHeight -= model
        model.getAllParts.foreach(p => if(cachedOuterBox.contains(p)) cachedOuterBox -= p)
        if(cachedWidth.contains(model)) cachedWidth -= model
    }

    def getModelPartOuterBox(part: ModelPart): (Vec3, Vec3) = {
        if(!cachedOuterBox.contains(part)) {
            val near = Vec3.createVectorHelper(scala.Double.PositiveInfinity, scala.Double.PositiveInfinity, scala.Double.PositiveInfinity)
            val far = Vec3.createVectorHelper(scala.Double.NegativeInfinity, scala.Double.NegativeInfinity, scala.Double.NegativeInfinity)
            part.getBoxList.foreach(cube => {
                val coords = getPartBoxCoordinates(part, cube)

                if(coords._1.xCoord < near.xCoord)
                    near.xCoord = coords._1.xCoord
                if(coords._1.yCoord < near.yCoord)
                    near.yCoord = coords._1.yCoord
                if(coords._1.zCoord < near.zCoord)
                    near.zCoord = coords._1.zCoord
                if(coords._2.xCoord > far.xCoord)
                    far.xCoord = coords._2.xCoord
                if(coords._2.yCoord > far.yCoord)
                    far.yCoord = coords._2.yCoord
                if(coords._2.zCoord > far.zCoord)
                    far.zCoord = coords._2.zCoord
            })
            cachedOuterBox += (part ->(near, far))
        }
        cachedOuterBox.get(part).get
    }

    def getRotatedAndOffsetVector(part: ModelPart, vec: Vec3): Vec3 = {
        vec.rotateAroundX(-part.rotateAngleX)
        vec.rotateAroundY(part.rotateAngleY)
        vec.rotateAroundZ(-part.rotateAngleZ)

        vec.addVector(part.offsetX + part.rotationPointX, part.offsetY + part.rotationPointY, part.offsetZ + part.rotationPointZ)
    }

    def getModelBoxTopLeftCornerCoord(part: ModelPart, box: ModelBox): Vec3 = {
        val corner = Vec3.createVectorHelper(box.posX1, box.posY1, box.posZ1)

        getRotatedAndOffsetVector(part, corner)
    }

    def distSq(part1: ModelPart, part2: ModelPart): Float = {
        val outer1 = getModelPartOuterBox(part1)
        val outer2 = getModelPartOuterBox(part2)

        val dx = Math.abs(outer1._1.xCoord - outer2._1.xCoord)
        val dy = Math.abs(outer1._1.yCoord - outer2._1.yCoord)
        val dz = Math.abs(outer1._1.zCoord - outer2._1.zCoord)

        (dx * dx + dy * dy + dz * dz).toFloat
    }

    def randomlyCombineModels(model1: Model, texture1: BufferedImage, model2: Model, texture2: BufferedImage): ((Model, BufferedImage), (Model, BufferedImage)) = {
        val allParts = model1.getAllParts.map(part => PartTextureCombo(ModelPartAttachmentPointData(part), texture1)) ++ model2.getAllParts.map(part => PartTextureCombo(ModelPartAttachmentPointData(part), texture2))

        var dominantModel: Model = null
        var dominantTexture: BufferedImage = null
        var recessiveModel: Model = null
        var recessiveTexture: BufferedImage = null
        val rnd = new Random()

        var mainPart = allParts(rnd.nextInt(allParts.length))
        var partsLeft: ListBuffer[PartTextureCombo] = allParts.to[ListBuffer]

        //Determine dominant model and work out the texture
        val dominant = createModelFromParts(mainPart, partsLeft)

        partsLeft = dominant._1
        dominantModel = dominant._2
        dominantTexture = dominant._3

        placeModelInCenterOfBoundingBox(dominantModel)

        //Work out recessive model and texture.
        val models: ListBuffer[ModelTextureCombo] = ListBuffer()

        if(partsLeft.nonEmpty)
            mainPart = partsLeft(rnd.nextInt(partsLeft.length))

        while(partsLeft.nonEmpty) {
            val recessive = createModelFromParts(mainPart, partsLeft)

            placeModelInCenterOfBoundingBox(recessive._2)

            models += ModelTextureCombo(recessive._2, recessive._3)
            partsLeft = recessive._1
            if(partsLeft.nonEmpty)
                mainPart = partsLeft(rnd.nextInt(partsLeft.length))
        }

        models.sortBy(_.model.getAllParts.length)

        val recessive = models.lastOption

        if(recessive.nonEmpty) {
            recessiveModel = recessive.get.model
            recessiveTexture = recessive.get.texture
        } else {
            recessiveModel = dominantModel
            recessiveTexture = dominantTexture
        }

        ((dominantModel, dominantTexture), (recessiveModel, recessiveTexture))
    }

    private def createModelFromParts(mainPart: PartTextureCombo, parts: ListBuffer[PartTextureCombo]): (ListBuffer[PartTextureCombo], Model, BufferedImage) = {
        val modelParts: ListBuffer[ModelPart] = ListBuffer()
        val textureRects: ListBuffer[Double] = ListBuffer()
        val textureParts: ListBuffer[BufferedImage] = ListBuffer()

        val allConnections: ListBuffer[ModelPartPointConnection] = ListBuffer()

        var currentLevel = Array(mainPart)
        var nextLevel = ListBuffer[PartTextureCombo]()
        val partsLeft = parts

        val img = GITextureHelper.getModelPartTexture(mainPart.getPart, mainPart.texture)
        textureRects += new Double(0, 0, img.getWidth, img.getHeight)
        textureParts += img
        modelParts += mainPart.getPart

        do {
            nextLevel.clear()

            for(level <- currentLevel if partsLeft.nonEmpty) {
                val connections = connectAllPoints(level, partsLeft.to[Array])

                for(connection <- connections) {
                    val image = GITextureHelper.getModelPartTexture(connection.part2.getPart, connection.part2.texture)
                    textureRects += new Double(0, 0, image.getWidth, image.getHeight)
                    textureParts += image

                    modelParts += connection.part2.getPart

                    nextLevel += connection.part2
                    allConnections += connection
                }
                partsLeft -= level
            }

            currentLevel = nextLevel.to[Array]
        } while(nextLevel.nonEmpty && partsLeft.nonEmpty)

        val stitched = GITextureHelper.stitchImages(textureRects, textureParts)
        val model = new Model

        for(i <- modelParts.indices) {
            val part = modelParts(i)
            val rect = stitched._2(i)

            GITextureHelper.changeModelPartTextureSize(part, (stitched._1.getWidth, stitched._1.getHeight))
            GITextureHelper.moveModelPartTextureOffset(part, (rect.getMinX.toInt, rect.getMinY.toInt))

            part.resetDisplayList()

            model.addPart(part)
        }

        (partsLeft, model, stitched._1)
    }

    /**
      * Chooses from parts the parts best suited to connect to all the AttachmentPoints. Returns these connections.
      */
    private def connectAllPoints(combo: PartTextureCombo, parts: Array[PartTextureCombo]): ListBuffer[ModelPartPointConnection] = {
        val connections: ListBuffer[ModelPartPointConnection] = ListBuffer()
        var connectedParts: HashMap[PartTextureCombo, ModelPartPointConnection] = HashMap()

        for(mainPartPoint <- combo.getPart.getAttachmentPoints) {
            if(!combo.part.usedConnections.contains(mainPartPoint)) {
                var connectionCandidates: ListBuffer[ModelPartPointConnection] = ListBuffer()

                for(p <- parts) {
                    if(p != combo) {
                        for(pPoint <- p.getPart.getAttachmentPoints) {
                            if(!p.part.usedConnections.contains(pPoint)) {
                                for(pPointConnectable <- pPoint.getConnectableModelPartTypes) {
                                    if(pPointConnectable.name == combo.getPart.modelPartType.name) {
                                        for(mainPartPointConnectable <- mainPartPoint.getConnectableModelPartTypes) {
                                            if(mainPartPointConnectable.name == p.getPart.modelPartType.name) {
                                                val connection = ModelPartPointConnection(combo, mainPartPoint, mainPartPointConnectable, p, pPoint, pPointConnectable)

                                                if(!connectedParts.contains(p) || connectedParts(p).weight < connection.weight) {
                                                    connectionCandidates += connection
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                connectionCandidates = connectionCandidates.sortBy(_.weight)

                if(connectionCandidates.nonEmpty) {
                    val last = connectionCandidates.last

                    connections += last
                    connectedParts += (last.part2 -> last)
                    last.part1.part.usedConnections += last.point1
                    last.part2.part.usedConnections += last.point2

                    connectPartTo(last.part2.getPart, last.point2, last.part1.getPart, last.point1)
                }
            }
        }
        connections
    }

    def connectPartTo(partToConnect: ModelPart, pointToConnect: ModelPartAttachmentPoint, partToConnectTo: ModelPart, pointToConnectTo: ModelPartAttachmentPoint) {
        val p1 = Vec3.createVectorHelper(partToConnect.getBoxList(0).posX1, partToConnect.getBoxList(0).posY1, partToConnect.getBoxList(0).posZ1)
        val p2 = Vec3.createVectorHelper(partToConnectTo.getBoxList(0).posX1, partToConnectTo.getBoxList(0).posY1, partToConnectTo.getBoxList(0).posZ1)

        val outerPart1 = getRotatedAndOffsetVector(partToConnect, p1.addVector(pointToConnect.getLocation.xCoord, pointToConnect.getLocation.yCoord, pointToConnect.getLocation.zCoord))
        val outerPart2 = getRotatedAndOffsetVector(partToConnectTo, p2.addVector(pointToConnectTo.getLocation.xCoord, pointToConnectTo.getLocation.yCoord, pointToConnectTo.getLocation.zCoord))

        val dX = outerPart2.xCoord - outerPart1.xCoord
        val dY = outerPart2.yCoord - outerPart1.yCoord
        val dZ = outerPart2.zCoord - outerPart1.zCoord

        partToConnect.rotationPointX += dX.toFloat
        partToConnect.rotationPointY += dY.toFloat
        partToConnect.rotationPointZ += dZ.toFloat

        removePartFromCache(partToConnect)
    }

    def placeModelInCenterOfBoundingBox(model: Model): Model = {
        val allParts = model.getAllParts

        val min = Vec3.createVectorHelper(scala.Double.PositiveInfinity, scala.Double.PositiveInfinity, scala.Double.PositiveInfinity)
        val max = Vec3.createVectorHelper(scala.Double.NegativeInfinity, scala.Double.NegativeInfinity, scala.Double.NegativeInfinity)

        allParts.foreach(part => {
            val coords = getModelPartOuterBox(part)

            if(coords._1.xCoord < min.xCoord)
                min.xCoord = coords._1.xCoord
            if(coords._1.yCoord < min.yCoord)
                min.yCoord = coords._1.yCoord
            if(coords._1.zCoord < min.zCoord)
                min.zCoord = coords._1.zCoord
            if(coords._2.xCoord > max.xCoord)
                max.xCoord = coords._2.xCoord
            if(coords._2.yCoord > max.yCoord)
                max.yCoord = coords._2.yCoord
            if(coords._2.zCoord > max.zCoord)
                max.zCoord = coords._2.zCoord
        })

        if(max.yCoord != 23.0F) {
            val dY = 23.0F - max.yCoord

            allParts.foreach(part => {
                part.rotationPointY += dY.toFloat
                removePartFromCache(part)
            })
        }

        if((max.xCoord + min.xCoord) / 2 != 0) {
            val dX = 0 - (max.xCoord + min.xCoord) / 2

            allParts.foreach(part => {
                part.rotationPointX += dX.toFloat
                removePartFromCache(part)
            })
        }

        if((max.zCoord + min.zCoord) / 2 != 0) {
            val dZ = 0 - (max.zCoord + min.zCoord) / 2

            allParts.foreach(part => {
                part.rotationPointZ += dZ.toFloat
                removePartFromCache(part)
            })
        }

        model
    }

    def removePartFromCache(part: ModelPart)  {
        cachedOuterBox -= part
        part.getBoxList.foreach(box => {
            cachedCoords -= ((part, box))
            cachedCoordsWithoutRotation -= ((part, box))
        })
    }

    private case class ModelPartPointConnection(part1: PartTextureCombo, point1: ModelPartAttachmentPoint, type1: ModelPartType, part2: PartTextureCombo, point2: ModelPartAttachmentPoint, type2: ModelPartType) {
        val weight = (part1.part.part.modelPartType.calculateTagSimilarity(type2) + part2.part.part.modelPartType.calculateTagSimilarity(type1)) / 2.0F

    }
    private case class ModelPartAttachmentPointData(part: ModelPart) {
        val usedConnections: ListBuffer[ModelPartAttachmentPoint] = ListBuffer()

        def getPart: ModelPart = part
    }
    private case class PartTextureCombo(part: ModelPartAttachmentPointData, texture: BufferedImage) {
        def getPart: ModelPart = part.part
    }
    private case class ModelTextureCombo(model: Model, texture: BufferedImage) {}
}