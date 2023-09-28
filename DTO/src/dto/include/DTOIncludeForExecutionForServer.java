package dto.include;

import dto.primary.DTOEnvVarDefValuesForSE;
import dto.primary.DTOEnvVarsDefForUi;
import dto.primary.DTONamesListForUi;
import dto.primary.DTOPopulationValuesForSE;

public class DTOIncludeForExecutionForServer {
    private DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE;
    private DTOPopulationValuesForSE dtoPopulationValuesForSE;

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
