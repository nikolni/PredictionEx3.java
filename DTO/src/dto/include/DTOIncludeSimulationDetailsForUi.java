package dto.include;

import dto.primary.DTODefinitionsForUi;
import dto.primary.DTOEnvVarsDefForUi;
import dto.primary.DTOWorldGridForUi;

public class DTOIncludeSimulationDetailsForUi {
    private final DTODefinitionsForUi definitions;
    private final DTOEnvVarsDefForUi envVarsDef;
    private final DTOWorldGridForUi worldGridForUi;

    public DTOIncludeSimulationDetailsForUi(DTODefinitionsForUi definitions, DTOEnvVarsDefForUi envVarsDef, DTOWorldGridForUi worldGridForUi) {
        this.definitions = definitions;
        this.envVarsDef = envVarsDef;
        this.worldGridForUi = worldGridForUi;
    }


    public DTODefinitionsForUi getDefinitions() {
        return definitions;
    }

    public DTOEnvVarsDefForUi getEnvVarsDef() {
        return envVarsDef;
    }

    public DTOWorldGridForUi getWorldGridForUi() {
        return worldGridForUi;
    }
}
