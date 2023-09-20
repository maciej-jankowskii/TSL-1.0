package com.tslcompany.truck;

import lombok.Getter;

@Getter
public enum Truck {

    BOX("Standard box truck 13,6m"),
    FRIGO("Standard frigo truck 13,6m"),
    FLATBED("Standard flatbed 13,6m"),
    TAUTLINER("Standard tautliner 13,6 x 2,45 x 2,7m"),
    MEGA("Mega trailer 13,6 x 2,45 x 2,94m"),
    TANKER("Standard taker"),
    TIPPER("Standard tipper"),
    ROAD_TRAIN("Road train 7,7 + 7,7m");

    private String description;

    Truck(String description) {
        this.description = description;
    }

}
