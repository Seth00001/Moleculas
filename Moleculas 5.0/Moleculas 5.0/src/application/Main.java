package application;

import java.util.ArrayList;

import Helpers.Point;
import engine.*;

public class Main {

	public static void main(String[] args) {
		Logger.INSTANCE.startLog();
		Grid grid = new Grid(60, 60, 60);		GridHelper helper = new GridHelper();
		helper.grid = grid;
		
//		r~5a; d ~1.5a;
//		a = 2
		InitialConditionProvider conditionProvider = new InitialConditionProvider(8, 12);
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

//		System.out.println(grid.queue.size());
//		System.out.println(grid.CollectClusterData().size());
		ArrayList<ClusterData> data = grid.CollectClusterData();
		System.out.println(data.size());
		
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);
	
//		ArrayList<Point> p = new ArrayList<Point>();
//		
//		Point p1 = new Point(1, 1, 1);
//		Point p2 = new Point(1, 1, 1);
//		
//		p.add(p1);
//		System.out.println(p.contains(p2));
//		System.out.println(p1.equals(p2));
//		System.out.println(p1.hashCode());
//		System.out.println(p2.hashCode());
		
		
	}

}
