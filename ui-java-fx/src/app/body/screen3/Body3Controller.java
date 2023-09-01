package app.body.screen3;

import app.body.screen1.tile.property.PropertyController;
import app.body.screen1.tile.property.PropertyResourceConstants;
import app.body.screen1.tile.rule.action.helper.ActionTileCreatorFactory;
import app.body.screen1.tile.rule.action.helper.ActionTileCreatorFactoryImpl;
import app.body.screen1.tile.termination.condition.TerminationConditionsController;
import app.body.screen1.tile.termination.condition.TerminationConditionsResourceConstants;
import dto.api.DTODefinitionsForUi;
import dto.api.DTOEnvVarsDefForUi;
import dto.definition.entity.api.EntityDefinitionDTO;
import dto.definition.property.definition.api.PropertyDefinitionDTO;
import dto.definition.rule.action.KillActionDTO;
import dto.definition.rule.action.SetActionDTO;
import dto.definition.rule.action.api.AbstractActionDTO;
import dto.definition.rule.action.condition.ConditionActionDTO;
import dto.definition.rule.action.numeric.DecreaseActionDTO;
import dto.definition.rule.action.numeric.IncreaseActionDTO;
import dto.definition.rule.action.numeric.calculation.DivideActionDTO;
import dto.definition.rule.action.numeric.calculation.MultiplyActionDTO;
import dto.definition.rule.api.RuleDTO;
import dto.definition.termination.condition.api.TerminationConditionsDTO;
import dto.definition.termination.condition.impl.TicksTerminationConditionsDTOImpl;
import dto.definition.termination.condition.impl.TimeTerminationConditionsDTOImpl;
import dto.definition.termination.condition.manager.api.TerminationConditionsDTOManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import system.engine.api.SystemEngineAccess;
import system.engine.impl.SystemEngineAccessImpl;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Body3Controller {


}

