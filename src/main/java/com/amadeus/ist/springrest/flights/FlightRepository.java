package com.amadeus.ist.springrest.flights;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends MongoRepository<FlightDTO, String>, FlightRepositoryCustom {
}
