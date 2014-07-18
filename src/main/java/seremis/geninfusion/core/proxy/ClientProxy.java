package seremis.geninfusion.core.proxy;

import seremis.geninfusion.client.render.BlockCompressorRenderer;
import seremis.geninfusion.client.render.BlockCrystalRenderer;
import seremis.geninfusion.client.render.BlockCrystalStandRenderer;
import seremis.geninfusion.client.render.BlockMonsterEggRenderer;
import seremis.geninfusion.client.render.EntityTransporterRenderer;
import seremis.geninfusion.client.render.ItemTransporterModulesRenderer;
import seremis.geninfusion.client.render.RenderBeam;
import seremis.geninfusion.client.render.TileCompressorRenderer;
import seremis.geninfusion.client.render.TileCrystalRenderer;
import seremis.geninfusion.client.render.TileCrystalStandRenderer;
import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.entity.EntityTransporter;
import seremis.geninfusion.item.ModItems;
import seremis.geninfusion.soul.entity.EntitySoulCustom;
import seremis.geninfusion.soul.entity.RenderEntitySoulCustom;
import seremis.geninfusion.tileentity.TileCompressor;
import seremis.geninfusion.tileentity.TileCrystal;
import seremis.geninfusion.tileentity.TileCrystalStand;
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
