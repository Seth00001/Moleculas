package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import Helpers.Point;
import application.Logger;
import application.Writable;
import engine.IntegrityDataCollector.Direction;
import engine.IntegrityDataCollector.LayerDataHandler;

public class GridHelper implements IPaintable{

	public Grid grid;
	public boolean calculationRunning;  
	
	//#region paint settings
	
	public int currentlyPaintedPlane, halfSize = 2;
	public double minCoef = 0.4, maxCoef = 1; // minimum value of alpha in temperature changing process
	public int steps = 5000;
	//#endregion
	
	
	public GridHelper() {
		calculationRunning = false;
		currentlyPaintedPlane = 0;
	}
	
	
	public void exportForVMD() throws IOException {
		Logger.log.logDebugSnapshot(new Writable() {
			
			private String name;
			
			@Override
			public void write(BufferedWriter writer) throws IOException {
				exportForVMD(writer);
			}
			
			@Override
			public String getName() {
				return "Debug.pdb";
			}

			@Override
			public void setName(String name) {
				this.name = name; 
			}
		});
	}
	
	public void exportForVMD(BufferedWriter writer) throws IOException {
		synchronized(grid.grid) {
						
			for(Point p : grid.queue) {
				{
					writer.write("ATOM    100  N   VAL A  25     " + (form(10*p.x)) + " " + (form(10*p.y)) + " " + (form(10*p.z)) + "  1.00 12.00      A1   C   " + System.lineSeparator());
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
				Integer step = 0;
				
				Writable snapshot = new Writable() {
					
					private String name;
					
					@Override
					public void write(BufferedWriter writer) throws Exception {
						exportForVMD(writer);
					}
					
					@Override
					public String getName() {
						return name;
					}
					
					@Override
					public void setName(String name) {
						this.name =  name;
					}
				};
				
				Writable voidDataX = new Writable() {
					
					private String name;
					
					@Override
					public void write(BufferedWriter writer) throws Exception {
						
						IntegrityDataCollector collector = new IntegrityDataCollector(grid);
						ArrayList<LayerDataHandler> data = collector.CollectPerLayerInvertedData(Direction.X);
						for(int i = 0; i < data.size(); i++) {
	
							StringBuilder builder = new StringBuilder();
							
							for(int j = 0; j < data.get(i).data.size(); j++) {
								
								builder.append(String.format("%s, ",data.get(i).data.get(j).size()));
							}
							
							writer.write(builder.substring(0,  builder.length() - 2) + "\r\n");
						}
												
					}
					
					@Override
					public String getName() {
						return name;
					}
					
					@Override
					public void setName(String name) {
						this.name =  name;
					}
				};

				Writable voidDataY = new Writable() {
					
					private String name;
					
					@Override
					public void write(BufferedWriter writer) throws Exception {
						
						IntegrityDataCollector collector = new IntegrityDataCollector(grid);
						ArrayList<LayerDataHandler> data = collector.CollectPerLayerInvertedData(Direction.Y);
						for(int i = 0; i < data.size(); i++) {
	
							StringBuilder builder = new StringBuilder();
							
							for(int j = 0; j < data.get(i).data.size(); j++) {
								
								builder.append(String.format("%s, ",data.get(i).data.get(j).size()));
							}
							
							writer.write(builder.substring(0,  builder.length() - 2) + "\r\n");
						}
												
					}
					
					@Override
					public String getName() {
						return name;
					}
					
					@Override
					public void setName(String name) {
						this.name =  name;
					}
				};

				Writable voidDataZ = new Writable() {
					
					private String name;
					
					@Override
					public void write(BufferedWriter writer) throws Exception {
						
						IntegrityDataCollector collector = new IntegrityDataCollector(grid);
						ArrayList<LayerDataHandler> data = collector.CollectPerLayerInvertedData(Direction.Z);
						for(int i = 0; i < data.size(); i++) {
	
							StringBuilder builder = new StringBuilder();
							
							for(int j = 0; j < data.get(i).data.size(); j++) {
								
								builder.append(String.format("%s, ",data.get(i).data.get(j).size()));
							}
							
							writer.write(builder.substring(0,  builder.length() - 2) + "\r\n");
						}
												
					}
					
					@Override
					public String getName() {
						return name;
					}
					
					@Override
					public void setName(String name) {
						this.name =  name;
					}
				};

				
				while(calculationRunning) {
					
						if(size < grid.queue.size()) {
							size = grid.queue.size();
							Logger.log.println(String.format("Particles were added! Step: %s; CurrentCount: %s", step, grid.queue.size()));
						}
						
//						Logger.log.println(String.format("AverageLinksCount: %s", grid.getAverageLinksCount()));
						
//						grid.setAlpha(getTemperature(step));
//						Logger.log.logTemperature(step, grid.alpha);
						
//						ArrayList<ClusterData> data = grid.CollectClusterData();
//						Logger.log.logIntegrity(step, grid.alpha, data);
												
						snapshot.setName(String.format("%s.pdb", Integer.toString(step)));
						Logger.log.logSnapshot(snapshot);
						
						Logger.log.logEnergy(step, grid.getEnergyData());
						
//						voidDataX.setName("X_" + step);
//						Logger.log.logIntegritySnapshot(voidDataX);
//						
//						voidDataY.setName("Y_" + step);
//						Logger.log.logIntegritySnapshot(voidDataY);
//						
//						voidDataZ.setName("Z_" + step);
//						Logger.log.logIntegritySnapshot(voidDataZ);
						
						
						for(int j = 0; j < 100; j++) {
							for(int i = 0; i < grid.queue.size(); i++) {
								synchronized(grid.grid) {	
									grid.jump(grid.queue.get((grid.random.nextInt(grid.queue.size()))));
								}
							}
						}
						
						step += 100;
						
						//стопор процесу для ПОТОЧНОГО графіку тмператури
//						if(1/grid.alpha < minCoef) calculationRunning = false;
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

	private double getTemperature(int step) { 
		double value;
		
		if(step < steps)
		{
			value = 1 / ((maxCoef - minCoef)/steps * step + minCoef);
		}
		else {
			value = 1 / ( -0.5 * (maxCoef - minCoef)/steps * step + (3 * maxCoef - minCoef)/2);
		}

		return value;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 10000, 10000);
		
		
		
		g.setColor(Color.BLUE);

		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				
				if(grid.grid[i][j][currentlyPaintedPlane] != 0) {
//					g.setColor(Color.BLUE);
					g.fillRect( ( i) * halfSize, (  j) * halfSize, 1 * halfSize, 1 * halfSize);
//					g.fillOval( ( i) * halfSize, (  j) * halfSize, 1 * halfSize, 1 * halfSize);
				}
				else {
//					g.setColor(Color.GRAY);
//					g.fillRect( ( 2 *i) * halfSize + 1, ( 2* j) * halfSize + 1, 2 * halfSize - 2, 2 * halfSize - 2);
				}
				
			}
		}
		
		
	}
	
}
