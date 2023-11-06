package com.soa.lab2soa.model.requests;

public enum SortParam {
    ID("id"),
    COORDINATES("coordinates"),
    CREATION_DATE("creationDate"),
    STUDENTS_COUNT("studentsCount"),
    TRANSFERRED_STUDENTS("transferredStudents"),
    AVERAGE_MARK("averageMark");

    private final String value;

    SortParam(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static SortParam fromString(String value) {
        for (SortParam p : SortParam.values()) {
            if (p.value.equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Such SortParam doesn't exist");
    }
}
