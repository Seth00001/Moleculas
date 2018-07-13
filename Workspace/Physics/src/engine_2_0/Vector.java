package engine_2_0;

public class Vector extends DPoint{

	public Vector(double x, double y, double z) {
		super(x, y, z);
	}
	
	public void normalize() {
		double norm = x * x + y * y + z * z;
		
		x = x / norm;
		y = y / norm;
		z = z / norm;
	}
	
	public void normalize(double norm) {
		double n = getNorm();
		
		x = x / n * norm;
		y = y / n * norm;
		z = z / n * norm;
	}
	
	public double getNorm() {
		return(Math.sqrt(x * x + y * y + z * z));
	}
	
	public void add(DPoint p) {
		x = x + p.x;
		y = y + p.y;
		z = z + p.z;
	}
	
	
	
	
}
