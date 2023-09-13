package app.body.screen2.tile.entity;

import app.body.screen2.main.Body2Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.util.List;

public class EntityController {


    @FXML
    private Label entityNameLabel;

    @FXML
    private TextField populationTextField;

    private Boolean isValueChanged = false;
    private List<String> entitiesNames;
    private List<Integer> entitiesPopulations;
    private Body2Controller callerController;
    private Integer previousValue = null;

    @FXML
    public void initialize() {
        populationTextField.setText("0");
    }
    public Integer getPopulationValue() {
        Integer population = 0;
        if(! populationTextField.getText().isEmpty()){
            population= Integer.parseInt(populationTextField.getText());
        }
        return population;

    }
    public void setEntitiesNames(List<String> entitiesNames) {
        this.entitiesNames = entitiesNames;
    }

    public boolean isPopulationQuantityValidity(){
        if(! populationTextField.getText().isEmpty()){
            boolean inputIsNumber = true;
            String input= populationTextField.getText();

            try{
                Integer.parseInt(input);
            } catch (NumberFormatException e) {
                inputIsNumber= false;
            }
            return  inputIsNumber && (getPopulationValue() >=0);
        }
        return true;
    }

    public void setEntityNameLabel(String entityNameLabel) {
        this.entityNameLabel.setText(entityNameLabel);
    }
    public void setPopulationTextField(String value){populationTextField.setText(value);}
    public void setCallerController(Body2Controller callerController) {
        this.callerController = callerController;
    }
}

