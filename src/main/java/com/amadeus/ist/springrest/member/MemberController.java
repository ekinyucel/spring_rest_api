package com.amadeus.ist.springrest.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@RestController
class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;
    private final AtomicLong counter = new AtomicLong(0);

    @Async
    @RequestMapping(value = "/retrieveMembers", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<Member>>> retrieveMembers() {
        logger.info("Request ID " + counter.incrementAndGet());
        long startTime = System.nanoTime();

        List<Member> resultList = memberService.retrieveMembers();

        long endTime = System.nanoTime();

        logger.info("total time " + (endTime - startTime) / 1e6);

        logger.info("Thread ID " + Thread.currentThread().getId() + "\n");

        return CompletableFuture.supplyAsync(
                () -> resultList)
                .thenApply(result -> ResponseEntity.ok(resultList))
                .exceptionally(e -> {
                    e.getCause();
                    return null;
                });
    }

    @Async
    @RequestMapping(value = "/retrieveMember/{flyerID}", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<Member>>> retrieveMember(@PathVariable final String flyerID) {
        logger.info("Request ID " + counter.incrementAndGet());
        long startTime = System.nanoTime();

        List<Member> member = memberService.retrieveMember(flyerID);

        long endTime = System.nanoTime();

        logger.info("total time " + (endTime - startTime) / 1e6);

        logger.info("Thread ID " + Thread.currentThread().getId() + "\n");

        return CompletableFuture.supplyAsync(
                () -> member)
                .thenApply(result -> ResponseEntity.ok(member))
                .exceptionally(e -> {
                    e.getCause();
                    return null;
                });
    }

    @Async
    @PostMapping("/enrollMember")
    public CompletableFuture<ResponseEntity<Boolean>> enrollMember(@RequestBody Member member, @RequestHeader HttpHeaders headers) {
        logger.info("headers " + headers);
        logger.info("Request ID " + counter.incrementAndGet());
        logger.info("Thread ID " + Thread.currentThread().getId() + "\n");
        boolean result = memberService.enrollMember(member);

        return CompletableFuture.supplyAsync(() -> result).thenApply(response -> ResponseEntity.ok((result == true) ? true : false));
    }

    @PutMapping("/updateMember")
    public ResponseEntity<Boolean> updateMember(@RequestBody Member member) {
        boolean result = memberService.updateMember(member);

        logger.info("Request ID " + counter.incrementAndGet());
        logger.info("Thread ID " + Thread.currentThread().getId() + "\n");

        if (result)
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/deleteMember/{flyerID}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteMember(@PathVariable final String flyerID){
        boolean result = memberService.deleteMember(flyerID);

        logger.info("Request ID " + counter.incrementAndGet());
        logger.info("Thread ID " + Thread.currentThread().getId() + "\n");

        if (result)
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
