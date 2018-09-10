package engine;

import java.util.ArrayList;
import java.util.Random;

import Helpers.Point;

public class Grid {
	
	public boolean[][][] grid; 
	public ArrayList<Point> queue;
	public Random random;
	public int dimX, dimY, dimZ;
	
	
	
	private boolean[][][] validationPattern;
	
	
	//#region optimization garbage section
	
//	private Point isValidTempPoint;
	
	//#endregion
	
	
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
		
				
		grid = new boolean[dimX][dimY][dimZ];
		queue = new ArrayList<Point>();
		random = new Random();
	}
	
	//#region
	
	//marks dimensions and grid specifics
	public boolean isValid(int x, int y, int z) {
		return(
				(x >= 0 && y >= 0 && z >= 0 && x < dimX && y < dimY && z < dimZ)
				&& validationPattern[x % 5][y % 5][z % 5]
			);
	}
	
	public void setPoint(int i, int j, int k) {
		if(isValid(i, j, k)) {
			grid[i][j][k] = true;
			queue.add(new Point(i, j, k));
		}
	}
	
	
	
	
	
	//#endregion
	
	
	
	
	
	
}
