package engine.per.file.engine.world.definition.value.generator.impl.init.impl.numeric;

public class InitFloatGenerator extends AbstractNumericInitGenerator<Float> {

    public InitFloatGenerator(Float from, Float to,Float initValue) {
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

    private boolean isBiggerThanRange(Float to,Float initValue){
        return initValue > to;
    }

    private boolean isSmallerThanRange(Float from,Float initValue){
        return initValue < from;
    }

}
