package Seremis.SoulCraft.client.render;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.util.HeatColorHelper;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.helper.SCRenderHelper;

public class RenderBeam {

    private int heatHead;
    private int heatTail;
    
    private float red = 0;
    private float green = 0;
    private float blue = 0;
    private float opacity = 0.4F;
    
    private float rotateSpeed = 5F;
    
    private float width = 0.1F;
    
    @ForgeSubscribe
    public void onWorldRenderLast(RenderWorldLastEvent event) {
        EntityPlayer player = event.context.mc.thePlayer;
        Entity entity = event.context.mc.renderViewEntity;
        
        if(player == null || CommonProxy.proxy.isServerWorld(player.worldObj)) {
            return;
        }
        
        int visibleDistance = 50;
        int transitionSteps = 20;

        if(!Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            visibleDistance = 25;
            transitionSteps = 10;
        }
        
        GL11.glPushMatrix();

        double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks;
        double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks;
        double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks;
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
        
        SCRenderHelper.bindTexture(Localizations.LOC_PARTICLE_TEXTURES + Localizations.BEAM);
        
        GL11.glTexParameterf(3553, 10242, 10497.0F);
        GL11.glTexParameterf(3553, 10243, 10497.0F);

        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        
        List<MagnetLink> renderLinks = MagnetLinkHelper.instance.getAllLinksInRange(interpPosX, interpPosY, interpPosZ, visibleDistance);
        
        float rot = (float)entity.worldObj.getWorldInfo().getWorldTime() % (360.0F / this.rotateSpeed) * this.rotateSpeed + this.rotateSpeed * event.partialTicks;
        
        GL11.glColor3f(1, 0, 1);
        
        for(MagnetLink link : renderLinks) {    
            GL11.glPushMatrix();
            GL11.glTranslated(link.line.head.x, link.line.head.y, link.line.head.z);
            
            GL11.glRotated(90.0, 1.0, 0.0, 0.0);
            GL11.glRotated(180.0 + link.line.getYaw(), 0.0, 0.0, -1.0);
            GL11.glRotated(link.line.getPitch(), 1.0, 0.0, 0.0);
            
            heatHead = link.connector1.getHeat();
            heatHead = link.connector2.getHeat();
            
            double transitionSpace = link.line.getLength() / 10;
            
            GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
            
            for(int t = 0; t < 3; t++) {
                GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);

                setRGBBasedOnHeat(true);

                // Draw the first color
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glColor4f(red, green, blue, opacity);
                GL11.glVertex2d(-width, 0.0D);
                GL11.glVertex2d(width, 0.0D);
                GL11.glVertex2d(width, link.line.getLength() / 2 - transitionSpace);
                GL11.glVertex2d(-width, link.line.getLength() / 2 - transitionSpace);
                GL11.glEnd();

                // Draw the transition
                double transitionPieceLength = transitionSpace * 2 / transitionSteps;

                for(int i = 0; i < transitionSteps; i++) {
                    transitRGB(i, transitionSteps);
                    GL11.glColor4d(red, green, blue, opacity);

                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex2d(-width, link.line.getLength() / 2 - transitionSpace + i * transitionPieceLength);
                    GL11.glVertex2d(width, link.line.getLength() / 2 - transitionSpace + i * transitionPieceLength);
                    GL11.glVertex2d(width, link.line.getLength() / 2 - transitionSpace + i * transitionPieceLength + transitionPieceLength);
                    GL11.glVertex2d(-width, link.line.getLength() / 2 - transitionSpace + i * transitionPieceLength + transitionPieceLength);
                    GL11.glEnd();
                }

                setRGBBasedOnHeat(false);

                // Draw the second color
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glColor4f(this.red, this.green, this.blue, opacity);
                GL11.glVertex2d(width, link.line.getLength() / 2 + transitionSpace);
                GL11.glVertex2d(-width, link.line.getLength() / 2 + transitionSpace);
                GL11.glVertex2d(-width, link.line.getLength());
                GL11.glVertex2d(width, link.line.getLength());
                GL11.glEnd();
            }
            
            GL11.glPopMatrix();
//            GL11.glRotatef(-rot, 0.0F, 1.0F, 0.0F);
//            GL11.glRotated(-link.line.getPitch(), 1.0, 0.0, 0.0);
//            GL11.glRotated((-180.0 + link.line.getYaw()), 0.0, 0.0, -1.0);
//            GL11.glTranslated(-link.line.head.x, -link.line.head.y, -link.line.head.z);
        }
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glPopMatrix();
    }
    
    public void setRGBBasedOnHeat(boolean heat1) {
        if(heat1) {
            Color color = HeatColorHelper.instance.convertHeatToColor(heatHead);
            red = color.getRed();
            green = color.getGreen();
            blue = color.getBlue();
        } else {
            Color color = HeatColorHelper.instance.convertHeatToColor(heatTail);
            red = color.getRed();
            green = color.getGreen();
            blue = color.getBlue();
        }
    }

    public void transitRGB(int scale, int maxScale) {
        setRGBBasedOnHeat(true);

        float startRed = red;
        float startGreen = green;
        float startBlue = blue;

        setRGBBasedOnHeat(false);

        float endRed = red;
        float endGreen = green;
        float endBlue = blue;

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
}
