/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di_mips;

import instruction.Instruction;
import instruction.Operator;
import instruction.Stages;

/**
 *
 * @author Tomas
 */
public class DI_MIPS {

    /**
     * @param args the command line arguments
     */
    
    private void begin() {
        File_Reader.readNextLine();
        // Init registers
        
        // In loop
            // Read instructions
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
