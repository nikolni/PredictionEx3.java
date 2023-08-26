package screen1.body.tile.rule.action.helper;

import java.net.URL;

public class ActionsResourcesConstants {

    private static final String BASE_PACKAGE = "/screen1/body/tile/rule/action";
    private static final  String IDS_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/increase/decrease/set/idsAction.fxml";

    private static final  String CALCULATION_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/calculation/conditionAction.fxml";

    private static final  String KILL_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/kill/killAction.fxml";

    private static final  String CONDITION_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/condition/conditionAction.fxml";

    //public static final String MAIN_FXML_RESOURCE_IDENTIFIER = BASE_PACKAGE + "/components/main/histogram.fxml";
    public static final URL KILL_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.KILL_FXML_RESOURCE_IDENTIFIER);
    public static final URL IDS_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.IDS_FXML_RESOURCE_IDENTIFIER);

    public static final URL CALCULATION_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.CALCULATION_FXML_RESOURCE_IDENTIFIER);

    public static final URL CONDITION_FXML_URL = ActionsResourcesConstants.class.getResource(ActionsResourcesConstants.CONDITION_FXML_RESOURCE_IDENTIFIER);

}
