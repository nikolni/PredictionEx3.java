package app.body.screen3.result;

import app.body.screen3.main.Body3Controller;
import dto.api.DTODefinitionsForUi;
import dto.api.DTOEntitiesAfterSimulationByQuantityForUi;
import dto.api.DTOPropertyHistogramForUi;
import dto.api.DTOSimulationEndingForUi;
import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
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
    private SystemEngineAccess systemEngine;

    public void setBody3Controller(Body3Controller body3Controller) {
        this.body3Controller = body3Controller;
    }

    @FXML
    public void initialize() {

    }

    public TreeView<String> getEntityPropTreeView() {
        return entityPropTreeView;
    }

    public void primaryInitialize(DTOSimulationEndingForUi dtoSimulationEndingForUi, SystemEngineAccess systemEngine) {
        Integer simulationID = dtoSimulationEndingForUi.getSimulationID();

            //fillEntityInfoGridPane(simulationID, systemEngine);
            viewComboBox.getItems().addAll("Histogram", "Consistency", "Property Average");
            viewComboBox.setVisible(false);
            ConsistencyValueLabel.setVisible(false);
            PropertyAverageValueLabel.setVisible(false);
            histogramGraphPane.setVisible(false);
            entityPropTreeView.setVisible(true);
            //handleSimulationSelection(simulationID,systemEngine);
    }

    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
    }

    /*public void fillEntityInfoGridPane(int simulationID, SystemEngineAccess systemEngine){
        DTOEntitiesAfterSimulationByQuantityForUi entitiesAfterSimulationForUi= systemEngine.getEntitiesDataAfterSimulationRunningByQuantity(simulationID);
        List<String> entitiesNames = entitiesAfterSimulationForUi.getEntitiesNames();
        List<Integer> entitiesPopulationAfterSimulation =entitiesAfterSimulationForUi.getEntitiesPopulationAfterSimulation();

        for (int i = 0; i < entitiesNames.size(); i++) {
            String entityName = entitiesNames.get(i);
            Integer population = entitiesPopulationAfterSimulation.get(i);

            Label nameLabel = new Label(entityName);
            Label populationLabel = new Label(Integer.toString(population));

        }

    }*/
    @FXML
    void comboBoxSelected(ActionEvent event) {
        String comboBoxsChoice = viewComboBox.getValue();
       /* ConsistencyValueLabel.setVisible(false);
        PropertyAverageValueLabel.setVisible(false);
        histogramGraphPane.setVisible(false);*/
        if ("Histogram".equals(comboBoxsChoice)) {
            ConsistencyValueLabel.setVisible(false);
            PropertyAverageValueLabel.setVisible(false);
            histogramGraphPane.setVisible(true);
        } else if ("Consistency".equals(comboBoxsChoice)) {
            PropertyAverageValueLabel.setVisible(false);
            histogramGraphPane.setVisible(false);
            ConsistencyValueLabel.setVisible(true);
        } else if ("Property Average".equals(comboBoxsChoice)) {
            ConsistencyValueLabel.setVisible(false);
            histogramGraphPane.setVisible(false);
            PropertyAverageValueLabel.setVisible(true);
        }

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

    public float calculatePropertyAverage(Integer simulationID,String entityName,String propertyName){
        DTOPropertyHistogramForUi dtoPropertyHistogramForUi;
        float sumOfProducts = 0.0f;
        float sumOfValues = 0.0f;
            dtoPropertyHistogramForUi=systemEngine.getPropertyDataAfterSimulationRunningByHistogramByNames(simulationID,entityName,propertyName);
            for (Map.Entry<Object, Long> entry : dtoPropertyHistogramForUi.getPropertyHistogram().entrySet()) {
                float key = (float)(entry.getKey());
                float value = (float)(entry.getValue());
                float product = key * value;

                sumOfProducts += product;
                sumOfValues += value;
            }

            return (sumOfProducts / sumOfValues);

    }


    public void handleSimulationSelection(int simulationID,SystemEngineAccess systemEngine) {

        TreeItem<String> rootItem = createEntitiesSubTree(simulationID, systemEngine);
        entityPropTreeView.setRoot(rootItem);
        entityPropTreeView.setShowRoot(false);
        //entityPropTreeView.setVisible(true);
        entityPropTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
                handleSelectedItemChange(newValue,systemEngine,simulationID);
        });
    }



    public void handleSelectedItemChange(TreeItem<String> selectedItem,SystemEngineAccess systemEngine,int simulationID){
        //entityPropTreeView.setVisible(true);
        viewComboBox.setVisible(true);

        BarChart<String, Number> histogram = createHistogram(selectedItem,systemEngine,simulationID);
        histogramGraphPane.getChildren().add(histogram);

        if(systemEngine.getDefinitionsDataFromSE().getPropertyDefinitionByName(selectedItem.getParent().getValue(),selectedItem.getValue()).getType().toLowerCase().equals("float"))
            PropertyAverageValueLabel.setText(String.valueOf(calculatePropertyAverage(simulationID,selectedItem.getParent().getValue(),selectedItem.getValue())));



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
