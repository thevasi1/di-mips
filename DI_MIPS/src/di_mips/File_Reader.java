/*
 * 
 */
package di_mips;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import instruction.Instruction;
import instruction.Operator;
import instruction.Stages;
import register.Register;


public class File_Reader {
    
    private String line;
    private static int numLin_inst = 0;
    private String inst_file;
    private String reg_file = "register.txt";
    BufferedReader br = null;

    public File_Reader(String inst_f, Register[] registers) {
        this.inst_file = inst_f;
        try {
            br = new BufferedReader(new FileReader(reg_file));
            initRegisters(registers);
            br = new BufferedReader(new FileReader(inst_file));
        } catch (IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }
        
    }

    private void initRegisters(Register[] registers) throws IOException { // REGISTERS
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
            // System.out.println("num_reg: " + num_reg);
            registers[num_reg] = register;
            // System.out.println("R" + num_reg_str+": " + registers[num_reg].getValue());
        }
    }
    
    public Instruction getNextInstruction() { // INSTRUCTIONS
        try {
            if ((line = br.readLine()) == null) { // Read the String line
                System.out.println("End of file reached.");
            } else {
                if ("".equals(line)) { // If line has nothing, skip
                    if ((line = br.readLine()) == null) {
                        System.out.println("End of file reached.");
                    }
                }
                char[] lineArr = line.toCharArray();
                int i = 0;

                // Operator
                Operator op;
                String opStr = "";

                // Registers
                Register dst;
                String dstStr = "";
                Register src1;
                String src1Str = "";
                Register src2;
                String src2Str = "";
                
                // Label
                String label = "";
                
                while (i < lineArr.length && lineArr[i] != ' ') { // op
                    opStr += lineArr[i];
                    i++;
                }
                op = transformIntoOp(opStr);
                if (op == null) { // It is a label or an error (we skip in both cases)
                    getNextInstruction();
                }
                i++;
                System.out.println("opStr: " + opStr);

                if ("LD".equals(opStr) || "SW".equals(opStr)) {             // LD, SW

                    while (lineArr[i] != ',') { // src2
                        src2Str += lineArr[i];
                        i++;
                    }
                    src2 = transformIntoReg(src2Str);

                    src1 = null;                // src1 (is not used)

                    while (lineArr[i] != ')') { // dst
                        dstStr += lineArr[i];
                        i++;
                    }
                    dst = transformIntoReg(dstStr);
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
                    System.out.println("ERROR!!! Operator badly intruded!");
                    return null;
                }

                Instruction inst = new Instruction(numLin_inst, op, dst, src1, src2, Stages.F, label);
                numLin_inst++;

                return inst;
            }
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
            System.out.println("Label declaration detected.");
            return null;
        }
    }

    private static Register transformIntoReg(String regStr) {
        char[] regArr = regStr.toCharArray();
        int i = 0;
        String valueStr = "";
        int value;
        
        while (!Character.isDigit(regArr[i])){ // Skip until we find the number
            i++;
        }
        while (i < regArr.length) { // Keep the register's number
            valueStr += Character.toString(regArr[i]);
            i++;
        }
        value = Integer.valueOf(valueStr);
        Register reg = new Register(value);
        return reg;
    }

    public boolean findBranchLine(String label){
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