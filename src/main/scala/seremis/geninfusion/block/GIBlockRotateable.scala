package seremis.geninfusion.block

import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import net.minecraft.world.{IBlockAccess, World}

abstract class GIBlockRotateable(material: Material, sidedTextureNames: Array[String]) extends GIBlockContainer(material) {

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

        world.setBlockMetadataWithNotify(x, y, z, direction - 2, 3)
    }

    @SideOnly(Side.CLIENT)
    override def getIcon(side: Int, metadata: Int): IIcon = getSidedIcons(side)

    @SideOnly(Side.CLIENT)
    override def getIcon(blockAccess: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = {
        val direction = blockAccess.getBlockMetadata(x, y, z) + 2
        getSidedIcons(getTextureIndex(direction, side))
    }

    private def getTextureIndex(direction: Int, side: Int): Int = {
        var index = if(side < 2) side else direction + side
        while(index > 5) {
            index = index - 4
        }
        index
    }
}