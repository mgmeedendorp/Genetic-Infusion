package seremis.geninfusion.item

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.common.util.Constants
import org.lwjgl.input.Keyboard
import seremis.geninfusion.api.soul.{ISoul, SoulHelper}
import seremis.geninfusion.api.util.{AncestryNode, AncestryNodeRoot}

import scala.collection.mutable

class ItemBlockCrystal(block: Block) extends ItemBlock(block) {

    override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[_], advancedToolTips: Boolean): Unit = {
        if(stack.hasTagCompound) {
            val tagCompound = stack.getTagCompound

            if(tagCompound.getString("type") == "clientRecipeRender") {
                val tagList = tagCompound.getTagList("ancestry", Constants.NBT.TAG_COMPOUND)

                list.asInstanceOf[java.util.List[String]].add("Mutated Entity")

                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                    list.asInstanceOf[java.util.List[String]].add("")
                    for(i <- 0 until tagList.tagCount()) {
                        val ancestorCompound = tagList.getCompoundTagAt(i)
                        val ancestor = AncestryNode.fromNBT(ancestorCompound).asInstanceOf[AncestryNodeRoot]

                        list.asInstanceOf[java.util.List[String]].add("* " + ancestor.name)
                    }
                } else {
                    list.asInstanceOf[java.util.List[String]].add(EnumChatFormatting.ITALIC + "Hold shift to see ancestors")
                }

            } else {
                if(!ItemBlockCrystal.soulCompoundMap.contains(tagCompound)) {
                    SoulHelper.instanceHelper.getISoulInstance(tagCompound).foreach(soul => ItemBlockCrystal.soulCompoundMap += (tagCompound -> soul))

                    if(!ItemBlockCrystal.soulCompoundMap.contains(tagCompound)) {
                        list.asInstanceOf[java.util.List[String]].add(EnumChatFormatting.RED + " " + EnumChatFormatting.ITALIC + "THIS CRYSTAL HAS A BROKEN NBT TAG, DO NOT USE THIS.")
                        return
                    }
                }

                val soul = ItemBlockCrystal.soulCompoundMap.get(tagCompound).get

                list.asInstanceOf[java.util.List[String]].add(soul.getName.getOrElse("Mutated Entity"))
                if(soul.getName.isEmpty && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
                    list.asInstanceOf[java.util.List[String]].add("")
                    for(ancestor <- soul.getAncestryNode.getUniqueAncestorRoots) {
                        list.asInstanceOf[java.util.List[String]].add("* " + ancestor.name)
                    }
                } else if(soul.getName.isEmpty) {
                    list.asInstanceOf[java.util.List[String]].add(EnumChatFormatting.ITALIC + "Hold shift to see ancestors")
                }
            }
        }
    }
}

object ItemBlockCrystal {
    var soulCompoundMap: mutable.WeakHashMap[NBTTagCompound, ISoul] = mutable.WeakHashMap()
}