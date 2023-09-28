package dto.include;

import dto.primary.DTOEnvVarsDefForUi;
import dto.primary.DTONamesListForUi;
import dto.primary.DTOWorldGridForUi;

public class DTOIncludeForExecutionForUi {
    private DTOWorldGridForUi dtoWorldGridForUi;
    private DTOEnvVarsDefForUi dtoEnvVarsDefForUi;
    private DTONamesListForUi dtoNamesListForUi;

    public DTOIncludeForExecutionForUi(DTOWorldGridForUi dtoWorldGridForUi, DTOEnvVarsDefForUi dtoEnvVarsDefForUi,
                                       DTONamesListForUi dtoNamesListForUi) {
        this.dtoWorldGridForUi = dtoWorldGridForUi;
        this.dtoEnvVarsDefForUi = dtoEnvVarsDefForUi;
        this.dtoNamesListForUi = dtoNamesListForUi;
    }


    public DTOWorldGridForUi getDtoWorldGridForUi() {
        return dtoWorldGridForUi;
    }

    public DTOEnvVarsDefForUi getDtoEnvVarsDefForUi() {
        return dtoEnvVarsDefForUi;
    }

    public DTONamesListForUi getDtoNamesListForUi() {
        return dtoNamesListForUi;
    }


}
