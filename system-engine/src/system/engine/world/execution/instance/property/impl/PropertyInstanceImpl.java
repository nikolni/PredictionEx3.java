package system.engine.world.execution.instance.property.impl;

import javafx.beans.property.IntegerProperty;
import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.execution.instance.property.api.PropertyInstance;

public class PropertyInstanceImpl implements PropertyInstance {

    private PropertyDefinition propertyDefinition;
    private Object value;
    private Integer lastTickNumberOfValueUpdate = 0;

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition, Object value) {
        this.propertyDefinition = propertyDefinition;
        this.value = value;

    }

    @Override
    public PropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object val) {
        this.value = val;
    }
    @Override
    public void setLastTickNumberOfValueUpdate(Integer lastTickNumberOfValueUpdate, Object newValue) {
        if(! value.equals(newValue)){
            this.lastTickNumberOfValueUpdate = lastTickNumberOfValueUpdate;
        }

    }
    @Override
    public Integer getLastTickNumberOfValueUpdate(){
        return lastTickNumberOfValueUpdate;}
}
