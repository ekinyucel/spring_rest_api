package com.amadeus.ist.springrest.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Member {
    private final String flyerID;
    private final String name;
    private final String lastname;
    private final int flyerPoints;
    private final String status;

    @JsonCreator
    public Member(@JsonProperty(value = "flyerID", required = true) String flyerID,
                  @JsonProperty(value = "name", required = true) String name,
                  @JsonProperty(value = "lastname", required = true) String lastname,
                  @JsonProperty(value = "flyerPoints", required = true) int flyerPoints,
                  @JsonProperty(value = "status", required = true)String status) {
        this.flyerID = flyerID;
        this.name = name;
        this.lastname = lastname;
        this.flyerPoints = flyerPoints;
        this.status = status;
    }

    public String getFlyerID() {
        return flyerID;
    }

    public int getFlyerPoints() {
        return flyerPoints;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return true;
        if (this.getClass() != o.getClass())
            return false;
        Member m = (Member) o;
        if (!this.flyerID.equals(m.getFlyerID()))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        // prime numbers are used to ensure uniqueness of the hashcode
        int hash = 11;
        hash = 31 * hash + Integer.parseInt(flyerID);
        hash = 31 * hash + (name == null ? 0 : name.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "Member information: " + this.flyerID + " " + this.name + " " + this.lastname + " " + this.flyerPoints;
    }

}
