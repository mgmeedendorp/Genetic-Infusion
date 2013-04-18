package Seremis.SoulCraft.handler;

import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import Seremis.SoulCraft.api.magnet.MagnetNetworkHandler;

public class EventHandler {

    @ForgeSubscribe
    public void onWorldLoad(WorldEvent.Load event) {
        World world = event.world;
        System.out.println("LoaD?");
        MagnetNetworkHandler.loadNetworksForWorld(world);
    }
    
    @ForgeSubscribe
    public void onWorldSave(WorldEvent.Save event) {
        System.out.println("SavE?");
        World world = event.world;
    }
}
