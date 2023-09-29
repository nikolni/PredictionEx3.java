package dto.primary;

public class DTOWorldGridForUi {

    private final Integer gridRows;
    private final Integer gridColumns;


    public DTOWorldGridForUi(Integer gridRows, Integer gridColumns) {
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;
    }

    public Integer getGridRows() {
        return gridRows;
    }
    public Integer getGridColumns() {
        return gridColumns;
    }
}
