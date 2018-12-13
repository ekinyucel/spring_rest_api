package com.amadeus.ist.springrest.flights.impl;

import com.amadeus.ist.springrest.flights.Flight;
import com.amadeus.ist.springrest.flights.FlightDTO;
import com.amadeus.ist.springrest.flights.FlightRepository;
import com.amadeus.ist.springrest.flights.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
class FlightServiceImpl implements FlightService {
    private final List<Flight> flightList;

    @Autowired
    private FlightRepository flightRepository;

    public FlightServiceImpl() {
        flightList = new CopyOnWriteArrayList<>();
    }

    @Override
    public List<Flight> retrieveFlights(String flightDate) {
        if (!flightDate.equals(null)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("FLT_DATE").is(flightDate));
            return flightRepository.retrieveMembers(query).stream()
                    .map(FlightDTO::toConvertMember)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
