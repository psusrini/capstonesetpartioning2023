package ca.mcmaster.capstonesetpartioning2023;



 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 

/**
 *
 * @author tamvadss
 */
public class Parameters {
    
    public static final int  MIP_EMPHASIS =  2;
    public static final boolean USE_PURE_CPLEX = true;
    
    //cplex config related
    public static final int  HEUR_FREQ  = -1 ;    
    public static final int  FILE_STRATEGY= 3;  
    public static final int MAX_THREADS =  System.getProperty("os.name").toLowerCase().contains("win") ? 1 : 32;
    public static boolean USE_BARRIER_FOR_SOLVING_LP = false;
  

    
    public static final int MAX_TEST_DURATION_HOURS =2;
    public static final int  BRANCHING_OVERRULE_CYLES =1;
    
    public static final String PRESOLVED_MIP_FILENAME =              
            System.getProperty("os.name").toLowerCase().contains("win") ?
     
     "F:\\temporary files here recovered\\ds.pre.sav":          
     
    "PBO.pre.sav";
    
           
    //for perf variability testing  
    public static final long PERF_VARIABILITY_RANDOM_SEED = 0;
    public static final java.util.Random  PERF_VARIABILITY_RANDOM_GENERATOR =             
            new  java.util.Random  (PERF_VARIABILITY_RANDOM_SEED);   
    
    
}
