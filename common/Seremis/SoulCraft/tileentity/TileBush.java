package Seremis.SoulCraft.tileentity;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import Seremis.SoulCraft.block.ModBlocks;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.misc.bush.BushManager;
import Seremis.SoulCraft.misc.bush.BushType;

public class TileBush extends SCTile {

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
                worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.bushBerry.blockID, 1, stage);
            }
        } else {
            sendTileDataToServer(0, new byte[] {(byte) stage});
        }
    }

    @Override
    public boolean receiveClientEvent(int id, int data) {
        if(id == 1) {
            this.stage = data;
            return true;
        } else {
            return super.receiveClientEvent(id, data);
        }
    }

    @Override
    public void setTileDataFromServer(int id, byte[] data) {
        if(id == 0) {
            setStage(data[0]);
        }
    }

    @Override
    public void updateEntity() {
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            return;
        }
        Random random = new Random();

        if(random.nextInt(2) == 0 && stage < 5) {
            updateStage();
            System.out.println(stage);
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
        if(stage < type.getMaxStage()) {
            setStage(stage + 1);
        }
    }
}
