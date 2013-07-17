package Seremis.SoulCraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class SCTileEntity extends TileEntity {

    private String owner;

    public String getOwner() {

        return owner;
    }

    public void setOwner(String owner) {

        this.owner = owner;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {

        return owner.equals(player.username);
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {

        super.readFromNBT(nbtTagCompound);
        owner = nbtTagCompound.getString("teOwner");
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {

        super.writeToNBT(nbtTagCompound);
        if(owner != null && owner != "") {
            nbtTagCompound.setString("teOwner", owner);
        }
    }

}
