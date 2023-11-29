/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import instruction.Instruction;
import java.util.Stack;

/**
 *
 * @author Tomas
 */
public class Register {
    
    protected int value;
    protected Dependency[] dependencies;
    Stack pila = new Stack();
    Instruction inst_aux;
    
    //inst_aux = pila.pop();
    
}

