package seremis.geninfusion.core.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import seremis.geninfusion.client.render.RenderCrystal;
import seremis.geninfusion.client.render.RenderSoulCage;
import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.soul.entity.EntitySoulCustom;
import seremis.geninfusion.soul.entity.EntitySoulCustomCreature;
import seremis.geninfusion.soul.entity.render.RenderEntitySoulCustom;
import seremis.geninfusion.tileentity.TileCrystal;
import seremis.geninfusion.tileentity.TileSoulCage;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRendering() {
        RenderingRegistry.registerBlockHandler(RenderIds.crystalRenderID, new RenderCrystal());
        RenderingRegistry.registerBlockHandler(RenderIds.soulCageRenderID, new RenderSoulCage());

        RenderingRegistry.registerEntityRenderingHandler(EntitySoulCustom.class, new RenderEntitySoulCustom());
        RenderingRegistry.registerEntityRenderingHandler(EntitySoulCustomCreature.class, new RenderEntitySoulCustom());

        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new RenderCrystal());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSoulCage.class, new RenderSoulCage());
    }

    @Override
    public void removeEntity(Entity entity) {
        super.removeEntity(entity);

        if(isRenderWorld(entity.worldObj)) {
            entity.worldObj.removeEntity(entity);
        }
    }

    @Override
    public String playerName() {
        return FMLClientHandler.instance().getClient().thePlayer.getDisplayName();
    }

    @Override
    public void registerHandlers() {

    }
}
