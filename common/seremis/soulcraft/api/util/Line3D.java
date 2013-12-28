package seremis.soulcraft.api.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class Line3D {

    public Coordinate3D head, tail;

    public Line3D(Coordinate3D head, Coordinate3D tail) {
        this.head = head;
        this.tail = tail;
    }

    public Line3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.head = new Coordinate3D(x1, y1, z1);
        this.tail = new Coordinate3D(x2, y2, z2);
    }

    public Line3D() {
        this(0, 0, 0, 0, 0, 0);
    }

    public Coordinate3D getHead() {
        return this.head;
    }

    public Coordinate3D getTail() {
        return this.tail;
    }

    public void setLine(Coordinate3D head, Coordinate3D tail) {
        this.head = head;
        this.tail = tail;
    }

    public void setLine(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.head = new Coordinate3D(x1, y1, z1);
        this.tail = new Coordinate3D(x2, y2, z2);
    }

    public Line3D setLineFromTile(TileEntity tile1, TileEntity tile2) {
        this.head = new Coordinate3D(tile1);
        this.tail = new Coordinate3D(tile2);
        
        return this;
    }

    private double length;
    
    public double getLength() {
        if(length == 0) {
            double x = Math.abs(head.getXCoord() - tail.getXCoord());
            double y = Math.abs(head.getYCoord() - tail.getYCoord());
            double z = Math.abs(head.getZCoord() - tail.getZCoord());
            length = Math.sqrt(x * x + y * y + z * z);
        }
        return length;
    }

    public ForgeDirection getSide(Coordinate3D coord) {
        ForgeDirection direction = ForgeDirection.UNKNOWN;
        boolean getHead;
        if(coord.equals(head)) {
            getHead = true;
        } else if(coord.equals(tail)) {
            getHead = false;
        } else {
            return null;
        }

        if(this.head != null && this.tail != null) {
            double varX = head.getXCoord() - tail.getXCoord();
            double varY = head.getYCoord() - tail.getYCoord();
            double varZ = head.getZCoord() - tail.getZCoord();
            if(Math.abs(varX) >= Math.abs(varY) && Math.abs(varX) >= Math.abs(varZ)) {
                if(varX >= 0) {
                    direction = ForgeDirection.EAST;
                }
                if(varX < 0) {
                    direction = ForgeDirection.WEST;
                }
            } else if(Math.abs(varY) >= Math.abs(varX) && Math.abs(varY) >= Math.abs(varZ)) {
                if(varY >= 0) {
                    direction = ForgeDirection.UP;
                }
                if(varY < 0) {
                    direction = ForgeDirection.DOWN;
                }
            } else if(Math.abs(varZ) >= Math.abs(varX) && Math.abs(varZ) >= Math.abs(varY)) {
                if(varZ >= 0) {
                    direction = ForgeDirection.SOUTH;
                }
                if(varZ < 0) {
                    direction = ForgeDirection.NORTH;
                }
            }
        }

        if(getHead) {
            if(direction == ForgeDirection.NORTH && head.getZCoord() >= tail.getZCoord()) {
                direction = ForgeDirection.NORTH.getOpposite();
            }
            if(direction == ForgeDirection.EAST && head.getXCoord() < tail.getXCoord()) {
                direction = ForgeDirection.EAST.getOpposite();
            }
            if(direction == ForgeDirection.SOUTH && head.getZCoord() < tail.getZCoord()) {
                direction = ForgeDirection.SOUTH.getOpposite();
            }
            if(direction == ForgeDirection.WEST && head.getXCoord() >= tail.getXCoord()) {
                direction = ForgeDirection.WEST.getOpposite();
            }
            if(direction == ForgeDirection.UP && head.getYCoord() < tail.getYCoord()) {
                direction = ForgeDirection.UP.getOpposite();
            }
            if(direction == ForgeDirection.DOWN && head.getYCoord() >= tail.getYCoord()) {
                direction = ForgeDirection.DOWN.getOpposite();
            }
        } else {
            if(direction == ForgeDirection.NORTH && tail.getZCoord() >= head.getZCoord()) {
                direction = ForgeDirection.NORTH.getOpposite();
            }
            if(direction == ForgeDirection.EAST && tail.getXCoord() < head.getXCoord()) {
                direction = ForgeDirection.EAST.getOpposite();
            }
            if(direction == ForgeDirection.SOUTH && tail.getZCoord() < head.getZCoord()) {
                direction = ForgeDirection.SOUTH.getOpposite();
            }
            if(direction == ForgeDirection.WEST && tail.getXCoord() >= head.getXCoord()) {
                direction = ForgeDirection.WEST.getOpposite();
            }
            if(direction == ForgeDirection.UP && tail.getYCoord() < head.getYCoord()) {
                direction = ForgeDirection.UP.getOpposite();
            }
            if(direction == ForgeDirection.DOWN && tail.getYCoord() >= head.getYCoord()) {
                direction = ForgeDirection.DOWN.getOpposite();
            }
        }
        return direction;
    }

    public ForgeDirection getSide(TileEntity tile) {
        Coordinate3D coord = new Coordinate3D(tile);
        if(coord.x == Math.floor(head.x) && coord.y == Math.floor(head.y) && coord.z == Math.floor(head.z)) {
            return getSide(head);
        }
        if(coord.x == Math.floor(tail.x) && coord.y == Math.floor(tail.y) && coord.z == Math.floor(tail.z)) {
            return getSide(tail);
        }
        return null;
    }

    public void writeToNBT(NBTTagCompound compound) {
        NBTTagCompound compound2 = new NBTTagCompound();
        compound2.setDouble("headX", head.x);
        compound2.setDouble("headY", head.y);
        compound2.setDouble("headZ", head.z);
        compound2.setDouble("tailX", tail.x);
        compound2.setDouble("tailY", tail.y);
        compound2.setDouble("tailZ", tail.z);
        compound.setCompoundTag("LineCoordinates", compound2);
    }

    public void readFromNBT(NBTTagCompound compound) {
        if(compound.hasKey("LineCoordinates")) {
            NBTTagCompound compound2 = compound.getCompoundTag("LineCoordinates");
            head.setCoords(compound2.getDouble("headX"), compound2.getDouble("headY"), compound2.getDouble("headZ"));
            tail.setCoords(compound2.getDouble("tailX"), compound2.getDouble("tailY"), compound2.getDouble("tailZ"));
        }
    }

    @Override
    public String toString() {
        return "Line3D[head x: " + head.x + ", y: " + head.y + ", z: " + head.z + " tail x: " + tail.x + ", y: " + tail.y + ", z: " + tail.z + "]";
    }

    private double yaw = -1;
    
    /**
     * Get the line's yaw in degrees
     * 
     * @return
     */
    public double getYaw() {
        if(yaw == -1) {
            double xd = head.x - tail.x;
            double zd = head.z - tail.z;
    
            yaw = Math.atan2(xd, zd) * 180.0D / Math.PI;
        }
        return yaw;
    }

    private double pitch = -1;
    
    /**
     * Get this line's pitch in degrees
     * 
     * @return
     */
    public double getPitch() {
        if(pitch == -1) {
            double xd = head.x - tail.x;
            double yd = head.y - tail.y;
            double zd = head.z - tail.z;
    
            double var7 = Math.sqrt(xd * xd + zd * zd);
    
            pitch = Math.atan2(yd, var7) * 180.0D / Math.PI;
        }
        return pitch;
    }
}