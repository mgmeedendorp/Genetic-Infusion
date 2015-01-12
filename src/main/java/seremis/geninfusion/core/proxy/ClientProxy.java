package seremis.geninfusion.core.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import seremis.geninfusion.client.render.BlockCrystalRenderer;
import seremis.geninfusion.client.render.TileCrystalRenderer;
import seremis.geninfusion.lib.RenderIds;
import seremis.geninfusion.soul.entity.EntitySoulCustom;
import seremis.geninfusion.soul.entity.EntitySoulCustomCreature;
import seremis.geninfusion.soul.entity.RenderEntitySoulCustom;
import seremis.geninfusion.tileentity.TileCrystal;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRendering() {
        RenderingRegistry.registerBlockHandler(RenderIds.CrystalRenderID, new BlockCrystalRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntitySoulCustom.class, new RenderEntitySoulCustom());
        RenderingRegistry.registerEntityRenderingHandler(EntitySoulCustomCreature.class, new RenderEntitySoulCustom());

        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new TileCrystalRenderer());
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
