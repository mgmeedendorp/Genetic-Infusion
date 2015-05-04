package seremis.geninfusion.soul.entity.animation

import net.minecraft.client.model.ModelBox
import net.minecraft.util.{MathHelper, Vec3}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.util.ModelPart
import seremis.geninfusion.api.soul.{IAnimation, IEntitySoulCustom, SoulHelper}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

abstract class Animation extends IAnimation {

    final val PI = Math.PI.asInstanceOf[Float]

    def getModel(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModel(entity)

    def getModelLeftLegs(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelLeftLegs(entity)
    def getModelRightLegs(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelRightLegs(entity)
    
    def getModelLegs(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelLegs(entity)

    def getModelLeftArms(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelLeftArms(entity)
    def getModelRightArms(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelRightArms(entity)

    def getModelArms(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelArms(entity)

    def armsHorizontal(entity: IEntitySoulCustom): Boolean = AnimationCache.armsHorizontal(entity)

    def getModelBody(entity: IEntitySoulCustom): ModelPart = AnimationCache.getModelBody(entity)

    def getModelHead(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelHead(entity)

    def getModelWings(entity: IEntitySoulCustom): Array[ModelPart] = AnimationCache.getModelWings(entity)
}

object AnimationCache {
    var cachedLegsLeft: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedLegsRight: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedArmsLeft: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedArmsRight: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedBody: Map[Array[ModelPart], ModelPart] = Map()
    var cachedHead: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedWings: Map[Array[ModelPart], Array[ModelPart]] = Map()

    var cachedCoords: Map[(ModelPart, ModelBox), (Vec3, Vec3)] = Map()
    var cachedArmsHorizontal: Map[Array[ModelPart], Boolean] = Map()
    var cachedOuterBox: Map[ModelPart, (Vec3, Vec3)] = Map()
    var cachedWidth: Map[Array[ModelPart], Float] = Map()
    var cachedHeight: Map[Array[ModelPart], Float] = Map()

    def getModel(entity: IEntitySoulCustom): Array[ModelPart] = {
        SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GENE_MODEL)
    }

    def getModelLegs(model: Array[ModelPart]): Array[ModelPart] = getModelLeftLegs(model) ++ getModelRightLegs(model)
    def getModelLegs(entity: IEntitySoulCustom): Array[ModelPart] = getModelLeftLegs(entity) ++ getModelRightLegs(entity)

    def getModelRightLegs(model: Array[ModelPart]): Array[ModelPart] = getModelLegs(model, false)
    def getModelLeftLegs(model: Array[ModelPart]): Array[ModelPart] = getModelLegs(model, true)

    def getModelRightLegs(entity: IEntitySoulCustom): Array[ModelPart] = getModelLegs(entity, false)
    def getModelLeftLegs(entity: IEntitySoulCustom): Array[ModelPart] = getModelLegs(entity, true)

    def getModelLegs(entity: IEntitySoulCustom, leftLeg: Boolean): Array[ModelPart] = getModelLegs(getModel(entity), leftLeg)

    def getModelLegs(model: Array[ModelPart], leftLeg: Boolean): Array[ModelPart] = {
        if(!(cachedLegsLeft.contains(model) && leftLeg || cachedLegsRight.contains(model) && !leftLeg)) {
            var legs: ListBuffer[ModelPart] = ListBuffer()

            for(part <- model) {
                val absoluteX = part.rotationPointX + part.offsetX * MathHelper.cos(part.rotateAngleY)

                if(intersectsPlaneY(part, 22.0F) && (absoluteX > 0 && leftLeg || absoluteX < 0 && !leftLeg)) {
                    legs += part
                }
            }

            if(leftLeg)
                cachedLegsLeft += (model -> legs.to[Array])
            else
                cachedLegsRight += (model -> legs.to[Array])
        }
        if(leftLeg)
            cachedLegsLeft.get(model).get
        else
            cachedLegsRight.get(model).get
    }

    def getModelArms(entity: IEntitySoulCustom): Array[ModelPart] = getModelLeftArms(entity) ++ getModelRightArms(entity)
    def getModelArms(model: Array[ModelPart]): Array[ModelPart] = getModelLeftArms(model) ++ getModelRightArms(model)

    def getModelLeftArms(entity: IEntitySoulCustom): Array[ModelPart] = getModelArms(entity, true)
    def getModelRightArms(entity: IEntitySoulCustom): Array[ModelPart] = getModelArms(entity, false)

    def getModelLeftArms(model: Array[ModelPart]): Array[ModelPart] = getModelArms(model, true)
    def getModelRightArms(model: Array[ModelPart]): Array[ModelPart] = getModelArms(model, false)

    def getModelArms(entity: IEntitySoulCustom, leftArm: Boolean): Array[ModelPart] = getModelArms(getModel(entity), leftArm)

    def getModelArms(model: Array[ModelPart], leftArm: Boolean): Array[ModelPart] = {
        if(!(cachedArmsLeft.contains(model) && leftArm || cachedArmsRight.contains(model) && !leftArm)) {
            var arms: ListBuffer[ModelPart] = ListBuffer()

            for(part <- model) {
                if(!intersectsPlaneY(part, 23.0F) && !part.equals(getModelBody(model))) {
                    var minX = 0.0F
                    var minY = 0.0F
                    var minZ = 0.0F
                    var maxX = 0.0F
                    var maxY = 0.0F
                    var maxZ = 0.0F

                    for(box <- part.getBoxList) {
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

                    val absoluteX = part.rotationPointX + part.offsetX * MathHelper.cos(part.rotateAngleY)

                    if(dy >= 3 * dx && dy >= 3 * dz && (absoluteX > 0 && leftArm || absoluteX < 0 && !leftArm)) {
                        arms += part
                    }
                }
            }
            if(leftArm)
                cachedArmsLeft += (model -> arms.to[Array])
            else
                cachedArmsRight += (model -> arms.to[Array])
        }
        if(leftArm)
            cachedArmsLeft.get(model).get
        else
            cachedArmsRight.get(model).get
    }

    def getModelBody(entity: IEntitySoulCustom): ModelPart = getModelBody(getModel(entity))

    def getModelBody(model: Array[ModelPart]): ModelPart = {
        if(!cachedBody.contains(model)) {
            var body: ModelPart = null
            var volume = 0.0F

            for(part <- model) {
                val lowestPartY = Math.max(getModelPartOuterBox(part)._1.yCoord, getModelPartOuterBox(part)._2.yCoord).toFloat + 0.001F
                if(getModelLegs(model) != null && getModelLegs(model).exists(leg => intersectsPlaneY(leg, lowestPartY))) {
                    var partVolume = 0.0D

                    part.getBoxList.foreach(box => partVolume += Math.abs(box.posX1 - box.posX2) * Math.abs(box.posY1 - box.posY2) * Math.abs(box.posZ1 - box.posZ2))

                    if(partVolume > volume) {
                        volume = partVolume.toFloat
                        body = part
                    }
                }
            }
            cachedBody += (model -> body)
        }
        cachedBody.get(model).get
    }

    def getModelHead(entity: IEntitySoulCustom): Array[ModelPart] = getModelHead(getModel(entity))

    def getModelHead(model: Array[ModelPart]): Array[ModelPart] = {
        if(!cachedHead.contains(model)) {
            var head: ListBuffer[ModelPart] = ListBuffer()

            var headCandidate: ModelPart = null
            var candidateMaxY = 23.0F
            var candidateMinY = 23.0F

            for(part <- model) {
                if(getModelBody(model) != part) {
                    var minX = 0.0F
                    var minY = 0.0F
                    var minZ = 0.0F
                    var maxX = 0.0F
                    var maxY = 0.0F
                    var maxZ = 0.0F

                    for(box <- part.getBoxList) {
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
                val nearY = part.rotationPointY + part.offsetY * MathHelper.cos(part.rotateAngleX + 0.001F)
                if(!part.equals(head.head) && !getModelArms(model).toList.contains(part) && !part.equals(getModelBody(model)) && nearY <= candidateMaxY && partsTouching(head.head, part)) {
                    head += part
                }
            }

            cachedHead += (model -> head.to[Array])
        }
        cachedHead.get(model).get
    }

    def getModelWings(entity: IEntitySoulCustom): Array[ModelPart] = getModelWings(getModel(entity))

    def getModelWings(model: Array[ModelPart]): Array[ModelPart] = {
        if(!cachedWings.contains(model)) {
            var wings: ListBuffer[ModelPart] = ListBuffer()

            for(part <- model) {
                if(getModelHead(model) != null && !getModelHead(model).toList.contains(part) && getModelArms(model) != null && !getModelArms(model).toList.contains(part) && getModelLegs(model) != null && !getModelLegs(model).toList.contains(part) && getModelBody(model) != null && !getModelBody(model).equals(part) && partsTouching(part, getModelBody(model))) {
                    wings += part
                }
            }
            cachedWings += (model -> wings.to[Array])
        }
        cachedWings.get(model).get
    }

    def armsHorizontal(entity: IEntitySoulCustom): Boolean = armsHorizontal(getModel(entity))

    def armsHorizontal(model: Array[ModelPart]): Boolean = {
        if(!cachedArmsHorizontal.contains(model)) {
            getModelArms(model).foreach(part => {
                val box = getModelPartOuterBox(part)
                val diffX = Math.abs(box._1.xCoord - box._2.xCoord)
                val diffY = Math.abs(box._1.yCoord - box._2.yCoord)
                val diffZ = Math.abs(box._1.zCoord - box._2.zCoord)

                cachedArmsHorizontal += (model -> (diffX > diffY * 1.5F || diffZ > diffY * 1.5F))
            })
        }
        cachedArmsHorizontal.get(model).get
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

    def isPartUnder(lowerPart: ModelPart, higherPart: ModelPart): Boolean = {
        lowerPart.getBoxList.exists(lowerBox => {
            higherPart.getBoxList.exists(higherBox => {
                coordsBeneath(getPartBoxCoordinates(lowerPart, lowerBox), getPartBoxCoordinates(higherPart, higherBox))
            })
        })
    }

    def partsTouching(part1: ModelPart, part2: ModelPart): Boolean = {
        part1.getBoxList.exists(box1 => {
            part2.getBoxList.exists(box2 => {
                coordsTouch(getPartBoxCoordinates(part1, box1), getPartBoxCoordinates(part2, box2))
            })
        })
    }

    def coordsBeneath(lowerCoords: (Vec3, Vec3), higherCoords: (Vec3, Vec3)): Boolean = getHighestCoordY(lowerCoords) >= getLowestCoordY(higherCoords)

    def getHighestCoordY(coords: (Vec3, Vec3)): Float = Math.min(coords._1.yCoord, coords._2.yCoord).toFloat

    def getLowestCoordY(coords: (Vec3, Vec3)): Float = Math.max(coords._1.yCoord, coords._2.yCoord).toFloat

    def coordsTouch(coords1: (Vec3, Vec3), coords2: (Vec3, Vec3)): Boolean = coords1._2.xCoord >= coords2._1.xCoord && coords1._1.xCoord <= coords2._2.xCoord && coords1._2.yCoord >= coords2._1.yCoord && coords1._1.yCoord <= coords2._2.yCoord && coords1._2.zCoord >= coords2._1.zCoord && coords1._1.zCoord <= coords2._2.zCoord

    def intersectsPlaneX(part: ModelPart, x: Float): Boolean = part.getBoxList.exists(box => interX(getPartBoxCoordinates(part, box), x))

    def intersectsPlaneY(part: ModelPart, y: Float): Boolean = part.getBoxList.exists(box => interY(getPartBoxCoordinates(part, box), y))

    def intersectsPlaneZ(part: ModelPart, z: Float): Boolean = part.getBoxList.exists(box => interZ(getPartBoxCoordinates(part, box), z))

    def interX(coords: (Vec3, Vec3), x: Float) = isInBetween(x, coords._1.xCoord, coords._2.xCoord)

    def interY(coords: (Vec3, Vec3), y: Float) = isInBetween(y, coords._1.yCoord, coords._2.yCoord)

    def interZ(coords: (Vec3, Vec3), z: Float) = isInBetween(z, coords._1.zCoord, coords._2.zCoord)

    def isInBetween(value: Double, min: Double, max: Double): Boolean = value <= Math.max(min, max) && value >= Math.min(min, max)

    def getPartBoxCoordinates(part: ModelPart, box: ModelBox): (Vec3, Vec3) = {
        if(!cachedCoords.contains((part, box))) {
            val x1 = part.rotationPointX + (part.offsetX + Math.min(box.posX2, box.posX1)) * MathHelper.cos(part.rotateAngleY)
            val y1 = part.rotationPointY + (part.offsetX + Math.min(box.posY2, box.posY1)) * MathHelper.cos(part.rotateAngleX)
            val z1 = part.rotationPointZ + (part.offsetX + Math.min(box.posZ2, box.posZ1)) * MathHelper.cos(part.rotateAngleY)
            val x2 = part.rotationPointX + (part.offsetX + Math.max(box.posX2, box.posX1)) * MathHelper.cos(part.rotateAngleY)
            val y2 = part.rotationPointY + (part.offsetY + Math.max(box.posY2, box.posY1)) * MathHelper.cos(part.rotateAngleX)
            val z2 = part.rotationPointZ + (part.offsetZ + Math.max(box.posZ2, box.posZ1)) * MathHelper.cos(part.rotateAngleY)

            val nearX = Math.min(x1, x2)
            val nearY = Math.min(y1, y2)
            val nearZ = Math.min(z1, z2)
            val farX = Math.max(x1, x2)
            val farY = Math.max(y1, y2)
            val farZ = Math.max(z1, z2)

            cachedCoords += ((part, box) ->(Vec3.createVectorHelper(nearX, nearY, nearZ), Vec3.createVectorHelper(farX, farY, farZ)))
        }
        cachedCoords.get((part, box)).get
    }

    def getModelWidth(entity: IEntitySoulCustom): Float = getModelWidth(getModel(entity))

    def getModelWidth(model: Array[ModelPart]): Float = {
        if(!cachedWidth.contains(model)) {
            var minX, minZ = Float.PositiveInfinity
            var maxX, maxZ = Float.NegativeInfinity

            for(part <- model) {
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

    def getModelHeight(model: Array[ModelPart]): Float = {
        if(!cachedHeight.contains(model)) {
            var minY = Float.PositiveInfinity
            var maxY = Float.NegativeInfinity

            for(part <- model) {
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

    def attachModelPartsToBody(parent1: Array[ModelPart], parent2: Array[ModelPart], model: Array[ModelPart]): Array[ModelPart] = {
        val legsLeft = if(getModelLeftLegs(parent1).forall(m => model.contains(m))) getModelLeftLegs(parent1) else if(getModelLeftLegs(parent2).forall(m => model.contains(m))) getModelLeftLegs(parent2) else null
        val legsRight = if(getModelRightLegs(parent1).forall(m => model.contains(m))) getModelRightLegs(parent1) else if(getModelRightLegs(parent2).forall(m => model.contains(m))) getModelRightLegs(parent2) else null
        val body = if(model.contains(getModelBody(parent1))) getModelBody(parent1) else if(model.contains(getModelBody(parent2))) getModelBody(parent2) else null
        val armsLeft = if(getModelLeftArms(parent1).forall(m => model.contains(m))) getModelLeftArms(parent1) else if(getModelLeftArms(parent2).forall(m => model.contains(m))) getModelLeftArms(parent2) else null
        val armsRight = if(getModelRightArms(parent1).forall(m => model.contains(m))) getModelRightArms(parent1) else if(getModelRightArms(parent2).forall(m => model.contains(m))) getModelRightArms(parent2) else null
        val head = if(getModelHead(parent1).forall(m => model.contains(m))) getModelHead(parent1) else if(getModelHead(parent2).forall(m => model.contains(m))) getModelHead(parent2) else null

        var highestLegY = 23.0F

        for(leg <- legsLeft ++ legsRight) {
            highestLegY = Math.min(getModelPartOuterBox(leg)._1.yCoord, getModelPartOuterBox(leg)._2.yCoord).toFloat
            if(!intersectsPlaneY(leg, 23.0F)) {
                var outerBox = getModelPartOuterBox(leg)
                val dY = 23.0 - Math.max(outerBox._1.yCoord, outerBox._2.yCoord)

                leg.rotationPointY += dY.toFloat

                cachedOuterBox -= leg
                leg.getBoxList.foreach(box => cachedCoords -= (leg -> box))
                outerBox = getModelPartOuterBox(leg)

                highestLegY = Math.min(highestLegY, Math.min(outerBox._1.yCoord, outerBox._2.yCoord)).toFloat
            }
        }

        val bodyBox = getModelPartOuterBox(body)

        println(bodyBox)
        println(Math.max(bodyBox._1.yCoord, bodyBox._2.yCoord))
        println(highestLegY)
        println((legsLeft ++ legsRight).length)

        val lowestYBody = Math.max(bodyBox._1.yCoord, bodyBox._2.yCoord)
        if(highestLegY != lowestYBody) {
            val dY = highestLegY - lowestYBody

            body.rotationPointY += dY.toFloat
            (armsLeft ++ armsRight ++ head).foreach(m => m.rotationPointY += dY.toFloat)
        }

        modelChanged(model)

        body.rotateAngleZ = (Math.PI/2/2).toFloat
        head.foreach(h => h.rotateAngleY = (Math.PI/4).toFloat)

        model
    }

    def modelChanged(model: Array[ModelPart]) {
        if(cachedArmsHorizontal.contains(model)) cachedArmsHorizontal -= model
        if(cachedArmsLeft.contains(model)) cachedArmsLeft -= model
        if(cachedArmsRight.contains(model)) cachedArmsRight -= model
        if(cachedBody.contains(model)) cachedBody -= model
        model.foreach(p => p.getBoxList.foreach(b => if(cachedCoords.contains(p -> b)) cachedCoords -= (p -> b)))
        if(cachedHead.contains(model)) cachedHead -= model
        if(cachedHeight.contains(model)) cachedHeight -= model
        if(cachedLegsLeft.contains(model)) cachedLegsLeft -= model
        if(cachedLegsRight.contains(model)) cachedLegsRight -= model
        model.foreach(p => if(cachedOuterBox.contains(p)) cachedOuterBox -= p)
        if(cachedWidth.contains(model)) cachedWidth -= model
        if(cachedWings.contains(model)) cachedWings -= model
    }
}
