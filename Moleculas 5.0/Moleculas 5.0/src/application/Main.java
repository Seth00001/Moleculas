package application;

import Helpers.DPoint;
import Helpers.Point;
import engine.*;

public class Main {

	public static void main(String[] args) {
		
		Grid grid = new Grid(100, 100, 100);
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		
		
		InitialConditionProvider conditionProvider = new InitialConditionProvider(20, 20);
		conditionProvider.setupEnvironment(grid);
		
//		for(int i = 0; i < grid.dimX ; i++) {
//			for(int j = 0; j < grid.dimY ; j++) {
//				for(int k = 0; k < grid.dimZ ; k++) {
//					
//					grid.setPoint(i, j, k);
//
//				}
//			}
//		}
		
//		System.out.println(grid.getNeirbourghsCount(1, 1, 1));
//		System.out.println(grid.grid[1][1][1]);
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);
		
	}

}
