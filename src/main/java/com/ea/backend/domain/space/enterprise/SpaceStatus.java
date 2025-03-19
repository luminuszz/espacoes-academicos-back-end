package com.ea.backend.domain.space.enterprise;

public enum SpaceStatus {

    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE");

    private  final String status;


    SpaceStatus(String status) {
        this.status = status;
    }
}
