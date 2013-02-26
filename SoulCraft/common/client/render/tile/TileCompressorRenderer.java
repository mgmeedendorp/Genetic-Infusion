package voidrunner101.SoulCraft.common.client.render.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import voidrunner101.SoulCraft.common.tileentity.TileCompressor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileCompressorRenderer extends TileEntitySpecialRenderer {

	private RenderItem renderItems;
	private RenderBlocks renderBlocks;
	private Minecraft    mc;
	
	public TileCompressorRenderer() {
		mc = FMLClientHandler.instance().getClient();
		renderBlocks = new RenderBlocks();
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
          GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);     
          GL11.glScalef(1.4F, 1.4F, 1.4F);

          ItemStack item = tile.getStackInSlot(0);
          EntityItem entityItem = new EntityItem(tile.worldObj);
          if(item != null && item.stackSize>0) {
        	  entityItem.func_92058_a(item);
        	  entityItem.hoverStart = 10.0F;
        	  item.stackSize=1;
        	  renderItems.doRenderItem(entityItem, 0, 0, 0, 0, 0);
          }
          GL11.glPopMatrix();
	}
}
