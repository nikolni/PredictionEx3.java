package admin.component.body.management.main;

import admin.component.body.management.simulation.details.main.SimulationsDetailsController;
import admin.component.body.management.thread.pool.size.ThreadsPoolSizeController;
import admin.component.body.management.thread.pool.update.UpdateThreadsPoolDetails;
import admin.component.body.management.server.RequestsFromServer;
import admin.main.AdminController;
import admin.util.constants.Constants;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private ScrollPane simulationsDetailsScrollPane;

    @FXML
    private Label valueWaitingLabel;

    @FXML
    private Label valueCurrentlyExecutingLabel;

    @FXML
    private Label valueOverLabel;
    private AdminController mainController;

    private final RequestsFromServer requestsFromServer = new RequestsFromServer();
    @FXML private HBox simulationDetailsComponent;
    @FXML private SimulationsDetailsController simulationDetailsComponentController;



    public void setMainController(AdminController mainController) {
        this.mainController = mainController;
    }

    public ManagementController() throws IOException {
        selectedFileProperty = new SimpleStringProperty();
    }

    @FXML
    private void initialize() {
        filePathLabel.textProperty().bind(selectedFileProperty);

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
            else{ //successful file upload
                try (ResponseBody responseBody = response.body()) {
                    if (responseBody != null) {
                        String json = response.body().string();
                        String simulationName = Constants.GSON_INSTANCE.fromJson(json, String.class);
                        simulationDetailsComponentController.addSimulationItemToTreeView(simulationName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        Stage primaryStage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/component/body/management/thread/pool/size/threadsPoolSize.fxml"));
            GridPane root = loader.load();

            ThreadsPoolSizeController threadsPoolSizeController = loader.getController();
            threadsPoolSizeController.setRequestsFromServer(requestsFromServer);

            Scene scene = new Scene(root, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
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