package system.engine.world.definition.value.generator.api;


import system.engine.world.definition.value.generator.impl.init.impl.bool.InitBooleanValueGenerator;
import system.engine.world.definition.value.generator.impl.init.impl.numeric.InitFloatGenerator;
import system.engine.world.definition.value.generator.impl.init.impl.numeric.InitIntegerGenerator;
import system.engine.world.definition.value.generator.impl.init.impl.string.InitStringGenerator;
import system.engine.world.definition.value.generator.impl.random.impl.bool.RandomBooleanValueGenerator;
import system.engine.world.definition.value.generator.impl.random.impl.numeric.RandomFloatGenerator;
import system.engine.world.definition.value.generator.impl.random.impl.numeric.RandomIntegerGenerator;
import system.engine.world.definition.value.generator.impl.random.impl.string.RandomStringGenerator;

public interface ValueGeneratorFactory {

    static ValueGenerator<Integer> createFixedInteger(Integer from, Integer to,Integer value) {
        return new InitIntegerGenerator(from, to,value);
    }

    static ValueGenerator<Float> createFixedFloat(Float from, Float to, Float value) {
        return new InitFloatGenerator(from, to,value);
    }
    static ValueGenerator<Boolean> createFixedBoolean(Boolean value) {
        return new InitBooleanValueGenerator(value);
    }
    static ValueGenerator<String> createFixedString(String value) {
        return new InitStringGenerator(value);
    }

    static ValueGenerator<Boolean> createRandomBoolean() {
        return new RandomBooleanValueGenerator();
    }

    static ValueGenerator<Integer> createRandomInteger(Integer from, Integer to) {
        return new RandomIntegerGenerator(from, to);
    }
    static ValueGenerator<Float> createRandomFloat(Float from, Float to) {
        return new RandomFloatGenerator(from, to);
    }

    static ValueGenerator<String> createRandomString() {
        return new RandomStringGenerator();
    }
}
