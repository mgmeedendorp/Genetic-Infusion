package seremis.geninfusion.world

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.WorldSavedData
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.lib.DefaultProps

class GIWorldSavedData extends WorldSavedData(DefaultProps.ID) {

    override def readFromNBT(compound: NBTTagCompound) {
        SoulHelper.textureID = compound.getInteger("currentTextureID")
    }

    override def writeToNBT(compound: NBTTagCompound) {
        compound.setInteger(DefaultProps.ID + "currentTextureID", SoulHelper.textureID)
    }
}
