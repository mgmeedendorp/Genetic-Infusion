package seremis.geninfusion.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.tileentity.TileCrystalStand;

@SideOnly(Side.CLIENT)
public class BlockCrystalStandRenderer implements ISimpleBlockRenderingHandler {

	public TileEntitySpecialRenderer render;
	
	public BlockCrystalStandRenderer(TileEntitySpecialRenderer render) {
		this.render = render;
	}
	
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glRotatef(10.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glScalef(1.2F, 1.2F, 1.2F);
        TileCrystalStand tile = new TileCrystalStand();
        render.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return RenderIds.CrystalStandRenderID;
    }

}
