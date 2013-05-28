package Seremis.core.geometry;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class Coordinate3D implements Cloneable {
	
	public double x = 0, y = 0, z = 0;
	
	public Coordinate3D() {
		
	}
	
	public Coordinate3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Coordinate3D(Entity ent) {
        this.x = ent.posX;
        this.y = ent.posY;
        this.z = ent.posZ;
    }
	
	public Coordinate3D(TileEntity tile) {
	    this.x = tile.xCoord;
	    this.y = tile.yCoord;
	    this.z = tile.zCoord;
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
	
	public double getDistanceTo(Coordinate3D coordinate) {
	    Line3D line = new Line3D(this, coordinate);
	    return line.getLength();
	}
	
	public Coordinate3D clone() {
	    return new Coordinate3D(x, y, z);
	}
}
