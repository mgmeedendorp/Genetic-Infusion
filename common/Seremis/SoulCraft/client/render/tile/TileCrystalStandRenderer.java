package Seremis.SoulCraft.client.render.tile;

import java.util.Random;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.tileentity.TileCrystalStand;

public class TileCrystalStandRenderer extends TileEntitySpecialRenderer {

	private ModelCrystal crystal;
	private ModelCrystalStand crystalStand;
	
	public TileCrystalStandRenderer() {
		crystal =  new ModelCrystal();
		crystalStand = new ModelCrystalStand();
	}
	
	public void renderCrystal(TileCrystalStand tile, float x, float y, float z, Random rand, float size) {
	    GL11.glPushMatrix();
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    GL11.glTranslatef(x + 0.43F, y - 0.05F*size, z + 0.43F);
	    GL11.glScalef((0.15F + rand.nextFloat() * 0.075F) * size, (0.5F + rand.nextFloat() * 0.1F) * size, (0.15F + rand.nextFloat() * 0.05F) * size);
	    crystal.render();
	    GL11.glScalef(1.0F, 1.0F, 1.0F);
	    GL11.glPopMatrix();
	}
	
	public void renderCrystalStand(TileCrystalStand tile, float x, float y, float z, Random rand, float size) {
	    GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
	    crystalStand.render();
	    GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		if(tile != null && tile instanceof TileCrystalStand) {
		    TileCrystalStand tco = (TileCrystalStand)tile;
		    bindTextureByName(Localizations.LOC_MODEL_TEXTURES + Localizations.BLANK);
	        Random rand = new Random(tco.xCoord + tco.yCoord * tco.zCoord);
	        try {
    	        if(tco.inv != null && tco.getStackInSlot(0) != null && tco.getStackInSlot(0).itemID == ModBlocks.crystal.blockID) {
    	            renderCrystal(tco, (float)x, (float)y, (float)z, rand, 0.6F);
    	        }
    	        } catch(Exception ex) {
    	            ex.printStackTrace();
    	        }
	        renderCrystalStand(tco, (float)x, (float)y, (float)z, rand, 1F);
		}	
		
	}
}
