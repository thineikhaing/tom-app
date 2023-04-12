package com.nus.tom.model.enums;

public enum LeaveType {
    ANNUAL("Annual Leave"),
    CC("Childcare Leave"),
    MC("Medical Leave"),

    MARRIAGE("Marriage Leave"),

    HOSPITALIZED("Hospitalized Leave");

    public final String value;

    LeaveType(String value) {
        this.value = value;
    }

}
