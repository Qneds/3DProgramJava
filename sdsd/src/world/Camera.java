package world;

import math.D3CoordinateMatrix;
import math.DefinedMatrixs;
import math.Matrix;
import math.MatrixException;
import math.SquareMatrix;

public class Camera {
	
	private D3CoordinateMatrix eye;
	private D3CoordinateMatrix upDir;
	private D3CoordinateMatrix target;
	
	
	public SquareMatrix lookAt;
	
	
	public Camera() {
		eye = new D3CoordinateMatrix(1., 1., 1.);
		target = new D3CoordinateMatrix(0., 0., 0.);
		upDir = new D3CoordinateMatrix(0., 1., 0.);
		
		try {
			lookAt = DefinedMatrixs.lookAtMatrix(target, eye, upDir);
		} catch (MatrixException e) {
			e.printStackTrace();
		}
		
	}
	
	public Camera(D3CoordinateMatrix target, D3CoordinateMatrix eye, D3CoordinateMatrix upDir) {
		this.eye = eye;
		this.target = target;
		this.upDir = upDir;
		
		try {
			lookAt = DefinedMatrixs.lookAtMatrix(target, eye, upDir);
		} catch (MatrixException e) {
			e.printStackTrace();
		}	
	}
	
	public void updateMatrix() {
		try {
			lookAt = DefinedMatrixs.lookAtMatrix(target, eye, upDir);
		} catch (MatrixException e) {
			e.printStackTrace();
		}
	}
	
	public void setEye(D3CoordinateMatrix eye) {
		this.eye = eye;
		////update???
	}
	
	public D3CoordinateMatrix getEye() {
		return eye;
	}
	
	public void setTarget(D3CoordinateMatrix target) {
		this.target = target;
		////update??
	}
	
	public D3CoordinateMatrix getTarget() {
		return target;
	}
	
	public void setUpDir(D3CoordinateMatrix upDir) {
		this.upDir = upDir;
		////update??
	}
	
	public D3CoordinateMatrix getUpDir() {
		return upDir;
	}
	
	public void moveCamera(D3CoordinateMatrix vec) {
		
		/*
		try {
			eye = D3CoordinateMatrix.addVec(vec, eye);
			target = D3CoordinateMatrix.addVec(vec, target);
			updateMatrix();
		} catch (MatrixException e) {
			e.printStackTrace();
		}
		*/
		try {
			SquareMatrix transf = lookAt.clone();
			
			transf.getMatrixArray()[0][3] = 0.;
			transf.getMatrixArray()[1][3] = 0.;
			transf.getMatrixArray()[2][3] = 0.;
			
			transf.transpose();
			
			transf.getMatrixArray()[0][3] = eye.x();
			transf.getMatrixArray()[1][3] = eye.y();
			transf.getMatrixArray()[2][3] = eye.z();
			
			vec = D3CoordinateMatrix.subtractVec((D3CoordinateMatrix) Matrix.multiplyMatrixs(transf, vec), eye );
			System.out.println(vec);
			moveCamera(vec);
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void moveCameraX(Double x) {
		moveCamera(new D3CoordinateMatrix(x, 0., 0.));
	}
	
	public void moveCameraY(Double y) {
		moveCamera(new D3CoordinateMatrix(0., y, 0.));
	}
	
	public void moveCameraZ(Double z) {
		moveCamera(new D3CoordinateMatrix(0., 0., z));
	}
	
	
	public void moveCameraInnerCords(D3CoordinateMatrix vec) {
		
		
		try {
			lookAt = (SquareMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.Translation(vec), lookAt);
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void moveCameraInnerCordsX(Double x) {
		moveCameraInnerCords(new D3CoordinateMatrix(x, 0., 0.));
	}
	
	public void moveCameraInnerCordsY(Double y) {
		moveCameraInnerCords(new D3CoordinateMatrix(0., y, 0.));
	}
	
	public void moveCameraInnerCordsZ(Double z) {
		moveCameraInnerCords(new D3CoordinateMatrix(0., 0., z));
	}
	
	public void rotateCameraZ(Double zc) {
		//       !-MO¯NA ZOSTAWIÆ
		
		
		/* 
		lookAt.getMatrixArray()[0][3] = 0.;!
		lookAt.getMatrixArray()[1][3] = 0.;!
		lookAt.getMatrixArray()[2][3] = 0.;!
		*/
		
		
		try {
			
			//ssssssupDir.multiplyByScalar(-1.);
			SquareMatrix transf = DefinedMatrixs.Translation(-eye.x(), -eye.y(), -eye.y());
			/*
			transf.getMatrixArray()[0][3] = 0.;
			transf.getMatrixArray()[1][3] = 0.;
			transf.getMatrixArray()[2][3] = 0.;
			*/
			//lookAt.transpose();
			//transf = lookAt;
			/*
			//upDir = (D3CoordinateMatrix) Matrix.multiplyMatrixs(lookAt, upDir);
			upDir = new D3CoordinateMatrix(0., 1., 0.);
			System.out.println(upDir.toString());
			upDir = (D3CoordinateMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationZ(zc), upDir)Matrix.multiplyMatrixs(DefinedMatrixs.RotationZ(zc), upDir);;
			System.out.println(upDir.toString());
			upDir = (D3CoordinateMatrix) Matrix.multiplyMatrixs(transf, upDir);
			System.out.println(upDir.toString());
			//upDir = upDir.normalize();
			*/
			
			lookAt = (SquareMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationZ(zc), lookAt);
		//	lookAt = (SquareMatrix) Matrix.multiplyMatrixs(lookAt, transf);!
			
			transf = lookAt.clone();
			
			transf.getMatrixArray()[0][3] = 0.;
			transf.getMatrixArray()[1][3] = 0.;
			transf.getMatrixArray()[2][3] = 0.;
			
			transf.transpose();
			
			transf.getMatrixArray()[0][3] = eye.x();
			transf.getMatrixArray()[1][3] = eye.y();
			transf.getMatrixArray()[2][3] = eye.z();
			
			upDir = new D3CoordinateMatrix(0., 1., 0.);
			
			upDir = (D3CoordinateMatrix) Matrix.multiplyMatrixs(transf, upDir);
			
			upDir = (D3CoordinateMatrix) Matrix.subtractMatrixs(upDir, eye);
			
			//TO-DO przrobiæ innerTrans
			
			
			
			
			//updateMatrix();
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void rotateCameraY(Double yb) {
		try {
			lookAt = (SquareMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationY(yb), lookAt);
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rotateCameraX(Double xa) {
		try {
			lookAt = (SquareMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationX(xa), lookAt);
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rotateCamera(Double xa, Double yb, Double zc) {
		try {
			lookAt = (SquareMatrix) Matrix.multiplyMatrixs(DefinedMatrixs.RotationFast(xa, yb, zc), lookAt);
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
