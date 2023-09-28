package dto.definition.property.definition;


import java.util.List;

public class PropertyDefinitionDTO {

    private final String uniqueName;
    private final String propertyType;
    private final Boolean isRandomInitialized;
    private final Boolean doesHaveRange;
    private final List<Object> rangeArray;

    public PropertyDefinitionDTO(String uniqueName, String propertyType, Boolean isRandomInitialized,
                                 Boolean doesHaveRange, List<Object> rangeArray) {
        this.uniqueName = uniqueName;
        this.propertyType = propertyType;
        this.isRandomInitialized = isRandomInitialized;
        this.doesHaveRange = doesHaveRange;
        this.rangeArray = rangeArray;
    }


    public String getUniqueName() {
        return uniqueName;
    }


    public String getType() {
        return propertyType;
    }


    public Boolean isRandomInitialized(){
        return isRandomInitialized;
    }


    public Boolean doesHaveRange(){
        return doesHaveRange;
    }


    public List<Object> getRange(){
        return rangeArray;
    }

}
