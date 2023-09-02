package ca.mcmaster.capstonesetpartioning2023.drivers;
   
//import static ca.mcmaster.saipopsv205.Constants.*;
//import static ca.mcmaster.saipopsv205.Parameters.*;
import static java.lang.System.exit; 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author tamvadss
 */
public class SaiPOPS_Driver {
    
     
    public static void main(String[] args) throws Exception{
        
        //logger.info ("Start Cplex with SAI POPS heuristic version 2.05 ..." );
        
        //logger.info ("MIP_EMPHASIS is " +MIP_EMPHASIS  );
        
        Solver solver = new Solver ( ) ;
         
        solver.solve ( );
        
    }//end main
     
   
    
}
