package gui;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Window win = new Window();
		ConcretePaintable p = new ConcretePaintable();
		win.addPaintable(p);
		
		(new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					win.refreshPanel();
					try {
						Thread.currentThread().join(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		})).start();
		
//		for(int i = 0; i < 1000; i++) {
//			p.x += 1;
//			p.y += 1;
//			p.r += 1;
//		
//			try {
//				Thread.currentThread().join(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//		}
		
//		Area ar = new Area(0, 0, 0, 256, 256 ,256);
//		
//		ArrayList<Area> areas = Area.divideInTwo(0, 3, ar);
//		
//		for(Area a : areas) {
//			win.addPainted(a);
//		}
//		
//		
//		
		
		
		
		

	}

}
