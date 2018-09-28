package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Helpers.Point;

public class GridHelper implements IPaintable{

	public Grid grid;
	public boolean calculationRunning;  
	
	//#region paint settings
	
	public int currentlyPaintedPlane, halfSize = 2;
	
	//#endregion
	
	
	public GridHelper() {
		calculationRunning = false;
		currentlyPaintedPlane = 0;
	}
	
	
	public void exportForVMD() throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("fsds.pdb"));
		
		synchronized(grid.grid) {
			for(Point p : grid.queue) {
				
				if(grid.getNeirbourghsCount(p.x, p.y, p.z) > 0) {
					writer.write("ATOM    100  N   VAL A  25     " + (form(10*p.x)) + " " + (form(10*p.y)) + " " + (form(10*p.z)) + "  1.00 12.00      A1   C   " + System.lineSeparator());
				}
				
			}
		}
		
		
		writer.flush();
		writer.close();
	}
	
	public String form(int number) {
		String s = Double.toString(number / 10.0);

		while (s.indexOf(".") < 3) {
			s = "0" + s;
		}

		while (s.length() < 7) {
			s += "0";
		}

		return s;
	}

	public void startCalculation() {
		calculationRunning = true;
		Thread t = new Thread() {
			public void run() {
				grid.rearrange();
				
				while(calculationRunning) {
					synchronized(grid.grid) {	
						
						
						for(int i = 0; i < grid.queue.size(); i++) {
							grid.jump(grid.queue.get((grid.random.nextInt(grid.queue.size()))));
//							System.out.println("Hop!");
						
						}
					}
					//
				}
			}
		};
		t.start();
	}
	
	public void stopCalculation(){
		calculationRunning = false;
	}
	
	public void rearrangeGridQueue() {
		if(grid != null) {
			int x, y; //future coordinates of elements
			Point temp;
			
			for(int i = 0; i < grid.queue.size(); i++) {
				x = grid.random.nextInt(grid.queue.size());
				y = grid.random.nextInt(grid.queue.size());
				
				temp = grid.queue.get(x);
				grid.queue.set(x, grid.queue.get(y));
				grid.queue.set(x, temp);
			}
		}
	}
	
	public double getTemperature() {
		return grid.alpha;
	}
	
	public void setTemperature(double d) {
		grid.setAlpha(d);
	}


	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 10000, 10000);
		
		
		
		g.setColor(Color.BLUE);
		
//		g.fillRect(0, 0, 10, 10);
		
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				
				if(grid.grid[i][j][currentlyPaintedPlane]) {
//					g.setColor(Color.BLUE);
					g.fillRect( ( i) * halfSize, (  j) * halfSize, 2 * halfSize, 2 * halfSize);
				}
				else {
//					g.setColor(Color.GRAY);
//					g.fillRect( ( 2 *i) * halfSize + 1, ( 2* j) * halfSize + 1, 2 * halfSize - 2, 2 * halfSize - 2);
				}
				
			}
		}
		
		
	}
	
}
