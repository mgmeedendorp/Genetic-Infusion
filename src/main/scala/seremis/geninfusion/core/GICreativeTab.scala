package seremis.geninfusion.core

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import seremis.geninfusion.block.ModBlocks
import seremis.geninfusion.soul.ModSouls

class 
GICreativeTab(name: String) extends CreativeTabs(name) {

    @SideOnly(Side.CLIENT)
    override def getTabIconItem: Item = Item.getItemFromBlock(ModBlocks.crystal)

    @SideOnly(Side.CLIENT)
    override def displayAllReleventItems(lst: java.util.List[_]) {
        super.displayAllReleventItems(lst)

        val list = lst.asInstanceOf[java.util.List[ItemStack]]
        val prevSize = list.size

        val souls = Array(ModSouls.SoulCreeper, ModSouls.SoulSkeleton, ModSouls.SoulSpider, ModSouls.SoulZombie)

        for(soul <- souls) {
            val stack = new ItemStack(ModBlocks.crystal)

            if(!stack.hasTagCompound) {
                stack.setTagCompound(new NBTTagCompound)
            }

            soul.writeToNBT(stack.getTagCompound)
            list.add(stack)
        }
    }
}