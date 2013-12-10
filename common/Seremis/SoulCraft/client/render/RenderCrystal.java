package Seremis.SoulCraft.client.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelCrystal;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class RenderCrystal {

    public static RenderCrystal instance = new RenderCrystal();

    private ModelCrystal model = new ModelCrystal();

    public void renderCrystal(double x, double y, double z, float scale, int heat, int red, int green, int blue) {
        //TODO remove this when finished
        instance = new RenderCrystal();

        Random rand = new Random(0);

        GL11.glPushMatrix();

        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.CRYSTAL);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glTranslatef((float) x + 0.35F, (float) y - 0.85F, (float) z + 0.35F);

        GL11.glColor4f(red, green, blue, (float) (1+heat*0.1));

        if(heat > 0) {
            float shakeX = (float) (new Random().nextInt(heat) * 0.00005);
            float shakeY = (float) (new Random().nextInt(heat) * 0.00005);
            float shakeZ = (float) (new Random().nextInt(heat) * 0.00005);
            GL11.glTranslatef(shakeX, shakeY, shakeZ);
        }

        GL11.glScalef(0.15F + rand.nextFloat() * 0.075F, 0.5F + rand.nextFloat() * 0.1F, 0.15F + rand.nextFloat() * 0.05F);
        GL11.glScalef(scale, scale, scale);

        model.render();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor3f(1, 1, 1);
        GL11.glPopMatrix();
    }

}
