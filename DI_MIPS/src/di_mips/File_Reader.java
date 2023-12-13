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
import register.Register;

/**
 *
 * @author Tomas
 */
public class File_Reader {
    
    static String line;
    static Instruction current_inst;
    
    public static Instruction readNextLine() {
//        line.read(); // BufferedReader stores the file line into variable "line" 
        transform(); // Turns the line into an instruction and updates "current_inst"
        return current_inst;
    }

    public static void readLine(String file) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            if ((line = br.readLine()) == null) { // Read the String line
                System.out.println("End of file reached.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static void transform() { // Turns the line into an instruction and updates "current_inst"
        
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
        i++;
        while (lineArr[i] != ',') { // dst
            dstStr += lineArr[i];
            i++;
        }
        i++;
        while (lineArr[i] != ',') { // src1
            src1Str += lineArr[i];
            i++;
        }
        i++;
        while (lineArr[i] != ';') { // src2
            src2Str += lineArr[i];
            i++;
        }
    }

    private File_Reader(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
