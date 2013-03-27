package Seremis.SoulCraft.core.proxy;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import Seremis.SoulCraft.client.render.block.BlockCompressorRenderer;
import Seremis.SoulCraft.client.render.block.BlockCrystalRenderer;
import Seremis.SoulCraft.client.render.block.BlockCrystalStandRenderer;
import Seremis.SoulCraft.client.render.block.BlockMonsterEggRenderer;
import Seremis.SoulCraft.client.render.tile.TileCompressorRenderer;
import Seremis.SoulCraft.client.render.tile.TileCrystalRenderer;
import Seremis.SoulCraft.client.render.tile.TileCrystalStandRenderer;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.handler.TickHandler;
import Seremis.SoulCraft.tileentity.TileCompressor;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
import Seremis.SoulCraft.tileentity.TileIsolatziumCrystal;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

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
	
	@Override
	public void registerTickHandlers() {
		TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
	}
			
}
