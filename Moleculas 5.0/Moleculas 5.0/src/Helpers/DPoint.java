package Helpers;

public class DPoint {
	public double x;
	public double y;
	public double z;
	
	public DPoint() {
		setDefaultValue();
	}
	
	public DPoint(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void toPoint(Point p) {
		p.x = (int) x;
		p.y = (int) y;
		p.z = (int) z;
	}

	public void setDefaultValue() {
		x = 0;
		y = 0;
		z = 0;
	}
	
}
