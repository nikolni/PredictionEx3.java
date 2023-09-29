package engine.per.file.engine.world.definition.property.impl;


import engine.per.file.engine.world.definition.property.api.AbstractPropertyDefinition;
import engine.per.file.engine.world.definition.value.generator.api.ValueGenerator;
import engine.per.file.engine.world.rule.enums.Type;

public class StringPropertyDefinition extends AbstractPropertyDefinition<String> {

    public StringPropertyDefinition(String uniqueName, ValueGenerator<String> valueGenerator) {
        super(uniqueName, Type.STRING, valueGenerator);
    }
}