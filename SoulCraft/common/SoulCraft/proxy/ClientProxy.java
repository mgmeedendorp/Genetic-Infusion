package SoulCraft.proxy;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import SoulCraft.client.render.block.BlockCompressorRenderer;
import SoulCraft.client.render.block.BlockCrystalRenderer;
import SoulCraft.client.render.block.BlockCrystalStandRenderer;
import SoulCraft.client.render.block.BlockMonsterEggRenderer;
import SoulCraft.client.render.tile.TileCompressorRenderer;
import SoulCraft.client.render.tile.TileCrystalRenderer;
import SoulCraft.client.render.tile.TileCrystalStandRenderer;
import SoulCraft.core.lib.RenderIds;
import SoulCraft.tileentity.TileCompressor;
import SoulCraft.tileentity.TileCrystalStand;
import SoulCraft.tileentity.TileIsolatziumCrystal;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{	
	@Override
	public void registerRendering() {
		RenderingRegistry.registerBlockHandler(RenderIds.MonsterEggRenderID, new BlockMonsterEggRenderer());
		RenderingRegistry.registerBlockHandler(RenderIds.CompressorRenderID, new BlockCompressorRenderer());
		RenderingRegistry.registerBlockHandler(RenderIds.IsolatziumCrystalRenderID, new BlockCrystalRenderer());
		RenderingRegistry.registerBlockHandler(RenderIds.CrystalStandRenderID, new BlockCrystalStandRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileCompressor.class, new TileCompressorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileIsolatziumCrystal.class, new TileCrystalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalStand.class, new TileCrystalStandRenderer());
	}
	
	@Override
	public void removeEntity(Entity entity) {
		super.removeEntity(entity);

		if (isRenderWorld(entity.worldObj)) {
			((WorldClient) entity.worldObj).removeEntityFromWorld(entity.entityId);
		}
	}
	
	@Override
	public String playerName() {
		return FMLClientHandler.instance().getClient().thePlayer.username;
	}
			
}
