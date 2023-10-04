package admin.component.body.management.simulation.details.single.simulation.tile.rule.action.calculation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CalculationActionController {

        @FXML
        private Label actionTypeLabel;

        @FXML
        private Label primaryEntityLabel;
        @FXML
        private Label secondEntityLabel;

        @FXML
        private Label resultPropertyNameLabel;

        @FXML
        private Label expression1Label;

        @FXML
        private Label expression2Label;

        public void setActionTypeLabel(String actionTypeLabel) {
                this.actionTypeLabel.setText(actionTypeLabel);
        }

        public void setPrimaryEntityLabel(String primaryEntityLabel) {
                this.primaryEntityLabel.setText(primaryEntityLabel);
        }
        public void setSecondEntityLabel(String secondEntityText) {
                this.secondEntityLabel.setText(secondEntityText);
        }

        public void setResultPropertyNameLabel(String resultPropertyNameLabel) {
                this.resultPropertyNameLabel.setText(resultPropertyNameLabel);
        }

        public void setExpression1Label(String expression1Label) {
                this.expression1Label.setText(expression1Label);
        }

        public void setExpression2Label(String expression2Label) {
                this.expression2Label.setText(expression2Label);
        }
}
