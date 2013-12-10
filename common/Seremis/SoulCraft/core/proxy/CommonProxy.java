package Seremis.SoulCraft.core.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.util.Coordinate3D;
import Seremis.SoulCraft.core.lib.Localizations;
import Seremis.SoulCraft.handler.ServerTickHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

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
        TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
    }

    public void renderBeam(World world, Coordinate3D position, Coordinate3D target, int r1, int g1, int b1, int r2, int g2, int b2) {}

    public void renderEngineBeam(World world, Coordinate3D position, float yaw, float pitch, int r, int g, int b) {}
}
