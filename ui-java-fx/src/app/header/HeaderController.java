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
    @FXML
    private Button queueManaementButton;
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

    @FXML
    void onDetailsButtonClick(ActionEvent event) {
        mainController.onDetailsButtonClick();
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
        } catch(RuntimeException | JAXBException | FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("This is an error message.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        selectedFileProperty.set(absolutePath);
        isFileSelected.set(true);
    }

    @FXML
    void onNewExecutionButtonClick(ActionEvent event) {mainController.onNewExecutionButtonClick();}

    @FXML
    void onQueueManagementButtonClick(ActionEvent event) {

    }

    @FXML
    void onResultButtonClick(ActionEvent event) {
        mainController.onResultButtonClick();
    }

}
