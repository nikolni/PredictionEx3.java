package system.engine.world.definition.property.api;

import system.engine.world.definition.value.generator.api.ValueGenerator;
import system.engine.world.definition.value.generator.impl.init.impl.numeric.AbstractNumericInitGenerator;
import system.engine.world.definition.value.generator.impl.random.api.AbstractRandomValueGenerator;
import system.engine.world.definition.value.generator.impl.random.impl.numeric.AbstractNumericRandomGenerator;
import system.engine.world.rule.enums.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition {

    private final String uniqueName;
    private final Type propertyType;
    private ValueGenerator<T> valueGenerator;

    public AbstractPropertyDefinition(String uniqueName, Type propertyType, ValueGenerator<T> valueGenerator) {
        this.uniqueName = uniqueName;
        this.propertyType = propertyType;
        this.valueGenerator = valueGenerator;
    }

    @Override
    public String getUniqueName() {
        return uniqueName;
    }

    @Override
    public Type getType() {
        return propertyType;
    }

    @Override
    public ValueGenerator getValueGenerator(){
        return valueGenerator;
    }

    @Override
    public void setValueGenerator(ValueGenerator valueGenerator){
        this.valueGenerator = valueGenerator;
    }

    @Override
    public T generateValue() {
        return valueGenerator.generateValue();
    }

    @Override
    public Boolean isRandomInitialized(){
        return this.valueGenerator instanceof AbstractRandomValueGenerator;
    }

    @Override
    public Boolean doesHaveRange(){
        return (this.propertyType.equals(Type.DECIMAL) | this.propertyType.equals(Type.FLOAT));
    }

    @Override
    public List<Object> getRange(){
        List<Object> rangeArray = new ArrayList<>();
        if(doesHaveRange()){
            if(isRandomInitialized()){
                AbstractNumericRandomGenerator numericRandomGenerator =(AbstractNumericRandomGenerator) valueGenerator;
                rangeArray.add(numericRandomGenerator.getFrom());
                rangeArray.add(numericRandomGenerator.getTO());
            }
            else {
                AbstractNumericInitGenerator numericInitGenerator =(AbstractNumericInitGenerator) valueGenerator;
                rangeArray.add(numericInitGenerator.getFrom());
                rangeArray.add(numericInitGenerator.getTO());
            }

        }
        return rangeArray;
    }
}
