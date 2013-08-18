package Seremis.SoulCraft.core.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.core.lib.Localizations;
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

    public void registerHandlers() {}

    public void renderBeam(World world, Coordinate3D position, Coordinate3D target, int heatAtCoord1, int heatAtCoord2) {}
}
