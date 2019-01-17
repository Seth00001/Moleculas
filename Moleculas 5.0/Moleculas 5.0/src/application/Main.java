package application;

import Helpers.DPoint;
import Helpers.Point;
import engine.*;

public class Main {

	public static void main(String[] args) {
		
		Grid grid = new Grid(200, 200, 200);
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		
		
		InitialConditionProvider conditionProvider = new InitialConditionProvider(15, 10);
		conditionProvider.setupEnvironment(grid);
		
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);
		
	}

}
