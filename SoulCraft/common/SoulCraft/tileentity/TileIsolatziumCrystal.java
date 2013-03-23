package SoulCraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;


public class TileIsolatziumCrystal extends SCTileEntity {
	
	@Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
    	readFromNBT(packet.customParam1);
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
}
