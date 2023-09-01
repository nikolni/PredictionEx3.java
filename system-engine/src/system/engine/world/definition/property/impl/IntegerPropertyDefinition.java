package system.engine.world.definition.property.impl;


import system.engine.world.definition.property.api.AbstractPropertyDefinition;
import system.engine.world.definition.value.generator.api.ValueGenerator;
import system.engine.world.rule.enums.Type;

public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {

    public IntegerPropertyDefinition(String uniqueName, ValueGenerator<Integer> valueGenerator) {
        super(uniqueName, Type.DECIMAL, valueGenerator);
    }

}
