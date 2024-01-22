/*
 * Read the input (usually from the files "instruction.txt" and "register.txt" and
 * interprete the content to tranlate it to objects that the Execution Calculation
 * part can interpret.
 */
package di_mips;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import instruction.Instruction;
import instruction.Operator;
import instruction.Stages;
import register.Register;

/**
 *
 * @author group_1
 */
public class File_Reader {
    
    private String line;
    private static int numLin_inst = 0;
    private String inst_file;
    private String reg_file = "register.txt";
    BufferedReader br = null;
    static Register[] registers;

    public File_Reader(String inst_f, Register[] registers) {
        this.inst_file = inst_f;
        try {
            this.registers=registers;
            br = new BufferedReader(new FileReader(reg_file));
            initRegisters();
            br = new BufferedReader(new FileReader(inst_file));
        } catch (IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }
        
    }

    private void initRegisters() throws IOException { // REGISTERS
        int num_reg = 0;
        String num_reg_str;
        while ((line = br.readLine()) != null) {
            char[] lineArr = line.toCharArray();
            num_reg_str = "";
            int i = 0;

            String R_value = "";
            int R_num_value;

            while (lineArr[i] == ' '|| lineArr[i] == 'R') { // Skip until we find num_reg
                i++;
            }
            while (Character.isDigit(lineArr[i])) { // Keep the register's number
                num_reg_str += Character.toString(lineArr[i]);
                i++;
            }
            while (lineArr[i] == ' '|| lineArr[i] == '=') { // Skip until we find the value (number)
                i++;
            }
            while (i < lineArr.length) { // Read value (number) until we get to the end of line
                if (Character.isDigit(lineArr[i])) {
                    R_value += lineArr[i];
                    i++;
                } else { // Register value is not a number
                    System.out.println("User error dtected!!!"
                            + "\n Wrong register value at line " + num_reg + "of file " + reg_file);
                    i++;
                }
            }

            R_num_value = Integer.valueOf(R_value);
            Register register = new Register(R_num_value);
            
            num_reg = Integer.valueOf(num_reg_str);
            registers[num_reg] = register;
        }
    }
    
    public Instruction getNextInstruction() { // INSTRUCTIONS
        try {
            Instruction inst = null;
            while (inst == null) {
                if ((line = br.readLine()) == null) { // Read the String line
                System.out.println("End of file reached.");
                return null;
                } else {
                    
                    // Variables
                    char[] lineArr = line.toCharArray();
                    int i = 0;
                    boolean isInstruction = true;

                    // Operator
                    Operator op;
                    String opStr = "";

                    // Registers
                    Register dst = null;
                    String dstStr = "";
                    Register src1 = null;
                    String src1Str = "";
                    Register src2 = null;
                    String src2Str = "";

                    // Label
                    String label = "";

                    while (i < lineArr.length && lineArr[i] != ' ') { // op
                        opStr += lineArr[i];
                        i++;
                    }
                    op = transformIntoOp(opStr);
                    i++;

                    if ("LD".equals(opStr) || "SW".equals(opStr)) {             // LD, SW

                        while (lineArr[i] != ',') { // dst
                            dstStr += lineArr[i];
                            i++;
                        }
                        dst = transformIntoReg(dstStr);

                        src1 = null;                // src1 (is not used)

                        while (lineArr[i] != ')') { // src2
                            src2Str += lineArr[i];
                            i++;
                        }
                        src2 = transformIntoReg(src2Str);
                        i++;

                    } else if ("BEQ".equals(opStr)) {                           // BEQ

                        dst = null;                 // dst (is not used)

                        while (lineArr[i] != ',') { // src1
                            src1Str += lineArr[i];
                            i++;
                        }
                        src1 = transformIntoReg(src1Str);
                        i++;
                        while (lineArr[i] != ',') { // src2
                            src2Str += lineArr[i];
                            i++;
                        }
                        src2 = transformIntoReg(src2Str);
                        i++;
                        while (lineArr[i] == ' ') {
                            i++;                        
                        }
                        while (i < lineArr.length) { // src2
                            label += lineArr[i];
                            i++;
                        }

                    } else if ("ADD".equals(opStr) || "SUB".equals(opStr)) {    // ADD, SUB
                        while (lineArr[i] != ',') { // dst
                            dstStr += lineArr[i];
                            i++;
                        }
                        dst = transformIntoReg(dstStr);
                        i++;
                        while (lineArr[i] != ',') { // src1
                            src1Str += lineArr[i];
                            i++;
                        }
                        src1 = transformIntoReg(src1Str);
                        i++;
                        while (i < lineArr.length) { // src2
                            src2Str += lineArr[i];
                            i++;
                        }
                        src2 = transformIntoReg(src2Str);
                    } else {
                        numLin_inst--;
                        isInstruction = false;
                        inst = null;
                    }
                    
                    if (isInstruction) {
                        inst = new Instruction(numLin_inst, op, dst, src1, src2, Stages.F, label);
                        numLin_inst++;
                    }
                }
            }
            return inst;
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null; // Only in failure or end-of-file case
    }

    private static Operator transformIntoOp(String opStr) {
        try {
            Operator op = Operator.valueOf(opStr);
            return op;
        } catch (IllegalArgumentException e) {
            return null; // Label declaration or comment detected.
        }
    }

    private static Register transformIntoReg(String regStr) {
        char[] regArr = regStr.toCharArray();
        int i = 0;
        String R_numberStr = "";
        int R_number;
        
        while (!Character.isDigit(regArr[i])){ // Skip until we find the register number
            i++;
        }
        while (i < regArr.length) { // Keep the register's number
            R_numberStr += Character.toString(regArr[i]);
            i++;
        }
        R_number = Integer.valueOf(R_numberStr);
        return registers[R_number];
    }

    public boolean findBranchLine(String label) {
        try {
            br = new BufferedReader(new FileReader(inst_file)); // We read the file from the beginning
            while ((line = br.readLine()) != null) {
                if (line.equals(label)) {
                    System.out.println("Label found.");
                    break;
                }
            }
            if (line == null) { // End of file reached (not found)
                System.out.println("End of file reached and label not found.");
                return false;
            }
            // if we arrive here, line = label
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        return true; // line = label
    }

}