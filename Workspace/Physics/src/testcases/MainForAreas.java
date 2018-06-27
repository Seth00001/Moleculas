package testcases;

import java.awt.Color;

import engine.Area;
import gui.Window;

public class MainForAreas {
	public static void main(String[] args) {
		Area a = new Area(0, 0, 0, 18, 20, 20);
		
		Window win = new Window();
		
		a.size = 2;
		a.color = Color.RED;
		win.addPaintable(a);
		
		
//		for(Area ar: Area.divideInTwo(0, 10, a)) {
//			ar.size = 2;
//			win.addPaintable(ar);
//		}
		
		for(Area ar: a.divideOnSome(1024)) {
			ar.size = 10;
			win.addPaintable(ar);
		}
		
		
		win.refreshPanel();
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
