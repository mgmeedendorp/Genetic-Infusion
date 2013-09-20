package Seremis.SoulCraft.client.render;

import java.util.Random;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelTransporter;
import Seremis.SoulCraft.client.model.ModelTransporterEngine;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.SoulCraft.tileentity.TileTransporter;

public class TileTransporterRenderer extends TileEntitySpecialRenderer {

    ModelTransporter model = new ModelTransporter();
    ModelTransporterEngine engine = new ModelTransporterEngine();

    float modifier = 0.01F;
    float upvalue = 0;
    int index = 0;
    boolean up = true;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        TileTransporter transporter = (TileTransporter) tile;
        Random random = new Random();
        index = up ? index + random.nextInt(2) : index - random.nextInt(2);

        upvalue = modifier * index;
        upvalue = upvalue / 20;
        if(index >= 100) {
            up = false;
        } else if(index <= -100) {
            up = true;
        }
        GL11.glPushMatrix();

        SCRenderHelper.avoidFlickering();

        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x, (float) y + upvalue, (float) z);

        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);

        model.render(transporter.direction);

        if(transporter.hasEngine()) {
            SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER_ENGINE);

            engine.render(transporter.direction);
        }

        GL11.glPopMatrix();
    }

}
