package world;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import display.OptionsWindow;
import display.Panel;
import display.Window;
import math.D3CoordinateMatrix;
import math.DefinedMatrixs;
import math.Matrix;
import math.MatrixException;

public class World implements KeyListener, Runnable{

	Cube cube = new Cube(0., 0., 0., 5., 5., 5.);
	public Camera camera = new Camera(new D3CoordinateMatrix(0., 0., 0.), new D3CoordinateMatrix(-10., 0., 0.), new D3CoordinateMatrix(0., 1., 0.));
	
	ArrayList<Mesh> meshList = new ArrayList<Mesh>();
	
	public static Double SPEED = 0.5;
	public static Double ROT_SPEED = Math.PI/60;
	
	public World() {
		//cube.innerCords.add(new D3CoordinateMatrix(0., 0., 5.));
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
		
		
		
		D3CoordinateMatrix eye = camera.getEye();
		D3CoordinateMatrix target = camera.getTarget();
		D3CoordinateMatrix upDir = camera.getUpDir();
		
		D3CoordinateMatrix f = new D3CoordinateMatrix(eye.x()-target.x(),eye.y()-target.y(), eye.z()-target.z()).normalize();
		D3CoordinateMatrix l = D3CoordinateMatrix.crossProduct(upDir, f).normalize();
		D3CoordinateMatrix u = D3CoordinateMatrix.crossProduct(f, l);
		
		switch(key) {
		case KeyEvent.VK_W:
		

			/*f.multiplyByScalar(-SPEED);
			try {
				camera.setEye(D3CoordinateMatrix.addVec(eye, f));
				camera.setTarget(D3CoordinateMatrix.addVec(target, f));
			} catch (MatrixException e) {}
			*/
			camera.moveCameraInnerCordsZ(SPEED);
			break;
			
		case KeyEvent.VK_S:
		
			/*f.multiplyByScalar(SPEED);
			try {
				camera.setEye(D3CoordinateMatrix.addVec(eye, f));
				camera.setTarget(D3CoordinateMatrix.addVec(target, f));
			} catch (MatrixException e) {}
			*/
			camera.moveCameraInnerCordsZ(-SPEED);
			break;
		
		case KeyEvent.VK_D:
			/*
			l.multiplyByScalar(SPEED);
			try {
				camera.setEye(D3CoordinateMatrix.addVec(eye, l));
				camera.setTarget(D3CoordinateMatrix.addVec(target, l));
			} catch (MatrixException e) {}
			*/
			camera.moveCameraInnerCordsX(-SPEED);
			break;
		case KeyEvent.VK_A:
			/*
			l.multiplyByScalar(-SPEED);
			try {
				camera.setEye(D3CoordinateMatrix.addVec(eye, l));
				camera.setTarget(D3CoordinateMatrix.addVec(target, l));
			} catch (MatrixException e) {}
			*/
			camera.moveCameraInnerCordsX(SPEED);
			break;
		case KeyEvent.VK_CONTROL :
			/*
			u.multiplyByScalar(-SPEED);
			try {
				camera.setEye(D3CoordinateMatrix.addVec(eye, u));
				camera.setTarget(D3CoordinateMatrix.addVec(target, u));
			} catch (MatrixException e) {}
			*/
			camera.moveCameraInnerCordsY(SPEED);
			break;
		case KeyEvent.VK_SPACE :
			/*
			u.multiplyByScalar(SPEED);
			try {
				camera.setEye(D3CoordinateMatrix.addVec(eye, u));
				camera.setTarget(D3CoordinateMatrix.addVec(target, u));
			} catch (MatrixException e) {}
			*/
			camera.moveCameraInnerCordsY(-SPEED);
			break;
			
			
		case KeyEvent.VK_E:
			/*
			try {
				u = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationZ(ROT_SPEED), u);
			} catch (MatrixException e) { }
			camera.setUpDir(u);
			*/
			camera.rotateCameraZ(-ROT_SPEED);
			break;
		case KeyEvent.VK_Q:
			/*
			try {
				u = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationZ(-ROT_SPEED), u);
			} catch (MatrixException e) { }
			camera.setUpDir(u);
			*/
			camera.rotateCameraZ(ROT_SPEED);
			break;
		case KeyEvent.VK_R:
			/*
			try {
				u = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationX(ROT_SPEED), u);
				f = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationX(ROT_SPEED), f);
			} catch (MatrixException e) {}
			
			camera.setUpDir(u);
			try {
				camera.setTarget((D3CoordinateMatrix) Matrix.addMatrixs(eye, f));
			} catch (MatrixException e) {}
			*/
			camera.rotateCameraX(-ROT_SPEED);
			break;
		case KeyEvent.VK_F:
			/*
			try {
				u = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationX(-ROT_SPEED), u);
				f = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationX(-ROT_SPEED), f);
			} catch (MatrixException e) {}
			
			camera.setUpDir(u);
			try {
				camera.setTarget((D3CoordinateMatrix) Matrix.addMatrixs(eye, f));
			} catch (MatrixException e) {}
			*/
			camera.rotateCameraX(ROT_SPEED);
			break;
			
		case KeyEvent.VK_Z:
			/*
			try {
				f = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationY(ROT_SPEED), f);
			} catch (MatrixException e) {}
			try {
				camera.setTarget((D3CoordinateMatrix) Matrix.addMatrixs(eye, f));
			} catch (MatrixException e) {}
			*/
			camera.rotateCameraY(-ROT_SPEED);
			break;
		case KeyEvent.VK_X:
			/*
			try {
				f = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationY(-ROT_SPEED), f);
			} catch (MatrixException e) {}
			try {
				camera.setTarget((D3CoordinateMatrix) Matrix.addMatrixs(eye, f));
			} catch (MatrixException e) {}
			*/
			camera.rotateCameraY(ROT_SPEED);
			break;	
		default:
			break;
		}
		
		//camera.updateMatrix();
		
		System.out.println(camera.getEye().toString());
		System.out.println(camera.getUpDir().toString());
		
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
					
					int tempX, tempY, tempZ; 
					//System.out.println(refPoint);
					tempX = (int) (Window.WIDTH/2*refPoint.x() + Window.WIDTH/2);
					tempY = (int) (Window.HEIGHT/2*refPoint.y() + Window.HEIGHT/2);
					tempZ=  (int) (refPoint.z() + 1 );
					
					
					//System.out.println(cord.w());
					g.setColor(Color.red);
				
					//tempY = Window.HEIGHT - tempY +5;
					
					g.fillOval(tempX - 10, tempY - 10 , (int)(20.), (int)(20.));
				}
			} catch (MatrixException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		for(Mesh mesh : meshList) {
			
			outerCords = mesh.worldCords();
			
			
			
			int tempX;
			int tempY;
			Double tempZ;
			
			for(Map.Entry<Integer, D3CoordinateMatrix> cord : outerCords.entrySet()) {
				
				try {
					cord.setValue((D3CoordinateMatrix) Matrix.multiplyMatrixs(camera.lookAt, cord.getValue()));
					cord.setValue((D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.perspectiveProjection(Math.toRadians(Window.FOV_X),Math.toRadians(Window.FOV_Y),1.,Window.DISTANCE_VIEW), cord.getValue()));
					
					cord.getValue().toNDC();
					
					
					if((cord.getValue().x() <= 1 && cord.getValue().x() >= - 1 ) && (cord.getValue().y() <= 1 && cord.getValue().y() >= - 1 ) && (cord.getValue().z() <= 1 && cord.getValue().z() >= - 1 )) {
						
						 
						
						tempX = (int) (Window.WIDTH/2*cord.getValue().x() + Window.WIDTH/2);
						tempY = (int) (Window.HEIGHT/2*cord.getValue().y() + Window.HEIGHT/2);
						tempZ=  (cord.getValue().z() + 1 );
						
						//System.out.println(cord);
						//System.out.println(cord.w());
						g.setColor(mesh.getColor());
					
						//tempY = Window.HEIGHT - tempY +5;
						
						g.fillOval(tempX - 5, tempY - 5, (int)(10.), (int)(10.));
					}
					
				} catch (MatrixException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		}
		
		
	}

	@Override
	public void run() {
		
		D3CoordinateMatrix cord;
		
		int i;
		
		while(true) {
			
			
			
			
			
			
		}
		
		
	}
	
	
}
