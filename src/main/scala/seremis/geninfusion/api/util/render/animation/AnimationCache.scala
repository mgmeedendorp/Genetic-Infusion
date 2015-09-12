package seremis.geninfusion.api.util.render.animation

import net.minecraft.client.model.ModelBox
import net.minecraft.util.Vec3
import seremis.geninfusion.api.soul.lib.{Genes, ModelPartTypes}
import seremis.geninfusion.api.soul.{IEntitySoulCustom, SoulHelper}
import seremis.geninfusion.api.util.render.model.{Model, ModelPart}

object AnimationCache {
    var cachedCoords: Map[(ModelPart, ModelBox), (Vec3, Vec3)] = Map()
    var cachedCoordsWithoutRotation: Map[(ModelPart, ModelBox), (Vec3, Vec3)] = Map()
    var cachedArmsHorizontal: Map[Model, Boolean] = Map()
    var cachedOuterBox: Map[ModelPart, (Vec3, Vec3)] = Map()
    var cachedWidth: Map[Model, Float] = Map()
    var cachedHeight: Map[Model, Float] = Map()
    var cachedHeadVertical: Map[Model, Boolean] = Map()

    def getModel(entity: IEntitySoulCustom): Model = {
        SoulHelper.geneRegistry.getValueFromAllele(entity, Genes.GeneModel)
    }

    def armsHorizontal(entity: IEntitySoulCustom): Boolean = armsHorizontal(getModel(entity))

    def armsHorizontal(model: Model): Boolean = {
        if(!cachedArmsHorizontal.contains(model)) {
            model.getParts(ModelPartTypes.ArmsLeft, ModelPartTypes.ArmsRight).foreach(arms => arms.foreach(part => {
                val box = getModelPartOuterBox(part)
                val diffX = Math.abs(box._1.xCoord - box._2.xCoord)
                val diffY = Math.abs(box._1.yCoord - box._2.yCoord)
                val diffZ = Math.abs(box._1.zCoord - box._2.zCoord)

                cachedArmsHorizontal += (model -> (diffX > diffY * 1.5F || diffZ > diffY * 1.5F))
            }))
        }
        cachedArmsHorizontal.get(model).get
    }

    def headVertical(model: Model): Boolean = {
        if(!cachedHeadVertical.contains(model)) {
            var highestHeadY = Float.NegativeInfinity
            var highestHeadZ = Float.NegativeInfinity
            var lowestBodyY = Float.PositiveInfinity
            var lowestBodyZ = Float.PositiveInfinity

            model.getParts(ModelPartTypes.Head).foreach(head => head.foreach(part => {
                val box = getModelPartOuterBox(part)

                highestHeadY = Math.max(box._2.yCoord, highestHeadY).toFloat
                highestHeadZ = Math.max(box._2.zCoord, highestHeadZ).toFloat
            }))

            model.getParts(ModelPartTypes.Body).foreach(body => body.foreach(part => {
                val box = getModelPartOuterBox(part)

                lowestBodyY = Math.min(box._1.yCoord, lowestBodyY).toFloat
                lowestBodyZ = Math.min(box._1.zCoord, lowestBodyZ).toFloat
            }))

            if(highestHeadY > lowestBodyY && highestHeadZ >= lowestBodyZ) {
                cachedHeadVertical += (model -> false)
            } else {
                cachedHeadVertical += (model -> true)
            }
        }
        cachedHeadVertical.get(model).get
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

    def getFromParents(parent1: Model, parent2: Model, model: Model, partName: String): Option[Array[ModelPart]] = {
        if(parent1.getParts(partName).exists(legs => legs.forall(m => model.getAllParts.contains(m)))) parent1.getParts(partName) else if(parent2.getParts(partName).exists(legs => legs.forall(m => model.getAllParts.contains(m)))) parent2.getParts(partName) else None
    }

    def attachModelPartsToBody(parent1: Model, parent2: Model, model: Model): Model = {
        val legsLeft = getFromParents(parent1, parent2, model, ModelPartTypes.LegsLeft)
        val legsRight = getFromParents(parent1, parent2, model, ModelPartTypes.LegsRight)
        val legs = legsLeft.getOrElse(new Array[ModelPart](0)) ++ legsRight.getOrElse(new Array[ModelPart](0))
        val body = getFromParents(parent1, parent2, model, ModelPartTypes.Body)
        val armsLeft = getFromParents(parent1, parent2, model, ModelPartTypes.ArmsLeft)
        val armsRight = getFromParents(parent1, parent2, model, ModelPartTypes.ArmsRight)
        val arms = armsLeft.getOrElse(new Array[ModelPart](0)) ++ armsLeft.getOrElse(new Array[ModelPart](0))
        val head = getFromParents(parent1, parent2, model, ModelPartTypes.Head)

        var highestLegY = 23.0F

        for(leg <- legs) {
            if(!intersectsPlaneY(leg, 23.0F)) {
                var outerBox = getModelPartOuterBox(leg)
                val dY = 23.0 - outerBox._2.yCoord

                leg.rotationPointY += dY.toFloat

                modelChanged(model)

                outerBox = getModelPartOuterBox(leg)

                highestLegY = Math.min(highestLegY, Math.min(outerBox._1.yCoord, outerBox._2.yCoord)).toFloat
            } else {
                highestLegY = Math.min(getModelPartOuterBox(leg)._1.yCoord, getModelPartOuterBox(leg)._2.yCoord).toFloat
            }
        }

        body.foreach(body => body.foreach(body => {
            val bodyBox = getModelPartOuterBox(body)

            val lowestYBody = Math.max(bodyBox._1.yCoord, bodyBox._2.yCoord)
            if(highestLegY != lowestYBody) {
                val dY = highestLegY - lowestYBody

                body.rotationPointY += dY.toFloat
                (armsLeft.getOrElse(new Array[ModelPart](0)) ++ armsRight.getOrElse(new Array[ModelPart](0)) ++ (if(head.nonEmpty) head.get else new Array[ModelPart](0))).foreach(m => m.rotationPointY += dY.toFloat)
            }
        }))

        if(headVertical(model)) {
            var lowestHeadY = Float.NegativeInfinity

            for(head <- head) {
                head.foreach(head => {
                    lowestHeadY = Math.max(Math.max(getModelPartOuterBox(head)._1.yCoord, getModelPartOuterBox(head)._2.yCoord), lowestHeadY).toFloat
                })
            }

            body.foreach(body => body.foreach(body => {
                val bodyBox = getModelPartOuterBox(body)

                val highestYBody = Math.min(bodyBox._1.yCoord, bodyBox._2.yCoord).toFloat

                if(lowestHeadY != highestYBody) {
                    val dy = Math.min(bodyBox._1.yCoord, bodyBox._2.yCoord).toFloat - lowestHeadY

                    head.foreach(head => head.foreach(head => head.rotationPointY += dy))
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
            }))
        } else {
            var highestHeadZ = Float.NegativeInfinity
            var highestHeadY = Float.NegativeInfinity
            var lowestHeadY = Float.PositiveInfinity

            head.foreach(head => {
                head.foreach(head => {
                    val box = getModelPartOuterBox(head)

                    highestHeadZ = Math.max(box._2.zCoord, highestHeadZ).toFloat
                    highestHeadY = Math.max(box._2.yCoord, highestHeadY).toFloat
                    lowestHeadY = Math.min(box._1.yCoord, lowestHeadY).toFloat
                })
            })

            val headSizeY = highestHeadY - lowestHeadY

            var lowestBodyZ = Float.PositiveInfinity
            var lowestBodyY = Float.PositiveInfinity
            var highestBodyY = Float.NegativeInfinity

            body.foreach(body => body.foreach(body => {
                val bodyBox = getModelPartOuterBox(body)

                lowestBodyZ = Math.min(bodyBox._1.zCoord, lowestBodyZ).toFloat
                lowestBodyY = Math.min(bodyBox._1.yCoord, lowestBodyY).toFloat
                highestBodyY = Math.max(bodyBox._2.yCoord, highestBodyY).toFloat
            }))

            val bodySizeY = highestBodyY - lowestBodyY

            var dy = highestBodyY - highestHeadY

            if(headSizeY >= bodySizeY) {
                dy += bodySizeY / 2 - headSizeY / 2
            } else {
                val dz = lowestBodyZ - highestHeadZ

                head.foreach(head => head.foreach(head => head.rotationPointZ += dz))
            }

            head.foreach(head => head.foreach(head => head.rotationPointY += dy))
        }

        modelChanged(model)

        model
    }

    def modelChanged(model: Model) {
        if(cachedArmsHorizontal.contains(model)) cachedArmsHorizontal -= model
        model.getAllParts.foreach(p => p.getBoxList.foreach(b => if(cachedCoords.contains(p -> b)) cachedCoords -= (p -> b)))
        if(cachedHeight.contains(model)) cachedHeight -= model
        model.getAllParts.foreach(p => if(cachedOuterBox.contains(p)) cachedOuterBox -= p)
        if(cachedWidth.contains(model)) cachedWidth -= model
    }
}
