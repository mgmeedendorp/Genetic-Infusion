package seremis.geninfusion.api.util.render.model.cuboid

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.Vec3
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.util.INBTTagable

class CuboidAttachmentPoint(var location: Vec3, var cuboidTypes: Array[CuboidType]) extends INBTTagable {

    def this(compound: NBTTagCompound) {
        this(null, null)
        readFromNBT(compound)
    }

    def getConnectableCuboidTypes = cuboidTypes
    def getLocation = location

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setDouble("vecX", location.xCoord)
        compound.setDouble("vecY", location.yCoord)
        compound.setDouble("vecZ", location.zCoord)

        val typeList = new NBTTagList()

        for(cuboidType <- cuboidTypes) {
            val nbt = new NBTTagCompound()

            cuboidType.writeToNBT(nbt)

            typeList.appendTag(nbt)
        }

        compound.setTag("cuboidTypes", typeList);

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        val x = compound.getDouble("vecX")
        val y = compound.getDouble("vecY")
        val z = compound.getDouble("vecZ")

        location = Vec3.createVectorHelper(x, y, z)

        val typeList = compound.getTagList("cuboidTypes", Constants.NBT.TAG_COMPOUND)

        cuboidTypes = new Array[CuboidType](typeList.tagCount())

        for(i <- 0 until typeList.tagCount()) {
            val nbt = typeList.getCompoundTagAt(i)

            cuboidTypes(i) = new CuboidType(nbt)
        }

        compound
    }
}
