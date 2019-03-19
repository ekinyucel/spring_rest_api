package com.amadeus.ist.springrest.flights;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@JsonDeserialize(builder = Flight.FlightBuilder.class)
public final class Flight {

    @Id
    @JsonProperty("fplSeq")
    private final int fplSEQ;

    @JsonProperty("flightDate")
    private final String flightDate;

    @JsonProperty("flyerID")
    private final String flyerId;

    @JsonProperty("pnr")
    private final String PNR;

    @JsonProperty("flightNumber")
    private final String flightNumber;

    @JsonProperty("flightClass")
    private final String flightClass;

    @JsonProperty("company")
    private final String company;

    @JsonProperty("origin")
    private final String origin;

    @JsonProperty("destination")
    private final String destination;

    @JsonProperty("status")
    private final String status;

    public Flight(FlightBuilder builder) {
        this.fplSEQ = builder.fplSEQ;
        this.flightDate = builder.flightDate;
        this.flyerId = builder.flyerId;
        this.PNR = builder.PNR;
        this.flightNumber = builder.flightNumber;
        this.flightClass = builder.flightClass;
        this.company = builder.company;
        this.origin = builder.origin;
        this.destination = builder.destination;
        this.status = builder.status;
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

    public String getStatus() {
        return status;
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
        return new FlightDTO(new ObjectId(), this.fplSEQ, this.flightDate, this.flyerId, this.PNR, this.flightNumber,
                this.flightClass, this.company, this.origin, this.destination, this.status);
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
        private String status;

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
            this.flightDate = flightDate;
            return this;
        }

        public FlightBuilder setFlyerID(String flyerID) {
            this.flyerId = flyerID;
            return this;
        }

        public FlightBuilder setPNR(String PNR) {
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

        public FlightBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Flight build() {
            return new Flight(this);
        }
    }
}
