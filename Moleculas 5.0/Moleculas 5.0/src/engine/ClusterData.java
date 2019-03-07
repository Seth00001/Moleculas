package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Helpers.Point;

public class ClusterData {
	private ArrayList<Point> incapsulatedMoleculas;
	
	public ClusterData()
	{
		incapsulatedMoleculas = new ArrayList<Point>();
	}
	
	public ClusterData(ArrayList<Point> particles)
	{
		incapsulatedMoleculas = particles;
	}
	
	public int size() {
		return incapsulatedMoleculas.size();
	}
	
}
