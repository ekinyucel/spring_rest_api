package com.amadeus.ist.springrest.flights;

import com.amadeus.ist.springrest.config.interfaces.Service;
import org.bson.Document;

import java.util.List;

public interface FlightService extends Service {
    List<Flight> retrieveFlights(String flightDate);

    List<Document> retrieveFlightNumbers(String flightDate);
}
