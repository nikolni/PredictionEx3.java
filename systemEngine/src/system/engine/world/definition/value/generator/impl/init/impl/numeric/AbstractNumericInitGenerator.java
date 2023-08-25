package system.engine.world.definition.value.generator.impl.init.impl.numeric;

import system.engine.world.definition.value.generator.impl.init.api.AbstractInitValueGenerator;

public abstract class AbstractNumericInitGenerator<T> extends AbstractInitValueGenerator<T> {

    protected T from;
    protected T to;


    protected void setFrom(T from){this.from = from;}
    protected void setTo(T to){this.to = to;}


    public T getFrom(){return from;}
    public T getTO(){return to;}

}
