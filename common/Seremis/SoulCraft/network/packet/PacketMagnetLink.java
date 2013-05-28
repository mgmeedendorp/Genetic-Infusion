package Seremis.SoulCraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.magnet.MagnetLink;
import Seremis.SoulCraft.api.magnet.MagnetLinkHelper;
import Seremis.SoulCraft.api.magnet.tile.IMagnetConnector;
import Seremis.SoulCraft.network.PacketTypeHandler;
import Seremis.core.geometry.Coordinate3D;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.Player;

public class PacketMagnetLink extends PacketSC {
    
    Coordinate3D head;
    Coordinate3D target;
    
    public PacketMagnetLink() {
        super(PacketTypeHandler.MAGNETLINK, false);
    }
    
    public PacketMagnetLink(Coordinate3D head, Coordinate3D target) {
        this();
        this.head = head;
        this.target = target;
    }
    
    public PacketMagnetLink(IMagnetConnector connector1, IMagnetConnector connector2) {
        this();
        this.head = new Coordinate3D(connector1.getTile());
        this.target = new Coordinate3D(connector2.getTile());
    }
    
    public PacketMagnetLink(MagnetLink link) {
        this(link.connector1, link.connector2);
    }
    
    @Override
    public void writeData(DataOutputStream data) throws IOException {
        data.writeDouble(head.x);
        data.writeDouble(head.y);
        data.writeDouble(head.z);
        data.writeDouble(target.x);
        data.writeDouble(target.y);
        data.writeDouble(target.z);
    }

    @Override
    public void readData(DataInputStream data) throws IOException {
        double hx, hy, hz, tx, ty, tz;
        hx = data.readDouble();
        hy = data.readDouble();
        hz = data.readDouble();
        tx = data.readDouble();
        ty = data.readDouble();
        tz = data.readDouble();
        head = new Coordinate3D(hx, hy, hz);
        target = new Coordinate3D(tx, ty, tz);
    }
    
    @Override 
    public void execute(INetworkManager manager, Player player) {
        EntityPlayer thePlayer = FMLClientHandler.instance().getClient().thePlayer;
        World world = thePlayer.worldObj;
        TileEntity tile1 = world.getBlockTileEntity((int)head.x, (int)head.y, (int)head.z);
        TileEntity tile2 = world.getBlockTileEntity((int)target.x, (int)target.y, (int)target.z);
        if(tile1 != null && tile2 != null && tile1 instanceof IMagnetConnector && tile2 instanceof IMagnetConnector) {
            IMagnetConnector connector1 = (IMagnetConnector)tile1;
            IMagnetConnector connector2 = (IMagnetConnector)tile2;
            if(!MagnetLinkHelper.instance.doesLinkExist(connector1, connector2)) {
                MagnetLink link = new MagnetLink(connector1, connector2);
                if(MagnetLinkHelper.instance.checkConditions(link)) {
                    MagnetLinkHelper.instance.addLink(link);
                }
            }
        }
    }

}
