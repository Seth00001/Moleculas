package Helpers;

public class Sphere {
	public DPoint center;
	public double radius;
	
	public Sphere() {
		this.center = new DPoint(0, 0, 0);
		this.radius = 0;
	}
	
	public Sphere(DPoint center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Sphere(double x, double y, double z, double radius) {
		this.center = new DPoint(x, y, z);
		this.radius = radius;
	}
	
	@Override
	public String toString() {
		return String.format("%d|%d|%d|%d", center.x, center.y, center.z, radius);
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		return this.hashCode() == o.hashCode();
	}
	
}
