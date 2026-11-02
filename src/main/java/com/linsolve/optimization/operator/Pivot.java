package com.linsolve.optimization.operator;

public class Pivot {
	
	private Integer pivot_line;
	private Integer pivot_column;
	private Double pivot;
	
	
	

	public Pivot(Integer pivot_line, Integer pivot_column, Double pivot) {
		super();
		this.pivot_line = pivot_line;
		this.pivot_column = pivot_column;
		this.pivot = pivot;
	}

	


	public Pivot(Integer pivot_line, Integer pivot_column) {
		super();
		this.pivot_line = pivot_line;
		this.pivot_column = pivot_column;
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




	public Double getPivot() {
		return pivot;
	}




	public void setPivot(Double pivot) {
		this.pivot = pivot;
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
