package engine;

public class PositionData {
	public int x, y, z;
	public double weight;

	public PositionData() {
		setDefaultValue();
	}
	
	public PositionData(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		weight = 0;
	}
	
	public PositionData(int x, int y, int z, double weight) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.weight = weight;
	}
	
	public void setDefaultValue() {
		x = 0;
		y = 0;
		z = 0;
		weight = 0;
	}
	
	public void setValue(int x, int y, int z, double weight) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.weight = weight;
	}
}
