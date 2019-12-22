package application;

import Helpers.Point;
import engine.*;
import initialConditions.SphereProvider;

public class Main {

	public static void main(String[] args) {
		
		Grid grid = new Grid(100, 100, 100);
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		
		
		SphereProvider provider = new SphereProvider();
		provider.radius = 20;
		
		
		
		
		provider.SetUpd(grid);
		
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);
		
	}

}
