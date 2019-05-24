package application;

import java.util.ArrayList;

import Helpers.*;
import engine.*;
import innitialConditions.*;

public class Main {

	public static void main(String[] args) {
		//Logger.INSTANCE.startLog();
		Grid grid = new Grid(301, 301, 301);	
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		helper.maxCoef = 1/1.5;
		helper.minCoef = 1/2.0;
		helper.steps = 5000;
				

//		r~5a; d ~1.5a;
//		a = 2
		//IInitialConditionProvider conditionProvider = new InitialConditionProviderBase(50, 100);
		
		IDistribution normalDistr = new IDistribution() {
			public double getRandom() {
				
				double sum = 0;
				
				for(int i = 0; i < 12; i++) {
					sum += Math.random();
				}
				
				return 0.5 + sum / 24;
			}			
		};
		
		IInitialConditionProvider conditionProvider = new InitialConditionPolydisperse(40, grid, normalDistr);
		conditionProvider.setupEnvironment(grid);
		
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);

	}

}
