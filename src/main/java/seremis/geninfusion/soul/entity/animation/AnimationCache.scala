package seremis.geninfusion.soul.entity.animation

import net.minecraft.client.model.ModelBox
import net.minecraft.util.{MathHelper, Vec3}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.api.soul.{SoulHelper, IAnimation, IEntitySoulCustom}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

abstract class Animation extends IAnimation {

    final val PI = Math.PI.asInstanceOf[Float]

    def getModel(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModel(entity)

    def getModelLegs(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelLegs(entity)

    def getModelArms(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelArms(entity)

    def getModelBody(entity: IEntitySoulCustom): ModelPart = AnimationCache.getModelBody(entity)

    def getModelHead(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelHead(entity)

    def getModelWings(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelWings(entity)
}

object AnimationCache {
    var cachedLegs: Map[IEntitySoulCustom, Array[ModelPart]] = Map()
    var cachedArms: Map[IEntitySoulCustom, Array[ModelPart]] = Map()
    var cachedBody: Map[IEntitySoulCustom, ModelPart] = Map()
    var cachedHead: Map[IEntitySoulCustom, Array[ModelPart]] = Map()
    var cachedWings: Map[IEntitySoulCustom, Array[ModelPart]] = Map()

    var cachedCoords: Map[(ModelPart, ModelBox), (Vec3, Vec3)] = Map()

    def getModel(entity: IEntitySoulCustom): Array[ModelPart] = {
        SoulHelper.geneRegistry.getValueModelPartArray(entity, Genes.GENE_MODEL)
    }

    def getModelLegs(entity: IEntitySoulCustom): Array[ModelPart] = {
        if(!cachedLegs.contains(entity)) {
            val model = getModel(entity)
            var legs: ListBuffer[ModelPart] = ListBuffer()

            for(part <- model) {
                if(intersectsPlaneY(part, 23.0F)) {
                    legs += part
                }
            }
            cachedLegs += (entity -> legs.to[Array])
        }
        cachedLegs.get(entity).get
    }

    def getModelArms(entity: IEntitySoulCustom): Array[ModelPart] = {
        if(!cachedArms.contains(entity)) {
            val model = getModel(entity)
            var arms: ListBuffer[ModelPart] = ListBuffer()

            for(part <- model) {
                if(!intersectsPlaneY(part, 23.0F) && !part.equals(getModelBody(entity))) {
                    var minX = 0.0F
                    var minY = 0.0F
                    var minZ = 0.0F
                    var maxX = 0.0F
                    var maxY = 0.0F
                    var maxZ = 0.0F

                    for(obj: Any <- part.cubeList) {
                        val box = obj.asInstanceOf[ModelBox]
                        if(Math.min(box.posX1, box.posX2) < minX)
                            minX = Math.min(box.posX1, box.posX2)
                        if(Math.max(box.posX1, box.posX2) > maxX)
                            maxX = Math.max(box.posX1, box.posX2)

                        if(Math.min(box.posY1, box.posY2) < minY)
                            minY = Math.min(box.posY1, box.posY2)
                        if(Math.max(box.posY1, box.posY2) > maxY)
                            maxY = Math.max(box.posY1, box.posY2)

                        if(Math.min(box.posZ1, box.posZ2) < minZ)
                            minZ = Math.min(box.posZ1, box.posZ2)
                        if(Math.max(box.posZ1, box.posZ2) > maxZ)
                            maxZ = Math.max(box.posZ1, box.posZ2)
                    }

                    val dx = maxX - minX
                    val dy = maxY - minY
                    val dz = maxZ - minZ

                    if(dy >= 3 * dx && dy >= 3 * dz) {
                        arms += part
                    }
                }
            }
            cachedArms += (entity -> arms.to[Array])
        }
        cachedArms.get(entity).get
    }

    def getModelBody(entity: IEntitySoulCustom): ModelPart = {
        if(!cachedBody.contains(entity)) {
            val model = getModel(entity)
            var body: ModelPart = null
            var volume = 0.0F

            for(part <- model) {
                var partVolume = 0.0F
                asScalaBuffer(part.cubeList).foreach(box => partVolume += Math.abs(box.asInstanceOf[ModelBox].posX1 - box.asInstanceOf[ModelBox].posX2) * Math.abs(box.asInstanceOf[ModelBox].posY1 - box.asInstanceOf[ModelBox].posY2) * Math.abs(box.asInstanceOf[ModelBox].posZ1 - box.asInstanceOf[ModelBox].posZ2))
                if(partVolume > volume) {
                    volume = partVolume
                    body = part
                }
            }
            cachedBody += (entity -> body)
        }
        cachedBody.get(entity).get
    }

    def getModelHead(entity: IEntitySoulCustom): Array[ModelPart] = {
        if(!cachedHead.contains(entity)) {
            val model = getModel(entity)
            var head: ListBuffer[ModelPart] = ListBuffer()

            var headCandidate: ModelPart = null
            var candidateMaxY = 23.0F
            var candidateMinY = 23.0F

            for(part <- model) {
                if(!part.equals(getModelBody(entity))) {
                    var minX = 0.0F
                    var minY = 0.0F
                    var minZ = 0.0F
                    var maxX = 0.0F
                    var maxY = 0.0F
                    var maxZ = 0.0F

                    for(obj: Any <- part.cubeList) {
                        val box = obj.asInstanceOf[ModelBox]
                        if((Math.min(box.posX1, box.posX2) + part.offsetX) * MathHelper.cos(part.rotateAngleY) < minX)
                            minX = Math.min(box.posX1, box.posX2)
                        if((Math.max(box.posX1, box.posX2) + part.offsetX) * MathHelper.cos(part.rotateAngleY) > maxX)
                            maxX = Math.max(box.posX1, box.posX2)

                        if((Math.min(box.posY1, box.posY2) + part.offsetY) * MathHelper.cos(part.rotateAngleX) < minY)
                            minY = Math.min(box.posY1, box.posY2)
                        if((Math.max(box.posY1, box.posY2) + part.offsetY) * MathHelper.cos(part.rotateAngleX) > maxY)
                            maxY = Math.max(box.posY1, box.posY2)

                        if((Math.min(box.posZ1, box.posZ2) + part.offsetZ) * MathHelper.cos(part.rotateAngleY) < minZ)
                            minZ = Math.min(box.posZ1, box.posZ2)
                        if((Math.max(box.posZ1, box.posZ2) + part.offsetZ) * MathHelper.cos(part.rotateAngleY) > maxZ)
                            maxZ = Math.max(box.posZ1, box.posZ2)
                    }

                    val dx = maxX - minX
                    val dy = maxY - minY
                    val dz = maxZ - minZ

                    if(maxY < candidateMaxY && dy < 2 * dx && dz < 2 * dy && dx <= 2 * dy) {
                        headCandidate = part
                        candidateMaxY = maxY + part.rotationPointY
                        candidateMinY = minY + part.rotationPointY
                    }
                }
            }
            head += headCandidate

            for(part <- model) {
                val nearY = part.rotationPointY + part.offsetY * MathHelper.cos(part.rotateAngleX)
                if(!part.equals(head(0)) && !getModelArms(entity).toList.contains(part) && !part.equals(getModelBody(entity)) && nearY <= candidateMaxY && partsTouching(head(0), part)) {
                    head += part
                }
            }

            cachedHead += (entity -> head.to[Array])
        }
        cachedHead.get(entity).get
    }

    def getModelWings(entity: IEntitySoulCustom): Array[ModelPart] = {
        if(!cachedWings.contains(entity)) {
            val model = getModel(entity)
            var wings: ListBuffer[ModelPart] = ListBuffer()

            for(part <- model) {
                if(!getModelHead(entity).toList.contains(part) && !getModelArms(entity).toList.contains(part) && !getModelLegs(entity).toList.contains(part) && !getModelBody(entity).equals(part) && partsTouching(part, getModelBody(entity))) {
                    wings += part
                }
            }
            cachedWings += (entity -> wings.to[Array])
        }
        cachedWings.get(entity).get
    }

    def partsTouching(part1: ModelPart, part2: ModelPart): Boolean = asScalaBuffer(part1.cubeList).exists(box1 => asScalaBuffer(part2.cubeList).exists(box2 => coordsTouch(getPartBoxCoordinates(part1, box1.asInstanceOf[ModelBox]), getPartBoxCoordinates(part2, box2.asInstanceOf[ModelBox]))))

    def coordsTouch(coords1: (Vec3, Vec3), coords2: (Vec3, Vec3)): Boolean = coords1._2.xCoord > coords2._1.xCoord && coords1._1.xCoord < coords2._2.xCoord && coords1._2.yCoord > coords2._1.yCoord && coords1._1.yCoord < coords2._2.yCoord && coords1._2.zCoord > coords2._1.zCoord && coords1._1.zCoord < coords2._2.zCoord

    def intersectsPlaneX(part: ModelPart, x: Float): Boolean = asScalaBuffer(part.cubeList).exists(box => interX(getPartBoxCoordinates(part, box.asInstanceOf[ModelBox]), x))
    def intersectsPlaneY(part: ModelPart, y: Float): Boolean = asScalaBuffer(part.cubeList).exists(box => interY(getPartBoxCoordinates(part, box.asInstanceOf[ModelBox]), y))
    def intersectsPlaneZ(part: ModelPart, z: Float): Boolean = asScalaBuffer(part.cubeList).exists(box => interZ(getPartBoxCoordinates(part, box.asInstanceOf[ModelBox]), z))

    def interX(coords: (Vec3, Vec3), x: Float) = isInBetween(x, coords._1.xCoord, coords._2.xCoord)
    def interY(coords: (Vec3, Vec3), y: Float) = isInBetween(y, coords._1.yCoord, coords._2.yCoord)
    def interZ(coords: (Vec3, Vec3), z: Float) = isInBetween(z, coords._1.zCoord, coords._2.zCoord)

    def isInBetween(value: Double, min: Double, max: Double): Boolean = value <= Math.max(min, max) && value >= Math.min(min, max)

    def getPartBoxCoordinates(part: ModelPart, box: ModelBox): (Vec3, Vec3) = {
        if(!cachedCoords.contains((part, box))) {
            val nearX = part.rotationPointX + part.offsetX * MathHelper.cos(part.rotateAngleY)
            val nearY = part.rotationPointY + part.offsetY * MathHelper.cos(part.rotateAngleX)
            val nearZ = part.rotationPointZ + part.offsetZ * MathHelper.cos(part.rotateAngleY)
            val farX = part.rotationPointX + (part.offsetX + Math.abs(box.posX2 - box.posX1)) * MathHelper.cos(part.rotateAngleY)
            val farY = part.rotationPointY + (part.offsetY + Math.abs(box.posY2 - box.posY1)) * MathHelper.cos(part.rotateAngleX)
            val farZ = part.rotationPointZ + (part.offsetZ + Math.abs(box.posZ2 - box.posZ1)) * MathHelper.cos(part.rotateAngleY)

            cachedCoords += ((part, box) -> (Vec3.createVectorHelper(nearX, nearY, nearZ), Vec3.createVectorHelper(farX, farY, farZ)))
        }
        cachedCoords.get((part, box)).get
    }
}
