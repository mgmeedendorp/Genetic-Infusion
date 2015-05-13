package seremis.geninfusion.item

import java.util

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.IIcon
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.lib.DefaultProps

class GIItem extends Item() {

    setMaxStackSize(64)
    setCreativeTab(GeneticInfusion.creativeTab)

    var iconBuffer: Array[IIcon] = null
    var metadata: Int = 0

    override def setUnlocalizedName(par1Str: String): GIItem = {
        super.setUnlocalizedName(par1Str).asInstanceOf[GIItem]
    }

    @SideOnly(Side.CLIENT)
    override def registerIcons(iconRegister: IIconRegister) {
        if (this.metadata == 0) {
            itemIcon = iconRegister.registerIcon(DefaultProps.ID + ":" + this.getUnlocalizedName.substring(5))
        } else {
            iconBuffer = Array.ofDim[IIcon](metadata)
            for (x <- 0 until iconBuffer.length) {
                iconBuffer(x) = iconRegister.registerIcon(DefaultProps.ID + ":" + this.getUnlocalizedName.substring(5) +
                    (x + 1))
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override def getIconFromDamage(metadata: Int): IIcon = {
        if (this.metadata != 0) {
            itemIcon = iconBuffer(metadata)
        }
        this.itemIcon
    }

    @SideOnly(Side.CLIENT)
    override def getSubItems(item: Item, creativetab: CreativeTabs, list: util.List[_]) {
        if (metadata > 0) {
            for (i <- 0 until getNumbersofMetadata) {
                list.asInstanceOf[util.List[ItemStack]].add(new ItemStack(item, 1, i))
            }
        } else {
            list.asInstanceOf[util.List[ItemStack]].add(new ItemStack(item, 1, 0))
        }
    }

    def getNumbersofMetadata: Int = this.metadata

    def setNumbersofMetadata(metadata: Int) {
        this.metadata = metadata
    }
}