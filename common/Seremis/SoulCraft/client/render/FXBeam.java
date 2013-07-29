package Seremis.SoulCraft.client.render;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.helper.SCRenderHelper;
import Seremis.core.geometry.Coordinate3D;
import Seremis.core.geometry.Line3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Based off Modular Force Field's Beam Renderer.
 * 
 * @author Calclavia, Seremis
 * 
 */
@SideOnly(Side.CLIENT)
public class FXBeam extends EntityFX {
 
    private Line3D line = null;
    
    private int heatHead = 0;
    private int heatTail = 0;
    
    private double red = 0;
    private double green = 0;
    private double blue = 0;
    
    public FXBeam(World world, Coordinate3D position, Coordinate3D target, int heatAtCoord1, int heatAtCoord2) {
        super(world, position.x, position.y, position.z, 0.0D, 0.0D, 0.0D);

        this.setSize(0.02F, 0.02F);
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

        particleMaxAge = 1;

        /**
         * Sets the particle age based on distance.
         */
        EntityLivingBase renderentity = (EntityLivingBase) Minecraft.getMinecraft().renderViewEntity;
        int visibleDistance = 50;

        if(!Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            visibleDistance = 25;
        }
        if(renderentity != null && renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) {
            this.particleMaxAge = 0;
        }
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        if(this.particleAge++ >= this.particleMaxAge) {
            setDead();
        }
    }

    public void setRGBBasedOnHeat(boolean heat1) {
        float red = 0;
        float green = 0;
        float blue = 0;
        
        if(heat1) {
            red = heatHead*0.1275F;
            green = 20;
            blue = 1;
        } else {
            blue = 5;
            green = 20;
        }
        
        this.particleRed = red/255;
        this.particleGreen = green/255;
        this.particleBlue = blue/255;
    }
    
    public void transitRGB(int stage, int steps) {
        setRGBBasedOnHeat(true);
        float r1 = particleRed;
        float g1 = particleGreen;
        float b1 = particleBlue;
        setRGBBasedOnHeat(false);
        float r2 = particleRed;
        float g2 = particleGreen;
        float b2 = particleBlue;
        
        float dRed = r1-r2;
        float dGreen = g1-g2;
        float dBlue = b1-b2;
        
        setRGBBasedOnHeat(true);
        
        this.red = particleRed - dRed*stage;
        this.green = particleGreen - dGreen*stage;
        this.blue = particleBlue - dBlue*stage;
        
        float ratio = (float) stage / (float) steps;
        this.red = (int) (r2 * ratio + r1 * (1 - ratio));
        this.green = (int) (g2 * ratio + g1 * (1 - ratio));
        this.blue = (int) (b2 * ratio + b1 * (1 - ratio));
    }

    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        tessellator.draw();
        
        GL11.glPushMatrix();

        float opacity = 0.5F;

        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK);

        GL11.glTexParameterf(3553, 10242, 10497.0F);
        GL11.glTexParameterf(3553, 10243, 10497.0F);
        
        SCRenderHelper.avoidFlickering();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 1);
        GL11.glDepthMask(false);

        float xx = (float) (this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
        float yy = (float) (this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
        float zz = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
        GL11.glTranslated(xx, yy, zz);

        GL11.glRotated(90.0, 1.0, 0.0, 0.0);
        GL11.glRotated(180.0 + line.getYaw(), 0.0, 0.0, -1.0);
        GL11.glRotated(line.getPitch(), 1.0, 0.0, 0.0);

        double width = 0.1D;
        double transitionSpace = 0.1D;
        
        for(int t = 0; t < 3; t++) {

            GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
            
            setRGBBasedOnHeat(true);
            
            //Draw the first color
//            tessellator.startDrawingQuads();
//            tessellator.setBrightness(200);
//            tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, opacity);
//            tessellator.addVertex(-width, 0.0D, 0.0D);
//            tessellator.addVertex(width, 0.0D, 0.0D);
//            tessellator.addVertex(width, line.getLength()/2-transitionSpace, 0.0D);     
//            tessellator.addVertex(-width, line.getLength()/2-transitionSpace, 0.0D);
//            tessellator.draw();
            
            //Draw the transition
            int steps = 200;
            double translationPieceLength = transitionSpace*2/steps;
            
            for(int i = 0; i<steps; i++) {
                transitRGB(i, steps);
                GL11.glColor4d(this.red, this.green, this.blue, 1F);
                
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(width, line.getLength()/2-transitionSpace+i*translationPieceLength);
                GL11.glVertex2d(-width, line.getLength()/2-transitionSpace+i*translationPieceLength);
                GL11.glVertex2d(-width, line.getLength()/2-transitionSpace+i*translationPieceLength +translationPieceLength);
                GL11.glVertex2d(width, line.getLength()/2-transitionSpace+i*translationPieceLength + translationPieceLength);
                GL11.glEnd();
            }
            
            setRGBBasedOnHeat(false);
            
            //Draw the second color
//            tessellator.startDrawingQuads();
//            tessellator.setBrightness(200);
//            tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, opacity);
//            tessellator.addVertex(-width, line.getLength()/2+transitionSpace, 0.0D);     
//            tessellator.addVertex(width, line.getLength()/2+transitionSpace, 0.0D);
//            tessellator.addVertex(width, line.getLength(), 0.0D);
//            tessellator.addVertex(-width, line.getLength(), 0.0D);
//            tessellator.draw();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        SCRenderHelper.stopFlickerAvoiding();
        GL11.glPopMatrix();
        
        tessellator.startDrawingQuads();
        
        SCRenderHelper.bindTexture("minecraft:textures/particle/particles.png");
    }
}