package Seremis.SoulCraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class SCTileEntity extends TileEntity {
	
	    private short state;
	    private String owner;
	    public int orientation;

	    public int getDirection() {

	        return orientation;
	    }

	    public void setOrientation(int orientation) {

	        this.orientation = orientation;
	    }

	    public short getState() {

	        return state;
	    }

	    public void setState(short state) {

	        this.state = state;
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

	    public void readFromNBT(NBTTagCompound nbtTagCompound) {

	        super.readFromNBT(nbtTagCompound);
	        orientation = nbtTagCompound.getInteger("orientation");
	        state = nbtTagCompound.getShort("teState");
	        owner = nbtTagCompound.getString("teOwner");
	    }

	    public void writeToNBT(NBTTagCompound nbtTagCompound) {

	        super.writeToNBT(nbtTagCompound);
	        nbtTagCompound.setInteger("orientation", orientation);
	        nbtTagCompound.setShort("teState", state);
	        if (owner != null && owner != "") {
	            nbtTagCompound.setString("teOwner", owner);
	        }
	    }

}
