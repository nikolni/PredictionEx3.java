package system.engine.world.execution.instance.enitty.api;


import system.engine.world.definition.entity.api.EntityDefinition;
import system.engine.world.execution.instance.property.api.PropertyInstance;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);

    EntityDefinition getEntityDefinition();



    void createConsistencyMapInSingleEntityInstance();

    Float getPropertyConsistencyByName(String PropertyName);

    void addPropertyInstance(PropertyInstance propertyInstance);
    Integer getRow() ;
    Integer getColumns();
    void setRow(Integer newRow);
    void setColumns(Integer newColumns);
    void moveEntityInWorld();
}
