package engine.per.file.engine.world.definition.value.generator.impl.init.impl.string;


import engine.per.file.engine.world.definition.value.generator.impl.init.api.AbstractInitValueGenerator;

public class InitStringGenerator extends AbstractInitValueGenerator<String> {

    public InitStringGenerator(String initValue) {
        setValue(initValue);
    }
}
