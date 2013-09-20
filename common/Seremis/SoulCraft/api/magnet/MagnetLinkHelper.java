package Seremis.SoulCraft.api.magnet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.api.util.Line3D;
import Seremis.SoulCraft.network.PacketTypeHandler;
import Seremis.SoulCraft.network.packet.PacketAddMagnetLink;
import Seremis.SoulCraft.network.packet.PacketRemoveMagnetLink;
import Seremis.SoulCraft.network.packet.PacketRemoveMagnetLinkConnector;
import Seremis.SoulCraft.network.packet.PacketResetMagnetLinks;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MagnetLinkHelper {

    public static MagnetLinkHelper instance = new MagnetLinkHelper();

    private List<MagnetLink> registeredMap = new ArrayList<MagnetLink>();

    // TODO something with networks

    public void addLink(MagnetLink link) {
        if(link.connector1.getTile().worldObj.isRemote) {
            addClientLink(link);
            return;
        }
        if(link != null && link.connector1 != null && link.connector2 != null && link.connector1 != link.connector2 && !doesLinkExist(link)) {
            if(checkConditions(link)) {
                registeredMap.add(link);
                PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketAddMagnetLink(link)));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void addClientLink(MagnetLink link) {
        if(link != null && link.connector1 != null && link.connector2 != null && link.connector1 != link.connector2 && !doesLinkExist(link)) {
            if(link.connector1.getTile().worldObj.getBlockTileEntity(link.connector1.getTile().xCoord, link.connector1.getTile().yCoord, link.connector1.getTile().zCoord) == link.connector1.getTile()) {
                if(link.connector2.getTile().worldObj.getBlockTileEntity(link.connector2.getTile().xCoord, link.connector2.getTile().yCoord, link.connector2.getTile().zCoord) == link.connector2.getTile()) {
                    registeredMap.add(link);
                }
            }
        }
    }

    public void removeLink(MagnetLink link) {
        registeredMap.remove(link);
        PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveMagnetLink(link)));
    }
    
    private void removeLink2(MagnetLink link) {
        PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveMagnetLink(link)));
    }

    public void removeAllLinksFrom(IMagnetConnector connector) {
        Iterator<MagnetLink> it = registeredMap.iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(link.connector1 == connector || link.connector2 == connector) {
                it.remove();
            }
        }
        PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.populatePacket(new PacketRemoveMagnetLinkConnector(connector)));
    }

    public List<MagnetLink> getLinksConnectedTo(IMagnetConnector connector) {
        List<MagnetLink> links = new ArrayList<MagnetLink>();
        
        Iterator<MagnetLink> it = getAllLinks().iterator();
        
        while(it.hasNext()) {
            MagnetLink link = it.next();
            
//            if(connector.getTile().worldObj.isRemote) {
//                System.out.println(link.connector1.getTile().worldObj.isRemote);
//                System.out.println(connector.getTile().worldObj.isRemote);
//                //TODO why can a server world be on a client?
//            }
            
            if(link.connector1 == connector || link.connector2 == connector) {
                links.add(link);
            }
        }

        return links;
    }

    public List<IMagnetConnector> getConnectorsConnectedTo(IMagnetConnector connector) {
        List<IMagnetConnector> connList = new ArrayList<IMagnetConnector>();
        
        Iterator<MagnetLink> it = getAllLinks().iterator();
        
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(link.connector1 == connector) {
                connList.add(link.connector2);
            } else if(link.connector2 == connector) {
                connList.add(link.connector1);
            }
        }
        return connList;
    }

    public List<MagnetLink> getAllLinks() {
        return registeredMap;
    }

    public boolean checkConditions(MagnetLink link) {
        return checkConditions(link.connector1, link.connector2);
    }

    public boolean checkConditions(IMagnetConnector connector1, IMagnetConnector connector2) {
        if(connector1 == null || connector2 == null || connector1 == connector2 || !(connector1 instanceof IMagnetConnector) || !(connector2 instanceof IMagnetConnector))
            return false;
        Line3D line = new Line3D();
        line.setLineFromTile(connector1.getTile(), connector2.getTile());

        MagnetLink link = new MagnetLink(connector1, connector2);
        if(connector1.canConnect(link) && connector2.canConnect(link)) {
            if(line.getLength() <= connector1.getRange() && line.getLength() <= connector2.getRange()) {
                if(!doesLinkExist(connector1, connector2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean doesLinkExist(MagnetLink link) {
        return doesLinkExist(link.connector1, link.connector2);
    }

    public boolean doesLinkExist(IMagnetConnector connector1, IMagnetConnector connector2) {
        if(connector1 == null || connector2 == null || connector1 == connector2)
            return true;
        
        Iterator<MagnetLink> it = getAllLinks().iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(link.connector1 == connector1 || link.connector2 == connector1) {
                if(link.connector1 == connector2 || link.connector2 == connector2) {
                    return true;
                }
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderLinks() {
        if(FMLClientHandler.instance().getClient().inGameHasFocus) {
            Iterator<MagnetLink> it = getAllLinks().iterator();
            while(it.hasNext()) {
                it.next().render();
            }
        }
    }

    public List<IMagnetConnector> getAllConnectors() {
        List<IMagnetConnector> connectors = new ArrayList<IMagnetConnector>();
        Iterator<MagnetLink> it = getAllLinks().iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(!connectors.contains(link.connector1))
                connectors.add(link.connector1);
            if(!connectors.contains(link.connector2))
                connectors.add(link.connector2);
        }
        return connectors;
    }

    public void tick() {
        Iterator<MagnetLink> it = getAllLinks().iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            if(!link.isConnectionPossible()) {
                it.remove();
                removeLink2(link);
            }
        }
    }

    @SideOnly(Side.SERVER)
    public void updatePlayerWithNetworks(EntityPlayer player) {
        PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.populatePacket(new PacketResetMagnetLinks()), (Player) player);

        for(MagnetLink link : getAllLinks()) {
            PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.populatePacket(new PacketAddMagnetLink(link)), (Player) player);
        }
    }

    public MagnetNetwork getNetworkFrom(MagnetLink link) {
        return getNetworkFrom(link.connector1);
    }

    public void deleteAllNetworks() {
        Iterator<MagnetLink> it = MagnetLinkHelper.instance.getAllLinks().iterator();
        while(it.hasNext()) {
            MagnetLink link = it.next();
            it.remove();
            this.removeLink2(link);
        }
    }
    
    public MagnetNetwork getNetworkFrom(IMagnetConnector connector) {
        MagnetNetwork network = new MagnetNetwork();

        return network;
    }
}
