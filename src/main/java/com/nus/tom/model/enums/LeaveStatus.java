package com.nus.tom.model.enums;

public enum LeaveStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    public final String value;

    LeaveStatus(String value) {
        this.value = value;
    }

}
