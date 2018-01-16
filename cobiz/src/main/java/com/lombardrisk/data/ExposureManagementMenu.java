package com.lombardrisk.data;

public enum ExposureManagementMenu {
    MARGIN_CALL("Margin Filters>Call"),
    MARGIN_RECALL("Margin Filters>Recall"),
    MARGIN_CALL_AND_RECALL("Margin Filters>Call Recall"),
    MARGIN_ISSUED("Margin Filters>Issued"),
    MARGIN_DELIVERY("Margin Filters>Delivery"),
    MARGIN_RETURN("Margin Filters>Return"),
    MARGIN_DELIVERY_AND_RETURN("Margin Filters>Delivery Return"),
    MARGIN_CONFIRMED("Margin Filters>Confirmed"),
    MARGIN_NO_CALLS("Margin Filters>No Calls"),
    MARGIN_WAIVED("Margin Filters>Waived"),
    MARGIN_COMPLETED("Margin Filters>Completed"),
    MARGIN_DISPUTES("Margin Filters>Disputes"),
    MARGIN_ERRORS("Margin Filters>Errors"),
    MARGIN_DYNAMIC_FILTER("Margin Filters>Dynamic Filter"),
    SUBSTITUTION_REQUEST("Substitution Filters>Sub Request"),
    SUBSTITUTION_CONFIRMATION("Substitution Filters>Sub Confirmation"),
    SUBSTITUTION_ISSUED("Substitution Filters>Issued"),
    SUBSTITUTION_CONFIRMED("Substitution Filters>Confirmed"),
    SUBSTITUTION_WAIVED("Substitution Filters>Waived"),
    SUBSTITUTION_PARTIALLY_BOOKED("Substitution Filters>Partially Booked"),
    SUBSTITUTION_COMPLETED("Substitution Filters>Completed"),
    ALL_DYNAMIC_FILTER("All Filters>Dynamic Filter"),
    USER_FILTER("User Filters>AddedFilter");

    private final String name;

    ExposureManagementMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
