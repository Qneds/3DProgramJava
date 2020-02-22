package math;

public class D3CoordinateMatrix extends Matrix {
	
	
	public D3CoordinateMatrix() {
		super(4, 1);
		this.matrix[3][0] = 1.;
	}
	
	public D3CoordinateMatrix(Double x, Double y, Double z) {
		
		super(4, 1);
		this.matrix[0][0] = x;
		this.matrix[1][0] = y;
		this.matrix[2][0] = z;
		this.matrix[3][0] = 1.;
	}
	
	
	public Double x() {
		return this.matrix[0][0];
	}
	
	public Double y() {
		return this.matrix[1][0];
	}
	
	public Double z() {
		return this.matrix[2][0];
	}
	
	public Double w() {
		return this.matrix[3][0];
	}
	
	public void setX(Double x) {
		this.matrix[0][0] = x;
	}
	
	public void setY(Double y) {
		this.matrix[1][0] = y;
	}
	
	public void setZ(Double z) {
		this.matrix[2][0] = z;
	}
	
	public void setW(Double w) {
		this.matrix[3][0] = w;
	}
	
	
	public static D3CoordinateMatrix crossProduct(D3CoordinateMatrix v1, D3CoordinateMatrix v2) {
		
		D3CoordinateMatrix temp = new D3CoordinateMatrix();
		
		temp.matrix[0][0] = v1.matrix[1][0]*v2.matrix[2][0] + v1.matrix[2][0]*v2.matrix[1][0];
		temp.matrix[1][0] = v1.matrix[2][0]*v2.matrix[0][0] + v1.matrix[0][0]*v2.matrix[2][0];
		temp.matrix[2][0] = v1.matrix[0][0]*v2.matrix[1][0] + v1.matrix[1][0]*v2.matrix[0][0];
		
		return temp;
		
	}
	
	public static Double dotProduct(D3CoordinateMatrix v1, D3CoordinateMatrix v2) {
		return v1.matrix[0][0]*v2.matrix[0][0] + v1.matrix[1][0]*v2.matrix[1][0] + v1.matrix[2][0]*v2.matrix[2][0];
		
	}
	
	public Double length() {
		
		return Math.sqrt(D3CoordinateMatrix.dotProduct(this, this));
		
	}
	
	public D3CoordinateMatrix normalize() {
		
		D3CoordinateMatrix temp = new D3CoordinateMatrix();
		
		Double length = this.length();
		
		temp.matrix[0][0] = this.matrix[0][0]/length;
		temp.matrix[1][0] = this.matrix[1][0]/length;
		temp.matrix[2][0] = this.matrix[2][0]/length;
		
		return temp;
		
	}
	
	public static D3CoordinateMatrix subtractVec(D3CoordinateMatrix m1, D3CoordinateMatrix m2) throws MatrixException {
		
	
		D3CoordinateMatrix temp = (D3CoordinateMatrix) Matrix.subtractMatrixs(m1, m2);
		
		temp.matrix[3][0] = 1.;
		
		return temp;
	}
	
	
	
	public static D3CoordinateMatrix addVec(D3CoordinateMatrix m1, D3CoordinateMatrix m2) throws MatrixException{
		
		D3CoordinateMatrix temp = (D3CoordinateMatrix) Matrix.addMatrixs(m1, m2);
		
		temp.matrix[3][0] = 1.;
		
		return temp;
		
	}

	@Override
	public String toString() {
		return "[ " + this.x() + " , " + this.y() + " , " + this.z() + " ]";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof D3CoordinateMatrix) {
			
			D3CoordinateMatrix c = (D3CoordinateMatrix)obj;
			
			return this.x() == c.x() && this.y() == c.y() && this.z() == c.z() && this.matrix[3][0] == c.matrix[3][0];
			
		}
		return false;
	}
	
	
	
	public void toNDC() {
		matrix[0][0] /= matrix[3][0];
		matrix[1][0] /= matrix[3][0];
		matrix[2][0] /= matrix[3][0];
		
	}
	
	public D3CoordinateMatrix clone() {
		
		D3CoordinateMatrix temp = new D3CoordinateMatrix(this.x(), this.y(), this.y());
		
		
		return temp;
	}
}