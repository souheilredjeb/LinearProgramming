package com.linsolve.optimization.algebraiclinearproblem.computations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.linsolve.optimization.algebraiccomponents.Matrix;
import com.linsolve.optimization.algebraiccomponents.Vector;
import com.linsolve.optimization.algebraiclinearproblem.BuildingAugmentedMatrixAuxiliaryProblem;
import com.linsolve.optimization.linearproblem.ComparingOperator;
import com.linsolve.optimization.linearproblem.Constraint;
import com.linsolve.optimization.linearproblem.ExtremumEnum;
import com.linsolve.optimization.linearproblem.Function;
import com.linsolve.optimization.linearproblem.LinearProblem;
import com.linsolve.optimization.linearproblem.ObjectiveFunction;
import com.linsolve.optimization.operator.ApplyingGaussPivot;
import com.linsolve.optimization.operator.Pivot;

public class SolvingAuxiliaryProblemSimplexDoublePhase 
{
	private LinearProblem lp;
	private BuildingAugmentedMatrixAuxiliaryProblem bamaux;
	private Pivot pivot;
	private Integer pivot_column;
	private Integer pivot_line;
	private Double  pivot_value;
	private Vector b_comparator_vector;
	private Boolean optimality;
	private ApplyingGaussPivot apg;
	
	public SolvingAuxiliaryProblemSimplexDoublePhase(LinearProblem lp) {
		super();
		this.lp = lp;
		this.bamaux=new BuildingAugmentedMatrixAuxiliaryProblem(lp);
		this.optimality=Boolean.FALSE;
		
	}

	public void check_optimality()
	{
		this.optimality=Boolean.TRUE;
		for(int j=0; j< this.bamaux.getZj_cj().getT().length;j++)
		{
			if( this.bamaux.getZj_cj().getT()[j].compareTo(Double.valueOf(0.0))>0)
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
			for(int j=0;j<this.bamaux.getZj_cj().getT().length;j++)
			{	
				if(this.bamaux.getZj_cj().getT()[j].compareTo(minimum_value)>0)
				{
					pivot_column=j;
					minimum_value=this.bamaux.getZj_cj().getT()[j];
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
		Double[] array_b_comparator= new Double[this.bamaux.getBic().getM()];		
		for(int i=0; i<this.bamaux.getBic().getM();i++)
		{
			 if(this.bamaux.getAuxiliary_problem_matrix().getT()[i][this.pivot_column]>0)
			 {
				 array_b_comparator[i]=this.bamaux.getAuxiliary_problem_matrix().getT()[i][this.bamaux.getAugmented_n()]/this.bamaux.getAuxiliary_problem_matrix().getT()[i][this.pivot_column];
			 }
		}	
		b_comparator_vector=new Vector(array_b_comparator);	
	}
	
	public void compute_pivot_line()
	{
		Double min_comp_vector=Double.MAX_VALUE;
		Integer pivot_line=null;
		for(int i=0; i<this.bamaux.getBic().getM();i++)
		{
			if(this.b_comparator_vector.getT()[i] !=null && this.b_comparator_vector.getT()[i]<=min_comp_vector)
			{
				min_comp_vector=this.b_comparator_vector.getT()[i];
				pivot_line=i;
			}		
		}
		this.pivot_line=pivot_line;	
	}
	
	public void reevaluate_base()
	{
		Integer[] b=this.bamaux.getBase().getInt_vect();	
		Double[] c= this.bamaux.getC_base().getT();
		for(int i=0;i<this.bamaux.getBase().getInt_vect().length;i++)
		{
			if(i==Integer.valueOf(pivot_line))
			{
				b[i]=pivot_column;
				c[i]=this.bamaux.getC_extended_I().getT()[this.pivot_column];
			}
		}
		this.bamaux.getBase().setInt_vect(b);
		this.bamaux.getC_base().setT(c);
	}
	
	public void apply_pivot_gauss()
	{
		pivot_value=this.bamaux.getAuxiliary_problem_matrix().getT()[this.pivot_line][this.pivot_column];
		this.pivot= new Pivot(pivot_line, pivot_column, pivot_value);	
		this.apg=new ApplyingGaussPivot(this.bamaux.getAuxiliary_problem_matrix(), pivot);
		this.apg.apply();
        this.bamaux.setAuxiliary_problem_matrix(this.apg.getMatrix());
	}
	
	public void reevaluate_zj_cj()
	{
		 Double[] zj__cj=this.bamaux.getZj_cj().getT();
		 for(int j=0;j<this.bamaux.getZj_cj().getT().length;j++)
		 { 
			 zj__cj[j]=	0.0;			
		 }	
		 for(int j=0;j<this.bamaux.getZj_cj().getT().length;j++)
		 { 
			 for(int i=0;i<this.bamaux.getC_base().getT().length;i++)
			 { 
				 zj__cj[j]+=this.bamaux.getC_base().getT()[i]*this.bamaux.getAuxiliary_problem_matrix().getT()[i][j];
			 } 
		 }
		 for(int j=0;j<this.bamaux.getC_extended_I().getT().length;j++) 
		 {
			 zj__cj[j]=zj__cj[j]-this.bamaux.getC_extended_I().getT()[j];
		 }		 
		 this.bamaux.getZj_cj().setT(zj__cj);
	}
	
	
	public void apply_simplex_single_phase() throws Exception {
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
		Double[] b_augmented_matrix=new Double[this.bamaux.getBic().getM()];
		for(int i=0;i<this.bamaux.getBic().getM();i++)
		{
			b_augmented_matrix[i]=this.bamaux.getAuxiliary_problem_matrix().getT()[i][this.bamaux.getAugmented_n()-1];
		}
		
		for(int i=0;i<this.bamaux.getBic().getM();i++)
		{
			z=z+this.bamaux.getC_base().getT()[i]*b_augmented_matrix[i];
		}
		System.out.println("Z Function : "+z);
		return z;	
	}
	
	public Map<Integer, Double> enumerate_based_variables_values()
	{
		Map<Integer, Double> hm= new HashMap<>();
		for(int i=0;i<this.bamaux.getBic().getM();i++)
		{
			hm.put(this.bamaux.getBase().getInt_vect()[i], this.bamaux.getAuxiliary_problem_matrix().getT()[i][this.bamaux.getAugmented_n()-1]);
			System.out.println("Var x"+this.bamaux.getBase().getInt_vect()[i]+" "+this.bamaux.getAuxiliary_problem_matrix().getT()[i][this.bamaux.getAugmented_n()-1]+" ");
		}	
		return hm;
	}

	public static void main(String[] args) throws Exception {
		
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
		
				
				
				ExtremumEnum extremum=ExtremumEnum.MINIMIZE;
				HashMap<Integer, Double> f=new HashMap<Integer, Double>();
				f.put(0, 1.0);
				f.put(1, 1.0);
				Function function = new Function(f);
				ObjectiveFunction objective_function= new ObjectiveFunction(extremum, function);
				HashMap<Integer, Double> fc= new HashMap<Integer,Double>();
				fc.put(0, 1.0);
				fc.put(1, 2.0);
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
				LinearProblem lp0= new LinearProblem(objective_function, constraints);
				SolvingAuxiliaryProblemSimplexDoublePhase sapsdp=new SolvingAuxiliaryProblemSimplexDoublePhase(lp0);		
				sapsdp.apply_simplex_single_phase();
				Matrix.displayMatrix(sapsdp.getBamaux().getAuxiliary_problem_matrix());
				System.out.println();
				System.out.println();
				System.out.println();
				//Vector.display_vector(sapsdp.getBamaux().getZj_cj());
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();	
				sapsdp.compute_goal_function();
				sapsdp.enumerate_based_variables_values();
				
				
				
				
				
				
				
				
				
				
	}



	public LinearProblem getLp() {
		return lp;
	}



	public void setLp(LinearProblem lp) {
		this.lp = lp;
	}



	public BuildingAugmentedMatrixAuxiliaryProblem getBamaux() {
		return bamaux;
	}



	public void setBamaux(BuildingAugmentedMatrixAuxiliaryProblem bamaux) {
		this.bamaux = bamaux;
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

	public Vector getB_comparator_vector() {
		return b_comparator_vector;
	}

	public void setB_comparator_vector(Vector b_comparator_vector) {
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
