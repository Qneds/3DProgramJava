package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import display.OptionsWindow;
import display.Window;
import math.D3CoordinateMatrix;
import math.DefinedMatrixs;
import math.Matrix;
import math.MatrixException;

public class World implements KeyListener {

	Cube cube = new Cube(0., 0., 0., 5., 5., 5.);
	public Camera camera = new Camera(new D3CoordinateMatrix(0., 0., 0.), new D3CoordinateMatrix(-10., 0., 0.), new D3CoordinateMatrix(0., 1., 0.));
	
	ArrayList<Mesh> meshList = new ArrayList<Mesh>();
	
	public static Double SPEED = 0.5;
	public static Double ROT_SPEED = Math.PI/60;
	
	public World() {
		meshList.add(cube);
		
	}
	
	public ArrayList<Mesh> getMeshList(){
		return meshList;
	}
	
	public void addMeshToMeshList(Mesh mesh) {
		meshList.add(mesh);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		
		
		
		
		switch(key) {
		case KeyEvent.VK_W:
			camera.moveCameraInnerCordsZ(SPEED);
			break;
			
		case KeyEvent.VK_S:
			camera.moveCameraInnerCordsZ(-SPEED);
			break;
		
		case KeyEvent.VK_D:
			camera.moveCameraInnerCordsX(-SPEED);
			break;
			
		case KeyEvent.VK_A:
			camera.moveCameraInnerCordsX(SPEED);
			break;
			
		case KeyEvent.VK_CONTROL :
			camera.moveCameraInnerCordsY(SPEED);
			break;
			
		case KeyEvent.VK_SPACE :
			camera.moveCameraInnerCordsY(-SPEED);
			break;
			
		case KeyEvent.VK_E:
			camera.rotateCameraZ(-ROT_SPEED);
			break;
			
		case KeyEvent.VK_Q:
			camera.rotateCameraZ(ROT_SPEED);
			break;
			
		case KeyEvent.VK_R:
			camera.rotateCameraX(-ROT_SPEED);
			break;
			
		case KeyEvent.VK_F:
			camera.rotateCameraX(ROT_SPEED);
			break;
			
		case KeyEvent.VK_Z:
			camera.rotateCameraY(-ROT_SPEED);
			break;
			
		case KeyEvent.VK_X:
			camera.rotateCameraY(ROT_SPEED);
			break;	
			
		default:
			break;
		}
		
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void draw(Graphics2D g) {
		
		HashMap<Integer, D3CoordinateMatrix> outerCords;
		
		
		
		if(OptionsWindow.getReferencePointDraw()) {
			
			D3CoordinateMatrix refPoint = new D3CoordinateMatrix(0., 0., 0.);
			
			try {
				refPoint = (D3CoordinateMatrix) Matrix.multiplyMatrixs(camera.lookAt, refPoint);
				refPoint = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.perspectiveProjection(Math.toRadians(Window.FOV_X), Math.toRadians(Window.FOV_Y), 1., Window.DISTANCE_VIEW), refPoint);
				refPoint.toNDC();
				
				if((refPoint.x() <= 1 && refPoint.x() >= - 1 ) && (refPoint.y() <= 1 && refPoint.y() >= - 1 ) && (refPoint.z() <= 1 && refPoint.z() >= - 1 )) {
					
					
					
					refPoint.setX(Window.WIDTH/2*refPoint.x() + Window.WIDTH/2); 
					refPoint.setY(Window.HEIGHT/2*refPoint.y() + Window.HEIGHT/2);
					refPoint.setZ(refPoint.z() + 1 );
					
					
					g.setColor(Color.red);
					g.fillOval((int)(refPoint.x()/1) - 10, (int)(refPoint.y()/1) - 10 , (int)(20.), (int)(20.));
				}
			} catch (MatrixException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		for(Mesh mesh : meshList) {
			
			outerCords = mesh.worldCords();
			
			g.setColor(mesh.getColor());
			
			
			
			for(Map.Entry<Integer, D3CoordinateMatrix> cord : outerCords.entrySet()) {
				
				try {
					cord.setValue((D3CoordinateMatrix) Matrix.multiplyMatrixs(camera.lookAt, cord.getValue()));
					cord.setValue((D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.perspectiveProjection(Math.toRadians(Window.FOV_X),Math.toRadians(Window.FOV_Y),1.,Window.DISTANCE_VIEW), cord.getValue()));
					
					cord.getValue().toNDC();
					
					
					if((cord.getValue().x() <= 1 && cord.getValue().x() >= - 1 ) && (cord.getValue().y() <= 1 && cord.getValue().y() >= - 1 ) && (cord.getValue().z() <= 1 && cord.getValue().z() >= - 1 )) {
						
						 
						
						cord.getValue().setX(Window.WIDTH/2*cord.getValue().x() + Window.WIDTH/2);
						cord.getValue().setY(Window.HEIGHT/2*cord.getValue().y() + Window.HEIGHT/2);
						cord.getValue().setZ(cord.getValue().z() + 1 );
						
						
						
						g.fillOval((int)(cord.getValue().x()/1) - 5, (int)(cord.getValue().y()/1)- 5, (int)(10.), (int)(10.));
					}
					
				} catch (MatrixException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			for(Edge edge : mesh.edges) {
				
				edge.drawEdge(g, outerCords);
				
				
			}
			
			
		}
		
		
	}
	
	
}
