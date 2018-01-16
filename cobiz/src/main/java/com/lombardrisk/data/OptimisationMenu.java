package com.lombardrisk.data;

/**
 * Created by Kent Gu on 7/6/2017.
 */
public enum OptimisationMenu {

    Filters_Asset("Filters>Asset"),
    Filters_Agreement("Filters>Agreement"),
    Rankings_Asset("Rankings>Asset"),
    Rankings_Agreement("Rankings>Agreement"),
    Optimisation_Rules("Optimisation>Rules"),
    Optimisation_Results("Optimisation>Results");

    private final String name;

    OptimisationMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
