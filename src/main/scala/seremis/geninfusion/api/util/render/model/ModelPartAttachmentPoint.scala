package seremis.geninfusion.api.util.render.model

import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.Vec3
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.util.INBTTagable

class ModelPartAttachmentPoint(var pointLocation: Vec3, var pointPartTypes: Array[ModelPartType]) extends INBTTagable {

    def this(compound: NBTTagCompound) {
        this(null, null)
        readFromNBT(compound)
    }

    def getConnectableModelPartTypes: Array[ModelPartType] = pointPartTypes
    def getConnectableModelPartTypeNames: Array[String] = pointPartTypes.map(a => a.name)

    def getPointLocation: Vec3 = pointLocation


    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setDouble("vecX", pointLocation.xCoord)
        compound.setDouble("vecY", pointLocation.yCoord)
        compound.setDouble("vecZ", pointLocation.zCoord)

        val typeList = new NBTTagList

        pointPartTypes.foreach(partType => {
            typeList.appendTag(partType.writeToNBT(new NBTTagCompound))
        })

        compound.setTag("partTypes", typeList)
        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        pointLocation = Vec3.createVectorHelper(compound.getDouble("vecX"), compound.getDouble("vecY"), compound.getDouble("vecZ"))

        val partTypesList = compound.getTagList("partTypes", Constants.NBT.TAG_COMPOUND)
        pointPartTypes = new Array[ModelPartType](partTypesList.tagCount)

        for(i <- 0 until partTypesList.tagCount) {
            pointPartTypes(i) = new ModelPartType(partTypesList.getCompoundTagAt(i))
        }

        compound
    }
}
