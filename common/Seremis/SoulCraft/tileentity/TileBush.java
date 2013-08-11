package Seremis.SoulCraft.tileentity;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import Seremis.SoulCraft.misc.EnumBushType;

public class TileBush extends SCTileEntity {

    private int stage = 0;
    private EnumBushType type;
    private String texture;

    public TileBush(EnumBushType type) {
        this.type = type;
        texture = type.getTexture(stage);
    }
//Lightvalue checking probably isn't very good every tick...
//    public void updateEntity() {
//        Random random = new Random();
//        if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 5) {
//            return;
//        }
//
//        if (worldObj.getBlockLightValue(xCoord, yCoord + 1, zCoord) < 9) {
//            return;
//        }
//
//        if (random.nextInt(25) == 0) {
//            updateStage();
//        }
//    }
    
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("stage", stage);
        compound.setInteger("type", type.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        stage = compound.getInteger("stage");
        type = EnumBushType.values()[compound.getInteger("type")];
    }

    public int getStage() {
        return stage;
    }

    public EnumBushType getType() {
        return type;
    }

    public void updateStage() {
        stage++;
        texture = type.getTexture(stage);
    }

    public String getTexture() {
        return texture;
    }
}
