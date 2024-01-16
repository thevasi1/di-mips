/*
 * This class contains all the methods that simulate the execution of an instruction
 * depending on the stage, operator, cycle and id
 */
package di_mips;

import instruction.Instruction;
import instruction.Stages;
import instruction.Operator;
import writer.File_Writer;

/**
 *
 * @author Usuario
 */
public class Executor {

    boolean previousStalled = false;
    File_Writer fw = new File_Writer();
    
    public Executor() {
    }

    /**
     * Launches the respective execution of an instruction with the cicle
     *
     * @param ins instruction data
     * @param cicle cicle of the execution of the stage
     */
    public void executeStage(Instruction ins, int cicle) {
        if (!ins.getOperator().equals(Operator.NOP) && canExecute(ins)) {
            previousStalled = false;
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
            }
        } else if(ins.getOperator().equals(Operator.NOP)){
            if(previousStalled){
                //stall
            } else {
                ins.setNextStage();
            }
        } else if(!ins.getOperator().equals(Operator.NOP) && !canExecute(ins)){
            previousStalled = true;
            fw.writeStage(cicle, ins.getId(), Stages.S, ins.getOperator());
        }
    }

    private void executeADD(Instruction ins, int cicle) {
        if (ins.getStage().equals(Stages.X)) {
            ins.getDst().setValue(ins.getSrc1().getValue() + ins.getSrc2().getValue());
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    private void executeSUB(Instruction ins, int cicle) {
        if (ins.getStage().equals(Stages.X)) {
            ins.getDst().setValue(ins.getSrc1().getValue() - ins.getSrc2().getValue());
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    private void executeLD(Instruction ins, int cicle) {
        if (ins.getStage().equals(Stages.M)) {
            //TODO
            ins.getDst().setValue(ins.getSrc1().getValue());
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    private void executeSW(Instruction ins, int cicle) {
        if (ins.getStage().equals(Stages.M)) {
            //TODO
            ins.getDst().setValue(ins.getSrc1().getValue());
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    private void executeBEQ(Instruction ins, int cicle) {
        if (ins.getStage().equals(Stages.D)) {
            if (ins.getSrc1().getValue() == ins.getSrc2().getValue()) {
                //code to jump
            }
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    public boolean canExecute(Instruction ins) {
        boolean cantExecute = ins.getDst().hasDependency();
        if (!cantExecute) {
            cantExecute = ins.getSrc1().hasDependency();
        }
        if (!cantExecute) {
            cantExecute = ins.getSrc2().hasDependency();
        }
        return !cantExecute;
    }
}
