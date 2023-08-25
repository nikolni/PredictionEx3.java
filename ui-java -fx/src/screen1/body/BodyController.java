package screen1.body;

import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.rule.api.RuleDTO;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.text.TextFlow;
import system.engine.api.SystemEngineAccess;
import system.engine.impl.SystemEngineAccessImpl;
import dto.api.*;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BodyController implements Initializable{
    @FXML
    private TreeView<String> detailesTreeView;
    @FXML
    private Label valueDefLabel;

    @FXML
    private Label quantityOfSquaresLabel;

    @FXML
    private TextFlow valueDefText;

    @FXML
    private TextFlow quantityOfSquaresText;

    SystemEngineAccess systemEngine = new SystemEngineAccessImpl();

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
        TreeItem<String> rootItem = new TreeItem<>("World");

        TreeItem<String> entitiesBranch = createEntitiesSubTree(systemEngine);
        TreeItem<String> ruleBranch = new TreeItem<>("Rules");
        TreeItem<String> terminationBranch = new TreeItem<>("Termination conditions");

        rootItem.getChildren().addAll(entitiesBranch, ruleBranch,terminationBranch);
        detailesTreeView.setShowRoot(false);
        detailesTreeView.setRoot(rootItem);
    }
    @FXML
    void selectItem(ContextMenuEvent event) {
       /* TreeItem<String> item = detailesTreeView.getSelectionModel().getSelectedItem();

        if(item != null) {
            System.out.println(item.getValue());
        }*/
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

        TreeItem<String> leafPopulation = new TreeItem<>("population");

        TreeItem<String> propertiesBranch = createPropertiesSubTree(entityDefinitionDTO.getProps());
        entityBranch.getChildren().addAll(leafPopulation, propertiesBranch);
        return entityBranch;
    }

    public TreeItem<String> createPropertiesSubTree(List<PropertyDefinitionDTO> properties){
        List<TreeItem<String>> propertiesBrunches = new ArrayList<>();

        for(PropertyDefinitionDTO propertyDefinitionDTO : properties){
            propertiesBrunches.add(createSinglePropertySubTree(propertyDefinitionDTO));
        }
        TreeItem<String> propertiesBranch = new TreeItem<>("properties");
        propertiesBranch.getChildren().addAll(propertiesBrunches);
        return propertiesBranch;
    }

    public TreeItem<String> createSinglePropertySubTree(PropertyDefinitionDTO propertyDefinitionDTO){
        TreeItem<String> leafName = new TreeItem<>("type: " + propertyDefinitionDTO.getType());

        TreeItem<String> leafRange = new TreeItem<>((propertyDefinitionDTO.doesHaveRange() ? " range: from " +
                propertyDefinitionDTO.getRange().get(0) + " to " + propertyDefinitionDTO.getRange().get(1) : " no range"));
        TreeItem<String> leafRandomInitialize = new TreeItem<>("random initialize: " +propertyDefinitionDTO.isRandomInitialized().toString());

        TreeItem<String> propertyBranch = new TreeItem<>("name: " + propertyDefinitionDTO.getUniqueName());
        propertyBranch.getChildren().addAll(leafName, leafRange, leafRandomInitialize);
        return propertyBranch;
    }

    public void createRulesSubTree(TreeItem<String> branchItem1, SystemEngineAccess systemEngineAccess){
        DTODefinitionsForUi dtoDefinitionsForUi = systemEngineAccess.getDefinitionsDataFromSE();
        List<RuleDTO> rules = dtoDefinitionsForUi.getRulesDTO();
    }

    public void createTeminationConditionsSubTree(TreeItem<String> branchItem1, SystemEngineAccess systemEngineAccess){
        DTODefinitionsForUi dtoDefinitionsForUi = systemEngineAccess.getDefinitionsDataFromSE();
        TerminationConditionsDTOManager terminationConditionsDTOManager = dtoDefinitionsForUi.getTerminationConditionsDTOManager();
    }
}

