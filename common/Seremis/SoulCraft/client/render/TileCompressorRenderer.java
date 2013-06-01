package Seremis.SoulCraft.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.tileentity.TileCompressor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileCompressorRenderer extends TileEntitySpecialRenderer {

	private RenderItem renderItems;
	private Minecraft    mc;
	public boolean renderItemShrink = false;
	
	public TileCompressorRenderer() {
		mc = FMLClientHandler.instance().getClient();
		new RenderBlocks();
	    renderItems = new RenderItem();
	    renderItems.setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
			if(tile instanceof TileCompressor && !mc.isGamePaused && ((TileCompressor)tile).anyPlayerInRange()) {
				TileCompressor tileComp = (TileCompressor)tile;
				doRenderItem(tileComp, x, y, z);
			}
	}
	
	@SideOnly(Side.CLIENT)
	public void doRenderItem(TileCompressor tile, double x, double y, double z) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.2F, (float)z + 0.5F);     
        GL11.glScalef(1.4F, 1.4F, 1.4F);
        ItemStack item = tile.getStackInSlot(0);
        
        EntityItem entityItem = new EntityItem(tile.worldObj);
        if(item != null && item.stackSize>0) {
        	entityItem.setEntityItemStack(item);
        	entityItem.hoverStart = 0.0F;
        	item.stackSize=1;
        	renderItems.doRenderItem(entityItem, 0, 0, 0, 0, 0);
        }
        GL11.glPopMatrix();
	}
}	
