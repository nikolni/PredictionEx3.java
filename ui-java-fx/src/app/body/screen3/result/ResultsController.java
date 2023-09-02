package app.body.screen3.result;

import app.body.screen3.Body3Controller;
import dto.api.DTODefinitionsForUi;
import dto.api.DTOEntitiesAfterSimulationByQuantityForUi;
import dto.api.DTOPropertyHistogramForUi;
import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import system.engine.api.SystemEngineAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultsController {

    @FXML
    private Pane entityTimeGraphPane;

    @FXML
    private TreeView<String> entityPropTreeView;

    @FXML
    private ComboBox<String> viewComboBox;

    @FXML
    private Label ConsistencyValueLabel;

    @FXML
    private Label PropertyAverageValueLabel;

    @FXML
    private Pane histogramGraphPane;

    private Body3Controller body3Controller;

    public void setBody3Controller(Body3Controller body3Controller) {
        this.body3Controller = body3Controller;
    }

    @FXML
    public void initialize() {

    }

    public BarChart<String, Number> createHistogram(TreeItem<String> selectedItem,SystemEngineAccess systemEngine,int simulationID) {

        // Create the X and Y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("property value");
        yAxis.setLabel("quantity of entities");

        // Prepare data for the chart
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        DTOPropertyHistogramForUi dtoPropertyHistogramForUi = systemEngine.getPropertyDataAfterSimulationRunningByHistogramByNames(simulationID,selectedItem.getParent().getValue(),selectedItem.getValue());
        Map< Object, Long> propertyHistogramMap = dtoPropertyHistogramForUi.getPropertyHistogram();

        for (Map.Entry<Object, Long> entry : propertyHistogramMap.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }

        barChart.getData().add(dataSeries);
        return barChart;
    }


    public void handleSimulationSelection(int simulationID,SystemEngineAccess systemEngine) {
        TreeItem<String> rootItem = createEntitiesSubTree(simulationID, systemEngine);
        entityPropTreeView.setRoot(rootItem);
        entityPropTreeView.setShowRoot(false);
        entityPropTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
                handleSelectedItemChange(newValue,systemEngine,simulationID);
        });
    }

    public void handleSelectedItemChange(TreeItem<String> selectedItem,SystemEngineAccess systemEngine,int simulationID){
        BarChart<String, Number> histogram = createHistogram(selectedItem,systemEngine,simulationID);
        histogramGraphPane.getChildren().add(histogram);

    }

        public TreeItem<String> createEntitiesSubTree(int simulationID, SystemEngineAccess systemEngine){
            DTOEntitiesAfterSimulationByQuantityForUi entitiesAfterSimulationForUi= systemEngine.getEntitiesDataAfterSimulationRunningByQuantity(simulationID);
            List<String> entitiesNames = entitiesAfterSimulationForUi.getEntitiesNames();

            List<TreeItem<String>> entitiesBrunches = new ArrayList<>();
            for(String entityName : entitiesNames){
                entitiesBrunches.add(createSingleEntitySubTree(entityName,systemEngine));
            }

            TreeItem<String> entitiesBranch = new TreeItem<>("Entities");
            entitiesBranch.getChildren().addAll(entitiesBrunches);
            return entitiesBranch;
        }

    public TreeItem<String> createSingleEntitySubTree(String entityName,SystemEngineAccess systemEngine) {

        TreeItem<String> entityBranch = new TreeItem<>(entityName);
        DTODefinitionsForUi dtoDefinitionsForUi = systemEngine.getDefinitionsDataFromSE();
        List<EntityDefinitionDTO> entities = dtoDefinitionsForUi.getEntitiesDTO();
        for (EntityDefinitionDTO entityDefinitionDTO : entities) {
            if (entityDefinitionDTO.getUniqueName().equals(entityName)) {
                for (PropertyDefinitionDTO propertyDefinitionDTO : entityDefinitionDTO.getProps()) {
                    entityBranch.getChildren().add(new TreeItem<>(propertyDefinitionDTO.getUniqueName()));
                }
            }
        }
        return entityBranch;
    }

}
