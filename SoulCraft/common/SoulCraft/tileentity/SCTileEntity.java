package SoulCraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class SCTileEntity extends TileEntity {
	
	 	private byte direction;
	    private short state;
	    private String owner;
	    public ForgeDirection orientation = ForgeDirection.getOrientation(2);

	    public byte getDirection() {

	        return direction;
	    }

	    public void setDirection(byte direction) {

	        this.direction = direction;
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
	        orientation = ForgeDirection.getOrientation(nbtTagCompound.getInteger("orientation"));
	        direction = nbtTagCompound.getByte("teDirection");
	        state = nbtTagCompound.getShort("teState");
	        owner = nbtTagCompound.getString("teOwner");
	    }

	    public void writeToNBT(NBTTagCompound nbtTagCompound) {

	        super.writeToNBT(nbtTagCompound);
	        nbtTagCompound.setInteger("orientation", this.orientation.ordinal());
	        nbtTagCompound.setByte("teDirection", direction);
	        nbtTagCompound.setShort("teState", state);
	        if (owner != null && owner != "") {
	            nbtTagCompound.setString("teOwner", owner);
	        }
	    }

}
