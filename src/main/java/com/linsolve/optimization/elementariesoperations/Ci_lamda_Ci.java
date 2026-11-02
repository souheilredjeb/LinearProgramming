package com.linsolve.optimization.elementariesoperations;

import com.linsolve.optimization.algebraiccomponents.Identity;
import com.linsolve.optimization.algebraiccomponents.Matrix;
import com.linsolve.optimization.algebraiccomponents.Eij;
import com.linsolve.optimization.algebraiccomponents.SquareMatrix;
import com.linsolve.optimization.basicoperations.AddingMatrices;
import com.linsolve.optimization.basicoperations.MultiplyingMatrices;
import com.linsolve.optimization.basicoperations.MultiplyingMatrixByScalar;
import com.linsolve.optimization.components.helper.GeneratingRandomMatrix;

public class Ci_lamda_Ci extends SquareMatrix 
{
	private Integer i;
	private Integer n;
	private Eij eii;
	private Identity id;
	private Double lamda;
	
	public Ci_lamda_Ci(Integer n, Double lamda,Integer i)
	{
		super(n);
		this.i=i;
		this.eii=new Eij(i,i,n,n);
		this.id= new Identity(n);
		this.n=n;
		this.m=n;
		this.lamda=lamda;
		MultiplyingMatrixByScalar.multiplyByScalar(this.eii, -1.0);
		MultiplyingMatrixByScalar.multiplyByScalar(this.eii, lamda);
		AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eii, -1.0), MultiplyingMatrixByScalar.multiplyByScalar(this.eii, lamda));
		AddingMatrices.add(AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eii, -1.0), MultiplyingMatrixByScalar.multiplyByScalar(this.eii, lamda)),this.id);
		this.setT(AddingMatrices.add(AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eii, -1.0), MultiplyingMatrixByScalar.multiplyByScalar(this.eii, lamda)),this.id).getT());	
	}
	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public Eij getEii() {
		return eii;
	}

	public void setEii(Eij eii) {
		this.eii = eii;
	}

	public Identity getId() {
		return id;
	}

	public void setId(Identity id) {
		this.id = id;
	}

	public Double getLamda() {
		return lamda;
	}

	public void setLamda(Double lamda) {
		this.lamda = lamda;
	}
	
	public static void main(String[] args) {
		Ci_lamda_Ci ci_lamda_ci = new Ci_lamda_Ci(5, 2.0, 1);
		Matrix m0=GeneratingRandomMatrix.supplyRandomMatrix(5,5);
		Matrix.displayMatrix(m0);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		Matrix.displayMatrix(MultiplyingMatrices.multiplyMatrix(m0,ci_lamda_ci));
		
	}

}
