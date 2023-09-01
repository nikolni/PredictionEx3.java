package app.body.screen1.tile.rule.action.helper;

import app.body.screen1.tile.rule.action.calculation.CalculationActionController;
import app.body.screen1.tile.rule.action.condition.ConditionActionController;
import app.body.screen1.tile.rule.action.kill.KillActionController;
import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.condition.ConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
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
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createConditionActionChildren(ConditionActionDTO conditionActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.CONDITION_FXML_URL);
            Node singleAction = loader.load();

            ConditionActionController conditionActionController = loader.getController();
            conditionActionController.setActionTypeLabel("condition");
            conditionActionController.setPrimaryEntityLabel(conditionActionDTO.getEntityDefinitionName());
            conditionActionController.setSingularityLabel(conditionActionDTO.getSingularity());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
