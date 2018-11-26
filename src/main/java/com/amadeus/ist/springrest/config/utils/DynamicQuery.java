package com.amadeus.ist.springrest.config.utils;

// TODO make this a generic array in which every repository can use it
// TODO convert it into an immutable class
public class DynamicQuery {
    private String flyerId;

    public String getFlyerId() {
        return flyerId;
    }

    public void setFlyerId(String flyerId) {
        this.flyerId = flyerId;
    }
}
