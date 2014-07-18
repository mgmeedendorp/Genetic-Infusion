package seremis.geninfusion.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import seremis.geninfusion.api.util.Coordinate3D;
import seremis.geninfusion.api.util.Line3D;
import seremis.geninfusion.lib.Localizations;
import seremis.geninfusion.helper.GIRenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


//Legacy code for the beam renderer
@SideOnly(Side.CLIENT)
public class FXBeamLegacy extends EntityFX {

    private Line3D line = null;

    private double red = 0;
    private double green = 0;
    private double blue = 0;

    private boolean render = true;
    
    private int redHead;
    private int greenHead;
    private int blueHead;
    
    private int redTail;
    private int greenTail;
    private int blueTail;
    
    private int transitionSteps = 20;

    public FXBeamLegacy(World world, Coordinate3D position, Coordinate3D target, int r1, int g1, int b1, int r2, int g2, int b2) {
        super(world, position.x, position.y, position.z);

        this.noClip = true;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        
        this.line = new Line3D(position, target);
        
        this.redHead = r1;
        this.greenHead = g1;
        this.blueHead = b1;
        this.redTail = r2;
        this.greenTail = g2;
        this.blueTail = b2;
        
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
        setSize((float) line.getLength(), 0.2F);

        particleMaxAge = 2;
        setRGBBasedOnHeat(true);

        EntityLivingBase renderentity = Minecraft.getMinecraft().renderViewEntity;
        int visibleDistance = 50;

        if(!Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            visibleDistance = 25;
            transitionSteps = 10;
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
            setDead();
        }
    }

    public void setRGBBasedOnHeat(boolean heat1) {
        float red = 0;
        float green = 0;
        float blue = 0;

        if(heat1) {
            red = redHead;
            green = greenHead;
            blue = blueHead;
        } else {
            red = redTail;
            green = greenTail;
            blue = blueTail;
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

    private float rotateSpeed = 5F;
    
    @Override
    public void renderParticle(Tessellator tess, float partialTickTime, float f1, float f2, float f3, float f4, float f5) {
        if(render) {
            float opacity = 0.4F;

            float rot = this.worldObj.getWorldInfo().getWorldTime() % (360.0F / this.rotateSpeed) * this.rotateSpeed + this.rotateSpeed * partialTickTime;
            
            GL11.glPushMatrix();

            GIRenderHelper.bindTexture(Localizations.LOC_PARTICLE_TEXTURES + Localizations.BEAM);
            
            GL11.glTexParameterf(3553, 10242, 10497.0F);
            GL11.glTexParameterf(3553, 10243, 10497.0F);

            GL11.glDisable(GL11.GL_CULL_FACE);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDepthMask(false);

            float xx = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTickTime - interpPosX);
            float yy = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTickTime - interpPosY);
            float zz = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTickTime - interpPosZ);
            GL11.glTranslated(xx, yy, zz);
            
            GL11.glRotated(90.0, 1.0, 0.0, 0.0);
            GL11.glRotated(180.0 + line.getYaw(), 0.0, 0.0, -1.0);
            GL11.glRotated(line.getPitch(), 1.0, 0.0, 0.0);

            double width = 0.1D;
            double transitionSpace = line.getLength() / 10;

            GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
            
            for(int t = 0; t < 3; t++) {
                GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);

                setRGBBasedOnHeat(true);

                // Draw the first color
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glColor4f(particleRed, particleGreen, particleBlue, opacity);
                GL11.glVertex2d(-width, 0.0D);
                GL11.glVertex2d(width, 0.0D);
                GL11.glVertex2d(width, line.getLength() / 2 - transitionSpace);
                GL11.glVertex2d(-width, line.getLength() / 2 - transitionSpace);
                GL11.glEnd();

                // Draw the transition
                double transitionPieceLength = transitionSpace * 2 / transitionSteps;

                for(int i = 0; i < transitionSteps; i++) {
                    transitRGB(i, transitionSteps);
                    GL11.glColor4d(red, green, blue, opacity);

                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex2d(-width, line.getLength() / 2 - transitionSpace + i * transitionPieceLength);
                    GL11.glVertex2d(width, line.getLength() / 2 - transitionSpace + i * transitionPieceLength);
                    GL11.glVertex2d(width, line.getLength() / 2 - transitionSpace + i * transitionPieceLength + transitionPieceLength);
                    GL11.glVertex2d(-width, line.getLength() / 2 - transitionSpace + i * transitionPieceLength + transitionPieceLength);
                    GL11.glEnd();
                }

                setRGBBasedOnHeat(false);

                // Draw the second color
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glColor4f(this.particleRed, this.particleGreen, this.particleBlue, opacity);
                GL11.glVertex2d(width, line.getLength() / 2 + transitionSpace);
                GL11.glVertex2d(-width, line.getLength() / 2 + transitionSpace);
                GL11.glVertex2d(-width, line.getLength());
                GL11.glVertex2d(width, line.getLength());
                GL11.glEnd();
            }

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_CULL_FACE);            
            
            GL11.glPopMatrix();
        }
        GIRenderHelper.bindTexture("minecraft:textures/particle/particles.png");
    }
}