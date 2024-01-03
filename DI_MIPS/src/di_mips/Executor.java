/*
 * This class contains all the methods that simulate the execution of an instruction
 * depending on the stage, operator, cycle and id
 */
package di_mips;

import instruction.Instruction;
import instruction.Stages;

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
     * @param cicle cicle of the execution of the stage
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
            if(ins.getStage().equals(Stages.X)){
                ins.getDst().setValue(ins.getSrc1().getValue() + ins.getSrc2().getValue());
                ins.getDst().removeDependency(ins.getId(), 'w');
                ins.getSrc1().removeDependency(ins.getId(), 'r');
                ins.getSrc2().removeDependency(ins.getId(), 'r');
            }
            //send to executor stage executed
            //return stage executed
            Stages stageExecuted = ins.getStage();
            //move to next stage
            ins.setNextStage();
    }
        
        
    
    
    private void executeSUB(Instruction ins, int cicle){
        if(ins.getStage().equals(Stages.X)){
            ins.getDst().setValue(ins.getSrc1().getValue() - ins.getSrc2().getValue());
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        //return stage executed
        Stages stageExecuted = ins.getStage();
        //move to next stage
        ins.setNextStage();
    }
    
    private void executeLD(Instruction ins, int cicle){
        if(ins.getStage().equals(Stages.M)){
            //TODO
            ins.getDst().setValue(ins.getSrc1().getValue());
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        Stages stageExecuted = ins.getStage();
        //move to next stage
        ins.setNextStage();
    }
    
    private void executeSW(Instruction ins, int cicle){
        if(ins.getStage().equals(Stages.M)){
            //TODO
            ins.getDst().setValue(ins.getSrc1().getValue());
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        Stages stageExecuted = ins.getStage();
        //move to next stage
        ins.setNextStage();
    }
    
    private int executeBEQ(Instruction ins, int cicle){
        if(ins.getStage().equals(Stages.D)){
            
        }
        return 0;
    }
    
    public boolean canExecute(Instruction ins){
        boolean cantExecute = ins.getDst().hasDependency();
        if(!cantExecute) cantExecute = ins.getSrc1().hasDependency();
        if(!cantExecute) cantExecute = ins.getSrc2().hasDependency();
        return !cantExecute;
    }
}
