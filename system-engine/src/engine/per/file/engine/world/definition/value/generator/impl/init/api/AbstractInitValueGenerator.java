package engine.per.file.engine.world.definition.value.generator.impl.init.api;


import engine.per.file.engine.world.definition.value.generator.api.ValueGenerator;

public abstract class AbstractInitValueGenerator<T> implements ValueGenerator<T> {

    protected T initValue;


    protected void setValue(T initValue){this.initValue = initValue;}

    @Override
    public T generateValue() {
        return initValue;
    }
}
