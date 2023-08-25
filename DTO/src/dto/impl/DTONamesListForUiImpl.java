package dto.impl;

import dto.api.DTONamesListForUi;

import java.util.List;

public class DTONamesListForUiImpl implements DTONamesListForUi {
    private final List<String> Names;


    public DTONamesListForUiImpl(List<String> Names) {
        this.Names = Names;
    }

    @Override
    public List<String> getNames() {
        return Names;
    }
}
