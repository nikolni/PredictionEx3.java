package system.engine.world.definition.property.impl;

import system.engine.world.definition.property.api.AbstractPropertyDefinition;
import system.engine.world.definition.value.generator.api.ValueGenerator;
import system.engine.world.rule.enums.Type;

public class FloatPropertyDefinition extends AbstractPropertyDefinition <Float> {

    public FloatPropertyDefinition(String uniqueName, ValueGenerator<Float> valueGenerator) {
        super(uniqueName, Type.FLOAT, valueGenerator);
    }
}
