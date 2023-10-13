package after.login.component.body.request.main;

import after.login.component.body.execution.main.ExecutionController;
import after.login.component.body.request.server.RequestsFromServer;
import after.login.main.UserController;
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
    private final RequestsFromServer requestsFromServer = new RequestsFromServer();
    private final List<Label> helperList = new ArrayList<>();

    public void primaryInitialize() {
        simulationNames = new ArrayList<>();

        UpdateRequestGridPane updateRequestGridPane = new UpdateRequestGridPane(requestGridPane, requestsFromServer, mainController.getUserName(), helperList);
        new Thread(updateRequestGridPane).start();

        UpdateSimulationsNames updateSimulationsNames = new UpdateSimulationsNames(this, requestsFromServer);
        new Thread(updateSimulationsNames).start();

    }
    public void setExecutionController(ExecutionController executionController) {
        this.executionController = executionController;
    }
    public void setMainController(UserController mainController) {
        this.mainController = mainController;
    }

    public void setSimulationsNamesList(List<String> simulationNames){
        this.simulationNames = simulationNames;
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
                        addNewRequestToGridPane();
                        requestsFromServer.postRequestToServer(simulationName, numberOfExecutions, terminationConditions,
                                mainController.getUserName());
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
       // if(requestIndex != null && isRequestApproved(requestIndex) && isExecutionLeft(requestIndex)) {
            if(requestIndex != null && isExecutionLeft(requestIndex)) {
            String simulationName = getSimulationNameChosen(requestIndex);
            String requestID = getRequestIDChosen(requestIndex);
            executionController.setMembers(simulationName, requestID, requestIndex);
            executionController.primaryInitialize();
            mainController.onExecutionClickFromRequest();
        }
    }
    private boolean isRequestApproved(int requestIndex){
        if(! (helperList.get(requestIndex*4 -3).getText().equals("Approved!"))){
            popUpWindow("The request was not approved ", "Error");
        }
        return helperList.get(requestIndex*4 -3).getText().equals("Approved!");
    }
    private boolean isExecutionLeft(int requestIndex){
        String executionsLeftForExecute = getExecutionsLeftOfRequestChosen(requestIndex).getText();
        if(! (Integer.parseInt(executionsLeftForExecute) > 0)){
            popUpWindow("No executions left of this request.", "Error");
        }
        return (Integer.parseInt(executionsLeftForExecute) > 0);
    }
    public void decreaseExecutionLeftLabel(int requestIndex){
        Label executionsLeftForExecute = getExecutionsLeftOfRequestChosen(requestIndex);
        int newValue = Integer.parseInt(executionsLeftForExecute.getText()) - 1;
        executionsLeftForExecute.setText(Integer.toString(newValue));
    }

    private void addNewRequestToGridPane() {
        Label simulationName= new Label(simulationNameTextField.getText());
        Label simulationNum= new Label(simulationNumTextField.getText());
        Label executionsLeftForExecute= new Label(simulationNumTextField.getText());

        requestGridPane.add(simulationName, 4, numOfRequests+1);
        requestGridPane.add(simulationNum, 5, numOfRequests+1);
        requestGridPane.add(executionsLeftForExecute, 6, numOfRequests+1);

        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> checkBox.setSelected(newValue));
        requestGridPane.add(checkBox, 8, numOfRequests+1);
        if(checkBoxes == null){
            checkBoxes = new ArrayList<>();
        }
        checkBoxes.add(checkBox);

        GridPane.setHalignment(simulationName, javafx.geometry.HPos.CENTER);
        GridPane.setHalignment(simulationNum, javafx.geometry.HPos.CENTER);
        GridPane.setHalignment(executionsLeftForExecute, javafx.geometry.HPos.CENTER);
        GridPane.setHalignment(checkBox, javafx.geometry.HPos.CENTER);

        helperList.add(null);  //id
        helperList.add(null);  //status
        helperList.add(simulationName);
        helperList.add(executionsLeftForExecute);

        numOfRequests++;
    }
    private void clearTextFields(){
        simulationNameTextField.clear();
        simulationNumTextField.clear();
        terminationConditionsTextField.clear();
    }
    private Integer getRequestForExecution(){
        Integer requestIndex = null;
        int index = 1;
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
    private Label getExecutionsLeftOfRequestChosen(int row){
        return helperList.get(row*4 -1);
    }
    private String getSimulationNameChosen(int row){
        return helperList.get(row*4 -2).getText();
    }
    private String getRequestIDChosen(int row){
        return helperList.get(row*4 -4).getText();
    }

    private boolean isAllFieldsFilledIn(){
        return !(simulationNameTextField.getText().isEmpty() || simulationNumTextField.getText().isEmpty() ||
                terminationConditionsTextField.getText().isEmpty());
    }

    private boolean isSimulationNameExist() {
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
            if(!sentence.equals("1")){
                String[] subSentences = sentence.split("=");
                if(subSentences.length != 2 || (!subSentences[0].equals("2") && !subSentences[0].equals("3"))){
                    popUpWindow("Termination conditions are not valid!", "Error!");
                    return false;
                }
            }
            else if(sentences.length > 1){
                popUpWindow("You can't choose 'by user' with another termination condition!", "Error!");
                return false;
            }
        }
        return true;
    }


}
