/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import di_mips.File_Reader;
import instruction.Instruction;
import java.util.Stack;

/**
 *
 * @author Tomas
 */
public class Register {
    
    protected int value;
    protected Dependency[] dependencies;
    Stack<Instruction> pila = new Stack();
    Instruction inst_aux;
    
    // We have two because is double issue.
    Instruction inst_1;
    Instruction inst_2;
    
    private void registerStalls() {
        while (true) {
            inst_aux = pila.pop();
            if (inst_aux.equals(null)) {
                inst_1 = File_Reader.readNextLine();;
            } else {
                inst_1 = File_Reader.readNextLine();
                inst_2 = File_Reader.readNextLine();
            }
        }
        
    }
    
}

