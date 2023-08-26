package screen1.body.tile.rule.action.helper;

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
import screen1.body.tile.rule.action.increase.decrease.set.IDSActionController;

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
            loader.setLocation(ActionsResourcesConstants.IDS_FXML_URL);
            Node singleAction = loader.load();

            IDSActionController idsActionController = loader.getController();
            idsActionController.setActionTypeLabel("divide");
            idsActionController.setPrimaryEntityLabel(divideActionDTO.getEntityDefinitionName());
            idsActionController.setPropertyNameLabel(divideActionDTO.getResultPropName());
            idsActionController.setExpressionLabel(divideActionDTO.getExpressionStrArg1());
            idsActionController.setExpressionLabel(divideActionDTO.getExpressionStrArg2());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createMultiplyActionChildren(MultiplyActionDTO multiplyActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.IDS_FXML_URL);
            Node singleAction = loader.load();

            IDSActionController idsActionController = loader.getController();
            idsActionController.setActionTypeLabel("multiply");
            idsActionController.setPrimaryEntityLabel(multiplyActionDTO.getEntityDefinitionName());
            idsActionController.setPropertyNameLabel(multiplyActionDTO.getResultPropName());
            idsActionController.setExpressionLabel(multiplyActionDTO.getExpressionStrArg1());
            idsActionController.setExpressionLabel(multiplyActionDTO.getExpressionStrArg2());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createKillActionChildren(KillActionDTO killActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.IDS_FXML_URL);
            Node singleAction = loader.load();

            IDSActionController idsActionController = loader.getController();
            idsActionController.setActionTypeLabel("kill");
            idsActionController.setPrimaryEntityLabel(killActionDTO.getEntityDefinitionName());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public  void createConditionActionChildren(ConditionActionDTO conditionActionDTO, FlowPane detailsFlowPane){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ActionsResourcesConstants.IDS_FXML_URL);
            Node singleAction = loader.load();

            IDSActionController idsActionController = loader.getController();
            idsActionController.setActionTypeLabel("condition");
            idsActionController.setPrimaryEntityLabel(conditionActionDTO.getEntityDefinitionName());
            idsActionController.setPrimaryEntityLabel(conditionActionDTO.getSingularity());
            detailsFlowPane.getChildren().add(singleAction);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
