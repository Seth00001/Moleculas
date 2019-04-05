package engine;

import java.util.ArrayList;
import java.util.LinkedList;

import Helpers.Point;

public class IntegrityDataCollector {

	private byte[][][] sample;
	private Grid grid;
	
	public IntegrityDataCollector(Grid grid) {
		this.grid = grid;
	}
	
	public ArrayList<LayerDataHandler> CollectPerLayerData() {
		
		ArrayList<LayerDataHandler> data = new ArrayList<LayerDataHandler>();
		sample = copyGrid(grid.grid);
		
		for(int i = 0; i < grid.dimX; i++) {
			data.add(GetLayerData(i, Direction.X));
		}
		
		for(int i = 0; i < grid.dimY; i++) {
			data.add(GetLayerData(i, Direction.Y));
		}
		
		for(int i = 0; i < grid.dimZ; i++) {
			data.add(GetLayerData(i, Direction.Z));
		}
		
		return data;
	}
	
	public ArrayList<LayerDataHandler> CollectPerLayerInvertedData() {
		
		ArrayList<LayerDataHandler> data = new ArrayList<LayerDataHandler>();
		sample = copyInvertedGrid(grid.grid);
		
		for(int i = 0; i < grid.dimX; i++) {
			data.add(GetLayerData(i, Direction.X));
		}
		
		for(int i = 0; i < grid.dimY; i++) {
			data.add(GetLayerData(i, Direction.Y));
		}
		
		for(int i = 0; i < grid.dimZ; i++) {
			data.add(GetLayerData(i, Direction.Z));
		}
		
		return data;
	}
	
	
	public LayerDataHandler GetLayerData(int layer, Direction normalDirection) { 
		LayerDataHandler layerData = new LayerDataHandler();
		layerData.layer = layer;
		layerData.normalDirection = normalDirection;
		
		ArrayList<Point> data;
		LinkedList<Point> queue;
		Point p;
		
		for(int i = normalDirection == Direction.X ? layer : 0; normalDirection == Direction.X ? i == layer : i < grid.dimX; i = normalDirection == Direction.X ? grid.dimX : i + 1) {
			for(int j = normalDirection == Direction.Y ? layer : 0; normalDirection == Direction.Y ? j == layer : j < grid.dimY; j = normalDirection == Direction.Y ? grid.dimY : j + 1) {
				for(int k = normalDirection == Direction.Z ? layer : 0; normalDirection == Direction.Z ? k == layer : k < grid.dimZ; k = normalDirection == Direction.Z ? grid.dimZ : k + 1) {
					if(sample[i][j][k] != 0) 
					{				
						data = new ArrayList<Point>();
						queue = new LinkedList<Point>();
						queue.add(new Point(i, j, k));
						sample[i][j][k] = 0;
						
						while(queue.size() > 0) {
							p = queue.removeFirst();
							
							queue.addAll(GetLayerNeirbourghs(p.x, p.y, p.z, normalDirection));
							
							data.add(p);
						}
						
						layerData.data.add(new ClusterData(data));
					}
				}
			}
		}
		
		return layerData;
	}
	
	public LinkedList<Point> GetLayerNeirbourghs(int i, int j, int k, Direction normalDirection) { 
		LinkedList<Point> points = new LinkedList<Point>();
		
		for(int x = normalDirection == Direction.X ? 0 : -1; normalDirection == Direction.X ? x == 0 : x <= 1; x++) {
			for(int y = normalDirection == Direction.Y ? 0 : -1; normalDirection == Direction.Y ? y == 0 : y <= 1; y++) {
				for(int z = normalDirection == Direction.Z ? 0 : -1; normalDirection == Direction.Z ? z == 0 : z <= 1; z++) {
					
					if( !(x==0 && y == 0 && z == 0) 
						&& isValid(i + x, j + y, z + k) 
						&& sample[i + x][j + y][z + k] != 0)
					{
						points.add(new Point(i + x, j + y, z + k));
						sample[i + x][j + y][z + k] = 0;
					}
				}
			}
		}
		
		return points;
	}
	
	public boolean isValid(int i, int j, int k) {
		return grid.isValid(i, j, k);
	}
	
	public ArrayList<ClusterData> CollectInvertedClusterData() {
		ArrayList<ClusterData> data = new ArrayList<ClusterData>();
		/*
		synchronized(grid)
		{
			
			LinkedList<Point> particles = new LinkedList<Point>(this.queue);
			byte[][][] volume = new byte[dimX][dimY][dimZ];
			
			for(int i = 0; i < dimX; i++) {
				for(int j = 0; j < dimY; j++) {
					for(int k = 0; k < dimZ; k++) {
						if(isValid(i, j, k)) {
							volume[i][j][k] = 1;
						}
					}
				}
			}

			//normalizing the array
			for(Point p : particles) 
			{
				volume[p.x][p.y][p.z] = 0;
			}

			particles = new LinkedList<Point>();
			for(int i = 0; i < dimX; i++) {
				for(int j = 0; j < dimY; j++) {
					for(int k = 0; k < dimZ; k++) {
						if(volume[i][j][k] == 1) {
							particles.add(new Point(i, j, k));
						}
					}
				}
			}

			Point p;
			
			while(true)
			{
				//collect data for 1 cluster
				LinkedList<Point> q = new LinkedList<Point>();
				
				for(Point pp : particles) {
					if(volume[pp.x][pp.y][pp.z] == 1) {
						
						q.add(pp);
						volume[pp.x][pp.y][pp.z] = 10;
						
						break;
					}
				}
			
				if(q.size() == 0) break;
				
				for(int n = 0; n < q.size(); n++) 
				{
					p = q.get(n);
					
					if(isValid(p.x, p.y, p.z)) 
					{
						for(int i = -1; i <= 1; i++ ) {
							for(int j = -1; j <= 1; j++ ) {
								for(int  k = -1; k <= 1; k++) {
									if(isValid(p.x + i, p.y + j, p.z + k)
											&& volume[p.x + i][p.y + j][p.z + k] == 1) 
									{
										Point p1 = new Point(p.x + i, p.y + j, p.z + k);
										
										q.add(p1);
										
										volume[p1.x][p1.y][p1.z] = 10;
									}
								}
							}
						}		
					}	
				}
				data.add(new ClusterData(new ArrayList<Point>(q)));
			}
		}
		*/
		return data;
	}

	public byte[][][] copyGrid(byte[][][] g)
	{
		byte[][][] res = new byte[grid.dimX][grid.dimY][grid.dimZ];
		
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				for(int k = 0; k < grid.dimZ; k++) {
					if(g[i][j][k] != 0) {
						res[i][j][k] = 1;
					}
				}
			}
		}
		
		return res;
	}
	
	public byte[][][] copyInvertedGrid(byte[][][] g)
	{
		byte[][][] res = new byte[grid.dimX][grid.dimY][grid.dimZ];
		
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				for(int k = 0; k < grid.dimZ; k++) {
					if(g[i][j][k] == 0) {
						res[i][j][k] = 1;
					}
				}
			}
		}
		
		return res;
	}
	
	
	
	
	
	
	
	public enum Direction{
		X,
		Y,
		Z;
	}
	
	public class LayerDataHandler{
		public int layer;
		public Direction normalDirection;
		public ArrayList<ClusterData> data;
		
		public LayerDataHandler() {
			layer = 0;
			normalDirection = Direction.X;
			data = new ArrayList<ClusterData>();
		}
	}

}
