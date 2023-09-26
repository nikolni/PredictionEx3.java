package after.login.component.body.request.main;

import after.login.component.body.execution.main.ExecutionController;
import constants.Constants;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import engine.per.file.engine.api.SystemEngineAccess;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RequestController {

    @FXML
    private TextField simulationNameTextField;

    @FXML
    private TextField simulationNumTextField;

    @FXML
    private TextField terminationConditionsTextField;

    @FXML
    private Button submitButton;

    @FXML
    private GridPane requestGridPane;

    @FXML
    private Button executeButton;
    private ExecutionController executionController;


    public void setExecutionController(ExecutionController executionController) {
        this.executionController = executionController;
    }

    @FXML
    void onSubmitClick(MouseEvent event) {
        if(! isAllFieldsFilledIn()){
            popUpWindow("Not all fields are filled!\nYou must fill in all fields.", "Error!");
        }
        else{
            sendRequestToTheServer();
        }

    }

    @FXML
    void onExecuteClick(MouseEvent event) {
        //check simulation name that was chosen
        //get right SE instance for simulation from server
        SystemEngineAccess systemEngineAccess;
        executionController.primaryInitialize(systemEngineAccess);
    }

    private boolean isAllFieldsFilledIn(){
        return !(simulationNameTextField.getText().isEmpty() || simulationNumTextField.getText().isEmpty() ||
                terminationConditionsTextField.getText().isEmpty());
    }
    private void popUpWindow(String massage, String title){
        Stage emptyFieldsErrorStage = new Stage();

        Label label = new Label(massage);
        Font font = new Font(16);
        label.setFont(font);
        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 350, 250);

        emptyFieldsErrorStage.setScene(scene);
        emptyFieldsErrorStage.setTitle(title);
        emptyFieldsErrorStage.show();
    }

    private void sendRequestToTheServer(){
        String simulationName = simulationNameTextField.getText();
        String numberOfExecutions = simulationNumTextField.getText();
        String terminationConditions = terminationConditionsTextField.getText();
        String finalUrl = HttpUrl
                .parse(Constants.USER_REQUEST_PAGE)
                .newBuilder()
                .addQueryParameter("simulation name", simulationName)
                .addQueryParameter("number of executions", numberOfExecutions)
                .addQueryParameter("termination conditions", terminationConditions)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                popUpWindow(e.getMessage(), "Error!");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    popUpWindow(responseBody, "Error!");
                } else {
                    popUpWindow("Your request has been received and is being processed", "Request was accepted");}
            }
        });
    }

}
