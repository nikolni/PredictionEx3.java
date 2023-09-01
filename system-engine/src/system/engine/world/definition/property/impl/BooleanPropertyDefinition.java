package system.engine.world.definition.property.impl;

import system.engine.world.definition.property.api.AbstractPropertyDefinition;
import system.engine.world.definition.value.generator.api.ValueGenerator;
import system.engine.world.rule.enums.Type;

public class BooleanPropertyDefinition extends AbstractPropertyDefinition<Boolean> {
    public BooleanPropertyDefinition(String uniqueName, ValueGenerator<Boolean> valueGenerator) {
        super(uniqueName, Type.BOOLEAN, valueGenerator);
    }
}
