package com.amadeus.ist.springrest.flights;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "full_passenger_list")
@JsonDeserialize(builder = Flight.FlightBuilder.class)
public final class Flight {

    @Id
    private int fplSEQ;
    private final String flightDate;
    private final String flyerId;
    private final String PNR;
    private final String flightNumber;
    private final String flightClass;
    private final String company;
    private final String origin;
    private final String destination;

    public Flight(FlightBuilder builder) {
        this.flightDate = builder.flightDate;
        this.flyerId = builder.flyerId;
        this.PNR = builder.PNR;
        this.flightNumber = builder.flightNumber;
        this.flightClass = builder.flightClass;
        this.company = builder.company;
        this.origin = builder.origin;
        this.destination = builder.destination;
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
        Flight flight = (Flight) o;
        return Objects.equals(flightDate, flight.flightDate) &&
                Objects.equals(flyerId, flight.flyerId) &&
                Objects.equals(origin, flight.origin) &&
                Objects.equals(destination, flight.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightDate, flyerId, origin, destination);
    }

    @Override
    public String toString() {
        return "Flight information: " + this.flyerId + " " + this.flightNumber + " " + this.origin + " " + this.destination + " " + this.flightDate;
    }

    public FlightDTO toConvertFlightDTO() {
        FlightDTO memberDTO = new FlightDTO(new ObjectId(), this.fplSEQ, this.flightDate, this.flyerId, this.PNR, this.flightNumber
                , this.flightClass, this.company, this.origin, this.destination);
        return memberDTO;

    }


    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
    public static class FlightBuilder {
        private int fplSEQ;
        private String flightDate;
        private String flyerId;
        private String PNR;
        private String flightNumber;
        private String flightClass;
        private String company;
        private String origin;
        private String destination;

        // factory method used fo preventing this reference to be escaped.
        public static FlightBuilder newInstance() {
            return new FlightBuilder();
        }

        private FlightBuilder() {
        }

        public FlightBuilder setFplSEQ(int fplSEQ) {
            this.fplSEQ = fplSEQ;
            return this;
        }

        public FlightBuilder setFlightDate(String flightDate) {
            if (flightDate.equals(null))
                throw new IllegalArgumentException("Flight Date must be filled");
            this.flightDate = flightDate;
            return this;
        }

        public FlightBuilder setFlyerID(String flyerID) {
            if (flyerID.equals(null))
                throw new IllegalArgumentException("FlyerID shall be entered");
            if (flyerID.length() != 13)
                throw new IllegalArgumentException("FlyerID must contain 13 digits");
            this.flyerId = flyerID;
            return this;
        }

        public FlightBuilder setPNR(String PNR) {
            if (PNR.equals(null))
                throw new IllegalArgumentException("PNR must be filled");
            this.PNR = PNR;
            return this;
        }

        public FlightBuilder setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public FlightBuilder setFlightClass(String flightClass) {
            this.flightClass = flightClass;
            return this;
        }

        public FlightBuilder setCompany(String company) {
            this.company = company;
            return this;
        }

        public FlightBuilder setOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public FlightBuilder setDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Flight build() {
            return new Flight(this);
        }
    }
}
