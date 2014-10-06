package seremis.geninfusion.client.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import seremis.geninfusion.api.magnet.MagnetLink;
import seremis.geninfusion.api.magnet.MagnetLinkHelper;
import seremis.geninfusion.api.util.HeatColorHelper;
import seremis.geninfusion.helper.GIRenderHelper;
import seremis.geninfusion.lib.Localizations;

import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderBeam {

    private int heatHead;
    private int heatTail;
    
    private float red = 0;
    private float green = 0;
    private float blue = 0;
    private float opacity = 0.5F;
    
    private float rotateSpeed = 2F;
    
    private float width = 0.2F;
    
    @SubscribeEvent
    public void onWorldRenderLast(RenderWorldLastEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        Entity entity = Minecraft.getMinecraft().thePlayer;
        
        if(player == null) {
            return;
        }
        
        int visibleDistance = 50;

        if(!Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            visibleDistance = 25;
        }
        
        GL11.glPushMatrix();

        double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks;
        double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks;
        double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks;
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
        
        GIRenderHelper.bindTexture(Localizations.LOC_PARTICLE_TEXTURES + Localizations.BEAM);

        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDepthMask(false);
        
        GL11.glShadeModel(GL11.GL_SMOOTH);
        
        List<MagnetLink> renderLinks = MagnetLinkHelper.instance.getAllLinksInRange(interpPosX, interpPosY, interpPosZ, visibleDistance);
        
        float rot = entity.worldObj.getWorldInfo().getWorldTime() % (360.0F / this.rotateSpeed) * this.rotateSpeed + this.rotateSpeed * event.partialTicks;
        
        for(MagnetLink link : renderLinks) {    
            if(link.dimensionID != entity.dimension) {
                continue;
            }
            
            GL11.glPushMatrix();
            GL11.glTranslated(link.line.head.x, link.line.head.y, link.line.head.z);
            
            GL11.glRotated(90.0, 1.0, 0.0, 0.0);
            GL11.glRotated(180.0 + link.line.getYaw(), 0.0, 0.0, -1.0);
            GL11.glRotated(link.line.getPitch(), 1.0, 0.0, 0.0);
            
            heatHead = link.connector1.getHeat();
            heatTail = link.connector2.getHeat();
            
            GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
            
            for(int t = 0; t < 3; t++) {
                GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
                
                GL11.glBegin(GL11.GL_QUADS);
                
                setRGBBasedOnHeat(true);
                GL11.glColor4f(red, green, blue, opacity);
                
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2d(-width, 0.0D);
                GL11.glTexCoord2f(1, 0);
                GL11.glVertex2d(width, 0.0D);
                
                setRGBBasedOnHeat(false);
                GL11.glColor4f(red, green, blue, opacity);
                
                GL11.glTexCoord2f(1, 1);
                GL11.glVertex2d(width, link.line.getLength());
                GL11.glTexCoord2f(0, 1);
                GL11.glVertex2d(-width, link.line.getLength());
                GL11.glEnd();
            }
            
            GL11.glPopMatrix();
        }

        GL11.glShadeModel(GL11.GL_FLAT);
        
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
            this.red = startRed - scale * stepRed;
        } else {
            this.red = startRed + scale * stepRed;
        }

        if(startGreen > endGreen) {
            this.green = startGreen - scale * stepGreen;
        } else {
            this.green = startGreen + scale * stepGreen;
        }

        if(startBlue > endBlue) {
            this.blue = startBlue - scale * stepBlue;
        } else {
            this.blue = startBlue + scale * stepBlue;
        }
    }
}
