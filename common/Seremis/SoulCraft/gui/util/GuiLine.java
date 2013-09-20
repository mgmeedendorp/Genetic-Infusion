package Seremis.SoulCraft.gui.util;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.api.util.Coordinate2D;
import Seremis.SoulCraft.api.util.Line2D;
import Seremis.SoulCraft.api.util.Line3D;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class GuiLine extends Line2D {

    public GuiLine() {
        this(null, null);
    }

    public GuiLine(Coordinate2D head, Coordinate2D tail) {
        super(head, tail);
    }

    public GuiLine(double x1, double y1, double x2, double y2) {
        this(new Coordinate2D(x1, y1), new Coordinate2D(x2, y2));
    }

    @Override
    public GuiLine setLine(Line3D line) {
        super.setLine(line);
        return this;
    }

    public void render(float r, float g, float b, float a) {
        GL11.glPushMatrix();
        GL11.glColor4f(r, g, b, a);
        SCRenderHelper.avoidFlickering();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDepthMask(false);

        GL11.glTranslated(head.getXCoord(), head.getYCoord(), 0);

        GL11.glRotated(90.0, 1.0, 0.0, 0.0);
        // scale

        double width = 0.1D;

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(-width, 0.0D);
        GL11.glVertex2d(width, 0.0D);
        GL11.glVertex2d(width, getLength());
        GL11.glVertex2d(-width, getLength());
        GL11.glEnd();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        SCRenderHelper.stopFlickerAvoiding();
        GL11.glPopMatrix();
    }

}
