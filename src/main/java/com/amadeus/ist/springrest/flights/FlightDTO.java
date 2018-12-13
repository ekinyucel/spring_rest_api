package com.amadeus.ist.springrest.flights;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "full_passenger_list")
public class FlightDTO {
    @Id
    @JsonProperty("_id")
    public ObjectId _id;

    @JsonProperty("FPL_SEQ")
    private final int fplSEQ;

    @JsonProperty("FLT_DATE")
    private final String flightDate;

    @JsonProperty("FLYER_ID")
    private final String flyerId;

    @JsonProperty("PNR")
    private final String PNR;

    @JsonProperty("FLT_NUMBER")
    private final String flightNumber;

    @JsonProperty("FLT_CLASS")
    private final String flightClass;

    @JsonProperty("COMPANY")
    private final String company;

    @JsonProperty("ORIGIN")
    private final String origin;

    @JsonProperty("DESTINATION")
    private final String destination;

    public FlightDTO(ObjectId _id, int fplSEQ, String flightDate, String flyerId, String PNR, String flightNumber, String flightClass,
                     String company, String origin, String destination) {
        this._id = _id;
        this.fplSEQ = fplSEQ;
        this.flightDate = flightDate;
        this.flyerId = flyerId;
        this.PNR = PNR;
        this.flightNumber = flightNumber;
        this.flightClass = flightClass;
        this.company = company;
        this.origin = origin;
        this.destination = destination;
    }

    public ObjectId get_id() {
        return _id;
    }

    public int getFplSEQ() {
        return fplSEQ;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public String getFlyerId() {
        return flyerId;
    }

    public String getPNR() {
        return PNR;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public String getCompany() {
        return company;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDTO flightDTO = (FlightDTO) o;
        return Objects.equals(flightDate, flightDTO.flightDate) &&
                Objects.equals(flyerId, flightDTO.flyerId) &&
                Objects.equals(flightNumber, flightDTO.flightNumber) &&
                Objects.equals(flightClass, flightDTO.flightClass) &&
                Objects.equals(company, flightDTO.company) &&
                Objects.equals(origin, flightDTO.origin) &&
                Objects.equals(destination, flightDTO.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightDate, flyerId, flightNumber, flightClass, company, origin, destination);
    }

    public Flight toConvertMember() {
        Flight member = Flight.FlightBuilder.newInstance()
                .setFplSEQ(this.fplSEQ)
                .setFlightDate(this.flightDate)
                .setFlyerID(this.flyerId)
                .setPNR(this.PNR)
                .setFlightNumber(this.flightNumber)
                .setFlightClass(this.flightClass)
                .setCompany(this.company)
                .setOrigin(this.origin)
                .setDestination(this.destination).build();
        return member;
    }
}
