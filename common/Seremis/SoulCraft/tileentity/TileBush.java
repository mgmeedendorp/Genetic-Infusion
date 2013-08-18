package Seremis.SoulCraft.tileentity;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.misc.bush.BushManager;
import Seremis.SoulCraft.misc.bush.BushType;

public class TileBush extends SCTileEntity {

    private int stage = 1;
    private BushType type;

    public TileBush(BushType type) {
        this.type = type;
    }
    
    public void setStage(int stage) {
        if(CommonProxy.proxy.isServerWorld(worldObj)) {
            if(stage <= type.getMaxStage()) {
                this.stage = stage;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    public void updateEntity() {
        if(CommonProxy.proxy.isRenderWorld(worldObj)) return;
        Random random = new Random();      
        
        if (random.nextInt(2) == 0 && stage < 5) {
            updateStage();
            System.out.println(stage);
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("stage", stage);
        compound.setInteger("type", BushManager.getTypeIndex(type));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        stage = compound.getInteger("stage");
        type = BushManager.getIndexType(compound.getInteger("type"));
    }

    public int getStage() {
        return stage;
    }

    public BushType getType() {
        return type;
    }

    public void updateStage() {
        if(stage<type.getMaxStage())
        setStage(stage+1);
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        readFromNBT(packet.customParam1);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeToNBT(compound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, compound);
    }
}
