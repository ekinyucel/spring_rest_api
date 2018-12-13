package com.amadeus.ist.springrest.flights;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepositoryCustom {
    List<FlightDTO> retrieveMembers(Query query);
}
