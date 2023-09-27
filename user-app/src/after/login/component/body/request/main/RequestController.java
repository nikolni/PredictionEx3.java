package after.login.component.body.request.main;

import after.login.component.body.execution.main.ExecutionController;
import after.login.component.body.running.list.view.update.UpdateListView;
import after.login.main.UserController;
import javafx.application.Platform;
import util.constants.Constants;
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
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static util.constants.Constants.LINE_SEPARATOR;
import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;
import static util.http.HttpClientUtil.SIMPLE_CALLBACK;

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
    private UserController mainController;


    @FXML
    public void initialize() {
        UpdateRequestGridPane updateRequestGridPane = new UpdateRequestGridPane(requestGridPane);
        new Thread(updateRequestGridPane).start();
    }
    public void setExecutionController(ExecutionController executionController) {
        this.executionController = executionController;
    }
    public void setMainController(UserController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void onSubmitClick(MouseEvent event) {
        if(isAllFieldsFilledIn()){
            if(isSimulationNameExist()) {
                try{
                    Integer.parseInt(simulationNumTextField.getText());
                    if(isTerminationConditionsValid()){
                        postRequestToServer();
                    }
                } catch (NumberFormatException e) {
                    popUpWindow("Simulation number field must contain numbers only!", "Error!");
                }
            }
            else{
                popUpWindow("Simulation name not exist!", "Error!");
            }
        }
        else{
            popUpWindow("Not all fields are filled!\nYou must fill in all fields.", "Error!");
        }
    }

    @FXML
    void onExecuteClick(MouseEvent event) {
        List<String> simulationNames = new ArrayList<>();
        getSimulationNamesFromServer(simulationNames);
        for(String name : simulationNames){
            if(name.equals()){
                SystemEngineAccess systemEngineAccess = getSystemEngineInstance();
            }
        }
        //check simulation name that was chosen
        //get right SE instance for simulation from server
        SystemEngineAccess systemEngineAccess;
        executionController.primaryInitialize(systemEngineAccess);
    }

    private boolean isAllFieldsFilledIn(){
        return !(simulationNameTextField.getText().isEmpty() || simulationNumTextField.getText().isEmpty() ||
                terminationConditionsTextField.getText().isEmpty());
    }

    private boolean isSimulationNameExist() {
        List<String> simulationNames = new ArrayList<>();
        getSimulationNamesFromServer(simulationNames);
        for (String name : simulationNames) {
            if (name.equals(simulationNameTextField.getText())) {
                return true;
            }
        }
        return false;
    }
    private boolean isTerminationConditionsValid(){
        String[] sentences = terminationConditionsTextField.getText().split(",");

        // Process the sentences
        for (String sentence : sentences) {
            if(!sentence.equals("1") && !sentence.equals("2") && !sentence.equals("3")){
                popUpWindow("Termination conditions are not valid!", "Error!");
                return false;
            }
            if(sentence.equals("3") && sentences.length > 1){
                popUpWindow("You can't choose 'by user' with another termination condition!", "Error!");
                return false;
            }
        }
        return true;
    }

    private void postRequestToServer(){
        String simulationName = simulationNameTextField.getText();
        String numberOfExecutions = simulationNumTextField.getText();
        String terminationConditions = terminationConditionsTextField.getText();

        String body = "simulation name="+simulationName + LINE_SEPARATOR +
                "number of executions="+numberOfExecutions + LINE_SEPARATOR +
                "termination conditions="+terminationConditions;

        Request request = new Request.Builder()
                .url(Constants.USER_REQUEST_PAGE)
                .post(RequestBody.create(body.getBytes()))
                .addHeader("user name", mainController.getUserName())
                .build();

        Call call = HTTP_CLIENT_PUBLIC.newCall(request);
        call.enqueue( new Callback() {
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
    private void getSimulationNamesFromServer(List<String> simulationNames) {
        String finalUrl = HttpUrl
                .parse(Constants.SIMULATION_NAMES_LIST_PAGE)
                .newBuilder()
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
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            List<String> simulationNames = Arrays.asList(Constants.GSON_INSTANCE.fromJson(json, String[].class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
