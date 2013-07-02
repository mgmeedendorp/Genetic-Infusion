package Seremis.SoulCraft.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import Seremis.SoulCraft.client.model.ModelTransporter;
import Seremis.SoulCraft.client.model.ModelTransporterEngine;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.tileentity.TileTransporter;
import cpw.mods.fml.client.FMLClientHandler;

public class RenderTileTransporter extends TileEntitySpecialRenderer {

    ModelTransporter model = new ModelTransporter();
    ModelTransporterEngine engine = new ModelTransporterEngine();
    
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        TileTransporter transporter = (TileTransporter)tile;
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER);
        model.render(transporter.direction);
        if(transporter.hasEngine()) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(Localizations.LOC_MODEL_TEXTURES + Localizations.ENTITY_TRANSPORTER_ENGINE);
            engine.render(transporter.direction);
        }
        GL11.glPopMatrix();
    }

}
