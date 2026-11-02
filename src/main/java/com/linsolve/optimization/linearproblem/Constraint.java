package com.linsolve.optimization.linearproblem;

public class Constraint {
	
	private Integer index;
	
	private Function function;
	
	private ComparingOperator operator;
	
	private Double B;

	public Constraint(Integer index, Function function, ComparingOperator operator, Double B) {
		super();
		this.index = index;
		this.function = function;
		this.operator = operator;
		this.B=B;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public ComparingOperator getOperator() {
		return operator;
	}

	public void setOperator(ComparingOperator operator) {
		this.operator = operator;
	}

	public Double getB() {
		return B;
	}

	public void setB(Double b) {
		B = b;
	}
	
	

}
