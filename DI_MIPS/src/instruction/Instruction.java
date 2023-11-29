/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instruction;

import register.Register;

/**
 *
 * @author Tomas
 */
public class Instruction {

    int id;
    Operator operator;
    Register dst;
    Register src1;
    Register src2;
    Stages stage;

    public Instruction(int id, Operator operator, Register dst, Register src1, Register src2, Stages stage) {
        this.id = id;
        this.operator = operator;
        this.dst = dst;
        this.src1 = src1;
        this.src2 = src2;
        this.stage = stage;
    }
    
    public void execute(int cic){

    }

    public void moveNextStage(){
        if(stage != Stages.W){
            Stages[] stages = Stages.values();
            stage = stages[stage.ordinal() + 1];
        }
    }
}

