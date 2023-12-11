/*
 * This class represents an instruction of the DI-MIPS
 */
package instruction;

import register.Register;

/**
 *
 * @author Tomas
 */
public class Instruction {

    int id;
    Operator operator;
    Register dst;
    Register src1;
    Register src2;
    Stages stage;

    /**
     * Constructor of an instruction
     * @param id unique identifier of the instruction
     * @param operator type of instruction
     * @param dst destination register
     * @param src1 source1 register
     * @param src2 source2 register
     * @param stage stage the instruction is in
     */
    public Instruction(int id, Operator operator, Register dst, Register src1, Register src2, Stages stage) {
        this.id = id;
        this.operator = operator;
        this.dst = dst;
        this.src1 = src1;
        this.src2 = src2;
        this.stage = stage;
    }
    
    /**
     * Launches the respective execution of an instruction depending on the
     * operator, stage, cycle and id
     * @param cic cicle of the execution of the stage
     */
    public void executeStage(int cic){
        switch (operator) {
            case ADD:
                // executorADD(stage, cicle, id)
                break;
            case SUB:
                break;
            case LD:
                break;
            case SW:
                break;
            case BEQ:
                break;
            default:
                throw new AssertionError();
        }
    }

    public void moveNextStage(){
        if(stage != Stages.W){
            Stages[] stages = Stages.values();
            stage = stages[stage.ordinal() + 1];
        }
    }
}

