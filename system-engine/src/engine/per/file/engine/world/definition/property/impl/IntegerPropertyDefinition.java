package engine.per.file.engine.world.definition.property.impl;


import engine.per.file.engine.world.definition.property.api.AbstractPropertyDefinition;
import engine.per.file.engine.world.definition.value.generator.api.ValueGenerator;
import engine.per.file.engine.world.rule.enums.Type;

public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {

    public IntegerPropertyDefinition(String uniqueName, ValueGenerator<Integer> valueGenerator) {
        super(uniqueName, Type.DECIMAL, valueGenerator);
    }

}
