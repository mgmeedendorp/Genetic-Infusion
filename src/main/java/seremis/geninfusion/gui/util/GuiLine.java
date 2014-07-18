package seremis.geninfusion.gui.util;

import org.lwjgl.opengl.GL11;

import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.gui.GIGui;
import seremis.geninfusion.helper.GIRenderHelper;

public class GuiLine extends GuiRectangle {

    public float rotation = 0;
    public float red = 0;
    public float green = 0;
    public float blue = 0;

    public GuiLine() {
        this(0, 0, 0, 0, 0, 0);
    }

    public GuiLine(GuiLine line) {
        this(line.x, line.y, line.w, line.h, line.u, line.v);
        this.rotation = line.rotation;
        this.red = line.red;
        this.blue = line.blue;
        this.green = line.green;
    }

    public GuiLine(int x, int y, int w, int h, int u, int v) {
        super(x, y, w, h, u, v);
    }
    
    public void render(GIGui gui) {
        GL11.glPushMatrix();

        GL11.glColor3f(red, green, blue);

        GIRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK);

        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);

        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        GL11.glTranslatef(x, y, 0);

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
