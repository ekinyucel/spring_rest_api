package com.amadeus.ist.springrest.flights.impl;

import com.amadeus.ist.springrest.flights.Flight;
import com.amadeus.ist.springrest.flights.FlightDTO;
import com.amadeus.ist.springrest.flights.FlightRepository;
import com.amadeus.ist.springrest.flights.FlightService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
class FlightServiceImpl implements FlightService {
    private final List<Flight> flightList;

    private final MongoTemplate mongoTemplate;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(MongoTemplate mongoTemplate) {
        flightList = new CopyOnWriteArrayList<>();
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Flight> retrieveFlights(String flightDate) {
        if (!flightDate.equals(null)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("FLT_DATE").is(flightDate));
            return flightRepository.retrieveMembers(query).stream()
                    .map(FlightDTO::toConvertMember)
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        }
        return null;
    }

    @Override
    public List<Document> retrieveFlightNumbers(String flightDate) {
        MatchOperation matchStage = Aggregation.match(new Criteria("FLT_DATE").is(flightDate));
        GroupOperation groupStage = Aggregation.group("FLT_NUMBER").count().as("flightCount");
        Aggregation aggregation = Aggregation.newAggregation(matchStage, groupStage);

        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, "full_passenger_list", Document.class);
        List<Document> document = result.getMappedResults();
        return document;
    }
}
