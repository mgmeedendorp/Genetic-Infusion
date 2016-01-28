package seremis.geninfusion.api.util.render.model.cuboid

import net.minecraft.nbt.{NBTTagCompound, NBTTagList, NBTTagString}
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.util.INBTTagable

import scala.util.Sorting

class CuboidType(var tags: Array[String]) extends INBTTagable {

    Sorting.quickSort(tags)

    def this(compound: NBTTagCompound) {
        this()
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        val list = new NBTTagList

        for(tag <- tags) {
            list.appendTag(new NBTTagString(tag))
        }
        compound.setTag("tags", list)

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        val list = compound.getTagList("tags", Constants.NBT.TAG_STRING)
        tags = new Array[String](list.tagCount())

        for(i <- 0 until list.tagCount()) {
            tags(i) = list.getStringTagAt(i)
        }

        compound
    }

    def similarity(cuboidType: CuboidType): Float = {
        val tags1 = tags
        val tags2 = cuboidType.tags

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
        weight
    }
}
