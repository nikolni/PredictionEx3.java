package dto.primary;

import java.util.List;

public class DTONamesListForUi {
    private final List<String> Names;

    public DTONamesListForUi(List<String> Names) {
        this.Names = Names;
    }

    public List<String> getNames() {
        return Names;
    }
}
