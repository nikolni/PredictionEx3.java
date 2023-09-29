package after.login.component.body.simulation.details.single.simulation.tile.rule.action.condition.multiple;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MultipleConditionActionController {

        @FXML
        private Label actionTypeValLabel;

        @FXML
        private Label primaryEntityValLabel;

        @FXML
        private Label singularityValLabel;

        @FXML
        private Label secondEntitylValLabel;

        @FXML
        private Label logicalLabel;

        @FXML
        private Label conditionsNumberLabel;

        @FXML
        private Label thenActionsLabel;

        @FXML
        private Label elseActionsLabel;

        public void setActionTypeLabel(String actionTypeLabel) {
                this.actionTypeValLabel.setText(actionTypeLabel);
        }

        public void setPrimaryEntityLabel(String primaryEntityLabel) {
                this.primaryEntityValLabel.setText(primaryEntityLabel);
        }
        public void setSecondEntityLabel(String secondEntityText) {
                this.secondEntitylValLabel.setText(secondEntityText);
        }

        public void setSingularityLabel(String singularityLabel) {
                this.singularityValLabel.setText(singularityLabel);
        }
        public void setLogicalLabel(String logicalLabel) {
                this.logicalLabel.setText(logicalLabel);
        }
        public void setConditionsNumberLabel(String conditionsNumberLabel) {
                this.conditionsNumberLabel.setText(conditionsNumberLabel);
        }
        public void setThenActionsLabel(String thenActionsLabel) {
                this.thenActionsLabel.setText(thenActionsLabel);
        }

        public void setElseActionsLabel(String elseActionsLabel) {
                this.elseActionsLabel.setText(elseActionsLabel);
        }
}
