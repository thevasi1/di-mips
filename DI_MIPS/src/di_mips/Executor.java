/*
 * This class contains all the methods that simulate the execution of an instruction
 * depending on the stage, operator, cycle and id
 */
package di_mips;

import instruction.Instruction;
import instruction.Stages;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class Executor {
    
    
    public Executor(){
    }
    
    /**
     * Launches the respective execution of an instruction with the cicle
     * @param ins instruction data
     * @param cic cicle of the execution of the stage
     */
    public void executeStage(Instruction ins, int cicle){
        switch (ins.getOperator()) {
            case ADD:
                executeADD(ins, cicle);
                break;
            case SUB:
                executeSUB(ins, cicle);
                break;
            case LD:
                executeLD(ins, cicle);
                break;
            case SW:
                executeSW(ins, cicle);
                break;
            case BEQ:
                executeBEQ(ins, cicle);
                break;
            default:
                throw new AssertionError();
        }
    }

    
    
    private void executeADD(Instruction ins, int cicle){
    }
    
    private void executeSUB(Instruction ins, int cicle){
    }
    
    private void executeLD(Instruction ins, int cicle){
    }
    
    private void executeSW(Instruction ins, int cicle){
    }
    
    private void executeBEQ(Instruction ins, int cicle){
    }
}
