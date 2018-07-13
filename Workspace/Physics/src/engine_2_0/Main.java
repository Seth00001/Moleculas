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
		

		HexGridHelper helper = new HexGridHelper(createGrid( 40, 40, 40));
	

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
		
		Vector x = new Vector(2, 1, -1),
				y = new Vector(0, 3, 3),
				z = new Vector(2, -2, 2);
		
		Vector x0 = new Vector(1, 0, 0),
				y0 = new Vector(0, 1, 0),
				z0 = new Vector(0, 0, 1);
				
		Vector x1 = new Vector(2, 1, -1),
				y1 = new Vector(0, 3, 3),
				z1 = new Vector(2, -2, 2);
		
		Vector x2 = new Vector(1, 0, 0),
				y2 = new Vector(0, 1, 0),
				z2 = new Vector(0, 0, 1);
		
		DPoint p = grid.toSpaceCoordinates(3, 3, 3);
		

//		x.normalize(21.2132);
//		y.normalize(21.2132);
//		z.normalize(21.2132);
		
		x.normalize(1);
		y.normalize(1);
		z.normalize(1);
		
		x1.normalize(1);
		y1.normalize(1);
		z1.normalize(1);
		
		x.add(p);
		y.add(p);
		z.add(p);
		
		x0.add(p);
		y0.add(p);
		z0.add(p);
		
		
		HexGridHelper h = new HexGridHelper();
		
		System.out.println( "ATOM    100  G   VAL A  25     " + (h.form(p.x)) + " " + (h.form(p.y)) + " " + (h.form(p.z)) + "  1.00 12.00      A1   C   ");
		
				
		System.out.println( "ATOM    100  B   VAL A  25     " + (h.form(x0.x)) + " " + (h.form(x0.y)) + " " + (h.form(x0.z)) + "  1.00 12.00      A1   C   ");
		System.out.println( "ATOM    100  B   VAL A  25     " + (h.form(y0.x)) + " " + (h.form(y0.y)) + " " + (h.form(y0.z)) + "  1.00 12.00      A1   C   ");
		System.out.println( "ATOM    100  B   VAL A  25     " + (h.form(z0.x)) + " " + (h.form(z0.y)) + " " + (h.form(z0.z)) + "  1.00 12.00      A1   C   ");
		
		x0.add(y2);
		y0.add(z2);
		z0.add(x2);
		
		System.out.println( "ATOM    100  B   VAL A  25     " + (h.form(x0.x)) + " " + (h.form(x0.y)) + " " + (h.form(x0.z)) + "  1.00 12.00      A1   C   ");
		System.out.println( "ATOM    100  B   VAL A  25     " + (h.form(y0.x)) + " " + (h.form(y0.y)) + " " + (h.form(y0.z)) + "  1.00 12.00      A1   C   ");
		System.out.println( "ATOM    100  B   VAL A  25     " + (h.form(z0.x)) + " " + (h.form(z0.y)) + " " + (h.form(z0.z)) + "  1.00 12.00      A1   C   ");
		
		x0.add(z2);
		
		System.out.println( "ATOM    100  B   VAL A  25     " + (h.form(x0.x)) + " " + (h.form(x0.y)) + " " + (h.form(x0.z)) + "  1.00 12.00      A1   C   ");
		
		
		
		
		
		System.out.println( "ATOM    100  N   VAL A  25     " + (h.form(x.x)) + " " + (h.form(x.y)) + " " + (h.form(x.z)) + "  1.00 12.00      A1   C   ");
		System.out.println( "ATOM    100  N   VAL A  25     " + (h.form(y.x)) + " " + (h.form(y.y)) + " " + (h.form(y.z)) + "  1.00 12.00      A1   C   ");
		System.out.println( "ATOM    100  N   VAL A  25     " + (h.form(z.x)) + " " + (h.form(z.y)) + " " + (h.form(z.z)) + "  1.00 12.00      A1   C   ");
		
		x.add(y1);
		y.add(z1);
		z.add(x1);
		
		System.out.println( "ATOM    100  N   VAL A  25     " + (h.form(x.x)) + " " + (h.form(x.y)) + " " + (h.form(x.z)) + "  1.00 12.00      A1   C   ");
		System.out.println( "ATOM    100  N   VAL A  25     " + (h.form(y.x)) + " " + (h.form(y.y)) + " " + (h.form(y.z)) + "  1.00 12.00      A1   C   ");
		System.out.println( "ATOM    100  N   VAL A  25     " + (h.form(z.x)) + " " + (h.form(z.y)) + " " + (h.form(z.z)) + "  1.00 12.00      A1   C   ");
		
		x.add(z1);
		
		System.out.println( "ATOM    100  N   VAL A  25     " + (h.form(x.x)) + " " + (h.form(x.y)) + " " + (h.form(x.z)) + "  1.00 12.00      A1   C   ");
		
		
		
		
		
		
		
		//#region
		
		//grid.populateAllLevels((int)(10/*grid.dimX * grid.dimY * grid.dimZ * grid.concentration*/));
		
//		DPoint p;
		Point cen = new Point(90, 50, 50);
		
	
		grid.normal = new DPoint(1.0/3, 1.0/6, -1.0/6);
		System.out.println(grid.normal.toString());
		
//		grid.setPoint(0, 0, 0);
//		grid.setPoint(0, 1, 0);
//		grid.setPoint(0, 2, 0);
//		grid.setPoint(1, 1, 0);
//		grid.setPoint(0, 1, 1);
		
		System.out.println(grid.getNeirbourgsCount(0, 1, 0));
		/*
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				for(int k = 0 ; k < grid.dimZ; k++) {
					
					p = grid.toSpaceCoordinates(i, j, k);
					
//					if(
//							(i == 0 && j == 0)||
//							(i == 0 && k == 0)||
//							(j == 0 && k == 0)
//						) {
//						grid.setPoint(i,  j,  k);
//					}
					if(grid.validate(i, j, k) && (int) ((grid.normal.x * p.x + grid.normal.y * p.y + grid.normal.z * p.z) * 3)  == 60
//							|| ( grid.validate(i, j, k) && (int)(grid.normal.x * p.x + grid.normal.y * p.y + grid.normal.z * p.z)  == 35)
							) {
						grid.setPointUnchecked(i, j, k);
					}
					
//					if(Math.random() < 0.01 && grid.validate(i, j, k)) {
//						grid.setPoint(i, j, k);
//					}
					
					
					//System.out.println(grid.normal.x * i + grid.normal.y * j + grid.normal.z * k);
				}
			}
		}
		
*/
		
		

		
		
		
		
		
		
		
		//#endregion
		
		
		return grid;
	}
	
}




