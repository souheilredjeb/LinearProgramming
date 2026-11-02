package com.linsolve.optimization.algebraiclinearproblem;

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

public class BuildingAugmentedMatrixAuxiliaryProblem 
{
	
	private LinearProblem lp;
	private BuildingInitialComponents bic;
	private Matrix auxiliary_problem_matrix;
	private Matrix augmented_matrix_II;
	private IntVector base;
	private Vector c_base;
	private Vector c_extended_I;
	private Vector zj_cj;
	private int augmented_n=0;
	private Integer negative_mult;
	private Integer positive_mult;
	private Integer II_phase_I_phase;
	private Integer size;
	private ArrayList<Integer> indice;
	private Vector c_extended_II;
	

	
	
	public BuildingAugmentedMatrixAuxiliaryProblem(LinearProblem lp) {
		super();
		this.lp = lp;
		this.bic=new BuildingInitialComponents(lp);
		this.positive_mult=0;
		this.negative_mult=0;
		this.augmented_n=this.bic.getC_vector().getT().length;
	
		for(int i=0;i<this.getBic().getB_vector().getT().length;i++)
		{	
			if(this.getBic().getB_vector().getT()[i]<0 || (this.getBic().getB_vector().getT()[i]==0 && this.lp.getConstraints().get(i).getOperator().equals(ComparingOperator.GREATERTHAN)))
			{
				negative_mult++;
				augmented_n+=2;
			}
			else if (this.getBic().getB_vector().getT()[i]>0 || (this.getBic().getB_vector().getT()[i]==0 && this.lp.getConstraints().get(i).getOperator().equals(ComparingOperator.LESSERTHAN)))
			{
				augmented_n+=1;
				positive_mult++;
			}
		}
		
		Integer[]  base_array=new Integer[this.getBic().getB_vector().getT().length];
		Double[]  c_base_array=new Double[this.getBic().getB_vector().getT().length];
		 
		Double[][] augmented_matrix_array =new Double[this.getBic().getB_vector().getT().length][augmented_n+1];

		Double[]   c_extended_I_array =new Double[augmented_matrix_array[0].length-1];
		for(int i=0;i<this.getBic().getB_vector().getT().length;i++)
		{
			for(int j=0;j<this.getBic().getInitial_matrix().getT()[0].length;j++)
			{
				augmented_matrix_array[i][j]=this.getBic().getInitial_matrix().getT()[i][j];
				c_extended_I_array[j]=0.0;
			}
			for(int j=this.getBic().getInitial_matrix().getT()[0].length;j<this.getBic().getInitial_matrix().getT()[0].length+this.negative_mult+this.positive_mult;j++)
			{		
					if(this.getBic().getB_vector().getT()[i]<0.0 || (this.getBic().getB_vector().getT()[i]==0 && this.lp.getConstraints().get(i).getOperator().equals(ComparingOperator.GREATERTHAN)))
					{
						
						if(j==this.getBic().getInitial_matrix().getT()[0].length+i)
						{
							  augmented_matrix_array[i][j]=-1.0;
							  base_array[i]=j+negative_mult;
							  augmented_matrix_array[i][j+negative_mult]=1.0;
							  c_extended_I_array[j+negative_mult]=1.0;
						}
					
					}
					else if(this.getBic().getB_vector().getT()[i]>0 ||(this.getBic().getB_vector().getT()[i]==0 && this.lp.getConstraints().get(i).getOperator().equals(ComparingOperator.LESSERTHAN)))
					{	
						if(j==this.getBic().getInitial_matrix().getT()[0].length+i)
						{
							augmented_matrix_array[i][j+this.negative_mult]=1.0;
						    base_array[i]=j+this.negative_mult;
						}	
					}	
				}
			
		}
		for(int i=0;i<base_array.length;i++)
		{
			System.out.print(base_array[i] + "||");
			
		}
		
		
	    for(int i=0; i<c_base_array.length;i++)
		{
			c_base_array[i]=c_extended_I_array[base_array[i]];
		}
		for(int i=0; i<c_base_array.length;i++)
		{
			if(c_base_array[i]==null)
			  c_base_array[i]=0.0;
		}
		for(int i=0;i<this.getBic().getB_vector().getT().length;i++)
		{
			for(int j=0;j<augmented_matrix_array[0].length-1;j++)
			{	
				if(augmented_matrix_array[i][j]==null)
				{
					augmented_matrix_array[i][j]=0.0;
				}	
			}
		}
		for(int i=0;i<c_extended_I_array.length;i++)
		{
			if(c_extended_I_array[i]==null)
			{
				c_extended_I_array[i]=0.0;
			}			
		}
		for(int i=0;i<this.getBic().getB_vector().getT().length;i++)
		{
				if(this.getBic().getB_vector().getT()[i]<0)
				{
					augmented_matrix_array[i][augmented_matrix_array[0].length-1]=-this.getBic().getB_vector().getT()[i];
							
				}
				else
				{
					augmented_matrix_array[i][augmented_matrix_array[0].length-1]=this.getBic().getB_vector().getT()[i];
				}	
		}
		
		this.indice=new ArrayList<Integer>();
	    this.size=augmented_matrix_array[0].length;
	    for(int i=0;i<this.getBic().getB_vector().getT().length;i++)
		{	
			for( int j=0; j<augmented_matrix_array[0].length;j++)
			{
					if(j>(this.getBic().getInitial_matrix().getT()[0].length-1) && augmented_matrix_array[i][j].equals(-1.0) && augmented_matrix_array[i][j+this.negative_mult].equals(1.0))
					{
						size--;	
					}
			}	
		}	
		
		Double[][] augmented_matrix_II_array= new Double[this.getBic().getB_vector().getT().length][size];
	    for(int i=0;i<this.getBic().getB_vector().getT().length;i++)
		{			
	    	for( int j=0; j<augmented_matrix_array[0].length;j++)
			{		
	    		if(j>(this.getBic().getInitial_matrix().getT()[0].length-1) && (augmented_matrix_array[i][j].equals(-1.0)) && ((j+this.negative_mult)<augmented_matrix_array[0].length)&&( augmented_matrix_array[i][j+this.negative_mult].equals(1.0)))
				{
					indice.add(j+this.negative_mult);
				}				
			}
	    	for( int j=0; j<augmented_matrix_array[0].length-1;j++)
			{
	    		if(i<augmented_matrix_II_array.length && j<augmented_matrix_II_array[0].length &&!indice.contains(j))
				{
	    			augmented_matrix_II_array[i][j]=augmented_matrix_array[i][j];
				}
	    		else if(i<augmented_matrix_II_array.length && j<augmented_matrix_II_array[0].length && j+this.negative_mult<augmented_matrix_array[0].length)
	    		{
	    			augmented_matrix_II_array[i][j]=augmented_matrix_array[i][j+this.negative_mult];
	    		}
			}
	    	augmented_matrix_II_array[i][size-1]=augmented_matrix_array[i][augmented_matrix_array[0].length-1];
		}
	    Double[] c_extended_II_array=new Double[augmented_n-negative_mult];

	    for(int j=0;j<c_extended_II_array.length;j++)
		{
			if(j<this.getBic().getC_vector().getT().length)
			{
				c_extended_II_array[j]=this.getBic().getC_vector().getT()[j];
			}
			else
			{
				c_extended_II_array[j]=0.0;
			}
		}	
		Double[] zj_cj_array=new Double[augmented_n];
	    Arrays.fill(zj_cj_array, 0.0);
	    for (int j = 0; j < c_extended_I_array.length; j++)
	    {
	        for (int i = 0; i < c_base_array.length; i++) {
	        	zj_cj_array[j] += c_base_array[i] * augmented_matrix_array[i][j];
	        }
	        zj_cj_array[j] -= c_extended_I_array[j];
	    }	
		this.base= new IntVector(base_array);
		this.c_base=new Vector(c_base_array);
	    this.auxiliary_problem_matrix=new Matrix(augmented_matrix_array.length,augmented_matrix_array[0].length , augmented_matrix_array);
	    this.augmented_matrix_II=new Matrix(augmented_matrix_II_array.length,augmented_matrix_II_array[0].length, augmented_matrix_II_array);
	    this.c_extended_I=new Vector(c_extended_I_array);
	    this.c_extended_II=new Vector(c_extended_II_array);
	    this.zj_cj= new Vector(zj_cj_array);	;
		this.indice=new ArrayList<Integer>();
		this.size=this.auxiliary_problem_matrix.getT()[0].length;
		for(int i=0;i<this.getBic().getB_vector().getT().length;i++)
		{	
				for( int j=0; j<this.auxiliary_problem_matrix.getT()[0].length;j++)
				{
						if(j>(this.getBic().getInitial_matrix().getT()[0].length-1) && this.auxiliary_problem_matrix.getT()[i][j].equals(-1.0)	&& this.auxiliary_problem_matrix.getT()[i][j+this.negative_mult].equals(1.0))
						{
							size--;	
						}
				}	
		}	
		for(int i=0;i<this.getBic().getB_vector().getT().length;i++)
		{			
	    	for( int j=0; j<this.auxiliary_problem_matrix.getT()[0].length;j++)
			{		
	    		if(j>(this.bic.getInitial_matrix().getT()[0].length-1) && (this.auxiliary_problem_matrix.getT()[i][j].equals(-1.0)) && ((j+this.negative_mult)<this.auxiliary_problem_matrix.getT()[0].length)&&( this.auxiliary_problem_matrix.getT()[i][j+this.negative_mult].equals(1.0)))
				{
					indice.add(j+this.negative_mult);
				}				
			}
		}
		
		
		
		

	}

    

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String input66="Maximize Z=15x1+20x2"
				+"subject to"
				+"x1+2x2>=10,"
				+"x1+x2>=6,"
				+"2x1-3x2<=6,"	
				+"non negative constraints";
		
		
		
		String input6="Maximize Z=15x1+20x2"
				+"subject to"
				+"2x1-3x2<=6,"
				+"x1+2x2>=10,"
				+"x1+x2>=6,"
				+"non negative constraints";
		
		String input666="Maximize Z=15x1+20x2"
				+"subject to"
				+"x1+2x2>=10,"
				+"x1+x2>=6,"
				+"2x1-3x2<=6,"	
				+"non negative constraints";
		
		
		String input_test1="Maximize Z=15x1+20x2"
				+"subject to"
				+"x1+2x2>=10,"
				+"2x1-3x2<=6,"
				+"x1+x2>=6,"			
				+"non negative constraints";
		/*ExtremumEnum extremum=ExtremumEnum.MAXIMIZE;
		HashMap<Integer, Double> f=new HashMap<Integer, Double>();
		f.put(0, 15.0);
		f.put(1, 20.0);
		Function function = new Function(f);
		ObjectiveFunction objective_function= new ObjectiveFunction(extremum, function);
		HashMap<Integer, Double> fc= new HashMap<Integer,Double>();
		fc.put(0, -1.0);
		fc.put(1, -2.0);
		Function func_constraint=new Function(fc);
		ComparingOperator cop1=ComparingOperator.LESSERTHAN;
		Constraint c0= new Constraint(0, func_constraint, cop1,-10.0);
		fc=new HashMap<Integer,Double>();
		fc.put(0, 1.0);
		fc.put(1, 1.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		Constraint c1= new Constraint(1,func_constraint,cop1,-6.0);
		fc=new HashMap<Integer,Double>();
		fc.put(0, 2.0);
		fc.put(1, -3.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		Constraint c2= new Constraint(2,func_constraint,cop1,6.0);
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		constraints.add(c0);
		constraints.add(c1);
		constraints.add(c2);
		LinearProblem lp_test1= new LinearProblem(objective_function, constraints);
		
		
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
		fc.put(0, 2.0);
		fc.put(1,-3.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		c1= new Constraint(1,func_constraint,cop1,6.0);
		fc=new HashMap<Integer,Double>();
		fc.put(0, 1.0);
		fc.put(1, 1.0);
		func_constraint=new Function(fc);
		cop1=ComparingOperator.LESSERTHAN;
		c2= new Constraint(2,func_constraint,cop1,-6.0);
		constraints = new ArrayList<Constraint>();
		constraints.add(c0);
		constraints.add(c1);
		constraints.add(c2);
		LinearProblem lp1= new LinearProblem(objective_function, constraints);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		BuildingAugmentedMatrixAuxiliaryProblem baugmatrixauxproblem = new BuildingAugmentedMatrixAuxiliaryProblem(lp_test1);	
		Matrix.displayMatrix(baugmatrixauxproblem.getAuxiliary_problem_matrix());
		System.out.println();
		System.out.println();
		Vector.display_vector_h(baugmatrixauxproblem.getC_extended_I());
		System.out.println();
		System.out.println();
		System.out.println(baugmatrixauxproblem.getIndice());*/
		
		/* ExtremumEnum extremum = ExtremumEnum.MAXIMIZE;
	        HashMap<Integer, Double> objectiveCoeffs = new HashMap<>();
	        objectiveCoeffs.put(0, 113.0);      // x0: Wool slacks
	        objectiveCoeffs.put(1, 210.0);      // x1: Cashmere sweater
	        objectiveCoeffs.put(2, 60.5);       // x2: Silk blouse
	        objectiveCoeffs.put(3, 53.5);       // x3: Silk camisole
	        objectiveCoeffs.put(4, 143.25);     // x4: Tailored skirt
	        objectiveCoeffs.put(5, 155.25);     // x5: Wool blazer
	        objectiveCoeffs.put(6, 139.0);      // x6: Velvet pants
	        objectiveCoeffs.put(7, 66.25);      // x7: Cotton sweater
	        objectiveCoeffs.put(8, 33.75);      // x8: Cotton miniskirt
	        objectiveCoeffs.put(9, 22.0);       // x9: Velvet shirt
	        objectiveCoeffs.put(10, 26.625);    // x10: Button-down blouse
	        
	        Function objectiveFunction = new Function(objectiveCoeffs);
	        ObjectiveFunction objFunc = new ObjectiveFunction(extremum, objectiveFunction);
	        
	        ArrayList<Constraint> constraints = new ArrayList<>();
	 
	        
	        HashMap<Integer, Double> c14 = new HashMap<>();
	        c14.put(0, 1.0);
	        c14.put(1, 0.0);
	        c14.put(2, 0.0);
	        c14.put(3, 0.0);
	        c14.put(4, 0.0);
	        c14.put(5, 0.0);
	        c14.put(6, 0.0);
	        c14.put(7, 0.0);
	        c14.put(8, 0.0);
	        c14.put(9, 0.0);
	        c14.put(10, 0.0);
	        constraints.add(new Constraint(
	            0, 
	            new Function(c14), 
	            ComparingOperator.LESSERTHAN, 
	            -4200.0
	        ));
	        
	        HashMap<Integer, Double> c15 = new HashMap<>();
	        c15.put(0, 0.0);
	        c15.put(1, 0.0);
	        c15.put(2, 0.0);
	        c15.put(3, 0.0);
	        c15.put(4, 0.0);
	        c15.put(5, 1.0);
	        c15.put(6, 0.0);
	        c15.put(7, 0.0);
	        c15.put(8, 0.0);
	        c15.put(9, 0.0);
	        c15.put(10, 0.0);
	        constraints.add(new Constraint(
	            1, 
	            new Function(c15), 
	            ComparingOperator.LESSERTHAN, 
	            -3000.0
	        ));
	        
	        HashMap<Integer, Double> c16 = new HashMap<>();
	        c16.put(0, 0.0);
	        c16.put(1, 0.0);
	        c16.put(2, 0.0);
	        c16.put(3, 0.0);
	        c16.put(4, 1.0);
	        c16.put(5, 0.0);
	        c16.put(6, 0.0);
	        c16.put(7, 0.0);
	        c16.put(8, 0.0);
	        c16.put(9, 0.0);
	        c16.put(10, 0.0);
	        constraints.add(new Constraint(
	            2, 
	            new Function(c16), 
	            ComparingOperator.LESSERTHAN, 
	            -2800.0
	        ));
	        
	        HashMap<Integer, Double> c13 = new HashMap<>();
	        c13.put(0, 0.0);
	        c13.put(1, 0.0);
	        c13.put(2, 0.0);
	        c13.put(3, 1.0);
	        c13.put(4, 0.0);
	        c13.put(5, 0.0);
	        c13.put(6, 0.0);
	        c13.put(7, 0.0);
	        c13.put(8, 0.0);
	        c13.put(9, 0.0);
	        c13.put(10, 0.0);
	        constraints.add(new Constraint(
	            3, 
	            new Function(c13), 
	            ComparingOperator.LESSERTHAN, 
	            15000.0
	        ));
	        
	     // Constraint 12: Silk Blouses: x2 ≤ 12,000
	        HashMap<Integer, Double> c12 = new HashMap<>();
	        c12.put(0, 0.0);
	        c12.put(1, 0.0);
	        c12.put(2, 1.0);
	        c12.put(3, 0.0);
	        c12.put(4, 0.0);
	        c12.put(5, 0.0);
	        c12.put(6, 0.0);
	        c12.put(7, 0.0);
	        c12.put(8, 0.0);
	        c12.put(9, 0.0);
	        c12.put(10, 0.0);
	        constraints.add(new Constraint(
	            4, 
	            new Function(c12), 
	            ComparingOperator.LESSERTHAN, 
	            12000.0
	        ));
	        
	        // Constraint 11: Cashmere Sweaters: x1 ≤ 4,000
	        HashMap<Integer, Double> c11 = new HashMap<>();
	        c11.put(0, 0.0);
	        c11.put(1, 1.0);
	        c11.put(2, 0.0);
	        c11.put(3, 0.0);
	        c11.put(4, 0.0);
	        c11.put(5, 0.0);
	        c11.put(6, 0.0);
	        c11.put(7, 0.0);
	        c11.put(8, 0.0);
	        c11.put(9, 0.0);
	        c11.put(10, 0.0);
	        constraints.add(new Constraint(
	            5, 
	            new Function(c11), 
	            ComparingOperator.LESSERTHAN, 
	            4000.0
	        ));
	        
	        // Constraint 10: Velvet Shirts: x9 ≤ 6,000
	        HashMap<Integer, Double> c10 = new HashMap<>();
	        c10.put(0, 0.0);
	        c10.put(1, 0.0);
	        c10.put(2, 0.0);
	        c10.put(3, 0.0);
	        c10.put(4, 0.0);
	        c10.put(5, 0.0);
	        c10.put(6, 0.0);
	        c10.put(7, 0.0);
	        c10.put(8, 0.0);
	        c10.put(9, 1.0);
	        c10.put(10, 0.0);
	        constraints.add(new Constraint(
	            6, 
	            new Function(c10), 
	            ComparingOperator.LESSERTHAN, 
	            6000.0
	        ));
	        
	     // ----- DEMAND UPPER BOUNDS -----
	        
	        // Constraint 9: Velvet Pants: x6 ≤ 5,500
	        HashMap<Integer, Double> c9 = new HashMap<>();
	        c9.put(0, 0.0);
	        c9.put(1, 0.0);
	        c9.put(2, 0.0);
	        c9.put(3, 0.0);
	        c9.put(4, 0.0);
	        c9.put(5, 0.0);
	        c9.put(6, 1.0);
	        c9.put(7, 0.0);
	        c9.put(8, 0.0);
	        c9.put(9, 0.0);
	        c9.put(10, 0.0);
	        constraints.add(new Constraint(
	            7, 
	            new Function(c9), 
	            ComparingOperator.LESSERTHAN, 
	            5500.0
	        ));
	        
	        // Constraint 8: Cotton Link: 0x0 + 0x1 + 0x2 + 0x3 + 0x4 + 0x5 + 0x6 + 1x7 - 1x8 + 0x9 + 0x10 ≤ 0
	        HashMap<Integer, Double> c8 = new HashMap<>();
	        c8.put(0, 0.0);
	        c8.put(1, 0.0);
	        c8.put(2, 0.0);
	        c8.put(3, 0.0);
	        c8.put(4, 0.0);
	        c8.put(5, 0.0);
	        c8.put(6, 0.0);
	        c8.put(7, 1.0);
	        c8.put(8, -1.0);
	        c8.put(9, 0.0);
	        c8.put(10, 0.0);
	        constraints.add(new Constraint(
	            8, 
	            new Function(c8), 
	            ComparingOperator.LESSERTHAN, 
	            0.0
	        ));
	        
	     // ----- PRODUCTION LINK CONSTRAINTS -----
	        
	        // Constraint 7: Silk Link: 0x0 + 0x1 + 1x2 - 1x3 + 0x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 + 0x10 ≤ 0
	        HashMap<Integer, Double> c7 = new HashMap<>();
	        c7.put(0, 0.0);
	        c7.put(1, 0.0);
	        c7.put(2, 1.0);
	        c7.put(3, -1.0);
	        c7.put(4, 0.0);
	        c7.put(5, 0.0);
	        c7.put(6, 0.0);
	        c7.put(7, 0.0);
	        c7.put(8, 0.0);
	        c7.put(9, 0.0);
	        c7.put(10, 0.0);
	        constraints.add(new Constraint(
	            9, 
	            new Function(c7), 
	            ComparingOperator.LESSERTHAN, 
	            0.0
	        ));
	        
	     // Constraint 6: Cotton: 0x0 + 0x1 + 0x2 + 0x3 + 0x4 + 0x5 + 0x6 + 1.5x7 + 0.5x8 + 0x9 + 0x10 ≤ 30,000
	        HashMap<Integer, Double> c6 = new HashMap<>();
	        c6.put(0, 0.0);
	        c6.put(1, 0.0);
	        c6.put(2, 0.0);
	        c6.put(3, 0.0);
	        c6.put(4, 0.0);
	        c6.put(5, 0.0);
	        c6.put(6, 0.0);
	        c6.put(7, 1.5);
	        c6.put(8, 0.5);
	        c6.put(9, 0.0);
	        c6.put(10, 0.0);
	        constraints.add(new Constraint(
	            10, 
	            new Function(c6), 
	            ComparingOperator.LESSERTHAN, 
	            30000.0
	        ));
	        
	        // Constraint 5: Velvet: 0x0 + 0x1 + 0x2 + 0x3 + 0x4 + 0x5 + 3x6 + 0x7 + 0x8 + 1.5x9 + 0x10 ≤ 20,000
	        HashMap<Integer, Double> c5 = new HashMap<>();
	        c5.put(0, 0.0);
	        c5.put(1, 0.0);
	        c5.put(2, 0.0);
	        c5.put(3, 0.0);
	        c5.put(4, 0.0);
	        c5.put(5, 0.0);
	        c5.put(6, 3.0);
	        c5.put(7, 0.0);
	        c5.put(8, 0.0);
	        c5.put(9, 1.5);
	        c5.put(10, 0.0);
	        constraints.add(new Constraint(
	            11, 
	            new Function(c5), 
	            ComparingOperator.LESSERTHAN, 
	            20000.0
	        ));
	        
	     // Constraint 4: Rayon: 0x0 + 0x1 + 0x2 + 0x3 + 2x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 + 1.5x10 ≤ 30,000
	        HashMap<Integer, Double> c4 = new HashMap<>();
	        c4.put(0, 0.0);
	        c4.put(1, 0.0);
	        c4.put(2, 0.0);
	        c4.put(3, 0.0);
	        c4.put(4, 2.0);
	        c4.put(5, 0.0);
	        c4.put(6, 0.0);
	        c4.put(7, 0.0);
	        c4.put(8, 0.0);
	        c4.put(9, 0.0);
	        c4.put(10, 1.5);
	        constraints.add(new Constraint(
	            12, 
	            new Function(c4), 
	            ComparingOperator.LESSERTHAN, 
	            30000.0
	        ));
	        
	        // Constraint 3: Silk: 0x0 + 0x1 + 1.5x2 + 0.5x3 + 0x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 + 0x10 ≤ 18,000
	        HashMap<Integer, Double> c3 = new HashMap<>();
	        c3.put(0, 0.0);
	        c3.put(1, 0.0);
	        c3.put(2, 1.5);
	        c3.put(3, 0.5);
	        c3.put(4, 0.0);
	        c3.put(5, 0.0);
	        c3.put(6, 0.0);
	        c3.put(7, 0.0);
	        c3.put(8, 0.0);
	        c3.put(9, 0.0);
	        c3.put(10, 0.0);
	        constraints.add(new Constraint(
	            13, 
	            new Function(c3), 
	            ComparingOperator.LESSERTHAN, 
	            18000.0
	        ));
	        
	     // Constraint 2: Cashmere: 0x0 + 1.5x1 + 0x2 + 0x3 + 0x4 + 0x5 + 0x6 + 0x7 + 0x8 + 0x9 + 0x10 ≤ 9,000
	        HashMap<Integer, Double> c2 = new HashMap<>();
	        c2.put(0, 0.0);
	        c2.put(1, 1.5);
	        c2.put(2, 0.0);
	        c2.put(3, 0.0);
	        c2.put(4, 0.0);
	        c2.put(5, 0.0);
	        c2.put(6, 0.0);
	        c2.put(7, 0.0);
	        c2.put(8, 0.0);
	        c2.put(9, 0.0);
	        c2.put(10, 0.0);
	        constraints.add(new Constraint(
	            14, 
	            new Function(c2), 
	            ComparingOperator.LESSERTHAN, 
	            9000.0
	        ));
	        
	        // Constraint 1: Acetate: 2x0 + 0x1 + 0x2 + 0x3 + 1.5x4 + 1.5x5 + 2x6 + 0x7 + 0x8 + 0x9 + 0x10 ≤ 28,000
	        HashMap<Integer, Double> c1 = new HashMap<>();
	        c1.put(0, 2.0);
	        c1.put(1, 0.0);
	        c1.put(2, 0.0);
	        c1.put(3, 0.0);
	        c1.put(4, 1.5);
	        c1.put(5, 1.5);
	        c1.put(6, 2.0);
	        c1.put(7, 0.0);
	        c1.put(8, 0.0);
	        c1.put(9, 0.0);
	        c1.put(10, 0.0);
	        constraints.add(new Constraint(
	            15, 
	            new Function(c1), 
	            ComparingOperator.LESSERTHAN, 
	            28000.0
	        ));
	        
	     // Constraint 0: Wool: 3x0 + 0x1 + 0x2 + 0x3 + 0x4 + 2.5x5 + 0x6 + 0x7 + 0x8 + 0x9 + 0x10 ≤ 45,000
	        HashMap<Integer, Double> c0 = new HashMap<>();
	        c0.put(0, 3.0);
	        c0.put(1, 0.0);
	        c0.put(2, 0.0);
	        c0.put(3, 0.0);
	        c0.put(4, 0.0);
	        c0.put(5, 2.5);
	        c0.put(6, 0.0);
	        c0.put(7, 0.0);
	        c0.put(8, 0.0);
	        c0.put(9, 0.0);
	        c0.put(10, 0.0);
	        constraints.add(new Constraint(
	            16, 
	            new Function(c0), 
	            ComparingOperator.LESSERTHAN, 
	            45000.0
	        ));*/
	        
		int x11 = 0;   // Area 1 -> School 1
        int x12 = 1;   // Area 1 -> School 2
        int x13 = 2;   // Area 1 -> School 3
        int x22 = 3;   // Area 2 -> School 2
        int x23 = 4;   // Area 2 -> School 3
        int x31 = 5;   // Area 3 -> School 1
        int x32 = 6;   // Area 3 -> School 2
        int x33 = 7;   // Area 3 -> School 3
        int x41 = 8;   // Area 4 -> School 1
        int x42 = 9;   // Area 4 -> School 2
        int x52 = 10;  // Area 5 -> School 2
        int x61 = 11;  // Area 6 -> School 1
        int x62 = 12;  // Area 6 -> School 2
        int x63 = 13;  // Area 6 -> School 3

        // ============================================================
        // OBJECTIVE FUNCTION: Minimize Total Bussing Cost
        // ============================================================
        ExtremumEnum extremum = ExtremumEnum.MINIMIZE;
        
        HashMap<Integer, Double> objectiveCoeffs = new HashMap<>();
        objectiveCoeffs.put(x11, 300.0);
        objectiveCoeffs.put(x12, 0.0);
        objectiveCoeffs.put(x13, 700.0);
        objectiveCoeffs.put(x22, 400.0);
        objectiveCoeffs.put(x23, 500.0);
        objectiveCoeffs.put(x31, 600.0);
        objectiveCoeffs.put(x32, 300.0);
        objectiveCoeffs.put(x33, 200.0);
        objectiveCoeffs.put(x41, 200.0);
        objectiveCoeffs.put(x42, 500.0);
        objectiveCoeffs.put(x52, 400.0);
        objectiveCoeffs.put(x61, 500.0);
        objectiveCoeffs.put(x62, 300.0);
        objectiveCoeffs.put(x63, 0.0);
        
        Function objectiveFunction = new Function(objectiveCoeffs);
        ObjectiveFunction objFunc = new ObjectiveFunction(extremum, objectiveFunction);
        
        // ============================================================
        // CONSTRAINTS - ALL GREATHERTHAN CONSTRAINTS FIRST
        // Then ALL LESSERTHAN CONSTRAINTS
        // ============================================================
        ArrayList<Constraint> constraints = new ArrayList<>();
        
        // ============================================================
        // PART 1: ALL GREATHERTHAN CONSTRAINTS (x ≥ b)
        // These come FIRST in the constraint list
        // ============================================================
        
        // ----- AREA POPULATION CONSTRAINTS (≥ part of equality) -----
        
        // Constraint 0: Area 1: x11 + x12 + x13 ≥ 450
        HashMap<Integer, Double> c0 = new HashMap<>();
        c0.put(x11, 1.0);
        c0.put(x12, 1.0);
        c0.put(x13, 1.0);
        c0.put(x22, 0.0);
        c0.put(x23, 0.0);
        c0.put(x31, 0.0);
        c0.put(x32, 0.0);
        c0.put(x33, 0.0);
        c0.put(x41, 0.0);
        c0.put(x42, 0.0);
        c0.put(x52, 0.0);
        c0.put(x61, 0.0);
        c0.put(x62, 0.0);
        c0.put(x63, 0.0);
        constraints.add(new Constraint(0, new Function(c0), 
            ComparingOperator.GREATERTHAN, -450.0));
        
        // Constraint 1: Area 2: x22 + x23 ≥ 600
        HashMap<Integer, Double> c1 = new HashMap<>();
        c1.put(x11, 0.0);
        c1.put(x12, 0.0);
        c1.put(x13, 0.0);
        c1.put(x22, 1.0);
        c1.put(x23, 1.0);
        c1.put(x31, 0.0);
        c1.put(x32, 0.0);
        c1.put(x33, 0.0);
        c1.put(x41, 0.0);
        c1.put(x42, 0.0);
        c1.put(x52, 0.0);
        c1.put(x61, 0.0);
        c1.put(x62, 0.0);
        c1.put(x63, 0.0);
        constraints.add(new Constraint(1, new Function(c1), 
            ComparingOperator.GREATERTHAN, -600.0));
        
        // Constraint 2: Area 3: x31 + x32 + x33 ≥ 550
        HashMap<Integer, Double> c2 = new HashMap<>();
        c2.put(x11, 0.0);
        c2.put(x12, 0.0);
        c2.put(x13, 0.0);
        c2.put(x22, 0.0);
        c2.put(x23, 0.0);
        c2.put(x31, 1.0);
        c2.put(x32, 1.0);
        c2.put(x33, 1.0);
        c2.put(x41, 0.0);
        c2.put(x42, 0.0);
        c2.put(x52, 0.0);
        c2.put(x61, 0.0);
        c2.put(x62, 0.0);
        c2.put(x63, 0.0);
        constraints.add(new Constraint(2, new Function(c2), 
            ComparingOperator.GREATERTHAN, -550.0));
        
        // Constraint 3: Area 4: x41 + x42 ≥ 350
        HashMap<Integer, Double> c3 = new HashMap<>();
        c3.put(x11, 0.0);
        c3.put(x12, 0.0);
        c3.put(x13, 0.0);
        c3.put(x22, 0.0);
        c3.put(x23, 0.0);
        c3.put(x31, 0.0);
        c3.put(x32, 0.0);
        c3.put(x33, 0.0);
        c3.put(x41, 1.0);
        c3.put(x42, 1.0);
        c3.put(x52, 0.0);
        c3.put(x61, 0.0);
        c3.put(x62, 0.0);
        c3.put(x63, 0.0);
        constraints.add(new Constraint(3, new Function(c3), 
            ComparingOperator.GREATERTHAN, -350.0));
        
        // Constraint 4: Area 5: x52 ≥ 500
        HashMap<Integer, Double> c4 = new HashMap<>();
        c4.put(x11, 0.0);
        c4.put(x12, 0.0);
        c4.put(x13, 0.0);
        c4.put(x22, 0.0);
        c4.put(x23, 0.0);
        c4.put(x31, 0.0);
        c4.put(x32, 0.0);
        c4.put(x33, 0.0);
        c4.put(x41, 0.0);
        c4.put(x42, 0.0);
        c4.put(x52, 1.0);
        c4.put(x61, 0.0);
        c4.put(x62, 0.0);
        c4.put(x63, 0.0);
        constraints.add(new Constraint(4, new Function(c4), 
            ComparingOperator.GREATERTHAN, -500.0));
        
        // Constraint 5: Area 6: x61 + x62 + x63 ≥ 450
        HashMap<Integer, Double> c5 = new HashMap<>();
        c5.put(x11, 0.0);
        c5.put(x12, 0.0);
        c5.put(x13, 0.0);
        c5.put(x22, 0.0);
        c5.put(x23, 0.0);
        c5.put(x31, 0.0);
        c5.put(x32, 0.0);
        c5.put(x33, 0.0);
        c5.put(x41, 0.0);
        c5.put(x42, 0.0);
        c5.put(x52, 0.0);
        c5.put(x61, 1.0);
        c5.put(x62, 1.0);
        c5.put(x63, 1.0);
        constraints.add(new Constraint(5, new Function(c5), 
            ComparingOperator.GREATERTHAN, -450.0));
        
        // ----- GRADE COMPOSITION CONSTRAINTS (≥ 30%) -----
        
        // Constraint 6: School 1 - 6th Grade ≥ 30%
        HashMap<Integer, Double> c6 = new HashMap<>();
        c6.put(x11, 0.02);
        c6.put(x12, 0.0);
        c6.put(x13, 0.0);
        c6.put(x22, 0.0);
        c6.put(x23, 0.0);
        c6.put(x31, -0.02);
        c6.put(x32, 0.0);
        c6.put(x33, 0.0);
        c6.put(x41, -0.02);
        c6.put(x42, 0.0);
        c6.put(x52, 0.0);
        c6.put(x61, -0.02);
        c6.put(x62, 0.0);
        c6.put(x63, 0.0);
        constraints.add(new Constraint(6, new Function(c6), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // Constraint 7: School 1 - 7th Grade ≥ 30%
        HashMap<Integer, Double> c7 = new HashMap<>();
        c7.put(x11, 0.08);
        c7.put(x12, 0.0);
        c7.put(x13, 0.0);
        c7.put(x22, 0.0);
        c7.put(x23, 0.0);
        c7.put(x31, 0.02);
        c7.put(x32, 0.0);
        c7.put(x33, 0.0);
        c7.put(x41, 0.02);
        c7.put(x42, 0.0);
        c7.put(x52, 0.0);
        c7.put(x61, 0.02);
        c7.put(x62, 0.0);
        c7.put(x63, 0.0);
        constraints.add(new Constraint(7, new Function(c7), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // Constraint 8: School 1 - 8th Grade ≥ 30%
        HashMap<Integer, Double> c8 = new HashMap<>();
        c8.put(x11, 0.0);
        c8.put(x12, 0.0);
        c8.put(x13, 0.0);
        c8.put(x22, 0.0);
        c8.put(x23, 0.0);
        c8.put(x31, 0.08);
        c8.put(x32, 0.0);
        c8.put(x33, 0.0);
        c8.put(x41, 0.10);
        c8.put(x42, 0.0);
        c8.put(x52, 0.0);
        c8.put(x61, 0.08);
        c8.put(x62, 0.0);
        c8.put(x63, 0.0);
        constraints.add(new Constraint(8, new Function(c8), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // Constraint 9: School 2 - 6th Grade ≥ 30%
        HashMap<Integer, Double> c9 = new HashMap<>();
        c9.put(x11, 0.0);
        c9.put(x12, 0.02);
        c9.put(x13, 0.0);
        c9.put(x22, 0.07);
        c9.put(x23, 0.0);
        c9.put(x31, 0.0);
        c9.put(x32, 0.02);
        c9.put(x33, 0.0);
        c9.put(x41, 0.0);
        c9.put(x42, 0.02);
        c9.put(x52, 0.09);
        c9.put(x61, 0.0);
        c9.put(x62, -0.02);
        c9.put(x63, 0.0);
        constraints.add(new Constraint(9, new Function(c9), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // Constraint 10: School 2 - 7th Grade ≥ 30%
        HashMap<Integer, Double> c10 = new HashMap<>();
        c10.put(x11, 0.0);
        c10.put(x12, 0.08);
        c10.put(x13, 0.0);
        c10.put(x22, -0.02);
        c10.put(x23, 0.0);
        c10.put(x31, 0.0);
        c10.put(x32, 0.02);
        c10.put(x33, 0.0);
        c10.put(x41, 0.0);
        c10.put(x42, 0.10);
        c10.put(x52, 0.04);
        c10.put(x61, 0.0);
        c10.put(x62, 0.02);
        c10.put(x63, 0.0);
        constraints.add(new Constraint(10, new Function(c10), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // Constraint 11: School 2 - 8th Grade ≥ 30%
        HashMap<Integer, Double> c11 = new HashMap<>();
        c11.put(x11, 0.0);
        c11.put(x12, 0.0);
        c11.put(x13, 0.0);
        c11.put(x22, 0.05);
        c11.put(x23, 0.0);
        c11.put(x31, 0.0);
        c11.put(x32, 0.08);
        c11.put(x33, 0.0);
        c11.put(x41, 0.0);
        c11.put(x42, -0.02);
        c11.put(x52, -0.03);
        c11.put(x61, 0.0);
        c11.put(x62, 0.10);
        c11.put(x63, 0.0);
        constraints.add(new Constraint(11, new Function(c11), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // Constraint 12: School 3 - 6th Grade ≥ 30%
        HashMap<Integer, Double> c12 = new HashMap<>();
        c12.put(x11, 0.0);
        c12.put(x12, 0.0);
        c12.put(x13, 0.02);
        c12.put(x22, 0.0);
        c12.put(x23, 0.07);
        c12.put(x31, 0.0);
        c12.put(x32, 0.0);
        c12.put(x33, 0.02);
        c12.put(x41, 0.0);
        c12.put(x42, 0.0);
        c12.put(x52, 0.0);
        c12.put(x61, 0.0);
        c12.put(x62, 0.0);
        c12.put(x63, -0.02);
        constraints.add(new Constraint(12, new Function(c12), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // Constraint 13: School 3 - 7th Grade ≥ 30%
        HashMap<Integer, Double> c13 = new HashMap<>();
        c13.put(x11, 0.0);
        c13.put(x12, 0.0);
        c13.put(x13, 0.08);
        c13.put(x22, 0.0);
        c13.put(x23, -0.02);
        c13.put(x31, 0.0);
        c13.put(x32, 0.0);
        c13.put(x33, 0.02);
        c13.put(x41, 0.0);
        c13.put(x42, 0.0);
        c13.put(x52, 0.0);
        c13.put(x61, 0.0);
        c13.put(x62, 0.0);
        c13.put(x63, 0.02);
        constraints.add(new Constraint(13, new Function(c13), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // Constraint 14: School 3 - 8th Grade ≥ 30%
        HashMap<Integer, Double> c14 = new HashMap<>();
        c14.put(x11, 0.0);
        c14.put(x12, 0.0);
        c14.put(x13, 0.0);
        c14.put(x22, 0.0);
        c14.put(x23, 0.05);
        c14.put(x31, 0.0);
        c14.put(x32, 0.0);
        c14.put(x33, 0.08);
        c14.put(x41, 0.0);
        c14.put(x42, 0.0);
        c14.put(x52, 0.0);
        c14.put(x61, 0.0);
        c14.put(x62, 0.0);
        c14.put(x63, 0.10);
        constraints.add(new Constraint(14, new Function(c14), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        // ============================================================
        // PART 2: ALL LESSERTHAN CONSTRAINTS (x ≤ b)
        // These come SECOND in the constraint list
        // ============================================================
        
        // ----- AREA POPULATION CONSTRAINTS (≤ part of equality) -----
        
        // Constraint 15: Area 1: x11 + x12 + x13 ≤ 450
        HashMap<Integer, Double> c15 = new HashMap<>();
        c15.put(x11, 1.0);
        c15.put(x12, 1.0);
        c15.put(x13, 1.0);
        c15.put(x22, 0.0);
        c15.put(x23, 0.0);
        c15.put(x31, 0.0);
        c15.put(x32, 0.0);
        c15.put(x33, 0.0);
        c15.put(x41, 0.0);
        c15.put(x42, 0.0);
        c15.put(x52, 0.0);
        c15.put(x61, 0.0);
        c15.put(x62, 0.0);
        c15.put(x63, 0.0);
        constraints.add(new Constraint(15, new Function(c15), 
            ComparingOperator.LESSERTHAN, 450.0));
        
        // Constraint 16: Area 2: x22 + x23 ≤ 600
        HashMap<Integer, Double> c16 = new HashMap<>();
        c16.put(x11, 0.0);
        c16.put(x12, 0.0);
        c16.put(x13, 0.0);
        c16.put(x22, 1.0);
        c16.put(x23, 1.0);
        c16.put(x31, 0.0);
        c16.put(x32, 0.0);
        c16.put(x33, 0.0);
        c16.put(x41, 0.0);
        c16.put(x42, 0.0);
        c16.put(x52, 0.0);
        c16.put(x61, 0.0);
        c16.put(x62, 0.0);
        c16.put(x63, 0.0);
        constraints.add(new Constraint(16, new Function(c16), 
            ComparingOperator.LESSERTHAN, 600.0));
        
        // Constraint 17: Area 3: x31 + x32 + x33 ≤ 550
        HashMap<Integer, Double> c17 = new HashMap<>();
        c17.put(x11, 0.0);
        c17.put(x12, 0.0);
        c17.put(x13, 0.0);
        c17.put(x22, 0.0);
        c17.put(x23, 0.0);
        c17.put(x31, 1.0);
        c17.put(x32, 1.0);
        c17.put(x33, 1.0);
        c17.put(x41, 0.0);
        c17.put(x42, 0.0);
        c17.put(x52, 0.0);
        c17.put(x61, 0.0);
        c17.put(x62, 0.0);
        c17.put(x63, 0.0);
        constraints.add(new Constraint(17, new Function(c17), 
            ComparingOperator.LESSERTHAN, 550.0));
        
        // Constraint 18: Area 4: x41 + x42 ≤ 350
        HashMap<Integer, Double> c18 = new HashMap<>();
        c18.put(x11, 0.0);
        c18.put(x12, 0.0);
        c18.put(x13, 0.0);
        c18.put(x22, 0.0);
        c18.put(x23, 0.0);
        c18.put(x31, 0.0);
        c18.put(x32, 0.0);
        c18.put(x33, 0.0);
        c18.put(x41, 1.0);
        c18.put(x42, 1.0);
        c18.put(x52, 0.0);
        c18.put(x61, 0.0);
        c18.put(x62, 0.0);
        c18.put(x63, 0.0);
        constraints.add(new Constraint(18, new Function(c18), 
            ComparingOperator.LESSERTHAN, 350.0));
        
        // Constraint 19: Area 5: x52 ≤ 500
        HashMap<Integer, Double> c19 = new HashMap<>();
        c19.put(x11, 0.0);
        c19.put(x12, 0.0);
        c19.put(x13, 0.0);
        c19.put(x22, 0.0);
        c19.put(x23, 0.0);
        c19.put(x31, 0.0);
        c19.put(x32, 0.0);
        c19.put(x33, 0.0);
        c19.put(x41, 0.0);
        c19.put(x42, 0.0);
        c19.put(x52, 1.0);
        c19.put(x61, 0.0);
        c19.put(x62, 0.0);
        c19.put(x63, 0.0);
        constraints.add(new Constraint(19, new Function(c19), 
            ComparingOperator.LESSERTHAN, 500.0));
        
        // Constraint 20: Area 6: x61 + x62 + x63 ≤ 450
        HashMap<Integer, Double> c20 = new HashMap<>();
        c20.put(x11, 0.0);
        c20.put(x12, 0.0);
        c20.put(x13, 0.0);
        c20.put(x22, 0.0);
        c20.put(x23, 0.0);
        c20.put(x31, 0.0);
        c20.put(x32, 0.0);
        c20.put(x33, 0.0);
        c20.put(x41, 0.0);
        c20.put(x42, 0.0);
        c20.put(x52, 0.0);
        c20.put(x61, 1.0);
        c20.put(x62, 1.0);
        c20.put(x63, 1.0);
        constraints.add(new Constraint(20, new Function(c20), 
            ComparingOperator.LESSERTHAN, 450.0));
        
        // ----- SCHOOL CAPACITY CONSTRAINTS (≤) -----
        
        // Constraint 21: School 1: x11 + x31 + x41 + x61 ≤ 900
        HashMap<Integer, Double> c21 = new HashMap<>();
        c21.put(x11, 1.0);
        c21.put(x12, 0.0);
        c21.put(x13, 0.0);
        c21.put(x22, 0.0);
        c21.put(x23, 0.0);
        c21.put(x31, 1.0);
        c21.put(x32, 0.0);
        c21.put(x33, 0.0);
        c21.put(x41, 1.0);
        c21.put(x42, 0.0);
        c21.put(x52, 0.0);
        c21.put(x61, 1.0);
        c21.put(x62, 0.0);
        c21.put(x63, 0.0);
        constraints.add(new Constraint(21, new Function(c21), 
            ComparingOperator.LESSERTHAN, 900.0));
        
        // Constraint 22: School 2: x12 + x22 + x32 + x42 + x52 + x62 ≤ 1100
        HashMap<Integer, Double> c22 = new HashMap<>();
        c22.put(x11, 0.0);
        c22.put(x12, 1.0);
        c22.put(x13, 0.0);
        c22.put(x22, 1.0);
        c22.put(x23, 0.0);
        c22.put(x31, 0.0);
        c22.put(x32, 1.0);
        c22.put(x33, 0.0);
        c22.put(x41, 0.0);
        c22.put(x42, 1.0);
        c22.put(x52, 1.0);
        c22.put(x61, 0.0);
        c22.put(x62, 1.0);
        c22.put(x63, 0.0);
        constraints.add(new Constraint(22, new Function(c22), 
            ComparingOperator.LESSERTHAN, 1100.0));
        
        // Constraint 23: School 3: x13 + x23 + x33 + x63 ≤ 1000
        HashMap<Integer, Double> c23 = new HashMap<>();
        c23.put(x11, 0.0);
        c23.put(x12, 0.0);
        c23.put(x13, 1.0);
        c23.put(x22, 0.0);
        c23.put(x23, 1.0);
        c23.put(x31, 0.0);
        c23.put(x32, 0.0);
        c23.put(x33, 1.0);
        c23.put(x41, 0.0);
        c23.put(x42, 0.0);
        c23.put(x52, 0.0);
        c23.put(x61, 0.0);
        c23.put(x62, 0.0);
        c23.put(x63, 1.0);
        constraints.add(new Constraint(23, new Function(c23), 
            ComparingOperator.LESSERTHAN, 1000.0));
        
        // ----- GRADE COMPOSITION CONSTRAINTS (≤ 36%) -----
        
        // Constraint 24: School 1 - 6th Grade ≤ 36%
        HashMap<Integer, Double> c24 = new HashMap<>();
        c24.put(x11, -0.04);
        c24.put(x12, 0.0);
        c24.put(x13, 0.0);
        c24.put(x22, 0.0);
        c24.put(x23, 0.0);
        c24.put(x31, -0.08);
        c24.put(x32, 0.0);
        c24.put(x33, 0.0);
        c24.put(x41, -0.08);
        c24.put(x42, 0.0);
        c24.put(x52, 0.0);
        c24.put(x61, -0.08);
        c24.put(x62, 0.0);
        c24.put(x63, 0.0);
        constraints.add(new Constraint(24, new Function(c24), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // Constraint 25: School 1 - 7th Grade ≤ 36%
        HashMap<Integer, Double> c25 = new HashMap<>();
        c25.put(x11, 0.02);
        c25.put(x12, 0.0);
        c25.put(x13, 0.0);
        c25.put(x22, 0.0);
        c25.put(x23, 0.0);
        c25.put(x31, -0.04);
        c25.put(x32, 0.0);
        c25.put(x33, 0.0);
        c25.put(x41, -0.04);
        c25.put(x42, 0.0);
        c25.put(x52, 0.0);
        c25.put(x61, -0.04);
        c25.put(x62, 0.0);
        c25.put(x63, 0.0);
        constraints.add(new Constraint(25, new Function(c25), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // Constraint 26: School 1 - 8th Grade ≤ 36%
        HashMap<Integer, Double> c26 = new HashMap<>();
        c26.put(x11, -0.06);
        c26.put(x12, 0.0);
        c26.put(x13, 0.0);
        c26.put(x22, 0.0);
        c26.put(x23, 0.0);
        c26.put(x31, 0.02);
        c26.put(x32, 0.0);
        c26.put(x33, 0.0);
        c26.put(x41, 0.04);
        c26.put(x42, 0.0);
        c26.put(x52, 0.0);
        c26.put(x61, 0.02);
        c26.put(x62, 0.0);
        c26.put(x63, 0.0);
        constraints.add(new Constraint(26, new Function(c26), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // Constraint 27: School 2 - 6th Grade ≤ 36%
        HashMap<Integer, Double> c27 = new HashMap<>();
        c27.put(x11, 0.0);
        c27.put(x12, -0.04);
        c27.put(x13, 0.0);
        c27.put(x22, 0.01);
        c27.put(x23, 0.0);
        c27.put(x31, 0.0);
        c27.put(x32, -0.04);
        c27.put(x33, 0.0);
        c27.put(x41, 0.0);
        c27.put(x42, -0.04);
        c27.put(x52, 0.03);
        c27.put(x61, 0.0);
        c27.put(x62, -0.08);
        c27.put(x63, 0.0);
        constraints.add(new Constraint(27, new Function(c27), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // Constraint 28: School 2 - 7th Grade ≤ 36%
        HashMap<Integer, Double> c28 = new HashMap<>();
        c28.put(x11, 0.0);
        c28.put(x12, 0.02);
        c28.put(x13, 0.0);
        c28.put(x22, -0.08);
        c28.put(x23, 0.0);
        c28.put(x31, 0.0);
        c28.put(x32, -0.04);
        c28.put(x33, 0.0);
        c28.put(x41, 0.0);
        c28.put(x42, 0.04);
        c28.put(x52, -0.02);
        c28.put(x61, 0.0);
        c28.put(x62, -0.04);
        c28.put(x63, 0.0);
        constraints.add(new Constraint(28, new Function(c28), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // Constraint 29: School 2 - 8th Grade ≤ 36%
        HashMap<Integer, Double> c29 = new HashMap<>();
        c29.put(x11, 0.0);
        c29.put(x12, -0.06);
        c29.put(x13, 0.0);
        c29.put(x22, -0.01);
        c29.put(x23, 0.0);
        c29.put(x31, 0.0);
        c29.put(x32, 0.02);
        c29.put(x33, 0.0);
        c29.put(x41, 0.0);
        c29.put(x42, -0.08);
        c29.put(x52, -0.09);
        c29.put(x61, 0.0);
        c29.put(x62, 0.04);
        c29.put(x63, 0.0);
        constraints.add(new Constraint(29, new Function(c29), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // Constraint 30: School 3 - 6th Grade ≤ 36%
        HashMap<Integer, Double> c30 = new HashMap<>();
        c30.put(x11, 0.0);
        c30.put(x12, 0.0);
        c30.put(x13, -0.04);
        c30.put(x22, 0.0);
        c30.put(x23, 0.01);
        c30.put(x31, 0.0);
        c30.put(x32, 0.0);
        c30.put(x33, -0.04);
        c30.put(x41, 0.0);
        c30.put(x42, 0.0);
        c30.put(x52, 0.0);
        c30.put(x61, 0.0);
        c30.put(x62, 0.0);
        c30.put(x63, -0.08);
        constraints.add(new Constraint(30, new Function(c30), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // Constraint 31: School 3 - 7th Grade ≤ 36%
        HashMap<Integer, Double> c31 = new HashMap<>();
        c31.put(x11, 0.0);
        c31.put(x12, 0.0);
        c31.put(x13, 0.02);
        c31.put(x22, 0.0);
        c31.put(x23, -0.08);
        c31.put(x31, 0.0);
        c31.put(x32, 0.0);
        c31.put(x33, -0.04);
        c31.put(x41, 0.0);
        c31.put(x42, 0.0);
        c31.put(x52, 0.0);
        c31.put(x61, 0.0);
        c31.put(x62, 0.0);
        c31.put(x63, -0.04);
        constraints.add(new Constraint(31, new Function(c31), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // Constraint 32: School 3 - 8th Grade ≤ 36%
        HashMap<Integer, Double> c32 = new HashMap<>();
        c32.put(x11, 0.0);
        c32.put(x12, 0.0);
        c32.put(x13, -0.06);
        c32.put(x22, 0.0);
        c32.put(x23, -0.01);
        c32.put(x31, 0.0);
        c32.put(x32, 0.0);
        c32.put(x33, 0.02);
        c32.put(x41, 0.0);
        c32.put(x42, 0.0);
        c32.put(x52, 0.0);
        c32.put(x61, 0.0);
        c32.put(x62, 0.0);
        c32.put(x63, 0.04);
        constraints.add(new Constraint(32, new Function(c32), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        // ============================================================
        // CREATE THE LINEAR PROBLEM AND SOLVE
        // ============================================================
        LinearProblem lp = new LinearProblem(objFunc, constraints);
        BuildingAugmentedMatrixAuxiliaryProblem bamauxp= new BuildingAugmentedMatrixAuxiliaryProblem(lp);
		Matrix.displayMatrix(bamauxp.getAuxiliary_problem_matrix());
		
		
	}



	public BuildingInitialComponents getBic() {
		return bic;
	}



	public void setBic(BuildingInitialComponents bic) {
		this.bic = bic;
	}



	public Matrix getAuxiliary_problem_matrix() {
		return auxiliary_problem_matrix;
	}



	public void setAuxiliary_problem_matrix(Matrix auxiliary_problem_matrix) {
		this.auxiliary_problem_matrix = auxiliary_problem_matrix;
	}



	public IntVector getBase() {
		return base;
	}



	public void setBase(IntVector base) {
		this.base = base;
	}



	public Vector getC_base() {
		return c_base;
	}



	public void setC_base(Vector c_base) {
		this.c_base = c_base;
	}



	public Vector getC_extended_I() {
		return c_extended_I;
	}



	public void setC_extended_I(Vector c_extended_I) {
		this.c_extended_I = c_extended_I;
	}



	public Vector getZj_cj() {
		return zj_cj;
	}



	public void setZj_cj(Vector zj_cj) {
		this.zj_cj = zj_cj;
	}



	public int getAugmented_n() {
		return augmented_n;
	}



	public void setAugmented_n(int augmented_n) {
		this.augmented_n = augmented_n;
	}



	public Integer getNegative_mult() {
		return negative_mult;
	}



	public void setNegative_mult(Integer negative_mult) {
		this.negative_mult = negative_mult;
	}



	public Integer getPositive_mult() {
		return positive_mult;
	}



	public void setPositive_mult(Integer positive_mult) {
		this.positive_mult = positive_mult;
	}



	public Integer getII_phase_I_phase() {
		return II_phase_I_phase;
	}



	public void setII_phase_I_phase(Integer iI_phase_I_phase) {
		II_phase_I_phase = iI_phase_I_phase;
	}



	public Integer getSize() {
		return size;
	}



	public void setSize(Integer size) {
		this.size = size;
	}



	public ArrayList<Integer> getIndice() {
		return indice;
	}



	public void setIndice(ArrayList<Integer> indice) {
		this.indice = indice;
	}



	public LinearProblem getLp() {
		return lp;
	}



	public void setLp(LinearProblem lp) {
		this.lp = lp;
	}



	public Matrix getAugmented_matrix_II() {
		return augmented_matrix_II;
	}



	public void setAugmented_matrix_II(Matrix augmented_matrix_II) {
		this.augmented_matrix_II = augmented_matrix_II;
	}



	public Vector getC_extended_II() {
		return c_extended_II;
	}



	public void setC_extended_II(Vector c_extended_II) {
		this.c_extended_II = c_extended_II;
	}
	
	
	

}
