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

    public int getId() {
        return id;
    }

    public Operator getOperator() {
        return operator;
    }

    public Register getDst() {
        return dst;
    }

    public Register getSrc1() {
        return src1;
    }

    public Register getSrc2() {
        return src2;
    }

    public Stages getStage() {
        return stage;
    }
    
    
    public void setNextStge(){
        int position = stage.ordinal();
        if(position < Stages.values().length - 1){
            stage = Stages.values()[position + 1];
        }
    }
}

