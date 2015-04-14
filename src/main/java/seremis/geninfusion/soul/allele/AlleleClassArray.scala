package seremis.geninfusion.soul.allele

import net.minecraft.client.Minecraft
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.api.soul.EnumAlleleType
import seremis.geninfusion.soul.Allele

class AlleleClassArray(isDominant: Boolean, var value: Array[Class[_]]) extends Allele(isDominant, EnumAlleleType.CLASS_ARRAY) {

    def this(args: AnyRef*) {
        this(args(0).asInstanceOf[Boolean], args(1).asInstanceOf[Array[Class[_]]].drop(1))
    }

    def this(compound: NBTTagCompound) {
        this(false, null)
        readFromNBT(compound)
    }

    override def writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setInteger("length", value.length)
        for (i <- 0 until value.length) {
            compound.setString("value" + i, value(i).getName)
        }
    }

    override def readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        value = Array.ofDim[Class[_]](compound.getInteger("length"))
        try {
            for (i <- 0 until value.length) {
                value(i) = Class.forName(compound.getString("value" + i))
            }
        } catch {
            case e: ClassNotFoundException => {
                println("For some reason a class cannot be found on the " + (if (Minecraft.getMinecraft.theWorld.isRemote) "client." else "server."))
                e.printStackTrace()
            }
        }
    }
}