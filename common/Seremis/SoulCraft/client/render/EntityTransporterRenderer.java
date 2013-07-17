package Seremis.SoulCraft.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelTransporter;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class EntityTransporterRenderer extends Render {

    ModelTransporter model = new ModelTransporter();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x - 0.5f, (float) y, (float) z - 0.5f);
        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);
        // model.render();
        GL11.glPopMatrix();

    }

    @Override
    protected ResourceLocation func_110775_a(Entity entity) {
        return null;
    }

}
