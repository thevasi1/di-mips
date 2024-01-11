/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di_mips;

import instruction.Instruction;
import instruction.Operator;
import instruction.Stages;
import java.util.ArrayList;
import java.util.Stack;
import register.Register;

/**
 *
 * @author Tomas
 */
public class DI_MIPS {

    /**
     * @param args the command line arguments
     */
    
    private static final int NUM_REG_MACHINE = 16;
    static Register[] registers = new Register[NUM_REG_MACHINE];
    int cicle = 1;
    File_Reader config = new File_Reader("registers.txt");
    File_Reader program = new File_Reader("program.txt");
    Stack<Instruction> stack = new Stack();
    ArrayList<Instruction> execution = new ArrayList();
    
    private void begin() {
        config.initRegisters(registers);
        
        
        boolean run = true;
        Instruction ins1;
        Instruction ins2;
        while(run){
            if(!stack.isEmpty()){
                ins1 = stack.pop();
                ins2 = program.getNextInstruction();
            } else {
                ins1 = program.getNextInstruction();
                ins2 = program.getNextInstruction();
            }
            
            if(!ins1.canBeInASequence(ins2)){
                stack.push(ins2);
                ins2 = new Instruction(0, Operator.NOP, null, null, null, Stages.F, null);
            }

            //add dependencies
            ins1.addDependencies(cicle);
            ins2.addDependencies(cicle);
            
            //add the instructions to the execution
            execution.add(ins1);
            execution.add(ins2);

            //execute
            
            //if necesary remove dependencies and instructions that are finished
            
            
            cicle++;
        }
        
        // In loop
            // Read instructions
            Instruction inst = program.getNextInstruction();
            //      Parse instructions
            // Add the convinient instructions to the queue
            // Execute the instructions on the queue
            // Refresh the queue
            
            
            
            
        // Can Execute?
        // TRUE
            // Execute
        // FALSE
            // Stall this and all the next instructions
    }
    
    public static void main(String[] args) {

        (new DI_MIPS()).begin();

    }
    
}
