package math;

public class SquareMatrix extends Matrix {

	SquareMatrix(int dim){
		super(dim, dim);
	}
	
	public static Matrix identityMatrix(int dim) {
		SquareMatrix temp = new SquareMatrix(dim);
		for(int i = 0 ; i < dim ; i++) {
			temp.matrix[i][i] = 1.;
		}
		return temp;
	}
	
	@Override 
	public void transpose() {
		super.transpose();
	}
	
	public SquareMatrix clone() {
		
		SquareMatrix temp = new SquareMatrix(this.i);
		
		for(int k = 0 ; k < i; k++) {
			
			for(int l = 0; l < j; l++) {
				
				temp.matrix[k][l] = matrix[k][l];
			}
			
		}
		return temp;
	}
	
	
}
