/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Tomas
 */
public class File_Reader {
    
    static String line;
    static Instruction current_inst;
    static int numLin_inst = 0;

    public static void initRegisters(String file, Register[] registers) { // REGISTERS
        int num_reg = 0;
        while (readLine(file)) {
            char[] lineArr = line.toCharArray();
            int i = 0;

            String R_name = "";
            String R_value = "";
            int R_num_value;

            while (lineArr[i] == 'R' || lineArr[i] != ' ' || lineArr[i] != '=') { // Skip until we find the value (number)
                i++;
            }
            while (i < lineArr.length) { // Read value (number) until we get to the end of line
                if (Character.isDigit(lineArr[i])) {
                    R_value += lineArr[i];
                    i++;
                } else {
                    System.out.println("User error dtected!!!"
                            + "\n Wrong register value at line " + num_reg + "of file " + file);
                    i++;
                }
            }
            
            R_num_value = Integer.valueOf(R_value);
            Register register = new Register(R_num_value);
            registers[num_reg] = register;
            num_reg++;
        }
        
    }
    
    public static Instruction getNextInstruction(String file) { // INSTRUCTIONS
        
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
        
        while (lineArr[i] != ' ') { // op
            opStr += lineArr[i];
            i++;
        }
        op = transformIntoOp(opStr);
        i++;
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
        while (lineArr[i] != ';') { // src2
            src2Str += lineArr[i];
            i++;
        }
        src2 = transformIntoReg(src2Str);
        
        Instruction inst = new Instruction(numLin_inst, op, dst, src1, src2, Stages.F);
        numLin_inst++;
        readLine(file);
        
        return inst;
    }

    private static boolean readLine(String file) { // Generic file reader in java for a txt file.
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            if ((line = br.readLine()) == null) { // Read the String line
                System.out.println("End of file reached.");
                return false; // If it's done readding the file
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return true;
    }

    private static Operator transformIntoOp(String opStr) {
        Operator op = Operator.valueOf(opStr);
        return op;
    }

    private static Register transformIntoReg(String dstStr) {
        char[] dstCharArr = dstStr.toCharArray();
        int i = 0;
        String valueStr = "";
        int value;
        
        while (!Character.isDigit(dstCharArr[i])){ // Skip until we find the number
            i++;
        }
        while (Character.isDigit(dstCharArr[i])){
            valueStr += String.valueOf(dstCharArr[i]);
            i++;
        }
        value = Integer.valueOf(valueStr);
        Register reg = new Register(value);
        return reg;
    }

}
