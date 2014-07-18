package seremis.geninfusion.client.render;

import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.helper.GIRenderHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockMonsterEggRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        renderMonsterEgg(block, 0, 0, 0, renderer, true);
        block.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        renderMonsterEgg(block, x, y, z, renderer, false);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return RenderIds.MonsterEggRenderID;
    }

    public boolean renderMonsterEgg(Block block, int x, int y, int z, RenderBlocks renderer, boolean item) {
        boolean var5 = false;
        int var6 = 0;

        for(int i = 0; i < 8; ++i) {
            byte var8 = 0;
            byte var9 = 1;

            if(i == 0) {
                var8 = 2;
            }

            if(i == 1) {
                var8 = 3;
            }

            if(i == 2) {
                var8 = 4;
            }

            if(i == 3) {
                var8 = 5;
                var9 = 2;
            }

            if(i == 4) {
                var8 = 6;
                var9 = 3;
            }

            if(i == 5) {
                var8 = 7;
                var9 = 5;
            }

            if(i == 6) {
                var8 = 6;
                var9 = 2;
            }

            if(i == 7) {
                var8 = 3;
            }

            float var10 = var8 / 16.0F;
            float var11 = 1.0F - var6 / 16.0F;
            float var12 = 1.0F - (var6 + var9) / 16.0F;
            var6 += var9;
            if(!item) {
                renderer.setRenderBounds(0.5F - var10, var12, 0.5F - var10, 0.5F + var10, var11, 0.5F + var10);
                renderer.renderStandardBlock(block, x, y, z);
            }
            if(item) {
                block.setBlockBounds(0.5F - var10, var12, 0.5F - var10, 0.5F + var10, var11, 0.5F + var10);
                renderer.setRenderBoundsFromBlock(block);
                GIRenderHelper.renderAllFaces(block, renderer, block.getBlockTextureFromSide(0));
            }
        }

        var5 = true;

        return var5;
    }

}
