package dto.impl;

import dto.api.DTOWorldGridForUi;

public class DTOWorldGridForUiImpl implements DTOWorldGridForUi {

    private final Integer gridRows;
    private final Integer gridColumns;


    public DTOWorldGridForUiImpl(Integer gridRows, Integer gridColumns) {
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;
    }

    @Override
    public Integer getGridRows() {
        return gridRows;
    }
    @Override
    public Integer getGridColumns() {
        return gridColumns;
    }
}
