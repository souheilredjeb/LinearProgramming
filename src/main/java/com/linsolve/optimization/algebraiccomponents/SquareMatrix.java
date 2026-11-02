package com.linsolve.optimization.algebraiccomponents;

public class SquareMatrix extends Matrix 
{
	
	protected Integer n;
	
	public SquareMatrix() 
	{
		;
	}

	public SquareMatrix(Integer n) {
		super(n,n);
		this.n = n;
		this.m=n;
		this.t=new Double[n][n];
		for(int i=0;i<this.t.length;i++)
		{
			for(int j=0;j<this.t[0].length;j++)
			{
				t[i][j]=0.0;
			}
		}
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}
	
}
