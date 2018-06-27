package testcases;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.HexGridHelper;
import gui.Paintable;
import gui.Window;
import hexagonalGrid.HexGrid;

public class MainForHexGrid {
	static int currentPaintedPlane = 0;
	static int hMod = 1;
	static int vMod = 1;
	static int radius = 10;

	public static void main(String[] args) {
		HexGrid grid = fillGrid();
		HexGridHelper h = new HexGridHelper(grid);
		h.saveForUser("");
		
		
		Window win = new Window();

		
		win.addPaintable(h);
		win.waitTime = 50;
		win.beginUpdating();
		
		
		
		
	}
	
	public static HexGrid fillGrid() {
		HexGrid h = new HexGrid(35, 35, 1);
	
		h.p0 = 5;
		h.p1 = 0.4;
		h.p1 = Math.pow(h.p1, h.p0);
		
		int c = 10;
		for(int i = c; i < h.dimX - c; i++) {
			for(int j = c; j < h.dimY - c; j++) {
				for(int k = 0; k < h.dimZ - 1; k++) {
					h.placeOne(i, j, k);
				}
			}
		}
		
		return h;
	}
	
	public static void Paint(Graphics2D g2, HexGrid grid) {
		int hMod = 1;
		int vMod = 1;
		int radius = 10;
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
}
