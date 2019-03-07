package application;

import java.util.ArrayList;

import Helpers.DPoint;
import Helpers.Point;
import engine.Grid;

public class InitialConditionProvider {

	private boolean[][][] validationPattern;
	int sphereRadius = 15, sphereDsitance = 10;
	
	
	
	public InitialConditionProvider() {
		
		validationPattern = new boolean[5][5][5];
		
		for(int i = 0; i < validationPattern.length; i++) {
			for(int j = 0; j < validationPattern[0].length; j++) {
				for(int k = 0; k < validationPattern[0][0].length; k++) {					
					validationPattern[i][j][k] = false;
				}
			}
		}
		//hex grid interpretation
		/*
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
		*/
		
		
		//square grid interpretation
		/*
		validationPattern[0][0][0] = true;
		
		validationPattern[2][0][0] = true;
		validationPattern[0][2][0] = true;
		validationPattern[0][0][2] = true;
		
		validationPattern[2][2][2] = true;
		
		validationPattern[0][2][2] = true;
		validationPattern[2][0][2] = true;
		validationPattern[2][2][0] = true;
		
		validationPattern[4][0][0] = true;
		validationPattern[0][4][0] = true;
		validationPattern[0][0][4] = true;
		
		validationPattern[0][4][4] = true;
		validationPattern[4][0][4] = true;
		validationPattern[4][4][0] = true;
		
		validationPattern[4][2][0] = true;
		validationPattern[4][0][2] = true;
		
		validationPattern[2][4][0] = true;
		validationPattern[2][0][4] = true;
		
		validationPattern[0][2][4] = true;
		validationPattern[0][4][2] = true;
		
		validationPattern[4][2][2] = true;
		
		validationPattern[4][4][2] = true;
		validationPattern[4][2][4] = true;
		validationPattern[2][4][4] = true;
		
		validationPattern[4][4][4] = true;
		
		
		validationPattern[1][1][1] = true;
		
		validationPattern[3][3][3] = true;
		
		validationPattern[3][1][1] = true;
		validationPattern[1][3][1] = true;
		validationPattern[1][1][3] = true;
		
		validationPattern[3][3][1] = true;
		validationPattern[3][1][3] = true;
		validationPattern[1][3][3] = true;
		*/
		
		/////////
		validationPattern[0][0][0] = true;
		validationPattern[2][0][0] = true;
		validationPattern[4][0][0] = true;
		
		validationPattern[1][2][0] = true;
		validationPattern[3][2][0] = true;
		
		validationPattern[4][0][0] = true;
		validationPattern[4][2][0] = true;
		validationPattern[4][4][0] = true;
		
		/////////
		validationPattern[1][1][1] = true;
		validationPattern[3][1][1] = true;
		validationPattern[0][3][1] = true;
		validationPattern[2][3][1] = true;
		validationPattern[4][3][1] = true;
		
		/////////
		validationPattern[0][0][2] = true;
		validationPattern[2][0][2] = true;
		validationPattern[4][0][2] = true;
		
		validationPattern[1][2][2] = true;
		validationPattern[3][2][2] = true;
		
		validationPattern[4][0][2] = true;
		validationPattern[4][2][2] = true;
		validationPattern[4][4][2] = true;
		
		/////////
		validationPattern[1][1][3] = true;
		validationPattern[3][1][3] = true;
		validationPattern[0][3][3] = true;
		validationPattern[2][3][3] = true;
		validationPattern[4][3][3] = true;
		
		
		/////////
		validationPattern[0][0][4] = true;
		validationPattern[2][0][4] = true;
		validationPattern[4][0][4] = true;
		
		validationPattern[1][2][4] = true;
		validationPattern[3][2][4] = true;
	
		validationPattern[4][0][4] = true;
		validationPattern[4][2][4] = true;
		validationPattern[4][4][4] = true;
	
	
	
	}
	
	public InitialConditionProvider(int radius, int distance) {
		this();
		
		sphereRadius = radius;
		sphereDsitance = distance;
	}
	
	public void setupEnvironment(Grid grid) {
		Point sector;
		DPoint center;
		double dx,dy,dz;
		dx = grid.dimX - 2 * sphereRadius - sphereDsitance;
		dy = grid.dimY - 2 * sphereRadius - sphereDsitance;
		dz = grid.dimZ - 2 * sphereRadius - sphereDsitance;
		
		ArrayList<DPoint> centers = new ArrayList<DPoint>();
		
		for(int i = 0; i < grid.dimX / sphereDsitance; i++) {
			for(int j = 0; j < grid.dimY / sphereDsitance; j++) {
				for(int k = 0; k < grid.dimZ / sphereDsitance; k++) {
					
					if(validationPattern[i % 4][j % 4][k % 4] 
						&& i*sphereDsitance > 0 && i*sphereDsitance < dx
						&& j*sphereDsitance > 0 && j*sphereDsitance < dy
						&& k*sphereDsitance > 0 && k*sphereDsitance < dz
					)
					{
						DPoint p = GetOffset(i, j, k);
							centers.add(new DPoint(
									(i + 0.5)*sphereDsitance,
									(j + 0.5)*sphereDsitance * 0.7,
									(k + 0.5)*sphereDsitance));
					}

				}
			}
		}
		
//		System.out.println(centers.size());
		
		for(int n = 0; n < centers.size(); n++) {
		
			for(int i = (int)(centers.get(n).x - sphereRadius); i < (int)(centers.get(n).x + sphereRadius); i++) {
				for(int j = (int)(centers.get(n).y - sphereRadius); j < (int)(centers.get(n).y + sphereRadius); j++) {
					for(int k = (int)(centers.get(n).z - sphereRadius); k < (int)(centers.get(n).z + sphereRadius); k++) {
						
						//sector = new Point(((i - sphereDsitance/2)/sphereDsitance), ((j - sphereDsitance/2)/sphereDsitance), ((k - sphereDsitance / 2)/sphereDsitance));
						
						center = centers.get(n);
						
						if(grid.isValid(i, j, k)
								&& (center.x - i)*(center.x - i) + (center.y - j)*(center.y - j) + (center.z - k)*(center.z - k) < sphereRadius*sphereRadius
								) {
							grid.setPoint(i, j, k);
						}
						
					}
				}
			}
			
		}
		
		
		
	}	
	
	public DPoint GetOffset(int i, int j, int k) {
		DPoint p = new DPoint(0, 0, 0);
		
		
//		p.x += j%2 * (sphereDsitance/2);
//		p.z += k%2 * (sphereDsitance);
		
		return p;
	}
	
}
