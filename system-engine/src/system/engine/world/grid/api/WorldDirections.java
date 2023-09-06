package system.engine.world.grid.api;

public enum WorldDirections {
    FORWARD, RIGHT, BACK, LEFT;

    public static WorldDirections intToEnum(int intValue) {
        WorldDirections worldDirections = null;

        switch (intValue) {
            case 0:
                worldDirections=  WorldDirections.FORWARD;
                break;
            case 1:
                worldDirections= WorldDirections.RIGHT;
                break;
            case 2:
                worldDirections= WorldDirections.BACK;
                break;
            case 3:
                worldDirections= WorldDirections.LEFT;
                break;
        }
        return worldDirections;
    }

}


