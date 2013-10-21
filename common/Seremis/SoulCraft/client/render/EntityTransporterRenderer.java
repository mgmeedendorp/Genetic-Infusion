package Seremis.SoulCraft.client.render;

import java.util.Random;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelTransporter;
import Seremis.SoulCraft.client.model.ModelTransporterEngine;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.entity.SCEntity;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class EntityTransporterRenderer extends Render {

    ModelTransporter model = new ModelTransporter();
    ModelTransporterEngine engine = new ModelTransporterEngine();

    float modifier = 0.01F;
    float upvalue = 0;
    int index = 0;
    boolean up = true;

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
        EntityTransporter transporter = (EntityTransporter) entity;
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
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotated(transporter.getYaw() + 90, 0, -1, 0);
        GL11.glTranslatef(-0.5F, upvalue, -0.5F);

        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);
     //   System.out.println(transporter.getYaw());
        model.render();

        if(transporter.hasEngine()) {
            SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER_ENGINE);

            engine.render();
        }

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ((SCEntity)entity).getTexture();
    }


}
