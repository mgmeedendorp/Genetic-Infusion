package seremis.geninfusion.api.util

import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.util.INBTTagable

abstract class AncestryNode extends INBTTagable {
    def getUniqueAncestors: Array[AncestryNodeRoot]
}

object AncestryNode {
    def fromNBT(compound: NBTTagCompound): AncestryNode = {
        var element: AncestryNode = null

        compound.getByte("elementId") match {
            case 0 => element = new AncestryNodeRoot("")
            case 1 => element = new AncestryNodeBranch(null, null)
        }
        element.readFromNBT(compound)

        element
    }
}

case class AncestryNodeRoot(var name: String) extends AncestryNode {

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setString("name", name)
        compound.setByte("elementId", 0)
        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        name = compound.getString("name")
        compound
    }

    override def toString: String = {
        "AncestryNodeRoot[name: " + name + "]"
    }

    override def getUniqueAncestors: Array[AncestryNodeRoot] = Array(this)
}

case class AncestryNodeBranch(var ancestor1: AncestryNode, var ancestor2: AncestryNode) extends AncestryNode {

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        compound.setTag("ancestor1", ancestor1.writeToNBT(new NBTTagCompound))
        compound.setTag("ancestor2", ancestor2.writeToNBT(new NBTTagCompound))
        compound.setByte("elementId", 1)
        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        ancestor1 = AncestryNode.fromNBT(compound.getCompoundTag("ancestor1"))
        ancestor2 = AncestryNode.fromNBT(compound.getCompoundTag("ancestor2"))
        compound
    }

    override def toString: String = {
        "AncestryNodeBranch[ancestor1: " + ancestor1 + ", ancestor2: " + ancestor2 + "]"
    }

    override def getUniqueAncestors: Array[AncestryNodeRoot] = (ancestor1.getUniqueAncestors ++ ancestor2.getUniqueAncestors).distinct
}