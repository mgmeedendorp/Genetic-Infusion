package seremis.geninfusion.api.util.vector

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
class Line3D(var head: Coordinate3D, var tail: Coordinate3D) {

    def this(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double) {
        this(new Coordinate3D(x1, y1, z1), new Coordinate3D(x2, y2, z2))
    }

    def getHead: Coordinate3D = this.head

    def getTail: Coordinate3D = this.tail

    def setLine(head: Coordinate3D, tail: Coordinate3D) {
        this.head = head
        this.tail = tail
    }

    def setLine(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double) {
        this.head = new Coordinate3D(x1, y1, z1)
        this.tail = new Coordinate3D(x2, y2, z2)
    }

    def setLineFromTile(tile1: TileEntity, tile2: TileEntity): Line3D = {
        this.head = new Coordinate3D(tile1)
        this.tail = new Coordinate3D(tile2)
        this
    }

    def getSide(coord: Coordinate3D): ForgeDirection = {
        var direction = ForgeDirection.UNKNOWN
        var getHead: Boolean = false
        if (coord == head) {
            getHead = true
        } else if (coord == tail) {
            getHead = false
        } else {
            return null
        }
        if (this.head != null && this.tail != null) {
            val varX = head.getXCoord - tail.getXCoord
            val varY = head.getYCoord - tail.getYCoord
            val varZ = head.getZCoord - tail.getZCoord
            if (Math.abs(varX) >= Math.abs(varY) && Math.abs(varX) >= Math.abs(varZ)) {
                if (varX >= 0) {
                    direction = ForgeDirection.EAST
                }
                if (varX < 0) {
                    direction = ForgeDirection.WEST
                }
            } else if (Math.abs(varY) >= Math.abs(varX) && Math.abs(varY) >= Math.abs(varZ)) {
                if (varY >= 0) {
                    direction = ForgeDirection.UP
                }
                if (varY < 0) {
                    direction = ForgeDirection.DOWN
                }
            } else if (Math.abs(varZ) >= Math.abs(varX) && Math.abs(varZ) >= Math.abs(varY)) {
                if (varZ >= 0) {
                    direction = ForgeDirection.SOUTH
                }
                if (varZ < 0) {
                    direction = ForgeDirection.NORTH
                }
            }
        }
        if (getHead) {
            if (direction == ForgeDirection.NORTH && head.getZCoord >= tail.getZCoord) {
                direction = ForgeDirection.NORTH.getOpposite
            }
            if (direction == ForgeDirection.EAST && head.getXCoord < tail.getXCoord) {
                direction = ForgeDirection.EAST.getOpposite
            }
            if (direction == ForgeDirection.SOUTH && head.getZCoord < tail.getZCoord) {
                direction = ForgeDirection.SOUTH.getOpposite
            }
            if (direction == ForgeDirection.WEST && head.getXCoord >= tail.getXCoord) {
                direction = ForgeDirection.WEST.getOpposite
            }
            if (direction == ForgeDirection.UP && head.getYCoord < tail.getYCoord) {
                direction = ForgeDirection.UP.getOpposite
            }
            if (direction == ForgeDirection.DOWN && head.getYCoord >= tail.getYCoord) {
                direction = ForgeDirection.DOWN.getOpposite
            }
        } else {
            if (direction == ForgeDirection.NORTH && tail.getZCoord >= head.getZCoord) {
                direction = ForgeDirection.NORTH.getOpposite
            }
            if (direction == ForgeDirection.EAST && tail.getXCoord < head.getXCoord) {
                direction = ForgeDirection.EAST.getOpposite
            }
            if (direction == ForgeDirection.SOUTH && tail.getZCoord < head.getZCoord) {
                direction = ForgeDirection.SOUTH.getOpposite
            }
            if (direction == ForgeDirection.WEST && tail.getXCoord >= head.getXCoord) {
                direction = ForgeDirection.WEST.getOpposite
            }
            if (direction == ForgeDirection.UP && tail.getYCoord < head.getYCoord) {
                direction = ForgeDirection.UP.getOpposite
            }
            if (direction == ForgeDirection.DOWN && tail.getYCoord >= head.getYCoord) {
                direction = ForgeDirection.DOWN.getOpposite
            }
        }
        direction
    }

    def getSide(tile: TileEntity): ForgeDirection = {
        val coord = new Coordinate3D(tile)
        if (coord.x == Math.floor(head.x) && coord.y == Math.floor(head.y) &&
            coord.z == Math.floor(head.z)) {
            return getSide(head)
        }
        if (coord.x == Math.floor(tail.x) && coord.y == Math.floor(tail.y) &&
            coord.z == Math.floor(tail.z)) {
            return getSide(tail)
        }
        null
    }

    def writeToNBT(compound: NBTTagCompound) {
        val compound2 = new NBTTagCompound()
        compound2.setDouble("headX", head.x)
        compound2.setDouble("headY", head.y)
        compound2.setDouble("headZ", head.z)
        compound2.setDouble("tailX", tail.x)
        compound2.setDouble("tailY", tail.y)
        compound2.setDouble("tailZ", tail.z)
        compound.setTag("LineCoordinates", compound2)
    }

    def readFromNBT(compound: NBTTagCompound) {
        if (compound.hasKey("LineCoordinates")) {
            val compound2 = compound.getCompoundTag("LineCoordinates")
            head.setCoord(compound2.getDouble("headX"), compound2.getDouble("headY"), compound2.getDouble("headZ"))
            tail.setCoord(compound2.getDouble("tailX"), compound2.getDouble("tailY"), compound2.getDouble("tailZ"))
        }
    }

    override def toString: String = "Line3D[head x: " + head.x + ", y: " + head.y + ", z: " + head.z + " tail x: " + tail.x + ", y: " + tail.y + ", z: " + tail.z + "]"

    def getYaw: Double = {
        val xd = head.x - tail.x
        val zd = head.z - tail.z
        Math.atan2(xd, zd) * 180.0D / Math.PI
    }

    def getPitch: Double = {
        val xd = head.x - tail.x
        val yd = head.y - tail.y
        val zd = head.z - tail.z
        val var7 = Math.sqrt(xd * xd + zd * zd)
        Math.atan2(yd, var7) * 180.0D / Math.PI
    }

    def getLength: Double = {
        val x = Math.abs(head.getXCoord - tail.getXCoord)
        val y = Math.abs(head.getYCoord - tail.getYCoord)
        val z = Math.abs(head.getZCoord - tail.getZCoord)
        Math.sqrt(x * x + y * y + z * z)
    }
}