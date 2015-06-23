package seremis.geninfusion.block

import java.util

import cpw.mods.fml.relauncher.{SideOnly, Side}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import seremis.geninfusion.lib.{Blocks, RenderIds}
import seremis.geninfusion.tileentity.TileCrystal
import seremis.geninfusion.util.UtilBlock

class BlockCrystal(material: Material) extends GIBlockContainer(material) {
    setUnlocalizedName(Blocks.CrystalName)
    setBlockBounds(0.3F, 0.0F, 0.3F, 0.6F, 0.83F, 0.6F)
    setNeedsIcon(false)

    override def isOpaqueCube: Boolean = false

    override def renderAsNormalBlock(): Boolean = false

    override def getRenderType: Int = RenderIds.CrystalRenderID

    override def createNewTileEntity(world: World, metadata: Int): TileEntity = new TileCrystal()

    @SideOnly(Side.CLIENT)
    override def getRenderBlockPass: Int = 1

    override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, placer: EntityLivingBase, stack: ItemStack) {
        val crystal = world.getTileEntity(x, y, z).asInstanceOf[TileCrystal]

        if(stack.hasTagCompound && !world.isRemote) {
            val compound = stack.getTagCompound
            crystal.readFromNBT(compound)
        } else if(stack.hasTagCompound) {
            if(stack.getTagCompound.hasKey("hasSoul")) {
                crystal.hasSoulClient = true
            }
        }

        super.onBlockPlacedBy(world, x, y, z, placer, stack)
    }

    override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, meta: Int) {
        val drop = new ItemStack(this, 1)

        val tile = world.getTileEntity(x, y, z)

        if(!world.isRemote && tile != null && tile.isInstanceOf[TileCrystal] && tile.asInstanceOf[TileCrystal].hasSoul) {
            val crystal = tile.asInstanceOf[TileCrystal]

            val compound = new NBTTagCompound()

            crystal.writeToNBT(compound)

            drop.setTagCompound(compound)
        } else if(tile != null && tile.isInstanceOf[TileCrystal] && tile.asInstanceOf[TileCrystal].hasSoul) {
            val crystal = tile.asInstanceOf[TileCrystal]

            val compound = new NBTTagCompound()

            compound.setBoolean("hasSoul", crystal.hasSoul)

            drop.setTagCompound(compound)
        }

        UtilBlock.dropItemInWorld(x, y, z, world, drop)
        super.breakBlock(world, x, y, z, block, meta)
    }

    override def getDrops(world: World, x: Int, y: Int, z: Int, meta: Int, fortune: Int): util.ArrayList[ItemStack] = {
        new util.ArrayList[ItemStack]()
    }
}
