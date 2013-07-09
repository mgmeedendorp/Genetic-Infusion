package Seremis.SoulCraft.helper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class SCRenderHelper {
	
	public static void renderAllFaces(Block block, RenderBlocks renderer, Icon tex) {
		renderAllFaces(block, renderer, tex,  tex,  tex, tex, tex, tex);
	}
	
	public static void renderAllFaces(Block block, RenderBlocks renderer, Icon tex1, Icon tex2, Icon tex3, Icon tex4, Icon tex5, Icon tex6) {
		Tessellator tessellator = Tessellator.instance;
		if(tex1 != null && tex2 != null && tex3 != null && tex4 != null && tex5 != null && tex6 != null) {
    		try{
    		    if(tessellator.isDrawing) return;
    			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
    			tessellator.startDrawingQuads();
    			tessellator.setNormal(0.0F, -1.0F, 0.0F);
    			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, tex1);
    			tessellator.draw();
    			tessellator.startDrawingQuads();
    			tessellator.setNormal(0.0F, 1.0F, 0.0F);
    			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, tex2);
    			tessellator.draw();
    			tessellator.startDrawingQuads();
    			tessellator.setNormal(0.0F, 0.0F, -1.0F);
    			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, tex3);
    			tessellator.draw();
    			tessellator.startDrawingQuads();
    			tessellator.setNormal(0.0F, 0.0F, 1.0F);
    			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, tex4);
    			tessellator.draw();
    			tessellator.startDrawingQuads();
    			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
    			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, tex5);
    			tessellator.draw();
    			tessellator.startDrawingQuads();
    			tessellator.setNormal(1.0F, 0.0F, 0.0F);
    			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, tex6);
    			tessellator.draw();
    			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    		}catch(Exception ex){ex.printStackTrace();}	
    	}
	}
	
	public static void bindTexture(String tex) {
	    FMLClientHandler.instance().getClient().renderEngine.bindTexture(tex);
	}
}
