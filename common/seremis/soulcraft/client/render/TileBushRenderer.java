package seremis.soulcraft.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import seremis.soulcraft.client.model.ModelBush1;
import seremis.soulcraft.client.model.ModelBush2;
import seremis.soulcraft.client.model.ModelBush3;
import seremis.soulcraft.client.model.ModelBush4;
import seremis.soulcraft.client.model.ModelBush5;
import seremis.soulcraft.tileentity.TileBush;

public class TileBushRenderer extends TileEntitySpecialRenderer {

    private ModelBush1 model1 = new ModelBush1();
    private ModelBush2 model2 = new ModelBush2();
    private ModelBush3 model3 = new ModelBush3();
    private ModelBush4 model4 = new ModelBush4();
    private ModelBush5 model5 = new ModelBush5();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        renderBush((TileBush) tile, x, y, z);
    }

    public void renderBush(TileBush tile, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0.5F, -1.5F, 0.5F);
        // System.out.println(tile.getType());
        tile.getType().applyTexture(tile.getStage());
        switch(tile.getStage()) {
            case 1:
                model1.render();
                break;
            case 2:
                model2.render();
                break;
            case 3:
                model3.render();
                break;
            case 4:
                model4.render();
                break;
            case 5:
                model5.render();
                break;
            default:
                model1.render();
                break;
        }
        GL11.glPopMatrix();
    }

}
