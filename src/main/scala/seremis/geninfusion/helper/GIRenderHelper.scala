package seremis.geninfusion.helper

import net.minecraft.block.Block
import net.minecraft.client.renderer.Tessellator
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

object GIRenderHelper {

    def renderAllFaces(block: Block, renderer: RenderBlocks, tex: IIcon) {
        renderAllFaces(block, renderer, tex, tex, tex, tex, tex, tex)
    }

    def renderAllFaces(block: Block, renderer: RenderBlocks, tex1: IIcon, tex2: IIcon, tex3: IIcon, tex4: IIcon, tex5: IIcon, tex6: IIcon) {
        val tessellator = Tessellator.instance

        if (tex1 != null && tex2 != null && tex3 != null && tex4 != null && tex5 != null && tex6 != null) {
            try {
                if (Tessellator.renderingWorldRenderer) {
                    return
                }
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F)
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0F, -1.0F, 0.0F)
                renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, tex1)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0F, 1.0F, 0.0F)
                renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, tex2)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0F, 0.0F, -1.0F)
                renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, tex3)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(0.0F, 0.0F, 1.0F)
                renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, tex4)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(-1.0F, 0.0F, 0.0F)
                renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, tex5)
                tessellator.draw()
                tessellator.startDrawingQuads()
                tessellator.setNormal(1.0F, 0.0F, 0.0F)
                renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, tex6)
                tessellator.draw()
                GL11.glTranslatef(0.5F, 0.5F, 0.5F)
            } catch {
                case ex: Exception => ex.printStackTrace()
            }
        }
    }

    def bindTexture(tex: String) {
        val resource = new ResourceLocation(tex)
        FMLClientHandler.instance().getClient.renderEngine.bindTexture(resource)
    }
}