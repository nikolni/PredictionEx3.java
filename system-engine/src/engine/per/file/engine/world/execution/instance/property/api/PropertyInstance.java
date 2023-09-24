package engine.per.file.engine.world.execution.instance.property.api;

import engine.per.file.engine.world.definition.property.api.PropertyDefinition;

public interface PropertyInstance {
    Float calculatePropertyAverage();

    PropertyDefinition getPropertyDefinition();
    Object getValue();
    void setValue(Object val);
    void setLastTickNumberOfValueUpdate(Integer lastTickNumberOfValueUpdate, Object newValue);
    Integer getLastTickNumberOfValueUpdate();
}
