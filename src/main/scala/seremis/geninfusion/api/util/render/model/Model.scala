package seremis.geninfusion.api.util.render.model

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.util.INBTTagable

import scala.collection.immutable.HashMap
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Model() extends INBTTagable {

    var partsMap: HashMap[String, Array[ModelPart]] = HashMap()

    var connectedToMap: HashMap[String, Array[ModelPart]] = HashMap()

    def this(modelParts: Array[ModelPart]*) {
        this()
        modelParts.foreach(parts => addModelParts(parts))
    }

    def this(compound: NBTTagCompound) {
        this()
        readFromNBT(compound)
    }

    def addPart(modelPart: ModelPart) {
        addModelParts(Array(modelPart))
    }

    def addModelParts(modelParts: Array[ModelPart]) {
        for(part <- modelParts) {
            part.attachmentPoints.foreach(point => point.getConnectableModelPartTypes.foreach(partType => {
                if(connectedToMap.contains(partType.name)) {
                    val parts = connectedToMap.get(partType.name).get

                    val list = parts.to[ListBuffer] += part

                    connectedToMap += (partType.name -> list.to[Array])
                } else {
                    connectedToMap += (partType.name -> Array(part))
                }
            }))

            if(partsMap.contains(part.modelPartType.name)) {
                val parts = partsMap.get(part.modelPartType.name).get

                val list = parts.to[ListBuffer] += part

                partsMap += (part.modelPartType.name -> list.to[Array])
            } else {
                partsMap += (part.modelPartType.name -> Array(part))
            }
        }
    }
    
    def getParts(modelPartTypeName: String*): Option[Array[ModelPart]] = {
        val list: ListBuffer[ModelPart] = ListBuffer()

        modelPartTypeName.foreach(partType => {
            partsMap.get(partType).foreach(array => {
                array.foreach(element => {
                    list += element
                })
            })
        })

        if(list.nonEmpty)
            Some(list.to[Array])
        else
            None
    }

    def getPartsWithTag(modelPartTypeName: String, tags: String*): Option[Array[ModelPart]] = {
        val list: ListBuffer[ModelPart] = ListBuffer()

        partsMap.get(modelPartTypeName).foreach(array => {
            array.foreach(element => {
                if(element.modelPartType.tags.nonEmpty && tags.forall(tag => element.modelPartType.tags.get.contains(tag))) {
                    list += element
                }
            })
        })

        if(list.nonEmpty)
            Some(list.to[Array])
        else
            None
    }

    def getPartsThatConnectTo(modelPartTypeName: String*): Option[Array[ModelPart]] = {
        val list: ListBuffer[ModelPart] = ListBuffer()

        modelPartTypeName.foreach(partType => {
            connectedToMap.get(partType).foreach(array => {
                array.foreach(element => {
                    list += element
                })
            })
        })

        if(list.nonEmpty)
            Some(list.to[Array])
        else
            None
    }

    def attachmentPointsCanAttach(point1: ModelPartAttachmentPoint, point2: ModelPartAttachmentPoint): Boolean = {
        point1.pointPartTypes.forall(partType1 => point2.getConnectableModelPartTypeNames.contains(partType1.name)) && point2.pointPartTypes.forall(partType2 => point1.getConnectableModelPartTypeNames.contains(partType2.name))
    }

    def partsConnectWeight(part1: ModelPart, part2: ModelPart): (Float, Option[ModelPartAttachmentPoint], Option[ModelPartAttachmentPoint]) = {
        var weight = 0.0F
        var point1: Option[ModelPartAttachmentPoint] = None
        var point2: Option[ModelPartAttachmentPoint] = None

        var index1 = 0
        var index2 = 0
        val totalLength = part1.getAttachmentPoints.length + part2.getAttachmentPoints.length

        for(p1 <- part1.getAttachmentPoints) {
            for(p2 <- part2.getAttachmentPoints) {
                val tmpWeight = attachmentPointsConnectWeight(p1, part1.modelPartType, p2, part2.modelPartType) * (1.0F - Math.max(index1 + index2, 1) / totalLength)

                if(tmpWeight > weight) {
                    weight = tmpWeight
                    point1 = Some(p1)
                    point2 = Some(p2)
                }

                if(weight == 1.0F)
                    return (weight, point1, point2)

                index2 += 1
            }
            index1 += 1
            index2 = 0
        }


        (weight, point1, point2)
    }

    def attachmentPointsConnectWeight(point1: ModelPartAttachmentPoint, pType1: ModelPartType, point2: ModelPartAttachmentPoint, pType2: ModelPartType): Float = {
        var finalWeight = 0.0F

        if(point2.getConnectableModelPartTypeNames.contains(pType1.name) && point1.getConnectableModelPartTypeNames.contains(pType2.name)) {
            finalWeight = 0.1F

            var index1 = 0
            var index2 = 0
            for(p1 <- point1.getConnectableModelPartTypes) {
                for(p2 <- point2.getConnectableModelPartTypes) {
                    var weight = 0.0F
                    if(pType1.tags.isEmpty && p2.tags.isEmpty && pType2.tags.isEmpty && p1.tags.isEmpty) {
                        return 1.0F
                    } else {
                        val arr1 = pType1.tags.getOrElse(new Array[String](0)) ++ p1.tags.getOrElse(new Array[String](0))
                        val arr2 = pType2.tags.getOrElse(new Array[String](0)) ++ p2.tags.getOrElse(new Array[String](0))
                        var i1 = 0
                        var i2 = 0
                        var weightModifier = 1.0F / Math.min(arr1.length, arr2.length).toFloat

                        while(i1 != arr1.length && i2 != arr2.length) {
                            if(arr1(i1) == arr2(i2)) {
                                weight += weightModifier

                                i1 += 1
                                i2 += 1
                            } else if(arr1(i1) < arr2(i2)) {
                                i1 += 1
                            } else if(arr1(i1) > arr2(i2)) {
                                i2 += 1
                            }
                        }

                        finalWeight = Math.max(finalWeight, weight)
                    }
                    index2 += 1
                }
                index1 += 1
                index2 = 0
            }
        }

        finalWeight
    }

    val resemblingPartCache: mutable.HashMap[ModelPart, Option[ModelPart]] = mutable.HashMap()

    def getMostResemblingPart(part: ModelPart): Option[ModelPart] = {
        if(!resemblingPartCache.contains(part)) {
            var result: Option[ModelPart] = None
            var resultTagsSame = -1

            val tags = part.modelPartType.tags.getOrElse(new Array[ModelPart](0))
            val parts = getParts(part.modelPartType.name).getOrElse(new Array[ModelPart](0))

            for(prt <- parts) {
                var tagsSame = 0

                if(prt.modelPartType.tags.nonEmpty) {
                    for(tag <- prt.modelPartType.tags.get) {
                        if(tags.contains(tag))
                            tagsSame += 1
                    }
                }

                if(tagsSame > resultTagsSame) {
                    resultTagsSame = tagsSame
                    result = Some(prt)
                }
            }

            resemblingPartCache += (part -> result)
        }
        resemblingPartCache.get(part).get
    }

    def getAllParts: Array[ModelPart] = {
        val parts: ListBuffer[ModelPart] = ListBuffer()

        partsMap.values.foreach(array => array.foreach(part => parts += part))

        parts.to[Array]
    }

    def getWholeModelExcept(except: Array[ModelPart]): Model = {
        val parts: ListBuffer[ModelPart] = ListBuffer()

        partsMap.values.foreach(array => array.foreach(part => if(!except.contains(part)) parts += part))

        new Model(parts.to[Array])
    }

    def getWholeModelExcept(except: ModelPart): Model = {
        getWholeModelExcept(Array(except))
    }

    def render(scale: Float) {
        getAllParts.foreach(part => part.render(scale))
    }

    def render() {
        render(0.0625F)
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        val list = new NBTTagList

        for(partArray <- partsMap.values) {
            for(part <- partArray) {
                list.appendTag(part.writeToNBT(new NBTTagCompound))
            }
        }
        compound.setTag("parts", list)

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        val list = compound.getTag("parts").asInstanceOf[NBTTagList]
        val parts: ListBuffer[ModelPart] = ListBuffer()

        for(i <- 0 until list.tagCount()) {
            val compound1 = list.getCompoundTagAt(i)

            parts += ModelPart.fromNBT(compound1)
        }

        addModelParts(parts.to[Array])

        compound
    }

    def copy(): Model = {
        val model = new Model()

        partsMap.foreach(a => a._2.foreach(part => {
            model.addPart(part.copy())
        }))
        model
    }
}
