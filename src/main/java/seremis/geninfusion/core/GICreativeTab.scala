package seremis.geninfusion.core

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import seremis.geninfusion.block.ModBlocks

class GICreativeTab(name: String) extends CreativeTabs(name) {

    @SideOnly(Side.CLIENT)
    override def getTabIconItem: Item = Item.getItemFromBlock(ModBlocks.crystal)
}