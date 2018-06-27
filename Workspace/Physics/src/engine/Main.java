package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gui.Paintable;
import gui.Window;
import hexagonalGrid.HexGrid;
import physicsCore.ThreadPool;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		HexGridHelper helper = new HexGridHelper(fillGrid());		
		Window win = new Window();
		//win.setHelper(helper);

		System.out.println("done");		
	}
	
	public static HexGrid fillGrid() {
		HexGrid h = new HexGrid(20, 20, 20);
		
		h.p0 = 5;
		h.p1 = 0.4;
		h.p1 = Math.pow(h.p1, h.p0);
		
//		h.placeOne(5, 5, 5);
		int c = 5;
		for(int i = c; i < h.dimX - c; i++) {
			for(int j = c; j < h.dimY - c; j++) {
				for(int k = c; k < h.dimZ - c; k++) {
					h.placeOne(i, j, k);
				}
			}
		}
		
		return h;
	}
}
	
	
	