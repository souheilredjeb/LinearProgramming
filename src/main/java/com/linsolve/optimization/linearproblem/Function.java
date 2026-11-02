package com.linsolve.optimization.linearproblem;

import java.util.HashMap;

public class Function {
	
	private HashMap<Integer,Double> function;
	
	public Function(HashMap<Integer,Double> function)
	{
		this.function=function;
	}
	
	

	public HashMap<Integer, Double> getFunction() {
		return function;
	}



	public void setFunction(HashMap<Integer, Double> function) {
		this.function = function;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
