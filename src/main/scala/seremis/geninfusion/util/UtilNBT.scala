package seremis.geninfusion.util

import net.minecraft.nbt.{CompressedStreamTools, NBTSizeTracker, NBTTagCompound}

object UtilNBT {

    def compoundToByteArray(compound: NBTTagCompound): Option[Array[Byte]] = {
        try {
            Option(CompressedStreamTools.compress(compound)).map(bytes => return Some(bytes))
        } catch {
            case e: Exception => e.printStackTrace(); return None
        }
        None
    }

    def byteArrayToCompound(abyte: Array[Byte]): Option[NBTTagCompound] = {
        Option(CompressedStreamTools.decompress(abyte, NBTSizeTracker.INFINITE)).map { compound => return Some(compound) }
        None
    }
}
