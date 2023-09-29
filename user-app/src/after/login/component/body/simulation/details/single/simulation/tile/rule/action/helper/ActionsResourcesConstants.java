package after.login.component.body.simulation.details.single.simulation.tile.rule.action.helper;

import java.net.URL;

public class ActionsResourcesConstants {

    private static final String BASE_PACKAGE = "/after/login/component/body/simulation/details/single/simulation/tile/rule/action";
    private static final  String IDS_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/increase/decrease/set/idsAction.fxml";
    private static final  String CALCULATION_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/calculation/calculationAction.fxml";

    private static final  String KILL_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/kill/killAction.fxml";

    private static final  String SINGLE_CONDITION_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/condition/single/singleConditionAction.fxml";
    private static final  String MULTIPLE_CONDITION_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/condition/multiple/multipleConditionAction.fxml";
    private static final  String PROXIMITY_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/proximity/proximityAction.fxml";
    private static final  String REPLACE_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/replace/replaceAction.fxml";
    public static final URL KILL_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.KILL_FXML_RESOURCE_IDENTIFIER);
    public static final URL IDS_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.IDS_FXML_RESOURCE_IDENTIFIER);

    public static final URL CALCULATION_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.CALCULATION_FXML_RESOURCE_IDENTIFIER);

    public static final URL SINGLE_CONDITION_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.SINGLE_CONDITION_FXML_RESOURCE_IDENTIFIER);
    public static final URL MULTIPLE_CONDITION_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.MULTIPLE_CONDITION_FXML_RESOURCE_IDENTIFIER);
    public static final URL PROXIMITY_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.PROXIMITY_FXML_RESOURCE_IDENTIFIER);
    public static final URL REPLACE_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.REPLACE_FXML_RESOURCE_IDENTIFIER);
}
