package Seremis.SoulCraft.client.render;

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
        float red;
        float green = 0;
        float blue = 0;
        
        if(heat1) {
            red = heatHead/255;
        } else {
            red = heatTail/255;
        }
        
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
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

        double ry = line.getYaw() * f;
        double rp = line.getPitch() * f;
        GL11.glRotated(90.0, 1.0, 0.0, 0.0);
        GL11.glRotated(180.0 + ry, 0.0, 0.0, -1.0);
        GL11.glRotated(rp, 1.0, 0.0, 0.0);

        double width = 0.1D;
        double transitionSpace = 0.1D;

     //   GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
        for(int t = 0; t < 3; t++) {

            GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
            
            setRGBBasedOnHeat(true);
            
            //Draw the first color
            tessellator.startDrawingQuads();
            tessellator.setBrightness(200);
            tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, opacity);
            tessellator.addVertex(-width, line.getLength()/2-transitionSpace, 0.0D);     
            tessellator.addVertex(width, line.getLength()/2-transitionSpace, 0.0D);
            tessellator.addVertex(width, 0.0D, 0.0D);
            tessellator.addVertex(-width, 0.0D, 0.0D);
            tessellator.draw();
            
            setRGBBasedOnHeat(false);
            
            //Draw the second color
            tessellator.startDrawingQuads();
            tessellator.setBrightness(200);
            tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, opacity);
            tessellator.addVertex(-width, line.getLength()/2+transitionSpace, 0.0D);     
            tessellator.addVertex(width, line.getLength()/2+transitionSpace, 0.0D);
            tessellator.addVertex(width, line.getLength(), 0.0D);
            tessellator.addVertex(-width, line.getLength(), 0.0D);
            tessellator.draw();
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