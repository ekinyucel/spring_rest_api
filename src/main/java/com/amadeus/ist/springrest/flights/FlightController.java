package com.amadeus.ist.springrest.flights;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @Async
    @GetMapping(value = "/flights")
    public CompletableFuture<ResponseEntity<List<Document>>> retrieveFlights(@RequestParam(value = "date") final String date) {
        return CompletableFuture.supplyAsync(
                () -> flightService.retrieveFlightNumbers(date))
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                    }
                    return ResponseEntity.ok(result);
                });
    }

    @Async
    @GetMapping(value = "/flightDetails")
    public CompletableFuture<ResponseEntity<List<Document>>> retrievePassengerDetails(@RequestParam(value = "date") final String date,
                                                                                      @RequestParam(value = "fltNumber") final String flightNumber) {
        return CompletableFuture.supplyAsync(
                () -> flightService.retrievePassengerDetails(date, flightNumber))
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                    }
                    return ResponseEntity.ok(result);
                });
    }
}
