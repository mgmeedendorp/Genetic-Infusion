package Seremis.SoulCraft.api.util;

public class Coordinate2D {

    public double x, y;

    public Coordinate2D() {

    }

    public Coordinate2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getXCoord() {
        return x;
    }

    public double getYCoord() {
        return y;
    }

    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate2D[x: " + x + " y: " + y + "]";
    }
}
