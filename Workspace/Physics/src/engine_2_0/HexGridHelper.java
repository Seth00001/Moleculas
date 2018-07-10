package engine_2_0;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import gui.Paintable;

public class HexGridHelper implements Paintable{
	public HexGrid grid;
	public int currentlyPaintedPlane;
	public double r;
	public boolean running;
	public int ax, ay, az, a2, a3, ox, oy, oz;
	
	double a = 1;
	double 
		kx = a * 3 * Math.sqrt(3) * 0.25,
		ky = a * 3 * 0.25,
		kz = a,
		dx = a * Math.sqrt(3) * 0.25,
		dy = 0,
		dz = a * 0.5;
	
	public long step;
	
//	public double concentration = 0.00129688;
	public int stepCount = 16000;

	public HexGridHelper() {
		currentlyPaintedPlane = 0;
		r = 10;
	}
	
	public HexGridHelper(HexGrid h) {
		this();
		grid = h;
		running = false;
		afterSetGrid();
	}
	
	public void afterSetGrid() {
		ax = 10;
		ay = 6;
		az = 10;
		
		a2 = 3;
		
//		DPoint p = grid.toSpaceCoordinates(grid.dimX, grid.dimY, grid.dimZ);
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		grid.setP0(5);

		step = 0;
	}
	
	public void saveGrid() {
		synchronized (grid){
			try {
				File f = new File("Backups\\" + step + " Backup ");
				f.createNewFile();
				FileOutputStream stream = new FileOutputStream(f);
				ObjectOutputStream writer = new ObjectOutputStream(stream);
				writer.writeObject(grid);
				writer.flush();
				writer.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		

	}
	
	public void loadGrid(String path) {
		synchronized(this) {
			try {
				File f = new File(path);
				FileInputStream stream = new FileInputStream(f);
				ObjectInputStream reader = new ObjectInputStream(stream);
				grid = (HexGrid) reader.readObject();
				reader.close();
				afterSetGrid();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Loading Completed!");
		}
		
	}
	
	public void runOneRawThread() {
		running = true;
		new Thread(new Runnable() {
			public void run() {
				int dif = 0;
				
				int volume = grid.getVacansys();
				
 				while(running) {
 					
 					dif = (int) ((volume * grid.concentration) - grid.calculateAtoms());
 					grid.populateTopLevels(dif);
 					System.out.println( grid.coordinates.size() + "  |  " +  dif);
 						 					
 					for(int j = 0; j < stepCount; j++) {
 						synchronized(grid)
 	 					{
 	 						for(int i = 0; i < grid.coordinates.size(); i++) {
 								grid.jump(grid.coordinates.get(i), i);
 							}
 							step++;
 	 					}
 					}

 				}
			}
		}).start();
	}
	
	public void runMany() {
		running = true;
		
		/*
		 * 
		 * 
		 * 
		 * */
	}
	
	public void pauseCalculations() {
		running = false;
	}
	
	public void saveForUser(String path) {
		
		
		
		
		
		
	}
	
	public void saveForVMD(String path) throws IOException{
		File file = new File("Prints\\" + path + "VMDReview.pdb");

		file.createNewFile();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
		
		double x, y, z;
			
		synchronized(grid)
		{
			for(int k = 0; k < grid.dimZ; k++) {
				for(int j = 0; j < grid.dimY; j++) {
					for(int i = 0; i < grid.dimX; i++) {
						if(grid.volume[i][j][k]/* && grid.getNeirbourgsCount(i, j, k) > 0*/) {
						
							
							
//							System.out.println(i + " | " + j + " | " + k);
							
//							x =  ax * i;
//							y =  ay * j;
//							z =  az * k;
							
//							x =  kx * i;
//							y =  ky * j;
//							z =  kz * k;
//							
//							if(grid.isCross(i, j, k)) {
//								x += dx;
//								y += dy;
//								z += dz;
//							}
				
							DPoint p = grid.toSpaceCoordinates(i,  j,  k);
							x = p.x;
							y = p.y;
							z = p.z;
							
							/*
							if(k % 2 == 0) {
								
								if(grid.isCross(i, j, k)) {
									x = x - a2;
								}
							}
							else {
								if(!grid.isCross(i, j, k)) {
									x = x - a2;
								}
							}
							
							if(!grid.isCross(i, j, k)) {
								z = z - a2; 
							}
	*/
							if(grid.isCross(i, j, k) ) {
								writer.write( "ATOM    100  N   VAL A  25     " + (form(x)) + " " + (form(y)) + " " + (form(z)) + "  1.00 12.00      A1   C   ");
							}
							else {
								writer.write( "ATOM    100  B   VAL A  25     " + (form(x)) + " " + (form(y)) + " " + (form(z)) + "  1.00 12.00      A1   C   ");
							}
	
							writer.newLine();
						}
					}
				}
			}
		}
		
		writer.flush();
	}
	
	public void saveAsDatFile(String s) throws Exception{
		synchronized(grid) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Dats\\" + s + ".dat")));
			
			for(Point p : grid.coordinates) {
				DPoint point = grid.toSpaceCoordinates(p.x, p.y, p.z);
				
				writer.write(point.x + " " + point.y + " " + point.z);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		}
	}
	
	public String form(double number) {
		NumberFormat f = new DecimalFormat("###.###");
		
		String s = Double.toString(((int)(number * 1000) * 0.001));
		
		if(s.indexOf(".") < 0) {
			s = s + ".";
		}
		
		while(s.indexOf(".") < 3) {
			s = "0" + s;
		}
		
		while(s.length() < 7) {
			s += "0";
		}
		
		return s.substring(0, 7);
	}

	public void setTemperature(double d) {
		grid.setP0(d);
	}
	
	public double getTemperature() {
		return grid.p0;
	}
	
	@Override
	public void paint(Graphics2D g) {
		
		//#region z-plane
		
		DPoint p;
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		p = grid.toSpaceCoordinates(grid.dimX, grid.dimY, grid.dimZ);
		
		double cx, cy;
		cx = (kit.getScreenSize().getWidth() - 10) / p.x;
		cy = (kit.getScreenSize().getHeight() - 90) / p.y;
		
		if(cx > cy) {
			cx = cy;
		}
		
		int offsetX = (int) ( p.x / grid.dimY * cx / 2);
		if(offsetX < 1) {
			offsetX = 1;
		}
		
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, (int)(p.x * cx), (int)(p.y * cx));
		
		for(int i = 0; i < 1; i++) {
			for(int j = 0; j < 3; j++) {
				if(grid.volume[i][j][currentlyPaintedPlane]) {
					g.setColor(Color.BLUE);
				}
				else {
					continue;
//					g.setColor(Color.LIGHT_GRAY);
				}
				p = grid.toSpaceCoordinates(i, j, currentlyPaintedPlane);
				g.fillRect((int)(p.x * cx + offsetX), (int)(p.y * cx), offsetX, offsetX);
				
			}
		}
		
		//#endregion
		
		//#region y-plane
		
//		Point p;
//		Toolkit kit = Toolkit.getDefaultToolkit();
//		
//		p = grid.toSpaceCoordinates(grid.dimX, grid.dimY, grid.dimZ);
//		
//		double cx, cy;
//		cx = (kit.getScreenSize().getWidth() - 10) / p.x;
//		cy = (kit.getScreenSize().getHeight() - 90) / p.y;
//		
//		if(cx > cy) {
//			cx = cy;
//		}
//		
//		int offsetX = (int) ( p.x / grid.dimY * cx / 2);
//		
//		g.setColor(Color.BLUE);
//		g.drawRect(0, 0, (int)(p.x * cx), (int)(p.y * cx));
//		
//		for(int i = 0; i < grid.dimX; i++) {
//			for(int j = 0; j < grid.dimZ; j++) {
//				if(grid.volume[i][currentlyPaintedPlane][j]) {
//					g.setColor(Color.BLUE);
//				}
//				else {
//					continue;
////					g.setColor(Color.LIGHT_GRAY);
//				}
//				p = grid.toSpaceCoordinates(i, currentlyPaintedPlane, j);
//				g.fillRect((int)(p.x * cx + offsetX), (int)(p.z * cx), offsetX, offsetX);
//				
//			}
//		}
		
		//#endregion

		//#region x-plane
//		Point p;
//		Toolkit kit = Toolkit.getDefaultToolkit();
//		
//		p = grid.toSpaceCoordinates(grid.dimX, grid.dimY, grid.dimZ);
//		Point t;
//		double cx, cy, cz, c;
//		
//		cy = ((kit.getScreenSize().getHeight() - 10) / p.y);
//		cz = ((kit.getScreenSize().getWidth() - 90) / p.z);
//		
//		
//		if(cz > cy) {
//			c = cy;
//		}
//		else {
//			c = cz;
//		}
//
//		g.setColor(Color.BLUE);
//		g.drawRect(0, 0, (int)(p.z * cz), (int)(p.y * cy));
//		
//	
//		for(int j = 0; j < grid.dimY; j++) {
//			for(int k = 0; k < grid.dimZ; k++) {
//				if(grid.volume[currentlyPaintedPlane][j][k]) {
//					g.setColor(Color.BLUE);
//					t = grid.toSpaceCoordinates(currentlyPaintedPlane, j, k);
//					g.fillRect((int) (t.z * c), (int) (t.y * c), (int) (3), (int) (3));
//				}
//
//			}
//		}
		
		//#endregion

		//#region xz - plane
		
	
		
		
		
		
		//#endregion
		
		
	}
		
	public void beginFinalization() {
		running = false;
	}
	
	public void getGridFromDatFile(String path) {
		File f = new File(path);
		
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(f));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public Point parseCoordString(String s) {
		Point p = new Point();
		
		
		
		
		
		return(p);
	}

}
