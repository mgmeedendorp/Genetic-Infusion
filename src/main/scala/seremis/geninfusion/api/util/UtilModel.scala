package seremis.geninfusion.api.util

import net.minecraft.util.Vec3
import seremis.geninfusion.api.lib.{CuboidTypes, Genes}
import seremis.geninfusion.api.render.Model
import seremis.geninfusion.api.render.cuboid.{Cuboid, CuboidAttachmentPoint, CuboidType}
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}

import scala.collection.immutable.HashMap
import scala.collection.mutable.{ListBuffer, WeakHashMap}
import scala.util.Random

object UtilModel {

    var cachedOuterBox: WeakHashMap[Cuboid, (Vec3, Vec3)] = WeakHashMap()
    var cachedOuterBoxWithoutRotation: WeakHashMap[Cuboid, (Vec3, Vec3)] = WeakHashMap()
    var cachedHeight: WeakHashMap[Model, Float] = WeakHashMap()
    var cachedWidth: WeakHashMap[Model, Float] = WeakHashMap()
    var cachedArmsHorizontal: WeakHashMap[Model, Boolean] = WeakHashMap()

    def getModel(entity: IEntitySoulCustom): Model = SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneModelAdult)

    def morphModel(modelFrom: Model, modelTo: Model, maxIndex: Int, index: Int): Model = {
        val result: ListBuffer[Cuboid] = ListBuffer()

        val cuboidsFrom = modelFrom.getCuboids
        val cuboidsTo = modelTo.getCuboids.to[ListBuffer]

        for(cuboidFrom <- cuboidsFrom) {
            val cuboidTo = modelTo.getMostResemblingCuboid(cuboidFrom)

            if(cuboidTo.nonEmpty)
                cuboidsTo -= cuboidTo.get

            result += morphCuboid(Some(cuboidFrom), cuboidTo, maxIndex, index)
        }

        for(cuboidTo <- cuboidsTo) {
            result += morphCuboid(None, Some(cuboidTo), maxIndex, index)
        }

        new Model(result.to[Array])
    }

    def morphCuboid(cuboidFrom: Option[Cuboid], cuboidTo: Option[Cuboid], maxIndex: Int, index: Int): Cuboid = {
        var boxFrom: (Vec3, Vec3) = (Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(0, 0, 0))
        var rotateAnglesFrom: (Float, Float, Float) = (0, 0, 0)
        var rotationPointsFrom: (Float, Float, Float) = (0, 0, 0)

        var boxTo: (Vec3, Vec3) = (Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(0, 0, 0))
        var rotateAnglesTo: (Float, Float, Float) = (0, 0, 0)
        var rotationPointsTo: (Float, Float, Float) = (0, 0, 0)

        if(cuboidFrom.nonEmpty) {
            boxFrom = getCuboidOuterBox(cuboidFrom.get)
            rotateAnglesFrom = (cuboidFrom.get.rotateAngleX, cuboidFrom.get.rotateAngleY, cuboidFrom.get.rotateAngleZ)
            rotationPointsFrom = (cuboidFrom.get.rotationPointX, cuboidFrom.get.rotationPointY, cuboidFrom.get.rotationPointZ)
        }

        if(cuboidTo.nonEmpty) {
            boxTo = getCuboidOuterBox(cuboidTo.get)
            rotateAnglesTo = (cuboidTo.get.rotateAngleX, cuboidTo.get.rotateAngleY, cuboidTo.get.rotateAngleZ)
            rotationPointsTo = (cuboidTo.get.rotationPointX, cuboidTo.get.rotationPointY, cuboidTo.get.rotationPointZ)
        }

        val x1 = (boxFrom._1.xCoord + ((boxTo._1.xCoord - boxFrom._1.xCoord) / maxIndex) * index).toFloat
        val x2 = (boxFrom._2.xCoord + ((boxTo._2.xCoord - boxFrom._2.xCoord) / maxIndex) * index).toFloat
        val y1 = (boxFrom._1.yCoord + ((boxTo._1.yCoord - boxFrom._1.yCoord) / maxIndex) * index).toFloat
        val y2 = (boxFrom._2.yCoord + ((boxTo._2.yCoord - boxFrom._2.yCoord) / maxIndex) * index).toFloat
        val z1 = (boxFrom._1.zCoord + ((boxTo._1.zCoord - boxFrom._1.zCoord) / maxIndex) * index).toFloat
        val z2 = (boxFrom._2.zCoord + ((boxTo._2.zCoord - boxFrom._2.zCoord) / maxIndex) * index).toFloat

        val cuboid = new Cuboid(x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, false, null, null)

        cuboid.rotationPointX = rotationPointsFrom._1 + ((rotationPointsTo._1 - rotationPointsFrom._1) / maxIndex) * index
        cuboid.rotationPointY = rotationPointsFrom._2 + ((rotationPointsTo._2 - rotationPointsFrom._2) / maxIndex) * index
        cuboid.rotationPointZ = rotationPointsFrom._3 + ((rotationPointsTo._3 - rotationPointsFrom._3) / maxIndex) * index

        cuboid.rotateAngleX = rotateAnglesFrom._1 + ((rotateAnglesTo._1 - rotateAnglesFrom._1) / maxIndex) * index
        cuboid.rotateAngleY = rotateAnglesFrom._2 + ((rotateAnglesTo._2 - rotateAnglesFrom._2) / maxIndex) * index
        cuboid.rotateAngleZ = rotateAnglesFrom._3 + ((rotateAnglesTo._3 - rotateAnglesFrom._3) / maxIndex) * index

        cuboid
    }

    def getModelWidth(model: Model): Float = {
        if(!cachedWidth.contains(model)) {
            var minX = Float.PositiveInfinity
            var minZ = Float.PositiveInfinity
            var maxX = Float.NegativeInfinity
            var maxZ = Float.NegativeInfinity

            for(cuboid <- model.getCuboids) {
                val outerBox = getCuboidOuterBox(cuboid)

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

    def getModelHeight(model: Model): Float = {
        if(!cachedHeight.contains(model)) {
            var minY = Float.PositiveInfinity
            var maxY = Float.NegativeInfinity

            for(cuboid <- model.getCuboids) {
                val outerBox = getCuboidOuterBox(cuboid)

                if(outerBox._1.yCoord < minY)
                    minY = outerBox._1.yCoord.toFloat
                if(outerBox._2.yCoord > maxY)
                    maxY = outerBox._2.yCoord.toFloat
            }
            cachedHeight += (model -> (maxY - minY) / 16)
        }
        cachedHeight.get(model).get
    }

    def getCuboidOuterBox(cuboid: Cuboid): (Vec3, Vec3) = {
        if(!cachedOuterBox.contains(cuboid)) {
            val x1 = cuboid.x
            val y1 = cuboid.y
            val z1 = cuboid.z
            val x2 = x1 + cuboid.xSize
            val y2 = y1 + cuboid.ySize
            val z2 = z1 + cuboid.zSize

            var p1 = Vec3.createVectorHelper(x1, y1, z1)
            var p2 = Vec3.createVectorHelper(x1, y1, z2)
            var p3 = Vec3.createVectorHelper(x1, y2, z1)
            var p4 = Vec3.createVectorHelper(x1, y2, z2)
            var p5 = Vec3.createVectorHelper(x2, y1, z1)
            var p6 = Vec3.createVectorHelper(x2, y1, z2)
            var p7 = Vec3.createVectorHelper(x2, y2, z1)
            var p8 = Vec3.createVectorHelper(x2, y2, z2)

            p1 = getRotatedAndOffsetVector(cuboid, p1)
            p2 = getRotatedAndOffsetVector(cuboid, p2)
            p3 = getRotatedAndOffsetVector(cuboid, p3)
            p4 = getRotatedAndOffsetVector(cuboid, p4)
            p5 = getRotatedAndOffsetVector(cuboid, p5)
            p6 = getRotatedAndOffsetVector(cuboid, p6)
            p7 = getRotatedAndOffsetVector(cuboid, p7)
            p8 = getRotatedAndOffsetVector(cuboid, p8)

            val nearX = Math.min(p1.xCoord, Math.min(p2.xCoord, Math.min(p3.xCoord, Math.min(p4.xCoord, Math.min(p5.xCoord, Math.min(p6.xCoord, Math.min(p7.xCoord, p8.xCoord)))))))
            val nearY = Math.min(p1.yCoord, Math.min(p2.yCoord, Math.min(p3.yCoord, Math.min(p4.yCoord, Math.min(p5.yCoord, Math.min(p6.yCoord, Math.min(p7.yCoord, p8.yCoord)))))))
            val nearZ = Math.min(p1.zCoord, Math.min(p2.zCoord, Math.min(p3.zCoord, Math.min(p4.zCoord, Math.min(p5.zCoord, Math.min(p6.zCoord, Math.min(p7.zCoord, p8.zCoord)))))))
            val farX = Math.max(p1.xCoord, Math.max(p2.xCoord, Math.max(p3.xCoord, Math.max(p4.xCoord, Math.max(p5.xCoord, Math.max(p6.xCoord, Math.max(p7.xCoord, p8.xCoord)))))))
            val farY = Math.max(p1.yCoord, Math.max(p2.yCoord, Math.max(p3.yCoord, Math.max(p4.yCoord, Math.max(p5.yCoord, Math.max(p6.yCoord, Math.max(p7.yCoord, p8.yCoord)))))))
            val farZ = Math.max(p1.zCoord, Math.max(p2.zCoord, Math.max(p3.zCoord, Math.max(p4.zCoord, Math.max(p5.zCoord, Math.max(p6.zCoord, Math.max(p7.zCoord, p8.zCoord)))))))

            cachedOuterBox += (cuboid -> (Vec3.createVectorHelper(nearX, nearY, nearZ), Vec3.createVectorHelper(farX, farY, farZ)))
        }
        cachedOuterBox.get(cuboid).get
    }

    def getCuboidOuterBoxWithoutRotation(cuboid: Cuboid): (Vec3, Vec3) = {
        if(!cachedOuterBoxWithoutRotation.contains(cuboid)) {
            val x1 = cuboid.x
            val y1 = cuboid.y
            val z1 = cuboid.z
            val x2 = x1 + cuboid.xSize
            val y2 = y1 + cuboid.ySize
            val z2 = z1 + cuboid.zSize

            val nearX = Math.min(x1, x2)
            val nearY = Math.min(y1, y2)
            val nearZ = Math.min(z1, z2)
            val farX = Math.max(x1, x2)
            val farY = Math.max(y1, y2)
            val farZ = Math.max(z1, z2)

            cachedOuterBoxWithoutRotation += (cuboid -> (Vec3.createVectorHelper(nearX, nearY, nearZ), Vec3.createVectorHelper(farX, farY, farZ)))
        }
        cachedOuterBoxWithoutRotation.get(cuboid).get
    }

    def armsHorizontal(model: Model): Boolean = {
        if(!cachedArmsHorizontal.contains(model)) {
            val arms = model.getCuboidsWithTag(CuboidTypes.Tags.Arm)

            if(arms.nonEmpty) {
                for(cuboid <- arms.get) {
                    val box = getCuboidOuterBox(cuboid)

                    val diffX = Math.abs(box._1.xCoord - box._2.xCoord)
                    val diffY = Math.abs(box._1.yCoord - box._2.yCoord)
                    val diffZ = Math.abs(box._1.zCoord - box._2.zCoord)

                    cachedArmsHorizontal += (model -> (diffX > diffY * 1.5F || diffZ > diffY * 1.5F))
                }
            } else {
                cachedArmsHorizontal += (model -> false)
            }
        }
        cachedArmsHorizontal.get(model).get
    }

    def randomlyCombineModels(model1: Model, model2: Model): (Model, Model) = {
        var dominantModel: Model = null
        var recessiveModel: Model = null

        val rnd = new Random()
        val allCuboids = model1.getCuboids ++ model2.getCuboids


        var mainCuboid = allCuboids(rnd.nextInt(allCuboids.length))
        var cuboidsLeft = allCuboids.to[ListBuffer]

        //Determine dominant model and work out texture locations
        val dominant = createModelFromCuboids(mainCuboid, cuboidsLeft)

        dominantModel = dominant._1
        cuboidsLeft = dominant._2

        placeModelInCenterOfBoundingBox(dominantModel)

        //Work out recessive model and texture
        val models: ListBuffer[Model] = ListBuffer()

        if(cuboidsLeft.nonEmpty)
            mainCuboid = cuboidsLeft(rnd.nextInt(cuboidsLeft.length))

        while(cuboidsLeft.nonEmpty) {
            val recessive = createModelFromCuboids(mainCuboid, cuboidsLeft)

            placeModelInCenterOfBoundingBox(recessive._1)

            models += recessive._1
            cuboidsLeft = recessive._2

            if(cuboidsLeft.nonEmpty)
                mainCuboid = cuboidsLeft(rnd.nextInt(cuboidsLeft.length))
        }

        models.sortBy(_.getCuboids.length)

        val recessive = models.lastOption

        if(recessive.nonEmpty) {
            recessiveModel = recessive.get
        } else {
            recessiveModel = dominantModel
        }

        dominantModel.populateTexturedRectsDestination()
        recessiveModel.populateTexturedRectsDestination()

        (dominantModel, recessiveModel)
    }

    def createModelFromCuboids(mainCuboid: Cuboid, cuboids: ListBuffer[Cuboid]): (Model, ListBuffer[Cuboid]) = {
        val modelCuboids: ListBuffer[Cuboid] = ListBuffer()
        val allConnections: ListBuffer[CuboidConnection] = ListBuffer()

        var currentLevel = Array(mainCuboid)
        var nextLevel = ListBuffer[Cuboid]()
        val cuboidsLeft = cuboids

        modelCuboids += mainCuboid

        do {
            nextLevel.clear()

            for(level <- currentLevel if cuboidsLeft.nonEmpty) {
                val connections = connectAllPoints(level, cuboidsLeft.to[Array])

                for(connection <- connections) {
                    modelCuboids += connection.data2.cuboid

                    nextLevel += connection.data2.cuboid
                    allConnections += connection
                }
                cuboidsLeft -= level
            }

            currentLevel = nextLevel.to[Array]
        } while(nextLevel.nonEmpty && cuboidsLeft.nonEmpty)

        (new Model(modelCuboids.to[Array]), cuboidsLeft)
    }

    /**
      * Chooses from parts the parts best suited to connect to all the AttachmentPoints. Returns these connections.
      */
    private def connectAllPoints(cuboid: Cuboid, cuboids: Array[Cuboid]): ListBuffer[CuboidConnection] = {
        val connections: ListBuffer[CuboidConnection] = ListBuffer()
        var connectedCuboids: HashMap[CuboidAttachmentPointData, CuboidConnection] = HashMap()

        val cuboidAPD = CuboidAttachmentPointData(cuboid)
        val cuboidsAPD = cuboids.map(cuboid => CuboidAttachmentPointData(cuboid))

        for(point <- cuboid.getAttachmentPoints) {
            if(!cuboidAPD.usedConnections.contains(point)) {
                var connectionCandidates: ListBuffer[CuboidConnection] = ListBuffer()

                for(c <- cuboidsAPD) {
                    if(c.cuboid != cuboid) {
                        for(cPoint <- c.cuboid.getAttachmentPoints) {
                            if(!c.usedConnections.contains(cPoint)) {
                                for(cPointConnectable <- cPoint.getConnectableCuboidTypes) {
                                    for(cuboidPointConnectable <- point.getConnectableCuboidTypes) {
                                        val connection = CuboidConnection(cuboidAPD, point, cuboidPointConnectable, c, cPoint, cPointConnectable)

                                        if(!connectedCuboids.contains(c) || connectedCuboids(c).weight < connection.weight) {
                                            connectionCandidates += connection
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if(connectionCandidates.nonEmpty) {
                    connectionCandidates = connectionCandidates.sortBy(_.weight)

                    val last = connectionCandidates.last

                    connections += last
                    connectedCuboids += (last.data2 -> last)
                    last.data1.usedConnections += last.point1
                    last.data2.usedConnections += last.point2

                    connectCuboidTo(last.data2.cuboid, last.point2, last.data1.cuboid, last.point1)
                }
            }
        }
        connections
    }

    def connectCuboidTo(cuboidToConnect: Cuboid, pointToConnect: CuboidAttachmentPoint, cuboidToConnectTo: Cuboid, pointToConnectTo: CuboidAttachmentPoint) {
        val p1 = Vec3.createVectorHelper(cuboidToConnect.x, cuboidToConnect.y, cuboidToConnect.z)
        val p2 = Vec3.createVectorHelper(cuboidToConnectTo.x, cuboidToConnectTo.y, cuboidToConnectTo.z)

        val outerPart1 = getRotatedAndOffsetVector(cuboidToConnect, p1.addVector(pointToConnect.getLocation.xCoord, pointToConnect.getLocation.yCoord, pointToConnect.getLocation.zCoord))
        val outerPart2 = getRotatedAndOffsetVector(cuboidToConnectTo, p2.addVector(pointToConnectTo.getLocation.xCoord, pointToConnectTo.getLocation.yCoord, pointToConnectTo.getLocation.zCoord))

        val dX = outerPart2.xCoord - outerPart1.xCoord
        val dY = outerPart2.yCoord - outerPart1.yCoord
        val dZ = outerPart2.zCoord - outerPart1.zCoord

        cuboidToConnect.rotationPointX += dX.toFloat
        cuboidToConnect.rotationPointY += dY.toFloat
        cuboidToConnect.rotationPointZ += dZ.toFloat

        removeCuboidFromCache(cuboidToConnect)
    }

    def getRotatedAndOffsetVector(cuboid: Cuboid, vec: Vec3): Vec3 = {
        vec.rotateAroundX(-cuboid.rotateAngleX)
        vec.rotateAroundY(cuboid.rotateAngleY)
        vec.rotateAroundZ(-cuboid.rotateAngleZ)

        vec.addVector(cuboid.rotationPointX, cuboid.rotationPointY, cuboid.rotationPointZ)
    }

    def placeModelInCenterOfBoundingBox(model: Model): Model = {
        val allCuboids = model.getCuboids

        val min = Vec3.createVectorHelper(Double.PositiveInfinity, Double.PositiveInfinity, Double.PositiveInfinity)
        val max = Vec3.createVectorHelper(Double.NegativeInfinity, Double.NegativeInfinity, Double.NegativeInfinity)

        for(cuboid <- allCuboids) {
            val coords = getCuboidOuterBox(cuboid)

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
        }

        if(max.yCoord != 23.0F) {
            val dY = 23.0F - max.yCoord

            for(cuboid <- allCuboids) {
                cuboid.rotationPointY += dY.toFloat
            }
        }

        if((max.xCoord + min.xCoord) / 2 != 0) {
            val dX = - (max.xCoord + min.xCoord) / 2

            for(cuboid <- allCuboids) {
                cuboid.rotationPointX += dX.toFloat
            }
        }

        if((max.zCoord + min.zCoord) / 2 != 0) {
            val dZ = - (max.zCoord + min.zCoord) / 2

            for(cuboid <- allCuboids) {
                cuboid.rotationPointZ += dZ.toFloat
            }
        }

        model
    }

    def removeCuboidFromCache(cuboid: Cuboid) {
        cachedOuterBox -= cuboid
        cachedOuterBoxWithoutRotation -= cuboid
    }

    private case class CuboidConnection(data1: CuboidAttachmentPointData, point1: CuboidAttachmentPoint, type1: CuboidType, data2: CuboidAttachmentPointData, point2: CuboidAttachmentPoint, type2: CuboidType) {
        val weight = (data1.cuboid.cuboidType.similarity(type2) + data2.cuboid.cuboidType.similarity(type1)) / 2.0F
    }
    private case class CuboidAttachmentPointData(cuboid: Cuboid) {
        val usedConnections: ListBuffer[CuboidAttachmentPoint] = ListBuffer()
    }
}
