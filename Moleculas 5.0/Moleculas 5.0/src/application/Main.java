package application;

import java.util.ArrayList;

import Helpers.Point;
import engine.*;

public class Main {

	public static void main(String[] args) {
		//Logger.INSTANCE.startLog();
		Grid grid = new Grid(60, 60, 60);	
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		
//		r~5a; d ~1.5a;
//		a = 2
		InitialConditionProvider conditionProvider = new InitialConditionProvider(8, 12);
		conditionProvider.setupEnvironment(grid);
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);

	}

}
