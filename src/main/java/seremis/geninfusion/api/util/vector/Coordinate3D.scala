package seremis.geninfusion.api.util.vector

import net.minecraft.entity.Entity
import net.minecraft.tileentity.TileEntity

class Coordinate3D(var x: Double, var y: Double, var z: Double) {

    def this(entity: Entity) {
        this(entity.posX, entity.posY, entity.posZ)
    }

    def this(tile: TileEntity) {
        this(tile.xCoord, tile.yCoord, tile.zCoord)
    }

    def getXCoord: Double = x
    def getYCoord: Double = y
    def getZCoord: Double = z

    def setXCoord(x: Double) = this.x = x
    def setYCoord(y: Double) = this.y = y
    def setZCoord(z: Double) = this.z = z

    def setCoord(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    def move(x: Double, y: Double, z: Double): Coordinate3D = {
        this.x += x
        this.y += y
        this.z += z
        this
    }

    def move(coord: Coordinate3D): Coordinate3D = move(coord.x, coord.y, coord.z)

    def moveBack(x: Double, y: Double, z: Double): Coordinate3D = {
        this.x -= x
        this.y -= y
        this.z -= z
        this
    }

    def moveBack(coord: Coordinate3D): Coordinate3D = moveBack(coord.x, coord.y, coord.z)

    def toArray: Array[Double] = Array(x, y, z)

    def fromArray(coords: Array[Double]): Coordinate3D = {
        x = coords(0)
        y = coords(1)
        z = coords(2)
        this
    }

    def getDistanceTo(coordinate: Coordinate3D): Double = {
        val line = new Line3D(this, coordinate)
        line.getLength
    }

    override def clone(): Coordinate3D = new Coordinate3D(x, y, z)

    override def equals(o: Any): Boolean = {
        if (o.isInstanceOf[Coordinate3D]) {
            val coord = o.asInstanceOf[Coordinate3D]
            return coord.x == x && coord.y == y && coord.z == z
        }
        if (o.isInstanceOf[TileEntity]) {
            return equals(new Coordinate3D(o.asInstanceOf[TileEntity]))
        }
        if (o.isInstanceOf[Entity]) {
            return equals(new Coordinate3D(o.asInstanceOf[Entity]))
        }
        false
    }

    override def toString: String = "Coordinate3D[x: " + x + " y: " + y + " z: " + z + "]"
}
