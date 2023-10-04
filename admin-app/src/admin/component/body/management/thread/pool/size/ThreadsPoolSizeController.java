package admin.component.body.management.thread.pool.size;

import admin.component.body.management.server.RequestsFromServer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;


public class ThreadsPoolSizeController {
    @FXML
    private TextField sizeTextField;

    @FXML
    private Button submitButton;
    private RequestsFromServer requestsFromServer;
    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        sizeTextField.setText("1");
        errorLabel.setVisible(false);
    }

    @FXML
    void onSubmitClick(MouseEvent event) {
        if (!sizeTextField.getText().isEmpty()) {
            if(isNewSizeValid()) {
                Integer newSize = Integer.parseInt(sizeTextField.getText());
                if (newSize != 1) {
                    requestsFromServer.setThreadsPoolSizeToServer(newSize);
                }
            }
        }
    }

    public boolean isNewSizeValid(){
        boolean inputIsNumber = true;
        String input= sizeTextField.getText();

        try{
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            inputIsNumber= false;
            errorLabel.setText("Only numbers!");
        }
        if(inputIsNumber){
            errorLabel.setVisible(false);
        }
        return  inputIsNumber;
    }

    public void setRequestsFromServer(RequestsFromServer requestsFromServer) {
        this.requestsFromServer = requestsFromServer;
    }
}
