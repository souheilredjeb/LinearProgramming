package com.linsolve.optimization.operator;

import com.linsolve.optimization.algebraiccomponents.Matrix;
import com.linsolve.optimization.basicoperations.MultiplyingMatrices;
import com.linsolve.optimization.elementariesoperations.Li_lamda_Li;
import com.linsolve.optimization.elementariesoperations.Li_plus_lamda_Lj;

public class ApplyingGaussPivot {
	

	private Matrix matrix;
	private Pivot pivot;
	private Integer pivot_line;
	private Integer pivot_column;
	private Double pivot_value;
	private Integer m;
	private Integer n;
	private Matrix matrix_reg;
	
	public ApplyingGaussPivot(Matrix matrix, Pivot pivot) {
		super();
		this.matrix = matrix;
		this.pivot = pivot;
		this.m=matrix.getM();
		this.n=matrix.getN();
		this.pivot_column=pivot.getPivot_column();
		this.pivot_line=pivot.getPivot_line();
		this.pivot_value=pivot.getPivot();	
		this.matrix_reg=new Matrix(m, n);
	}
	
	public void apply()
	{
		Li_lamda_Li li_lamda_li= new Li_lamda_Li(this.m, 1.0/this.pivot_value, this.pivot_line);
		this.matrix=MultiplyingMatrices.multiplyMatrix(li_lamda_li, this.matrix);
		Li_plus_lamda_Lj li_plus_lamda_lj=null;
		li_plus_lamda_lj=new Li_plus_lamda_Lj(m, -this.matrix.getT()[1][pivot_column],1, 0);	
		for(int i=0;i<this.m;i++)
		{
			if(i!=this.pivot_line)
			{
				li_plus_lamda_lj=new Li_plus_lamda_Lj(m, -this.matrix.getT()[i][pivot_column], i, pivot_line);
				this.matrix=MultiplyingMatrices.multiplyMatrix(li_plus_lamda_lj, this.matrix);
			}
		}	
	}





	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double[][] array_matrix={{1.0,0.0,1.0,0.0,0.0,6.0},{0.25,1.0,0.0,1.0,0.0,6.0},{3.0,2.0,0.0,0.0,1.0,22.0}};
		Matrix m= new Matrix(3, 6, array_matrix);
		System.out.println();
		System.out.println();
		System.out.println();
		Matrix.displayMatrix(m);
		Pivot p=new Pivot(0, 0, 1.0);
		ApplyingGaussPivot  agp=new ApplyingGaussPivot(m, p);
		agp.apply();
		p=new Pivot(1, 1, agp.getMatrix().getT()[1][1]);
		agp=new ApplyingGaussPivot(agp.getMatrix(), p);
		agp.apply();
		p=new Pivot(2, 2, agp.getMatrix().getT()[2][2]);
		agp=new ApplyingGaussPivot(agp.getMatrix(), p);
		agp.apply();

	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

	public Pivot getPivot() {
		return pivot;
	}

	public void setPivot(Pivot pivot) {
		this.pivot = pivot;
	}

	public Integer getPivot_line() {
		return pivot_line;
	}

	public void setPivot_line(Integer pivot_line) {
		this.pivot_line = pivot_line;
	}

	public Integer getPivot_column() {
		return pivot_column;
	}

	public void setPivot_column(Integer pivot_column) {
		this.pivot_column = pivot_column;
	}

	public Double getPivot_value() {
		return pivot_value;
	}

	public void setPivot_value(Double pivot_value) {
		this.pivot_value = pivot_value;
	}

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public Matrix getMatrix_reg() {
		return matrix_reg;
	}

	public void setMatrix_reg(Matrix matrix_reg) {
		this.matrix_reg = matrix_reg;
	}
	
	
	

	

}
