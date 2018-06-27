package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

import gui.Paintable;
import gui.Window;
import hexagonalGrid.HexGrid;
import hexagonalGrid.Molecula;
import physicsCore.ThreadPool;

public class HexGridHelper implements Paintable{
	public HexGrid grid;
	private String defaultPath;
	private ThreadPool pool;
	private boolean running;
	private double a = 1;

	/*for paint*/
	public int currentPaintedPlane;
	public int radius;
	public double hMod = 1.5;
	public double vMod = 1;
	
	/*Area stuff*/
	private ArrayList<Molecula> buffered;
	private CyclicBarrier barrier;
	
	private Runnable syncPart = new Runnable() {
		public void run() {
			for(Molecula mol : buffered) {
				grid.engageOne(mol);
			}
			
			buffered = (ArrayList<Molecula>) grid.moleculas.clone();

		}
	};
	
	public HexGridHelper(){
		defaultPath = "Images\\";
		pool = new ThreadPool();
		pool.setThreadCount(8);
	}
	
	public HexGridHelper(HexGrid hg){
		this();
		grid = hg;
		afterSetGrid();
	}
	
	public void afterSetGrid() {
		buffered = (ArrayList<Molecula>) grid.moleculas.clone();
		
		currentPaintedPlane = 0;
		radius = 10;

		Toolkit t = Toolkit.getDefaultToolkit();
		
		radius = grid.dimX /t.getScreenSize().width < grid.dimY /t.getScreenSize().height ? 
					grid.dimX / t.getScreenSize().width :
					grid.dimY / t.getScreenSize().height;
		radius  = 10;			
	}
	
	public double getTemperature() {
		return grid.p0;
	}
	
	public synchronized void setTemperature(double d) {
		grid.p0 = d;
		grid.p1 = 0.4;
		grid.p1 = Math.pow(grid.p1, grid.p0);
	}

	public String GetDefaultPath() {
		return defaultPath;
	}
	
	public void setdefaultPath(String path) {
		defaultPath = path;
	}
	
	public void loadGrid(Molecula[][][] volume) {
		grid = new HexGrid(volume.length, volume[0].length, volume[0][0].length);
		grid.volume = volume;
		afterSetGrid();
	}
	
	public void loadGrid(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path));
		grid = (HexGrid) stream.readObject();
		stream.close();
		afterSetGrid();
	}
	
	public void saveGrid(String path) throws FileNotFoundException, IOException {
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path));
		stream.writeObject(grid);
		stream.flush();
		stream.close();
	}
	
	public synchronized void saveForUser(String path){
		File file = new File(path + "UserReview.txt");
		try {
			file.createNewFile();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.err.println("File cannot be created");
		}
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(path + "UserReview.txt"));){
			
			for(int k = 0; k < grid.dimZ; k++) {
				for(int j = 0; j < grid.dimY; j++) {
					for(int i = 0; i < grid.dimX; i++) {
						writer.write( grid.getOne(i, j, k) );
					}
					writer.newLine();
				}
				for(int n = 0; n < grid.dimZ; n++) {writer.write("===");}
				writer.newLine();
			}
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("File cannot be writed in");
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void saveForVMD(String path) throws IOException{
		File file = new File(path + "VMDReview.pdb");

		file.createNewFile();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
		for(int k = 0; k < grid.dimZ; k++) {
			for(int j = 0; j < grid.dimY; j++) {
				for(int i = 0; i < grid.dimX; i++) {
					if(grid.volume[i][j][k] != null) {
						writer.write( "ATOM    100  N   VAL A  25     " + (int) (100 + i * a) + ".000 " + (int) (100 + j * a) + ".000 " + (int) (100 + k * a) + ".000  1.00 12.00      A1   C   ");
						writer.newLine();
					}
				}
			}
		}
		//writer.newLine();
		writer.flush();
	}
	
	public synchronized void printXY(int n) {
		grid.HorsPlaneXY(n);
	}
	
	public void paint(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				if(grid.volume[i][j][currentPaintedPlane] != null) {
					if(grid.isCross(i, j, currentPaintedPlane)) {
						g2.fillOval( (int) (hMod * radius) * i, (int) (vMod * radius) * j, radius, radius);
					}
					else {
						g2.fillOval( (int) (hMod * radius) * i  + radius/2, (int) (vMod * radius) * j, radius, radius);
					}
				}				
			}
		}
	}
	
	public synchronized void cycledMethod() {
		grid.cycledMethod();
	}

	public void beginFinalization(){
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Finalization failed!");
		}
	}
	
	public void runOneThread() {
		running = true;
		pool.add(new Runnable() {
			public void run() {
				while(running) {
					cycledMethod();
				}
			}
		});
	}
	
	public boolean IsRunning() {
		return running;
	}

	public void startManyL(){
		running = true;
		barrier = new CyclicBarrier(8, syncPart);
		Area a = new Area(0, 0, 0, grid.dimX, grid.dimY, grid.dimZ);
		//startArea(a);
		for(Area ar: Area.divideInTwo(0, 3, a)) {
			startAreaL(ar);
		}
		
		for(Area ar: a.divideOnSome(8)) {
			startAreaL(ar);
		}
		
	}
	
	public void startAreaL(Area a) {
		pool.add(new Runnable() {
			public void run() {
				while(running) {
					ArrayList<Molecula> moleculaList = getMoleculasInArea(a);
					for(Molecula m : moleculaList) {
						grid.engageOne(m);
					}
					
					try {
						barrier.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		});
		
	}
	
	public ArrayList<Molecula> getMoleculasInArea(Area a){
		ArrayList<Molecula> mol = new ArrayList<Molecula>();
		
		for(int i = a.bx; i < a.bx + a.dx; i++ ) {
			for(int j = a.by; j < a.by + a.dy; j++ ) {
				for(int k = a.bz; k < a.bz + a.dz; k++ ) {
					if(grid.volume[i][j][k] != null) {
						mol.add(grid.volume[i][j][k]);
						synchronized(buffered) {
							buffered.remove(grid.volume[i][j][k]);
						}
					}
				}
			}
		}
		
		return(grid.Shuffle(mol));
	}
	
	public void startMany() {
		
		running = true;
		//barrier = new CyclicBarrier(8, syncPart);
		Area a = new Area(0, 0, 0, grid.dimX, grid.dimY, grid.dimZ);
		LinkedList<Molecula> list = moleculasInArea(a);
		ArrayList<Molecula> al = new ArrayList(moleculasInArea(a));
		//printMoleculas(list);
		
		
		try {
			long sTime = System.nanoTime();	
			//System.out.println(list.size());
			al = grid.Shuffle(al);
			Logger.instance.write(Long.toString(System.nanoTime() - sTime) + "aList");
			
			//printMoleculas(al);
			System.out.println(al.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			long sTime = System.nanoTime();	
			//System.out.println(list.size());
			list = grid.Shuffle(list);
			Logger.instance.write(Long.toString(System.nanoTime() - sTime) + "List");
			//printMoleculas(list);
			System.out.println(list.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}
	
	public LinkedList<Molecula> moleculasInArea(Area a){
		LinkedList<Molecula> list = new LinkedList<Molecula>(grid.moleculas);
		return list;
	}
	
	public void printMoleculas(LinkedList<Molecula> list) {
		Iterator it = list.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
		
	}
	
	
	
	
	public void pauseCalculations() {
		running = false;
	}
	/*management section
	 * divide
	 * go
	 * wait all
	 * jump rests another Runnable
	 * unite
	 * */
	
	
	/*end of management section*/
	//tested

}
