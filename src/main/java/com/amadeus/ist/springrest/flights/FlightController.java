package com.amadeus.ist.springrest.flights;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @Async
    @RequestMapping(value = "/retrieveFlights/{flightDate}", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<Flight>>> retrieveFlights(@PathVariable final String flightDate) {
        logger.info("flight date " + flightDate);
        return CompletableFuture.supplyAsync(
                () -> flightService.retrieveFlights(flightDate))
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                    }
                    return ResponseEntity.ok(result);
                });
    }
}
