package Seremis.SoulCraft.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.block.BlockBush;
import Seremis.SoulCraft.client.model.ModelBush5;
import Seremis.SoulCraft.core.lib.RenderIds;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockBushRenderer implements ISimpleBlockRenderingHandler {

    ModelBush5 model5 = new ModelBush5();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslated(0.5, 2.0F, 0.5);
        GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        ((BlockBush)block).getType(metadata).applyTexture(((BlockBush)block).getType(metadata).getMaxStage());
        model5.render();
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return RenderIds.BushRenderID;
    }

}
