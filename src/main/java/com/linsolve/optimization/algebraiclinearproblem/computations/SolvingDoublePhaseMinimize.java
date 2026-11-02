package com.linsolve.optimization.algebraiclinearproblem.computations;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.linsolve.optimization.algebraiccomponents.IntVector;
import com.linsolve.optimization.algebraiccomponents.Matrix;
import com.linsolve.optimization.algebraiccomponents.Vector;
import com.linsolve.optimization.linearproblem.ComparingOperator;
import com.linsolve.optimization.linearproblem.Constraint;
import com.linsolve.optimization.linearproblem.ExtremumEnum;
import com.linsolve.optimization.linearproblem.Function;
import com.linsolve.optimization.linearproblem.LinearProblem;
import com.linsolve.optimization.linearproblem.ObjectiveFunction;
import com.linsolve.optimization.operator.ApplyingGaussPivot;
import com.linsolve.optimization.operator.Pivot;

public class SolvingDoublePhaseMinimize {

	private BuildingAugmentedMatrixDoublePhase baumatrixdoublephase;
	private  Double[][] augmented_matrix_phase_II;
	private Double[] b_vector;
	private Double[] c_vector;
	private Integer[] base;
	private Double[] c_base;
	private Double[] zj_cj_phase_II;
	private  Double[] c_extended_II;
    
	private LinearProblem lp;
	private Boolean optimality;
	private Pivot pivot;
	private Integer pivot_column;
	private Integer pivot_line;
	private Double  pivot_value;
	private Double[] b_comparator_vector;
    private ApplyingGaussPivot apg;
    
	public SolvingDoublePhaseMinimize(LinearProblem lp) throws Exception {
		super();
		this.lp = lp;
		this.baumatrixdoublephase=new BuildingAugmentedMatrixDoublePhase(lp);
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
	
	public void check_optimality()
	{
		this.optimality=Boolean.TRUE;
		for(int j=0; j< this.baumatrixdoublephase.getZj_cj_phase_II().getT().length;j++)
		{
			if(this.baumatrixdoublephase.getZj_cj_phase_II().getT()[j] !=null && this.baumatrixdoublephase.getZj_cj_phase_II().getT()[j].compareTo(Double.valueOf(0.0))>0)
			{
				this.optimality=Boolean.FALSE;
				break;
			}
		}
		
	}
	
	public Integer compute_pivot_column() throws Exception
	{
		Integer pivot_column=null;
		Double minimum_value=Double.valueOf(0.0);	
		if(this.optimality.equals(Boolean.FALSE))
		{
			for(int j=0;j<this.baumatrixdoublephase.getZj_cj_phase_II().getT().length;j++)
			{	
				if(this.baumatrixdoublephase.getZj_cj_phase_II().getT()[j].compareTo(minimum_value)>0)
				{
					pivot_column=j;
					minimum_value=this.baumatrixdoublephase.getZj_cj_phase_II().getT()[j];
				}
			}
		}
		this.pivot_column=pivot_column;
		if(pivot_column !=null)
		{
			return pivot_column;
		}
		else
		{
			return null;
		}
	}
	
	public void build_b_comporator_vector()
	{
		this.b_comparator_vector= new Double[this.b_vector.length];	
		for(int i=0; i<this.b_vector.length;i++)
		{
			if(augmented_matrix_phase_II[i][augmented_matrix_phase_II[0].length-1]>=0 && this.pivot_column !=null && augmented_matrix_phase_II[i][pivot_column]>0)
			{
				b_comparator_vector[i]=augmented_matrix_phase_II[i][augmented_matrix_phase_II[0].length-1]/augmented_matrix_phase_II[i][pivot_column];
			}
		}	
	}
	
	public Integer compute_pivot_line()
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
	
	public Map<Integer, Double> enumerate_based_variables_values_1()
	{
		Map<Integer, Double> hm= new HashMap<>();
		for(int i=0;i<this.getB_vector().length;i++)
		{
			hm.put(this.base[i], this.augmented_matrix_phase_II[i][this.augmented_matrix_phase_II[0].length-1]);
			System.out.println("Var "+getVariableName(this.base[i])+" "+this.augmented_matrix_phase_II[i][this.augmented_matrix_phase_II[0].length-1]+" ");
		}	
		return hm;
	}
	
	public static String getVariableName(int index) {
	    switch(index) {
	        case 0: return "x11";
	        case 1: return "x12";
	        case 2: return "x13";
	        case 3: return "x22";
	        case 4: return "x23";
	        case 5: return "x31";
	        case 6: return "x32";
	        case 7: return "x33";
	        case 8: return "x41";
	        case 9: return "x42";
	        case 10: return "x52";
	        case 11: return "x61";
	        case 12: return "x62";
	        case 13: return "x63";
	        default: return "x"+index;
	    }
	}	
	public void apply() throws Exception
	{
		this.apply_simplex_phase_II();
	    this.compute_goal_function();
	    this.enumerate_based_variables_values();
	}
	
    public static void main(String[] args) throws Exception {
    	 // ============================================================
        // DEFINE VARIABLE INDICES (0 to 17) - ALL 18 VARIABLES
        // ============================================================
        // ENGLISH FULL-TIME OPERATORS (Pattern A/B)
        // ============================================================
        // DEFINE VARIABLE INDICES (0 to 17) - ALL 18 VARIABLES
        // ============================================================
        // ENGLISH FULL-TIME OPERATORS (Pattern A/B)
        int Xef1A = 0;   // English Full-time 7AM Pattern A (Phone: shifts 1 & 3)
        int Xef1B = 1;   // English Full-time 7AM Pattern B (Phone: shifts 2 & 4)
        int Xef2A = 2;   // English Full-time 9AM Pattern A (Phone: shifts 2 & 4)
        int Xef2B = 3;   // English Full-time 9AM Pattern B (Phone: shifts 3 & 5)
        int Xef3A = 4;   // English Full-time 11AM Pattern A (Phone: shifts 3 & 5)
        int Xef3B = 5;   // English Full-time 11AM Pattern B (Phone: shifts 4 & 6)
        int Xef4A = 6;   // English Full-time 1PM Pattern A (Phone: shifts 4 & 6)
        int Xef4B = 7;   // English Full-time 1PM Pattern B (Phone: shifts 5 & 7)

        // ENGLISH PART-TIME OPERATORS
        int Xep5 = 8;    // English Part-time 3PM (Phone: shifts 5 & 6)
        int Xep6 = 9;    // English Part-time 5PM (Phone: shifts 6 & 7)

        // SPANISH FULL-TIME OPERATORS (Pattern A/B)
        int Ysf1A = 10;  // Spanish Full-time 7AM Pattern A (Phone: shifts 1 & 3)
        int Ysf1B = 11;  // Spanish Full-time 7AM Pattern B (Phone: shifts 2 & 4)
        int Ysf2A = 12;  // Spanish Full-time 9AM Pattern A (Phone: shifts 2 & 4)
        int Ysf2B = 13;  // Spanish Full-time 9AM Pattern B (Phone: shifts 3 & 5)
        int Ysf3A = 14;  // Spanish Full-time 11AM Pattern A (Phone: shifts 3 & 5)
        int Ysf3B = 15;  // Spanish Full-time 11AM Pattern B (Phone: shifts 4 & 6)
        int Ysf4A = 16;  // Spanish Full-time 1PM Pattern A (Phone: shifts 4 & 6)
        int Ysf4B = 17;  // Spanish Full-time 1PM Pattern B (Phone: shifts 5 & 7)

        // ============================================================
        // OBJECTIVE FUNCTION: MINIMIZE TOTAL COST
        // ============================================================
        HashMap<Integer, Double> objectiveCoefficients = new HashMap<>();

        // English Full-time costs
        objectiveCoefficients.put(Xef1A, 40.0);
        objectiveCoefficients.put(Xef1B, 40.0);
        objectiveCoefficients.put(Xef2A, 40.0);
        objectiveCoefficients.put(Xef2B, 40.0);
        objectiveCoefficients.put(Xef3A, 40.0);
        objectiveCoefficients.put(Xef3B, 44.0);
        objectiveCoefficients.put(Xef4A, 44.0);
        objectiveCoefficients.put(Xef4B, 44.0);

        // English Part-time costs
        objectiveCoefficients.put(Xep5, 44.0);
        objectiveCoefficients.put(Xep6, 48.0);

        // Spanish Full-time costs
        objectiveCoefficients.put(Ysf1A, 40.0);
        objectiveCoefficients.put(Ysf1B, 40.0);
        objectiveCoefficients.put(Ysf2A, 40.0);
        objectiveCoefficients.put(Ysf2B, 40.0);
        objectiveCoefficients.put(Ysf3A, 40.0);
        objectiveCoefficients.put(Ysf3B, 44.0);
        objectiveCoefficients.put(Ysf4A, 44.0);
        objectiveCoefficients.put(Ysf4B, 44.0);

        Function objectiveFunction = new Function(objectiveCoefficients);
        ObjectiveFunction objFunc = new ObjectiveFunction(ExtremumEnum.MINIMIZE, objectiveFunction);

        // ============================================================
        // CONSTRAINTS - ALL 18 VARIABLES REFERENCED
        // Using LESSERTHAN with NEGATIVE RHS:
        // -a0*X0 - a1*X1 - ... - a17*X17 <= -b
        // ============================================================
        ArrayList<Constraint> constraints = new ArrayList<>();

        // ---- ENGLISH COVERAGE CONSTRAINTS ----

        // Constraint 0: Shift 1 (7-9 AM) - Xef1A >= 6
        // LESSERTHAN form: -Xef1A <= -6
        HashMap<Integer, Double> c0Coeff = new HashMap<>();
        c0Coeff.put(Xef1A, 1.0);
        c0Coeff.put(Xef1B, 0.0);
        c0Coeff.put(Xef2A, 0.0);
        c0Coeff.put(Xef2B, 0.0);
        c0Coeff.put(Xef3A, 0.0);
        c0Coeff.put(Xef3B, 0.0);
        c0Coeff.put(Xef4A, 0.0);
        c0Coeff.put(Xef4B, 0.0);
        c0Coeff.put(Xep5, 0.0);
        c0Coeff.put(Xep6, 0.0);
        c0Coeff.put(Ysf1A, 0.0);
        c0Coeff.put(Ysf1B, 0.0);
        c0Coeff.put(Ysf2A, 0.0);
        c0Coeff.put(Ysf2B, 0.0);
        c0Coeff.put(Ysf3A, 0.0);
        c0Coeff.put(Ysf3B, 0.0);
        c0Coeff.put(Ysf4A, 0.0);
        c0Coeff.put(Ysf4B, 0.0);
        Function c0Func = new Function(c0Coeff);
        Constraint c0 = new Constraint(0, c0Func, ComparingOperator.LESSERTHAN, -6.0);
        constraints.add(c0);

        // Constraint 1: Shift 2 (9-11 AM) - Xef1B + Xef2A >= 12
        // LESSERTHAN form: -Xef1B - Xef2A <= -12
        HashMap<Integer, Double> c1Coeff = new HashMap<>();
        c1Coeff.put(Xef1A, 0.0);
        c1Coeff.put(Xef1B, 1.0);
        c1Coeff.put(Xef2A, 1.0);
        c1Coeff.put(Xef2B, 0.0);
        c1Coeff.put(Xef3A, 0.0);
        c1Coeff.put(Xef3B, 0.0);
        c1Coeff.put(Xef4A, 0.0);
        c1Coeff.put(Xef4B, 0.0);
        c1Coeff.put(Xep5, 0.0);
        c1Coeff.put(Xep6, 0.0);
        c1Coeff.put(Ysf1A, 0.0);
        c1Coeff.put(Ysf1B, 0.0);
        c1Coeff.put(Ysf2A, 0.0);
        c1Coeff.put(Ysf2B, 0.0);
        c1Coeff.put(Ysf3A, 0.0);
        c1Coeff.put(Ysf3B, 0.0);
        c1Coeff.put(Ysf4A, 0.0);
        c1Coeff.put(Ysf4B, 0.0);
        Function c1Func = new Function(c1Coeff);
        Constraint c1 = new Constraint(1, c1Func, ComparingOperator.LESSERTHAN, -12.0);
        constraints.add(c1);

        // Constraint 2: Shift 3 (11-1 PM) - Xef1A + Xef2B + Xef3A >= 10
        // LESSERTHAN form: -Xef1A - Xef2B - Xef3A <= -10
        HashMap<Integer, Double> c2Coeff = new HashMap<>();
        c2Coeff.put(Xef1A, 1.0);
        c2Coeff.put(Xef1B, 0.0);
        c2Coeff.put(Xef2A, 0.0);
        c2Coeff.put(Xef2B, 1.0);
        c2Coeff.put(Xef3A, 1.0);
        c2Coeff.put(Xef3B, 0.0);
        c2Coeff.put(Xef4A, 0.0);
        c2Coeff.put(Xef4B, 0.0);
        c2Coeff.put(Xep5, 0.0);
        c2Coeff.put(Xep6, 0.0);
        c2Coeff.put(Ysf1A, 0.0);
        c2Coeff.put(Ysf1B, 0.0);
        c2Coeff.put(Ysf2A, 0.0);
        c2Coeff.put(Ysf2B, 0.0);
        c2Coeff.put(Ysf3A, 0.0);
        c2Coeff.put(Ysf3B, 0.0);
        c2Coeff.put(Ysf4A, 0.0);
        c2Coeff.put(Ysf4B, 0.0);
        Function c2Func = new Function(c2Coeff);
        Constraint c2 = new Constraint(2, c2Func, ComparingOperator.LESSERTHAN, -10.0);
        constraints.add(c2);

        // Constraint 3: Shift 4 (1-3 PM) - Xef1B + Xef2A + Xef3B + Xef4A >= 13
        // LESSERTHAN form: -Xef1B - Xef2A - Xef3B - Xef4A <= -13
        HashMap<Integer, Double> c3Coeff = new HashMap<>();
        c3Coeff.put(Xef1A, 0.0);
        c3Coeff.put(Xef1B, 1.0);
        c3Coeff.put(Xef2A, 1.0);
        c3Coeff.put(Xef2B, 0.0);
        c3Coeff.put(Xef3A, 0.0);
        c3Coeff.put(Xef3B, 1.0);
        c3Coeff.put(Xef4A, 1.0);
        c3Coeff.put(Xef4B, 0.0);
        c3Coeff.put(Xep5, 0.0);
        c3Coeff.put(Xep6, 0.0);
        c3Coeff.put(Ysf1A, 0.0);
        c3Coeff.put(Ysf1B, 0.0);
        c3Coeff.put(Ysf2A, 0.0);
        c3Coeff.put(Ysf2B, 0.0);
        c3Coeff.put(Ysf3A, 0.0);
        c3Coeff.put(Ysf3B, 0.0);
        c3Coeff.put(Ysf4A, 0.0);
        c3Coeff.put(Ysf4B, 0.0);
        Function c3Func = new Function(c3Coeff);
        Constraint c3 = new Constraint(3, c3Func, ComparingOperator.LESSERTHAN, -13.0);
        constraints.add(c3);

        // Constraint 4: Shift 5 (3-5 PM) - Xef2B + Xef3A + Xef4B + Xep5 >= 11
        // LESSERTHAN form: -Xef2B - Xef3A - Xef4B - Xep5 <= -11
        HashMap<Integer, Double> c4Coeff = new HashMap<>();
        c4Coeff.put(Xef1A, 0.0);
        c4Coeff.put(Xef1B, 0.0);
        c4Coeff.put(Xef2A, 0.0);
        c4Coeff.put(Xef2B, 1.0);
        c4Coeff.put(Xef3A, 1.0);
        c4Coeff.put(Xef3B, 0.0);
        c4Coeff.put(Xef4A, 0.0);
        c4Coeff.put(Xef4B, 1.0);
        c4Coeff.put(Xep5, 1.0);
        c4Coeff.put(Xep6, 0.0);
        c4Coeff.put(Ysf1A, 0.0);
        c4Coeff.put(Ysf1B, 0.0);
        c4Coeff.put(Ysf2A, 0.0);
        c4Coeff.put(Ysf2B, 0.0);
        c4Coeff.put(Ysf3A, 0.0);
        c4Coeff.put(Ysf3B, 0.0);
        c4Coeff.put(Ysf4A, 0.0);
        c4Coeff.put(Ysf4B, 0.0);
        Function c4Func = new Function(c4Coeff);
        Constraint c4 = new Constraint(4, c4Func, ComparingOperator.LESSERTHAN, -11.0);
        constraints.add(c4);

        // Constraint 5: Shift 6 (5-7 PM) - Xef3B + Xef4A + Xep5 + Xep6 >= 5
        // LESSERTHAN form: -Xef3B - Xef4A - Xep5 - Xep6 <= -5
        HashMap<Integer, Double> c5Coeff = new HashMap<>();
        c5Coeff.put(Xef1A, 0.0);
        c5Coeff.put(Xef1B, 0.0);
        c5Coeff.put(Xef2A, 0.0);
        c5Coeff.put(Xef2B, 0.0);
        c5Coeff.put(Xef3A, 0.0);
        c5Coeff.put(Xef3B, 1.0);
        c5Coeff.put(Xef4A, 1.0);
        c5Coeff.put(Xef4B, 0.0);
        c5Coeff.put(Xep5, 1.0);
        c5Coeff.put(Xep6, 1.0);
        c5Coeff.put(Ysf1A, 0.0);
        c5Coeff.put(Ysf1B, 0.0);
        c5Coeff.put(Ysf2A, 0.0);
        c5Coeff.put(Ysf2B, 0.0);
        c5Coeff.put(Ysf3A, 0.0);
        c5Coeff.put(Ysf3B, 0.0);
        c5Coeff.put(Ysf4A, 0.0);
        c5Coeff.put(Ysf4B, 0.0);
        Function c5Func = new Function(c5Coeff);
        Constraint c5 = new Constraint(5, c5Func, ComparingOperator.LESSERTHAN, -5.0);
        constraints.add(c5);

        // Constraint 6: Shift 7 (7-9 PM) - Xef4B + Xep6 >= 2
        // LESSERTHAN form: -Xef4B - Xep6 <= -2
        HashMap<Integer, Double> c6Coeff = new HashMap<>();
        c6Coeff.put(Xef1A, 0.0);
        c6Coeff.put(Xef1B, 0.0);
        c6Coeff.put(Xef2A, 0.0);
        c6Coeff.put(Xef2B, 0.0);
        c6Coeff.put(Xef3A, 0.0);
        c6Coeff.put(Xef3B, 0.0);
        c6Coeff.put(Xef4A, 0.0);
        c6Coeff.put(Xef4B, 1.0);
        c6Coeff.put(Xep5, 0.0);
        c6Coeff.put(Xep6, 1.0);
        c6Coeff.put(Ysf1A, 0.0);
        c6Coeff.put(Ysf1B, 0.0);
        c6Coeff.put(Ysf2A, 0.0);
        c6Coeff.put(Ysf2B, 0.0);
        c6Coeff.put(Ysf3A, 0.0);
        c6Coeff.put(Ysf3B, 0.0);
        c6Coeff.put(Ysf4A, 0.0);
        c6Coeff.put(Ysf4B, 0.0);
        Function c6Func = new Function(c6Coeff);
        Constraint c6 = new Constraint(6, c6Func, ComparingOperator.LESSERTHAN, -2.0);
        constraints.add(c6);

        // ---- SPANISH COVERAGE CONSTRAINTS ----

        // Constraint 7: Shift 1 (7-9 AM) - Ysf1A >= 2
        // LESSERTHAN form: -Ysf1A <= -2
        HashMap<Integer, Double> c7Coeff = new HashMap<>();
        c7Coeff.put(Xef1A, 0.0);
        c7Coeff.put(Xef1B, 0.0);
        c7Coeff.put(Xef2A, 0.0);
        c7Coeff.put(Xef2B, 0.0);
        c7Coeff.put(Xef3A, 0.0);
        c7Coeff.put(Xef3B, 0.0);
        c7Coeff.put(Xef4A, 0.0);
        c7Coeff.put(Xef4B, 0.0);
        c7Coeff.put(Xep5, 0.0);
        c7Coeff.put(Xep6, 0.0);
        c7Coeff.put(Ysf1A, 1.0);
        c7Coeff.put(Ysf1B, 0.0);
        c7Coeff.put(Ysf2A, 0.0);
        c7Coeff.put(Ysf2B, 0.0);
        c7Coeff.put(Ysf3A, 0.0);
        c7Coeff.put(Ysf3B, 0.0);
        c7Coeff.put(Ysf4A, 0.0);
        c7Coeff.put(Ysf4B, 0.0);
        Function c7Func = new Function(c7Coeff);
        Constraint c7 = new Constraint(7, c7Func, ComparingOperator.LESSERTHAN, -2.0);
        constraints.add(c7);

        // Constraint 8: Shift 2 (9-11 AM) - Ysf1B + Ysf2A >= 3
        // LESSERTHAN form: -Ysf1B - Ysf2A <= -3
        HashMap<Integer, Double> c8Coeff = new HashMap<>();
        c8Coeff.put(Xef1A, 0.0);
        c8Coeff.put(Xef1B, 0.0);
        c8Coeff.put(Xef2A, 0.0);
        c8Coeff.put(Xef2B, 0.0);
        c8Coeff.put(Xef3A, 0.0);
        c8Coeff.put(Xef3B, 0.0);
        c8Coeff.put(Xef4A, 0.0);
        c8Coeff.put(Xef4B, 0.0);
        c8Coeff.put(Xep5, 0.0);
        c8Coeff.put(Xep6, 0.0);
        c8Coeff.put(Ysf1A, 0.0);
        c8Coeff.put(Ysf1B, 1.0);
        c8Coeff.put(Ysf2A, 1.0);
        c8Coeff.put(Ysf2B, 0.0);
        c8Coeff.put(Ysf3A, 0.0);
        c8Coeff.put(Ysf3B, 0.0);
        c8Coeff.put(Ysf4A, 0.0);
        c8Coeff.put(Ysf4B, 0.0);
        Function c8Func = new Function(c8Coeff);
        Constraint c8 = new Constraint(8, c8Func, ComparingOperator.LESSERTHAN, -3.0);
        constraints.add(c8);

        // Constraint 9: Shift 3 (11-1 PM) - Ysf1A + Ysf2B + Ysf3A >= 3
        // LESSERTHAN form: -Ysf1A - Ysf2B - Ysf3A <= -3
        HashMap<Integer, Double> c9Coeff = new HashMap<>();
        c9Coeff.put(Xef1A, 0.0);
        c9Coeff.put(Xef1B, 0.0);
        c9Coeff.put(Xef2A, 0.0);
        c9Coeff.put(Xef2B, 0.0);
        c9Coeff.put(Xef3A, 0.0);
        c9Coeff.put(Xef3B, 0.0);
        c9Coeff.put(Xef4A, 0.0);
        c9Coeff.put(Xef4B, 0.0);
        c9Coeff.put(Xep5, 0.0);
        c9Coeff.put(Xep6, 0.0);
        c9Coeff.put(Ysf1A, 1.0);
        c9Coeff.put(Ysf1B, 0.0);
        c9Coeff.put(Ysf2A, 0.0);
        c9Coeff.put(Ysf2B, 1.0);
        c9Coeff.put(Ysf3A, 1.0);
        c9Coeff.put(Ysf3B, 0.0);
        c9Coeff.put(Ysf4A, 0.0);
        c9Coeff.put(Ysf4B, 0.0);
        Function c9Func = new Function(c9Coeff);
        Constraint c9 = new Constraint(9, c9Func, ComparingOperator.LESSERTHAN, -3.0);
        constraints.add(c9);

        // Constraint 10: Shift 4 (1-3 PM) - Ysf1B + Ysf2A + Ysf3B + Ysf4A >= 4
        // LESSERTHAN form: -Ysf1B - Ysf2A - Ysf3B - Ysf4A <= -4
        HashMap<Integer, Double> c10Coeff = new HashMap<>();
        c10Coeff.put(Xef1A, 0.0);
        c10Coeff.put(Xef1B, 0.0);
        c10Coeff.put(Xef2A, 0.0);
        c10Coeff.put(Xef2B, 0.0);
        c10Coeff.put(Xef3A, 0.0);
        c10Coeff.put(Xef3B, 0.0);
        c10Coeff.put(Xef4A, 0.0);
        c10Coeff.put(Xef4B, 0.0);
        c10Coeff.put(Xep5, 0.0);
        c10Coeff.put(Xep6, 0.0);
        c10Coeff.put(Ysf1A, 0.0);
        c10Coeff.put(Ysf1B, 1.0);
        c10Coeff.put(Ysf2A, 1.0);
        c10Coeff.put(Ysf2B, 0.0);
        c10Coeff.put(Ysf3A, 0.0);
        c10Coeff.put(Ysf3B, 1.0);
        c10Coeff.put(Ysf4A, 1.0);
        c10Coeff.put(Ysf4B, 0.0);
        Function c10Func = new Function(c10Coeff);
        Constraint c10 = new Constraint(10, c10Func, ComparingOperator.LESSERTHAN, -4.0);
        constraints.add(c10);

        // Constraint 11: Shift 5 (3-5 PM) - Ysf2B + Ysf3A + Ysf4B >= 3
        // LESSERTHAN form: -Ysf2B - Ysf3A - Ysf4B <= -3
        HashMap<Integer, Double> c11Coeff = new HashMap<>();
        c11Coeff.put(Xef1A, 0.0);
        c11Coeff.put(Xef1B, 0.0);
        c11Coeff.put(Xef2A, 0.0);
        c11Coeff.put(Xef2B, 0.0);
        c11Coeff.put(Xef3A, 0.0);
        c11Coeff.put(Xef3B, 0.0);
        c11Coeff.put(Xef4A, 0.0);
        c11Coeff.put(Xef4B, 0.0);
        c11Coeff.put(Xep5, 0.0);
        c11Coeff.put(Xep6, 0.0);
        c11Coeff.put(Ysf1A, 0.0);
        c11Coeff.put(Ysf1B, 0.0);
        c11Coeff.put(Ysf2A, 0.0);
        c11Coeff.put(Ysf2B, 1.0);
        c11Coeff.put(Ysf3A, 1.0);
        c11Coeff.put(Ysf3B, 0.0);
        c11Coeff.put(Ysf4A, 0.0);
        c11Coeff.put(Ysf4B, 1.0);
        Function c11Func = new Function(c11Coeff);
        Constraint c11 = new Constraint(11, c11Func, ComparingOperator.LESSERTHAN, -3.0);
        constraints.add(c11);

        // Constraint 12: Shift 6 (5-7 PM) - Ysf3B + Ysf4A >= 2
        // LESSERTHAN form: -Ysf3B - Ysf4A <= -2
        HashMap<Integer, Double> c12Coeff = new HashMap<>();
        c12Coeff.put(Xef1A, 0.0);
        c12Coeff.put(Xef1B, 0.0);
        c12Coeff.put(Xef2A, 0.0);
        c12Coeff.put(Xef2B, 0.0);
        c12Coeff.put(Xef3A, 0.0);
        c12Coeff.put(Xef3B, 0.0);
        c12Coeff.put(Xef4A, 0.0);
        c12Coeff.put(Xef4B, 0.0);
        c12Coeff.put(Xep5, 0.0);
        c12Coeff.put(Xep6, 0.0);
        c12Coeff.put(Ysf1A, 0.0);
        c12Coeff.put(Ysf1B, 0.0);
        c12Coeff.put(Ysf2A, 0.0);
        c12Coeff.put(Ysf2B, 0.0);
        c12Coeff.put(Ysf3A, 0.0);
        c12Coeff.put(Ysf3B, 1.0);
        c12Coeff.put(Ysf4A, 1.0);
        c12Coeff.put(Ysf4B, 0.0);
        Function c12Func = new Function(c12Coeff);
        Constraint c12 = new Constraint(12, c12Func, ComparingOperator.LESSERTHAN, -2.0);
        constraints.add(c12);

        // Constraint 13: Shift 7 (7-9 PM) - Ysf4B >= 1
        // LESSERTHAN form: -Ysf4B <= -1
        HashMap<Integer, Double> c13Coeff = new HashMap<>();
        c13Coeff.put(Xef1A, 0.0);
        c13Coeff.put(Xef1B, 0.0);
        c13Coeff.put(Xef2A, 0.0);
        c13Coeff.put(Xef2B, 0.0);
        c13Coeff.put(Xef3A, 0.0);
        c13Coeff.put(Xef3B, 0.0);
        c13Coeff.put(Xef4A, 0.0);
        c13Coeff.put(Xef4B, 0.0);
        c13Coeff.put(Xep5, 0.0);
        c13Coeff.put(Xep6, 0.0);
        c13Coeff.put(Ysf1A, 0.0);
        c13Coeff.put(Ysf1B, 0.0);
        c13Coeff.put(Ysf2A, 0.0);
        c13Coeff.put(Ysf2B, 0.0);
        c13Coeff.put(Ysf3A, 0.0);
        c13Coeff.put(Ysf3B, 0.0);
        c13Coeff.put(Ysf4A, 0.0);
        c13Coeff.put(Ysf4B, 1.0);
        Function c13Func = new Function(c13Coeff);
        Constraint c13 = new Constraint(13, c13Func, ComparingOperator.LESSERTHAN, -1.0);
        constraints.add(c13);

        // ============================================================
        // CREATE THE LINEAR PROBLEM
        // ============================================================
        LinearProblem lp = new LinearProblem(objFunc, constraints);

        System.out.println("========== CALL CENTER STAFFING LINEAR PROGRAM ==========\n");
        lp.printLinearProblem();

        
        SolvingDoublePhaseMinimize sdpm= new  SolvingDoublePhaseMinimize(lp) ;
        sdpm.getBaumatrixdoublephase().getBase_phase_II();
		sdpm.getBaumatrixdoublephase().getC_base_phase_II();
		sdpm.getBaumatrixdoublephase().getC_extended_II();
		sdpm.getBaumatrixdoublephase().getZj_cj_phase_II();
		IntVector.display_vector(sdpm.getBaumatrixdoublephase().getBase_phase_II());
		System.out.println();
		System.out.println();
		Vector.display_vector(sdpm.getBaumatrixdoublephase().getC_base_phase_II());
		System.out.println();
		System.out.println();
		Vector.display_vector_h(sdpm.getBaumatrixdoublephase().getC_extended_II());
		System.out.println();
		System.out.println();
		Vector.display_vector_h(sdpm.getBaumatrixdoublephase().getZj_cj_phase_II());
	    /*sdpm.apply_simplex_phase_II();;
	    sdpm.compute_goal_function();
	    sdpm.enumerate_based_variables_values();*/
		
		sdpm.apply();
	   	Matrix.displayMatrix(sdpm.getBaumatrixdoublephase().getAugmented_matrix_phase_II());
    	
    	
        
        
        
       /* sdpm.apply_simplex_phase_II();
        sdpm.compute_goal_function();
        sdpm.enumerate_based_variables_values();*/


	}




	public BuildingAugmentedMatrixDoublePhase getBaumatrixdoublephase() {
		return baumatrixdoublephase;
	}




	public void setBaumatrixdoublephase(BuildingAugmentedMatrixDoublePhase baumatrixdoublephase) {
		this.baumatrixdoublephase = baumatrixdoublephase;
	}




	public LinearProblem getLp() {
		return lp;
	}




	public void setLp(LinearProblem lp) {
		this.lp = lp;
	}




	public Boolean getOptimality() {
		return optimality;
	}




	public void setOptimality(Boolean optimality) {
		this.optimality = optimality;
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




	public ApplyingGaussPivot getApg() {
		return apg;
	}




	public void setApg(ApplyingGaussPivot apg) {
		this.apg = apg;
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

	public Pivot getPivot() {
		return pivot;
	}

	public void setPivot(Pivot pivot) {
		this.pivot = pivot;
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
    
    
	
	
	
	
}