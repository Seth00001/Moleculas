package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import Helpers.Point;
import logging.Logger;
import logging.Writable;

public class GridHelper implements IPaintable{

	public Grid grid;
	public boolean calculationRunning;  
	public boolean snapshotsCreating;
	
	//#region paint settings
	
	public int currentlyPaintedPlane, halfSize = 2;
	
	//#endregion
	
	public GridHelper() {
		calculationRunning = false;
		currentlyPaintedPlane = 0;
	}
	
	
	public void exportForVMD() throws IOException {
		Writable snapshot = new Writable() {

			private String name = "fsds.pdb";
			
			@Override
			public void write(BufferedWriter writer) throws Exception {
				try {
					exportForVMD(writer);
				}
				catch(Exception ex) {
					Logger.log.println(String.format("%s", ex.getStackTrace()));
				}
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public void setName(String name) {
				this.name = name;
			}
			
		};
		
		Logger.log.logDebugSnapshot(snapshot);
	}
	
	public void exportForVMD(BufferedWriter writer) throws IOException {
		synchronized(grid.grid) {
			for(Point p : grid.queue) {
				
				if(/*grid.getNeirbourghsCount(p.x, p.y, p.z) > 9)*/grid.grid[p.x][p.y] [p.z] > 9) 
				{
					writer.write("ATOM    100  N   VAL A  25     " + (form(10*p.x)) + " " + (form(10*p.y)) + " " + (form(10*p.z)) + "  1.00 12.00      A1   C   " + System.lineSeparator());
				}
				else
				{
					//writer.write("ATOM    100  B   VAL A  25     " + (form(10*p.x)) + " " + (form(10*p.y)) + " " + (form(10*p.z)) + "  1.00 12.00      A1   C   " + System.lineSeparator());
				}
			}
			
		}
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
				
				double size = grid.queue.size();
				double step = 0;

				double probab = 0;
				
				Writable snapshot = new Writable() {

					private String name;
					
					@Override
					public void write(BufferedWriter writer) throws Exception {
						try {
							exportForVMD(writer);
						}
						catch(Exception ex) {
							Logger.log.println(String.format("%s", ex.getStackTrace()));
						}
					}

					@Override
					public String getName() {
						return name;
					}

					@Override
					public void setName(String name) {
						this.name = name;
					}
					
				};
				
				while(calculationRunning) {
				
					Logger.log.println(String.format("alpha: %s | p0: %s", grid.alpha, grid.p0));
					
					Logger.log.println(String.format("Step: %s", step));
					snapshot.setName(String.format("%s.pdb", step));
					Logger.log.logSnapshot(snapshot);
					
//					probab = grid.probab();
//					
//					Logger.log.println(Double.toString(probab));
//					
//					grid.refillTopRegion(probab);
					
					for(int k = 0; k < 1000; k++) 
					{
						for(int i = 0; i < grid.queue.size(); i++) {
							synchronized(grid.grid) {	
								grid.jump(grid.queue.get((grid.random.nextInt(grid.queue.size()))));
							}
						}
						
					}
						
					step += 1000;

				}	
			}
		};
		t.start();
	}
	
	public void stopCalculation(){
		calculationRunning = false;
	}
	
	public void beginExporting() {
		snapshotsCreating = true;
	}
	
	public void endExporting() {
		snapshotsCreating = false;
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
		
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				
				if(grid.grid[i][j][currentlyPaintedPlane] != 0) {
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
