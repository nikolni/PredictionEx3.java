package after.login.component.body.running.result;

import after.login.component.body.running.main.ProgressAndResultController;
import after.login.component.body.running.server.RequestsFromServer;
import dto.definition.entity.EntityDefinitionDTO;
import dto.definition.property.definition.PropertyDefinitionDTO;
import dto.include.DTOIncludeForResultsAdditionalForUi;
import dto.include.DTOIncludeForResultsPrimaryForUi;
import dto.primary.DTODefinitionsForUi;
import dto.primary.DTOEntitiesAfterSimulationByQuantityForUi;
import dto.primary.DTOPropertyHistogramForUi;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultsController {


    @FXML
    private ScrollPane entityTimeGraphPane;

    @FXML
    private TreeView<String> entityPropTreeView;

    @FXML
    private ComboBox<String> viewComboBox;

    @FXML
    private Label ConsistencyValueLabel;

    @FXML
    private Label PropertyAverageValueLabel;

    @FXML
    private ScrollPane histogramGraphPane;

    @FXML
    private Label ConsistencyLabel;

    @FXML
    private Label PropertyAverageLabel;
    private ProgressAndResultController progressAndResultController;
    private RequestsFromServer requestsFromServer ;
    private String userName;
    private DTOIncludeForResultsPrimaryForUi primaryResults;
    private DTOIncludeForResultsAdditionalForUi additionalResults;

    public void setMembers(ProgressAndResultController progressAndResultController, String userName,
                           RequestsFromServer requestsFromServer) {
        this.progressAndResultController = progressAndResultController;
        this.userName = userName;
        this.requestsFromServer = requestsFromServer;
    }

    public TreeView<String> getEntityPropTreeView() {
        return entityPropTreeView;
    }

    public void primaryInitialize() {

            viewComboBox.getItems().addAll("Histogram", "Consistency", "Property Average");
            viewComboBox.setVisible(false);
            ConsistencyValueLabel.setVisible(false);
            PropertyAverageValueLabel.setVisible(false);
            ConsistencyLabel.setVisible(false);
            PropertyAverageLabel.setVisible(false);
            histogramGraphPane.setVisible(false);
            entityPropTreeView.setVisible(false);
            entityTimeGraphPane.setVisible(false);
            viewComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                ConsistencyValueLabel.setVisible(false);
                ConsistencyLabel.setVisible(false);
                PropertyAverageValueLabel.setVisible(false);
                PropertyAverageLabel.setVisible(false);
                histogramGraphPane.setVisible(false);

                if (newValue != null) {
                    switch (newValue) {
                        case "Histogram":
                            histogramGraphPane.setVisible(true);
                            break;
                        case "Consistency":
                            ConsistencyValueLabel.setVisible(true);
                            ConsistencyLabel.setVisible(true);
                            break;
                        case "Property Average":
                            PropertyAverageValueLabel.setVisible(true);
                            PropertyAverageLabel.setVisible(true);
                            break;

                    }
                }
            });
    }

    public void handleSimulationSelection(int simulationID) {
        requestsFromServer.getPrimaryResults(userName, simulationID);
        requestsFromServer.setResultsPrimaryForUiConsumer(this::useResultsPrimary);

        entityTimeGraphPane.setContent(createEntitiesByTickGraph(primaryResults.getEntitiesAfterSimulationByQuantity()));
        entityTimeGraphPane.setVisible(true);

        TreeItem<String> rootItem = createEntitiesSubTree();
        entityPropTreeView.setRoot(rootItem);
        entityPropTreeView.setShowRoot(false);
        entityPropTreeView.setVisible(true);

        entityPropTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null && newValue.isLeaf())
                handleSelectedProperty(newValue,simulationID);
        });
    }
    private void useResultsPrimary(DTOIncludeForResultsPrimaryForUi primaryResultsConsumer){
        primaryResults = primaryResultsConsumer;
    }
    public LineChart<Number, Number> createEntitiesByTickGraph(DTOEntitiesAfterSimulationByQuantityForUi dtoEntitiesAfterSimulationByQuantity) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setTickUnit(1.0);
        yAxis.setTickUnit(1.0);

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        Map<Integer, Integer> entitiesData = dtoEntitiesAfterSimulationByQuantity.getEntitiesLeftByTicks();

        if (entitiesData.size() > 0) {
            if (entitiesData.size() > 1000) {
                int jump = 1000;
                for (int i = 0; i < entitiesData.size(); i += jump) {
                    int key = i;
                    int value = entitiesData.get(key);
                    series.getData().add(new XYChart.Data<>(key, value));
                }
            } else {
                for (Map.Entry<Integer, Integer> entry : entitiesData.entrySet()) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                }
            }
        }

        lineChart.getData().add(series);
        return lineChart;

        /*for (Map.Entry<Integer, Integer> entry : systemEngine.getEntitiesDataAfterSimulationRunningByQuantity(simulationID).getEntitiesLeftByTicks().entrySet())
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));*/
    }
    public TreeItem<String> createEntitiesSubTree(){
        List<String> entitiesNames = primaryResults.getEntitiesAfterSimulationByQuantity().getEntitiesNames();

        List<TreeItem<String>> entitiesBrunches = new ArrayList<>();
        for(String entityName : entitiesNames){
            entitiesBrunches.add(createSingleEntitySubTree(entityName,primaryResults.getDtoDefinitions()));
        }

        TreeItem<String> entitiesBranch = new TreeItem<>("Entities");
        entitiesBranch.getChildren().addAll(entitiesBrunches);
        return entitiesBranch;
    }
    public void handleSelectedProperty(TreeItem<String> selectedItem, int simulationID){
        viewComboBox.setVisible(true);
        viewComboBox.setPromptText("select option");

        requestsFromServer.getAdditionalResults(userName, simulationID, selectedItem.getParent().getValue(), selectedItem.getValue());
        requestsFromServer.setAdditionalResultsConsumer(this::useAdditionalResults);

        if(additionalResults.getDtoSimulationProgress().getEntitiesLeft().get(selectedItem.getParent().getValue())>0){
            if(primaryResults.getDtoDefinitions().getPropertyDefinitionByName(selectedItem.getParent().getValue(),
                    selectedItem.getValue()).getType().toLowerCase().equals("float")) {
                PropertyAverageValueLabel.setText(String.valueOf(calculatePropertyAverage(additionalResults.getDtoPropertyHistogram())));
            }else{
                PropertyAverageValueLabel.setText("Property's type is not numeric");
                PropertyAverageValueLabel.setWrapText(true);
            }

            BarChart<String, Number> histogram = createHistogram(additionalResults.getDtoPropertyHistogram());
            histogramGraphPane.setContent(histogram);
        }
        else{

            PropertyAverageValueLabel.setText("Entity's population is 0");
            PropertyAverageValueLabel.setWrapText(true);
            Label zeroPopulation=new Label("Entity's population is 0 - no data to show");
            zeroPopulation.setWrapText(true);
            histogramGraphPane.setContent(zeroPopulation);
        }
        ConsistencyValueLabel.setText(String.valueOf(additionalResults.getDtoEntityPropertyConsistency().getConsistency()));
    }
    private void useAdditionalResults(DTOIncludeForResultsAdditionalForUi additionalResultsConsumer){
        additionalResults = additionalResultsConsumer;
    }
    public BarChart<String, Number> createHistogram(DTOPropertyHistogramForUi dtoPropertyHistogramForUi) {

        // Create the X and Y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        yAxis.setTickUnit(1.0);

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("property value");
        yAxis.setLabel("quantity of entities");

        // Prepare data for the chart
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        Map< Object, Long> propertyHistogramMap = dtoPropertyHistogramForUi.getPropertyHistogram();

        for (Map.Entry<Object, Long> entry : propertyHistogramMap.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }

        barChart.getData().add(dataSeries);
        return barChart;
    }

    public float calculatePropertyAverage(DTOPropertyHistogramForUi dtoPropertyHistogramForUi){
        float sumOfProducts = 0.0f;
        float sumOfValues = 0.0f;
            for (Map.Entry<Object, Long> entry : dtoPropertyHistogramForUi.getPropertyHistogram().entrySet()) {
                float key = Float.parseFloat(entry.getKey().toString());
                float value = Float.parseFloat(entry.getValue().toString());
                float product = key * value;

                sumOfProducts += product;
                sumOfValues += value;
            }

            return (sumOfProducts / sumOfValues);

    }

    public TreeItem<String> createSingleEntitySubTree(String entityName,DTODefinitionsForUi dtoDefinitionsForUi) {

        TreeItem<String> entityBranch = new TreeItem<>(entityName);
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
