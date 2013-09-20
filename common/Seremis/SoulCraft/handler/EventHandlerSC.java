package Seremis.SoulCraft.handler;

import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.WorldEvent;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.block.BlockBush;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.network.PacketTypeHandler;
import Seremis.SoulCraft.network.packet.PacketResetMagnetLinks;
import cpw.mods.fml.common.network.PacketDispatcher;

public class EventHandlerSC {

    @ForgeSubscribe
    public void bonemealUsed(BonemealEvent event) {
        System.out.println("busy");
        if(event.ID == ModBlocks.bushBerry.blockID) {
            ((BlockBush) ModBlocks.bushBerry).grow(event.world, event.X, event.Y, event.Z);
        }
    }
    
    @ForgeSubscribe
    public void worldLoad(WorldEvent.Load event) {
        World world = event.world;
        
        if(CommonProxy.proxy.isServerWorld(world)) {
            PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketResetMagnetLinks()));
        }
        MagnetLinkHelper.instance.deleteAllNetworks();
    }
}
