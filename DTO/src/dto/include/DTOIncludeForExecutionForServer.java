package dto.include;

import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.primary.DTOEnvVarDefValuesForSE;
import dto.primary.DTOPopulationValuesForSE;

public class DTOIncludeForExecutionForServer {
    private final DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE;
    private final DTOPopulationValuesForSE dtoPopulationValuesForSE;

    public DTOIncludeForExecutionForServer(DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE,
                                           DTOPopulationValuesForSE dtoPopulationValuesForSE) {
        this.dtoEnvVarDefValuesForSE = dtoEnvVarDefValuesForSE;
        this.dtoPopulationValuesForSE = dtoPopulationValuesForSE;
    }


    public DTOEnvVarDefValuesForSE getDtoEnvVarDefValuesForSE() {
        return dtoEnvVarDefValuesForSE;
    }

    public DTOPopulationValuesForSE getDtoPopulationValuesForSE() {
        return dtoPopulationValuesForSE;
    }


}
