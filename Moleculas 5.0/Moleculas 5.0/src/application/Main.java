package application;

import Helpers.Point;
import engine.*;

public class Main {

	public static void main(String[] args) {
		
		Grid grid = new Grid(400, 400, 400);
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		
		Point po = new Point(200, 200, 200);
		double radius = 60;
		
		
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				for(int k = 0; k < grid.dimZ; k++) {
					if(
						//k < grid.dimZ / 2
						(i - po.x)*(i - po.x) + (j - po.y)*(j - po.y) + (k - po.z)*(k - po.z) < 4000
					)
					{
						grid.setPoint(i, j, k);
						
					}					
				}
			}
		}
		
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);
		
	}

}
