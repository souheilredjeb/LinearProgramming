package com.linsolve.optimization.elementariesoperations;

import com.linsolve.optimization.algebraiccomponents.Identity;
import com.linsolve.optimization.algebraiccomponents.Matrix;
import com.linsolve.optimization.algebraiccomponents.Eij;
import com.linsolve.optimization.algebraiccomponents.SquareMatrix;
import com.linsolve.optimization.basicoperations.AddingMatrices;
import com.linsolve.optimization.basicoperations.MultiplyingMatrices;
import com.linsolve.optimization.basicoperations.MultiplyingMatrixByScalar;
import com.linsolve.optimization.components.helper.GeneratingRandomMatrix;


public class Li_swap_Lj extends SquareMatrix
{
	private Integer i;
	private Integer j;
	private Integer m;
	private Eij eij;
	private Eij eji;
	private Eij eii;
	private Eij ejj;
	private Identity id;
	
	public Li_swap_Lj(Integer m,Integer i, Integer j)
	{
		super(m);
		this.i=i;
		this.j=j;
		this.m=m;
		this.eij=new Eij(i,j,m,m);
		this.eji=new Eij(j,i,m,m);
		this.eii=new Eij(i,i,m,m);
		this.ejj=new Eij(j,j,m,m);
		this.id= new Identity(m);	
		AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eii, -1.0),MultiplyingMatrixByScalar.multiplyByScalar(this.ejj, -1.0));
		AddingMatrices.add(eji, eij);
		AddingMatrices.add(AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eii, -1.0),MultiplyingMatrixByScalar.multiplyByScalar(this.ejj, -1.0)), AddingMatrices.add(eji, eij));
		this.setT(AddingMatrices.add(this.id,AddingMatrices.add(AddingMatrices.add(MultiplyingMatrixByScalar.multiplyByScalar(this.eii, -1.0),MultiplyingMatrixByScalar.multiplyByScalar(this.ejj, -1.0)), AddingMatrices.add(eji, eij))).getT());
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

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public Eij getEij() {
		return eij;
	}

	public void setEij(Eij eij) {
		this.eij = eij;
	}

	public Eij getEji() {
		return eji;
	}

	public void setEji(Eij eji) {
		this.eji = eji;
	}

	public Eij getEii() {
		return eii;
	}

	public void setEii(Eij eii) {
		this.eii = eii;
	}

	public Eij getEjj() {
		return ejj;
	}

	public void setEjj(Eij ejj) {
		this.ejj = ejj;
	}

	public Identity getId() {
		return id;
	}

	public void setId(Identity id) {
		this.id = id;
	}
	
	public static void main(String[] args) {
		Matrix m0=GeneratingRandomMatrix.supplyRandomMatrix(5,5);
		Matrix.displayMatrix(m0);
	    System.out.println();
	    System.out.println();
	    System.out.println();
	   
	    
		Li_swap_Lj swap= new Li_swap_Lj(5,2, 3);

		
		Matrix.displayMatrix(MultiplyingMatrices.multiplyMatrix(swap, m0));
		System.out.println();
		System.out.println();
		System.out.println();
		
		Matrix m1=GeneratingRandomMatrix.supplyRandomMatrix(4,5);
		Matrix.displayMatrix(m1);
		System.out.println();
		System.out.println();
		System.out.println();
		Li_swap_Lj swap1= new Li_swap_Lj(4,1, 3);
		Matrix.displayMatrix(MultiplyingMatrices.multiplyMatrix(swap1, m1));
		 
	}

}
