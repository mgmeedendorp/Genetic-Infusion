package seremis.geninfusion.block

import java.util.Random

import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import seremis.geninfusion.item.ModItems
import seremis.geninfusion.lib.Blocks
import seremis.geninfusion.lib.RenderIds
import seremis.geninfusion.tileentity.TileCrystal

class BlockCrystal(material: Material) extends GIBlockContainer(material) {
    setTextureName(Blocks.CRYSTAL_UNLOCALIZED_NAME)
    setBlockBounds(0.3F, 0.0F, 0.3F, 0.6F, 0.83F, 0.6F)
    setNeedsIcon(false)

    override def isOpaqueCube: Boolean = false

    override def renderAsNormalBlock(): Boolean = false

    override def getRenderType: Int = RenderIds.crystalRenderID

    override def createNewTileEntity(world: World, metadata: Int): TileEntity = new TileCrystal()

    override def getItemDropped(meta: Int, random: Random, fortune: Int): Item = ModItems.crystalShard

    override def quantityDropped(random: Random): Int = 3 + random.nextInt(6)
}
