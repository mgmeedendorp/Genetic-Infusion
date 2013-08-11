package Seremis.SoulCraft.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelBush5;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.tileentity.TileBush;

public class TileBushRenderer extends TileEntitySpecialRenderer {

    // private ModelBush1 model1 = new ModelBush1();
    // private ModelBush2 model2 = new ModelBush2();
    // private ModelBush3 model3 = new ModelBush3();
    // private ModelBush4 model4 = new ModelBush4();
    private ModelBush5 model5 = new ModelBush5();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        renderBush((TileBush) tile, x, y, z);
    }

    public void renderBush(TileBush tile, double x, double y, double z) {
        String texture = tile.getTexture();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glScalef(0, 0, 0);
        SCRenderHelper.bindTexture(texture);
        switch(tile.getStage()) {
            case 0:
                model5.render();
                break;
            case 1:
                model5.render();
                break;
            case 2:
                model5.render();
                break;
            case 3:
                model5.render();
                break;
            case 4:
                model5.render();
                break;
        }
        GL11.glPopMatrix();
    }

}
