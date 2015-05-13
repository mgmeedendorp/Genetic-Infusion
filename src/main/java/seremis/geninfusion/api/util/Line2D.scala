package seremis.geninfusion.api.util

class Line2D(var head: Coordinate2D, var tail: Coordinate2D) {

    def this(x1: Double, y1: Double, x2: Double, y2: Double) {
        this(new Coordinate2D(x1, y1), new Coordinate2D(x2, y2))
    }

    def getHead: Coordinate2D = this.head

    def getTail: Coordinate2D = this.tail

    def setLine(head: Coordinate2D, tail: Coordinate2D) {
        this.head = head
        this.tail = tail
    }

    def setLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        this.head = new Coordinate2D(x1, y1)
        this.tail = new Coordinate2D(x2, y2)
    }

    def getLength: Double = {
        val dx = Math.abs(head.getXCoord - tail.getXCoord)
        val dy = Math.abs(head.getYCoord - tail.getYCoord)
        Math.sqrt(dx * dx + dy * dy)
    }

    def getYaw: Double = {
        val xd = head.x - tail.x
        val yd = head.y - tail.y
        Math.atan2(xd, yd) * 180.0D / Math.PI
    }

    override def toString: String = "Line2D[head x: " + head.x + ", y: " + head.y + " tail x: " + tail.x + ", y: " + tail.y + "]"

}