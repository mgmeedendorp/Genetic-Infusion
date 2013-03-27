package Seremis.core.geometry;

public class Line3D {
	
	private Coordinate3D head, tail;
	
	public Line3D(Coordinate3D head, Coordinate3D tail) {
		this.head = head;
		this.tail = tail;
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
	
	public double getLength() {
		double x = Math.abs(head.getXCoord()-tail.getXCoord());
		double y = Math.abs(head.getYCoord()-tail.getYCoord());
		double z = Math.abs(head.getZCoord()-tail.getZCoord());
		return Math.sqrt(x*x+y*y+z*z);
	}
}