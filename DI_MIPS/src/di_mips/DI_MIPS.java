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
        FileReader.readNextLine();
    }
    
    public static void main(String[] args) {

        (new DI_MIPS()).begin();

    }
    
}
