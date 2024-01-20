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

    public File_Writer() {
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

    public void writeStage(int cycle, int instructionId, Stages stage, Operator operator) {
        if (cycle > this.cyclesCount) {
            this.cyclesCount = cycle;
        }

        if (!this.isInstructionEncountered(instructionId)) {
            this.addNewInstruction(cycle, instructionId, stage, operator);
        } else {
            this.addNewStageForInstruction(cycle, instructionId, stage);
        }
    }

    private boolean isInstructionEncountered(int instructionId) {
        return this.instructionsOperators.containsKey(instructionId);
    }

    private void addNewStageForInstruction(int cycle, int instructionId, Stages stage) {
        Map<Integer, Stages> instructionRow = this.getInstructionRow(instructionId);
        instructionRow.put(cycle, stage);
    }

    private void addNewInstruction(int cycle, int instructionId, Stages stage, Operator operator) {
        Map<Integer, Stages> newInstructionRow = new HashMap<Integer, Stages>();
        newInstructionRow.put(cycle, stage);

        this.stages.add(newInstructionRow);

        this.instructionsOperators.put(instructionId, operator);
        this.instructionsStart.put(instructionId, cycle);
    }

    private Map<Integer, Stages> getInstructionRow(int instructionId) {
        Iterator<Integer> it = this.instructionsOperators.keySet().iterator();
        int instructionOrder = 0;

        while (it.hasNext()) {
            if (it.next().equals(instructionId)) {
                return this.stages.get(instructionOrder);
            }
            instructionOrder++;
        }
        return null;
    }

    private void writeLineToFile(String line) {
        for (int i = 0; i < line.length(); ++i) {
            try {
                this.writer.write(line.charAt(i));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void writeNewLineToFile() {
        final String NEWLINE = "\n";
        this.writeLineToFile(NEWLINE);
    }

    private void writeFile() {
        this.writeCycleTimelineHeader();
        this.writeNewLineToFile();
        this.writeInstructionRows();
    }

    private void writeCycleTimelineHeader() {
        final String TAB = "\t";
        final String SPACE_BETWEEN_NUMBERS = "   ";

        StringBuilder cyclesHeader = new StringBuilder(TAB);
        for (int i = 0; i < this.cyclesCount; i++) {
            cyclesHeader.append(i + 1);
            cyclesHeader.append(SPACE_BETWEEN_NUMBERS);
        }

        this.writeLineToFile(cyclesHeader.toString());
        this.writeNewLineToFile();
    }

    private void writeInstructionRows() {
        for (Map.Entry<Integer, Operator> entry : this.instructionsOperators.entrySet()) {
            int instructionId = entry.getKey();
            String instructionName = entry.getValue().toString();

            String instructionLine = this.getInstructionLine(instructionId, instructionName);

            this.writeLineToFile(instructionLine);
            this.writeNewLineToFile();
        }
    }

    private String getInstructionLine(int instructionId, String instructionName){
        final String TAB = "\t";

        StringBuilder instructionLine = new StringBuilder(instructionName + TAB);
        Map<Integer, Stages> instructionStages = this.getInstructionRow(instructionId);

        this.fillInstructionUntilFirstCycle(instructionId, instructionLine);
        this.fillInstructionWithStages(instructionId, instructionLine, instructionStages);
        
        return instructionLine.toString();
    }

    private StringBuilder fillInstructionUntilFirstCycle(int instructionId, StringBuilder instructionLine) {
        String EMPTY_CYCLE_SPACE = "    ";
        int instructionStartCycle = this.instructionsStart.get(instructionId);

        for (int i = 1; i < instructionStartCycle; i++) {
            instructionLine.append(EMPTY_CYCLE_SPACE);
        }

        return instructionLine;
    }

    private StringBuilder fillInstructionWithStages(int instructionId,
                                                    StringBuilder instructionLine,
                                                    Map<Integer, Stages> instructionStages) {
        final String SPACE_BETWEEN_STAGES = "   ";

        Iterator<Integer> it = instructionStages.keySet().iterator();
        int instructionStartCycle = this.instructionsStart.get(instructionId);

        while (it.hasNext()) {
            String stage = instructionStages.get(instructionStartCycle).toString();

            instructionLine.append(stage);
            instructionLine.append(SPACE_BETWEEN_STAGES);

            instructionStartCycle++;
            it.next();
        }

        return instructionLine;
    }

    public void makeFile() {
        try {
            this.writeFile();
            this.writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
