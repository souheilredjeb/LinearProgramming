package com.linsolve.optimization.algebraiclinearproblem.computations;

import java.util.ArrayList;
import java.util.HashMap;

import com.linsolve.optimization.algebraiccomponents.Matrix;
import com.linsolve.optimization.linearproblem.ComparingOperator;
import com.linsolve.optimization.linearproblem.Constraint;
import com.linsolve.optimization.linearproblem.ExtremumEnum;
import com.linsolve.optimization.linearproblem.Function;
import com.linsolve.optimization.linearproblem.LinearProblem;
import com.linsolve.optimization.linearproblem.ObjectiveFunction;






public class StudentsToSchoolsSolver {

public static void main(String[] args) throws Exception {
        
        // ============================================================
        // VARIABLE INDICES (0 to 17) - ALL 18 VARIABLES
        // ============================================================
        int x11 = 0;   // Area 1 -> School 1
        int x12 = 1;   // Area 1 -> School 2
        int x13 = 2;   // Area 1 -> School 3
        int x21 = 3;   // Area 2 -> School 1 (INFEASIBLE)
        int x22 = 4;   // Area 2 -> School 2
        int x23 = 5;   // Area 2 -> School 3
        int x31 = 6;   // Area 3 -> School 1
        int x32 = 7;   // Area 3 -> School 2
        int x33 = 8;   // Area 3 -> School 3
        int x41 = 9;   // Area 4 -> School 1
        int x42 = 10;  // Area 4 -> School 2
        int x43 = 11;  // Area 4 -> School 3 (INFEASIBLE)
        int x51 = 12;  // Area 5 -> School 1 (INFEASIBLE)
        int x52 = 13;  // Area 5 -> School 2
        int x53 = 14;  // Area 5 -> School 3 (INFEASIBLE)
        int x61 = 15;  // Area 6 -> School 1
        int x62 = 16;  // Area 6 -> School 2
        int x63 = 17;  // Area 6 -> School 3

        // ============================================================
        // OBJECTIVE FUNCTION: Minimize Total Bussing Cost
        // ============================================================
        ExtremumEnum extremum = ExtremumEnum.MINIMIZE;
        
        HashMap<Integer, Double> objectiveCoeffs = new HashMap<>();
        
        // Area 1
        objectiveCoeffs.put(x11, 300.0);
        objectiveCoeffs.put(x12, 0.0);
        objectiveCoeffs.put(x13, 700.0);
        
        // Area 2
        objectiveCoeffs.put(x21, 0.0);     // infeasible
        objectiveCoeffs.put(x22, 400.0);
        objectiveCoeffs.put(x23, 500.0);
        
        // Area 3
        objectiveCoeffs.put(x31, 600.0);
        objectiveCoeffs.put(x32, 300.0);
        objectiveCoeffs.put(x33, 200.0);
        
        // Area 4
        objectiveCoeffs.put(x41, 200.0);
        objectiveCoeffs.put(x42, 500.0);
        objectiveCoeffs.put(x43, 0.0);     // infeasible
        
        // Area 5
        objectiveCoeffs.put(x51, 0.0);     // infeasible
        objectiveCoeffs.put(x52, 400.0);
        objectiveCoeffs.put(x53, 0.0);     // infeasible
        
        // Area 6
        objectiveCoeffs.put(x61, 500.0);
        objectiveCoeffs.put(x62, 300.0);
        objectiveCoeffs.put(x63, 0.0);
        
        Function objectiveFunction = new Function(objectiveCoeffs);
        ObjectiveFunction objFunc = new ObjectiveFunction(extremum, objectiveFunction);
        
        // ============================================================
        // CONSTRAINTS - NEGATIVE RHS CONSTRAINTS FIRST!
        // ============================================================
        ArrayList<Constraint> constraints = new ArrayList<>();
        
        
        // ----- PART 1: NEGATIVE RHS CONSTRAINTS (from ≥ inequalities) -----
        // These MUST come FIRST in the constraint list
        // Rule: x ≥ b → x ≤ -b (coefficients unchanged, RHS becomes negative)
        
        // 1. x11 + x12 + x13 ≥ 450 → x11 + x12 + x13 ≤ -450
  
        // ----- PART 1: CONSTRAINTS WITH b < 0 (≥ constraints) -----
        // These will be modified by the solver (multiplied by -1 internally)

        // 1. x11 + x12 + x13 ≥ 450  →  x11 + x12 + x13 ≤ -450  (b < 0)
        
        HashMap<Integer, Double> c1 = new HashMap<>();
        c1.put(0, 1.0);   // x11
        c1.put(1, 1.0);   // x12
        c1.put(2, 1.0);   // x13
        c1.put(3, 0.0);   // x21
        c1.put(4, 0.0);   // x22
        c1.put(5, 0.0);   // x23
        c1.put(6, 0.0);   // x31
        c1.put(7, 0.0);   // x32
        c1.put(8, 0.0);   // x33
        c1.put(9, 0.0);   // x41
        c1.put(10, 0.0);  // x42
        c1.put(11, 0.0);  // x43
        c1.put(12, 0.0);  // x51
        c1.put(13, 0.0);  // x52
        c1.put(14, 0.0);  // x53
        c1.put(15, 0.0);  // x61
        c1.put(16, 0.0);  // x62
        c1.put(17, 0.0);  // x63
        constraints.add(new Constraint(0, new Function(c1), 
            ComparingOperator.LESSERTHAN, -450.0));
        
        HashMap<Integer, Double> c2 = new HashMap<>();
        c2.put(0, 0.0);   // x11
        c2.put(1, 0.0);   // x12
        c2.put(2, 0.0);   // x13
        c2.put(3, 1.0);   // x21
        c2.put(4, 1.0);   // x22
        c2.put(5, 1.0);   // x23
        c2.put(6, 0.0);   // x31
        c2.put(7, 0.0);   // x32
        c2.put(8, 0.0);   // x33
        c2.put(9, 0.0);   // x41
        c2.put(10, 0.0);  // x42
        c2.put(11, 0.0);  // x43
        c2.put(12, 0.0);  // x51
        c2.put(13, 0.0);  // x52
        c2.put(14, 0.0);  // x53
        c2.put(15, 0.0);  // x61
        c2.put(16, 0.0);  // x62
        c2.put(17, 0.0);  // x63
        constraints.add(new Constraint(1, new Function(c2), 
            ComparingOperator.LESSERTHAN, -600.0));

        // 3. x31 + x32 + x33 ≥ 550  →  x31 + x32 + x33 ≤ -550  (b < 0)
        HashMap<Integer, Double> c3 = new HashMap<>();
        c3.put(0, 0.0);   // x11
        c3.put(1, 0.0);   // x12
        c3.put(2, 0.0);   // x13
        c3.put(3, 0.0);   // x21
        c3.put(4, 0.0);   // x22
        c3.put(5, 0.0);   // x23
        c3.put(6, 1.0);   // x31
        c3.put(7, 1.0);   // x32
        c3.put(8, 1.0);   // x33
        c3.put(9, 0.0);   // x41
        c3.put(10, 0.0);  // x42
        c3.put(11, 0.0);  // x43
        c3.put(12, 0.0);  // x51
        c3.put(13, 0.0);  // x52
        c3.put(14, 0.0);  // x53
        c3.put(15, 0.0);  // x61
        c3.put(16, 0.0);  // x62
        c3.put(17, 0.0);  // x63
        constraints.add(new Constraint(2, new Function(c3), 
            ComparingOperator.LESSERTHAN, -550.0));

        // 4. x41 + x42 + x43 ≥ 350  →  x41 + x42 + x43 ≤ -350  (b < 0)
        HashMap<Integer, Double> c4 = new HashMap<>();
        c4.put(0, 0.0);   // x11
        c4.put(1, 0.0);   // x12
        c4.put(2, 0.0);   // x13
        c4.put(3, 0.0);   // x21
        c4.put(4, 0.0);   // x22
        c4.put(5, 0.0);   // x23
        c4.put(6, 0.0);   // x31
        c4.put(7, 0.0);   // x32
        c4.put(8, 0.0);   // x33
        c4.put(9, 1.0);   // x41
        c4.put(10, 1.0);  // x42
        c4.put(11, 1.0);  // x43
        c4.put(12, 0.0);  // x51
        c4.put(13, 0.0);  // x52
        c4.put(14, 0.0);  // x53
        c4.put(15, 0.0);  // x61
        c4.put(16, 0.0);  // x62
        c4.put(17, 0.0);  // x63
        constraints.add(new Constraint(3, new Function(c4), 
            ComparingOperator.LESSERTHAN, -350.0));

        // 5. x51 + x52 + x53 ≥ 500  →  x51 + x52 + x53 ≤ -500  (b < 0)
        HashMap<Integer, Double> c5 = new HashMap<>();
        c5.put(0, 0.0);   // x11
        c5.put(1, 0.0);   // x12
        c5.put(2, 0.0);   // x13
        c5.put(3, 0.0);   // x21
        c5.put(4, 0.0);   // x22
        c5.put(5, 0.0);   // x23
        c5.put(6, 0.0);   // x31
        c5.put(7, 0.0);   // x32
        c5.put(8, 0.0);   // x33
        c5.put(9, 0.0);   // x41
        c5.put(10, 0.0);  // x42
        c5.put(11, 0.0);  // x43
        c5.put(12, 1.0);  // x51
        c5.put(13, 1.0);  // x52
        c5.put(14, 1.0);  // x53
        c5.put(15, 0.0);  // x61
        c5.put(16, 0.0);  // x62
        c5.put(17, 0.0);  // x63
        constraints.add(new Constraint(4, new Function(c5), 
            ComparingOperator.LESSERTHAN, -500.0));
        
        HashMap<Integer, Double> c6 = new HashMap<>();
        c6.put(0, 0.0);   // x11
        c6.put(1, 0.0);   // x12
        c6.put(2, 0.0);   // x13
        c6.put(3, 0.0);   // x21
        c6.put(4, 0.0);   // x22
        c6.put(5, 0.0);   // x23
        c6.put(6, 0.0);   // x31
        c6.put(7, 0.0);   // x32
        c6.put(8, 0.0);   // x33
        c6.put(9, 0.0);   // x41
        c6.put(10, 0.0);  // x42
        c6.put(11, 0.0);  // x43
        c6.put(12, 0.0);  // x51
        c6.put(13, 0.0);  // x52
        c6.put(14, 0.0);  // x53
        c6.put(15, 1.0);  // x61
        c6.put(16, 1.0);  // x62
        c6.put(17, 1.0);  // x63
        constraints.add(new Constraint(5, new Function(c6), 
            ComparingOperator.LESSERTHAN, -450.0));
        
        HashMap<Integer,Double> c61= new HashMap<>();
        
        c61.put(3,1.0);
        constraints.add(new Constraint(6, new Function(c61), 
                ComparingOperator.GREATERTHAN, 0.0));

       
        HashMap<Integer,Double> c62= new HashMap<>();
        c62.put(11,1.0);
        constraints.add(new Constraint(7, new Function(c62), 
                ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer,Double> c63= new HashMap<>();
        c63.put(12,1.0);
        constraints.add(new Constraint(8, new Function(c63), 
                ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer,Double> c64= new HashMap<>();
        c64.put(14,1.0);
        constraints.add(new Constraint(9, new Function(c64), 
                ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer,Double> c65= new HashMap<>();
        c65.put(14,1.0);
        constraints.add(new Constraint(10, new Function(c65), 
                ComparingOperator.GREATERTHAN, 0.0));

      
        HashMap<Integer, Double> c7 = new HashMap<>();
        c7.put(0, 0.02);   // x11
        c7.put(1, 0.0);    // x12
        c7.put(2, 0.0);    // x13
        c7.put(3, 0.0);    // x21
        c7.put(4, 0.0);    // x22
        c7.put(5, 0.0);    // x23
        c7.put(6, 0.0);    // x31
        c7.put(7, 0.0);    // x32
        c7.put(8, 0.0);    // x33
        c7.put(9, -0.02);  // x41
        c7.put(10, 0.0);   // x42
        c7.put(11, 0.0);   // x43
        c7.put(12, 0.0);   // x51
        c7.put(13, 0.0);   // x52
        c7.put(14, 0.0);   // x53
        c7.put(15, 0.04);  // x61
        c7.put(16, 0.0);   // x62
        c7.put(17, 0.0);   // x63
        constraints.add(new Constraint(11, new Function(c7), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c8 = new HashMap<>();
        c8.put(0, 0.0);    // x11
        c8.put(1, 0.0);    // x12
        c8.put(2, 0.0);    // x13
        c8.put(3, 0.0);    // x21
        c8.put(4, 0.07);   // x22
        c8.put(5, 0.0);    // x23
        c8.put(6, 0.0);    // x31
        c8.put(7, 0.0);    // x32
        c8.put(8, 0.0);    // x33
        c8.put(9, 0.0);    // x41
        c8.put(10, -0.02); // x42
        c8.put(11, 0.0);   // x43
        c8.put(12, 0.0);   // x51
        c8.put(13, 0.09);  // x52
        c8.put(14, 0.0);   // x53
        c8.put(15, 0.0);   // x61
        c8.put(16, 0.04);  // x62
        c8.put(17, 0.0);   // x63
        constraints.add(new Constraint(12, new Function(c8), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c9 = new HashMap<>();
        c9.put(0, 0.0);    // x11
        c9.put(1, 0.0);    // x12
        c9.put(2, 0.02);   // x13
        c9.put(3, 0.0);    // x21
        c9.put(4, 0.0);    // x22
        c9.put(5, 0.07);   // x23
        c9.put(6, 0.0);    // x31
        c9.put(7, 0.0);    // x32
        c9.put(8, 0.0);    // x33
        c9.put(9, 0.0);    // x41
        c9.put(10, 0.0);   // x42
        c9.put(11, 0.0);   // x43
        c9.put(12, 0.0);   // x51
        c9.put(13, 0.0);   // x52
        c9.put(14, 0.0);   // x53
        c9.put(15, 0.0);   // x61
        c9.put(16, 0.0);   // x62
        c9.put(17, 0.04);  // x63
        constraints.add(new Constraint(13, new Function(c9), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c10 = new HashMap<>();
        c10.put(0, 0.08);   // x11
        c10.put(1, 0.0);    // x12
        c10.put(2, 0.0);    // x13
        c10.put(3, 0.0);    // x21
        c10.put(4, 0.0);    // x22
        c10.put(5, 0.0);    // x23
        c10.put(6, 0.02);   // x31
        c10.put(7, 0.0);    // x32
        c10.put(8, 0.0);    // x33
        c10.put(9, 0.10);   // x41
        c10.put(10, 0.0);   // x42
        c10.put(11, 0.0);   // x43
        c10.put(12, 0.0);   // x51
        c10.put(13, 0.0);   // x52
        c10.put(14, 0.0);   // x53
        c10.put(15, -0.02); // x61
        c10.put(16, 0.0);   // x62
        c10.put(17, 0.0);   // x63
        constraints.add(new Constraint(14, new Function(c10), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c11 = new HashMap<>();
        c11.put(0, 0.0);    // x11
        c11.put(1, 0.08);   // x12
        c11.put(2, 0.0);    // x13
        c11.put(3, 0.0);    // x21
        c11.put(4, -0.02);  // x22
        c11.put(5, 0.0);    // x23
        c11.put(6, 0.0);    // x31
        c11.put(7, 0.02);   // x32
        c11.put(8, 0.0);    // x33
        c11.put(9, 0.0);    // x41
        c11.put(10, 0.10);  // x42
        c11.put(11, 0.0);   // x43
        c11.put(12, 0.0);   // x51
        c11.put(13, 0.04);  // x52
        c11.put(14, 0.0);   // x53
        c11.put(15, 0.0);   // x61
        c11.put(16, -0.02); // x62
        c11.put(17, 0.0);   // x63
        constraints.add(new Constraint(15, new Function(c11), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c12 = new HashMap<>();
        c12.put(0, 0.0);    // x11
        c12.put(1, 0.0);    // x12
        c12.put(2, 0.08);   // x13
        c12.put(3, 0.0);    // x21
        c12.put(4, 0.0);    // x22
        c12.put(5, -0.02);  // x23
        c12.put(6, 0.0);    // x31
        c12.put(7, 0.0);    // x32
        c12.put(8, 0.02);   // x33
        c12.put(9, 0.0);    // x41
        c12.put(10, 0.0);   // x42
        c12.put(11, 0.0);   // x43
        c12.put(12, 0.0);   // x51
        c12.put(13, 0.0);   // x52
        c12.put(14, 0.0);   // x53
        c12.put(15, 0.0);   // x61
        c12.put(16, 0.0);   // x62
        c12.put(17, -0.02); // x63
        constraints.add(new Constraint(16, new Function(c12), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c13 = new HashMap<>();
        c13.put(0, 0.0);    // x11
        c13.put(1, 0.0);    // x12
        c13.put(2, 0.0);    // x13
        c13.put(3, 0.0);    // x21
        c13.put(4, 0.0);    // x22
        c13.put(5, 0.0);    // x23
        c13.put(6, 0.08);   // x31
        c13.put(7, 0.0);    // x32
        c13.put(8, 0.0);    // x33
        c13.put(9, 0.02);   // x41
        c13.put(10, 0.0);   // x42
        c13.put(11, 0.0);   // x43
        c13.put(12, 0.0);   // x51
        c13.put(13, 0.0);   // x52
        c13.put(14, 0.0);   // x53
        c13.put(15, 0.08);  // x61
        c13.put(16, 0.0);   // x62
        c13.put(17, 0.0);   // x63
        constraints.add(new Constraint(17, new Function(c13), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c14 = new HashMap<>();
        c14.put(0, 0.0);    // x11
        c14.put(1, 0.0);    // x12
        c14.put(2, 0.0);    // x13
        c14.put(3, 0.0);    // x21
        c14.put(4, 0.05);   // x22
        c14.put(5, 0.0);    // x23
        c14.put(6, 0.0);    // x31
        c14.put(7, 0.08);   // x32
        c14.put(8, 0.0);    // x33
        c14.put(9, 0.0);    // x41
        c14.put(10, 0.02);  // x42
        c14.put(11, 0.0);   // x43
        c14.put(12, 0.0);   // x51
        c14.put(13, -0.03); // x52
        c14.put(14, 0.0);   // x53
        c14.put(15, 0.0);   // x61
        c14.put(16, 0.08);  // x62
        c14.put(17, 0.0);   // x63
        constraints.add(new Constraint(18, new Function(c14), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c15 = new HashMap<>();
        c15.put(0, 0.0);    // x11
        c15.put(1, 0.0);    // x12
        c15.put(2, 0.0);    // x13
        c15.put(3, 0.0);    // x21
        c15.put(4, 0.0);    // x22
        c15.put(5, 0.05);   // x23
        c15.put(6, 0.0);    // x31
        c15.put(7, 0.0);    // x32
        c15.put(8, 0.08);   // x33
        c15.put(9, 0.0);    // x41
        c15.put(10, 0.0);   // x42
        c15.put(11, 0.0);   // x43
        c15.put(12, 0.0);   // x51
        c15.put(13, 0.0);   // x52
        c15.put(14, 0.0);   // x53
        c15.put(15, 0.0);   // x61
        c15.put(16, 0.0);   // x62
        c15.put(17, 0.08);  // x63
        constraints.add(new Constraint(19, new Function(c15), 
            ComparingOperator.GREATERTHAN, 0.0));
        
        HashMap<Integer, Double> c16 = new HashMap<>();
        c16.put(0, 1.0);   // x11
        c16.put(1, 1.0);   // x12
        c16.put(2, 1.0);   // x13
        c16.put(3, 0.0);   // x21
        c16.put(4, 0.0);   // x22
        c16.put(5, 0.0);   // x23
        c16.put(6, 0.0);   // x31
        c16.put(7, 0.0);   // x32
        c16.put(8, 0.0);   // x33
        c16.put(9, 0.0);   // x41
        c16.put(10, 0.0);  // x42
        c16.put(11, 0.0);  // x43
        c16.put(12, 0.0);  // x51
        c16.put(13, 0.0);  // x52
        c16.put(14, 0.0);  // x53
        c16.put(15, 0.0);  // x61
        c16.put(16, 0.0);  // x62
        c16.put(17, 0.0);  // x63
        constraints.add(new Constraint(20, new Function(c16), 
            ComparingOperator.LESSERTHAN, 450.0));
        
        HashMap<Integer, Double> c17 = new HashMap<>();
        c17.put(0, 0.0);   // x11
        c17.put(1, 0.0);   // x12
        c17.put(2, 0.0);   // x13
        c17.put(3, 0.0);   // x21
        c17.put(4, 1.0);   // x22
        c17.put(5, 1.0);   // x23
        c17.put(6, 0.0);   // x31
        c17.put(7, 0.0);   // x32
        c17.put(8, 0.0);   // x33
        c17.put(9, 0.0);   // x41
        c17.put(10, 0.0);  // x42
        c17.put(11, 0.0);  // x43
        c17.put(12, 0.0);  // x51
        c17.put(13, 0.0);  // x52
        c17.put(14, 0.0);  // x53
        c17.put(15, 0.0);  // x61
        c17.put(16, 0.0);  // x62
        c17.put(17, 0.0);  // x63
        constraints.add(new Constraint(21, new Function(c17), 
            ComparingOperator.LESSERTHAN, 600.0));
        
        HashMap<Integer, Double> c18 = new HashMap<>();
        c18.put(0, 0.0);   // x11
        c18.put(1, 0.0);   // x12
        c18.put(2, 0.0);   // x13
        c18.put(3, 0.0);   // x21
        c18.put(4, 0.0);   // x22
        c18.put(5, 0.0);   // x23
        c18.put(6, 1.0);   // x31
        c18.put(7, 1.0);   // x32
        c18.put(8, 1.0);   // x33
        c18.put(9, 0.0);   // x41
        c18.put(10, 0.0);  // x42
        c18.put(11, 0.0);  // x43
        c18.put(12, 0.0);  // x51
        c18.put(13, 0.0);  // x52
        c18.put(14, 0.0);  // x53
        c18.put(15, 0.0);  // x61
        c18.put(16, 0.0);  // x62
        c18.put(17, 0.0);  // x63
        constraints.add(new Constraint(22, new Function(c18), 
            ComparingOperator.LESSERTHAN, 550.0));
        
        HashMap<Integer, Double> c19 = new HashMap<>();
        c19.put(0, 0.0);   // x11
        c19.put(1, 0.0);   // x12
        c19.put(2, 0.0);   // x13
        c19.put(3, 0.0);   // x21
        c19.put(4, 0.0);   // x22
        c19.put(5, 0.0);   // x23
        c19.put(6, 0.0);   // x31
        c19.put(7, 0.0);   // x32
        c19.put(8, 0.0);   // x33
        c19.put(9, 1.0);   // x41
        c19.put(10, 1.0);  // x42
        c19.put(11, 0.0);  // x43
        c19.put(12, 0.0);  // x51
        c19.put(13, 0.0);  // x52
        c19.put(14, 0.0);  // x53
        c19.put(15, 0.0);  // x61
        c19.put(16, 0.0);  // x62
        c19.put(17, 0.0);  // x63
        constraints.add(new Constraint(23, new Function(c19), 
            ComparingOperator.LESSERTHAN, 350.0));
        
        HashMap<Integer, Double> c20 = new HashMap<>();
        c20.put(0, 0.0);   // x11
        c20.put(1, 0.0);   // x12
        c20.put(2, 0.0);   // x13
        c20.put(3, 0.0);   // x21
        c20.put(4, 0.0);   // x22
        c20.put(5, 0.0);   // x23
        c20.put(6, 0.0);   // x31
        c20.put(7, 0.0);   // x32
        c20.put(8, 0.0);   // x33
        c20.put(9, 0.0);   // x41
        c20.put(10, 0.0);  // x42
        c20.put(11, 0.0);  // x43
        c20.put(12, 0.0);  // x51
        c20.put(13, 1.0);  // x52
        c20.put(14, 0.0);  // x53
        c20.put(15, 0.0);  // x61
        c20.put(16, 0.0);  // x62
        c20.put(17, 0.0);  // x63
        constraints.add(new Constraint(24, new Function(c20), 
            ComparingOperator.LESSERTHAN, 500.0));

        HashMap<Integer, Double> c21 = new HashMap<>();
        c21.put(0, 0.0);   // x11
        c21.put(1, 0.0);   // x12
        c21.put(2, 0.0);   // x13
        c21.put(3, 0.0);   // x21
        c21.put(4, 0.0);   // x22
        c21.put(5, 0.0);   // x23
        c21.put(6, 0.0);   // x31
        c21.put(7, 0.0);   // x32
        c21.put(8, 0.0);   // x33
        c21.put(9, 0.0);   // x41
        c21.put(10, 0.0);  // x42
        c21.put(11, 0.0);  // x43
        c21.put(12, 0.0);  // x51
        c21.put(13, 0.0);  // x52
        c21.put(14, 0.0);  // x53
        c21.put(15, 1.0);  // x61
        c21.put(16, 1.0);  // x62
        c21.put(17, 1.0);  // x63
        constraints.add(new Constraint(25, new Function(c21), 
            ComparingOperator.LESSERTHAN, 450.0));
        
        HashMap<Integer, Double> c22 = new HashMap<>();
        c22.put(0, 1.0);   // x11
        c22.put(1, 0.0);   // x12
        c22.put(2, 0.0);   // x13
        c22.put(3, 0.0);   // x21
        c22.put(4, 0.0);   // x22
        c22.put(5, 0.0);   // x23
        c22.put(6, 1.0);   // x31
        c22.put(7, 0.0);   // x32
        c22.put(8, 0.0);   // x33
        c22.put(9, 1.0);   // x41
        c22.put(10, 0.0);  // x42
        c22.put(11, 0.0);  // x43
        c22.put(12, 0.0);  // x51
        c22.put(13, 0.0);  // x52
        c22.put(14, 0.0);  // x53
        c22.put(15, 1.0);  // x61
        c22.put(16, 0.0);  // x62
        c22.put(17, 0.0);  // x63
        constraints.add(new Constraint(26, new Function(c22), 
            ComparingOperator.LESSERTHAN, 900.0));

        HashMap<Integer, Double> c23 = new HashMap<>();
        c23.put(0, 0.0);   // x11
        c23.put(1, 1.0);   // x12
        c23.put(2, 0.0);   // x13
        c23.put(3, 0.0);   // x21
        c23.put(4, 1.0);   // x22
        c23.put(5, 0.0);   // x23
        c23.put(6, 0.0);   // x31
        c23.put(7, 1.0);   // x32
        c23.put(8, 0.0);   // x33
        c23.put(9, 0.0);   // x41
        c23.put(10, 1.0);  // x42
        c23.put(11, 0.0);  // x43
        c23.put(12, 0.0);  // x51
        c23.put(13, 1.0);  // x52
        c23.put(14, 0.0);  // x53
        c23.put(15, 0.0);  // x61
        c23.put(16, 1.0);  // x62
        c23.put(17, 0.0);  // x63
        constraints.add(new Constraint(27, new Function(c23), 
            ComparingOperator.LESSERTHAN, 1100.0));
        
        HashMap<Integer, Double> c24 = new HashMap<>();
        c24.put(0, 0.0);   // x11
        c24.put(1, 0.0);   // x12
        c24.put(2, 1.0);   // x13
        c24.put(3, 0.0);   // x21
        c24.put(4, 0.0);   // x22
        c24.put(5, 1.0);   // x23
        c24.put(6, 0.0);   // x31
        c24.put(7, 0.0);   // x32
        c24.put(8, 1.0);   // x33
        c24.put(9, 0.0);   // x41
        c24.put(10, 0.0);  // x42
        c24.put(11, 0.0);  // x43
        c24.put(12, 0.0);  // x51
        c24.put(13, 0.0);  // x52
        c24.put(14, 0.0);  // x53
        c24.put(15, 0.0);  // x61
        c24.put(16, 0.0);  // x62
        c24.put(17, 1.0);  // x63
        constraints.add(new Constraint(28, new Function(c24), 
            ComparingOperator.LESSERTHAN, 1000.0));
        
        
        HashMap<Integer, Double> c25 = new HashMap<>();
        c25.put(0, -0.04);  // x11
        c25.put(1, 0.0);    // x12
        c25.put(2, 0.0);    // x13
        c25.put(3, 0.0);    // x21
        c25.put(4, 0.0);    // x22
        c25.put(5, 0.0);    // x23
        c25.put(6, -0.06);  // x31
        c25.put(7, 0.0);    // x32
        c25.put(8, 0.0);    // x33
        c25.put(9, -0.08);  // x41
        c25.put(10, 0.0);   // x42
        c25.put(11, 0.0);   // x43
        c25.put(12, 0.0);   // x51
        c25.put(13, 0.0);   // x52
        c25.put(14, 0.0);   // x53
        c25.put(15, -0.02); // x61
        c25.put(16, 0.0);   // x62
        c25.put(17, 0.0);   // x63
        constraints.add(new Constraint(29, new Function(c25), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer, Double> c26 = new HashMap<>();
        c26.put(0, 0.0);    // x11
        c26.put(1, -0.04);  // x12
        c26.put(2, 0.0);    // x13
        c26.put(3, 0.0);    // x21
        c26.put(4, 0.01);   // x22
        c26.put(5, 0.0);    // x23
        c26.put(6, 0.0);    // x31
        c26.put(7, -0.06);  // x32
        c26.put(8, 0.0);    // x33
        c26.put(9, 0.0);    // x41
        c26.put(10, -0.08); // x42
        c26.put(11, 0.0);   // x43
        c26.put(12, 0.0);   // x51
        c26.put(13, 0.03);  // x52
        c26.put(14, 0.0);   // x53
        c26.put(15, 0.0);   // x61
        c26.put(16, -0.02); // x62
        c26.put(17, 0.0);   // x63
        constraints.add(new Constraint(30, new Function(c26), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer, Double> c27 = new HashMap<>();
        c27.put(0, 0.02);   // x11
        c27.put(1, 0.0);    // x12
        c27.put(2, 0.0);    // x13
        c27.put(3, 0.0);    // x21
        c27.put(4, 0.0);    // x22
        c27.put(5, 0.0);    // x23
        c27.put(6, -0.04);  // x31
        c27.put(7, 0.0);    // x32
        c27.put(8, 0.0);    // x33
        c27.put(9, 0.04);   // x41
        c27.put(10, 0.0);   // x42
        c27.put(11, 0.0);   // x43
        c27.put(12, 0.0);   // x51
        c27.put(13, 0.0);   // x52
        c27.put(14, 0.0);   // x53
        c27.put(15, -0.08); // x61
        c27.put(16, 0.0);   // x62
        c27.put(17, 0.0);   // x63
        constraints.add(new Constraint(31, new Function(c27), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer, Double> c28 = new HashMap<>();
        c28.put(0, 0.0);    // x11
        c28.put(1, 0.02);   // x12
        c28.put(2, 0.0);    // x13
        c28.put(3, 0.0);    // x21
        c28.put(4, -0.08);  // x22
        c28.put(5, 0.0);    // x23
        c28.put(6, 0.0);    // x31
        c28.put(7, -0.04);  // x32
        c28.put(8, 0.0);    // x33
        c28.put(9, 0.0);    // x41
        c28.put(10, 0.04);  // x42
        c28.put(11, 0.0);   // x43
        c28.put(12, 0.0);   // x51
        c28.put(13, -0.02); // x52
        c28.put(14, 0.0);   // x53
        c28.put(15, 0.0);   // x61
        c28.put(16, -0.08); // x62
        c28.put(17, 0.0);   // x63
        constraints.add(new Constraint(32, new Function(c28), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer, Double> c29 = new HashMap<>();
        c29.put(0, 0.0);    // x11
        c29.put(1, 0.0);    // x12
        c29.put(2, 0.02);   // x13
        c29.put(3, 0.0);    // x21
        c29.put(4, 0.0);    // x22
        c29.put(5, -0.08);  // x23
        c29.put(6, 0.0);    // x31
        c29.put(7, 0.0);    // x32
        c29.put(8, -0.04);  // x33
        c29.put(9, 0.0);    // x41
        c29.put(10, 0.0);   // x42
        c29.put(11, 0.0);   // x43
        c29.put(12, 0.0);   // x51
        c29.put(13, 0.0);   // x52
        c29.put(14, 0.0);   // x53
        c29.put(15, 0.0);   // x61
        c29.put(16, 0.0);   // x62
        c29.put(17, -0.08); // x63
        constraints.add(new Constraint(33, new Function(c29), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer, Double> c30 = new HashMap<>();
        c30.put(0, -0.06);  // x11
        c30.put(1, 0.0);    // x12
        c30.put(2, 0.0);    // x13
        c30.put(3, 0.0);    // x21
        c30.put(4, 0.0);    // x22
        c30.put(5, 0.0);    // x23
        c30.put(6, 0.02);   // x31
        c30.put(7, 0.0);    // x32
        c30.put(8, 0.0);    // x33
        c30.put(9, -0.04);  // x41
        c30.put(10, 0.0);   // x42
        c30.put(11, 0.0);   // x43
        c30.put(12, 0.0);   // x51
        c30.put(13, 0.0);   // x52
        c30.put(14, 0.0);   // x53
        c30.put(15, 0.02);  // x61
        c30.put(16, 0.0);   // x62
        c30.put(17, 0.0);   // x63
        constraints.add(new Constraint(34, new Function(c30), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer, Double> c31 = new HashMap<>();
        c31.put(0, 0.0);    // x11
        c31.put(1, -0.06);  // x12
        c31.put(2, 0.0);    // x13
        c31.put(3, 0.0);    // x21
        c31.put(4, -0.01);  // x22
        c31.put(5, 0.0);    // x23
        c31.put(6, 0.0);    // x31
        c31.put(7, 0.02);   // x32
        c31.put(8, 0.0);    // x33
        c31.put(9, 0.0);    // x41
        c31.put(10, -0.04); // x42
        c31.put(11, 0.0);   // x43
        c31.put(12, 0.0);   // x51
        c31.put(13, -0.09); // x52
        c31.put(14, 0.0);   // x53
        c31.put(15, 0.0);   // x61
        c31.put(16, 0.02);  // x62
        c31.put(17, 0.0);   // x63
        constraints.add(new Constraint(35, new Function(c31), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer, Double> c32 = new HashMap<>();
        c32.put(0, 0.0);    // x11
        c32.put(1, 0.0);    // x12
        c32.put(2, -0.06);  // x13
        c32.put(3, 0.0);    // x21
        c32.put(4, 0.0);    // x22
        c32.put(5, -0.01);  // x23
        c32.put(6, 0.0);    // x31
        c32.put(7, 0.0);    // x32
        c32.put(8, 0.02);   // x33
        c32.put(9, 0.0);    // x41
        c32.put(10, 0.0);   // x42
        c32.put(11, 0.0);   // x43
        c32.put(12, 0.0);   // x51
        c32.put(13, 0.0);   // x52
        c32.put(14, 0.0);   // x53
        c32.put(15, 0.0);   // x61
        c32.put(16, 0.0);   // x62
        c32.put(17, 0.02);  // x63
        constraints.add(new Constraint(36, new Function(c32), 
            ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer,Double> c33= new HashMap<Integer,Double>();    
        c33.put(3,1.0);
        constraints.add(new Constraint(37, new Function(c33), 
                ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer,Double> c34= new HashMap<Integer,Double>();    
        c34.put(11,1.0);
        constraints.add(new Constraint(38, new Function(c34), 
                ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer,Double> c35= new HashMap<Integer,Double>();    
        c35.put(12,1.0);
        constraints.add(new Constraint(39, new Function(c35), 
                ComparingOperator.LESSERTHAN, 0.0));
        
        HashMap<Integer,Double> c36= new HashMap<Integer,Double>();    
        c36.put(14,1.0);
        constraints.add(new Constraint(40, new Function(c36), 
                ComparingOperator.LESSERTHAN, 0.0));
        
        
        
        // ============================================================
        // CREATE THE LINEAR PROBLEM AND SOLVE
        // ============================================================
        LinearProblem lp = new LinearProblem(objFunc, constraints);
        
        System.out.println("========== STUDENTS TO SCHOOLS LINEAR PROGRAM ==========\n");
        lp.printLinearProblem();
        
        // Solve using your simplex solver
        SolvingDoublePhaseMinimize sdpm = new SolvingDoublePhaseMinimize(lp);
        
        // Display initial augmented matrix (Phase II)
        System.out.println("\n========== INITIAL AUGMENTED MATRIX (PHASE II) ==========");
        Matrix m0 = new Matrix(
            sdpm.getAugmented_matrix_phase_II().length,
            sdpm.getAugmented_matrix_phase_II()[0].length,
            sdpm.getAugmented_matrix_phase_II()
        );
        Matrix.displayMatrix(m0);
        
        // Apply simplex Phase II
        System.out.println("\n========== RUNNING SIMPLEX PHASE II ==========");
        sdpm.apply_simplex_phase_II();
        
        // Compute and display optimal objective value
        Double optimalValue = sdpm.compute_goal_function();
        System.out.println("Optimal Total Bussing Cost = $" + optimalValue);
        
        // Display optimal solution
        System.out.println("\n========== OPTIMAL SOLUTION ==========");
        sdpm.enumerate_based_variables_values_1();
        
        // Check if optimality is achieved
        if (sdpm.getOptimality()) {
            System.out.println("\n✓ OPTIMAL SOLUTION FOUND!");
        } else {
            System.out.println("\n✗ OPTIMALITY NOT REACHED!");
        }
    }
}
