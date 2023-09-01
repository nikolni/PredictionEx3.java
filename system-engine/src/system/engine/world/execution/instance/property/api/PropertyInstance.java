package system.engine.world.execution.instance.property.api;

import system.engine.world.definition.property.api.PropertyDefinition;

public interface PropertyInstance {
    PropertyDefinition getPropertyDefinition();
    Object getValue();
    void setValue(Object val);

}
