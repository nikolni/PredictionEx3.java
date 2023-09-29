package engine.per.file.engine.world.definition.value.generator.impl.init.impl.numeric;

public class InitIntegerGenerator extends AbstractNumericInitGenerator<Integer> {

    public InitIntegerGenerator(Integer from, Integer to,Integer initValue) {
        if(isBiggerThanRange(to, initValue)){
            this.setValue(to);
        } else if (isSmallerThanRange(from, initValue)) {
            this.setValue(from);
        }
        else{
            this.setValue(initValue);
        }
        setFrom(from);
        setTo(to);
    }

    private boolean isBiggerThanRange(Integer to,Integer initValue){
        return initValue > to;
    }

    private boolean isSmallerThanRange(Integer from,Integer initValue){
        return initValue < from;
    }
}
