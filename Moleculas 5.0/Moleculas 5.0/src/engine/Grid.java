package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import Helpers.Point;
import application.Logger;

public class Grid {
	
	public byte[][][] grid; 
	public ArrayList<Point> queue;
	public Random random;
	public int dimX, dimY, dimZ;
	public IntegrityDataCollector collector;
	//#region environment parameters
	
	
	public double alpha = 2; 
	public double p0 = 0.685;
	
	//#endregion
	
	
	private boolean[][][] validationPattern;
	
	public Grid() {
		this(100, 100, 100);
	}
	
	public Grid(int DimX, int DimY, int DimZ) {
		dimX = DimX;
		dimY = DimY;
		dimZ = DimZ;
		
		validationPattern = new boolean[3][3][3];
		
		for(int i = 0; i < validationPattern.length; i++) {
			for(int j = 0; j < validationPattern[0].length; j++) {
				for(int k = 0; k < validationPattern[0][0].length; k++) {					
					validationPattern[i][j][k] = false;
				}
			}
		}

		collector = new IntegrityDataCollector(this);
		
//		validationPattern[0][0][0] = true;
//		validationPattern[0][0][2] = true;
//		validationPattern[0][2][0] = true;
//		validationPattern[2][0][0] = true;
//		validationPattern[0][2][2] = true;
//		validationPattern[2][0][2] = true;
//		validationPattern[2][2][0] = true;
//		validationPattern[2][2][2] = true;
//		
//		validationPattern[1][1][1] = true;
						
		validationPattern[0][0][0] = true;
		validationPattern[0][0][2] = true;
		validationPattern[0][2][0] = true;
		validationPattern[2][0][0] = true;
		validationPattern[0][2][2] = true;
		validationPattern[2][0][2] = true;
		validationPattern[2][2][0] = true;
		validationPattern[2][2][2] = true;
		
		validationPattern[0][1][1] = true;
		validationPattern[1][0][1] = true;
		validationPattern[1][1][0] = true;
		
		validationPattern[2][1][1] = true;
		validationPattern[1][2][1] = true;
		validationPattern[1][1][2] = true;
		
		
		grid = new byte[dimX][dimY][dimZ];
		queue = new ArrayList<Point>();
		random = new Random();
	}
	
	public void setAlpha(double value) {
		alpha = value;
		double ep0 = Math.pow(Math.E, alpha);
		p0 = Math.pow(0.685, alpha); // 0.685

		//Logger.INSTANCE.write(String .format("alpha: %s; p0: %s", alpha, p0));
		
	}
	
	public double getAlpha() {
		return alpha;
	}
	
	//region
	
	//marks dimensions and grid specifics
	public boolean isValid(int x, int y, int z) {
		return(
				(x >= 0 && y >= 0 && z >= 0 && x < dimX && y < dimY && z < dimZ)
				&& validationPattern[x % 2][y % 2][z % 2]
			);
	}
	
	public void setPoint(int i, int j, int k) {
		if(isValid(i, j, k)
			&& grid[i][j][k] == 0) 
		{
			grid[i][j][k] = 10;
			queue.add( new Point(i, j, k));
		}
	}
	
	public void setPointUnchecked(int i, int j, int k) {
		if(isValid(i, j, k)) {
			grid[i][j][k] = 127;
			//queue.add(new Point(i, j, k));
		}
	}
	
	public int getNeirbourghsCount(int x, int y, int z) {
		int count = 0;
		
		for(int i = -1; i <= 1; i++ ) {
			for(int j = -1; j <= 1; j++ ) {
				for(int  k = -1; k <= 1; k++) {
					if(isValid(x + i, y + j, z + k)
							&& !(i == 0 && j == 0 && k == 0)
							&& grid[x + i][y + j][z + k] > 9) {
						count++;
					}
				}
			}
		}		
		return count;
	}
	
	public boolean hasBoundedNeirbourghs(int x, int y, int z) {
		//boolean has = false;
		
		for(int i = -1; i <= 1; i++ ) {
			for(int j = -1; j <= 1; j++ ) {
				for(int  k = -1; k <= 1; k++) {
					if(isValid(x + i, y + j, z + k)
							&& !(i == 0 && j == 0 && k == 0)
							&& grid[x + i][y + j][z + k] > 9) {
						return true;
						/*has = true;
						break;*/
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
//		if( grid[x][y][z] < 10) {
//			data = getFreePositionData(x, y, z);
//		}
//		else {
			data = getBoundedPositionData(x, y, z);
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
		/*boolean globalHandle = hasBoundedNeirbourghs(i, j, k);
		if(!globalHandle || isTopRegion(i, j, k)) {
			grid[i][j][k] = 1;
		}else {
			grid[i][j][k] = 10;
		}*/
		grid[i][j][k] = (byte) ((!hasBoundedNeirbourghs(i, j, k) || isTopRegion(i, j, k))?1:10);
		
		p.x = i;
		p.y = j;
		p.z = k;
	}
	
	public void jump(Point p) {
		int count = getNeirbourghsCount(p.x, p.y, p.z);
		if( count < 8 && random.nextDouble() < Math.pow(p0, count)/*Math.exp(-1 * p0 * count) */) {
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
			
			//temp = null;
		}
	}
	
	//endregion
	 
	public boolean isTopRegion(int i, int j, int k) {
		
		return k > dimZ;
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
		return 0.001077 * 1.5 - count / volume;
//;//0.00128 * 4.0 - count / volume;//0.00017657 - count / volume;//0.00009625 - count / volume;
	}
	
	public ArrayList<ClusterData> CollectClusterData() {
		ArrayList<ClusterData> data = new ArrayList<ClusterData>();
		
		synchronized(grid)
		{
			
			LinkedList<Point> particles = new LinkedList<Point>(this.queue);
			//byte[][][] volume = this.grid.clone();
			byte[][][] volume = new byte[dimX][dimY][dimZ];
			
			for(int i = 0; i < dimX; i++) {
				for(int j = 0; j < dimY; j++) {
					for(int k = 0; k < dimZ; k++) {
						volume[i][j][k] = grid[i][j][k];
					}
				}
			}
			
			//normalizing the array
			for(Point p : particles) 
			{
				volume[p.x][p.y][p.z] = 1;
			}
	
			Point p;
			
			while(true)
			{
				//collect data for 1 cluster
				LinkedList<Point> q = new LinkedList<Point>();
				
				for(Point pp : particles) {
					if(volume[pp.x][pp.y][pp.z] == 1) {
						
						q.add(pp);
						volume[pp.x][pp.y][pp.z] = 10;
						
						break;
					}
				}
							
				if(q.size() == 0) break;
				
				for(int n = 0; n < q.size(); n++) 
				{
					p = q.get(n);
					
					if(isValid(p.x, p.y, p.z)) 
					{
						for(int i = -1; i <= 1; i++ ) {
							for(int j = -1; j <= 1; j++ ) {
								for(int  k = -1; k <= 1; k++) {
									if(isValid(p.x + i, p.y + j, p.z + k)
											&& volume[p.x + i][p.y + j][p.z + k] == 1) 
									{
										Point p1 = new Point(p.x + i, p.y + j, p.z + k);
										
										q.add(p1);
										
										volume[p1.x][p1.y][p1.z] = 10;
									}
								}
							}
						}		
					}	
				}
				data.add(new ClusterData(new ArrayList<Point>(q)));
			}

		}
		
		return data;
	}
	
	public ArrayList<ClusterData> CollectinvertedClusterData() {
		ArrayList<ClusterData> data = new ArrayList<ClusterData>();
		
		synchronized(grid)
		{
			
			LinkedList<Point> particles = new LinkedList<Point>(this.queue);
			byte[][][] volume = new byte[dimX][dimY][dimZ];
			
			for(int i = 0; i < dimX; i++) {
				for(int j = 0; j < dimY; j++) {
					for(int k = 0; k < dimZ; k++) {
						if(isValid(i, j, k)) {
							volume[i][j][k] = 1;
						}
					}
				}
			}

			//normalizing the array
			for(Point p : particles) 
			{
				volume[p.x][p.y][p.z] = 0;
			}

			particles = new LinkedList<Point>();
			for(int i = 0; i < dimX; i++) {
				for(int j = 0; j < dimY; j++) {
					for(int k = 0; k < dimZ; k++) {
						if(volume[i][j][k] == 1) {
							particles.add(new Point(i, j, k));
						}
					}
				}
			}

			Point p;
			
			while(true)
			{
				//collect data for 1 cluster
				LinkedList<Point> q = new LinkedList<Point>();
				
				for(Point pp : particles) {
					if(volume[pp.x][pp.y][pp.z] == 1) {
						
						q.add(pp);
						volume[pp.x][pp.y][pp.z] = 10;
						
						break;
					}
				}
			
				if(q.size() == 0) break;
				
				for(int n = 0; n < q.size(); n++) 
				{
					p = q.get(n);
					
					if(isValid(p.x, p.y, p.z)) 
					{
						for(int i = -1; i <= 1; i++ ) {
							for(int j = -1; j <= 1; j++ ) {
								for(int  k = -1; k <= 1; k++) {
									if(isValid(p.x + i, p.y + j, p.z + k)
											&& volume[p.x + i][p.y + j][p.z + k] == 1) 
									{
										Point p1 = new Point(p.x + i, p.y + j, p.z + k);
										
										q.add(p1);
										
										volume[p1.x][p1.y][p1.z] = 10;
									}
								}
							}
						}		
					}	
				}
				data.add(new ClusterData(new ArrayList<Point>(q)));
			}
		}
		
		return data;
	}

	public double getAverageLinksCount() {
		int neirbourghs = 0;
		int particlesCount = 0;
		
		int tempNC = 0;
		
		for(Point p : queue) {
			tempNC = getNeirbourghsCount(p.x, p.y, p.z);
			if(tempNC < 8) {
				neirbourghs = neirbourghs + tempNC; 
				particlesCount++;
			}
		}
		
		return neirbourghs/particlesCount;
	}
	
}
