package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import gui.Paintable;

public class Area implements Paintable{
	public Color color = Color.lightGray;
	public int size = 1;
	public int bx, by, bz, dx, dy, dz; //beginings and dimensions
	
	public Area(int Bx, int By, int Bz, int Dx, int Dy, int Dz){
		bx = Bx;
		by = By;
		bz = Bz;
		dx = Dx;
		dy = Dy;
		dz = Dz;
	}
	
	public static ArrayList<Area> divideInTwo(int depth, int maxDepth, Area ar){
		ArrayList<Area> res = new ArrayList<Area>();
		if(depth < maxDepth && ( ar.dx > 15 || ar.dy > 15 || ar.dz > 15 )) {
			if(ar.dx >= ar.dy && ar.dx >= ar.dz) {
				res.addAll(divideInTwo(depth + 1, maxDepth, new Area( 
						ar.bx,
						ar.by,
						ar.bz,
						(ar.dx-3)/2,
						ar.dy, 
						ar.dz  )));
				res.addAll(divideInTwo(depth + 1, maxDepth, new Area( 
						ar.bx + (ar.dx-3)/2 + 3, 
						ar.by, 
						ar.bz, 
						ar.dx - (ar.dx-3)/2 - 3, 
						ar.dy, 
						ar.dz  )));
			}
			else if(ar.dy >= ar.dx && ar.dy >= ar.dz) {
				res.addAll(divideInTwo(depth + 1, maxDepth, new Area( 
						ar.bx,
						ar.by,
						ar.bz,
						ar.dx, 
						(ar.dy-3)/2, 
						ar.dz  )));
				res.addAll(divideInTwo(depth + 1, maxDepth, new Area( 
						ar.bx, 
						ar.by + (ar.dy - 3)/2 + 3, 
						ar.bz, 
						ar.dx, 
						ar.dy - (ar.dy-3)/2 - 3, 
						ar.dz  )));
			}
			else if(ar.dz >= ar.dy && ar.dz >= ar.dx) {
				res.addAll(divideInTwo(depth + 1, maxDepth, new Area( 
						ar.bx,
						ar.by,
						ar.bz,
						ar.dx, 
						ar.dy, 
						(ar.dz-3)/2  )));
				res.addAll(divideInTwo(depth + 1, maxDepth, new Area( 
						ar.bx, 
						ar.by, 
						ar.bz + (ar.dz - 3)/2 + 3, 
						ar.dx, 
						ar.dy, 
						ar.dz - (ar.dz-3)/2 - 3 )));
			}
		}
		else {
			res.add(ar);
		}
		
		return res;
	}
	
	public ArrayList<Area> divideOnSome(int count){
		ArrayList<Area> list = new ArrayList<Area>();
		int width = ((dx + 3) / count) - 3;
		if(width > 3 * count) {
			for(int i = 0; i < count; i++) {
				list.add(new Area( (width + 3) * i , by, bz, width, dy, dz));
			}
		}
		else {
			return divideOnSome(count - 1);
		}
		return(list);
	}
	
	@Override
	public void paint(Graphics2D g2) {
		
		g2.setColor(color);
		for(int i = bx; i < bx + dx; i++) {
			for(int j = by; j < by + dy; j++) {
				g2.fillRect(i * size, j * size, size - 1, size - 1);
			}
		}
		
		//g2.fillRect(bx * s, by * s, dx * s, dy * s);
	}

	
	
	
	
	
	
	
	
}
