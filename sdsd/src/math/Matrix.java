package math;



public class Matrix {

	protected Double matrix[][];
	protected int i, j;
	
	public Matrix(int i, int j) {
		this.i = i;
		this.j = j;
		matrix = new Double[i][j];
		
		for(int k = 0 ; k < i; k++) {
			
			for(int l = 0; l < j; l++) {
				
				matrix[k][l] = 0.0;
			}
			
		}
		
	}
	
	public Double[][] getMatrixArray() {
		return matrix;
	}
	
	public static Matrix addMatrixs(Matrix m1, Matrix m2) throws MatrixException{
		
		
		if(m1.i != m2.i || m1.j != m2.j)
			throw new MatrixException("Nie mo¿na dodawaæ macierzy o ró¿nej iloœci kolumn lub wierszy.");
			
		Matrix sum = new Matrix(m1.i, m1.j);
		
		///
		if(m1 instanceof SquareMatrix && m2 instanceof SquareMatrix)
			sum = new SquareMatrix(m1.i);
			
		if(m1 instanceof SquareMatrix && m2 instanceof D3CoordinateMatrix)
			sum = new D3CoordinateMatrix();
		if(m1 instanceof D3CoordinateMatrix && m2 instanceof D3CoordinateMatrix)
			sum = new D3CoordinateMatrix();
		
		////
		
		for(int k = 0; k < sum.i; k++) {
			
			for(int l = 0; l < sum.j ; l++ ) {
					sum.matrix[k][l] = m1.matrix[k][l] + m2.matrix[k][l];
			}
		}
			
		return sum;
			
	}
		
	public static Matrix subtractMatrixs(Matrix m1, Matrix m2) throws MatrixException{
		
		
		if(m1.i != m2.i || m1.j != m2.j)
			throw new MatrixException("Nie mo¿na odejmowaæ macierzy o ró¿nej iloœci kolumn lub wierszy.");
			
		Matrix subtract = new Matrix(m1.i, m1.j);
		
		/////////////
		if(m1 instanceof SquareMatrix && m2 instanceof SquareMatrix)
			subtract = new SquareMatrix(m1.i);
			
		if(m1 instanceof SquareMatrix && m2 instanceof D3CoordinateMatrix)
			subtract = new D3CoordinateMatrix();
		if(m1 instanceof D3CoordinateMatrix && m2 instanceof D3CoordinateMatrix)
			subtract = new D3CoordinateMatrix();
		
		
		//////////
		for(int k = 0; k < subtract.i; k++) {
			
			for(int l = 0; l < subtract.j ; l++ ) {
				subtract.matrix[k][l] = m1.matrix[k][l] - m2.matrix[k][l];
			}
		}
			
		return subtract;
			
	}
		

	public void multiplyByScalar(Double c) {
		
		for(int k = 0; k < this.i; k++) {
			
			for(int l = 0; l < this.j ; l++ ) {
				 this.matrix[k][l] *= c;
			}
		}
		
	}
	
	public static Matrix multiplyMatrixs(Matrix m1, Matrix m2) throws MatrixException {
		
		if(m1.j != m2.i)
			throw new MatrixException("Nie mo¿na mno¿yæ takich matryc");
		
		///////	
		Matrix newM = new Matrix(m1.i, m2.j);;
		if(m1 instanceof SquareMatrix && m2 instanceof SquareMatrix)
			newM = new SquareMatrix(m1.i);
			
		if(m1 instanceof SquareMatrix && m2 instanceof D3CoordinateMatrix)
		newM = new D3CoordinateMatrix();
		////////////
		
		Double temp =  0.;
		int con = m1.j;
		
		for(int k = 0; k < newM.i; k++) {
			
			for(int l = 0; l < newM.j ; l++ ) {
				
				for(int i = 0 ; i < con ; i++) {
					temp += m1.matrix[k][i]*m2.matrix[i][l] ;
				}
				
				newM.matrix[k][l] = temp;
				temp = 0.;
			}
		}
		
		return newM;
	}
	
	public void transpose() {
		
		
		Double[][] temp = new Double[j][i];
		
		
		for(int k = 0; k < j ; k++) {
			
			for(int l = 0 ; l < i ; l++) {
				
				temp[k][l] = matrix[l][k];
						
			}
		}
		
		matrix = temp;
		
		
		int tmp = i;
		
		i = j;
		j = tmp;
		
	}

	public Matrix clone() {
		
		Matrix temp = new Matrix(this.i, this.j);
		
		for(int k = 0 ; k < i; k++) {
			
			for(int l = 0; l < j; l++) {
				
				temp.matrix[k][l] = matrix[k][l];
			}
			
		}
		return temp;
		
	}
	
	
	
}
