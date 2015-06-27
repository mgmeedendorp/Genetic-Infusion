package seremis.geninfusion.util

import net.minecraft.nbt.{NBTSizeTracker, CompressedStreamTools, NBTTagCompound}

object UtilNBT {

    def compoundToByteArray(compound: NBTTagCompound): Option[Array[Byte]] = {
        var abyte: Array[Byte] = null
        try {
            abyte = CompressedStreamTools.compress(compound)
        } catch {
            case e: Exception => e.printStackTrace() return None
        }
        Some(abyte)
    }

    def byteArrayToCompound(abyte: Array[Byte]): Option[NBTTagCompound] = {
        Option(CompressedStreamTools.decompress(abyte, NBTSizeTracker.INFINITE)).map { compound => return Some(compound) }
        None
    }
}
