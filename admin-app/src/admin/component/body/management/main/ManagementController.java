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
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static admin.util.constants.Constants.popUpWindow;
import static admin.util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

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

    public ManagementController(){
        selectedFileProperty = new SimpleStringProperty();
    }

    @FXML
    private void initialize() {
        filePathLabel.textProperty().bind(selectedFileProperty);

        UpdateThreadsPoolDetails updateThreadsPoolDetails = new UpdateThreadsPoolDetails(requestsFromServer, this);
        new Thread(updateThreadsPoolDetails).start();

        simulationDetailsComponentController.primaryInitialize();
    }

    @FXML
    void onLoadFileClick(MouseEvent event){
        //String RESOURCE = "/upload/file";
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File f = fileChooser.showOpenDialog(primaryStage);
        if (f == null) {
            return;
        }
        String absolutePath = f.getAbsolutePath();
        final boolean[] exceptionOccurred = {false};

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/xml")))
                        .build();

        Request request = new Request.Builder()
                .url(Constants.FILE_PAGE)
                .post(body)
                .build();

        Call call = HTTP_CLIENT_PUBLIC.newCall(request);
        call.enqueue( new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> popUpWindow(e.getMessage(), "File failure!"));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    if(response.code() == 400){
                        Platform.runLater(() -> {
                            popUpWindow("the simulation already exist in the system", "File error!");
                            exceptionOccurred[0] =true;
                        });
                    }
                    else {
                        String responseBody = response.body().string();
                        Platform.runLater(() -> popUpWindow(responseBody, "File error!"));
                    }
                } else {
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            String simulationName = Constants.GSON_INSTANCE.fromJson(json, String.class);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        if(!exceptionOccurred[0]){
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