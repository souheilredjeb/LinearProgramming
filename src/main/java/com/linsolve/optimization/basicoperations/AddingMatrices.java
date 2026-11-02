package com.linsolve.optimization.basicoperations;

import com.linsolve.optimization.algebraiccomponents.Matrix;

public class AddingMatrices {
	
	public static Matrix add(Matrix m1, Matrix m2)
	{
		Matrix result=new Matrix(m1.getM(), m1.getN());
		Double[][] t_result=new Double[m1.getM()][m1.getN()];
		for(int i=0; i<m1.getM();i++)
		{
			for (int j=0; j<m1.getN();j++)
			{
				t_result[i][j]=m1.getT()[i][j]+m2.getT()[i][j];
				result.setT(t_result);
			}
		}
		return result;
	}

}
