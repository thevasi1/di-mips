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
public class Dependency {
    
    int cicle;
    int instruction;
    char type;
    
    public Dependency(int cicle, int instruction, char type){
        this.cicle = cicle;
        this.instruction = instruction;
        this.type = type;
    }
    
    public boolean readAfterWrite(Dependency d){
        if(type == 'r' && d.type == 'w'){
            if(cicle > d.cicle){
                return true;
            } else if(cicle == d.cicle){
                return instruction>d.instruction;
            }
        }
        return false;
    }
    
    public boolean writeAfterWrite(Dependency d){
        return (type == 'w' && type == d.type && cicle >= d.cicle && instruction > d.instruction);
    }
    
}
