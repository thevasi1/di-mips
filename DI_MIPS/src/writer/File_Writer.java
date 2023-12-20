/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package writer;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Vasil
 */
public class File_Writer {
    
    private FileWriter writer; 

    File_Writer(){
        String fileName = "../output.txt";
        try {
            this.writer = new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    void writeLineToFile(String line){
        for (int i = 0; i < line.length(); ++i){
            try {
                this.writer.write(line.charAt(i));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } 
        }
    }

    @Override
    protected void finalize() {
        try {
            this.writer.close(); 
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
