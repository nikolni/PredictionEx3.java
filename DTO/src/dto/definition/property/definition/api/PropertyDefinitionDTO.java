package dto.definition.property.definition.api;

import java.util.List;

public interface PropertyDefinitionDTO {
    String getUniqueName();
    String getType();
    Boolean doesHaveRange();
    List<Object> getRange();
    Boolean isRandomInitialized();
}