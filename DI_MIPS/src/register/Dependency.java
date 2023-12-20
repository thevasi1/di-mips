/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

/**
 *
 * @author Tomas
 */
public class Dependency implements Comparable {
    
    int cicle;
    int instruction;
    char type;
    
    public Dependency(int cicle, int instruction, char type){
        this.cicle = cicle;
        this.instruction = instruction;
        this.type = type;
    }
    
    public boolean isDependency(Dependency d){
        if(type == 'r' && d.type == 'w'){
            if(cicle > d.cicle){
                return true;
            } else if(cicle == d.cicle){
                return instruction>d.instruction;
            }
        } else if(type == 'w' && d.type == 'w'){
            return cicle >= d.cicle && instruction > d.instruction;
        }
        return false;
    }
    
    @Override
    public int compareTo(Object t) {
        Dependency d = (Dependency) t;
        if(instruction == d.instruction && type == d.type){
            return 1;
        }
        return -1;
    }
    
}
