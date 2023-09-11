package app.header;

import app.main.AppController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import system.engine.api.SystemEngineAccess;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class HeaderController {


    @FXML
    private GridPane headerComponent;

    @FXML
    private Button loadFileButton;


    /*@FXML
    private StackPane queueManagementStackPane;*/
    @FXML
    private Button queueManagementButton;
    @FXML
    private Label waitingLabel;
    @FXML
    private Label overLabel;
    @FXML
    private Label currentlyExecutingLabel;
    @FXML
    private Label valueCurrentlyExecutingLabel;
    @FXML
    private Label valueWaitingLabel;
    @FXML
    private GridPane queueManagementGridPane;


    @FXML
    private Label valueOverLabel;
    @FXML
    private Button detailsButton;
    @FXML
    private Button newExecutionButton;
    @FXML
    private Button resultButtonClick;
    @FXML
    private Label selectedFileNameLabel;

    private SystemEngineAccess systemEngine;
    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private AppController mainController;
    private Stage primaryStage;


    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public HeaderController() {
        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        selectedFileNameLabel.textProperty().bind(selectedFileProperty);
        detailsButton.disableProperty().bind(isFileSelected.not());
        newExecutionButton.disableProperty().bind(isFileSelected.not());
        resultButtonClick.disableProperty().bind(isFileSelected.not());
    }



    @FXML
    void onLoadFileButtonClick(ActionEvent event) {
        queueManagementGridPane.setVisible(false);
        queueManagementButton.setStyle("-fx-text-fill: white;");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        try {
            systemEngine.getXMLFromUser(absolutePath);
            queueManagementButton.setDisable(false);
        } catch(RuntimeException | JAXBException | FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("This is an error message.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);

        mainController.onLoadFileButtonClick();

    }

    @FXML
    void onDetailsButtonClick(ActionEvent event) {
        mainController.onDetailsButtonClick();

        queueManagementGridPane.setVisible(false);
        queueManagementButton.setStyle("-fx-text-fill: white;");
    }

    @FXML
    void onNewExecutionButtonClick(ActionEvent event) {
        mainController.onNewExecutionButtonClick();

        queueManagementGridPane.setVisible(false);
        queueManagementButton.setStyle("-fx-text-fill: white;");}

    @FXML
    void onQueueManagementButtonClick(ActionEvent event) {

            mainController.onQueueManagementButtonClick();
            queueManagementButton.setStyle("-fx-text-fill: blue;");
            queueManagementGridPane.setVisible(true);

    }

    public void setCurrentlyExecutingLabel(String currentlyExecutingLabel) {
        this.valueCurrentlyExecutingLabel.setText(currentlyExecutingLabel);
    }

    public void setWaitingLabel(String waitingLabel) {
        this.valueWaitingLabel.setText(waitingLabel);
    }

    public void setOverLabel(String overLabel) {
        this.valueOverLabel.setText(overLabel);
    }

    @FXML
    void onResultButtonClick(ActionEvent event) {
        mainController.onResultButtonClick();
        queueManagementGridPane.setVisible(false);
        queueManagementButton.setStyle("-fx-text-fill: white;");
    }

}
