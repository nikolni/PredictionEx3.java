package engine.per.file.engine.world.definition.property.impl;

import engine.per.file.engine.world.definition.property.api.AbstractPropertyDefinition;
import engine.per.file.engine.world.definition.value.generator.api.ValueGenerator;
import engine.per.file.engine.world.rule.enums.Type;

public class BooleanPropertyDefinition extends AbstractPropertyDefinition<Boolean> {
    public BooleanPropertyDefinition(String uniqueName, ValueGenerator<Boolean> valueGenerator) {
        super(uniqueName, Type.BOOLEAN, valueGenerator);
    }
}
