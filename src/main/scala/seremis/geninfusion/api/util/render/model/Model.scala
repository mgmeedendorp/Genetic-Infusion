package seremis.geninfusion.api.util.render.model

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import seremis.geninfusion.api.soul.{IModelPartType, SoulHelper}
import seremis.geninfusion.util.INBTTagable

import scala.collection.immutable.HashMap
import scala.collection.mutable.ListBuffer

object Model {
    def fromNBT(compound: NBTTagCompound): Model = {
        val model = new Model
        model.readFromNBT(compound)
        model
    }
}

class Model extends INBTTagable {

    var partsMap: HashMap[IModelPartType, Array[ModelPart]] = HashMap()

    def this(modelParts: Array[ModelPart]) {
        this()
        addModelParts(modelParts)
    }

    def addPart(modelPart: ModelPart) {
        addModelParts(Array(modelPart))
    }

    def addModelParts(modelParts: Array[ModelPart]) {
        for(part <- modelParts) {
            if(partsMap.contains(part.modelPartType)) {
                val parts = partsMap.get(part.modelPartType).get

                val list = parts.to[ListBuffer] += part

                partsMap += (part.modelPartType -> list.to[Array])
            } else {
                partsMap += (part.modelPartType -> Array(part))
            }
        }
    }
    
    def getParts(modelPartType: String*): Option[Array[ModelPart]] = {
        val list: ListBuffer[ModelPart] = ListBuffer()

        modelPartType.foreach(partType => {
            SoulHelper.modelPartTypeRegistry.getModelPartType(partType).foreach(instance => {
                partsMap.get(instance).foreach(array => {
                    array.foreach(element => {
                        list += element
                    })
                })
            })
        })

        if(list.nonEmpty)
            Some(list.to[Array])
        else
            None
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
}
