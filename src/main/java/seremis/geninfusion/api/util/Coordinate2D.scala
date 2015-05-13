package seremis.geninfusion.api.util

class Coordinate2D(var x: Double, var y: Double) {

    def getXCoord: Double = x
    def getYCoord: Double = y

    def setXCoord(x: Double) = this.x = x
    def setYCoord(y: Double) = this.y = y

    def setCoord(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    override def toString: String = {
        "Coordinate2D[x: " + x + " y: " + y + "]"
    }
}
