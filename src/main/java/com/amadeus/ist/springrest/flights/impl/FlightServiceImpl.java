package com.amadeus.ist.springrest.flights.impl;

import com.amadeus.ist.springrest.flights.Flight;
import com.amadeus.ist.springrest.flights.FlightRepository;
import com.amadeus.ist.springrest.flights.FlightService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
class FlightServiceImpl implements FlightService {
    private final static String oollection = "full_passenger_list";
    private final List<Flight> flightList;

    private final MongoTemplate mongoTemplate;

    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(MongoTemplate mongoTemplate, FlightRepository flightRepository) {
        flightList = new CopyOnWriteArrayList<>();
        this.mongoTemplate = mongoTemplate;
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Document> retrievePassengerDetails(String flightDate, String flightNumber) {
        // TODO: add better parameter control before passing them through the service.
        if (!flightDate.isEmpty() && !flightNumber.isEmpty()) {
            MatchOperation matchDate = Aggregation.match(new Criteria("FLT_DATE").is(flightDate));
            MatchOperation matchFlightNumber = Aggregation.match(new Criteria("FLT_NUMBER").is(flightNumber));
            ProjectionOperation omittingFields = Aggregation.project().andExclude("_id");

            Aggregation aggregation = Aggregation.newAggregation(matchDate, matchFlightNumber, omittingFields);
            return aggregationResults(aggregation);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Document> retrieveFlightNumbers(String flightDate) {
        MatchOperation matchStage = Aggregation.match(new Criteria("FLT_DATE").is(flightDate));
        GroupOperation groupStage = Aggregation.group("FLT_NUMBER").count().as("flightCount");

        Aggregation aggregation = Aggregation.newAggregation(matchStage, groupStage);
        return aggregationResults(aggregation);
    }

    private List<Document> aggregationResults(Aggregation aggregation) {
        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, oollection, Document.class);
        return result.getMappedResults();
    }
}
