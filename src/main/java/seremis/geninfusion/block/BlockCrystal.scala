package seremis.geninfusion.block

import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import seremis.geninfusion.lib.{Blocks, RenderIds}
import seremis.geninfusion.tileentity.TileCrystal

class BlockCrystal(material: Material) extends GIBlockContainer(material) {
    setUnlocalizedName(Blocks.CrystalName)
    setBlockBounds(0.3F, 0.0F, 0.3F, 0.6F, 0.83F, 0.6F)
    setNeedsIcon(false)

    override def isOpaqueCube: Boolean = false

    override def renderAsNormalBlock(): Boolean = false

    override def getRenderType: Int = RenderIds.CrystalRenderID

    override def createNewTileEntity(world: World, metadata: Int): TileEntity = new TileCrystal()
}
