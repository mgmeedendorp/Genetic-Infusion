package seremis.geninfusion.util

import net.minecraft.client.model.ModelBox
import net.minecraft.util.Vec3
import seremis.geninfusion.api.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.render.animation.AnimationCache
import seremis.geninfusion.api.util.render.model.{Model, ModelPart, ModelPartAttachmentPoint}

import scala.collection.immutable.TreeMap
import scala.collection.mutable.{HashMap, ListBuffer, WeakHashMap}

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
            var pos1 = Vec3.createVectorHelper(part.offsetX + Math.min(box.posX2, box.posX1), part.offsetY + Math.min(box.posY2, box.posY1), part.offsetZ + Math.min(box.posZ2, box.posZ1))
            var pos2 = Vec3.createVectorHelper(part.offsetX + Math.max(box.posX2, box.posX1), part.offsetY + Math.max(box.posY2, box.posY1), part.offsetZ + Math.max(box.posZ2, box.posZ1))

            pos1.rotateAroundX(-part.rotateAngleX)
            pos1.rotateAroundY(-part.rotateAngleY)
            pos1.rotateAroundZ(-part.rotateAngleZ)
            pos2.rotateAroundX(-part.rotateAngleX)
            pos2.rotateAroundY(-part.rotateAngleY)
            pos2.rotateAroundZ(-part.rotateAngleZ)

            pos1 = pos1.addVector(part.rotationPointX, part.rotationPointY, part.rotationPointZ)
            pos2 = pos2.addVector(part.rotationPointX, part.rotationPointY, part.rotationPointZ)

            val nearX = Math.min(pos1.xCoord, pos2.xCoord)
            val nearY = Math.min(pos1.yCoord, pos2.yCoord)
            val nearZ = Math.min(pos1.zCoord, pos2.zCoord)
            val farX = Math.max(pos1.xCoord, pos2.xCoord)
            val farY = Math.max(pos1.yCoord, pos2.yCoord)
            val farZ = Math.max(pos1.zCoord, pos2.zCoord)

            cachedCoords += ((part, box) ->(Vec3.createVectorHelper(nearX, nearY, nearZ), Vec3.createVectorHelper(farX, farY, farZ)))
        }
        cachedCoords.get((part, box)).get
    }

    def getPartBoxCoordinatesWithoutRotation(part: ModelPart, box: ModelBox): (Vec3, Vec3) = {
        if(!cachedCoordsWithoutRotation.contains(part -> box)) {
            val pos1 = Vec3.createVectorHelper(Math.min(box.posX2, box.posX1), Math.min(box.posY2, box.posY1), Math.min(box.posZ2, box.posZ1))
            val pos2 = Vec3.createVectorHelper(Math.max(box.posX2, box.posX1), Math.max(box.posY2, box.posY1), Math.max(box.posZ2, box.posZ1))

            val nearX = Math.min(pos1.xCoord, pos2.xCoord)
            val nearY = Math.min(pos1.yCoord, pos2.yCoord)
            val nearZ = Math.min(pos1.zCoord, pos2.zCoord)
            val farX = Math.max(pos1.xCoord, pos2.xCoord)
            val farY = Math.max(pos1.yCoord, pos2.yCoord)
            val farZ = Math.max(pos1.zCoord, pos2.zCoord)

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
            val near = Vec3.createVectorHelper(Double.PositiveInfinity, Double.PositiveInfinity, Double.PositiveInfinity)
            val far = Vec3.createVectorHelper(Double.NegativeInfinity, Double.NegativeInfinity, Double.NegativeInfinity)
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

    def distSq(part1: ModelPart, part2: ModelPart): Float = {
        val outer1 = getModelPartOuterBox(part1)
        val outer2 = getModelPartOuterBox(part2)

        val dx = Math.abs(outer1._1.xCoord - outer2._1.xCoord)
        val dy = Math.abs(outer1._1.yCoord - outer2._1.yCoord)
        val dz = Math.abs(outer1._1.zCoord - outer2._1.zCoord)

        (dx * dx + dy * dy + dz * dz).toFloat
    }

    def reattachModelParts(model: Model): Model = {
        val possibleCombinations: HashMap[Float, PartPointPair] = HashMap()

        for(part1 <- model.getAllParts) {
            for(part2 <- model.getAllParts) {
                val connectionInfo = model.partsConnectWeight(part1, part2)

                if(connectionInfo._2.nonEmpty && connectionInfo._3.nonEmpty) {
                    possibleCombinations += (connectionInfo._1 -> PartPointPair(part1, connectionInfo._2.get, part2, connectionInfo._3.get))
                }
            }
        }

        val sortedCombinations = TreeMap(possibleCombinations.toSeq:_*)
        val usedAttachmentPoints: ListBuffer[PartPoint] = ListBuffer()
        val connectedParts: ConnectedParts = new ConnectedParts()

        for(combination <- sortedCombinations) {
            if(!usedAttachmentPoints.contains(combination._2.getPartPoint1) && !usedAttachmentPoints.contains(combination._2.getPartPoint2)) {
                connectParts(combination._2.getPartPoint1, combination._2.getPartPoint2, connectedParts)

                usedAttachmentPoints += combination._2.getPartPoint1
                usedAttachmentPoints += combination._2.getPartPoint2
                connectedParts add combination._2
            }
        }

        model
    }

    def connectParts(point1: PartPoint, point2: PartPoint, connectedParts: ConnectedParts) = {
        val outerPart1 = getModelPartOuterBox(point1.part)
        val outerPart2 = getModelPartOuterBox(point2.part)

        val dX1 = ((outerPart2._1.xCoord + point2.point.getPointLocation.xCoord) - (outerPart1._1.xCoord + point1.point.getPointLocation.xCoord)) / 2
        val dY1 = ((outerPart2._1.yCoord + point2.point.getPointLocation.yCoord) - (outerPart1._1.yCoord + point1.point.getPointLocation.yCoord)) / 2
        val dZ1 = ((outerPart2._1.zCoord + point2.point.getPointLocation.zCoord) - (outerPart1._1.zCoord + point1.point.getPointLocation.zCoord)) / 2

        val dX2 = ((outerPart1._1.xCoord + point1.point.getPointLocation.xCoord) - (outerPart2._1.xCoord + point2.point.getPointLocation.xCoord)) / 2
        val dY2 = ((outerPart1._1.yCoord + point1.point.getPointLocation.yCoord) - (outerPart2._1.yCoord + point2.point.getPointLocation.yCoord)) / 2
        val dZ2 = ((outerPart1._1.zCoord + point1.point.getPointLocation.zCoord) - (outerPart2._1.zCoord + point2.point.getPointLocation.zCoord)) / 2

        val connectedTo1 = connectedParts.getAllPartsConnectedTo(point1.part)
        val connectedTo2 = connectedParts.getAllPartsConnectedTo(point2.part)

        connectedTo1.foreach(part => {
            part.rotationPointX += dX1.toFloat
            part.rotationPointY += dY1.toFloat
            part.rotationPointZ += dZ1.toFloat

            cachedOuterBox -= part
            part.getBoxList.foreach(box => {
                cachedCoords -= ((part, box))
                cachedCoordsWithoutRotation -= ((part, box))
            })
        })

        connectedTo2.foreach(part => {
            part.rotationPointX += dX2.toFloat
            part.rotationPointY += dY2.toFloat
            part.rotationPointZ += dZ2.toFloat

            cachedOuterBox -= part
            part.getBoxList.foreach(box => {
                cachedCoords -= ((part, box))
                cachedCoordsWithoutRotation -= ((part, box))
            })
        })
    }

    private case class PartPoint(part: ModelPart, point: ModelPartAttachmentPoint) {}
    private case class PartPointPair(part1: ModelPart, point1: ModelPartAttachmentPoint, part2: ModelPart, point2: ModelPartAttachmentPoint) {
        def getPartPoint1 = PartPoint(part1, point1)
        def getPartPoint2 = PartPoint(part2, point2)
        def contains(part: ModelPart) = part1.equals(part) || part2.equals(part)
    }

    private class ConnectedParts(pairs: ListBuffer[PartPointPair] = new ListBuffer[PartPointPair]()) {

        def add(pair: PartPointPair) = {
            pairs += pair
        }

        def getAllPartsConnectedTo(start: ModelPart): Array[ModelPart] = {
            val connected: ListBuffer[ModelPart] = ListBuffer()

            connected += start

            //TODO optimize this
            for(i <- 0 to pairs.size) {
                for(pair <- pairs) {
                    for(part <- connected) {
                        if(!connected.contains(part) && pair.contains(part)) {
                            connected += part
                        }
                    }
                }
            }

            connected.to[Array]
        }
    }
}