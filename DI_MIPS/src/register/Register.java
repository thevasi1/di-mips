/*
 * Object register.
 */
package register;

import di_mips.File_Reader;
import instruction.Instruction;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author group_1
 */
public class Register {

    int value;
    ArrayList<Dependency> dependencies = new ArrayList();

    public Register(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void addDependency(int instruction, int cicle, char type) {
        Dependency d = new Dependency(cicle, instruction, type);
        dependencies.add(d);
    }

    public void removeDependency(int instruction, char type) {
        Dependency d = new Dependency(0, instruction, type);
        for (int i = 0; i < dependencies.size(); i++) {
            if (dependencies.get(i).compareTo(d) == 1) {
                dependencies.remove(i);
                i--;
            }
        }
    }
    
    public Dependency getDependency(int id, char type){
        Dependency d = new Dependency(0, id, type);
        for (int i = 0; i < dependencies.size(); i++) {
            if(dependencies.get(i).compareTo(d) == 1){
                return dependencies.get(i);
            }
            
        }
        return null;
    }

    public boolean hasDependency(Dependency d) {
        boolean hasDependency = false;
        if(!dependencies.isEmpty() && d != null){
            for (int i = 0; i < dependencies.size(); i++) {
                if (d.compareTo(dependencies.get(i)) == -1) {
                    if (d.isDependency(dependencies.get(i))) {
                        hasDependency = true;
                    }
                }
            }
        }  
        return hasDependency;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
