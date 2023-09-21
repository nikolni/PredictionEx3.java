package app.body.screen1.main;

import app.body.screen1.tile.world.grid.WorldGridSizesController;
import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.ProximityActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.condition.MultipleConditionActionDTO;
import dto.definition.rule.action.condition.SingleConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
import dto.definition.rule.action.ReplaceActionDTO;
import dto.definition.rule.action.numeric.calculation.DivideActionDTO;
import dto.definition.rule.action.numeric.calculation.MultiplyActionDTO;
import dto.definition.rule.api.RuleDTO;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.impl.TicksTerminationConditionsDTOImpl;
import dto.definition.termination.condition.impl.TimeTerminationConditionsDTOImpl;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import app.body.screen1.tile.property.PropertyController;
import app.body.screen1.tile.ResourceConstants;
import app.body.screen1.tile.rule.action.helper.ActionTileCreatorFactory;
import app.body.screen1.tile.rule.action.helper.ActionTileCreatorFactoryImpl;
import app.body.screen1.tile.termination.condition.TerminationConditionsController;
import app.body.screen1.tile.termination.condition.TerminationConditionsResourceConstants;
import system.engine.api.SystemEngineAccess;
import dto.primary.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Body1Controller{
    @FXML
    private TreeView<String> detailsTreeView;
    @FXML
    private Label valueDefLabel;  //for population
    @FXML
    private Label quantityOfSquaresLabel; //for quantity of properties
    @FXML
    private TextFlow valueDefText;

    @FXML
    private TextFlow quantityOfSquaresText;
    @FXML
    private FlowPane detailsFlowPane;
    @FXML
    private ScrollPane detailsScrollPane;

    private SystemEngineAccess systemEngine;

    @FXML
    public void initialize() {
        quantityOfSquaresLabel.setVisible(false);
        quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);
        detailsTreeView.setVisible(false);
        detailsScrollPane.setVisible(false);
    }

    public void setVisibleTab(){
        detailsTreeView.setVisible(true);
        detailsFlowPane.setVisible(true);
        detailsScrollPane.setVisible(true);
    }

    public void setUnVisibleTab(){
        detailsTreeView.setVisible(false);
        detailsFlowPane.setVisible(false);
        detailsScrollPane.setVisible(false);
    }

    public void primaryInitialize(){
        quantityOfSquaresLabel.setVisible(false);
        quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);
        detailsFlowPane.getChildren().clear();
        TreeItem<String> rootItem = new TreeItem<>("World");
        TreeItem<String> entitiesBranch = createEntitiesSubTree(systemEngine);
        TreeItem<String> ruleBranch = createRulesSubTree(systemEngine);
        TreeItem<String> terminationBranch = new TreeItem<>("Termination conditions");
        TreeItem<String> environmentBranch = new TreeItem<>("Environment variables");
        TreeItem<String> worldGridBranch = new TreeItem<>("World grid sizes");

        rootItem.getChildren().addAll(entitiesBranch, ruleBranch,terminationBranch,environmentBranch, worldGridBranch);
        detailsTreeView.setRoot(rootItem);

        detailsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleSelectedItemChange(newValue);
        });
    }

    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
    }

    private void handleSelectedItemChange(TreeItem<String> selectedItem) {
        detailsTreeView.setVisible(true);
        detailsScrollPane.setVisible(true);

        // Check if the selected item is a leaf (entity)
        if (selectedItem != null && selectedItem.isLeaf()) {
            // Check if "Entities" branch is expanded
            if (selectedItem.getParent().getValue().equals("Entities")) {
                handleSingleEntitySelection(selectedItem);
            } else if (selectedItem.getParent().getValue().equals("Rules")) {
                handleSingleRuleSelection(selectedItem);
            } else if (selectedItem.getValue().equals("Termination conditions")) {
                handleTerminationConditionsSelection(selectedItem);
                //detailsTreeView.getRoot().setExpanded(false);
            } else if (selectedItem.getValue().equals("Environment variables")) {
                handleEnvironmentVariablesSelection(selectedItem);
            }
            else if (selectedItem.getValue().equals("World grid sizes")) {
                handleWorldGridSizesSelection(selectedItem);}
        }
    }

    private void handleWorldGridSizesSelection(TreeItem<String> selectedItem) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ResourceConstants.WORLD_GRID_FXML_RESOURCE);
            Node singleNode = loader.load();
            Integer rows= systemEngine.getDTOWorldGridForUi().getGridRows();
            Integer columns = systemEngine.getDTOWorldGridForUi().getGridColumns();
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


    private void handleSingleEntitySelection(TreeItem<String> entitySelectedItem){
            valueDefText.getChildren().clear(); // Clear existing text
            quantityOfSquaresLabel.setVisible(false);
            quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);
        detailsFlowPane.getChildren().clear();
        if (entitySelectedItem != null) {
                String selectedValue = entitySelectedItem.getValue();
                DTODefinitionsForUi dtoDefinitionsForUi = systemEngine.getDefinitionsDataFromSE();
                List<EntityDefinitionDTO> entities = dtoDefinitionsForUi.getEntitiesDTO();
                for(EntityDefinitionDTO entityDefinitionDTO : entities){

                    if(entityDefinitionDTO.getUniqueName().equals(selectedValue)){
                        //valueDefLabel.setVisible(true);
                        //valueDefText.setVisible(true);
                        //population
                        //valueDefLabel.setText("entity's population:");
                        //Text entityPopulation = new Text(String.valueOf(entityDefinitionDTO.getPopulation()));
                       // valueDefText.getChildren().add(entityPopulation);
                        //property list
                        for(PropertyDefinitionDTO propertyDefinitionDTO:entityDefinitionDTO.getProps()){
                            createPropertyChildrenInFlowPane(propertyDefinitionDTO);
                        }

                    }
                }
            }

        //detailsTreeView.getRoot().setExpanded(false);
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


    private TreeItem<String> createEntitiesSubTree(SystemEngineAccess systemEngineAccess){
        DTODefinitionsForUi dtoDefinitionsForUi = systemEngineAccess.getDefinitionsDataFromSE();
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

       /* TreeItem<String> leafPopulation = new TreeItem<>("population");

        TreeItem<String> propertiesBranch = createPropertiesSubTree(entityDefinitionDTO.getProps());
        entityBranch.getChildren().addAll(leafPopulation, propertiesBranch);*/
        return entityBranch;
    }

    private TreeItem<String> createRulesSubTree(SystemEngineAccess systemEngineAccess){
        DTODefinitionsForUi dtoDefinitionsForUi = systemEngineAccess.getDefinitionsDataFromSE();
        List<RuleDTO> rules = dtoDefinitionsForUi.getRulesDTO();
        List<TreeItem<String>> rulesBrunches = new ArrayList<>();

        for(RuleDTO ruleDTO : rules){
            rulesBrunches.add(new TreeItem<>( ruleDTO.getName()));
        }

        TreeItem<String> rulesBrunch = new TreeItem<>("Rules");
        rulesBrunch.getChildren().addAll(rulesBrunches);
        return rulesBrunch;
    }


    private void handleSingleRuleSelection(TreeItem<String> ruleSelectedItem) {
            valueDefText.getChildren().clear(); // Clear existing text
            quantityOfSquaresText.getChildren().clear(); // Clear existing text
        detailsFlowPane.getChildren().clear();
            if (ruleSelectedItem != null) {
                String selectedRuleName = ruleSelectedItem.getValue();
                DTODefinitionsForUi dtoDefinitionsForUi = systemEngine.getDefinitionsDataFromSE();
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
                        /*for(AbstractActionDTO abstractActionDTO:ruleDTO.getActions()){
                            createPropertyChildrenInFlowPane(propertyDefinitionDTO);
                        }*/
                        createActionChildrenInFlowPane(ruleDTO);
                    }
                }
            }

        //detailsTreeView.getRoot().setExpanded(false);
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



    public RuleDTO searchName(String searchName, List<RuleDTO> rules) {
        for (RuleDTO rule : rules) {
            if (searchName.equalsIgnoreCase(rule.getName())) {
                return rule;
            }
        }
        return null;
    }



    private void handleEnvironmentVariablesSelection(TreeItem<String> environmentVariablesSelectedItem){

            quantityOfSquaresLabel.setVisible(false);
            quantityOfSquaresText.setVisible(false);
            valueDefLabel.setVisible(false);
            valueDefText.setVisible(false);
            if (environmentVariablesSelectedItem != null) {
                String selectedValue = environmentVariablesSelectedItem.getValue();
                DTOEnvVarsDefForUi dtoEnvVarsDefForUi = systemEngine.getEVDFromSE();
                List<PropertyDefinitionDTO> propertyDefinitionDTOS = dtoEnvVarsDefForUi.getEnvironmentVars();

                detailsFlowPane.getChildren().clear();
                //property list
                for (PropertyDefinitionDTO propertyDefinitionDTO : propertyDefinitionDTOS) {
                    createPropertyChildrenInFlowPane(propertyDefinitionDTO);
                }
            }

    }


    private void handleTerminationConditionsSelection(TreeItem<String> terminationConditionsSelectedItem){

            quantityOfSquaresLabel.setVisible(false);
            quantityOfSquaresText.setVisible(false);
            valueDefLabel.setVisible(false);
            valueDefText.setVisible(false);
            if (terminationConditionsSelectedItem != null) {
                String selectedValue = terminationConditionsSelectedItem.getValue();
                DTODefinitionsForUi dtoDefinitionsForUi = systemEngine.getDefinitionsDataFromSE();
                TerminationConditionsDTOManager terminationConditionsDTOManager = dtoDefinitionsForUi.getTerminationConditionsDTOManager();
                List<TerminationConditionsDTO> terminationConditionsDTOList = terminationConditionsDTOManager.getTerminationConditionsDTOList();
                detailsFlowPane.getChildren().clear();
                createTerminationConditionsChildrenInFlowPane(terminationConditionsDTOList);
            }

        DTODefinitionsForUi dtoDefinitionsForUi = systemEngine.getDefinitionsDataFromSE();
        TerminationConditionsDTOManager terminationConditionsDTOManager = dtoDefinitionsForUi.getTerminationConditionsDTOManager();
    }

    private void createTerminationConditionsChildrenInFlowPane(List<TerminationConditionsDTO> terminationConditionsDTOList) {
        try{
            for(TerminationConditionsDTO terminationConditionsDTO : terminationConditionsDTOList) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(TerminationConditionsResourceConstants.TERMINATION_CONDITION_FXML_RESOURCE);
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

