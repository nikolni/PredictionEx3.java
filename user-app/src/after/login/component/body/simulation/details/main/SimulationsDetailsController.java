package after.login.component.body.simulation.details.main;

import after.login.component.body.simulation.details.single.simulation.main.SingleSimulationController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextFlow;

import java.util.List;

public class SimulationsDetailsController {
    @FXML
    private TreeView<String> detailsTreeView;

    @FXML
    private TextFlow valueDefText;

    @FXML
    private Label valueDefLabel;

    @FXML
    private Label quantityOfSquaresLabel;

    @FXML
    private TextFlow quantityOfSquaresText;

    @FXML
    private ScrollPane detailsScrollPane;

    @FXML
    private FlowPane detailsFlowPane;
    private List<SingleSimulationController> singleSimulationControllerList;

    @FXML
    public void initialize() {
        quantityOfSquaresLabel.setVisible(false);
        quantityOfSquaresText.setVisible(false);
        valueDefLabel.setVisible(false);
        valueDefText.setVisible(false);
        detailsTreeView.setVisible(false);
        detailsScrollPane.setVisible(false);
        TreeItem<String> rootItem = new TreeItem<>("Simulations");
        detailsTreeView.setRoot(rootItem);

        detailsTreeView.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> handleSelectedItemChange(newValue));
    }

    public void setVisibleTab(){
        detailsTreeView.setVisible(true);
        detailsFlowPane.setVisible(true);
        detailsScrollPane.setVisible(true);
    }
    public void addSimulationItemToTreeView(String simulationName, SystemEngineAccess systemEngineAccess){
        SingleSimulationController singleSimulationController = new SingleSimulationController(systemEngineAccess, this);
        singleSimulationController.primaryInitialize(simulationName);
        singleSimulationControllerList.add(singleSimulationController);
    }

    private void handleSelectedItemChange(TreeItem<String> selectedItem) {
        int count = 0;
        if (selectedItem != null) {
            for (TreeItem<String> item : detailsTreeView.getTreeItem(0).getChildren()) {
                if (selectedItem.getParent().getParent().getValue().equals(item.getValue())) {
                    singleSimulationControllerList.get(count).handleSelectedItemChange(selectedItem);
                    break;
                }
                count++;
            }
        }
    }
    public TreeView<String> getDetailsTreeView() {
        return detailsTreeView;
    }

    public TextFlow getValueDefText() {
        return valueDefText;
    }

    public Label getValueDefLabel() {
        return valueDefLabel;
    }

    public Label getQuantityOfSquaresLabel() {
        return quantityOfSquaresLabel;
    }

    public TextFlow getQuantityOfSquaresText() {
        return quantityOfSquaresText;
    }

    public ScrollPane getDetailsScrollPane() {
        return detailsScrollPane;
    }

    public FlowPane getDetailsFlowPane() {
        return detailsFlowPane;
    }

}
