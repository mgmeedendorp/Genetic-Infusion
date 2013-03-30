package Seremis.SoulCraft.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.helper.SCRenderHelper;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockMonsterEggRenderer implements ISimpleBlockRenderingHandler{
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		renderMonsterEgg( block, 0, 0, 0, renderer, true);
        block.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderMonsterEgg(block, x, y, z, renderer, false);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.MonsterEggRenderID;
	}
	
	public boolean renderMonsterEgg(Block block, int x, int y, int z, RenderBlocks renderer, boolean item) {
		boolean var5 = false;
        int var6 = 0;

        for (int var7 = 0; var7 < 8; ++var7)
        {
            byte var8 = 0;
            byte var9 = 1;

            if (var7 == 0)
            {
                var8 = 2;
            }

            if (var7 == 1)
            {
                var8 = 3;
            }

            if (var7 == 2)
            {
                var8 = 4;
            }

            if (var7 == 3)
            {
                var8 = 5;
                var9 = 2;
            }

            if (var7 == 4)
            {
                var8 = 6;
                var9 = 3;
            }

            if (var7 == 5)
            {
                var8 = 7;
                var9 = 5;
            }

            if (var7 == 6)
            {
                var8 = 6;
                var9 = 2;
            }

            if (var7 == 7)
            {
                var8 = 3;
            }

            float var10 = (float)var8 / 16.0F;
            float var11 = 1.0F - (float)var6 / 16.0F;
            float var12 = 1.0F - (float)(var6 + var9) / 16.0F;
            var6 += var9;
            if(!item){
            	renderer.setRenderBounds((double)(0.5F - var10), (double)var12, (double)(0.5F - var10), (double)(0.5F + var10), (double)var11, (double)(0.5F + var10));
                renderer.renderStandardBlock(block, x, y, z);
            }
            if(item){
            	block.setBlockBounds((float)(0.5F - var10), (float)var12, (float)(0.5F - var10), (float)(0.5F + var10), (float)var11, (float)(0.5F + var10));
            	renderer.setRenderBoundsFromBlock(block);
            	SCRenderHelper.renderAllFaces(block, renderer, block.getBlockTextureFromSide(0));
            }
        }

        var5 = true;
        
        return var5;
	}

}
