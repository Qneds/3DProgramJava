package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import display.Window;
import math.D3CoordinateMatrix;
import math.DefinedMatrixs;
import math.Matrix;
import math.MatrixException;
import math.SquareMatrix;

public class Mesh {
	
	protected Color pointsColor;
	
	protected HashMap<Integer, D3CoordinateMatrix> innerCords;
	
	protected Double x, y, z, ax, by, cz, scale;
	
	protected SquareMatrix transMatrix;
	
	protected HashSet<Edge> edges;
	
	protected HashMap<Integer, D3CoordinateMatrix> displayCords;
	

	
	public Mesh(Double x, Double y, Double z, Double ax, Double by, Double cz, HashMap<Integer, D3CoordinateMatrix> innerCords) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.ax = ax;
		this.by = by;
		this.cz = cz;
		this.scale = 1.;
		if(innerCords == null) {
			this.innerCords = new HashMap<Integer,  D3CoordinateMatrix>();
			this.innerCords.put(1,  new D3CoordinateMatrix(0., 0., 0.));
		} else
			this.innerCords = innerCords;
		displayCords = new HashMap<Integer, D3CoordinateMatrix>();
		pointsColor = Color.BLACK;
		edges = new HashSet<Edge>();
		
		try {
			transMatrix = DefinedMatrixs.TransformWithoutScaleMatrix(DefinedMatrixs.Translation(x, y, z), DefinedMatrixs.RotationFast(Math.toRadians(ax), Math.toRadians(by), Math.toRadians(cz)));
		} catch (MatrixException e) {}
		
	}
	
	public Mesh(Double x, Double y, Double z, HashMap<Integer, D3CoordinateMatrix> innerCords) {
		this(x, y, z, 0., 0., 0., innerCords);
	}
	
	public Mesh(HashMap<Integer, D3CoordinateMatrix> innerCords) {
		this(0., 0., 0., 0., 0., 0., innerCords);
	}
	
	public void setColor(Color color) {
		pointsColor = color;
	}
	
	public Color getColor() {
		return pointsColor;
	}
	
	public void setCords(D3CoordinateMatrix cords) {
		
		this.x = cords.x();
		this.y = cords.y();
		this.z = cords.z();
		
		updateMatrixWScale();
		
	}
	
	public D3CoordinateMatrix getCords() {
		
		return new D3CoordinateMatrix(x, y, z);
		
	}
	
	public void setOrientationCords(D3CoordinateMatrix orientationCords) {
		
		this.ax = orientationCords.x();
		this.by = orientationCords.y();
		this.cz = orientationCords.z();
		
		updateMatrixWScale();
		
	}
	
	public D3CoordinateMatrix getOrientationCords() {
		
		return new D3CoordinateMatrix(ax, by, cz);
	}
	
	public void setOrientationX(Double ax) {
		
		this.ax = ax;
		updateMatrixWScale();
	}
	
	public void setOrientationY(Double by) {
		
		this.by = by;
		updateMatrixWScale();
	}
	
	public void setOrientationZ(Double cz) {
		this.cz = cz;
		updateMatrixWScale();
	}
	
	public void setScale(Double scale) {
		this.scale = scale;
		updateMatrixWScale();
	}
	
	public Double getScale() {
		return scale;
	}
	
	public HashMap<Integer, D3CoordinateMatrix> worldCords() {
		
		HashMap<Integer, D3CoordinateMatrix> outerCords = new HashMap<Integer, D3CoordinateMatrix>();
		
		for(Map.Entry<Integer, D3CoordinateMatrix> cord: innerCords.entrySet()) {
			try {
				outerCords.put(cord.getKey(), (D3CoordinateMatrix) Matrix.multiplyMatrixs(transMatrix, cord.getValue()));
			} catch (MatrixException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return outerCords;
		
	}
	
	public void updateMatrix(){
		try {
			transMatrix = DefinedMatrixs.TransformWithoutScaleMatrix(DefinedMatrixs.RotationFast(Math.toRadians(ax), Math.toRadians(by), Math.toRadians(cz)), DefinedMatrixs.Translation(x, y, z));
		} catch (MatrixException e) {}
		
	}
	
	public void updateMatrixWScale() {
		
		try {
			transMatrix = DefinedMatrixs.TransformWithScaleMatrix(DefinedMatrixs.RotationFast(Math.toRadians(ax), Math.toRadians(by), Math.toRadians(cz)), DefinedMatrixs.Translation(x, y, z), scale, scale, scale);
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void putDisplayCord(int key, D3CoordinateMatrix cord) {
		displayCords.put(key, cord);
	}
	
	public HashMap<Integer, D3CoordinateMatrix> getDisplayCords() {
		return displayCords;
	}
	
	public void setDisplayCords(HashMap<Integer, D3CoordinateMatrix> cords) {
		displayCords = cords;
	}
	
	public void setEdges(HashSet<Edge> e) {
		edges = e;
	}
	
	public void draw(Graphics2D g) {
		
		
		synchronized (displayCords) {
			g.setColor(this.getColor());
			Iterator<Entry<Integer, D3CoordinateMatrix>> it = displayCords.entrySet().iterator();
			while(it.hasNext()) {
				
				Entry<Integer, D3CoordinateMatrix> cord = it.next();
				
				if ((cord.getValue().x() <= 1 && cord.getValue().x() >= -1)
						&& (cord.getValue().y() <= 1 && cord.getValue().y() >= -1)
						&& (cord.getValue().z() <= 1 && cord.getValue().z() >= -1)) {

					cord.getValue().setX(Window.WIDTH / 2 * cord.getValue().x() + Window.WIDTH / 2);
					cord.getValue().setY(Window.HEIGHT / 2 * cord.getValue().y() + Window.HEIGHT / 2);
					cord.getValue().setZ(cord.getValue().z() + 1);

					
					g.fillOval((int)(cord.getValue().x()/1) - 5, (int) (cord.getValue().y()/1) - 5, (int) (10.), (int) (10.));
				}
			}
		}
		
		
	}
		
}
