package after.login.component.body.request.main;

import after.login.component.body.execution.main.ExecutionController;
import after.login.component.body.request.server.RequestsFromServer;
import after.login.main.UserController;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.util.*;

import static util.constants.Constants.popUpWindow;

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
    private RequestsFromServer requestsFromServer = null;


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
                        String simulationName = simulationNameTextField.getText();
                        String numberOfExecutions = simulationNumTextField.getText();
                        String terminationConditions = terminationConditionsTextField.getText();
                        requestsFromServer.postRequestToServer(simulationName, numberOfExecutions, terminationConditions,
                                mainController.getUserName());
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
            String requestID = getRequestIDChosen(requestIndex);
            executionController.setSimulationNameAndRequestID(simulationName, requestID);
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
    private String getRequestIDChosen(int row){
        int targetRow = row;
        int targetColumn = 0;
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
        simulationNames = requestsFromServer.getSimulationNamesFromServer();
        if(simulationNames != null) {
            for (String name : simulationNames) {
                if (name.equals(simulationNameTextField.getText())) {
                    return true;
                }
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


}
