package Seremis.SoulCraft.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.api.util.Line3D;
import Seremis.SoulCraft.helper.SCRenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FXBeam extends EntityFX {

    private Line3D line = null;

    private int heatHead = 0;
    private int heatTail = 0;

    private double red = 0;
    private double green = 0;
    private double blue = 0;

    private boolean render = true;

    public FXBeam(World world, Coordinate3D position, Coordinate3D target, int heatAtCoord1, int heatAtCoord2) {
        super(world, position.x, position.y, position.z);

        this.noClip = true;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.line = new Line3D(position, target);
        this.heatHead = heatAtCoord1;
        this.heatTail = heatAtCoord2;
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;        
        this.setSize((float) line.getLength(), 0.02F);

        particleMaxAge = 2;
        this.setRGBBasedOnHeat(true);

        EntityLivingBase renderentity = (EntityLivingBase) Minecraft.getMinecraft().renderViewEntity;
        int visibleDistance = 50;

        if(!Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            visibleDistance = 25;
        }
        if(renderentity != null && renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) {
            render = false;
        }
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if(this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }

    public void setRGBBasedOnHeat(boolean heat1) {
        float red = 0;
        float green = 0;
        float blue = 0;

        if(heat1) {
            red = 160;
            green = 70;
            blue = 220;
        } else {
            red = 160;
            green = 70;
            blue = 220;
        }

        this.particleRed = red / 255;
        this.particleGreen = green / 255;
        this.particleBlue = blue / 255;
    }

    public void transitRGB(int scale, int maxScale) {
        setRGBBasedOnHeat(true);

        float startRed = this.particleRed;
        float startGreen = this.particleGreen;
        float startBlue = this.particleBlue;

        setRGBBasedOnHeat(false);

        float endRed = this.particleRed;
        float endGreen = this.particleGreen;
        float endBlue = this.particleBlue;

        float dRed = Math.abs(startRed - endRed);
        float dGreen = Math.abs(startGreen - endGreen);
        float dBlue = Math.abs(startBlue - endBlue);

        float stepRed = dRed / maxScale;
        float stepGreen = dGreen / maxScale;
        float stepBlue = dBlue / maxScale;

        if(startRed > endRed) {
            float scaledRed = startRed - scale * stepRed;
            this.red = scaledRed;
        } else {
            float scaledRed = startRed + scale * stepRed;
            this.red = scaledRed;
        }

        if(startGreen > endGreen) {
            float scaledGreen = startGreen - scale * stepGreen;
            this.green = scaledGreen;
        } else {
            float scaledGreen = startGreen + scale * stepGreen;
            this.green = scaledGreen;
        }

        if(startBlue > endBlue) {
            float scaledBlue = startBlue - scale * stepBlue;
            this.blue = scaledBlue;
        } else {
            float scaledBlue = startBlue + scale * stepBlue;
            this.blue = scaledBlue;
        }
    }

    @Override
    public void renderParticle(Tessellator tess, float partialTickTime, float f1, float f2, float f3, float f4, float f5) {
        
        if(render) {
            float opacity = 4F;

            GL11.glPushMatrix();

            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            
            float xx = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTickTime - interpPosX);
            float yy = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTickTime - interpPosY);
            float zz = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTickTime - interpPosZ);
            GL11.glTranslated(xx, yy, zz);

            GL11.glRotated(90.0, 1.0, 0.0, 0.0);
            GL11.glRotated(180.0 + line.getYaw(), 0.0, 0.0, -1.0);
            GL11.glRotated(line.getPitch(), 1.0, 0.0, 0.0);

            double width = 0.1D;
            double transitionSpace = line.getLength() / 10;

            for(int t = 0; t < 3; t++) {
                GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);

                setRGBBasedOnHeat(true);

                // Draw the first color
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glColor4f(this.particleRed, this.particleGreen, this.particleBlue, opacity);
                GL11.glVertex2d(-width, 0.0D);
                GL11.glVertex2d(width, 0.0D);
                GL11.glVertex2d(width, line.getLength() / 2 - transitionSpace);
                GL11.glVertex2d(-width, line.getLength() / 2 - transitionSpace);
                GL11.glEnd();

                // Draw the transition
                int steps = (int) 60;
                double translationPieceLength = transitionSpace * 2 / steps;

                for(int i = 0; i < steps; i++) {
                    transitRGB(i, steps);
                    GL11.glColor4d(this.red, this.green, this.blue, opacity);

                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex2d(width, line.getLength() / 2 - transitionSpace + i * translationPieceLength);
                    GL11.glVertex2d(-width, line.getLength() / 2 - transitionSpace + i * translationPieceLength);
                    GL11.glVertex2d(-width, line.getLength() / 2 - transitionSpace + i * translationPieceLength + translationPieceLength);
                    GL11.glVertex2d(width, line.getLength() / 2 - transitionSpace + i * translationPieceLength + translationPieceLength);
                    GL11.glEnd();
                }

                setRGBBasedOnHeat(false);

                // Draw the second color
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glColor4f(this.particleRed, this.particleGreen, this.particleBlue, opacity);
                GL11.glVertex2d(-width, line.getLength() / 2 + transitionSpace);
                GL11.glVertex2d(width, line.getLength() / 2 + transitionSpace);
                GL11.glVertex2d(width, line.getLength());
                GL11.glVertex2d(-width, line.getLength());
                GL11.glEnd();
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            
            GL11.glPopMatrix();
        }
        SCRenderHelper.bindTexture("minecraft:textures/particle/particles.png");
    }
}