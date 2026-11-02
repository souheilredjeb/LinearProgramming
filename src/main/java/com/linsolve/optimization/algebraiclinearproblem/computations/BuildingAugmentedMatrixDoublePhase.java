package com.linsolve.optimization.algebraiclinearproblem.computations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.linsolve.optimization.algebraiccomponents.IntVector;
import com.linsolve.optimization.algebraiccomponents.Matrix;
import com.linsolve.optimization.algebraiccomponents.Vector;
import com.linsolve.optimization.linearproblem.ComparingOperator;
import com.linsolve.optimization.linearproblem.Constraint;
import com.linsolve.optimization.linearproblem.ExtremumEnum;
import com.linsolve.optimization.linearproblem.Function;
import com.linsolve.optimization.linearproblem.LinearProblem;
import com.linsolve.optimization.linearproblem.ObjectiveFunction;

public class BuildingAugmentedMatrixDoublePhase {

	private LinearProblem lp;
	private SolvingAuxiliaryProblemSimplexDoublePhase sapsdp;
	private Matrix augmented_matrix_phase_II;
	private Vector c_extended_II;
	private IntVector base_phase_II;
	private Vector c_base_phase_II;
	private Vector zj_cj_phase_II;
	
	
	public BuildingAugmentedMatrixDoublePhase(LinearProblem lp) throws Exception {
		super();
		this.lp = lp;
		this.sapsdp= new SolvingAuxiliaryProblemSimplexDoublePhase(lp);
		this.sapsdp.apply_simplex_single_phase();
	    Integer augmented_n =this.sapsdp.getBamaux().getAugmented_n();
        Integer size= this.sapsdp.getBamaux().getSize();
        ArrayList<Integer> indice=this.sapsdp.getBamaux().getIndice();

		Double[][] augmented_matrix_II_array= new Double[this.sapsdp.getBamaux().getBic().getB_vector().getT().length][size];
		Integer negative_mult=this.sapsdp.getBamaux().getNegative_mult();
		this.sapsdp.getBamaux().getAuxiliary_problem_matrix().getT();
		this.sapsdp.getBamaux().getBic().getInitial_matrix().getT();
	    for(int i=0;i<this.sapsdp.getBamaux().getBic().getB_vector().getT().length;i++)
		{			
	    	for( int j=0; j<this.sapsdp.getBamaux().getAuxiliary_problem_matrix().getT()[0].length-1;j++)
			{
	    		if(i<augmented_matrix_II_array.length && j<augmented_matrix_II_array[0].length &&!indice.contains(j))
				{
	    			augmented_matrix_II_array[i][j]=this.sapsdp.getBamaux().getAuxiliary_problem_matrix().getT()[i][j];
				}
	    		else if(i<augmented_matrix_II_array.length && j<augmented_matrix_II_array[0].length && j+negative_mult<this.sapsdp.getBamaux().getAuxiliary_problem_matrix().getT()[0].length)
	    		{
	    			augmented_matrix_II_array[i][j]=this.sapsdp.getBamaux().getAuxiliary_problem_matrix().getT()[i][j+negative_mult];
	    		}
			}
	    	augmented_matrix_II_array[i][size-1]=this.sapsdp.getBamaux().getAuxiliary_problem_matrix().getT()[i][this.sapsdp.getBamaux().getAuxiliary_problem_matrix().getT()[0].length-1];
		}
	    Double[] c_extended_II_array=new Double[augmented_matrix_II_array[0].length-1];  
	    for(int j=0;j<c_extended_II_array.length;j++)
		{
			if(j<this.sapsdp.getBamaux().getBic().getC_vector().getT().length)
			{
				c_extended_II_array[j]=this.sapsdp.getBamaux().getBic().getC_vector().getT()[j];
			}
			else
			{
				c_extended_II_array[j]=0.0;
			}
		}	
	    
	    this.c_extended_II=new Vector(c_extended_II_array);      
	    Double[] zj_cj_phase_II_array=new Double[augmented_matrix_II_array[0].length-1];
	    
	    this.base_phase_II=this.sapsdp.getBamaux().getBase(); 
	    Integer[] base_array=new Integer[this.sapsdp.getBamaux().getBase().getInt_vect().length];
	    for(int i=0;i<base_array.length;i++)
	    {
	    	if(this.sapsdp.getBamaux().getBase().getInt_vect()[i]>(this.sapsdp.getBamaux().getBic().getC_vector().getT().length-1))
	    	{
	    		base_array[i]=this.sapsdp.getBamaux().getBase().getInt_vect()[i]-negative_mult;
	    	}
	    	else
	    	{
	    		base_array[i]=this.sapsdp.getBamaux().getBase().getInt_vect()[i];
	    	}	
	    }
	    this.base_phase_II.setInt_vect(base_array);
	    
	    
	    
	    
	    
	    
	    
	    Double[] c_base_array=new Double[this.sapsdp.getBamaux().getBic().getB_vector().getT().length];
	    
	    for(int i=0;i<c_base_array.length;i++)
	    {	 
	    	  c_base_array[i]=c_extended_II_array[this.sapsdp.getBamaux().getBase().getInt_vect()[i]];
	  
	    }  
	    this.c_base_phase_II=new Vector(c_base_array);
	    Arrays.fill(zj_cj_phase_II_array, 0.0);
	   for (int j = 0; j < zj_cj_phase_II_array.length; j++)
	    {
	        for (int i = 0; i < c_base_array.length; i++) {
	        	zj_cj_phase_II_array[j] += c_base_array[i] * augmented_matrix_II_array[i][j];
	        }
	        zj_cj_phase_II_array[j] -= c_extended_II_array[j];
	    }
		
	   	System.out.println();
	   	System.out.println();
		this.augmented_matrix_phase_II=new Matrix(augmented_matrix_II_array.length, augmented_matrix_II_array[0].length , augmented_matrix_II_array);
		
		
		this.zj_cj_phase_II=new Vector(zj_cj_phase_II_array);
	}


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ExtremumEnum extremum=ExtremumEnum.MINIMIZE;
		HashMap<Integer, Double> f=new HashMap<Integer, Double>();
		f.put(0, 1.0);
		f.put(1, 1.0);
		Function function = new Function(f);
		ObjectiveFunction objective_function= new ObjectiveFunction(extremum, function);
		HashMap<Integer, Double> fc= new HashMap<Integer,Double>();
		fc.put(0, 7.0);
		fc.put(1, 1.0);
		Function func_constraint=new Function(fc);
		ComparingOperator cop1=ComparingOperator.LESSERTHAN;
		Constraint c0= new Constraint(0, func_constraint, cop1,-7.0);
		fc=new HashMap<Integer,Double>();
		fc.put(0, 1.0);
		fc.put(1, 2.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		Constraint c1= new Constraint(1,func_constraint,cop1,-4.0);
		
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		constraints.add(c0);
		constraints.add(c1);
		LinearProblem lp0= new LinearProblem(objective_function, constraints);
		
		extremum=ExtremumEnum.MAXIMIZE;
	    f=new HashMap<Integer, Double>();
		f.put(0, 15.0);
		f.put(1, 20.0);
		function = new Function(f);
		
		objective_function= new ObjectiveFunction(extremum, function);
		fc= new HashMap<Integer,Double>();
		fc.put(0, 1.0);
		fc.put(1, 2.0);
	    func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		c0= new Constraint(0, func_constraint, cop1,-10.0);
		
		fc=new HashMap<Integer,Double>();
		fc.put(0, 1.0);
		fc.put(1, 1.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		Constraint c2= new Constraint(1,func_constraint,cop1,-6.0);
		fc=new HashMap<Integer,Double>();
		fc.put(0, 2.0);
		fc.put(1,-3.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		c1= new Constraint(2,func_constraint,cop1,6.0);
		constraints = new ArrayList<Constraint>();
		constraints.add(c2);
		constraints.add(c0);
		constraints.add(c1);
		LinearProblem lp1= new LinearProblem(objective_function, constraints);
		
		String input6="Maximize Z=15x1+20x2"
				+"subject to"
				+"x1+2x2>=10,"
				+"2x1-3x2<=6,"
				+"x1+x2>=6,"			
				+"non negative constraints";
		
		
		
		
		
		
		
		
		BuildingAugmentedMatrixDoublePhase bamdp=new BuildingAugmentedMatrixDoublePhase(lp1);
		//bamdp.getSapsdp().getBamaux().getBase()
		
		

	}


	public LinearProblem getLp() {
		return lp;
	}


	public void setLp(LinearProblem lp) {
		this.lp = lp;
	}


	public SolvingAuxiliaryProblemSimplexDoublePhase getSapsdp() {
		return sapsdp;
	}


	public void setSapsdp(SolvingAuxiliaryProblemSimplexDoublePhase sapsdp) {
		this.sapsdp = sapsdp;
	}


	public Matrix getAugmented_matrix_phase_II() {
		return augmented_matrix_phase_II;
	}


	public void setAugmented_matrix_phase_II(Matrix augmented_matrix_phase_II) {
		this.augmented_matrix_phase_II = augmented_matrix_phase_II;
	}


	public Vector getC_extended_II() {
		return c_extended_II;
	}


	public void setC_extended_II(Vector c_extended_II) {
		this.c_extended_II = c_extended_II;
	}




	public Vector getZj_cj_phase_II() {
		return zj_cj_phase_II;
	}


	public void setZj_cj_phase_II(Vector zj_cj_phase_II) {
		this.zj_cj_phase_II = zj_cj_phase_II;
	}


	public IntVector getBase_phase_II() {
		return base_phase_II;
	}


	public void setBase_phase_II(IntVector base_phase_II) {
		this.base_phase_II = base_phase_II;
	}


	public Vector getC_base_phase_II() {
		return c_base_phase_II;
	}


	public void setC_base_phase_II(Vector c_base_phase_II) {
		this.c_base_phase_II = c_base_phase_II;
	}
	
	
}
