package seremis.geninfusion.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import seremis.geninfusion.client.model.ModelCrystalStand;
import seremis.geninfusion.helper.GIRenderHelper;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.tileentity.TileCrystalStand;

public class TileCrystalStandRenderer extends TileEntitySpecialRenderer {

    private ModelCrystalStand crystalStand;

    public TileCrystalStandRenderer() {
        crystalStand = new ModelCrystalStand();
    }

    public void renderCrystalStand(TileCrystalStand tile, float x, float y, float z, float size) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        GL11.glScalef(1.0F, 1.0F, 1.0F);

        crystalStand.render();

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        if(tile != null && tile instanceof TileCrystalStand) {
            TileCrystalStand tco = (TileCrystalStand) tile;
            if(tco.hasCrystal()) {
                RenderCrystal.instance.renderCrystal((float) x + 0.1F, (float) y + 0.8F, (float) z + 0.06F, 0.7F, tco.getHeat());
            }
            GIRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK);
            renderCrystalStand(tco, (float) x, (float) y, (float) z,  1F);
        }

    }
}
