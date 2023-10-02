package component.body.management.main;

import component.body.management.update.thread.status.UpdateThreadsPoolDetails;
import dto.definition.user.request.DTOUserRequestForUi;
import dto.include.DTOIncludeSimulationDetailsForUi;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import main.AdminController;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.constants.Constants;
import util.http.HttpClientUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static util.constants.Constants.*;
import static util.constants.Constants.GSON_INSTANCE;

public class ManagementController {

    @FXML
    private Label filePathLabel;
    private SimpleStringProperty selectedFileProperty;

    @FXML
    private Button loadFileButton;

    @FXML
    private Button setThreadsCountButton;

    @FXML
    private TreeView<String> simulationsDetailsTree;

    @FXML
    private Label valueWaitingLabel;

    @FXML
    private Label valueCurrentlyExecutingLabel;

    @FXML
    private Label valueOverLabel;
    private AdminController mainController;




    public void setMainController(AdminController mainController) {
        this.mainController = mainController;
    }

    public ManagementController() {
        selectedFileProperty = new SimpleStringProperty();
    }

    @FXML
    private void initialize() {
        filePathLabel.textProperty().bind(selectedFileProperty);
        TreeItem<String> rootItem = new TreeItem<>("Simulations");
        simulationsDetailsTree.setRoot(rootItem);
    }

    @FXML
    void onLoadFileClick(MouseEvent event) throws IOException {
        String RESOURCE = "/upload-file";
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File f = fileChooser.showOpenDialog(primaryStage);
        String absolutePath = f.getAbsolutePath();
        if (f == null) {
            return;
        }
        boolean exceptionOccurred = false;

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/xml")))
                        .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        try {
            Response response = call.execute();
            if (response.code() != 200) {
                String responseBody = response.body().string();
                Platform.runLater(() -> popUpWindow(responseBody, "Error!"));
            }
        } catch(RuntimeException | FileNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid XML file - Please load other file");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            exceptionOccurred=true;
        }
        if(!exceptionOccurred){
            selectedFileProperty.set(absolutePath);
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
