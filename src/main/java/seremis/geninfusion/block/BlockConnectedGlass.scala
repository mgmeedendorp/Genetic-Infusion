package seremis.geninfusion.block

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess
import seremis.geninfusion.helper.ConnectedTextureHelper
import seremis.geninfusion.lib.Blocks

class BlockConnectedGlass(material: Material) extends GIBlock(material) {

    setUnlocalizedName(Blocks.ConnectedGlassName)
    setHardness(2F)
    setResistance(3F)

    val textureHelper: ConnectedTextureHelper = new ConnectedTextureHelper(this, Blocks.ConnectedGlassName)

    @SideOnly(Side.CLIENT)
    override def registerIcons(iconRegister: IIconRegister) {
        super.registerIcons(iconRegister)
        textureHelper.registerIcons(iconRegister)
    }

    @SideOnly(Side.CLIENT)
    override def getIcon(blockAccess: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = textureHelper.getIcon(blockAccess, x, y, z, side)

    override def isOpaqueCube: Boolean = false

    @SideOnly(Side.CLIENT)
    override def shouldSideBeRendered(blockAccess: IBlockAccess, x: Int, y: Int, z: Int, side: Int): Boolean = blockAccess.getBlock(x, y, z) != this && super.shouldSideBeRendered(blockAccess, x, y, z, side)

    override def renderAsNormalBlock(): Boolean = false
}