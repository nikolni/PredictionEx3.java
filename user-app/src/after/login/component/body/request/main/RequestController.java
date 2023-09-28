package after.login.component.body.request.main;

import after.login.component.body.execution.main.ExecutionController;
import after.login.main.UserController;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import util.constants.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.*;

import static util.constants.Constants.LINE_SEPARATOR;
import static util.constants.Constants.popUpWindow;
import static util.http.HttpClientUtil.HTTP_CLIENT_PUBLIC;

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
    private Integer numOfRequests=0;
    private List<CheckBox> checkBoxes = null;
    private List<String> simulationNames;


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
                        addNewRequestToGridPane();
                        clearTextFields();
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
        Integer requestIndex = getRequestForExecution();
        if(requestIndex != null) {
            String simulationName = getSimulationNameChosen(requestIndex);
            executionController.setSimulationName(simulationName);
        }
    }

    private void addNewRequestToGridPane() {
        Label simulationName= new Label(simulationNameTextField.getText());
        Label simulationNum= new Label(simulationNumTextField.getText());
        Label terminationConditions= new Label(terminationConditionsTextField.getText());
        requestGridPane.add(simulationName, 4, numOfRequests);
        requestGridPane.add(simulationNum, 5, numOfRequests);
        requestGridPane.add(terminationConditions, 6, numOfRequests);
        CheckBox checkBox = new CheckBox();
        requestGridPane.add(checkBox, 7, numOfRequests);
        if(checkBoxes == null){
            checkBoxes = new ArrayList<>();
        }
        checkBoxes.add(checkBox);
        numOfRequests++;
    }
    private void clearTextFields(){
        simulationNameTextField.clear();
        simulationNumTextField.clear();
        terminationConditionsTextField.clear();
    }
    private Integer getRequestForExecution(){
        Integer requestIndex = null;
        int index = 0;
        for(CheckBox checkBox: checkBoxes){
            if(checkBox.isSelected()){
                if(requestIndex == null) {
                    requestIndex = index;
                }
                else{
                    popUpWindow("You can't choose more than one request to execute!", "Error!");
                    return null;
                }
            }
            index++;
        }
        return requestIndex;
    }
    private String getSimulationNameChosen(int row){
        int targetRow = row;
        int targetColumn = 4;
        Node childInGridPane = null;

        for (Node child : requestGridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(child);
            Integer columnIndex = GridPane.getColumnIndex(child);

            if (rowIndex != null && columnIndex != null && rowIndex == targetRow && columnIndex == targetColumn) {
                childInGridPane = child;
                break;
            }
        }
        return ((Label) childInGridPane).getText();
    }

    private boolean isAllFieldsFilledIn(){
        return !(simulationNameTextField.getText().isEmpty() || simulationNumTextField.getText().isEmpty() ||
                terminationConditionsTextField.getText().isEmpty());
    }

    private boolean isSimulationNameExist() {
        getSimulationNamesFromServer();
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
                .addHeader("user_name", mainController.getUserName())
                .build();

        Call call = HTTP_CLIENT_PUBLIC.newCall(request);
        call.enqueue( new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    popUpWindow(e.getMessage(), "Error!");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> {
                        popUpWindow(responseBody, "Error!");
                    });
                } else {
                    Platform.runLater(() -> {
                        popUpWindow("Your request has been received and is being processed", "Request was accepted");
                    });
                }
            }
        });
    }
    private void getSimulationNamesFromServer() {
        String finalUrl = HttpUrl
                .parse(Constants.SIMULATION_NAMES_LIST_PAGE)
                .newBuilder()
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    popUpWindow(e.getMessage(), "Error!");
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> {
                        popUpWindow(responseBody, "Error!");
                    });
                } else {
                    // Read and process the response content
                    try (ResponseBody responseBody = response.body()) {
                        if (responseBody != null) {
                            String json = response.body().string();
                            simulationNames = Arrays.asList(Constants.GSON_INSTANCE.fromJson(json, String[].class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
