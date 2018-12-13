package com.amadeus.ist.springrest.flights.impl;

import com.amadeus.ist.springrest.flights.FlightDTO;
import com.amadeus.ist.springrest.flights.FlightRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class FlightRepositoryImpl implements FlightRepositoryCustom {
    private final MongoOperations mongoOperations;

    @Autowired
    public FlightRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<FlightDTO> retrieveMembers(Query query) {
        return mongoOperations.find(query, FlightDTO.class);
    }
}
