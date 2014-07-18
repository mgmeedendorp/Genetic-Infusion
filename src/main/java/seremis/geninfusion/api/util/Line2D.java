package seremis.geninfusion.api.util;

public class Line2D {

    public Coordinate2D head, tail;

    public Line2D(Coordinate2D head, Coordinate2D tail) {
        this.head = head;
        this.tail = tail;
    }

    public Line2D(double x1, double y1, double x2, double y2) {
        this.head = new Coordinate2D(x1, y1);
        this.tail = new Coordinate2D(x2, y2);
    }

    public Line2D() {
        this(0, 0, 0, 0);
    }

    public Coordinate2D getHead() {
        return this.head;
    }

    public Coordinate2D getTail() {
        return this.tail;
    }

    public void setLine(Coordinate2D head, Coordinate2D tail) {
        this.head = head;
        this.tail = tail;
    }

    public void setLine(double x1, double y1, double x2, double y2) {
        this.head = new Coordinate2D(x1, y1);
        this.tail = new Coordinate2D(x2, y2);
    }

    public Line2D setLine(Line3D coord) {
        this.head = coord.head.to2D();
        this.tail = coord.tail.to2D();
        return this;
    }

    private double length = -1;
    
    public double getLength() {
        if(length == -1) {
            double dx = Math.abs(head.getXCoord() - tail.getXCoord());
            double dy = Math.abs(head.getYCoord() - tail.getYCoord());
            length = Math.sqrt(dx * dx + dy * dy);
        }
        return length;
    }

    private double yaw = -1;
    
    public double getYaw() {
        if(yaw == -1) {
            double xd = head.x - tail.x;
            double yd = head.y - tail.y;
    
            yaw = Math.atan2(xd, yd) * 180.0D / Math.PI;
        }
        return yaw;
    }

    @Override
    public String toString() {
        return "Line2D[head x: " + head.x + ", y: " + head.y + " tail x: " + tail.x + ", y: " + tail.y + "]";
    }
}