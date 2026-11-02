package com.linsolve.optimization.algebraiclinearproblem.computations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.linsolve.optimization.algebraiccomponents.Matrix;
import com.linsolve.optimization.linearproblem.ComparingOperator;
import com.linsolve.optimization.linearproblem.Constraint;
import com.linsolve.optimization.linearproblem.ExtremumEnum;
import com.linsolve.optimization.linearproblem.Function;
import com.linsolve.optimization.linearproblem.LinearProblem;
import com.linsolve.optimization.linearproblem.ObjectiveFunction;
import com.linsolve.optimization.operator.ApplyingGaussPivot;
import com.linsolve.optimization.operator.Pivot;

public class SolvingDoublePhaseMaximize {
	
	private BuildingAugmentedMatrixDoublePhase baumatrixdoublephase;
	private  Double[][] augmented_matrix_phase_II;
	private Double[] b_vector;
	private Double[] c_vector;
	private Integer[] base;
	private Double[] c_base;
	private Double[] zj_cj_phase_II;
	private  Double[] c_extended_II;
	private LinearProblem lp;
	private Pivot pivot;
	private Integer pivot_column;
	private Integer pivot_line;
	private Double  pivot_value;
	private Double[] b_comparator_vector;
	private Boolean optimality;
	private ApplyingGaussPivot apg;
	
	

	public SolvingDoublePhaseMaximize(LinearProblem lp) throws Exception {
		super();
		this.lp = lp;
		this.baumatrixdoublephase=new BuildingAugmentedMatrixDoublePhase(lp);
		System.out.println();
		System.out.println();
		Matrix.displayMatrix(this.baumatrixdoublephase.getAugmented_matrix_phase_II());
		this.augmented_matrix_phase_II=this.baumatrixdoublephase.getAugmented_matrix_phase_II().getT();
		this.b_vector=this.baumatrixdoublephase.getSapsdp().getBamaux().getBic().getB_vector().getT();
		this.c_vector=this.baumatrixdoublephase.getSapsdp().getBamaux().getBic().getC_vector().getT();
		this.base=this.baumatrixdoublephase.getBase_phase_II().getInt_vect();
		this.c_base=this.baumatrixdoublephase.getC_base_phase_II().getT();
		this.zj_cj_phase_II=this.baumatrixdoublephase.getZj_cj_phase_II().getT();
		this.c_extended_II=this.baumatrixdoublephase.getC_extended_II().getT();
		this.b_comparator_vector=new Double[this.baumatrixdoublephase.getSapsdp().getBamaux().getBic().getB_vector().getT().length];
		this.optimality=Boolean.FALSE;
	}

	public Boolean check_optimality()
	{
		this.optimality=Boolean.TRUE;
		for(int j=0; j< this.zj_cj_phase_II.length;j++)
		{
			if(this.zj_cj_phase_II[j] !=null && this.zj_cj_phase_II[j].compareTo(Double.valueOf(0.0))<0)
			{
				this.optimality=Boolean.FALSE;
				break;
			}
		}
		return optimality;
	}

	public Integer compute_pivot_column() throws Exception
	{
		Integer pivot_column=null;
		Double maximum_value=Double.valueOf(0.0);	
		if(this.optimality.equals(Boolean.FALSE))
		{
			for(int j=0;j<this.zj_cj_phase_II.length;j++)
			{	
				if(this.zj_cj_phase_II[j].compareTo(maximum_value)<0)
				{
					pivot_column=j;
					maximum_value=this.zj_cj_phase_II[j];
				}
			}
			this.pivot_column=pivot_column;
		}
		if(this.pivot_column !=null)
		{
			return pivot_column;
		}
		else
		{
			this.optimality=Boolean.TRUE;
			return null;	
		}	
	}
	
	
	public  void build_b_comporator_vector()
	{
		this.b_comparator_vector= new Double[this.b_vector.length];	
		for(int i=0; i<this.b_vector.length;i++)
		{
			if( augmented_matrix_phase_II[i][pivot_column]>0)
			{
				b_comparator_vector[i]=augmented_matrix_phase_II[i][augmented_matrix_phase_II[0].length-1]/augmented_matrix_phase_II[i][pivot_column];
			}
		}	
	}
	
	public  Integer compute_pivot_line()
	{
		Double min_comp_vector=Double.MAX_VALUE;
		Integer pivot_line=null;
		for(int i=0; i<this.b_vector.length;i++)
		{
			if(b_comparator_vector[i] !=null && b_comparator_vector[i]<min_comp_vector)
			{
				min_comp_vector=b_comparator_vector[i];
				pivot_line=i;
			}		
		}
		this.pivot_line=pivot_line;
		return pivot_line;
	}
	
	public void apply_pivot_gauss()
	{
		pivot_value=this.baumatrixdoublephase.getAugmented_matrix_phase_II().getT()[this.pivot_line][this.pivot_column];
		this.pivot= new Pivot(pivot_line, pivot_column, pivot_value);	
		this.apg=new ApplyingGaussPivot(this.baumatrixdoublephase.getAugmented_matrix_phase_II(), pivot);
		this.apg.apply();
        this.baumatrixdoublephase.setAugmented_matrix_phase_II(this.apg.getMatrix());
        this.augmented_matrix_phase_II=this.apg.getMatrix().getT();
	}
	
	public  void reevaluate_base()
	{
		if(this.pivot_column !=null && this.pivot_line !=null)
		{
		for(int i=0;i<this.base.length;i++)
		{
			if(Integer.valueOf(i).equals(pivot_line))
			{
				this.base[i]=pivot_column;
				this.c_base[i]=this.c_extended_II[pivot_column];
			}
		}
		}
	}
	
	
	public  void reevaluate_zj_cj()
	{
		if(this.pivot_column !=null && this.pivot_line !=null)
		{
			 for(int j=0;j<this.getAugmented_matrix_phase_II()[0].length-1;j++)
			 {
				zj_cj_phase_II[j]=0.0;			
			 }
			 for(int j=0;j< this.getAugmented_matrix_phase_II()[0].length-1;j++) 
			 { 
				 for(int i=0;i<c_base.length;i++)
				 {
					zj_cj_phase_II[j]=zj_cj_phase_II[j]+c_base[i]*this.augmented_matrix_phase_II[i][j];
				 } 
			 } 
			 for(int j=0;j< this.getAugmented_matrix_phase_II()[0].length-1;j++) 
			 {
				 zj_cj_phase_II[j]=zj_cj_phase_II[j]-this.c_extended_II[j];
			 }
		} 
	}
	
	
	
	public void cleanNearZeroValues() {
	    Double[][] matrix = this.baumatrixdoublephase.getAugmented_matrix_phase_II().getT();
	    double epsilon = 1e-10; // Threshold for considering a value "close to zero"
    
	    if (matrix != null) 
	    {
	        for (int i = 0; i < matrix.length; i++) 
	        {
	            if (matrix[i] != null) 
	            {
	                for (int j = 0; j < matrix[i].length; j++) 
	                {
	                    if (matrix[i][j] != null) 
	                    {
	                        double value = matrix[i][j];
	                        if (Math.abs(value) < epsilon)
	                        {
	                            matrix[i][j] = 0.0;
	                        }
	                    }
	                }
	            }
	        }
	    }
	    this.baumatrixdoublephase.getAugmented_matrix_phase_II().setT(matrix);
	    
	}
	

	
	public void cleanNearZeroValuesZjCj() {
	    Double[] zj__cj = this.baumatrixdoublephase.getZj_cj_phase_II().getT();
	    double epsilon = 1e-12; // Threshold for considering a value "close to zero"
	    
	    if (zj__cj != null) 
	    {
	        for (int i = 0; i < zj__cj.length; i++) 
	        {
	            if (zj__cj[i] != null && Math.abs(zj__cj[i]) < epsilon) 
	            {
	                zj__cj[i] = 0.0;
	            }
	        }
	    }
	    this.baumatrixdoublephase.getZj_cj_phase_II().setT(zj__cj);
	}
	
	
	

	
	public void apply_simplex_phase_II() throws Exception
	{
		do
		{
			this.check_optimality();
			if(this.optimality.equals(Boolean.FALSE))
			{
				this.compute_pivot_column();
				this.build_b_comporator_vector();
				this.compute_pivot_line();
				this.apply_pivot_gauss();
				this.reevaluate_base();
				this.reevaluate_zj_cj();
			}
		}
		while(this.optimality.equals(Boolean.FALSE));
	}
	
	public Double compute_goal_function() throws Exception
	{
		Double z=0.0;
		Double[] b_augmented_matrix=new Double[this.getB_vector().length];
		for(int i=0;i<this.getB_vector().length;i++)
		{
			b_augmented_matrix[i]=this.augmented_matrix_phase_II[i][this.augmented_matrix_phase_II[0].length-1];
		}	
		for(int i=0;i<this.getB_vector().length;i++)
		{
			if(this.base[i]<this.c_vector.length)
			{
				z=z+this.getC_vector()[base[i]]*b_augmented_matrix[i];
			}
		}
		System.out.println("Z Function : "+z);
		return z;
	}
	
	
	
	public Map<Integer, Double> enumerate_based_variables_values()
	{
		Map<Integer, Double> hm= new HashMap<>();
		for(int i=0;i<this.getB_vector().length;i++)
		{
			hm.put(this.base[i], this.augmented_matrix_phase_II[i][this.augmented_matrix_phase_II[0].length-1]);
			System.out.println("Var x"+this.base[i]+" "+this.augmented_matrix_phase_II[i][this.augmented_matrix_phase_II[0].length-1]+" ");
		}	
		return hm;
	}
	
	public void apply() throws Exception
	{
		 this.apply_simplex_phase_II();
	     this.compute_goal_function();
	     this.enumerate_based_variables_values();
	}
	
	
	
	public static void main(String[] args) throws Exception 
	{
		
		    ExtremumEnum extremum = ExtremumEnum.MAXIMIZE;
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
	        ));
        LinearProblem lp = new LinearProblem(objFunc, constraints);
        SolvingDoublePhaseMaximize sdpm= new  SolvingDoublePhaseMaximize(lp) ;
        sdpm.apply();
	}

	public BuildingAugmentedMatrixDoublePhase getBaumatrixdoublephase() {
		return baumatrixdoublephase;
	}

	public void setBaumatrixdoublephase(BuildingAugmentedMatrixDoublePhase baumatrixdoublephase) {
		this.baumatrixdoublephase = baumatrixdoublephase;
	}

	public Double[][] getAugmented_matrix_phase_II() {
		return augmented_matrix_phase_II;
	}

	public void setAugmented_matrix_phase_II(Double[][] augmented_matrix_phase_II) {
		this.augmented_matrix_phase_II = augmented_matrix_phase_II;
	}

	public Double[] getB_vector() {
		return b_vector;
	}

	public void setB_vector(Double[] b_vector) {
		this.b_vector = b_vector;
	}

	public Double[] getC_vector() {
		return c_vector;
	}

	public void setC_vector(Double[] c_vector) {
		this.c_vector = c_vector;
	}

	public Integer[] getBase() {
		return base;
	}

	public void setBase(Integer[] base) {
		this.base = base;
	}

	public Double[] getC_base() {
		return c_base;
	}

	public void setC_base(Double[] c_base) {
		this.c_base = c_base;
	}

	public Double[] getZj_cj_phase_II() {
		return zj_cj_phase_II;
	}

	public void setZj_cj_phase_II(Double[] zj_cj_phase_II) {
		this.zj_cj_phase_II = zj_cj_phase_II;
	}

	public Double[] getC_extended_II() {
		return c_extended_II;
	}

	public void setC_extended_II(Double[] c_extended_II) {
		this.c_extended_II = c_extended_II;
	}

	public LinearProblem getLp() {
		return lp;
	}

	public void setLp(LinearProblem lp) {
		this.lp = lp;
	}

	public Pivot getPivot() {
		return pivot;
	}

	public void setPivot(Pivot pivot) {
		this.pivot = pivot;
	}

	public Integer getPivot_column() {
		return pivot_column;
	}

	public void setPivot_column(Integer pivot_column) {
		this.pivot_column = pivot_column;
	}

	public Integer getPivot_line() {
		return pivot_line;
	}

	public void setPivot_line(Integer pivot_line) {
		this.pivot_line = pivot_line;
	}

	public Double getPivot_value() {
		return pivot_value;
	}

	public void setPivot_value(Double pivot_value) {
		this.pivot_value = pivot_value;
	}

	public Double[] getB_comparator_vector() {
		return b_comparator_vector;
	}

	public void setB_comparator_vector(Double[] b_comparator_vector) {
		this.b_comparator_vector = b_comparator_vector;
	}

	public Boolean getOptimality() {
		return optimality;
	}

	public void setOptimality(Boolean optimality) {
		this.optimality = optimality;
	}

	public ApplyingGaussPivot getApg() {
		return apg;
	}

	public void setApg(ApplyingGaussPivot apg) {
		this.apg = apg;
	}
	
	

}
