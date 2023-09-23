package component.body.simulation.details.single.simulation.tile.rule.action.condition.single;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SingleConditionActionController {

        @FXML
        private Label actionTypeValLabel;

        @FXML
        private Label primaryEntityValLabel;

        @FXML
        private Label singularityValLabel;

        @FXML
        private Label secondEntitylValLabel;

        @FXML
        private Label propertyNameLabel;

        @FXML
        private Label operatorLabel;

        @FXML
        private Label valueLabel;
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
        public void setPropertyNameLabel(String propertyNameText) {
                this.propertyNameLabel.setText(propertyNameText);
        }
        public void setOperatorLabel(String operatorLabel) {
                this.operatorLabel.setText(operatorLabel);
        }
        public void setValueLabel(String valueLabel) {
                this.valueLabel.setText(valueLabel);
        }
        public void setThenActionsLabel(String thenActionsLabel) {
                this.thenActionsLabel.setText(thenActionsLabel);
        }

        public void setElseActionsLabel(String elseActionsLabel) {
                this.elseActionsLabel.setText(elseActionsLabel);
        }
}
