package Seremis.core.geometry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class Coordinate3D {
	
	private double x, y, z;
	
	public Coordinate3D() {
		
	}
	
	public Coordinate3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getXCoord() {
		return this.x;
	}
	
	public double getYCoord() {
		return this.y;
	}
	
	public double getZCoord() {
		return this.z;
	}
	
	public void setCoords(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setCoords(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setCoordsFromTile(TileEntity tile) {
		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
	}
	
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		nbtTagCompound.setDouble("XCoord", x);
		nbtTagCompound.setDouble("YCoord", y);
		nbtTagCompound.setDouble("ZCoord", z);
	}
	
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		if(nbtTagCompound.hasKey("XCoord")){
			this.x = nbtTagCompound.getDouble("XCoord");
			this.y = nbtTagCompound.getDouble("YCoord");
			this.z = nbtTagCompound.getDouble("ZCoord");
		}
	}
}
