package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import math.D3CoordinateMatrix;

public class Edge {
	
	private int p1, p2;
	private Color color;
	
	
	public Edge(int p1, int p2, Color color) {
		
		this.p1 = p1;
		this.p2 = p2;
		this.color = color;
	}
	
	public Edge(int p1, int p2) {
		this(p1, p2, Color.BLACK);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Edge) {
			
			Edge e  = (Edge)obj;
			
			return (this.p1 == e.p1 && this.p2 == e.p2) || (this.p1 == e.p2 && this.p2 == e.p1);
		}
		
		
		return false;
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime*result + p1;
		result = prime*result + p2;
		
		return result;
	}
	
	public void drawEdge(Graphics2D g, Mesh mesh) {
		
		g.drawLine((int)(mesh.displayCords.get(p1).x()/1), (int)(mesh.displayCords.get(p1).y()/1), (int)(mesh.displayCords.get(p2).x()/1), (int)(mesh.displayCords.get(p2).y()/1));
		
		
	}
	
	public void drawEdge(Graphics2D g, HashMap<Integer, D3CoordinateMatrix> cords) {
		
		g.drawLine((int)(cords.get(p1).x()/1), (int)(cords.get(p1).y()/1), (int)(cords.get(p2).x()/1), (int)(cords.get(p2).y()/1));
		
	}

}
