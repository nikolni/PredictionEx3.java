package system.engine.world.definition.value.generator.impl.random.impl.numeric;

public class RandomFloatGenerator extends AbstractNumericRandomGenerator<Float> {

    public RandomFloatGenerator(Float from, Float to) {
        super(from, to);
    }

    @Override
    public Float generateValue() {
        return from + random.nextFloat() * (to - from);
    }
}
