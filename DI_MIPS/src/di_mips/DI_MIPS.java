/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di_mips;

import instruction.Instruction;
import instruction.Operator;
import instruction.Stages;
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
    
    private void begin() {
        File_Reader.initRegisters("registers.txt", registers);
        // Init registers
        
        // In loop
            // Read instructions
            Instruction inst = File_Reader.getNextInstruction("program.txt");
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
