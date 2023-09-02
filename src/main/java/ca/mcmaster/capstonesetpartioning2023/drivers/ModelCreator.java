package ca.mcmaster.capstonesetpartioning2023.drivers;


import static ca.mcmaster.capstonesetpartioning2023.Constants.*;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tamvadss
 * 
 * 
 *   Minimize x1+ 2x2  
 *   Subject to: 
 * 
 *    x1+ x2   = 1
 * 
 * 
 */
public class ModelCreator {
    
    public static void main(String[] args) throws Exception{
        //
        
        String[] names = new String[TWO];
        names[ZERO] = "x1";
        names[ONE] = "x2";
        
        IloCplex cplex  = new IloCplex ();
        IloNumVar[] variables =  cplex.boolVarArray(TWO,    names ) ;
        
        String[] q_Name = new String[ ONE];
        q_Name[ZERO] = "age";
        IloNumVar[] intVars =  cplex.intVarArray (ONE, FIVE, TEN,    q_Name ) ;
        
        IloNumExpr numericExpr_Obj = cplex.numExpr();
        numericExpr_Obj = cplex.sum( numericExpr_Obj,  cplex.prod (ONE, variables[ZERO])  );    
        numericExpr_Obj = cplex.sum( numericExpr_Obj,  cplex.prod (TWO, variables[ONE])  );    
         numericExpr_Obj = cplex.sum( numericExpr_Obj,  cplex.prod (ONE,  intVars[ZERO])  );    
        cplex.addMinimize(numericExpr_Obj);     
        
        IloNumExpr numericExpr_c1 = cplex.numExpr();
        numericExpr_c1 = cplex.sum( numericExpr_c1,  cplex.prod (ONE, variables[ZERO])  );    
        numericExpr_c1 = cplex.sum( numericExpr_c1,  cplex.prod (ONE, variables[ONE])  );    
        cplex.addEq(numericExpr_c1,ONE );     
        
        cplex.exportModel("c:\\temp\\VerySimpleModel.lp");
        
        cplex.solve ();
        
        System.out.println(names[ZERO]+ "="+ cplex.getValue(variables[ZERO]) );
        System.out.println(names[ONE ]+ "="+ cplex.getValue(variables[ONE]) );
        System.out.println( "Objective ="+ cplex.getValue(numericExpr_Obj ) );
        
    }
    
}
