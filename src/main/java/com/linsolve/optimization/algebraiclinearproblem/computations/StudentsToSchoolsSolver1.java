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

public class StudentsToSchoolsSolver1 {

    public static void main(String[] args) throws Exception {
        
        // ============================================================
        // VARIABLE INDICES (ONLY VALID variables - infeasible ones removed)
        // ============================================================
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
        sdpm.enumerate_based_variables_values();
        
        // Check if optimality is achieved
        if (sdpm.getOptimality()) {
            System.out.println("\n✓ OPTIMAL SOLUTION FOUND!");
        } else {
            System.out.println("\n✗ OPTIMALITY NOT REACHED!");
        }
    }
}