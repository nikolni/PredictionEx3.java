package system.engine.api;

import dto.api.*;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import system.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.termination.condition.api.TerminationCondition;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SystemEngineAccess {

    void getXMLFromUser(String xmlPath) throws JAXBException, FileNotFoundException;
    boolean getIsHaveValidFileInSystem();

    DTODefinitionsForUi getDefinitionsDataFromSE();
    DTOEnvVarsDefForUi getEVDFromSE();

   // DTOEnvVarsInsForUi getEVIFromSE();
    DTOSimulationsTimeRunDataForUi getSimulationsTimeRunDataFromSE();

    DTOEntitiesAfterSimulationByQuantityForUi getEntitiesDataAfterSimulationRunningByQuantity(Integer simulationID);

    DTONamesListForUi getEntitiesNames();

    DTONamesListForUi getPropertiesNames(int entityDefinitionIndex );
    DTOPropertyHistogramForUi getPropertyDataAfterSimulationRunningByHistogram(Integer simulationID,
                                                                               int entityDefinitionIndex,int propertyIndex);
    EntityDefinitionManager updateEntitiesPopulation(DTOPopulationValuesForSE dtoPopulationValuesForSE);
    EnvVariablesInstanceManager updateEnvironmentVarDefinition(DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE);
    void addWorldInstance(Integer simulationID, EnvVariablesInstanceManager envVariablesInstanceManager, EntityDefinitionManager entityDefinitionManager);

    DTOSimulationEndingForUi runSimulation(Integer simulationID) ;

    int getTotalTicksNumber();
    int getTotalSecondsNumber();

   // List<DTOSimulationEndingForUi> getDTOSimulationEndingForUiList();
    DTOPropertyHistogramForUi getPropertyDataAfterSimulationRunningByHistogramByNames(Integer simulationID,
                                                                                      String entityName,String propertyName);
    DTOSimulationProgressForUi getDtoSimulationProgressForUi(Integer simulationID);
    void addTaskToQueue(Runnable runSimulationRunnable);
    public DTOThreadsPoolStatusForUi getThreadsPoolStatus();
    DTOWorldGridForUi getDTOWorldGridForUi();
    void pauseSimulation(int simulationID);
    void resumeSimulation(int simulationID);
    void cancelSimulation(int simulationID);
    TerminationConditionsDTO getTerminationConditions();
    DTORerunValuesForUi getValuesForRerun(Integer simulationID);
}
