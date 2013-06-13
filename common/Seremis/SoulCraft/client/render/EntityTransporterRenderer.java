package Seremis.SoulCraft.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelTransporter;
import Seremis.SoulCraft.core.lib.Localizations;
import cpw.mods.fml.client.FMLClientHandler;

public class EntityTransporterRenderer extends Render {

    ModelTransporter model = new ModelTransporter();
    
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x - 0.5f, (float)y, (float)z - 0.5f);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);
        model.render(0);
        GL11.glPopMatrix();
    }

}
