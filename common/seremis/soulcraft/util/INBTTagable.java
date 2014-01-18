package seremis.soulcraft.util;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTTagable {

    public void writeToNBT(NBTTagCompound compound);
    public void readFromNBT(NBTTagCompound compound);
}
