package system.engine.api;

import dto.api.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import system.engine.run.simulation.SimulationCallback;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

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
    void addWorldInstance();

    DTOSimulationEndingForUi runSimulation(SimulationCallback callback, SimpleBooleanProperty isResumed, Integer simulationID);

    int getTotalTicksNumber();

    List<DTOSimulationEndingForUi> getDTOSimulationEndingForUiList();
    DTOPropertyHistogramForUi getPropertyDataAfterSimulationRunningByHistogramByNames(Integer simulationID,
                                                                                      String entityName,String propertyName);
    DTOSimulationProgressForUi getDtoSimulationProgressForUi(Integer simulationID);
    void addTaskToQueue(Task<Boolean> runSimulationTask);
    DTOWorldGridForUi getDTOWorldGridForUi();

}
