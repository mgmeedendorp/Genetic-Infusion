package seremis.geninfusion.block

import java.util

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.IIcon
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.lib.DefaultProps

class GIBlock(material: Material) extends Block(material) {

    setCreativeTab(GeneticInfusion.creativeTab)

    private var iconBuffer: Array[IIcon] = _
    private var metadata: Int = 0
    private var needsIcon: Boolean = true
    private var sidedIconBuffer: Array[IIcon] = _
    private var needsSidedTexture: Boolean = false
    private var sidedTextureNames: Array[String] = Array("bottom", "top", "back", "front", "left", "right")

    @SideOnly(Side.CLIENT)
    override def registerIcons(iconRegister: IIconRegister) {
        if (!needsIcon) {
            return
        }
        if (this.needsSidedTexture) {
            sidedIconBuffer = Array.ofDim[IIcon](sidedTextureNames.length)
            for (i <- 0 until sidedTextureNames.length) {
                sidedIconBuffer(i) = iconRegister.registerIcon(DefaultProps.ID + ":" + getUnlocalizedName.substring(5) + "_" + sidedTextureNames(i))
            }
        }
        if (this.metadata == 0) {
            blockIcon = iconRegister.registerIcon(DefaultProps.ID + ":" + getUnlocalizedName.substring(5))
        } else {
            iconBuffer = Array.ofDim[IIcon](metadata)
            for (x <- 0 until iconBuffer.length) {
                iconBuffer(x) = iconRegister.registerIcon(DefaultProps.ID + ":" + getUnlocalizedName.substring(5) + (x + 1))
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override def getIcon(side: Int, metadata: Int): IIcon = {
        if (this.metadata > 0 && needsIcon) {
            blockIcon = iconBuffer(metadata)
        }
        if (needsSidedTexture) {
            return getSidedIcons(side)
        }
        this.blockIcon
    }

    @SideOnly(Side.CLIENT)
    def getSidedIcons: Array[IIcon] = sidedIconBuffer

    @SideOnly(Side.CLIENT)
    override def getSubBlocks(item: Item, tab: CreativeTabs, subItems: util.List[_]) {
        if (metadata > 0) {
            for (ix <- 0 until getNumbersOfMetadata) {
                subItems.asInstanceOf[util.List[ItemStack]].add(new ItemStack(this, 1, ix))
            }
        } else {
            subItems.asInstanceOf[util.List[ItemStack]].add(new ItemStack(this, 1, 0))
        }
    }

    def getNumbersOfMetadata: Int = metadata

    def setNumbersofMetadata(metadata: Int) {
        this.metadata = metadata
    }

    def setNeedsIcon(needsIcon: Boolean) {
        this.needsIcon = needsIcon
    }

    def setNeedsSidedTexture(needsSidedTexture: Boolean, textureNames: Array[String]) {
        this.needsSidedTexture = needsSidedTexture
        this.sidedTextureNames = textureNames
    }
}