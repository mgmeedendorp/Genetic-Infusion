package seremis.geninfusion.block

import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import net.minecraft.world.{IBlockAccess, World}
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.tileentity.GITile

abstract class GIBlockContainerRotateable(material: Material, useTile: Boolean, sidedTextureNames: Array[String]) extends GIBlockContainer(material) {

    setNeedsSidedTexture(true, sidedTextureNames)

    override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, entity: EntityLivingBase, itemStack: ItemStack) {
        var direction = 0
        val facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3

        facing match {
            case 3 => direction = ForgeDirection.WEST.ordinal()
            case 2 => direction = ForgeDirection.EAST.ordinal()
            case 1 => direction = ForgeDirection.SOUTH.ordinal()
            case 0 => direction = ForgeDirection.NORTH.ordinal()
        }

        if (!useTile) {
            world.setBlockMetadataWithNotify(x, y, z, direction - 2, 3)
        }

        if (useTile && GeneticInfusion.commonProxy.isServerWorld(world)) {
            val tile = world.getTileEntity(x, y, z)

            if (tile != null && tile.isInstanceOf[GITile]) {
                tile.asInstanceOf[GITile].setDirection(direction)
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override def getIcon(side: Int, metadata: Int): IIcon = getSidedIcons(side)

    @SideOnly(Side.CLIENT)
    override def getIcon(blockAccess: IBlockAccess,  x: Int, y: Int, z: Int, side: Int): IIcon = {
        if (useTile) {
            val tile = blockAccess.getTileEntity(x, y, z)

            if (tile != null && tile.isInstanceOf[GITile]) {
                val direction = tile.asInstanceOf[GITile].getDirection

                return getSidedIcons(getTextureIndex(direction, side))
            }
        } else {
            val direction = blockAccess.getBlockMetadata(x, y, z) + 2

            return getSidedIcons(getTextureIndex(direction, side))
        }
        null
    }

    private def getTextureIndex(direction: Int, side: Int): Int = {
        var index = if (side < 2) side else direction + side
        while (index > 5) {
            index = index - 4
        }
        index
    }
}