package com.linsolve.optimization.linearproblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;

public class LinearProblem {
	
	private ObjectiveFunction objective_function;
	private ArrayList<Constraint> constraints;
	
	
	

	public LinearProblem(ObjectiveFunction objective_function, ArrayList<Constraint> constraints) {
		super();
		this.objective_function = objective_function;
		this.constraints = constraints;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExtremumEnum extremum=ExtremumEnum.MAXIMIZE;
		HashMap<Integer, Double> f=new HashMap<Integer, Double>();
		f.put(0, 7.0);
		f.put(1, 4.0);
		Function function = new Function(f);
		ObjectiveFunction objective_function= new ObjectiveFunction(extremum, function);
		HashMap<Integer, Double> fc= new HashMap<Integer,Double>();
		fc.put(0, 0.0);
		fc.put(1, 1.0);
		Function func_constraint=new Function(fc);
		ComparingOperator cop1=ComparingOperator.LESSERTHAN;
		Constraint c0= new Constraint(0, func_constraint, cop1,20.0);
		fc=new HashMap<Integer,Double>();
		fc.put(0, 1.0);
		fc.put(1, 3.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		Constraint c1= new Constraint(1,func_constraint,cop1,50.0);
		fc=new HashMap<Integer,Double>();
		fc.put(0, 4.0);
		fc.put(1, 2.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		Constraint c2= new Constraint(2,func_constraint,cop1,60.0);
		fc=new HashMap<Integer,Double>();
		fc.put(0, 1.0);
		fc.put(1, 0.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		Constraint c3= new Constraint(3,func_constraint,cop1,25.0);
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		constraints.add(c0);
		constraints.add(c1);
		constraints.add(c2);
		constraints.add(c3);
		LinearProblem lp0= new LinearProblem(objective_function, constraints);
		lp0.printLinearProblem();
		
	}
	
	public void printObjectiveFunction()
	{
		System.out.println(" Extremum "+this.getObjective_function().getExtremum().getLibelle());
		Set<Map.Entry<Integer, Double>> set = this.objective_function.getFunction().getFunction().entrySet();
				
		 // Get an iterator
		Iterator<Map.Entry<Integer, Double>> i = set.iterator();
		 // Display elements
		 while(i.hasNext()) {
		 Map.Entry<Integer, Double> me = (Map.Entry<Integer, Double>)i.next();
		 System.out.print("Index Var : "+me.getKey()+ "  ");
		 System.out.println("Var Value : "+me.getValue());
		 }
		 System.out.println();
	}
	
	public void printConstraint(Constraint c)
	{
		System.out.println();
		System.out.println(" Constraint Index : "+c.getIndex());
		System.out.println(" Constraint Function : ");
		Set<Map.Entry<Integer, Double>> set = c.getFunction().getFunction().entrySet();
		Iterator<Map.Entry<Integer, Double>> i = set.iterator();
		while(i.hasNext())
		{
			 Map.Entry<Integer, Double> me = (Map.Entry<Integer, Double>)i.next();
			 System.out.print("Index Var : "+me.getKey()+ "  ");
			 System.out.println("Var Value : "+me.getValue());
		}
		System.out.println(" B component : ");
		System.out.println(c.getB());
		System.out.println();
	}
	
	public void printConstraints()
	{
		for(Constraint c: this.getConstraints())
		{
			this.printConstraint(c);
		}
	}
	
	public void printLinearProblem()
	{
		this.printObjectiveFunction();
		this.printConstraints();
	}
	
	

	public ObjectiveFunction getObjective_function() {
		return objective_function;
	}

	public void setObjective_function(ObjectiveFunction objective_function) {
		this.objective_function = objective_function;
	}

	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}
	
	

}
