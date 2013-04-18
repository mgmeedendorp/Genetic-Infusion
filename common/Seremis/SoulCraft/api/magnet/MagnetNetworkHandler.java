package Seremis.SoulCraft.api.magnet;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.core.geometry.Coordinate3D;

public class MagnetNetworkHandler {
    
    public static void doRenderTickHandling() {
        List<MagnetLink> linkList = MagnetLinkHelper.instance.getAllLinks();
        for(MagnetLink link : linkList) {
            link.render();
        }
    }
    
    public static void loadNetworksForWorld(World world) {
        List<MagnetLink> linkList = MagnetLinkHelper.instance.getAllLinks();
        for(MagnetLink link : linkList) {
            Coordinate3D head = link.line.getHead();
            Coordinate3D tail = link.line.getTail();
            TileEntity tile1 = world.getBlockTileEntity((int)head.x, (int)head.y, (int)head.z);
            TileEntity tile2 = world.getBlockTileEntity((int)tail.x, (int)tail.y, (int)tail.z);
            if(tile1 != null && tile2 != null) {
                if(tile1 instanceof IMagnetConnector && tile2 instanceof IMagnetConnector) {
                    IMagnetConnector connector1 = (IMagnetConnector)tile1;
                    IMagnetConnector connector2 = (IMagnetConnector)tile2;
                    MagnetLink finalLink = new MagnetLink(connector1, connector2);
                    finalLink.register();
                    System.out.println("Final Link: " + finalLink.toString());
                }
            }
        }
    }
}
