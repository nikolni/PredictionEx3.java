
package system.engine.impl;

import dto.api.*;
import dto.creation.*;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.impl.DTOSimulationEndingForUiImpl;
import dto.impl.DTOWorldGridForUiImpl;
import jaxb2.copy.WorldFromXml;
import system.engine.api.SystemEngineAccess;
import system.engine.run.simulation.api.RunSimulation;
import system.engine.run.simulation.impl.RunSimulationImpl;
import system.engine.run.simulation.manager.RunSimulationManager;
import system.engine.world.api.WorldDefinition;
import system.engine.world.api.WorldInstance;
import system.engine.world.definition.property.api.PropertyDefinition;
import system.engine.world.definition.value.generator.api.ValueGenerator;
import system.engine.world.definition.value.generator.api.ValueGeneratorFactory;
import system.engine.world.execution.instance.environment.api.EnvVariablesInstanceManager;
import system.engine.world.execution.instance.environment.impl.EnvVariablesInstanceManagerImpl;
import system.engine.world.termination.condition.api.TerminationCondition;
import system.engine.world.termination.condition.impl.TicksTerminationConditionImpl;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

public class SystemEngineAccessImpl implements SystemEngineAccess {

    private WorldDefinition worldDefinition;
    //private List< WorldInstance> worldInstances;
    //private final List<DTOSimulationEndingForUi> simulationEndingForUiList;
    private EnvVariablesInstanceManager envVariablesInstanceManager;
    private boolean isHaveValidFileInSystem=false;
    private final Map<Integer, WorldInstance> simulationIdToWorldInstance;
    private RunSimulationManager runSimulationManager;


    public SystemEngineAccessImpl() {
        this.simulationIdToWorldInstance = new HashMap<>();
        //simulationEndingForUiList=new ArrayList<>();

    }

    @Override
    public void getXMLFromUser(String xmlPath) throws JAXBException, FileNotFoundException {
        WorldFromXml worldFromXml = new WorldFromXml();
        worldDefinition = worldFromXml.FromXmlToPRDWorld(xmlPath);
        runSimulationManager = new RunSimulationManager( worldDefinition.getThreadPoolSize(), simulationIdToWorldInstance);
        isHaveValidFileInSystem=true;
        simulationIdToWorldInstance.clear();
    }

    public boolean getIsHaveValidFileInSystem() {
        return isHaveValidFileInSystem;
    }

    @Override
    public DTODefinitionsForUi getDefinitionsDataFromSE() {
        return new CreateDTODefinitionsForUi().getData(worldDefinition);
    }

    @Override
    public DTOEnvVarsDefForUi getEVDFromSE() {
        return new CreateDTOEVDForUi().getData(worldDefinition);
    }

    @Override
    public DTOEnvVarsInsForUi getEVIFromSE() {
        return new CreateDTOEVIForUi().getData(envVariablesInstanceManager);
    }

    @Override
    public DTOSimulationsTimeRunDataForUi getSimulationsTimeRunDataFromSE() {
        return new CreateDTOSimulationsTimeRunDataForUi().getData(simulationIdToWorldInstance);
    }

    @Override
    public DTOEntitiesAfterSimulationByQuantityForUi getEntitiesDataAfterSimulationRunningByQuantity(Integer simulationID) {
        return new CreateDTOEntitiesAfterSimulationByQuantityForUi().getData(worldDefinition, simulationIdToWorldInstance.get(simulationID ));
    }

    @Override
    public DTONamesListForUi getEntitiesNames() {
        return new CreateEntitiesNamesListForUi().getData(worldDefinition);
    }

    @Override
    public DTONamesListForUi getPropertiesNames(int entityDefinitionIndex) {
        return new CreatePropertiesNamesListForUi().getData(worldDefinition.getEntityDefinitionManager().
                getDefinitions().get(entityDefinitionIndex -1));
    }


    @Override
    public DTOPropertyHistogramForUi getPropertyDataAfterSimulationRunningByHistogram(Integer simulationID,
                                                  int entityDefinitionIndex,int propertyIndex) {
        String entityName = worldDefinition.getEntityDefinitionManager().
                getDefinitions().get(entityDefinitionIndex -1).getUniqueName();
        String propertyName = worldDefinition.getEntityDefinitionManager().
                getDefinitions().get(entityDefinitionIndex -1).getProps().get(propertyIndex -1).getUniqueName();

        return new CreateDTOPropertyHistogramForUi().getData(simulationIdToWorldInstance.get(simulationID), entityName, propertyName);
    }

    @Override
    public void updateEntitiesPopulation(DTOPopulationValuesForSE dtoPopulationValuesForSE) {
        Map<String, Integer> entityNameDefToPopulation = dtoPopulationValuesForSE.getEntityNameDefToPopulation();
        worldDefinition.addPopulationToEntitiesDefinition(entityNameDefToPopulation);
    }
    @Override
    public void updateEnvironmentVarDefinition(DTOEnvVarDefValuesForSE dtoEnvVarDefValuesForSE) {
        int index = 0;

        List<Object> initValues = dtoEnvVarDefValuesForSE.getEnvironmentVarInitValues();
        List<PropertyDefinition> propertyDefinitions = createListOfPropertyDefinitionsFromDTO(dtoEnvVarDefValuesForSE.getPropertyDefinitionDTOList());
        for (PropertyDefinition propertyDefinition : propertyDefinitions) {
            if (initValues.get(index) != null) {
                propertyDefinition.setValueGenerator(createInitValueGenerator(propertyDefinition, initValues.get(index)));
            }
            index++;
        }
        createEnvVarInstanceManager();
    }

    private ValueGenerator createInitValueGenerator(PropertyDefinition propertyDefinition, Object initValue){
        ValueGenerator valueGenerator = null;

        switch (propertyDefinition.getType()) {

            case DECIMAL:
                valueGenerator= ValueGeneratorFactory.createFixedInteger((int) (propertyDefinition.getRange().get(0)),
                        (int) (propertyDefinition.getRange().get(1)),(int)initValue );
                break;
            case FLOAT:
                valueGenerator = ValueGeneratorFactory.createFixedFloat((float) (propertyDefinition.getRange().get(0)),
                        (float) (propertyDefinition.getRange().get(1)),(float)initValue );
                break;
            case BOOLEAN:
                valueGenerator=ValueGeneratorFactory.createFixedBoolean((Boolean) initValue);
                break;
            case STRING:
                valueGenerator=ValueGeneratorFactory.createFixedString((String) initValue);
                break;
        }
        return valueGenerator;
    }

    private List<PropertyDefinition> createListOfPropertyDefinitionsFromDTO(List<PropertyDefinitionDTO> propertyDefinitionDTOList){
        List<PropertyDefinition> propertyDefinitions = new ArrayList<>();
        for(PropertyDefinitionDTO environmentVar : propertyDefinitionDTOList){
            propertyDefinitions.add(worldDefinition.getEnvVariablesDefinitionManager().getEnvVar(environmentVar.getUniqueName()));
        }
        return propertyDefinitions;
    }

    private void createEnvVarInstanceManager(){
        envVariablesInstanceManager= new EnvVariablesInstanceManagerImpl(worldDefinition.getEnvVariablesDefinitionManager());

    }
    @Override
    public void addWorldInstance(Integer simulationID){
        simulationIdToWorldInstance.put(simulationID, worldDefinition.createWorldInstance(simulationID));
    }

    @Override
    public DTOSimulationEndingForUi runSimulation(Integer simulationID)
            throws IllegalArgumentException{

            int[] terminationConditionArr;

            RunSimulation runSimulationInstance = new RunSimulationImpl(simulationIdToWorldInstance.get(simulationID));
            runSimulationManager.addSimulationIdToRunSimulation(simulationID, runSimulationInstance);
            terminationConditionArr = runSimulationInstance.runSimulationOnLastWorldInstance(worldDefinition,
                    simulationIdToWorldInstance.get(simulationID), envVariablesInstanceManager);

            DTOSimulationEndingForUi dtoSimulationEndingForUi = new DTOSimulationEndingForUiImpl(simulationID, terminationConditionArr);
            //simulationEndingForUiList.add(dtoSimulationEndingForUi);
            runSimulationManager.increaseCompletedTaskCount();
            return dtoSimulationEndingForUi;
    }
    @Override
    public DTOThreadsPoolStatusForUi getThreadsPoolStatus(){
        return runSimulationManager.getThreadsPoolStatus();
    }

    @Override
    public void addTaskToQueue(Runnable runSimulationRunnable){
        runSimulationManager.addTaskToQueue(runSimulationRunnable);
    }

    @Override
    public int getTotalTicksNumber(){
        List<TerminationCondition> terminationConditionList =  worldDefinition.getTerminationConditionsManager().getTerminationConditionsList();
        for(TerminationCondition terminationCondition: terminationConditionList){
            if(terminationCondition instanceof TicksTerminationConditionImpl){
                return terminationCondition.getTerminationCondition();
            }
        }
        return 0;
    }

    /*@Override
    public List<DTOSimulationEndingForUi> getDTOSimulationEndingForUiList() {
        return simulationEndingForUiList;
    }*/

    @Override
    public DTOPropertyHistogramForUi getPropertyDataAfterSimulationRunningByHistogramByNames(Integer simulationID,
                                                                                             String entityName,String propertyName) {
        return new CreateDTOPropertyHistogramForUi().getData(simulationIdToWorldInstance.get(simulationID), entityName, propertyName);
    }

    @Override
    public DTOSimulationProgressForUi getDtoSimulationProgressForUi(Integer simulationID){
        return runSimulationManager.getDtoSimulationProgressForUi(simulationID);
    }

    @Override
    public DTOWorldGridForUi getDTOWorldGridForUi(){
        DTOWorldGridForUi dtoWorldGridForUi = new DTOWorldGridForUiImpl(worldDefinition.getGridRows(), worldDefinition.getGridColumns());
        return dtoWorldGridForUi;
    }
    @Override
    public void pauseSimulation(int simulationID){
        runSimulationManager.pauseSimulation(simulationID);
    }

    @Override
    public void resumeSimulation(int simulationID){
        runSimulationManager.resumeSimulation(simulationID);
    }
    @Override
    public void cancelSimulation(int simulationID){
        runSimulationManager.cancelSimulation(simulationID);
    }

    @Override
    public TerminationConditionsDTO getTerminationConditions() {
        return new CreateDTODefinitionsForUi().getData(worldDefinition).
                getTerminationConditionsDTOManager().getTerminationConditionsDTOList().get(0);
    }
}
