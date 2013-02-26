package voidrunner101.SoulCraft.common.helper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.ModLoader;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public class SCRenderHelper {
	
	public static void renderAllFaces(Block block, RenderBlocks renderer, int tex, boolean st) {
		renderAllFaces(block, renderer, tex,  tex,  tex, tex, tex, tex, st);
	}
	
	public static void renderAllFaces(Block block, RenderBlocks renderer, int tex1, int tex2, int tex3, int tex4, int tex5, int tex6, boolean solidTop) {
		Tessellator var4 = Tessellator.instance;
		try{
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, tex1);
			var4.draw();
			if(solidTop) GL11.glDisable(3008);
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, tex2);
			if(solidTop) GL11.glDisable(3008);
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, tex3);
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, tex4);
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, tex5);
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, tex6);
			var4.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}catch(Exception ex){ex.printStackTrace();}	
	}
}
