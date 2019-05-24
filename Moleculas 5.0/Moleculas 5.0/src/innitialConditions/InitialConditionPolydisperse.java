package innitialConditions;

import java.util.ArrayList;

import Helpers.*;
import application.Logger;
import engine.Grid;

public class InitialConditionPolydisperse implements IInitialConditionProvider{
	
	protected IDistribution random;
	protected Grid grid;
	protected ArrayList<Sphere> structure;
	
	protected StructuralCell[][][] volumeMap;
	protected double averageDiameter; 
	
	public InitialConditionPolydisperse(double averageEffectiveDiameter, Grid grid, IDistribution distribution) {
		
		this.random = distribution;
		this.grid = grid;
		this.structure = new ArrayList<Sphere>();
		this.averageDiameter = averageEffectiveDiameter;
		
		prepareStructure();
	}
	
	public void setupEnvironment(Grid grid) {
		Logger.log.println("Setting up environment...");
		
		for(Sphere s: structure) {
			
			for(int i = (int)(-s.radius); i <= s.radius; i++) {
				for(int j = (int)(-s.radius); j <= s.radius; j++) {
					for(int k = (int)(-s.radius); k <= s.radius; k++) {
						if( i*i + j*j + k*k < s.radius*s.radius ) {
							grid.setPoint((int)(s.center.x + i), (int)(s.center.y + j), (int)(s.center.z + k));
						}
					}
				}
			}
		}
		
		Logger.log.println("Environment was set up");
	}
	
	private void prepareStructure() {
		Logger.log.println("Preparing structure...");
		
		volumeMap = new StructuralCell
				[(int)(grid.dimX / averageDiameter)]
				[(int)(grid.dimY / averageDiameter)]
				[(int)(grid.dimZ / averageDiameter)];
		
		
		//structure.add(new Sphere(100, 100, 100, 20));
		
		
		
		
		
		
		
		
		
		Logger.log.println("Structure prepared");
	}
	
	
	
	
	
	private void addSphere(double x, double y, double z, double radius) {
		
		int a = (int)(x / averageDiameter), 
				b = (int)(y / averageDiameter), 
				c = (int)(z / averageDiameter);
		
		Sphere s = new Sphere(x, y, z, radius);
		structure.add(s);
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				for(int k = -1; k <= 1; k++) {
					if(!volumeMap[a + i][b + j][c + k].relatives.contains(s)
							&& isSphereValid(s)) {
						volumeMap[a + i][b + j][c + k].relatives.add(s);
					}
				}
			}
		}
		
	}
	
	private boolean isSphereValid(Sphere s) { 
		return grid.isValid((int)(s.center.x + s.radius), (int)(s.center.x + s.radius), (int)(s.center.x + s.radius)) 
				&&grid.isValid((int)(s.center.x - s.radius), (int)(s.center.x - s.radius), (int)(s.center.x - s.radius));
	}
	
	protected class StructuralCell{
		public ArrayList<Sphere> relatives;
		
		public StructuralCell(){
			relatives = new ArrayList<Sphere>();
		}
	}
	
}
