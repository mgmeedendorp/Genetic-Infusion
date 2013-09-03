package Seremis.SoulCraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import Seremis.SoulCraft.api.magnet.tile.TileMagnetConnector;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.network.PacketTypeHandler;
import Seremis.SoulCraft.network.packet.PacketTileData;
import cpw.mods.fml.common.network.PacketDispatcher;

public abstract class SCTileMagnetConnector extends TileMagnetConnector {

    private String owner;
    private int teDirection;

    public int getDirection() {
        return teDirection;
    }
    
    public void setDirection(int direction) {
        this.teDirection = direction;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType().blockID, 0, direction);
    }
    
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public boolean isUseableByPlayer(EntityPlayer player) {
        return owner.equals(player.username);
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
    
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        owner = compound.getString("teOwner");
        teDirection = compound.getInteger("teDirection");
    }

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(owner != null && owner != "") {
            compound.setString("teOwner", owner);
        }
        compound.setInteger("teDirection", teDirection);
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        readFromNBT(packet.customParam1);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound var1 = new NBTTagCompound();
        writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
    }
    
    public void sendTileData(int id, int data) {
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketTileData(data, id, this.xCoord, this.yCoord, this.zCoord)));
        }
        else this.setTileData(id, data);
    }

    public void setTileData(int id, int data){

    }
}
