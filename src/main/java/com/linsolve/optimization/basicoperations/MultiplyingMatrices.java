package com.linsolve.optimization.basicoperations;

import com.linsolve.optimization.algebraiccomponents.Matrix;

public class MultiplyingMatrices {
	
	public static Matrix multiplyMatrix(Matrix matrix_1, Matrix matrix_2) {
        //A*B
        if(!(matrix_1.getN() == matrix_2.getM())) {
            return null;
        } else {
            Matrix result_store = new Matrix(matrix_1.getM(), matrix_2.getN());
            Double[][] t_store = new Double[matrix_1.getM()][matrix_2.getN()];
            Double cij = 0.0;
            
            for(int i = 0; i < matrix_1.getM(); i++) {
                for(int j = 0; j < matrix_2.getN(); j++) {
                    cij = 0.0;
                    for(int k = 0; k < matrix_1.getN(); k++) {
                        cij = cij + matrix_1.getT()[i][k] * matrix_2.getT()[k][j];
                    }
                    t_store[i][j] = cij;
                }
            }
            result_store.setT(t_store);    
            return result_store;
        }
    }

}
