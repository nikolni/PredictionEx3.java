package system.engine.api;

import dto.api.*;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import system.engine.world.termination.condition.api.TerminationCondition;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SystemEngineAccess {

    void getXMLFromUser(String xmlPath) throws JAXBException, FileNotFoundException;
    boolean getIsHaveValidFileInSystem();

    DTODefinitionsForUi getDefinitionsDataFromSE();
    DTOEnvVarsDefForUi getEVDFromSE();

    DTOEnvVarsInsForUi getEVIFromSE();
    DTOSimulationsTimeRunDataForUi getSimulationsTimeRunDataFromSE();

    DTOEntitiesAfterSimulationByQuantityForUi getEntitiesDataAfterSimulationRunningByQuantity(Integer simulationID);

    DTONamesListForUi getEntitiesNames();

    DTONamesListForUi getPropertiesNames(int entityDefinitionIndex );
    DTOPropertyHistogramForUi getPropertyDataAfterSimulationRunningByHistogram(Integer simulationID,
                                                                               int entityDefinitionIndex,int propertyIndex);
    void updateEntitiesPopulation(DTOPopulationValuesForSE dtoPopulationValuesForSE);
    void updateEnvironmentVarDefinition(DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE);
    void addWorldInstance(Integer simulationID);

    DTOSimulationEndingForUi runSimulation(Integer simulationID) ;

    int getTotalTicksNumber();

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
}
