package Seremis.SoulCraft.gui.util;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.gui.SCGui;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class GuiLine extends GuiRectangle {

    public float rotation = 0;
    public float red = 0;
    public float green = 0;
    public float blue = 0;
    
    public GuiLine() {
        this(0, 0, 0, 0);
    }
    
    public GuiLine(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void render(SCGui gui) {
        GL11.glPushMatrix();

        GL11.glColor3f(red, green, blue);

        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK);
        
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
        
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        
        GL11.glTranslatef(x, y, 0.0F);
        
        GL11.glTranslatef(w / 2, h, 0);
        GL11.glRotated(180 + rotation, 0.0F, 0.0F, -1.0F);
        GL11.glTranslatef(-w / 2, -h, 0);        
        
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(0, 0);
            GL11.glVertex2f(w, 0);
            GL11.glVertex2f(w, h);
            GL11.glVertex2f(0, h);
        GL11.glEnd();
        
        GL11.glDisable(GL11.GL_BLEND);
        
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }

}
