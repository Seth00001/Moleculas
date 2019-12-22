package initialConditions;

import Helpers.Point;
import engine.Grid;
import logging.Logger;

public class CylinderProvider implements IInitialConditionProvider{
	
	public Point vector;
	public double radius;
	public double length;
	
	public CylinderProvider(Point vect) {
		vector = vect;
		length = 100;
		radius = 0;
	}

	public void SetUpd(Grid grid) {
		int x = grid.dimX / 2;
		int y = grid.dimY / 2; 
		int z = grid.dimZ / 2;
		
		double d = 0;//vector.x*i + vector.y*y + vector.z*z;
		double p = 0;//(vector.x*vector.x + vector.y*vector.y + vector.z*vector.z)/d;
		
		double a = 0;//vector.x * p;
		double b = 0;//vector.y * p;
		double c = 0;//vector.z * p;
	
		
		
		//populating
		for(int i = 0; i < grid.dimX; i ++) {
			for(int j = 0; j < grid.dimY; j ++) {
				for(int k = 0; k < grid.dimZ; k ++) {
					
					d = vector.x*i + vector.y*j + vector.z*k;
					p = d/(vector.x*vector.x + vector.y*vector.y + vector.z*vector.z);
					
					a = vector.x * p;
					b = vector.y * p;
					c = vector.z * p;
					
					double ax = (a - i)*(a - i) + (b - j)*(b - j) + (c - k)*(c - k);
					
					if(
							grid.isValid(i, j, k)
							&& ax <= radius*radius
							&& (x - a)*(x - a) + (y - b)*(y - b) + (z - c)*(z - c) <= (length*length)/4
					) {
						grid.setPoint(i, j, k);
						
//						System.out.println(i + "   " +  j + "    " + k  + "   " + (ax <= radius*radius));
					}
				}	
			}	
		}
		
		
		
	}
	
}
