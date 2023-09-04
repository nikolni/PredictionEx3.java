package app.body.screen1.tile.rule.action.helper;

import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.condition.ConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
import dto.definition.rule.action.numeric.calculation.DivideActionDTO;
import dto.definition.rule.action.numeric.calculation.MultiplyActionDTO;
import javafx.scene.layout.FlowPane;

public interface ActionTileCreatorFactory {

      void createIncreaseActionChildren(IncreaseActionDTO increaseActionDTO, FlowPane detailsFlowPane);
      void createDecreaseActionChildren(DecreaseActionDTO decreaseActionDTO, FlowPane detailsFlowPane);
      void createSetActionChildren(SetActionDTO setActionDTO, FlowPane detailsFlowPane);
      void createDivideActionChildren(DivideActionDTO divideActionDTO, FlowPane detailsFlowPane);
      void createMultiplyActionChildren(MultiplyActionDTO multiplyActionDTO, FlowPane detailsFlowPane);
      void createKillActionChildren(KillActionDTO killActionDTO, FlowPane detailsFlowPane);
      void createSingleConditionActionChildren(ConditionActionDTO conditionActionDTO, FlowPane detailsFlowPane);

      void createMultipleConditionActionChildren(ConditionActionDTO conditionActionDTO, FlowPane detailsFlowPane);
}
