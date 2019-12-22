package initialConditions;

import engine.Grid;

public class SphereProvider implements IInitialConditionProvider{

	//settings
	public double radius;
	
	
	public void SphereProvider() {
		
	}
	
	public void SetUpd(Grid grid) {
		int x = grid.dimX / 2;
		int y = grid.dimY / 2; 
		int z = grid.dimZ / 2;
		
		for(int i = 0; i < grid.dimX; i ++) {
			for(int j = 0; j < grid.dimY; j ++) {
				for(int k = 0; k < grid.dimZ; k ++) {
					
					if(
							(x - i)*(x - i) + (y - j)*(y - j) + (z - k)*(z - k) <= radius*radius
					) {
						grid.setPoint(i, j, k);
					}
				}	
			}	
		}
		
		
	}
}
