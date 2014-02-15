package seremis.soulcraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.network.PacketTypeHandler;
import seremis.soulcraft.network.packet.PacketTileData;
import cpw.mods.fml.common.network.PacketDispatcher;

public class SCTile extends TileEntity {

    private int teDirection;

    public int getDirection() {
        return teDirection;
    }

    public void setDirection(int direction) {
        this.teDirection = direction;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType().blockID, 1, direction);
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean receiveClientEvent(int eventId, int variable) {
        if(eventId == 1) {
            teDirection = variable;
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            return true;
        } else {
            return super.receiveClientEvent(eventId, variable);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        teDirection = compound.getInteger("teDirection");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("teDirection", teDirection);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        readFromNBT(packet.data);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound var1 = new NBTTagCompound();
        writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }

    public void sendTileDataToServer(int id, byte[] data) {
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketTileData(data, id, this.xCoord, this.yCoord, this.zCoord)));
        } else {
            setTileDataFromClient(id, data);
        }
    }

    public void setTileDataFromServer(int id, byte[] data) {

    }

    public void sendTileDataToClient(int id, byte[] data) {
        if(CommonProxy.proxy.isServerWorld(worldObj)) {
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 128D, worldObj.provider.dimensionId, PacketTypeHandler.populatePacket(new PacketTileData(data, id, this.xCoord, this.yCoord, this.zCoord)));
        } else {
            setTileDataFromServer(id, data);
        }
    }

    public void setTileDataFromClient(int id, byte[] data) {

    }
}
