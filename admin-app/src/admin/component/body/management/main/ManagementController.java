package admin.component.body.management.main;

import admin.component.body.management.update.thread.status.UpdateThreadsPoolDetails;
import admin.component.body.management.update.thread.status.server.RequestsFromServer;
import admin.main.AdminController;
import admin.util.constants.Constants;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import okhttp3.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    private final RequestsFromServer requestsFromServer = new RequestsFromServer();




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

        UpdateThreadsPoolDetails updateThreadsPoolDetails = new UpdateThreadsPoolDetails(requestsFromServer, this);
        new Thread(updateThreadsPoolDetails).start();
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
                .url(Constants.BASE_URL + RESOURCE)
                .post(body)
                .build();

        Call call = Constants.HTTP_CLIENT.newCall(request);

        try {
            Response response = call.execute();
            if (response.code() != 200) {
                String responseBody = response.body().string();
                Platform.runLater(() -> Constants.popUpWindow(responseBody, "Error!"));
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