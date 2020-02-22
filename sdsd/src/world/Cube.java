package world;

import java.util.HashMap;

import math.D3CoordinateMatrix;

public class Cube extends Mesh{
	
			
	
	
	public Cube(Double x, Double y, Double z, Double xRange, Double yRange, Double zRange) {
		super(x, y, z, cordsFactory(xRange, yRange, zRange));
		
		
		D3CoordinateMatrix temp[] = new D3CoordinateMatrix[2];
		
	/*	
		temp[0] = innerCords.get(0);
		temp[1] = innerCords.get(1);
		edges.add(temp);
		temp[1] = innerCords.get(2);
		edges.add(temp);
		temp[1] = innerCords.get(3);
		edges.add(temp);
		
		temp[0] = innerCords.get(7);
		temp[1] = innerCords.get(6);
		edges.add(temp);
		temp[1] = innerCords.get(5);
		edges.add(temp);
		temp[1] =  innerCords.get(4);
		edges.add(temp);
		*/
		
	}
		
	
	
	
	
	
	
	
	private static HashMap<Integer, D3CoordinateMatrix> cordsFactory(Double xRange, Double yRange, Double zRange){
		
		HashMap<Integer, D3CoordinateMatrix> cords = new HashMap<Integer, D3CoordinateMatrix>();
		
		cords.put(1, new D3CoordinateMatrix(xRange, yRange, zRange));
		cords.put(2, new D3CoordinateMatrix(-xRange, yRange, zRange));
		cords.put(3, new D3CoordinateMatrix(xRange, -yRange, zRange));
		cords.put(4, new D3CoordinateMatrix(xRange, yRange, -zRange));
		cords.put(5, new D3CoordinateMatrix(-xRange, -yRange, zRange));
		cords.put(6, new D3CoordinateMatrix(-xRange, yRange, -zRange));
		cords.put(7, new D3CoordinateMatrix(xRange, -yRange, -zRange));
		cords.put(8, new D3CoordinateMatrix(-xRange, -yRange, -zRange));
		
		return cords;
		
	}
	
	
}
