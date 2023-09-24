package engine.per.file.engine.world.definition.property.api;

import engine.per.file.engine.world.definition.value.generator.api.ValueGenerator;
import engine.per.file.engine.world.rule.enums.Type;

import java.util.List;

public interface PropertyDefinition {
    String getUniqueName();
    Type getType();
    Object generateValue();
    ValueGenerator getValueGenerator();

    void setValueGenerator(ValueGenerator valueGenerator);

    Boolean doesHaveRange();
    List<Object> getRange();
    Boolean isRandomInitialized();
}