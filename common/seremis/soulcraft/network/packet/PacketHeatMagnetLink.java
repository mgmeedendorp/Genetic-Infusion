package seremis.soulcraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import seremis.soulcraft.api.magnet.MagnetLink;
import seremis.soulcraft.api.magnet.tile.IMagnetConnector;
import seremis.soulcraft.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHeatMagnetLink extends SCPacket {

    public int x1, y1, z1;
    public int x2, y2, z2;
    public int heat1, heat2;
    public int dimensionID;
    
    public PacketHeatMagnetLink() {
        super(PacketTypeHandler.HEAT_MAGNET_LINKS);
    }
    
    public PacketHeatMagnetLink(MagnetLink link) {
        this(link.connector1.getTile().xCoord, link.connector1.getTile().yCoord, link.connector1.getTile().zCoord, link.connector2.getTile().xCoord, link.connector2.getTile().yCoord, link.connector2.getTile().zCoord, link.connector1.getHeat(), link.connector2.getHeat());
    }
    
    public PacketHeatMagnetLink(int x1, int y1, int z1, int x2, int y2, int z2, int heat1, int heat2) {
        super(PacketTypeHandler.HEAT_MAGNET_LINKS);
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.heat1 = heat1;
        this.heat2 = heat2;
    }
    
    @Override
    public void readData(DataInputStream dataStream) throws IOException {
        this.x1 = dataStream.readInt();
        this.y1 = dataStream.readInt();
        this.z1 = dataStream.readInt();
        this.x2 = dataStream.readInt();
        this.y2 = dataStream.readInt();
        this.z2 = dataStream.readInt();
        this.heat1 = dataStream.readInt();
        this.heat2 = dataStream.readInt();
    }

    @Override
    public void writeData(DataOutputStream dataStream) throws IOException {
        dataStream.writeInt(x1);
        dataStream.writeInt(y1);
        dataStream.writeInt(z1);
        dataStream.writeInt(x2);
        dataStream.writeInt(y2);
        dataStream.writeInt(z2);
        dataStream.writeInt(heat1);
        dataStream.writeInt(heat2);
    }

    @Override
    public void execute(INetworkManager network, Player player) {
        TileEntity tile1 = ((EntityPlayer) player).worldObj.getBlockTileEntity(x1, y1, z1);
        TileEntity tile2 = ((EntityPlayer) player).worldObj.getBlockTileEntity(x2, y2, z2);

        if(tile1 != null && tile2 != null && tile1 instanceof IMagnetConnector && tile2 instanceof IMagnetConnector) {
            IMagnetConnector conn1 = (IMagnetConnector) tile1;
            IMagnetConnector conn2 = (IMagnetConnector) tile2;

            conn1.cool(conn1.getHeat());
            conn2.cool(conn2.getHeat());
            conn1.warm(heat1);
            conn2.warm(heat2);
        }
    }
}
