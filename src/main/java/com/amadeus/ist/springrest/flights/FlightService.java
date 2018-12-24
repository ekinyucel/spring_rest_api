package com.amadeus.ist.springrest.flights;

import com.amadeus.ist.springrest.config.interfaces.Service;
import org.bson.Document;

import java.util.List;

public interface FlightService extends Service {
    List<Document> retrievePassengerDetails(String flightDate, String flightNumber);
    List<Document> retrieveFlightNumbers(String flightDate);
}
