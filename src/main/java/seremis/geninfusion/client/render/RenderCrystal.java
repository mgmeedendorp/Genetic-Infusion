package seremis.geninfusion.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import seremis.geninfusion.client.model.ModelCrystal;
import seremis.geninfusion.client.model.ModelSoulCage;
import seremis.geninfusion.helper.GIRenderHelper;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.lib.RenderIds;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class RenderCrystal extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        RenderCrystal.renderCrystal(x, y, z, 1);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glRotatef(100.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -1.05F, -0.5F);
        RenderCrystal.renderCrystal(0, 0, 0, 1.5F);
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

    private static ModelCrystal model = new ModelCrystal();


    public static void renderCrystal(double x, double y, double z, float scale) {
        Random rand = new Random(0);

        GL11.glPushMatrix();

        GIRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.CRYSTAL);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glTranslatef((float) x + 0.35F, (float) y - 0.85F, (float) z + 0.35F);

        Color color = new Color(10, 100, 10);

        GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), 0.8F);

        GL11.glScalef(0.15F + rand.nextFloat() * 0.075F, 0.5F + rand.nextFloat() * 0.1F, 0.15F + rand.nextFloat() * 0.05F);
        GL11.glScalef(scale, scale, scale);

        model.render();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor3f(1, 1, 1);
        GL11.glPopMatrix();
    }
}
