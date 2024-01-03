/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;


import instruction.Operator;
import instruction.Stages;

/**
 *
 * @author Vasil
 */
public class File_Writer {
    
    private List<Map<Integer, Stages>> stages;
    private Map<Integer, Operator> instructionsOperators;
    private Map<Integer, Integer> instructionsStart;

    private FileWriter writer;
    private int cyclesCount;

    public File_Writer(){
        this.stages = new ArrayList<>();

        this.instructionsOperators = new LinkedHashMap<Integer, Operator>(); 
        this.instructionsStart = new HashMap<Integer, Integer>(); 

        this.cyclesCount = 0;
        String fileName = "./output.txt";

        try {
            this.writer = new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void writeStage(int cycle, int instructionId, Stages stage, Operator operator){
        if (cycle > this.cyclesCount) {
            this.cyclesCount = cycle;
        }

        if (!this.instructionsOperators.containsKey(instructionId)){
            Map<Integer, Stages> newInstructionRow = new HashMap<Integer, Stages>();
            newInstructionRow.put(cycle, stage);

            this.stages.add(newInstructionRow);

            this.instructionsOperators.put(instructionId, operator);
            this.instructionsStart.put(instructionId, cycle);

            return;
        }

        Iterator<Integer> it = this.instructionsOperators.keySet().iterator();
        int instructionOrder = 0;

        while (it.hasNext()){
            if (it.next().equals(instructionId)) {
                System.out.println("We get instructionId of " + instructionOrder);
                break;
            }
            instructionOrder++;
        }
           
        this.stages.get(instructionOrder).put(cycle, stage);
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

    void printMap(){
        for (Map.Entry<Integer, Operator> entry : this.instructionsOperators.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    void printFile() {
        // print cycle top
        String TRIPPLE_SPACE = "   ";
        String QUADRAPLE_SPACE = "    ";

        StringBuilder cyclesHeader = new StringBuilder(TRIPPLE_SPACE + QUADRAPLE_SPACE);
        for (int i = 0; i < this.cyclesCount; i++) {
            cyclesHeader.append(i + 1);
            cyclesHeader.append(TRIPPLE_SPACE);
        }
        cyclesHeader.append('\n');
        this.writeLineToFile(cyclesHeader.toString());

        //print instructions
        int instructionOrder = 0;
        for (Map.Entry<Integer, Operator> entry : this.instructionsOperators.entrySet()) {
            int instructionId = entry.getKey();
            String instructionName = entry.getValue().toString();

            int instructionCycleStart = this.instructionsStart.get(instructionId);
            StringBuilder instructionLine = new StringBuilder(instructionName);

            // add empty spaces untill start of cycle
            for (int i = 0; i <= instructionCycleStart; i++) {
                instructionLine.append(QUADRAPLE_SPACE);
            }

            // add stages
            Map<Integer, Stages> instructionStages = this.stages.get(instructionOrder);

            Iterator<Integer> it = instructionStages.keySet().iterator();
            while (it.hasNext()) {
                String stage = instructionStages.get(instructionCycleStart).toString();

                instructionLine.append(stage);
                instructionLine.append(TRIPPLE_SPACE);

                instructionCycleStart++;
                it.next();
            }

            // write
            instructionLine.append('\n');
            this.writeLineToFile(instructionLine.toString());

            // increment
            instructionOrder++;
        }
    }

    @Override
    public void finalize() {
        try {
            this.printMap();
            this.printFile();
            this.writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
