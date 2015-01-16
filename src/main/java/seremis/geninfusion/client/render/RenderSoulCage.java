package seremis.geninfusion.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntityRendererPiston;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import seremis.geninfusion.client.model.ModelSoulCage;
import seremis.geninfusion.helper.GIRenderHelper;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.lib.RenderIds;

@SideOnly(Side.CLIENT)
public class RenderSoulCage extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

    ModelSoulCage model = new ModelSoulCage();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        GL11.glPushMatrix();

        GIRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
        model.render(0.0625F);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();

        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderId() {
        return RenderIds.soulCageRenderID;
    }
}
