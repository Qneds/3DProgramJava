package math;

public class DefinedMatrixs {

	
	
	public static SquareMatrix identityMatrix(int dim) {
		SquareMatrix temp = new SquareMatrix(dim);
		for(int i = 0 ; i < dim ; i++) {
			temp.matrix[i][i] = 1.;
		}
		return temp;
	}
	
	
	public static SquareMatrix orthogonalProjection(Double left, Double right, Double bottom, Double top, Double near, Double far) {
		
		SquareMatrix matrix = new SquareMatrix(4);
		matrix.matrix[0][0] = 2/(right - left);
		matrix.matrix[1][1] = 2/(top - bottom);
		matrix.matrix[2][2] = -2/(far - near);
		matrix.matrix[3][3] = 1.;
		
		matrix.matrix[0][3] = (right + left)/(right - left);
		matrix.matrix[1][3] = (top + bottom)/(top - bottom);
		matrix.matrix[2][3] = (far + near)/(far - near);
		
		return matrix;
		
	}
	
	public static SquareMatrix perspectiveProjection(Double fovX, Double fovY, Double farZ, Double nearZ) {
		SquareMatrix matrix = new SquareMatrix(4);
		
		matrix.matrix[0][0] = 1./Math.tan(fovX/2.);
		matrix.matrix[1][1] = 1./Math.tan(fovY/2.);
		matrix.matrix[2][2] = -(farZ + nearZ)/(farZ - nearZ);
		matrix.matrix[2][3] = (-2*farZ*nearZ)/(farZ - nearZ);
		matrix.matrix[3][2] = -1.;
		
		return matrix;
		
	}
	
	
	public static SquareMatrix perspectiveProjection(Double left, Double right, Double bottom, Double top, Double near, Double far) {
		
		SquareMatrix matrix = new SquareMatrix(4);
		matrix.matrix[0][0] = 2*near/(right - left);
		matrix.matrix[1][1] = 2*near/(top - bottom);
		matrix.matrix[2][2] = -(far + near)/(far - near);
		
		matrix.matrix[3][2] = -1.;
		
		matrix.matrix[0][2] = (right + left)/(right - left);
		matrix.matrix[1][2] = (top + bottom)/(top - bottom);
		
		matrix.matrix[2][3] = (-2*far*near)/(far - near);
		
		return matrix;
		
	}
	
	public static SquareMatrix RotationX(Double a) {
		
		SquareMatrix matrix = new SquareMatrix(4);
		
		matrix.matrix[0][0] = 1.;
		matrix.matrix[1][1] = Math.cos(a);
		matrix.matrix[2][2] = Math.cos(a);
		matrix.matrix[1][2] = -1*Math.sin(a);
		matrix.matrix[2][1] = Math.sin(a);
		matrix.matrix[3][3] = 1.;
		
		return matrix;
	}
	
	public static SquareMatrix RotationY(Double b) {
		SquareMatrix matrix = new SquareMatrix(4);
		
		matrix.matrix[1][1] = 1.;
		matrix.matrix[0][0] = Math.cos(b);
		matrix.matrix[2][2] = Math.cos(b);
		matrix.matrix[2][0] = -1*Math.sin(b);
		matrix.matrix[0][2] = Math.sin(b);
		matrix.matrix[3][3] = 1.;
		
		return matrix;
		
	}
	
	public static SquareMatrix RotationZ(Double c) {
		SquareMatrix matrix = new SquareMatrix(4);
		
		matrix.matrix[2][2] = 1.;
		matrix.matrix[1][1] = Math.cos(c);
		matrix.matrix[0][0] = Math.cos(c);
		matrix.matrix[0][1] = -1*Math.sin(c);
		matrix.matrix[1][0] = Math.sin(c);
		matrix.matrix[3][3] = 1.;
		
		return matrix;
		
	}
	
	public static SquareMatrix RotationSlow(Double ax, Double by, Double cz) {
		
		SquareMatrix matrix = null;
		
		try {
			 matrix = (SquareMatrix) Matrix.multiplyMatrixs(Matrix.multiplyMatrixs(RotationZ(cz), RotationY(by)), RotationX(ax));
		} catch (MatrixException e) {
			e.printStackTrace();
		}
		
		return matrix;
	}
	
	public static SquareMatrix RotationFast(Double ax, Double by, Double cz) {
		SquareMatrix matrix = new SquareMatrix(4);
		
		
		Double A = Math.cos(ax);
		Double B = Math.sin(ax);
		Double C = Math.cos(by);
		Double D = Math.sin(by);
		Double E = Math.cos(cz);
		Double F = Math.sin(cz);
		
		Double AD = A*D;
		Double BD = B*D;
		
		matrix.matrix[0][0] = C*E;
		matrix.matrix[0][1] = -C*F;
		matrix.matrix[0][2] = D;
		matrix.matrix[1][0] = BD*E + A*F;
		matrix.matrix[1][1] = -BD*F + A*E;
		matrix.matrix[1][2] = -B*C;
		matrix.matrix[2][0] = -AD*E + B*F;
		matrix.matrix[2][1] = AD*F + B*E;
		matrix.matrix[2][2] = A*C;
		matrix.matrix[3][3] = 1.;
		
		return matrix;
		
	}
	
	public static SquareMatrix Translation(Double x, Double y, Double z) {
		
		SquareMatrix temp = identityMatrix(4);
		
		temp.matrix[0][3] = x;
		temp.matrix[1][3] = y;
		temp.matrix[2][3] = z;
		
		return temp;
						
		
	}
	
	public static SquareMatrix Translation(D3CoordinateMatrix cords) {
		
		return Translation(cords.matrix[0][0], cords.matrix[1][0], cords.matrix[2][0]);
						
		
	}
	
	public static SquareMatrix Scale(Double x, Double y, Double z) {
		
		SquareMatrix temp = new SquareMatrix(4);
		
		temp.matrix[0][0] = x;
		temp.matrix[1][1] = y;
		temp.matrix[2][2] = z;
		temp.matrix[3][3] = 1.;
		
		return temp;
		
	}
	///////////////
	public static SquareMatrix TransformWithoutScaleMatrix(SquareMatrix translationMatrix, SquareMatrix rotationMatrix) throws MatrixException {
		
		SquareMatrix temp ;
	
		
		temp = (SquareMatrix) Matrix.multiplyMatrixs(translationMatrix, rotationMatrix);
		
		
		
		return  temp;
	}

	
	public static SquareMatrix TransformWithScaleMatrix(SquareMatrix rotationMatrix, SquareMatrix translationMatrix, Double xScale, Double yScale, Double zScale) throws MatrixException {
		
		SquareMatrix temp = (SquareMatrix) Matrix.multiplyMatrixs(TransformWithoutScaleMatrix(translationMatrix, rotationMatrix), Scale(xScale, yScale, zScale));
		return temp;
		
	}
	
	public static SquareMatrix TransformWithoutScaleMatrixInverse(SquareMatrix rotationMatrix, SquareMatrix translationMatrix) throws MatrixException {
		
		SquareMatrix temp ;
		/*
		
		if(rotationMatrix.i != 4 || rotationMatrix.j != 4) {
			throw new MatrixException("Macierz rotacji o nie w³aœciwych wymiarach.");
		}
		
		
		for(int k = 0; k < 3 ; k++ ) {
			for(int l = 0 ; l < 3 ; l++) {
				temp.matrix[k][l] = rotationMatrix.matrix[k][l] ;
			}
		}
		
		for(int k = 0 ; k < 4 ; k++) {
			temp.matrix[k][3] = rotationMatrix.matrix[k][0] ;
		}
		
		
		
		*/
		
		temp = (SquareMatrix) Matrix.multiplyMatrixs(rotationMatrix, translationMatrix);
		
		
		
		return  temp;
	}

	
	public static SquareMatrix TransformWithScaleMatrixInverse(SquareMatrix rotationMatrix, SquareMatrix translationMatrix, Double xScale, Double yScale, Double zScale) throws MatrixException {
		
		SquareMatrix temp = (SquareMatrix) Matrix.multiplyMatrixs(TransformWithoutScaleMatrixInverse(rotationMatrix, translationMatrix), Scale(xScale, yScale, zScale));
		return temp;
		
	}
	/////////////////////
	
	
	public static SquareMatrix lookAtMatrix(D3CoordinateMatrix target, D3CoordinateMatrix eye , D3CoordinateMatrix upDir) throws MatrixException {
		
		SquareMatrix rot = new SquareMatrix(4);
		SquareMatrix trans = DefinedMatrixs.Translation(-eye.x(), -eye.y(), -eye.z());
		
		D3CoordinateMatrix forward;
		D3CoordinateMatrix left;
		D3CoordinateMatrix up;
		
		
		forward = D3CoordinateMatrix.subtractVec(eye, target);
		forward = forward.normalize();
		
		
		//error left is a NaN
		left = D3CoordinateMatrix.crossProduct(upDir, forward);
		left = left.normalize();
		
		up = D3CoordinateMatrix.crossProduct(forward, left);
		
		rot.matrix[0][0] = left.x();
		rot.matrix[0][1] = left.y();
		rot.matrix[0][2] = left.z();
		rot.matrix[1][0] = up.x();
		rot.matrix[1][1] = up.y();
		rot.matrix[1][2] = up.z();
		rot.matrix[2][0] = forward.x();
		rot.matrix[2][1] = forward.y();
		rot.matrix[2][2] = forward.z();
		rot.matrix[3][3] = 1.;
		
		
		
		return (SquareMatrix) Matrix.multiplyMatrixs(rot, trans);
		
		
	}
	
}
