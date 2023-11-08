package com.soa.lab2soa.model.domain;

import com.soa.lab2soa.model.requests.SortParam;

public enum Semester {
    SECOND("SECOND"),
    THIRD("THIRD"),
    FOURTH("FOURTH"),
    SIXTH("SIXTH"),
    SEVENTH("SEVENTH");

    private final String value;

    Semester(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Semester fromString(String value) {
        for (Semester p : Semester.values()) {
            if (p.value.equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Such Semester doesn't exist");
    }
}
