package component.body.request.main;

import component.body.execution.main.ExecutionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import system.engine.api.SystemEngineAccess;

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
    void onExecuteClick(MouseEvent event) {
        //check simulation name that was chosen
        //get right SE instance for simulation from server
        SystemEngineAccess systemEngineAccess;
        executionController.primaryInitialize(systemEngineAccess);
    }

    @FXML
    void onSubmitClick(MouseEvent event) {

    }

}
