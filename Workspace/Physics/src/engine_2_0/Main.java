package engine_2_0;

import java.time.temporal.JulianFields;
import java.util.ArrayList;

import gui.Window;

public class Main {
	
	static double coefA, coefB, coefC;
	
	public static String path = "Backups\\2083285 Backup";
	
	public static void main(String[] args) {
		
		int radius, height, dim;
		radius = 1000;
		height = 3000;
		dim = 100;
		

		HexGridHelper helper = new HexGridHelper(createGrid( 100, 100, 100));
	

//		HexGridHelper helper = new HexGridHelper();
//		helper.loadGrid(path);
		
		System.out.println(helper.grid.lowerBoundary + "   " + helper.grid.higherBoundary);
		
		
		Window win = new Window();
		
		helper.currentlyPaintedPlane = 0;
		
		win.setHelper(helper);
		win.refreshPanel();
		
//		helper.grid.populateTopLevels(1000);
		
		new Thread(new Runnable() {
			public void run() {
				
				while(true) {
					try {
						Thread.currentThread().sleep(win.backupPeriod);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(helper.step);
				}
			}
		}).start();		
	}	
	
	public static HexGrid createGrid(int dx, int dy, int dz) { 
		HexGrid grid = new HexGrid(dx, dy, dz);
		
		DPoint x = new DPoint(-1/3, 1/6, -1/6),
				y = new DPoint(0, 0.5, 0.5),
				z = new DPoint(-1/6, 0.5, 1/3);
		
		
		//#region
		
		//grid.populateAllLevels((int)(10/*grid.dimX * grid.dimY * grid.dimZ * grid.concentration*/));
		
		Point p;
		Point cen = new Point(90, 50, 50);
		
	
		
		
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				for(int k = 0 ; k < grid.dimZ; k++) {
					
					if(
							(i == 0 && j == 0)||
							(i == 0 && k == 0)||
							(j == 0 && k == 0)
						) {
						grid.setPoint(i,  j,  k);
					}
				
				}
			}
		}
		

		
		for(int i = 0; i < 100; i++) {
			
		}
		
		
		
		
		
		
		//#endregion
		
		
		return grid;
	}
	
}




