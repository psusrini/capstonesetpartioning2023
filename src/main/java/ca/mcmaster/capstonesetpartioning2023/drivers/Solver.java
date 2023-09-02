/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.capstonesetpartioning2023.drivers;
  
   
import static ca.mcmaster.capstonesetpartioning2023.Constants.*;
import static ca.mcmaster.capstonesetpartioning2023.Parameters.*; 
import ca.mcmaster.capstonesetpartioning2023.callbacks.EmptyCallback;
import ca.mcmaster.capstonesetpartioning2023.callbacks.SetPart_Callback;
import ca.mcmaster.capstonesetpartioning2023.constraints.*;
import ca.mcmaster.capstonesetpartioning2023.utilities.CplexUtils;
import ilog.concert.IloException;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import static java.lang.System.exit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet; 


/**
 *
 * @author tamvadss
 */
public class Solver {
    
  
     
    private   IloCplex cplex;
     
    public    TreeMap<String, Double>  objectiveFunctionMap =null;
    public  TreeMap<String, IloNumVar>  mapOfAllVariablesInTheModel = new TreeMap<String, IloNumVar> ();
    //constraints, smallest first
    public  TreeMap<Integer, HashSet<LowerBoundConstraint>> mapOfAllConstraintsInTheModel = 
            new TreeMap<Integer, HashSet<LowerBoundConstraint>> ();
     
   
    public Solver ( ) throws Exception{
   
      
        
        initCplex(); 
                  
         
        
        IloCplex.BranchCallback callback =  new EmptyCallback(   );
        
        if (   !USE_PURE_CPLEX){
            callback=
                    new SetPart_Callback(    objectiveFunctionMap,mapOfAllVariablesInTheModel, 
                            mapOfAllConstraintsInTheModel);
        }// else if FEAS then SAIFEAS
        
         
        
        cplex.use(callback) ;        
        
    }

    public void solve () throws IloException{
        //logger.info ("Solve invoked ..." );
        for (int hours = ONE; hours <= MAX_TEST_DURATION_HOURS ; hours ++){                
            cplex.solve();
            print_statistics (cplex, hours) ;
            
          
            if (cplex.getStatus().equals( IloCplex.Status.Infeasible)) break;
            if (cplex.getStatus().equals( IloCplex.Status.Optimal)) break;
            
            if (hours == BRANCHING_OVERRULE_CYLES)  {
                //logger.info ("Restoring empty callback ... ") ;
                cplex.use( new EmptyCallback(   ));
            }            

        }
        cplex.end();
        //logger.info ("Solve completed." );
        System.out.println ("Solve completed." );
    }
    
    
    private void initCplex ( ) throws Exception{
        cplex = new IloCplex ();
        
        System.out.println ("CPLEX version "+ cplex.getVersion());
        boolean isWindows =   System.getProperty("os.name").toLowerCase().contains("win") ;
        if (!cplex.getVersion().startsWith("22") && ! isWindows){
            System.err.println ("CPLEX version not the latest -- STOP" );
            exit(ONE);
        }
        
        cplex.importModel( PRESOLVED_MIP_FILENAME);
        CplexUtils.setCplexParameters(cplex) ;
        
        //logger.info( "Cplex parameter MIP emphasis "+ MIP_EMPHASIS);
        //logger.info( "Barrier used for LP  "+ USE_BARRIER_FOR_SOLVING_LP);        
        //logger.info( "Random seed "+ PERF_VARIABILITY_RANDOM_SEED);
        
        objectiveFunctionMap = CplexUtils.getObjective(cplex);
                
        for ( IloNumVar var : CplexUtils.getVariables(cplex)){
            mapOfAllVariablesInTheModel.put (var.getName(), var);
        }
        
        List<LowerBoundConstraint> lbcList = CplexUtils.getConstraints(cplex,objectiveFunctionMap );
                
        //arrange by size
        for (LowerBoundConstraint lbc: lbcList){
            int numVars = lbc.getVariableCount();
                        
            HashSet<LowerBoundConstraint> current =  mapOfAllConstraintsInTheModel.get (numVars);
            if (null==current) current = new HashSet<LowerBoundConstraint>();
            current.add (lbc) ;
            mapOfAllConstraintsInTheModel.put (numVars, current);               
        }
                                               
    }
    
       
    private void print_statistics (IloCplex cplex, int hour) throws IloException {
        double bestSoln = BILLION;
        double relativeMipGap = BILLION;
        IloCplex.Status cplexStatus  = cplex.getStatus();
        if (cplexStatus.equals( IloCplex.Status.Feasible)  ||cplexStatus.equals( IloCplex.Status.Optimal) ) {
            bestSoln=cplex.getObjValue();
            relativeMipGap=  cplex.getMIPRelativeGap();
        };
        
        System.out.println ("" + hour + ","+  bestSoln + ","+  
                cplex.getBestObjValue() + "," + cplex.getNnodesLeft64() +
                "," + cplex.getNnodes64() + "," + relativeMipGap ) ;
    }
    
}
