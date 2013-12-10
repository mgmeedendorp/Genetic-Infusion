package Seremis.SoulCraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketRemoveMagnetLink extends SCPacket {

    public int x1, y1, z1;
    public int x2, y2, z2;
    public int dimensionID;

    public PacketRemoveMagnetLink() {
        super(PacketTypeHandler.REMOVE_MAGNET_LINK);
    }

    public PacketRemoveMagnetLink(MagnetLink link) {
        this((int) link.line.head.x, (int) link.line.head.y, (int) link.line.head.z, (int) link.line.tail.x, (int) link.line.tail.y, (int) link.line.tail.z, link.dimensionID);
    }

    public PacketRemoveMagnetLink(int x1, int y1, int z1, int x2, int y2, int z2, int dimensionID) {
        super(PacketTypeHandler.REMOVE_MAGNET_LINK);
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.dimensionID = dimensionID;
    }

    @Override
    public void readData(DataInputStream dataStream) throws IOException {
        this.x1 = dataStream.readInt();
        this.y1 = dataStream.readInt();
        this.z1 = dataStream.readInt();
        this.x2 = dataStream.readInt();
        this.y2 = dataStream.readInt();
        this.z2 = dataStream.readInt();
        this.dimensionID = dataStream.readInt();
    }

    @Override
    public void writeData(DataOutputStream dataStream) throws IOException {
        dataStream.writeInt(x1);
        dataStream.writeInt(y1);
        dataStream.writeInt(z1);
        dataStream.writeInt(x2);
        dataStream.writeInt(y2);
        dataStream.writeInt(z2);
        dataStream.writeInt(dimensionID);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        TileEntity tile1 = ((EntityPlayer) player).worldObj.getBlockTileEntity(x1, y1, z1);
        TileEntity tile2 = ((EntityPlayer) player).worldObj.getBlockTileEntity(x2, y2, z2);

        if(tile1 != null && tile2 != null && tile1 instanceof IMagnetConnector && tile2 instanceof IMagnetConnector) {
            IMagnetConnector conn1 = (IMagnetConnector) tile1;
            IMagnetConnector conn2 = (IMagnetConnector) tile2;

            for(MagnetLink link : MagnetLinkHelper.instance.getLinksConnectedTo(conn1)) {
                if(link.getOther(conn1) == conn2 && link.dimensionID == dimensionID) {
                    MagnetLinkHelper.instance.removeLink(link);
                }
            }
        }
    }
}
