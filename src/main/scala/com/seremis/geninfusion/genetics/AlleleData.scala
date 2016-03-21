package com.seremis.geninfusion.genetics

import com.seremis.geninfusion.api.genetics.IAlleleData
import com.seremis.geninfusion.api.registry.GIRegistry
import com.seremis.geninfusion.api.util.TypedName
import net.minecraft.nbt.NBTTagCompound

case class AlleleData[A](name: TypedName[A], var data: A, var isDominant: Boolean) extends IAlleleData[A] {

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        data = GIRegistry.dataTypeRegistry.readValueFromNBT(compound, name)
        isDominant = compound.getBoolean(name.name + ".isDominant")

        compound
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        GIRegistry.dataTypeRegistry.writeValueToNBT(compound, name, data)
        compound.setBoolean(name.name + ".isDominant", isDominant)

        compound
    }

    override def getName: TypedName[A] = name

    override def getData: A = data
}
