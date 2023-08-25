package dto.definition.property.definition.impl;


import java.util.List;

public class PropertyDefinitionDTOImpl implements dto.definition.property.definition.api.PropertyDefinitionDTO {

    private final String uniqueName;
    private final String propertyType;
    private final Boolean isRandomInitialized;
    private final Boolean doesHaveRange;
    private final List<Object> rangeArray;

    public PropertyDefinitionDTOImpl(String uniqueName, String propertyType, Boolean isRandomInitialized,
                                     Boolean doesHaveRange, List<Object> rangeArray) {
        this.uniqueName = uniqueName;
        this.propertyType = propertyType;
        this.isRandomInitialized = isRandomInitialized;
        this.doesHaveRange = doesHaveRange;
        this.rangeArray = rangeArray;
    }

    @Override
    public String getUniqueName() {
        return uniqueName;
    }

    @Override
    public String getType() {
        return propertyType;
    }

    @Override
    public Boolean isRandomInitialized(){
        return isRandomInitialized;
    }

    @Override
    public Boolean doesHaveRange(){
        return doesHaveRange;
    }

    @Override
    public List<Object> getRange(){
        return rangeArray;
    }

}
