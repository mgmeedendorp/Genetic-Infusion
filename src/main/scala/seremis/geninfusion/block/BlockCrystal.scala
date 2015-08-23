package seremis.geninfusion.block

import java.util

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.particle.{EffectRenderer, EntityDiggingFX}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import seremis.geninfusion.api.soul.lib.CrystalColors
import seremis.geninfusion.api.soul.{ISoul, SoulHelper}
import seremis.geninfusion.lib.{Blocks, RenderIds}
import seremis.geninfusion.tileentity.TileCrystal
import seremis.geninfusion.util.UtilBlock

import scala.util.Random

class BlockCrystal(material: Material) extends GIBlockContainer(material) {
    setUnlocalizedName(Blocks.CrystalName)
    setBlockBounds(0.3F, 0.0F, 0.3F, 0.6F, 0.83F, 0.6F)
    setHardness(3.0F)
    setResistance(10.0F)
    setHarvestLevel("pickaxe", -1)
    setLightLevel(0.125F)

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
            crystal.setSoul(SoulHelper.instanceHelper.getISoulInstance(compound))

            crystal.colorCounter = 0
            crystal.sendTileDataToClient(0, Array(0.toByte))
        }

        super.onBlockPlacedBy(world, x, y, z, placer, stack)
    }

    override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, meta: Int) {
        val drop = new ItemStack(this, 1)

        val tile = world.getTileEntity(x, y, z)

        getCrystalSoulNBT(world, x, y, z, new NBTTagCompound).foreach(compound => drop.setTagCompound(compound))

        UtilBlock.dropItemInWorld(x, y, z, world, drop)
        super.breakBlock(world, x, y, z, block, meta)
    }

    override def getDrops(world: World, x: Int, y: Int, z: Int, meta: Int, fortune: Int): util.ArrayList[ItemStack] = {
        new util.ArrayList[ItemStack]()
    }

    @SideOnly(Side.CLIENT)
    override def addHitEffects(world: World, target: MovingObjectPosition, effectRenderer: EffectRenderer): Boolean = {
        val x = target.blockX
        val y = target.blockY
        val z = target.blockZ

        addEffects(world, x, y, z, effectRenderer)
        true
    }

    @SideOnly(Side.CLIENT)
    override def addDestroyEffects(world: World, x: Int, y: Int, z: Int, meta: Int, effectRenderer: EffectRenderer): Boolean = {
        addEffects(world, x, y, z, effectRenderer)
        true
    }

    @SideOnly(Side.CLIENT)
    def addEffects(world: World, x: Int, y: Int, z: Int, effectRenderer: EffectRenderer) {
        val rand = new Random()
        val block = world.getBlock(x, y, z)

        val pX = x.toDouble + rand.nextDouble * (block.getBlockBoundsMaxX - block.getBlockBoundsMinX - (0.1F * 2.0F).toDouble) + 0.1F.toDouble + block.getBlockBoundsMinX
        val pY = y.toDouble + rand.nextDouble * (block.getBlockBoundsMaxY - block.getBlockBoundsMinY - (0.1F * 2.0F).toDouble) + 0.1F.toDouble + block.getBlockBoundsMinY
        val pZ = z.toDouble + rand.nextDouble * (block.getBlockBoundsMaxZ - block.getBlockBoundsMinZ - (0.1F * 2.0F).toDouble) + 0.1F.toDouble + block.getBlockBoundsMinZ

        val diggingFX = new EntityDiggingFX(world, pX, pY, pZ, 0.0D, 0.0D, 0.0D, block, world.getBlockMetadata(x, y, z)).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F)

        val crystal = world.getTileEntity(x, y, z).asInstanceOf[TileCrystal]

        if(crystal.hasSoul)
            //For some reason this acts weird with the proper color, but this gets close enough
            diggingFX.setRBGColorF(CrystalColors.ColorNonEmpty.getRed, CrystalColors.ColorNonEmpty.getGreen, 0)
        else
            diggingFX.setRBGColorF(CrystalColors.ColorEmpty.getRed, CrystalColors.ColorEmpty.getGreen, CrystalColors.ColorEmpty.getBlue)

        effectRenderer.addEffect(diggingFX)
    }

    override def getPickBlock(target: MovingObjectPosition, world: World, x: Int, y: Int, z: Int, player: EntityPlayer): ItemStack = {
        val stack = new ItemStack(this, 1, 0)
        getCrystalSoulNBT(world, x, y, z, new NBTTagCompound).foreach(compound => stack.setTagCompound(compound))
        stack
    }

    def getCrystalSoul(world: World, x: Int, y: Int, z: Int): Option[ISoul] = {
        val tile = world.getTileEntity(x, y, z)

        if(tile != null && tile.isInstanceOf[TileCrystal]) {
            return tile.asInstanceOf[TileCrystal].getSoul
        }
        None
    }

    def getCrystalSoulNBT(world: World, x: Int, y: Int, z: Int, compound: NBTTagCompound): Option[NBTTagCompound] = getCrystalSoul(world, x, y, z).map(s => Some(s.writeToNBT(compound))).getOrElse(None)
}
