package Seremis.SoulCraft.api.util;

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

    public double getLength() {
        double x = Math.abs(head.getXCoord() - tail.getXCoord());
        double y = Math.abs(head.getYCoord() - tail.getYCoord());
        return Math.sqrt(x * x + y * y);
    }
}
