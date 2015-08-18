package seremis.geninfusion.item

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemStack, ItemBlock}
import net.minecraft.util.EnumChatFormatting
import org.lwjgl.input.Keyboard
import seremis.geninfusion.api.soul.SoulHelper

class ItemBlockCrystal(block: Block) extends ItemBlock(block) {

    override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[_], advancedToolTips: Boolean) = {
        if(stack.hasTagCompound) {
            for(soul <- SoulHelper.instanceHelper.getISoulInstance(stack.getTagCompound)) {
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