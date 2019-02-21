package application;

import engine.*;

public class Main {

	public static void main(String[] args) {
		Logger.INSTANCE.startLog();
		Grid grid = new Grid(250, 250, 260);
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		
//		r~5a; d ~1.5a;
//		a = 2
		InitialConditionProvider conditionProvider = new InitialConditionProvider(10, 12);
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
