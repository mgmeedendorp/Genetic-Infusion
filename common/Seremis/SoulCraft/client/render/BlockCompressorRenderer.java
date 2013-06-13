package Seremis.SoulCraft.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.helper.SCRenderHelper;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockCompressorRenderer implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		renderBlockCompressor(block, 0, 0, 0, renderer, true);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderBlockCompressor(block, x, y, z, renderer, false);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.CompressorRenderID;
	}
	
	public boolean renderBlockCompressor(Block block, int x, int y, int z, RenderBlocks renderer, boolean item) {
       
		for(int var7 = 0; var7<10; var7++) {
			float var1 = 0f;
		    float var2 = 0f;
		    float var3 = 0f;
		    float var4 = 0f;
		    float var5 = 0f;
		    float var6 = 0f;
		    
			switch(var7) {
				case 0: var1 = 0f;
						var2 = 0f;
						var3 = 0f;
						var4 = 16f;
						var5 = 1f;
						var6 = 16f;
						break;
				case 1: var1 = 0f;
						var2 = 1f;
						var3 = 0f;
						var4 = 16f;
						var5 = 15f;
						var6 = 1f;
						break;
				case 2: var1 = 0f;
						var2 = 1f;
						var3 = 1f;
						var4 = 1f;
						var5 = 15f;
						var6 = 15f;
						break;
				case 3: var1 = 15f;
						var2 = 1f;
						var3 = 1f;
						var4 = 16f;
						var5 = 15f;
						var6 = 15f;
						break;
				case 4: var1 = 0f;
						var2 = 1f;
						var3 = 15f;
						var4 = 16f;
						var5 = 15f;
						var6 = 16f;
						break;
				case 5: var1 = 0f;
						var2 = 15f;
						var3 = 0f;
						var4 = 4f;
						var5 = 16f;
						var6 = 16f;
						break;
				case 6:	var1 = 4f;
						var2 = 15f;
						var3 = 0f;
						var4 = 12f;
						var5 = 16f;
						var6 = 4f;
						break;
				case 7: var1 = 12f;
						var2 = 15f;
						var3 = 0f;
						var4 = 16f;
						var5 = 16f;
						var6 = 16f;
						break;
				case 8: var1 = 4f;
						var2 = 15f;
						var3 = 12f;
						var4 = 12f;
						var5 = 16f;
						var6 = 16f;
						break;
				}
		
			float var8 = var1/16;
			float var9 = var2/16;
			float var10 = var3/16;
			float var11 = var4/16;
			float var12 = var5/16;
			float var13 = var6/16;
		
			if(!item){
            	renderer.setRenderBounds(var8, var9, var10, var11, var12, var13);
            	renderer.renderStandardBlock(block, x, y, z);
        	}
        	if(item){
            	block.setBlockBounds((float)var8, (float)var9, (float)var10, (float)var11, (float)var12, (float)var13);
            	renderer.setRenderBoundsFromBlock(block);
            	SCRenderHelper.renderAllFaces(block, renderer, block.getIcon(0, 0), block.getIcon(1, 0), block.getIcon(2, 0), block.getIcon(3, 0), block.getIcon(4, 0) ,block.getIcon(5, 0));
        	}
		}
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }
}
