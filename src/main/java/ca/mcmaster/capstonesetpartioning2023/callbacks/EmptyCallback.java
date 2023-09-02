/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.capstonesetpartioning2023.callbacks;
      
import static ca.mcmaster.capstonesetpartioning2023.Constants.*; 
import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.NodeId;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author tamvadss
 */
public class EmptyCallback extends IloCplex.BranchCallback{
    
    

    @Override
    protected void main() throws IloException {
        if ( getNbranches()> ZERO ){  
            
            NodeId id = getNodeId();

            IloNumVar[][] vars = new IloNumVar[TWO][ONE] ;
            double[ ][] bounds = new double[TWO ][ONE];
            IloCplex.BranchDirection[ ][]  dirs = new  IloCplex.BranchDirection[ TWO][ONE];
            getBranches (vars, bounds, dirs) ;
            
            System.out.println("Node "+ id + " is  branching on variable " + 
                    vars[ZERO][ZERO] + " direction "+ dirs[ZERO][ZERO]
                    + " bound is " + bounds[ZERO][ZERO]) ;
            System.out.println("Node "+ id + " is  branching on variable " + 
                    vars[ONE][ZERO] + " direction "+ dirs[ONE][ZERO]
                    + " bound is " + bounds[ONE][ZERO]) ;

        }
    }
     
  
}
