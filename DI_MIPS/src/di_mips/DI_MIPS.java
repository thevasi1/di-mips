/*
 * Main class.
 */
package di_mips;

import instruction.Instruction;
import instruction.Operator;
import instruction.Stages;
import java.util.ArrayList;
import java.util.Stack;
import register.Memory;
import register.Register;

/**
 *
 * @author group_1
 */
public class DI_MIPS {

    /**
     * @param args the command line arguments
     */
    private static final int NUM_REG_MACHINE = 16;
    static Register[] registers = new Register[NUM_REG_MACHINE];
    Memory mem = new Memory(255);
    int cicle = 1;
    //File_Reader config = new File_Reader("registers.txt");
    File_Reader program = new File_Reader("instruction.txt", registers);
    Stack<Instruction> stack = new Stack();
    ArrayList<Instruction> execution = new ArrayList();
    static Executor executor = new Executor();
    public static boolean branchResolved = true;

    private void begin() {
        //config.initRegisters(registers);

        int programLine = 0;
        boolean run = true;
        Instruction ins1;
        Instruction ins2 = null;
        while (run) {
            if (branchResolved) {
                if (!stack.isEmpty()) {
                    ins1 = stack.pop();
                    ins1.setId(programLine);
                    programLine++;
                    if (!ins1.getOperator().equals(Operator.BEQ)) {
                        ins2 = program.getNextInstruction();
                        if (ins2 != null) {
                            ins2.setId(programLine);
                            programLine++;
                        }
                    } else {
                        ins2 = new Instruction(programLine, Operator.NOP, null, null, null, Stages.F, null);
                        programLine++;
                    }

                } else {
                    ins1 = program.getNextInstruction();
                    if (ins1 != null) {
                        ins1.setId(programLine);
                        programLine++;
                    }
                    if (ins1 != null && !ins1.getOperator().equals(Operator.BEQ)) {
                        ins2 = program.getNextInstruction();
                        if (ins2 != null) {
                            ins2.setId(programLine);
                            programLine++;
                        }
                    } else if(ins1 != null) {
                        ins2 = new Instruction(programLine, Operator.NOP, null, null, null, Stages.F, null);
                        programLine++;
                    }
                }

                if (ins1 != null) {
                    System.out.println("ins1:" + ins1.toString());
                }

                if (ins2 != null) {
                    System.out.println("ins2:" + ins2.toString());
                }

                if (ins1 != null && ins2 != null && !ins1.canBeInASequence(ins2)) {
                    stack.push(ins2);
                    ins2 = new Instruction(ins2.getId(), Operator.NOP, null, null, null, Stages.F, null);
                }

                if (ins1 != null) {
                    //add dependencies ins1
                    ins1.addDependencies(cicle);
                    //add the ins1 to the execution
                    execution.add(ins1);
                }
                if (ins2 != null) {

                    //add dependencies ins2
                    ins2.addDependencies(cicle);
                    //add the ins2 to the execution
                    execution.add(ins2);
                }

                if ((ins1 != null && ins2 != null) && (ins1.getOperator().equals(Operator.BEQ) || ins2.getOperator().equals(Operator.BEQ))) {
                    branchResolved = false;
                }
            }

            //execute
            for (int i = 0; i < execution.size(); i++) {
                executor.executeStage(execution.get(i), cicle, mem, program);
                //when an instruction is finished we delete it
                if (execution.get(i).getStage().equals(Stages.END)) {
                    execution.remove(i);
                    //decrease the index
                    i--;
                }
            }

            if (execution.isEmpty()) {
                run = false;
            }

            cicle++;

        }

        executor.makeFile();
    }

    public static void main(String[] args) {

        (new DI_MIPS()).begin();
    }

}
