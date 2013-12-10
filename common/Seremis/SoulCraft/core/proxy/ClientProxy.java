package Seremis.SoulCraft.core.proxy;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.client.render.BlockBushRenderer;
import Seremis.SoulCraft.client.render.BlockCompressorRenderer;
import Seremis.SoulCraft.client.render.BlockCrystalRenderer;
import Seremis.SoulCraft.client.render.BlockCrystalStandRenderer;
import Seremis.SoulCraft.client.render.BlockMonsterEggRenderer;
import Seremis.SoulCraft.client.render.EntityTransporterRenderer;
import Seremis.SoulCraft.client.render.FXBeam;
import Seremis.SoulCraft.client.render.ItemTransporterModulesRenderer;
import Seremis.SoulCraft.client.render.TileBushRenderer;
import Seremis.SoulCraft.client.render.TileCompressorRenderer;
import Seremis.SoulCraft.client.render.TileCrystalRenderer;
import Seremis.SoulCraft.client.render.TileCrystalStandRenderer;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.entity.EntityTransporter;
import Seremis.SoulCraft.handler.RenderTickHandler;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.tileentity.TileBush;
import Seremis.SoulCraft.tileentity.TileCompressor;
import Seremis.SoulCraft.tileentity.TileCrystal;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
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
        RenderingRegistry.registerBlockHandler(RenderIds.BushRenderID, new BlockBushRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntityTransporter.class, new EntityTransporterRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileCompressor.class, new TileCompressorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new TileCrystalRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalStand.class, new TileCrystalStandRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBush.class, new TileBushRenderer());

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
    }

    @Override
    public void renderBeam(World world, Coordinate3D position, Coordinate3D target, int r1, int g1, int b1, int r2, int g2, int b2) {
        FMLClientHandler.instance().getClient().effectRenderer.addEffect(new FXBeam(world, position, target, r1, g1, b1, r2, g2, b2));
    }
}
