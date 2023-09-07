package app.body.screen1.tile.rule.action.helper;

import app.body.screen1.tile.rule.action.calculation.CalculationActionController;
import app.body.screen1.tile.rule.action.condition.multiple.MultipleConditionActionController;
import app.body.screen1.tile.rule.action.condition.single.SingleConditionActionController;
import app.body.screen1.tile.rule.action.kill.KillActionController;
import app.body.screen1.tile.rule.action.proximity.ProximityActionController;
import app.body.screen1.tile.rule.action.replace.ReplaceActionController;
import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.ProximityActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.condition.ConditionActionDTO;
import dto.definition.rule.action.condition.MultipleConditionActionDTO;
import dto.definition.rule.action.condition.SingleConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
import dto.definition.rule.action.ReplaceActionDTO;
import dto.definition.rule.action.numeric.calculation.DivideActionDTO;
import dto.definition.rule.action.numeric.calculation.MultiplyActionDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import app.body.screen1.tile.rule.action.increase.decrease.set.IDSActionController;

import java.io.IOException;

public class ActionTileCreatorFactoryImpl implements ActionTileCreatorFactory{

    @Override
    public  void createIncreaseActionChildren(IncreaseActionDTO increaseActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.IDS_FXML_URL);
            Node singleAction = loader.load();

            IDSActionController idsActionController = loader.getController();
            idsActionController.setActionTypeLabel("increase");
            idsActionController.setPrimaryEntityLabel(increaseActionDTO.getEntityDefinitionName());
            idsActionController.setSecondEntityLabel(increaseActionDTO.getSecondEntityDefinitionDTO());
            idsActionController.setPropertyNameLabel(increaseActionDTO.getPropertyName());
            idsActionController.setExpressionLabel(increaseActionDTO.getExpressionStr());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public  void createDecreaseActionChildren(DecreaseActionDTO decreaseActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.IDS_FXML_URL);
            Node singleAction = loader.load();

            IDSActionController idsActionController = loader.getController();
            idsActionController.setActionTypeLabel("decrease");
            idsActionController.setPrimaryEntityLabel(decreaseActionDTO.getEntityDefinitionName());
            idsActionController.setSecondEntityLabel(decreaseActionDTO.getSecondEntityDefinitionDTO());
            idsActionController.setPropertyNameLabel(decreaseActionDTO.getPropertyName());
            idsActionController.setExpressionLabel(decreaseActionDTO.getExpressionStr());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createSetActionChildren(SetActionDTO setActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.IDS_FXML_URL);
            Node singleAction = loader.load();

            IDSActionController idsActionController = loader.getController();
            idsActionController.setActionTypeLabel("set");
            idsActionController.setPrimaryEntityLabel(setActionDTO.getEntityDefinitionName());
            idsActionController.setSecondEntityLabel(setActionDTO.getSecondEntityDefinitionDTO());
            idsActionController.setPropertyNameLabel(setActionDTO.getPropertyName());
            idsActionController.setExpressionLabel(setActionDTO.getExpressionStr());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createDivideActionChildren(DivideActionDTO divideActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.CALCULATION_FXML_URL);
            Node singleAction = loader.load();

            CalculationActionController calculationActionController = loader.getController();
            calculationActionController.setActionTypeLabel("divide");
            calculationActionController.setPrimaryEntityLabel(divideActionDTO.getEntityDefinitionName());
            calculationActionController.setSecondEntityLabel(divideActionDTO.getSecondEntityDefinitionDTO());
            calculationActionController.setResultPropertyNameLabel(divideActionDTO.getResultPropName());
            calculationActionController.setExpression1Label(divideActionDTO.getExpressionStrArg1());
            calculationActionController.setExpression2Label(divideActionDTO.getExpressionStrArg2());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createMultiplyActionChildren(MultiplyActionDTO multiplyActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.CALCULATION_FXML_URL);
            Node singleAction = loader.load();

            CalculationActionController calculationActionController = loader.getController();
            calculationActionController.setActionTypeLabel("multiply");
            calculationActionController.setPrimaryEntityLabel(multiplyActionDTO.getEntityDefinitionName());
            calculationActionController.setSecondEntityLabel(multiplyActionDTO.getSecondEntityDefinitionDTO());
            calculationActionController.setResultPropertyNameLabel(multiplyActionDTO.getResultPropName());
            calculationActionController.setExpression1Label(multiplyActionDTO.getExpressionStrArg1());
            calculationActionController.setExpression2Label(multiplyActionDTO.getExpressionStrArg2());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createKillActionChildren(KillActionDTO killActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.KILL_FXML_URL);
            Node singleAction = loader.load();

            KillActionController killActionController = loader.getController();
            killActionController.setActionTypeLabel("kill");
            killActionController.setPrimaryEntityLabel(killActionDTO.getEntityDefinitionName());
            killActionController.setSecondEntityLabel(killActionDTO.getSecondEntityDefinitionDTO());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createSingleConditionActionChildren(ConditionActionDTO conditionActionDTO, FlowPane detailsFlowPane){
        SingleConditionActionDTO singleConditionActionDTO= (SingleConditionActionDTO)conditionActionDTO;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.SINGLE_CONDITION_FXML_URL);
            Node singleAction = loader.load();

            SingleConditionActionController singleConditionActionController = loader.getController();
            singleConditionActionController.setActionTypeLabel("condition");
            singleConditionActionController.setPrimaryEntityLabel(singleConditionActionDTO.getEntityDefinitionName());
            singleConditionActionController.setSecondEntityLabel(singleConditionActionDTO.getSecondEntityDefinitionDTO());
            singleConditionActionController.setSingularityLabel(singleConditionActionDTO.getSingularity());
            singleConditionActionController.setThenActionsLabel(singleConditionActionDTO.getThenActionNumber().toString());
            singleConditionActionController.setElseActionsLabel(singleConditionActionDTO.getElseActionNumber().toString());
            singleConditionActionController.setPropertyNameLabel(singleConditionActionDTO.getPropertyName());
            singleConditionActionController.setOperatorLabel(singleConditionActionDTO.getOperator());
            singleConditionActionController.setValueLabel(singleConditionActionDTO.getExpressionStr());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public  void createMultipleConditionActionChildren(ConditionActionDTO conditionActionDTO, FlowPane detailsFlowPane){
        MultipleConditionActionDTO multipleConditionActionDTO= (MultipleConditionActionDTO)conditionActionDTO;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.MULTIPLE_CONDITION_FXML_URL);
            Node singleAction = loader.load();

            MultipleConditionActionController multipleConditionActionController = loader.getController();
            multipleConditionActionController.setActionTypeLabel("condition");
            multipleConditionActionController.setPrimaryEntityLabel(multipleConditionActionDTO.getEntityDefinitionName());
            multipleConditionActionController.setSecondEntityLabel(multipleConditionActionDTO.getSecondEntityDefinitionDTO());
            multipleConditionActionController.setSingularityLabel(multipleConditionActionDTO.getSingularity());
            multipleConditionActionController.setThenActionsLabel(multipleConditionActionDTO.getThenActionNumber().toString());
            multipleConditionActionController.setElseActionsLabel(multipleConditionActionDTO.getElseActionNumber().toString());
            multipleConditionActionController.setLogicalLabel(multipleConditionActionDTO.getLogical());
            multipleConditionActionController.setConditionsNumberLabel(multipleConditionActionDTO.getConditionsNumber().toString());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createProximityActionChildren(ProximityActionDTO proximityActionDTO, FlowPane detailsFlowPane) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.PROXIMITY_FXML_URL);
            Node singleAction = loader.load();

            ProximityActionController proximityActionController = loader.getController();
            proximityActionController.setActionTypeLabel("proximity");
            proximityActionController.setPrimaryEntityLabel(proximityActionDTO.getEntityDefinitionName());
            proximityActionController.setSecondEntityLabel(proximityActionDTO.getTargetEntityDefinitionName());
            proximityActionController.setOfLabel(proximityActionDTO.getOf());
            proximityActionController.setActionsNumLabel(proximityActionDTO.getActionsCollectionSize().toString());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createReplaceActionChildren(ReplaceActionDTO replaceActionDTO, FlowPane detailsFlowPane) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.REPLACE_FXML_URL);
            Node singleAction = loader.load();

            ReplaceActionController replaceActionController = loader.getController();
            replaceActionController.setActionTypeLabel("replace");
            replaceActionController.setKillEntityLabel(replaceActionDTO.getEntityDefinitionName());
            replaceActionController.setCreateEntityLabel(replaceActionDTO.getCreateEntityDefinitionName());
            replaceActionController.setModeLabel(replaceActionDTO.getMode());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
