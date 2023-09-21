package dto.primary;

import java.util.Map;

public class DTORerunValuesForUi {
    private Map<String, Object> environmentVarsValues;
    private Map<String, Integer> entitiesPopulations;


    public DTORerunValuesForUi(Map<String, Object> environmentVarsValues, Map<String, Integer> entitiesPopulations) {
        this.environmentVarsValues = environmentVarsValues;
        this.entitiesPopulations = entitiesPopulations;
    }

    public Map<String, Object> getEnvironmentVarsValues() {
        return environmentVarsValues;
    }

    public Map<String, Integer> getEntitiesPopulations() {
        return entitiesPopulations;
    }

}
