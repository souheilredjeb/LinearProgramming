package com.linsolve.optimization.algebraiccomponents;

public class Kronecker 
{
	private Integer i;	
	private Integer j;
	private Double kroneckerij;
	
	public Kronecker() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Kronecker(Integer i, Integer j) {
		super();
		this.i = i;
		this.j = j;
		this.kroneckerij=0.0;
	}
	
	public void applyKronecker(int k, int l)
	{
		if(i==k && j==l)
		{
			this.kroneckerij=1.0;
		}
		else
		{
			this.kroneckerij=0.0;
		}
	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	public Integer getJ() {
		return j;
	}

	public void setJ(Integer j) {
		this.j = j;
	}

	public Double getKroneckerij() {
		return kroneckerij;
	}

	public void setKroneckerij(Double kroneckerij) {
		this.kroneckerij = kroneckerij;
	}
}
