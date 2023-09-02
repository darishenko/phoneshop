package com.es.core.model.phone.sortEnam;

public enum SortOrder {
    ASC("asc"),
    DESC("desc");
    public final String label;

    SortOrder(String label) {
        this.label = label;
    }

    public static SortOrder valueOfLabel(String label) {
        for (SortOrder sortOrder : values()) {
            if (sortOrder.label.equals(label)) {
                return sortOrder;
            }
        }
        return null;
    }
}
