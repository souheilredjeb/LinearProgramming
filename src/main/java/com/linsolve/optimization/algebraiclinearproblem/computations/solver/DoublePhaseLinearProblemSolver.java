package com.linsolve.optimization.algebraiclinearproblem.computations.solver;

import java.util.ArrayList;
import java.util.HashMap;

import com.linsolve.optimization.algebraiclinearproblem.computations.SolvingDoublePhaseMaximize;
import com.linsolve.optimization.algebraiclinearproblem.computations.SolvingDoublePhaseMinimize;
import com.linsolve.optimization.linearproblem.ComparingOperator;
import com.linsolve.optimization.linearproblem.Constraint;
import com.linsolve.optimization.linearproblem.ExtremumEnum;
import com.linsolve.optimization.linearproblem.Function;
import com.linsolve.optimization.linearproblem.LinearProblem;
import com.linsolve.optimization.linearproblem.ObjectiveFunction;

public class DoublePhaseLinearProblemSolver
{
	private LinearProblem lp;
	private SolvingDoublePhaseMinimize sdpmin;
	private SolvingDoublePhaseMaximize sdpmax;
	
	

	public DoublePhaseLinearProblemSolver(LinearProblem lp) throws Exception {
		super();
		this.lp = lp;
		if(lp.getObjective_function().getExtremum().equals(ExtremumEnum.MAXIMIZE))
		{
			this.sdpmax=new SolvingDoublePhaseMaximize(lp);
		}
		else if(lp.getObjective_function().getExtremum().equals(ExtremumEnum.MINIMIZE))
		{
			this.sdpmin=new SolvingDoublePhaseMinimize(lp);
		}
	}
	
	public void apply() throws Exception
	{
		if(lp.getObjective_function().getExtremum().equals(ExtremumEnum.MAXIMIZE))
		{
			this.sdpmax.apply();
		}
		else if(lp.getObjective_function().getExtremum().equals(ExtremumEnum.MINIMIZE))
		{
			this.sdpmin.apply();
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
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
	        DoublePhaseLinearProblemSolver dplps=new DoublePhaseLinearProblemSolver(lp);
	        dplps.apply();
	        
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

	        objectiveFunction = new Function(objectiveCoefficients);
	        objFunc = new ObjectiveFunction(ExtremumEnum.MINIMIZE, objectiveFunction);

	        // ============================================================
	        // CONSTRAINTS - ALL 18 VARIABLES REFERENCED
	        // Using LESSERTHAN with NEGATIVE RHS:
	        // -a0*X0 - a1*X1 - ... - a17*X17 <= -b
	        // ============================================================
	         constraints = new ArrayList<>();

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
	        Constraint c01 = new Constraint(0, c0Func, ComparingOperator.LESSERTHAN, -6.0);
	        constraints.add(c01);

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
	        Constraint c1_1 = new Constraint(1, c1Func, ComparingOperator.LESSERTHAN, -12.0);
	        constraints.add(c1_1);

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
	        Constraint c2_1 = new Constraint(2, c2Func, ComparingOperator.LESSERTHAN, -10.0);
	        constraints.add(c2_1);

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
	        Constraint c3_1 = new Constraint(3, c3Func, ComparingOperator.LESSERTHAN, -13.0);
	        constraints.add(c3_1);

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
	        Constraint c4_1 = new Constraint(4, c4Func, ComparingOperator.LESSERTHAN, -11.0);
	        constraints.add(c4_1);

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
	        Constraint c5_1 = new Constraint(5, c5Func, ComparingOperator.LESSERTHAN, -5.0);
	        constraints.add(c5_1);

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
	        Constraint c6_1 = new Constraint(6, c6Func, ComparingOperator.LESSERTHAN, -2.0);
	        constraints.add(c6_1);

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
	        Constraint c7_1 = new Constraint(7, c7Func, ComparingOperator.LESSERTHAN, -2.0);
	        constraints.add(c7_1);

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
	        Constraint c8_1 = new Constraint(8, c8Func, ComparingOperator.LESSERTHAN, -3.0);
	        constraints.add(c8_1);

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
	        Constraint c9_1 = new Constraint(9, c9Func, ComparingOperator.LESSERTHAN, -3.0);
	        constraints.add(c9_1);

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
	        Constraint c10_1 = new Constraint(10, c10Func, ComparingOperator.LESSERTHAN, -4.0);
	        constraints.add(c10_1);

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
	        Constraint c11_1 = new Constraint(11, c11Func, ComparingOperator.LESSERTHAN, -3.0);
	        constraints.add(c11_1);

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
	        Constraint c12_1 = new Constraint(12, c12Func, ComparingOperator.LESSERTHAN, -2.0);
	        constraints.add(c12_1);

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
	        Constraint c13_1 = new Constraint(13, c13Func, ComparingOperator.LESSERTHAN, -1.0);
	        constraints.add(c13_1);

	        // ============================================================
	        // CREATE THE LINEAR PROBLEM
	        // ============================================================
	        lp= new LinearProblem(objFunc, constraints);
	        dplps=new DoublePhaseLinearProblemSolver(lp);
	        dplps.apply();
	        
	}

	public LinearProblem getLp() {
		return lp;
	}

	public void setLp(LinearProblem lp) {
		this.lp = lp;
	}

	public SolvingDoublePhaseMinimize getSdpmin() {
		return sdpmin;
	}

	public void setSdpmin(SolvingDoublePhaseMinimize sdpmin) {
		this.sdpmin = sdpmin;
	}

	public SolvingDoublePhaseMaximize getSdpmax() {
		return sdpmax;
	}

	public void setSdpmax(SolvingDoublePhaseMaximize sdpmax) {
		this.sdpmax = sdpmax;
	}
	
	

}
