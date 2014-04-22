package seremis.soulcraft.core.proxy;

import seremis.soulcraft.client.render.BlockCompressorRenderer;
import seremis.soulcraft.client.render.BlockCrystalRenderer;
import seremis.soulcraft.client.render.BlockCrystalStandRenderer;
import seremis.soulcraft.client.render.BlockMonsterEggRenderer;
import seremis.soulcraft.client.render.EntityTransporterRenderer;
import seremis.soulcraft.client.render.ItemTransporterModulesRenderer;
import seremis.soulcraft.client.render.RenderBeam;
import seremis.soulcraft.client.render.TileCompressorRenderer;
import seremis.soulcraft.client.render.TileCrystalRenderer;
import seremis.soulcraft.client.render.TileCrystalStandRenderer;
import seremis.soulcraft.core.lib.RenderIds;
import seremis.soulcraft.entity.EntityTransporter;
import seremis.soulcraft.item.ModItems;
import seremis.soulcraft.soul.entity.EntitySoulCustom;
import seremis.soulcraft.soul.entity.RenderEntitySoulCustom;
import seremis.soulcraft.tileentity.TileCompressor;
import seremis.soulcraft.tileentity.TileCrystal;
import seremis.soulcraft.tileentity.TileCrystalStand;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
    @Override
    public void registerRendering() {
    	TileEntitySpecialRenderer crystalStand = new TileCrystalStandRenderer();
    	
        RenderingRegistry.registerBlockHandler(RenderIds.MonsterEggRenderID, new BlockMonsterEggRenderer());
        RenderingRegistry.registerBlockHandler(RenderIds.CompressorRenderID, new BlockCompressorRenderer());
        RenderingRegistry.registerBlockHandler(RenderIds.CrystalRenderID, new BlockCrystalRenderer());
        RenderingRegistry.registerBlockHandler(RenderIds.CrystalStandRenderID, new BlockCrystalStandRenderer(crystalStand));

        RenderingRegistry.registerEntityRenderingHandler(EntityTransporter.class, new EntityTransporterRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntitySoulCustom.class, new RenderEntitySoulCustom());

        ClientRegistry.bindTileEntitySpecialRenderer(TileCompressor.class, new TileCompressorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new TileCrystalRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalStand.class, crystalStand);

        MinecraftForgeClient.registerItemRenderer(ModItems.transporterModules, new ItemTransporterModulesRenderer());
    }

    @Override
    public void removeEntity(Entity entity) {
        super.removeEntity(entity);

        if(isRenderWorld(entity.worldObj)) {
            ((WorldClient) entity.worldObj).removeEntity(entity);
        }
    }

    @Override
    public String playerName() {
        return FMLClientHandler.instance().getClient().thePlayer.getDisplayName();
    }

    @Override
    public void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new RenderBeam());
    }
}
