package Seremis.SoulCraft.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelTransporter;
import Seremis.SoulCraft.client.model.ModelTransporterEngine;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class EntityTransporterRenderer extends Render {

    private ModelTransporter model = new ModelTransporter();
    private ModelTransporterEngine engine = new ModelTransporterEngine();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
        EntityTransporter transporter = (EntityTransporter) entity;

        GL11.glPushMatrix();

        SCRenderHelper.avoidFlickering();

        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotated(transporter.getYaw() + 90, 0, -1, 0);
        GL11.glRotated(transporter.getPitch(), 0, 0, -1);
        GL11.glTranslatef(-0.5F, -0.7F, -0.5F);

        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);
        model.render();

        if(transporter.hasEngine()) {
            SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER_ENGINE);

            engine.render();
        }

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }

}
