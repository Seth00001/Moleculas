package application;

import Helpers.Point;
import engine.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Grid grid = new Grid(100, 100, 100);
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		
		Point po = new Point(100, 100, 200);
		double radius = 60;
		
		
		
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				for(int k = 0; k < grid.dimZ; k++) {
					if(
						k == 0
					)
					{
						grid.setPointUnchecked(i, j, k);
						
					}
//					if(k >= 1 && k <= 5) {
//						grid.setPoint(i, j, k);
//					}
					
				}
			}
		}
		
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);
//		
//		System.out.println(grid.getNeirbourghsCount(1, 1, 1));

		
//		grid.setPoint(1, 1, 1);

//		for(int i = 0; i < 10000; i++) {
//			for(Point p : grid.queue) {
//				grid.jump(grid.queue.get(0));
//			}
//		}
//		
//		
//		
//		Point p = grid.queue.get(0);
//		PositionData d = grid.getNextPosition(p.x, p.y, p.z);
//		System.out.println(String.format("%d  %d  %d", d.x, d.y, d.z));
//		System.out.println(grid.getNeirbourghsCount(p.x, p.y, p.z));
		
//		for(int i = 0; i < 100; i++) {
//			for(Point p : grid.queue) {
//				grid.jump(p);
//			}
//		}
//			try {
//			
//			helper.exportForVMD();	
//		}
//		catch(Exception e) {
//			
//		}
//		
		
		
		
		
		
	}

}
