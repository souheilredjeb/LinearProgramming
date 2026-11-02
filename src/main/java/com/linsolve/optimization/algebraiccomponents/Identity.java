package com.linsolve.optimization.algebraiccomponents;



public class Identity extends SquareMatrix {

	private Integer n;
	private Kronecker kroneckerij;
	
	public Identity(Integer n) 
	{
		super(n);
		this.n = n;
		for(int i=0; i<this.t.length;i++)
		{	
			for(int j=0;j<this.t[0].length;j++)
			{
				this.kroneckerij=new Kronecker(i, i);
				this.kroneckerij.applyKronecker(i, j);
				this.t[i][j]=this.kroneckerij.getKroneckerij(); 
			}
		}
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public Kronecker getKroneckerij() {
		return kroneckerij;
	}

	public void setKroneckerij(Kronecker kroneckerij) {
		this.kroneckerij = kroneckerij;
	}

	public static void main(String[] args) {
		Identity id = new Identity(5);
		Matrix.displayMatrix(id);

	}

}
