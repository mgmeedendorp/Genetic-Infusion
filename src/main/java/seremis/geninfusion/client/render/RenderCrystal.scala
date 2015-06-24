package seremis.geninfusion.client.render

import java.util.Random

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockAccess
import net.minecraftforge.client.IItemRenderer
import net.minecraftforge.client.IItemRenderer.{ItemRendererHelper, ItemRenderType}
import org.lwjgl.opengl.GL11
import org.lwjgl.util.Color
import seremis.geninfusion.api.soul.lib.CrystalColors
import seremis.geninfusion.client.model.ModelCrystal
import seremis.geninfusion.helper.GIRenderHelper
import seremis.geninfusion.lib.{Localizations, RenderIds}
import seremis.geninfusion.tileentity.TileCrystal

object RenderCrystal {

    val model: ModelCrystal = new ModelCrystal()

    def renderCrystal(x: Double, y: Double, z: Double, scale: Float, color: Color, seed: Int) {
        val rand = new Random(seed)
        GL11.glPushMatrix()

        GIRenderHelper.bindTexture(Localizations.LocModelTextures + Localizations.Crystal)

        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_COLOR)
        GL11.glDisable(GL11.GL_CULL_FACE)

        GL11.glTranslatef(x.toFloat + 0.35F, y.toFloat - 0.85F, z.toFloat + 0.35F)

        GL11.glColor3f(color.getRed/255F, color.getGreen/255F, color.getBlue/255F)

        GL11.glScalef(0.15F + rand.nextFloat() * 0.075F, 0.5F + rand.nextFloat() * 0.1F, 0.15F + rand.nextFloat() * 0.05F)
        GL11.glScalef(scale, scale, scale)

        model.render()

        GL11.glEnable(GL11.GL_CULL_FACE)
        GL11.glDisable(GL11.GL_BLEND)
        GL11.glColor3f(1, 1, 1)
        GL11.glPopMatrix()
    }
}

@SideOnly(Side.CLIENT)
class RenderCrystal extends TileEntitySpecialRenderer with ISimpleBlockRenderingHandler with IItemRenderer {

    override def renderTileEntityAt(tile: TileEntity, x: Double, y: Double, z: Double, f: Float) {
        val crystal = tile.asInstanceOf[TileCrystal]
        var color: Color = CrystalColors.ColorEmpty

        if(crystal.hasSoul)
            color = CrystalColors.ColorNonEmpty

        if(crystal.hasSoul && crystal.colorCounter > 0) {
            val red = CrystalColors.ColorEmpty.getRed + ((CrystalColors.ColorEmpty.getRed - CrystalColors.ColorNonEmpty.getRed).toFloat/crystal.maxColorCounter.toFloat)*crystal.colorCounter
            val green = CrystalColors.ColorEmpty.getGreen + ((CrystalColors.ColorEmpty.getGreen - CrystalColors.ColorNonEmpty.getGreen).toFloat/crystal.maxColorCounter.toFloat)*crystal.colorCounter
            val blue = CrystalColors.ColorEmpty.getBlue + ((CrystalColors.ColorEmpty.getBlue - CrystalColors.ColorNonEmpty.getBlue).toFloat/crystal.maxColorCounter.toFloat)*crystal.colorCounter

            color.setRed(red.toInt)
            color.setGreen(green.toInt)
            color.setBlue(blue.toInt)
        }

        RenderCrystal.renderCrystal(x, y, z, 1, color, tile.xCoord+tile.yCoord+tile.zCoord)
    }

    override def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {

    }

    override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = false

    override def shouldRender3DInInventory(modelId: Int): Boolean = true

    @SideOnly(Side.CLIENT)
    override def getRenderId: Int = RenderIds.CrystalRenderID

    override def shouldUseRenderHelper(renderType: ItemRenderType, item: ItemStack, helper: ItemRendererHelper): Boolean = true

    override def handleRenderType(item: ItemStack, renderType: ItemRenderType): Boolean = true

    override def renderItem(renderType: ItemRenderType, item: ItemStack, data: AnyRef*) {
        GL11.glPushMatrix()

        GL11.glRotatef(100.0F, 0.0F, 1.0F, 0.0F)
        GL11.glTranslatef(-0.5F, -1.05F, -0.5F)

        if(renderType == ItemRenderType.EQUIPPED || renderType == ItemRenderType.EQUIPPED_FIRST_PERSON)
            GL11.glTranslatef(-0.6F, 0.5F, 0.5F)

        if(item.hasTagCompound)
            RenderCrystal.renderCrystal(0, 0, 0, 1.5F, CrystalColors.ColorNonEmpty, 0)
        else
            RenderCrystal.renderCrystal(0, 0, 0, 1.5F, CrystalColors.ColorEmpty, 0)

        GL11.glPopMatrix()
    }
}