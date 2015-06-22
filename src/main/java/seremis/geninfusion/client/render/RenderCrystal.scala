package seremis.geninfusion.client.render

import java.util.Random

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockAccess
import org.lwjgl.opengl.GL11
import org.lwjgl.util.Color
import seremis.geninfusion.api.soul.lib.CrystalColors
import seremis.geninfusion.client.model.ModelCrystal
import seremis.geninfusion.helper.GIRenderHelper
import seremis.geninfusion.lib.{Localizations, RenderIds}

object RenderCrystal {

    val model: ModelCrystal = new ModelCrystal()

    def renderCrystal(x: Double, y: Double, z: Double, scale: Float, color: Color) {
        val rand = new Random(0)
        GL11.glPushMatrix()

        GIRenderHelper.bindTexture(Localizations.LocModelTextures + Localizations.Crystal)

        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_COLOR)
        GL11.glDisable(GL11.GL_CULL_FACE)

        GL11.glTranslatef(x.toFloat + 0.35F, y.toFloat - 0.85F, z.toFloat + 0.35F)

        GL11.glColor4f(color.getRed, color.getGreen, color.getBlue, 0.99F)

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
class RenderCrystal extends TileEntitySpecialRenderer with ISimpleBlockRenderingHandler {

    override def renderTileEntityAt(tile: TileEntity, x: Double, y: Double, z: Double, f: Float) {
        RenderCrystal.renderCrystal(x, y, z, 1, new Color(10, 10, 0))
    }

    override def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {
        GL11.glPushMatrix()

        GL11.glRotatef(100.0F, 0.0F, 1.0F, 0.0F)
        GL11.glTranslatef(-0.5F, -1.05F, -0.5F)

        RenderCrystal.renderCrystal(0, 0, 0, 1.5F, CrystalColors.ColorEmpty)

        GL11.glPopMatrix()
    }

    override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = false

    override def shouldRender3DInInventory(modelId: Int): Boolean = true

    @SideOnly(Side.CLIENT)
    override def getRenderId: Int = RenderIds.CrystalRenderID
}