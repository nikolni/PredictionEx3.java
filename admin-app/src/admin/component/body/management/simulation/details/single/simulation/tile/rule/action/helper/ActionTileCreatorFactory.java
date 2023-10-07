package admin.component.body.management.simulation.details.single.simulation.tile.rule.action.helper;

import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.ProximityActionDTO;
import dto.definition.rule.action.ReplaceActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.condition.ConditionActionDTO;
import dto.definition.rule.action.condition.MultipleConditionActionDTO;
import dto.definition.rule.action.condition.SingleConditionActionDTO;
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
      void createSingleConditionActionChildren(SingleConditionActionDTO singleConditionActionDTO, FlowPane detailsFlowPane);

      void createMultipleConditionActionChildren(MultipleConditionActionDTO multipleConditionActionDTO, FlowPane detailsFlowPane);
      void createProximityActionChildren(ProximityActionDTO proximityActionDTO, FlowPane detailsFlowPane);
      void createReplaceActionChildren(ReplaceActionDTO replaceActionDTO, FlowPane detailsFlowPane);
}
