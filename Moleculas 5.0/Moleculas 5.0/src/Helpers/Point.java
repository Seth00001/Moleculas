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
	
	@Override 
	public boolean equals(Object o) {
		return this.hashCode() == o.hashCode();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("%s | %s | %s", this.x, this.y, this.z);
	}
	
}
