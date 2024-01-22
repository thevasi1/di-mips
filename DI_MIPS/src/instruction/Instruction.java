/*
 * This class represents an instruction of the DI-MIPS
 */
package instruction;

import register.Register;

/**
 *
 * @author group_1
 */
public class Instruction {

    int id;
    Operator operator;
    Register dst;
    Register src1;
    Register src2;
    Stages stage;
    String label;

    /**
     * Constructor of an instruction
     * @param id unique identifier of the instruction
     * @param operator type of instruction
     * @param dst destination register
     * @param src1 source1 register
     * @param src2 source2 register
     * @param stage stage the instruction is in
     */
    public Instruction(int id, Operator operator, Register dst, Register src1, Register src2, Stages stage, String label) {
        this.id = id;
        this.operator = operator;
        this.dst = dst;
        this.src1 = src1;
        this.src2 = src2;
        this.stage = stage;
        this.label = label;
    }

    public void setId(int id){
        this.id = id;
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
    
    public String getLabel(){
        return label;
    }
    
    public void addDependencies(int cicle){
        if(!operator.equals(Operator.NOP)){
            if(operator.equals(Operator.ADD) || operator.equals(Operator.SUB)){
                dst.addDependency(id, cicle, 'w');
                src1.addDependency(id, cicle, 'r');
                src2.addDependency(id, cicle, 'r');
            } else if(operator.equals(Operator.LD)){
                dst.addDependency(id, cicle, 'w');
                src2.addDependency(id, cicle, 'r');
            } else if(operator.equals(Operator.SW)){
                dst.addDependency(id, cicle, 'r');
                src2.addDependency(id, cicle, 'r');
            } else {
                src1.addDependency(id, cicle, 'r');
                src2.addDependency(id, cicle, 'r');
            }
            
        }
    }
    
    public boolean canBeInASequence(Instruction ins){
        return !(operator.equals(ins.operator) || (operator.equals(Operator.ADD) && ins.operator.equals(Operator.SUB)) || 
                (operator.equals(Operator.LD) && ins.operator.equals(Operator.SW)) || (operator.equals(Operator.SUB) && ins.operator.equals(Operator.ADD)) ||
                (operator.equals(Operator.SW) && ins.operator.equals(Operator.LD)));
    }
    
    public void setNextStage(){
        int position = stage.ordinal();
        if(position < Stages.values().length - 1){
            stage = Stages.values()[position + 1];
        }
    }
    
    /**
     *
     * @return
     */
    public String toString(){
        String str;
        if(operator.equals(Operator.NOP)){
            return "NOP";
        }
        if(src1 == null){
             str = "Instruction: " + id + "\no " + operator.toString() + "\nd " +dst.toString() + "\nsrc1 " + "\nsrc2 " +src2.toString() + "\nsatge " +stage.toString();
        } else if(dst == null){
             str = "Instruction: " + id + "\no " + operator.toString() + "\nd " + "\nsrc1 " +src1.toString() + "\nsrc2 " +src2.toString()+ "\nsatge " +stage.toString();
        } else {
             str = "Instruction: " + id + "\no " + operator.toString() + "\nd " +dst.toString() + "\nsrc1 " +src1.toString() + "\nsrc2 " +src2.toString() + "\nsatge " +stage.toString();
        }

        return str; 
    }
}

