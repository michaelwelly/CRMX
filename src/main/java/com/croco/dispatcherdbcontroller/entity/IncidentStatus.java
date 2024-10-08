package com.croco.dispatcherdbcontroller.entity;

public enum IncidentStatus {
    REQUEST,
    PENDING,
    INVESTIGATING,
    IN_WORK,
    REPAIRED,
    DECLINED;

    IncidentStatus() {
    }
}