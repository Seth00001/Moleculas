package Helpers;

public class Point{

	public int x, y, z;
	
	public Point() {
		setDefaultValue();
	}
	
	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void toDPoint(DPoint p) {
		p.x = x;
		p.y = y;
		p.z = z;
	}
	
	public void setDefaultValue() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	
}
