/*
 * This class contains all the methods that simulate the execution of an instruction
 * depending on the stage, operator, cycle and id
 */
package di_mips;

import instruction.Instruction;
import instruction.Stages;
import instruction.Operator;
import register.Memory;
import writer.File_Writer;

/**
 *
 * @author Usuario
 */
public class Executor {

    int previousCicle = 0;
    boolean stall = false;
    File_Writer fw = new File_Writer();

    public Executor() {
    }

    /**
     * Launches the respective execution of an instruction with the cicle
     *
     * @param ins instruction data
     * @param cicle cicle of the execution of the stage
     */
    public void executeStage(Instruction ins, int cicle, Memory mem, File_Reader fr) {
        if (cicle == previousCicle + 1) {
            stall = false;
            previousCicle = cicle;
        }

        if (ins.getOperator().equals(Operator.NOP)) {
            if (stall) {
                fw.writeStage(cicle, ins.getId(), Stages.S, ins.getOperator());
            } else {
                fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
                ins.setNextStage();
            }
        } else if (stall) {
            stall = true;
            fw.writeStage(cicle, ins.getId(), Stages.S, ins.getOperator());
        } else {
            stall = false;
            switch (ins.getOperator()) {
                case ADD:
                    executeADD(ins, cicle);
                    break;
                case SUB:
                    executeSUB(ins, cicle);
                    break;
                case LD:
                    executeLD(ins, cicle, mem);
                    break;
                case SW:
                    executeSW(ins, cicle, mem);
                    break;
                case BEQ:
                    executeBEQ(ins, cicle, fr);
                    break;
            }
        }
    }

    private void executeADD(Instruction ins, int cicle) {
        if (ins.getStage().equals(Stages.X) && canExecute(ins)) {
            ins.getDst().setValue(ins.getSrc1().getValue() + ins.getSrc2().getValue());
        } else if (ins.getStage().equals(Stages.X) && !canExecute(ins)) {
            stall = true;
            fw.writeStage(cicle, ins.getId(), Stages.S, ins.getOperator());
            return;
        }

        if (ins.getStage().equals(Stages.M)) {
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
        if (ins.getStage().equals(Stages.X) && canExecute(ins)) {
            ins.getDst().setValue(ins.getSrc1().getValue() - ins.getSrc2().getValue());
        } else if (ins.getStage().equals(Stages.X) && !canExecute(ins)) {
            stall = true;
            fw.writeStage(cicle, ins.getId(), Stages.S, ins.getOperator());
            return;
        }
        if (ins.getStage().equals(Stages.M)) {
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    private void executeLD(Instruction ins, int cicle, Memory mem) {
        if (ins.getStage().equals(Stages.M) && canExecute(ins)) {
            ins.getDst().setValue(mem.getValue(ins.getSrc2().getValue()));
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        } else if (ins.getStage().equals(Stages.M) && !canExecute(ins)) {
            stall = true;
            fw.writeStage(cicle, ins.getId(), Stages.S, ins.getOperator());
            return;
        }

        if (ins.getStage().equals(Stages.W)) {
            ins.getDst().removeDependency(ins.getId(), 'w');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    private void executeSW(Instruction ins, int cicle, Memory mem) {
        if (ins.getStage().equals(Stages.M) && canExecute(ins)) {
            mem.setValue(ins.getSrc2().getValue(), ins.getDst().getValue());
        } else if (ins.getStage().equals(Stages.M) && !canExecute(ins)) {
            stall = true;
            fw.writeStage(cicle, ins.getId(), Stages.S, ins.getOperator());
            return;
        }
        
        if (ins.getStage().equals(Stages.W)) {
            ins.getDst().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    private void executeBEQ(Instruction ins, int cicle, File_Reader fr) {
        if (ins.getStage().equals(Stages.D) && canExecute(ins)) {
            if (ins.getSrc1().getValue() == ins.getSrc2().getValue()) {
                fr.findBranchLine(ins.getLabel());
            }
            DI_MIPS.branchResolved = true;
        } else if (ins.getStage().equals(Stages.D) && !canExecute(ins)) {
            stall = true;
            fw.writeStage(cicle, ins.getId(), Stages.S, ins.getOperator());
            return;
        }
        
        if(ins.getStage().equals(Stages.X)){
            ins.getSrc1().removeDependency(ins.getId(), 'r');
            ins.getSrc2().removeDependency(ins.getId(), 'r');
        }
        //send to executor stage executed
        fw.writeStage(cicle, ins.getId(), ins.getStage(), ins.getOperator());
        //move to next stage
        ins.setNextStage();
    }

    public boolean canExecute(Instruction ins) {
        boolean cantExecute = false;
        if (ins.getDst() != null) {
            //System.out.println(ins.getId() + " has depemdency " + ins.getDst().hasDependency(ins.getDst().getDependency(ins.getId(), 'w'))) ;
            cantExecute = ins.getDst().hasDependency(ins.getDst().getDependency(ins.getId(), 'w'));
        }
        if (!cantExecute) {
            if (ins.getSrc1() != null) {
                //System.out.println(ins.getId() + " has depemdency" +  ins.getSrc1().hasDependency(ins.getSrc1().getDependency(ins.getId(), 'r')));
                cantExecute = ins.getSrc1().hasDependency(ins.getSrc1().getDependency(ins.getId(), 'r'));
            }
        }
        if (!cantExecute) {
            if (ins.getSrc2() != null) {
                //System.out.println(ins.getId() + " has depemdency" +  ins.getSrc2().hasDependency(ins.getSrc2().getDependency(ins.getId(), 'r')));
                cantExecute = ins.getSrc2().hasDependency(ins.getSrc2().getDependency(ins.getId(), 'r'));
            }
        }
        return !cantExecute;
    }

    public void makeFile() {
        fw.makeFile();
    }
}
