package seremis.soulcraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import seremis.soulcraft.api.magnet.tile.TileMagnetConnector;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.network.PacketTypeHandler;
import seremis.soulcraft.network.packet.PacketTileData;
import cpw.mods.fml.common.network.PacketDispatcher;

public abstract class SCTileMagnetConnector extends TileMagnetConnector {

    private int teDirection;

    public int getDirection() {
        return teDirection;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }
    
    public void setDirection(int direction) {
        this.teDirection = direction;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType().blockID, 0, direction);
    }

    @Override
    public boolean receiveClientEvent(int eventId, int variable) {
        if(eventId == 0) {
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
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, compound);
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
