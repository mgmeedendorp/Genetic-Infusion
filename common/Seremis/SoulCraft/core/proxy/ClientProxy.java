package Seremis.SoulCraft.core.proxy;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import Seremis.SoulCraft.client.render.BlockCompressorRenderer;
import Seremis.SoulCraft.client.render.BlockCrystalRenderer;
import Seremis.SoulCraft.client.render.BlockCrystalStandRenderer;
import Seremis.SoulCraft.client.render.BlockMonsterEggRenderer;
import Seremis.SoulCraft.client.render.EntityTransporterRenderer;
import Seremis.SoulCraft.client.render.FXBeam;
import Seremis.SoulCraft.client.render.TileCompressorRenderer;
import Seremis.SoulCraft.client.render.TileCrystalRenderer;
import Seremis.SoulCraft.client.render.TileCrystalStandRenderer;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.handler.RenderTickHandler;
import Seremis.SoulCraft.tileentity.TileCompressor;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
import Seremis.SoulCraft.tileentity.TileIsolatziumCrystal;
import Seremis.core.geometry.Coordinate3D;
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
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTransporter.class, new EntityTransporterRenderer());
		
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
	public void registerHandlers() {
		TickRegistry.registerTickHandler(new RenderTickHandler(), Side.CLIENT);
	}
	
	@Override
    public void renderBeam(World world, Coordinate3D position, Coordinate3D target, float red, float green, float blue, int age)
    {
	        FMLClientHandler.instance().getClient().effectRenderer.addEffect(new FXBeam(world, position, target, red, green, blue, age));
    }
			
}
