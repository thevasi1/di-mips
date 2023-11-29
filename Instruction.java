public class Instruction {

    int id;
    Operator operator;
    // Register dst;
    // Register src1;
    // Register src2;
    Stages stage;

    public Instruction(int id, Operator operator,  Stages stage) {
        this.id = id;
        this.operator = operator;
        // this.dst = dst;
        // this.src1 = src1;
        // this.src2 = src2;
        this.stage = stage;
    }

    void nextStage(){
        if(stage != Stages.W){
            Stages[] stages = Stages.values();
            stage = stages[stage.ordinal() + 1];
        }
    }
}
