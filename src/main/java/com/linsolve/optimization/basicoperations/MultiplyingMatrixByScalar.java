package com.linsolve.optimization.basicoperations;

import com.linsolve.optimization.algebraiccomponents.Matrix;

public class MultiplyingMatrixByScalar 
{
	public static Matrix multiplyByScalar(Matrix matrix, Double lamda)
	{
		Matrix result=new Matrix(matrix.getM(),matrix.getN());
		Double[][] t_result=new Double[matrix.getM()][matrix.getN()];
		for(int i=0;i<t_result.length;i++)
		{
			for(int j=0;j<t_result[0].length;j++)
			{
				t_result[i][j]=lamda*matrix.getT()[i][j];
			}			
		}
		result.setT(t_result);
		return result;
	}


}
