package dto.api;

import java.util.Map;

public interface DTORerunValuesForUi {
     Map<String, Object> getEnvironmentVarsValues() ;

     Map<String, Integer> getEntitiesPopulations();
}
