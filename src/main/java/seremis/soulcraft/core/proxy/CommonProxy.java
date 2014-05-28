package seremis.soulcraft.core.proxy;

import seremis.soulcraft.core.lib.Localizations;
import seremis.soulcraft.handler.ServerTickHandler;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.SidedProxy;

public class CommonProxy {

    @SidedProxy(clientSide = Localizations.LOC_CLIENTPROXY, serverSide = Localizations.LOC_COMMONPROXY)
    public static CommonProxy proxy;

    public void registerRendering() {}// client only

    public void removeEntity(Entity entity) {
        entity.worldObj.removeEntity(entity);
    }

    public boolean isRenderWorld(World world) {
        return world.isRemote;
    }

    public boolean isServerWorld(World world) {
        return !world.isRemote;
    }

    public String playerName() {
        return "";
    }

    public void registerHandlers() {
    	FMLCommonHandler.instance().bus().register(new ServerTickHandler());
    }
}
