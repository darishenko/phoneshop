package com.es.core.model.phone.sortEnam;

public enum SortField {
    BRAND("brand"),
    MODEL("model"),
    DISPLAYSIZEINCHES("displaySize"),
    PRICE("price");
    public final String label;

    SortField(String label) {
        this.label = label;
    }

    public static SortField valueOfLabel(String label) {
        for (SortField sortField : values()) {
            if (sortField.label.equals(label)) {
                return sortField;
            }
        }
        return null;
    }
}
