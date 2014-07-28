package seremis.geninfusion.tileentity;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.core.proxy.CommonProxy;
import seremis.geninfusion.network.packet.PacketTileData;

public class GITile extends TileEntity {

    private int teDirection;

    public int getDirection() {
        return teDirection;
    }

    public void setDirection(int direction) {
        this.teDirection = direction;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, direction);
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public boolean receiveClientEvent(int eventId, int variable) {
        if(eventId == 1) {
            teDirection = variable;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, compound);
    }

    public void sendTileDataToServer(int id, byte[] data) {
        if(CommonProxy.instance.isRenderWorld(worldObj)) {
            GeneticInfusion.packetPipeline.sendToServer(new PacketTileData(data, id, this.xCoord, this.yCoord, this.zCoord));
        } else {
            setTileDataFromClient(id, data);
        }
    }

    public void setTileDataFromServer(int id, byte[] data) {

    }

    public void sendTileDataToClient(int id, byte[] data) {
        if(CommonProxy.instance.isServerWorld(worldObj)) {
            GeneticInfusion.packetPipeline.sendToAllAround(new PacketTileData(data, id, xCoord, yCoord, zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 128));
        } else {
            setTileDataFromServer(id, data);
        }
    }

    public void setTileDataFromClient(int id, byte[] data) {

    }
}
