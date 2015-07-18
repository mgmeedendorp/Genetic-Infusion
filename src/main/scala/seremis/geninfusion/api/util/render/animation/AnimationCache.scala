package seremis.geninfusion.api.util.render.animation

import net.minecraft.client.model.ModelBox
import net.minecraft.util.{MathHelper, Vec3}
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.render.model.ModelPart

import scala.collection.mutable.ListBuffer

object AnimationCache {
    var cachedLegsLeft: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedLegsRight: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedArmsLeft: Map[Array[ModelPart], Option[Array[ModelPart]]] = Map()
    var cachedArmsRight: Map[Array[ModelPart], Option[Array[ModelPart]]] = Map()
    var cachedWingsLeft: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedWingsRight: Map[Array[ModelPart], Array[ModelPart]] = Map()
    var cachedBody: Map[Array[ModelPart], ModelPart] = Map()
    var cachedHead: Map[Array[ModelPart], Array[ModelPart]] = Map()

    var cachedCoords: Map[(ModelPart, ModelBox), (Vec3, Vec3)] = Map()
    var cachedArmsHorizontal: Map[Array[ModelPart], Boolean] = Map()
    var cachedOuterBox: Map[ModelPart, (Vec3, Vec3)] = Map()
    var cachedWidth: Map[Array[ModelPart], Float] = Map()
    var cachedHeight: Map[Array[ModelPart], Float] = Map()

    def getModel(entity: IEntitySoulCustom): Array[ModelPart] = {
        SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneModel)
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
                val outerBox = getModelPartOuterBox(part)
                val outerX = if(Math.max(Math.abs(outerBox._1.xCoord), Math.abs(outerBox._2.xCoord)) == Math.abs(outerBox._1.xCoord)) outerBox._1.xCoord else outerBox._2.xCoord

                if(intersectsPlaneY(part, 22.0F) && (outerX > 0 && leftLeg || outerX < 0 && !leftLeg)) {
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

    def getModelArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = if(getModelLeftArms(entity).nonEmpty && getModelRightArms(entity).nonEmpty) Some(getModelLeftArms(entity).get ++ getModelRightArms(entity).get) else if(getModelLeftArms(entity).nonEmpty) getModelLeftArms(entity) else if(getModelRightArms(entity).nonEmpty) getModelRightArms(entity) else None
    def getModelArms(model: Array[ModelPart]): Option[Array[ModelPart]] = if(getModelLeftArms(model).nonEmpty && getModelRightArms(model).nonEmpty) Some(getModelLeftArms(model).get ++ getModelRightArms(model).get) else if(getModelLeftArms(model).nonEmpty) getModelLeftArms(model) else if(getModelRightArms(model).nonEmpty) getModelRightArms(model) else None

    def getModelLeftArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModelArms(entity, true)
    def getModelRightArms(entity: IEntitySoulCustom): Option[Array[ModelPart]] = getModelArms(entity, false)

    def getModelLeftArms(model: Array[ModelPart]): Option[Array[ModelPart]] = getModelArms(model, true)
    def getModelRightArms(model: Array[ModelPart]): Option[Array[ModelPart]] = getModelArms(model, false)

    def getModelArms(entity: IEntitySoulCustom, leftArm: Boolean): Option[Array[ModelPart]] = getModelArms(getModel(entity), leftArm)

    def getModelArms(model: Array[ModelPart], leftArm: Boolean): Option[Array[ModelPart]] = {
        if(!(cachedArmsLeft.contains(model) && leftArm || cachedArmsRight.contains(model) && !leftArm)) {
            var arms: ListBuffer[ModelPart] = ListBuffer()

            for(part <- model) {
                if(!intersectsPlaneY(part, 23.0F) && !part.equals(getModelBody(model))) {
                    var minX = Float.PositiveInfinity
                    var minY = Float.PositiveInfinity
                    var minZ = Float.PositiveInfinity
                    var maxX = Float.NegativeInfinity
                    var maxY = Float.NegativeInfinity
                    var maxZ = Float.NegativeInfinity

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

                    val absoluteX = part.rotationPointX + minX + part.offsetX * MathHelper.cos(part.rotateAngleY)

                    if(dy >= 3 * dx && dy >= 3 * dz && (absoluteX > 0 && leftArm || absoluteX < 0 && !leftArm)) {
                        arms += part
                    }
                }
            }
            if(leftArm)
                cachedArmsLeft += (model -> (if(arms.nonEmpty) Some(arms.to[Array]) else None))
            else
                cachedArmsRight += (model -> (if(arms.nonEmpty) Some(arms.to[Array]) else None))
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
                val lowestPartY = Math.max(getModelPartOuterBox(part)._1.yCoord, getModelPartOuterBox(part)._2.yCoord).toFloat
                if(getModelLegs(model) == null || getModelLegs(model).exists(leg => intersectsPlaneY(leg, lowestPartY + 1F))) {
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
                    var minX = Float.PositiveInfinity
                    var minY = Float.PositiveInfinity
                    var minZ = Float.PositiveInfinity
                    var maxX = Float.NegativeInfinity
                    var maxY = Float.NegativeInfinity
                    var maxZ = Float.NegativeInfinity

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
                if(!part.equals(head.head) && !getModelArms(model).exists(arms => arms.toList.contains(part)) && !part.equals(getModelBody(model)) && nearY <= candidateMaxY && partsTouching(head.head, part)) {
                    head += part
                }
            }

            cachedHead += (model -> head.to[Array])
        }
        cachedHead.get(model).get
    }

    def getModelWings(entity: IEntitySoulCustom): Array[ModelPart] = getModelLeftWings(entity) ++ getModelRightWings(entity)
    def getModelWings(model: Array[ModelPart]): Array[ModelPart] = getModelLeftWings(model) ++ getModelRightWings(model)

    def getModelLeftWings(entity: IEntitySoulCustom): Array[ModelPart] = getModelWings(entity, true)
    def getModelRightWings(entity: IEntitySoulCustom): Array[ModelPart] = getModelWings(entity, false)

    def getModelLeftWings(model: Array[ModelPart]): Array[ModelPart] = getModelWings(model, true)
    def getModelRightWings(model: Array[ModelPart]): Array[ModelPart] = getModelWings(model, false)

    def getModelWings(entity: IEntitySoulCustom, leftWings: Boolean): Array[ModelPart] = getModelWings(getModel(entity), leftWings)

    def getModelWings(model: Array[ModelPart], leftWings: Boolean): Array[ModelPart] = {
        if(!(cachedWingsLeft.contains(model) && leftWings || cachedWingsRight.contains(model) && !leftWings)) {
            var wings: ListBuffer[ModelPart] = ListBuffer()

            for(part <- model) {
                if(getModelHead(model) != null && !getModelHead(model).toList.contains(part) && getModelArms(model) != null && !getModelArms(model).exists(arms => arms.toList.contains(part)) && getModelLegs(model) != null && !getModelLegs(model).toList.contains(part) && getModelBody(model) != null && !getModelBody(model).equals(part) && partsTouching(part, getModelBody(model))) {
                    val absoluteX = part.rotationPointX + part.offsetX * MathHelper.cos(part.rotateAngleY)

                    if(absoluteX > 0 && leftWings || absoluteX < 0 && !leftWings)
                        wings += part
                }
            }
            
            if(leftWings)
                cachedWingsLeft += (model -> wings.to[Array])
            else
                cachedWingsRight += (model -> wings.to[Array])
        }
        if(leftWings)
            cachedWingsLeft.get(model).get
        else
            cachedWingsRight.get(model).get
    }

    def armsHorizontal(entity: IEntitySoulCustom): Boolean = armsHorizontal(getModel(entity))

    def armsHorizontal(model: Array[ModelPart]): Boolean = {
        if(!cachedArmsHorizontal.contains(model)) {
            getModelArms(model).foreach(arms => arms.foreach(part => {
                val box = getModelPartOuterBox(part)
                val diffX = Math.abs(box._1.xCoord - box._2.xCoord)
                val diffY = Math.abs(box._1.yCoord - box._2.yCoord)
                val diffZ = Math.abs(box._1.zCoord - box._2.zCoord)

                cachedArmsHorizontal += (model -> (diffX > diffY * 1.5F || diffZ > diffY * 1.5F))
            }))
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
            var pos1 = Vec3.createVectorHelper(part.offsetX + Math.min(box.posX2, box.posX1), part.offsetY + Math.min(box.posY2, box.posY1), part.offsetZ + Math.min(box.posZ2, box.posZ1))
            var pos2 = Vec3.createVectorHelper(part.offsetX + Math.max(box.posX2, box.posX1), part.offsetY + Math.max(box.posY2, box.posY1), part.offsetZ + Math.max(box.posZ2, box.posZ1))

            pos1.rotateAroundX(part.rotateAngleX)
            pos1.rotateAroundY(part.rotateAngleY)
            pos1.rotateAroundZ(part.rotateAngleZ)
            pos2.rotateAroundX(part.rotateAngleX)
            pos2.rotateAroundY(part.rotateAngleY)
            pos2.rotateAroundZ(part.rotateAngleZ)

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
        val armsLeft = if(getModelLeftArms(parent1).exists(arms => arms.forall(m => model.contains(m)))) getModelLeftArms(parent1) else if(getModelLeftArms(parent2).exists(arms => arms.forall(m => model.contains(m)))) getModelLeftArms(parent2) else None
        val armsRight = if(getModelRightArms(parent1).exists(arms => arms.forall(m => model.contains(m)))) getModelRightArms(parent1) else if(getModelRightArms(parent2).exists(arms => arms.forall(m => model.contains(m)))) getModelRightArms(parent2) else None
        val heads = if(getModelHead(parent1).forall(m => model.contains(m))) getModelHead(parent1) else if(getModelHead(parent2).forall(m => model.contains(m))) getModelHead(parent2) else null


        println(legsLeft + " " + legsRight + " " + body + " " + armsLeft + " " + armsRight + " " + heads)

        var highestLegY = 23.0F

        for(leg <- legsLeft ++ legsRight) {
            highestLegY = Math.min(getModelPartOuterBox(leg)._1.yCoord, getModelPartOuterBox(leg)._2.yCoord).toFloat
            if(!intersectsPlaneY(leg, 23.0F)) {
                var outerBox = getModelPartOuterBox(leg)
                val dY = 23.0 - Math.max(outerBox._1.yCoord, outerBox._2.yCoord)

                leg.rotationPointY += dY.toFloat

                cachedOuterBox -= leg
                leg.getBoxList.foreach(box => cachedCoords -= (leg -> box))

                modelChanged(model)

                outerBox = getModelPartOuterBox(leg)

                highestLegY = Math.min(highestLegY, Math.min(outerBox._1.yCoord, outerBox._2.yCoord)).toFloat
            }
        }

        val bodyBox = getModelPartOuterBox(body)

        val lowestYBody = Math.max(bodyBox._1.yCoord, bodyBox._2.yCoord)
        if(highestLegY != lowestYBody) {
            val dY = highestLegY - lowestYBody

            body.rotationPointY += dY.toFloat
            (armsLeft.getOrElse(new Array[ModelPart](0)) ++ armsRight.getOrElse(new Array[ModelPart](0)) ++ (if(heads != null) heads else new Array[ModelPart](0))).foreach(m => m.rotationPointY += dY.toFloat)
        }

        var lowestHeadY = Float.NegativeInfinity

        for(head <- heads) {
            lowestHeadY = Math.max(Math.max(getModelPartOuterBox(head)._1.yCoord, getModelPartOuterBox(head)._2.yCoord), lowestHeadY).toFloat
        }

        val highestYBody = Math.min(bodyBox._1.yCoord, bodyBox._2.yCoord).toFloat

        if(lowestHeadY != highestYBody) {
            val dy = Math.min(bodyBox._1.yCoord, bodyBox._2.yCoord).toFloat - lowestHeadY

            heads.foreach(head => head.rotationPointY += dy)
        }

        if(armsLeft != null && armsRight != null && armsLeft.exists(arms => arms.length == 1) && armsRight.exists(arms => arms.length == 1)) {
            for(arm <- armsLeft.getOrElse(new Array[ModelPart](0)) ++ armsRight.getOrElse(new Array[ModelPart](0))) {
                val armBox = getModelPartOuterBox(arm)
                val armTop = Math.min(armBox._1.yCoord, armBox._2.yCoord).toFloat

                if(armTop != highestYBody) {
                    val dy = highestYBody - armTop

                    arm.rotationPointY += dy
                }
            }
        }

        modelChanged(model)

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
        if(cachedWingsRight.contains(model)) cachedWingsRight -= model
        if(cachedWingsLeft.contains(model)) cachedWingsLeft -= model
    }
}
