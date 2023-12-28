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
import java.util.HashMap;
import register.Register;

/**
 *
 * @author Tomas
 */
public class File_Reader {
    
    static String line;
    static Instruction current_inst;
    static int numLin_inst = 0;
    
    public static Instruction readNextLine() {
//        readLine(file); // BufferedReader stores the file line into variable "line"
//        transformCode(); // Turns the line into an instruction and updates "current_inst"
        return current_inst;
    }

    public static boolean readLine(String file) {
        
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

    public static void transformDeclarations(String file) { // REGISTERS
        
        HashMap<String, Register> map = new HashMap<>();
        while (readLine(file)) {
            char[] lineArr = line.toCharArray();
            int i = 0;

            String R_name = "";
            String R_value = "";
            int R_num_value;

            while (lineArr[i] != 'R') { // Skip until we find a register (R) declaration
                i++;
            }
            while (lineArr[i] != ' ' && lineArr[i] != '=') { // Read register name declaration (for ex.: R0)
                R_name += lineArr[i];
                i++;
            }
            while (lineArr[i] == '=' || lineArr[i] != ' ') { // Skip until we find the number
                i++;
            }
            while (lineArr[i] == '=' || lineArr[i] != ' ') { // Read number (for ex.: 1)
                R_value += lineArr[i];
                i++;
            }
            while (lineArr[i] != ';') { // Skip until we find the end of line (;)
                i++;
            }
            R_num_value = Integer.valueOf(R_value);
//          Register register = new Register(R_num_value, ...);
//          map.put(R_name, register);
        }
        
    }
    
    public static void getNextInstruction(String file) { // INSTRUCTIONS
        
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
        
        Instruction inst = new Instruction(numLin_inst, op, dst, src2, src2, Stages.F);
        numLin_inst++;
        readLine(file);
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
        
        while (dstCharArr[i] == 'R' || dstCharArr[i] == ' '){
            i++; // Skip until we find the number
        }
        
        while (dstCharArr[i] != '0' || dstCharArr[i] != '1' || dstCharArr[i] != '2' || dstCharArr[i] != '3' || 
                dstCharArr[i] != '4' || dstCharArr[i] != '5' || dstCharArr[i] != '6' || dstCharArr[i] != '7' || 
                dstCharArr[i] != '8' || dstCharArr[i] != '9') {
            valueStr += String.valueOf(dstCharArr[i]);
            i++;
        }
        value = Integer.valueOf(valueStr);
        Register reg = new Register(value);
        return reg;
    }

    private File_Reader(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
