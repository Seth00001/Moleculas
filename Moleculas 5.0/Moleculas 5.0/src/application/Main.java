package application;

import java.util.ArrayList;

import Helpers.Point;
import engine.*;
import engine.IntegrityDataCollector.Direction;
import engine.IntegrityDataCollector.LayerDataHandler;

public class Main {

	public static void main(String[] args) {
		//Logger.INSTANCE.startLog();
		Grid grid = new Grid(60, 60, 60);	
		GridHelper helper = new GridHelper();
		helper.grid = grid;
		helper.maxCoef = 1/1.5;
		helper.minCoef = 1/2.0;
		helper.steps = 5000;
		
		

		

		
		
		
		
		
//		r~5a; d ~1.5a;
//		a = 2
//		InitialConditionProvider conditionProvider = new InitialConditionProvider(8, 12);
//		conditionProvider.setupEnvironment(grid);
		
		for(int i = 0; i < grid.dimX; i++) {
			for(int j = 0; j < grid.dimY; j++) {
				grid.setPoint(i, j, 1);
			}
		}
		
		IntegrityDataCollector col = new IntegrityDataCollector(grid);
		
		ArrayList<LayerDataHandler> data = col.CollectPerLayerData();
		
		for(LayerDataHandler hwd : data) {
			
			System.out.println(String.format("Clusters contained: %s", hwd.data.size()));
			
			if(hwd.data.size() > 0) {
				for(ClusterData cluster : hwd.data) {
					System.out.println(String.format("Cluster Size: %s", cluster.size()));
				}
				System.out.println(String.format("--------------------------------------"));
			}
			
			
		}
		
		
//		LayerDataHandler layer = col.GetLayerData(1, Direction.Z);
		
//		System.out.println(col.GetLayerNeirbourghs(1, 1, 1, Direction.Z).size());
		
		
		
//		System.out.println(String.format("%s     ", grid.queue.size()));
		
//		col.GetLayerNeirbourghs(5, 5, 5, Direction.Z);
		
		
		
		MainWindow win = new MainWindow();
		win.setHelper(helper);
		win.setVisible(true);

	}

}
