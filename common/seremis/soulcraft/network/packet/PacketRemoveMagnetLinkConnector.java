package seremis.soulcraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import seremis.soulcraft.api.magnet.MagnetLinkHelper;
import seremis.soulcraft.api.magnet.tile.IMagnetConnector;
import seremis.soulcraft.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketRemoveMagnetLinkConnector extends SCPacket {

    public int x, y, z;

    public PacketRemoveMagnetLinkConnector() {
        super(PacketTypeHandler.REMOVE_MAGNET_LINK_CONNECTOR);
    }

    public PacketRemoveMagnetLinkConnector(int x, int y, int z) {
        super(PacketTypeHandler.REMOVE_MAGNET_LINK_CONNECTOR);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketRemoveMagnetLinkConnector(IMagnetConnector conn) {
        super(PacketTypeHandler.REMOVE_MAGNET_LINK_CONNECTOR);
        this.x = conn.getTile().xCoord;
        this.y = conn.getTile().yCoord;
        this.z = conn.getTile().zCoord;
    }

    @Override
    public void readData(DataInputStream dataStream) throws IOException {
        this.x = dataStream.readInt();
        this.y = dataStream.readInt();
        this.z = dataStream.readInt();
    }

    @Override
    public void writeData(DataOutputStream dataStream) throws IOException {
        dataStream.writeInt(x);
        dataStream.writeInt(y);
        dataStream.writeInt(z);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z);

        if(tile != null && tile instanceof IMagnetConnector) {
            IMagnetConnector conn = (IMagnetConnector) tile;

            MagnetLinkHelper.instance.removeAllLinksFrom(conn);
        }
    }
}
