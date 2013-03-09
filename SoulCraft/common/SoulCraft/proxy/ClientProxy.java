package SoulCraft.proxy;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.MinecraftForgeClient;
import SoulCraft.client.render.block.BlockCompressorRenderer;
import SoulCraft.client.render.block.BlockMonsterEggRenderer;
import SoulCraft.client.render.tile.TileCompressorRenderer;
import SoulCraft.core.DefaultProps;
import SoulCraft.tileentity.TileCompressor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{	
	@Override
	public void registerRendering() {
		MinecraftForgeClient.preloadTexture(DefaultProps.BLOCKS_TEXTURE_FILE);
		MinecraftForgeClient.preloadTexture(DefaultProps.ITEMS_TEXTURE_FILE);
		
		RenderingRegistry.registerBlockHandler(DefaultProps.MonsterEggRenderID, new BlockMonsterEggRenderer());
		RenderingRegistry.registerBlockHandler(DefaultProps.CompressorRenderID, new BlockCompressorRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileCompressor.class, new TileCompressorRenderer());
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
