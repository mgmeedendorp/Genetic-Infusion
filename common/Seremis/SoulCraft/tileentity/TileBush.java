package Seremis.SoulCraft.tileentity;

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
