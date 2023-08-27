package app.header;

import app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

public class HeaderController {


    @FXML
    private GridPane headerComponent;

    @FXML
    private Button loadFileButton;
    @FXML
    private Button queueManaementButton;
    @FXML
    private Button detailsButton;
    @FXML
    private Button newExecutionButton;
    @FXML
    private Button resultButtonClick;

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }



    @FXML
    void onDetailsButtonClick(ActionEvent event) {
        mainController.onDetailsButtonClick();
    }

    @FXML
    void onLoadFileButtonClick(ActionEvent event) {

    }

    @FXML
    void onNewExecutionButtonClick(ActionEvent event) {

    }

    @FXML
    void onQueueManagementButtonClick(ActionEvent event) {

    }

    @FXML
    void onResultButtonClick(ActionEvent event) {

    }

}
