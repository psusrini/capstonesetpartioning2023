/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mcmaster.capstonesetpartioning2023.heuristics;

import ca.mcmaster.capstonesetpartioning2023.constraints.LowerBoundConstraint;
import static java.lang.System.exit;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet; 

/**
 *
 * @author tamvadss
 */
public class MOMS {
    
    
    //fixed variables and their values
    public TreeMap<String, Boolean> fixedVariables ;
    
    public TreeSet<String> fractionalVariables ;
    public TreeMap<Integer, HashSet<LowerBoundConstraint>> mapOfAllConstraintsInTheModel;
   
    
    private TreeMap<Integer, HashSet<LowerBoundConstraint>> constraintsAtThisNode = 
            new TreeMap<Integer, HashSet<LowerBoundConstraint>>();
    
    private boolean  zzqn = true; 
       
    
    
    //public SaiPOPS ( 
    public MOMS(TreeMap<String, Boolean> fixings ,
            TreeSet<String> fractionalVariables ,
            TreeMap<Integer, HashSet<LowerBoundConstraint>> mapOfAllConstraintsInTheModel  
           
            
            
            ){
        
        this .fractionalVariables = fractionalVariables;
        this.mapOfAllConstraintsInTheModel=mapOfAllConstraintsInTheModel;
         
        this.fixedVariables = fixings;
    }
    
    public  String  getBranchingVariable (   ){
        
        
        //walk thru all the constraints to collect information
        for (HashSet<LowerBoundConstraint> lbcSet : mapOfAllConstraintsInTheModel.values()){
            for (LowerBoundConstraint lbc : lbcSet){
                LowerBoundConstraint lbcCopy = lbc.getCopy(fixedVariables);
               
                if (null != lbcCopy){
                    
                    lbcCopy.recordFractionalStatus(fractionalVariables);   
                    
                    //System.err.println(lbcCopy);
                    
                    int size = lbcCopy.getVariableCount();
                    HashSet<LowerBoundConstraint> current = constraintsAtThisNode.get (size );
                    if (null==current) current = new HashSet<LowerBoundConstraint>();
                    current.add(lbcCopy);
                    constraintsAtThisNode.put (size, current);
                   
                }
            }
        }
               
        return chooseVariable();        
    }
    
    private String chooseVariable (){
        
        //STUDENTS :
        //use constraintsAtThisNode and fractionalVariables to make a branching variable choice 
        
        return null;
        
    }
    
}
