package engine.per.file.engine.api;

import dto.primary.*;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import engine.per.file.engine.world.definition.entity.manager.api.EntityDefinitionManager;
import engine.per.file.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import engine.per.file.jaxb2.generated.PRDWorld;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface SystemEngineAccess {

    void getXMLFromUser(String xmlPath) throws JAXBException, FileNotFoundException;

 void fromFileToSE(PRDWorld prdWorld);

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

    void runSimulation(Integer simulationID) ;

    int getTotalTicksNumber();
    int getTotalSecondsNumber();

   // List<DTOSimulationEndingForUi> getDTOSimulationEndingForUiList();
    DTOPropertyHistogramForUi getPropertyDataAfterSimulationRunningByHistogramByNames(Integer simulationID,
                                                                                      String entityName,String propertyName);



    DTOEntityPropertyConsistencyForUi getConsistencyDTOByEntityPropertyName(Integer simulationID,
                                                                            String entityName, String propertyName);

    DTOSimulationProgressForUi getDtoSimulationProgressForUi(Integer simulationID);
    void addTaskToQueue(Runnable runSimulationRunnable);
     DTOThreadsPoolStatusForUi getThreadsPoolStatus();
    DTOWorldGridForUi getDTOWorldGridForUi();
    void pauseSimulation(int simulationID);
    void resumeSimulation(int simulationID);
    void cancelSimulation(int simulationID);
    List<TerminationConditionsDTO> getTerminationConditions();
    DTORerunValuesForUi getValuesForRerun(Integer simulationID);
    List<String> getAllSimulationsStatus();
    String getSimulationStatusByID(Integer executionID);
    void prepareForExecution(DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE ,
                        DTOPopulationValuesForSE dtoPopulationValuesForSE, Integer executionID);
}
