package engine.per.file.engine.world.definition.property.impl;

import engine.per.file.engine.world.definition.property.api.AbstractPropertyDefinition;
import engine.per.file.engine.world.definition.value.generator.api.ValueGenerator;
import engine.per.file.engine.world.rule.enums.Type;

public class FloatPropertyDefinition extends AbstractPropertyDefinition<Float> {

    public FloatPropertyDefinition(String uniqueName, ValueGenerator<Float> valueGenerator) {
        super(uniqueName, Type.FLOAT, valueGenerator);
    }
}
