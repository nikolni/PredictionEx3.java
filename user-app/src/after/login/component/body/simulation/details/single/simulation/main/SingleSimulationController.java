package after.login.component.body.simulation.details.single.simulation.main;

import after.login.component.body.simulation.details.server.RequestsFromServer;
import after.login.component.body.simulation.details.single.simulation.tile.ResourceConstants;
import after.login.component.body.simulation.details.single.simulation.tile.rule.action.helper.ActionTileCreatorFactory;
import after.login.component.body.simulation.details.single.simulation.tile.termination.condition.TerminationConditionsController;
import after.login.component.body.simulation.details.single.simulation.tile.world.grid.WorldGridSizesController;
import after.login.component.body.simulation.details.single.simulation.tile.property.PropertyController;
import after.login.component.body.simulation.details.single.simulation.tile.rule.action.helper.ActionTileCreatorFactoryImpl;
import after.login.component.body.simulation.details.main.SimulationsDetailsController;
import dto.definition.entity.EntityDefinitionDTO;
import dto.definition.property.definition.PropertyDefinitionDTO;
import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.ProximityActionDTO;
import dto.definition.rule.action.ReplaceActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.condition.MultipleConditionActionDTO;
import dto.definition.rule.action.condition.SingleConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
import dto.definition.rule.action.numeric.calculation.DivideActionDTO;
import dto.definition.rule.action.numeric.calculation.MultiplyActionDTO;
import dto.definition.rule.RuleDTO;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.impl.TicksTerminationConditionsDTOImpl;
import dto.definition.termination.condition.impl.TimeTerminationConditionsDTOImpl;
import dto.definition.termination.condition.manager.TerminationConditionsDTOManager;
import dto.include.DTOIncludeSimulationDetailsForUi;
import dto.primary.DTODefinitionsForUi;
import dto.primary.DTOEnvVarsDefForUi;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingleSimulationController {

    private final TreeView<String> detailsTreeView;
    private final Label valueDefLabel;  //for population
    private final Label quantityOfSquaresLabel; //for quantity of properties
    private final TextFlow valueDefText;
    private final TextFlow quantityOfSquaresText;
    private final FlowPane detailsFlowPane;
    private final ScrollPane detailsScrollPane;
    private final RequestsFromServer requestsFromServer;
    public SingleSimulationController(SimulationsDetailsController simulationsDetailsController, RequestsFromServer requestsFromServer){
        detailsTreeView = simulationsDetailsController.getDetailsTreeView();
        valueDefLabel= simulationsDetailsController.getValueDefLabel();  //for population
        quantityOfSquaresLabel= simulationsDetailsController.getQuantityOfSquaresLabel(); //for quantity of properties
        valueDefText= simulationsDetailsController.getValueDefText();
        quantityOfSquaresText= simulationsDetailsController.getQuantityOfSquaresText();
        detailsFlowPane= simulationsDetailsController.getDetailsFlowPane();
        detailsScrollPane= simulationsDetailsController.getDetailsScrollPane();
        this.requestsFromServer = requestsFromServer;
    }
    public void primaryInitialize(String simulationName){
        quantityOfSquaresLabel.setVisible(false);
        quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);
        detailsFlowPane.getChildren().clear();

        DTOIncludeSimulationDetailsForUi simulationDetails = requestsFromServer.getSimulationDetailsFromServer(simulationName);

        TreeItem<String> rootItem = new TreeItem<>(simulationName);
        TreeItem<String> entitiesBranch = createEntitiesSubTree(simulationDetails);
        TreeItem<String> ruleBranch = createRulesSubTree(simulationDetails);
        TreeItem<String> terminationBranch = new TreeItem<>("Termination conditions");
        TreeItem<String> environmentBranch = new TreeItem<>("Environment variables");
        TreeItem<String> worldGridBranch = new TreeItem<>("World grid sizes");

        rootItem.getChildren().addAll(entitiesBranch, ruleBranch,terminationBranch,environmentBranch, worldGridBranch);
        detailsTreeView.getTreeItem(0).getChildren().add(rootItem);


    }

    public void handleSelectedItemChange(TreeItem<String> selectedItem, String simulationName) {
        detailsTreeView.setVisible(true);
        detailsScrollPane.setVisible(true);

        DTOIncludeSimulationDetailsForUi simulationDetails = requestsFromServer.getSimulationDetailsFromServer(simulationName);

        // Check if the selected item is a leaf (entity)
        if (selectedItem != null && selectedItem.isLeaf()) {
            // Check if "Entities" branch is expanded
            if (selectedItem.getParent().getValue().equals("Entities")) {
                handleSingleEntitySelection(selectedItem, simulationDetails);
            } else if (selectedItem.getParent().getValue().equals("Rules")) {
                handleSingleRuleSelection(selectedItem, simulationDetails);
            } else if (selectedItem.getValue().equals("Termination conditions")) {
                handleTerminationConditionsSelection(selectedItem, simulationDetails);
                //detailsTreeView.getRoot().setExpanded(false);
            } else if (selectedItem.getValue().equals("Environment variables")) {
                handleEnvironmentVariablesSelection(selectedItem, simulationDetails);
            }
            else if (selectedItem.getValue().equals("World grid sizes")) {
                handleWorldGridSizesSelection(simulationDetails);}
        }
    }

    private void handleWorldGridSizesSelection(DTOIncludeSimulationDetailsForUi simulationDetails) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ResourceConstants.WORLD_GRID_FXML_RESOURCE);
            Node singleNode = loader.load();
            Integer rows= simulationDetails.getWorldGridForUi().getGridRows();
            Integer columns = simulationDetails.getWorldGridForUi().getGridColumns();
            Integer maxPopulationQuantity = rows * columns;

            WorldGridSizesController worldGridSizesController = loader.getController();
            worldGridSizesController.setRowsLabel(rows.toString());
            worldGridSizesController.setColumnsLabel(columns.toString());
            worldGridSizesController.setPopulationLabel(maxPopulationQuantity.toString());

            detailsFlowPane.getChildren().clear();
            detailsFlowPane.getChildren().add(singleNode);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void handleSingleEntitySelection(TreeItem<String> entitySelectedItem, DTOIncludeSimulationDetailsForUi simulationDetails){
            valueDefText.getChildren().clear(); // Clear existing text
            quantityOfSquaresLabel.setVisible(false);
            quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);
        detailsFlowPane.getChildren().clear();
        if (entitySelectedItem != null) {
                String selectedValue = entitySelectedItem.getValue();
                DTODefinitionsForUi dtoDefinitionsForUi = simulationDetails.getDefinitions();
                List<EntityDefinitionDTO> entities = dtoDefinitionsForUi.getEntitiesDTO();
                for(EntityDefinitionDTO entityDefinitionDTO : entities){

                    if(entityDefinitionDTO.getUniqueName().equals(selectedValue)){
                        for(PropertyDefinitionDTO propertyDefinitionDTO:entityDefinitionDTO.getProps()){
                            createPropertyChildrenInFlowPane(propertyDefinitionDTO);
                        }

                    }
                }
            }
    }


    private void createPropertyChildrenInFlowPane(PropertyDefinitionDTO propertyDefinitionDTO){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ResourceConstants.PROPERTY_FXML_RESOURCE);
            Node singleProperty = loader.load();

            PropertyController propertyController = loader.getController();
            propertyController.setPropertyNameLabel(propertyDefinitionDTO.getUniqueName());
            propertyController.setPropertyTypeLabel(propertyDefinitionDTO.getType());
            propertyController.setPropertyRangeLabel((propertyDefinitionDTO.doesHaveRange() ?
                    propertyDefinitionDTO.getRange().get(0) + " to " + propertyDefinitionDTO.getRange().get(1) : "no range"));
            propertyController.setPropertyIsRandomLabel(String.valueOf(propertyDefinitionDTO.isRandomInitialized()));
            detailsFlowPane.getChildren().add(singleProperty);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private TreeItem<String> createEntitiesSubTree(DTOIncludeSimulationDetailsForUi simulationDetails){
        DTODefinitionsForUi dtoDefinitionsForUi = simulationDetails.getDefinitions();
        List<EntityDefinitionDTO> entities = dtoDefinitionsForUi.getEntitiesDTO();
        List<TreeItem<String>> entitiesBrunches = new ArrayList<>();

        for(EntityDefinitionDTO entityDefinitionDTO : entities){
            entitiesBrunches.add(createSingleEntitySubTree(entityDefinitionDTO));
        }

        TreeItem<String> entitiesBranch = new TreeItem<>("Entities");
        entitiesBranch.getChildren().addAll(entitiesBrunches);
        return entitiesBranch;
    }

    private TreeItem<String> createSingleEntitySubTree(EntityDefinitionDTO entityDefinitionDTO){
        TreeItem<String> entityBranch = new TreeItem<>( entityDefinitionDTO.getUniqueName());
        return entityBranch;
    }

    private TreeItem<String> createRulesSubTree(DTOIncludeSimulationDetailsForUi simulationDetails){
        DTODefinitionsForUi dtoDefinitionsForUi = simulationDetails.getDefinitions();
        List<RuleDTO> rules = dtoDefinitionsForUi.getRulesDTO();
        List<TreeItem<String>> rulesBrunches = new ArrayList<>();

        for(RuleDTO ruleDTO : rules){
            rulesBrunches.add(new TreeItem<>( ruleDTO.getName()));
        }

        TreeItem<String> rulesBrunch = new TreeItem<>("Rules");
        rulesBrunch.getChildren().addAll(rulesBrunches);
        return rulesBrunch;
    }


    private void handleSingleRuleSelection(TreeItem<String> ruleSelectedItem, DTOIncludeSimulationDetailsForUi simulationDetails) {
            valueDefText.getChildren().clear(); // Clear existing text
            quantityOfSquaresText.getChildren().clear(); // Clear existing text
        detailsFlowPane.getChildren().clear();
            if (ruleSelectedItem != null) {
                String selectedRuleName = ruleSelectedItem.getValue();
                DTODefinitionsForUi dtoDefinitionsForUi = simulationDetails.getDefinitions();
                List<RuleDTO> rules = dtoDefinitionsForUi.getRulesDTO();
                for (RuleDTO ruleDTO : rules) {

                    if (ruleDTO.getName().equals(selectedRuleName)) {
                        valueDefLabel.setVisible(true);
                        valueDefText.setVisible(true);
                        quantityOfSquaresLabel.setVisible(true);
                        quantityOfSquaresText.setVisible(true);
                        // RuleDTO rule = searchName(ruleName, rules);

                        valueDefLabel.setText("Activation:");
                        Text ruleActivation = new Text("active every " + ruleDTO.getActivation().getTicks() +
                                " ticks with probability of: " + ruleDTO.getActivation().getProbability());
                        valueDefText.getChildren().add(ruleActivation);
                        quantityOfSquaresLabel.setText("Actions number:");
                        Text actionNumber = new Text(((Integer) ruleDTO.getNumOfActions()).toString());
                        quantityOfSquaresText.getChildren().add(actionNumber);
                        createActionChildrenInFlowPane(ruleDTO);
                    }
                }
            }
    }


    private void createActionChildrenInFlowPane(RuleDTO rule){
        List <AbstractActionDTO> actionDTOS = rule.getActions();
        ActionTileCreatorFactory actionTileCreatorFactory = new ActionTileCreatorFactoryImpl();

        for(AbstractActionDTO action : actionDTOS){
            switch(action.getActionType()){
                case INCREASE:
                    IncreaseActionDTO increaseAction = (IncreaseActionDTO)action;
                    actionTileCreatorFactory.createIncreaseActionChildren(increaseAction, detailsFlowPane);
                    break;
                case DECREASE:
                    DecreaseActionDTO decreaseAction = (DecreaseActionDTO)action;
                    actionTileCreatorFactory.createDecreaseActionChildren(decreaseAction, detailsFlowPane);
                    break;
                case SET:
                    SetActionDTO setActionDTO = (SetActionDTO) action;
                    actionTileCreatorFactory.createSetActionChildren(setActionDTO, detailsFlowPane);
                    break;
                case CALCULATION:
                    if(action instanceof DivideActionDTO) {
                        DivideActionDTO divideAction = (DivideActionDTO) action;
                        actionTileCreatorFactory.createDivideActionChildren(divideAction, detailsFlowPane);
                    }
                    else{
                        MultiplyActionDTO multiplyAction = (MultiplyActionDTO) action;
                        actionTileCreatorFactory.createMultiplyActionChildren(multiplyAction, detailsFlowPane);
                    }
                    break;
                case CONDITION:
                    if(action instanceof SingleConditionActionDTO) {
                        SingleConditionActionDTO singleConditionActionDTO = (SingleConditionActionDTO) action;
                        actionTileCreatorFactory.createSingleConditionActionChildren(singleConditionActionDTO, detailsFlowPane);
                    }
                    else{
                        MultipleConditionActionDTO multipleConditionActionDTO = (MultipleConditionActionDTO) action;
                        actionTileCreatorFactory.createMultipleConditionActionChildren(multipleConditionActionDTO, detailsFlowPane);
                    }
                    break;
                case KILL:
                    KillActionDTO killAction = (KillActionDTO)action;
                    actionTileCreatorFactory.createKillActionChildren(killAction, detailsFlowPane);
                    break;
                case PROXIMITY:
                    ProximityActionDTO proximityActionDTO = (ProximityActionDTO) action;
                    actionTileCreatorFactory.createProximityActionChildren(proximityActionDTO, detailsFlowPane);
                    break;
                case REPLACE:
                    ReplaceActionDTO replaceActionDTO = (ReplaceActionDTO) action;
                    actionTileCreatorFactory.createReplaceActionChildren(replaceActionDTO, detailsFlowPane);
                    break;
            }
        }
    }

    private void handleEnvironmentVariablesSelection(TreeItem<String> environmentVariablesSelectedItem, DTOIncludeSimulationDetailsForUi simulationDetails){

            quantityOfSquaresLabel.setVisible(false);
            quantityOfSquaresText.setVisible(false);
            valueDefLabel.setVisible(false);
            valueDefText.setVisible(false);
            if (environmentVariablesSelectedItem != null) {
                DTOEnvVarsDefForUi dtoEnvVarsDefForUi = simulationDetails.getEnvVarsDef();
                List<PropertyDefinitionDTO> propertyDefinitionDTOS = dtoEnvVarsDefForUi.getEnvironmentVars();

                detailsFlowPane.getChildren().clear();
                //property list
                for (PropertyDefinitionDTO propertyDefinitionDTO : propertyDefinitionDTOS) {
                    createPropertyChildrenInFlowPane(propertyDefinitionDTO);
                }
            }

    }


    private void handleTerminationConditionsSelection(TreeItem<String> terminationConditionsSelectedItem, DTOIncludeSimulationDetailsForUi simulationDetails){
        quantityOfSquaresLabel.setVisible(false);
        quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);
        if (terminationConditionsSelectedItem != null) {
            DTODefinitionsForUi dtoDefinitionsForUi = simulationDetails.getDefinitions();
            TerminationConditionsDTOManager terminationConditionsDTOManager = dtoDefinitionsForUi.getTerminationConditionsDTOManager();
            List<TerminationConditionsDTO> terminationConditionsDTOList = terminationConditionsDTOManager.getTerminationConditionsDTOList();
            detailsFlowPane.getChildren().clear();
            createTerminationConditionsChildrenInFlowPane(terminationConditionsDTOList);
        }
    }

    private void createTerminationConditionsChildrenInFlowPane(List<TerminationConditionsDTO> terminationConditionsDTOList) {
        try{
            for(TerminationConditionsDTO terminationConditionsDTO : terminationConditionsDTOList) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ResourceConstants.TERMINATION_CONDITION_FXML_RESOURCE);
                Node terminationCondition = loader.load();
                TerminationConditionsController terminationConditionsController = loader.getController(); //Node terminationCondition = loader.load();
                if (terminationConditionsDTO instanceof TicksTerminationConditionsDTOImpl) {
                    terminationConditionsController.setTicksSecDefLabel("ticks:");
                    terminationConditionsController.setTicksSecValueLabel
                            (((Integer) terminationConditionsDTO.getTerminationCondition()).toString());
                } else if(terminationConditionsDTO instanceof TimeTerminationConditionsDTOImpl){
                    terminationConditionsController.setTicksSecDefLabel("seconds:");
                    terminationConditionsController.setTicksSecValueLabel
                            (((Integer) terminationConditionsDTO.getTerminationCondition()).toString());
                } else{
                    terminationConditionsController.setTicksSecDefLabel("byUser");
                    terminationConditionsController.setTicksSecValueLabel("");
                }
                detailsFlowPane.getChildren().add(terminationCondition);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

