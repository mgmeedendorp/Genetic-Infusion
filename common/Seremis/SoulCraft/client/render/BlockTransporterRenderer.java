package Seremis.SoulCraft.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelTransporter;
import Seremis.SoulCraft.client.model.ModelTransporterEngine;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.helper.SCRenderHelper;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockTransporterRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        ModelTransporter model = new ModelTransporter();
        ModelTransporterEngine engine = new ModelTransporterEngine();
        GL11.glRotatef((float)Math.PI, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.8F, -0.5F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);
        model.render(ForgeDirection.EAST);
        SCRenderHelper.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER_ENGINE);
        engine.render(ForgeDirection.EAST);
        
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
        return RenderIds.TransporterRenderID;
    }

}
