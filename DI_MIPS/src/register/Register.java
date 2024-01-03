/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import di_mips.File_Reader;
import instruction.Instruction;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Tomas
 */
public class Register {
    
    int value;
    ArrayList<Dependency> dependencies;

    public Register(int value){
        this.value = value;
    }
    
    public void setValue(int value){
        this.value = value;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public void addDependency(int instruction, int cicle, char type){
        Dependency d = new Dependency(instruction, cicle, type);
        dependencies.add(d);
    }
     
    public void removeDependency(int instruction, char type){
        Dependency d = new Dependency(instruction, 0, type);
        dependencies.remove(d);
    }
    
    public boolean hasDependency(){
        for (int i = 0; i < dependencies.size(); i++) {
            for (int j = i; j < dependencies.size(); j++) {
                if(dependencies.get(i).compareTo(dependencies.get(j)) == -1){
                    if (dependencies.get(i).isDependency(dependencies.get(j))){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

