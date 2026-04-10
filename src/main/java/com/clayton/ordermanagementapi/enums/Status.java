package com.clayton.ordermanagementapi.enums;

public enum Status {
    RECEIVED,
    PREPARING,
    READY,
    DELIVERED;

    public boolean canTransitionTo(Status newStatus){

        if (this == RECEIVED && newStatus == PREPARING) return true;

        if (this == PREPARING && newStatus == READY) return true;

        if (this == READY && newStatus == DELIVERED) return true;

        return false;
    }
}
