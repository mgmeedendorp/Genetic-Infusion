package Seremis.SoulCraft.client.render;

import java.util.Random;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelCrystal;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.tileentity.TileIsolatziumCrystal;

public class TileCrystalRenderer extends TileEntitySpecialRenderer {

    private ModelCrystal model;

    public TileCrystalRenderer() {
        model = new ModelCrystal();
    }

    public void renderCrystal(double x, double y, double z, Random rand, float size) {
        GL11.glPushMatrix();
        // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // GL11.glEnable(GL11.GL_BLEND);
        GL11.glTranslatef((float) x + 0.35F, (float) y - 0.85F * size, (float) z + 0.35F);

        SCRenderHelper.avoidFlickering();

        GL11.glScalef((0.15F + rand.nextFloat() * 0.075F) * size, (0.5F + rand.nextFloat() * 0.1F) * size, (0.15F + rand.nextFloat() * 0.05F) * size);

        model.render();

        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {

        TileIsolatziumCrystal tco = (TileIsolatziumCrystal) tile;

        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK);
        Random rand = new Random(tco.xCoord + tco.yCoord * tco.zCoord);
        renderCrystal((float) x, (float) y, (float) z, rand, 1F);
    }

}
