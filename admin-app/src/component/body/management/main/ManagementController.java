package component.body.management.main;

import component.body.management.update.thread.status.UpdateThreadsPoolDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import engine.per.file.engine.api.SystemEngineAccess;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class ManagementController {

    @FXML
    private Label filePathLabel;

    @FXML
    private Button loadFileButton;

    @FXML
    private Button setThreadsCountButton;

    @FXML
    private ListView<?> availableSimulationsDetailsList;

    @FXML
    private Label valueWaitingLabel;

    @FXML
    private Label valueCurrentlyExecutingLabel;

    @FXML
    private Label valueOverLabel;
    private SystemEngineAccess systemEngine;

    public void setSystemEngine(SystemEngineAccess systemEngineAccess){
        this.systemEngine = systemEngineAccess;
    }

    public void primaryInitialize() {
        UpdateThreadsPoolDetails updateThreadsPoolDetails = new UpdateThreadsPoolDetails(this, systemEngine);
        new Thread(updateThreadsPoolDetails).start();
    }

    @FXML
    void onLoadFileClick(MouseEvent event) {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        boolean exceptionOccurred = false;
        String absolutePath = selectedFile.getAbsolutePath();
        try {
            systemEngine.getXMLFromUser(absolutePath);
        } catch(RuntimeException | JAXBException | FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid XML file - Please load other file");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            exceptionOccurred=true;
        }
        if(!exceptionOccurred){
            selectedFileProperty.set(absolutePath);
            isFileSelected.set(true);
           // mainController.onLoadFileButtonClick();
        }
    }

    @FXML
    void onSetThreadsCount(MouseEvent event) {

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

}
