package engine;

import java.util.ArrayList;
import java.util.Random;

import Helpers.Point;

public class Grid {
	
	public byte[][][] grid; 
	public ArrayList<Point> queue;
	public Random random;
	public int dimX, dimY, dimZ;
	public double concentration;
	//#region environment parameters
	
	
	public double alpha = 2; 
	public double p0 = 0.685;
	
	//#endregion
	
	
	private boolean[][][] validationPattern;
	
	
	//#region optimization garbage section
	
	private int neirbourghsCountResult;
	private PositionData collectedPositionData;
	
	
	//#endregion optimization garbage section
	
	public Grid() {
		this(100, 100, 100);
	}
	
	public Grid(int DimX, int DimY, int DimZ) {
		dimX = DimX;
		dimY = DimY;
		dimZ = DimZ;
		
		validationPattern = new boolean[5][5][5];
		
		for(int i = 0; i < validationPattern.length; i++) {
			for(int j = 0; j < validationPattern[0].length; j++) {
				for(int k = 0; k < validationPattern[0][0].length; k++) {					
					validationPattern[i][j][k] = false;
				}
			}
		}
		
		validationPattern[0][0][0] = true;
		validationPattern[0][0][4] = true;
		validationPattern[0][4][0] = true;
		validationPattern[4][0][0] = true;
		validationPattern[0][4][4] = true;
		validationPattern[4][0][4] = true;
		validationPattern[4][4][0] = true;
		validationPattern[4][4][4] = true;
		
		validationPattern[2][2][0] = true;
		
		validationPattern[1][1][1] = true;
		validationPattern[3][3][1] = true;
		
		validationPattern[2][0][2] = true;
		validationPattern[0][2][2] = true;
		validationPattern[4][2][2] = true;
		validationPattern[2][4][2] = true;

		validationPattern[1][3][3] = true;
		validationPattern[3][1][3] = true;
		
		validationPattern[2][2][4] = true;
		
				
		grid = new byte[dimX][dimY][dimZ];
		queue = new ArrayList<Point>();
		random = new Random();
		
		concentration = 0.001077 / 4;
	
		
	}
	
	public void setAlpha(double value) {
		alpha = value;
		double ep0 = Math.pow(Math.E, alpha);
		p0 = Math.pow(0.685, alpha); // 0.685
		
		System.out.println(String .format("alpha: %s; p0: %s", alpha, p0));
		
	}
	
	public double getAlpha() {
		return alpha;
	}
	
	//region
	
	//marks dimensions and grid specifics
	public boolean isValid(int x, int y, int z) {
		return(
				(x >= 0 && y >= 0 && z >= 0 && x < dimX && y < dimY && z < dimZ)
				&& validationPattern[x % 4][y % 4][z % 4]
				//&& (x - dimX*0.5)*(x - dimX*0.5) + (y - dimY*0.5)*(y - dimY*0.5) < dimX*dimX * 0.25
			);
	}
	
	public void setPoint(int i, int j, int k) {
		if(isValid(i, j, k)) {
			grid[i][j][k] = 10;
			queue.add(new Point(i, j, k));
		}
	}
	
	public void setPointUnbound(int i, int j, int k) {
		if(isValid(i, j, k)) {
			grid[i][j][k] = 1;
			queue.add(new Point(i, j, k));
		}
	}
	
	public void setPointUnchecked(int i, int j, int k) {
		if(isValid(i, j, k)) {
			grid[i][j][k] = 127;
			//queue.add(new Point(i, j, k));
		}
	}
	
	public int getNeirbourghsCount(int x, int y, int z) {
//		int count = 0;
		neirbourghsCountResult = 0;
		
		for(int i = -1; i <= 1; i++ ) {
			for(int j = -1; j <= 1; j++ ) {
				for(int  k = -1; k <= 1; k++) {
					if(isValid(x + i, y + j, z + k)
							&& (i != 0 && j != 0 && k != 0)
							&& grid[x + i][y + j][z + k] > 9) {
//						count++;
						neirbourghsCountResult++;
					}
				}
			}
		}
//		return count;
		return neirbourghsCountResult;
	}
	
	public boolean hasBoundedNeirbourghs(int x, int y, int z) {
		for(int i = -1; i <= 1; i++ ) {
			for(int j = -1; j <= 1; j++ ) {
				for(int  k = -1; k <= 1; k++) {
					if(isValid(x + i, y + j, z + k)
							&& (i != 0 && j != 0 && k != 0)
							&& grid[x + i][y + j][z + k] > 9) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/*public int getPositionWeight(int x, int y, int z) {
		
		int count = 0;
		
		for(int i = -1; i <= 1; i++ ) {
			for(int j = -1; j <= 1; j++ ) {
				for(int  k = -1; k <= 1; k++) {
					if(isValid(x + i, y + j, z + k)
							&& (i != 0 && j != 0 && k != 0)
							&& grid[x + i][y + j][z + k] > 9
							&& getNeirbourghsCount(x + i, y + j, z + k) > 0) {
						count++;
					}
				}
			}
		}
		return count;
	}*/
	
	public ArrayList<PositionData> getBoundedPositionData(int x, int y, int z){
		ArrayList<PositionData> data = new ArrayList<PositionData>();
		
		for(int i = -1; i <= 1; i++ ) {
			for(int j = -1; j <= 1; j++ ) {
				for(int  k = -1; k <= 1; k++) {
					
					if(isValid(x + i, y + j, z + k)
							&& !(i == 0 && j == 0 && k == 0)
							&& grid[x + i][y + j][z + k] == 0 
							) {
						data.add(new PositionData(x + i, y + j, z + k, 
//								getNeirbourghsCount(x + i, y + j, z + k) != 0 ? Math.exp( alpha * (getNeirbourghsCount(x + i, y + j, z + k))) : 0
								Math.exp( alpha * (getNeirbourghsCount(x + i, y + j, z + k)))
								));
					}
				}
			}
		}
		
		data.add(new PositionData(x, y, z, Math.exp( alpha * (getNeirbourghsCount(x, y, z)))));
		
		return data;
	}
	
	public ArrayList<PositionData> getFreePositionData(int x, int y, int z){
		ArrayList<PositionData> data = new ArrayList<PositionData>();
		
		for(int i = -1; i <= 1; i++ ) {
			for(int j = -1; j <= 1; j++ ) {
				for(int  k = -1; k <= 1; k++) {

					if(isValid(x + i, y + j, z + k)
							&& grid[x + i][y + j][z + k] == 0
							) {
						
						data.add(new PositionData(x + i, y + j, z + k, 
								1
								));

					}
				}
			}
		}
		
		data.add(new PositionData(x, y, z, 1));

		return data;
	}
	
	public PositionData getNextPosition(int x, int y, int z) {
		ArrayList<PositionData> data;
//		if( grid[x][y][z] < 10 ) {
//			data = getFreePositionData(x, y, z);
//		}
//		else {
//			data = getBoundedPositionData(x, y, z);
//		}
		
		//if( grid[x][y][z] > 9 || hasBoundedNeirbourghs(x, y, z)) 
		{
			data = getBoundedPositionData(x, y, z);
			
		}
//		else 
//		{
//			data = getFreePositionData(x, y, z);
//		}
		
		
		
		double amplitude = 0;
		
		for(int i = 0; i < data.size(); i++) {
			amplitude += data.get(i).weight;
		}
		
		double point = amplitude * random.nextDouble();
		double sum = 0;
		
		for(int i = 0; i < data.size(); i++) {
			sum += data.get(i).weight;
			if(sum >= point) {
				return data.get(i);
			}
		}
		
		return data.get(data.size() - 1);
	}

	//from, where
	public void move(Point p, int i, int j, int k) {
		
		grid[p.x][p.y][p.z] = 0;
		boolean globalHandle = hasBoundedNeirbourghs(i, j, k);
		if(!globalHandle || isTopRegion(i, j, k)) {
			grid[i][j][k] = 1;
		}
		else {
			grid[i][j][k] = 10;
		}
		
		p.x = i;
		p.y = j;
		p.z = k;
		
		
		/*boolean globalHandle = hasBoundedNeirbourghs(i, j, k);
		if(!globalHandle || isTopRegion(i, j, k)) {
			grid[i][j][k] = 1;
		}
		else {
			grid[i][j][k] = 10;
		}
		grid[p.x][p.y][p.z] = 0;
		p.x = i;
		p.y = j;
		p.z = k;*/
	}
	
	public void jump(Point p) {
		int count = getNeirbourghsCount(p.x, p.y, p.z);
		if( count < 4 && random.nextDouble() < Math.pow(p0, count)/*Math.exp(-1 * p0 * count) */) {
			PositionData data = getNextPosition(p.x, p.y, p.z);
			if(grid[data.x][data.y][data.z] == 0) {
				move(p, data.x, data.y, data.z);	
			}
			
		}
	}
	
	public void rearrange() {
		
		int x, y;
		Point temp;
		for(int i = 0; i < queue.size() / 2 ; i++) {
			x = random.nextInt(queue.size());
			y = random.nextInt(queue.size());
			
			temp = queue.get(x);
			queue.set(x, queue.get(y));
			queue.set(y, temp);
			
			temp = null;
		}
	}
	
	//endregion
	 
	public boolean isTopRegion(int i, int j, int k) {
		
		return k > dimZ* 0.75;
	}
	
	public void refillTopRegion(double probability) {
		for(int i = 0; i < dimX; i++) {
			for(int j = 0; j < dimY; j++) {
				for(int k = 0; k < dimZ; k++) {
					if(isValid(i, j, k) && isTopRegion(i, j, k) && grid[i][j][k] == 0 && random.nextDouble() < probability) {
						setPoint(i, j, k);
					}
				}
			}
		}
	}
	
	public double probab() {
		double count = 0;
		double volume = 0;//(int) (dimX * dimY * dimZ * 0.2);
		
	
		for(int i = 0; i < dimX; i++) {
			for(int j = 0; j < dimY; j++) {
				for(int k = 0; k < dimZ; k++) {
					if(isTopRegion(i, j, k)) {
						if(grid[i][j][k] != 0) {
							count++;
						}
						if(isValid(i, j, k)) {
							volume++;
						}
					}
				}
			}
		}

		//System.out.println(count + " / " + volume + " count/volume");
		//0.015508
		return concentration - count / volume;
//;//0.00128 * 4.0 - count / volume;//0.00017657 - count / volume;//0.00009625 - count / volume;
	}
	
}
