package seremis.geninfusion.api.util.render.model

import net.minecraft.nbt.{NBTTagCompound, NBTTagList, NBTTagString}
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.util.INBTTagable

class ModelPartType(var name: String, var tags: Option[Array[String]]) extends INBTTagable {

    def this(name: String) {
        this(name, None)
    }

    def this(compound: NBTTagCompound) {
        this(null)
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
}
