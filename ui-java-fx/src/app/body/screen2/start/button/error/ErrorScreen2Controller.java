package app.body.screen2.start.button.error;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ErrorScreen2Controller {
    @FXML
    private TextFlow envVarsText;

    @FXML
    private TextFlow entitiesText;

    @FXML
    private TextFlow populationText;

    public void setEntitiesText(String entitiesText) {
        Text entitiesNames = new Text(String.valueOf(entitiesText));
        this.entitiesText.getChildren().add(entitiesNames);
    }

    public void setEnvVarsText(String envVarsText) {
        Text envVarsNames = new Text(String.valueOf(envVarsText));
        this.envVarsText.getChildren().add(envVarsNames);
    }

    public void setPopulationText(String populationText) {
        Text population = new Text(String.valueOf(populationText));
        this.populationText.getChildren().add(population);
    }

}
