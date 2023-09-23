package component.body.simulation.details.single.simulation.tile;

import java.net.URL;

public class ResourceConstants {
    private static final String PROPERTY_FXML_RESOURCE_IDENTIFIER = "/component/body/simulation/details/single/simulation/tile/property/property.fxml";
    public static final URL PROPERTY_FXML_RESOURCE = ResourceConstants.class.getResource(ResourceConstants.PROPERTY_FXML_RESOURCE_IDENTIFIER);

    private static final String WORLD_GRID_FXML_RESOURCE_IDENTIFIER = "/component/body/simulation/details/single/simulation/tile/world/grid/WorldGridSizes.fxml";
    public static final URL WORLD_GRID_FXML_RESOURCE = ResourceConstants.class.getResource(ResourceConstants.WORLD_GRID_FXML_RESOURCE_IDENTIFIER);

    private static final String TERMINATION_CONDITION_FXML_RESOURCE_IDENTIFIER = "/component/body/simulation/details/single/simulation/tile/termination/condition/terminationConditions.fxml";
    public static final URL TERMINATION_CONDITION_FXML_RESOURCE = ResourceConstants.class.getResource(ResourceConstants.TERMINATION_CONDITION_FXML_RESOURCE_IDENTIFIER);
}
