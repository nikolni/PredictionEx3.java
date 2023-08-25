package system.engine.world.definition.property.impl;


import system.engine.world.definition.property.api.AbstractPropertyDefinition;
import system.engine.world.definition.value.generator.api.ValueGenerator;
import system.engine.world.rule.enums.Type;

public class StringPropertyDefinition extends AbstractPropertyDefinition<String> {

    public StringPropertyDefinition(String uniqueName, ValueGenerator<String> valueGenerator) {
        super(uniqueName, Type.STRING, valueGenerator);
    }
}