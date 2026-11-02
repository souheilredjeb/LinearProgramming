package com.linsolve.optimization.linearproblem;

public class ObjectiveFunction {
	
	private ExtremumEnum extremum;
	private Function function;
	
	

	public ObjectiveFunction(ExtremumEnum extremum, Function function) {
		super();
		this.extremum = extremum;
		this.function = function;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public ExtremumEnum getExtremum() {
		return extremum;
	}

	public void setExtremum(ExtremumEnum extremum) {
		this.extremum = extremum;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
	
	

}
