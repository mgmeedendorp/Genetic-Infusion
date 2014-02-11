package seremis.soulcraft.core.proxy;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
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
import seremis.soulcraft.handler.RenderTickHandler;
import seremis.soulcraft.item.ModItems;
import seremis.soulcraft.soul.entity.EntitySoulCustom;
import seremis.soulcraft.soul.entity.RenderEntitySoulCustom;
import seremis.soulcraft.tileentity.TileCompressor;
import seremis.soulcraft.tileentity.TileCrystal;
import seremis.soulcraft.tileentity.TileCrystalStand;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRendering() {
        RenderingRegistry.registerBlockHandler(RenderIds.MonsterEggRenderID, new BlockMonsterEggRenderer());
        RenderingRegistry.registerBlockHandler(RenderIds.CompressorRenderID, new BlockCompressorRenderer());
        RenderingRegistry.registerBlockHandler(RenderIds.CrystalRenderID, new BlockCrystalRenderer());
        RenderingRegistry.registerBlockHandler(RenderIds.CrystalStandRenderID, new BlockCrystalStandRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntityTransporter.class, new EntityTransporterRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntitySoulCustom.class, new RenderEntitySoulCustom());

        ClientRegistry.bindTileEntitySpecialRenderer(TileCompressor.class, new TileCompressorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new TileCrystalRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalStand.class, new TileCrystalStandRenderer());

        MinecraftForgeClient.registerItemRenderer(ModItems.transporterModules.itemID, new ItemTransporterModulesRenderer());
    }

    @Override
    public void removeEntity(Entity entity) {
        super.removeEntity(entity);

        if(isRenderWorld(entity.worldObj)) {
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
        MinecraftForge.EVENT_BUS.register(new RenderBeam());
    }
}
