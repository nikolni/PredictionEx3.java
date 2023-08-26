package screen1.body;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.condition.ConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
import dto.definition.rule.action.numeric.calculation.DivideActionDTO;
import dto.definition.rule.action.numeric.calculation.MultiplyActionDTO;
import dto.definition.rule.api.RuleDTO;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import screen1.body.tile.property.PropertyController;
import screen1.body.tile.property.PropertyResourceConstants;
import screen1.body.tile.rule.action.helper.ActionTileCreatorFactory;
import screen1.body.tile.rule.action.helper.ActionTileCreatorFactoryImpl;
import system.engine.api.SystemEngineAccess;
import system.engine.impl.SystemEngineAccessImpl;
import dto.api.*;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BodyController implements Initializable{
    @FXML
    private TreeView<String> detailesTreeView;
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

    private SystemEngineAccess systemEngine = new SystemEngineAccessImpl();

    public BodyController(){
        try {
            systemEngine.getXMLFromUser("C:/Users/maaya/javaProjects/predictionEx2/ex1-cigarets.xml");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        quantityOfSquaresLabel.setVisible(false);
        quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);

        TreeItem<String> rootItem = new TreeItem<>("World");
        TreeItem<String> entitiesBranch = createEntitiesSubTree(systemEngine);
        TreeItem<String> ruleBranch = createRulesSubTree(systemEngine);
        TreeItem<String> terminationBranch = new TreeItem<>("Termination conditions");
        TreeItem<String> environmentBranch = new TreeItem<>("Environment Variables");

        rootItem.getChildren().addAll(entitiesBranch, ruleBranch,terminationBranch,environmentBranch);
        //detailesTreeView.setShowRoot(false);
        detailesTreeView.setRoot(rootItem);
    }

    private boolean isBranchExpanded(String branchValue,TreeView<String> detailesTreeView) {
        if(branchValue.equals("World"))
            return true;
        TreeItem<String> root = detailesTreeView.getRoot();
        for(TreeItem<String> treeItem:root.getChildren()){
            if (treeItem.getValue().equals(branchValue))
                return treeItem.isExpanded();
        }
        return false;
    }

    @FXML
    void selectItem(MouseEvent event) {
        detailesTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isLeaf()) {
                quantityOfSquaresLabel.setVisible(false);
                quantityOfSquaresText.setVisible(false);
                valueDefLabel.setVisible(false);
                valueDefText.setVisible(false);
                if(isBranchExpanded("World",detailesTreeView)){
                    if(isBranchExpanded("Entities",detailesTreeView)){
                        handleEntitySelection();
                        //detailesTreeView.getRoot().setExpanded(false);
                    }
                    else if(isBranchExpanded("Rules",detailesTreeView)){
                        handleRulesSelection();
                        //detailesTreeView.getRoot().setExpanded(false);
                    }

                }
            }
        });
    }

    public void handleEntitySelection(){
        detailesTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueDefText.getChildren().clear(); // Clear existing text
            quantityOfSquaresLabel.setVisible(false);
            quantityOfSquaresText.setVisible(false);
            if (newValue != null) {
                String selectedValue = newValue.getValue();
                DTODefinitionsForUi dtoDefinitionsForUi = systemEngine.getDefinitionsDataFromSE();
                List<EntityDefinitionDTO> entities = dtoDefinitionsForUi.getEntitiesDTO();
                for(EntityDefinitionDTO entityDefinitionDTO : entities){
                    detailsFlowPane.getChildren().clear();
                    if(entityDefinitionDTO.getUniqueName().equals(selectedValue)){
                        valueDefLabel.setVisible(true);
                        valueDefText.setVisible(true);
                        //population
                        valueDefLabel.setText("entity's population:");
                        Text entityPopulation = new Text(String.valueOf(entityDefinitionDTO.getPopulation()));
                        valueDefText.getChildren().add(entityPopulation);
                        //property list
                        for(PropertyDefinitionDTO propertyDefinitionDTO:entityDefinitionDTO.getProps()){
                            createPropertyChildrenInFlowPane(propertyDefinitionDTO);
                        }

                    }
                }
            }
        });
        //detailesTreeView.getRoot().setExpanded(false);
    }

    public void createPropertyChildrenInFlowPane(PropertyDefinitionDTO propertyDefinitionDTO){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PropertyResourceConstants.PROPERTY_FXML_RESOURCE);
            Node singleProperty = loader.load();

            PropertyController propertyController = loader.getController();
            propertyController.setPropertyNameLabel(propertyDefinitionDTO.getUniqueName());
            propertyController.setPropertyTypeLabel(propertyDefinitionDTO.getType());
            propertyController.setPropertyRangeLabel((propertyDefinitionDTO.doesHaveRange() ? " range: from " +
                    propertyDefinitionDTO.getRange().get(0) + " to " + propertyDefinitionDTO.getRange().get(1) : " no range"));
            propertyController.setPropertyIsRandomLabel(String.valueOf(propertyDefinitionDTO.isRandomInitialized()));
            detailsFlowPane.getChildren().add(singleProperty);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public TreeItem<String> createEntitiesSubTree(SystemEngineAccess systemEngineAccess){
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

    public TreeItem<String> createSingleEntitySubTree(EntityDefinitionDTO entityDefinitionDTO){
        TreeItem<String> entityBranch = new TreeItem<>( entityDefinitionDTO.getUniqueName());

       /* TreeItem<String> leafPopulation = new TreeItem<>("population");

        TreeItem<String> propertiesBranch = createPropertiesSubTree(entityDefinitionDTO.getProps());
        entityBranch.getChildren().addAll(leafPopulation, propertiesBranch);*/
        return entityBranch;
    }

    public TreeItem<String> createRulesSubTree(SystemEngineAccess systemEngineAccess){
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


    private void handleRulesSelection() {
        detailesTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueDefText.getChildren().clear(); // Clear existing text
            quantityOfSquaresText.getChildren().clear(); // Clear existing text
            //detailsFlowPane.getChildren().clear();
            if (newValue != null) {
                String selectedRuleName = newValue.getValue();
                DTODefinitionsForUi dtoDefinitionsForUi = systemEngine.getDefinitionsDataFromSE();
                List<RuleDTO> rules = dtoDefinitionsForUi.getRulesDTO();
                for (RuleDTO ruleDTO : rules) {
                    detailsFlowPane.getChildren().clear();
                    if (ruleDTO.getName().equals(selectedRuleName)) {
                        valueDefLabel.setVisible(true);
                        valueDefText.setVisible(true);
                        quantityOfSquaresLabel.setVisible(true);
                        quantityOfSquaresText.setVisible(true);
                        // RuleDTO rule = searchName(ruleName, rules);

                        valueDefLabel.setText("Activation:");
                        Text ruleActivation = new Text("active every " + ruleDTO.getActivation().getTicks() +
                                "ticks with probability of: " + ruleDTO.getActivation().getProbability());
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
        });
        //detailesTreeView.getRoot().setExpanded(false);
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
                    ConditionActionDTO conditionAction = (ConditionActionDTO) action;
                    actionTileCreatorFactory.createConditionActionChildren(conditionAction, detailsFlowPane);
                    break;
                case KILL:
                    KillActionDTO killAction = (KillActionDTO)action;
                    actionTileCreatorFactory.createKillActionChildren(killAction, detailsFlowPane);
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



    public void showEnvironmentVarsDetailes(){
        quantityOfSquaresLabel.setVisible(false);
        quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);
    }
    public void createTeminationConditionsSubTree(TreeItem<String> branchItem1, SystemEngineAccess systemEngineAccess){
        DTODefinitionsForUi dtoDefinitionsForUi = systemEngineAccess.getDefinitionsDataFromSE();
        TerminationConditionsDTOManager terminationConditionsDTOManager = dtoDefinitionsForUi.getTerminationConditionsDTOManager();
    }
}

