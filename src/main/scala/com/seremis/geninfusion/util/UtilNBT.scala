package com.seremis.geninfusion.util

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import net.minecraft.nbt.{CompressedStreamTools, NBTTagCompound}

object UtilNBT {

    def compoundToByteArray(compound: NBTTagCompound): Array[Byte] = {
        val streamOut = new ByteArrayOutputStream()

        CompressedStreamTools.writeCompressed(compound, streamOut)

        streamOut.toByteArray
    }

    def byteArrayToCompound(bytes: Array[Byte]): NBTTagCompound = CompressedStreamTools.readCompressed(new ByteArrayInputStream(bytes))
}