package seremis.geninfusion.api.util.render.model

import net.minecraft.nbt.{NBTTagCompound, NBTTagList, NBTTagString}
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.util.INBTTagable

import scala.util.Sorting

class ModelPartType(var name: String, var tags: Option[Array[String]]) extends INBTTagable {

    //All tags are sorted alphabetically
    if(tags.nonEmpty) Sorting.quickSort(tags.get)

    def this(name: String) {
        this(name, None)
    }

    def this(compound: NBTTagCompound) {
        this(null, None)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setString("name", name)

        if(tags.nonEmpty) {
            val list = new NBTTagList

            for(tag <- tags.get) {
                list.appendTag(new NBTTagString(tag))
            }
            compound.setTag("tags", list)
        }

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        name = compound.getString("name")

        if(compound.hasKey("tags")) {
            val list = compound.getTagList("tags", Constants.NBT.TAG_STRING)
            tags = Some(new Array[String](list.tagCount()))

            for(i <- 0 until list.tagCount()) {
                tags.get(i) = list.getStringTagAt(i)
            }
        }

        compound
    }

    def calculateTagSimilarity(partType: ModelPartType): Float = {
        if(name == partType.name) {
            if(tags.nonEmpty && partType.tags.nonEmpty) {
                val tags1 = tags.get
                val tags2 = partType.tags.get

                var weight = 0.0F
                var deltaWeight = 1.0F / (tags1.length + tags2.length).toFloat

                var i1 = 0
                var i2 = 0

                while(i1 < tags1.length && i2 < tags2.length) {
                    if(tags1(i1) == tags2(i2)) {
                        weight += deltaWeight
                        i1 += 1
                        i2 += 1
                    } else if(tags1(i1) > tags2(i2)) {
                        i2 += 1
                    } else {
                        i1 += 1
                    }
                }
                return weight
            } else if(tags.isEmpty && tags.isEmpty) {
                return 1.0F
            }
        }
        0.0F
    }
}
