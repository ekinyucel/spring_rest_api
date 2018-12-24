package com.amadeus.ist.springrest.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@RestController
class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;
    private final AtomicLong counter = new AtomicLong(0);

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Async
    @RequestMapping(value = "/retrieveMembers", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<Member>>> retrieveMembers() {
        long threadId = Thread.currentThread().getId();
        logger.info("Thread ID: " + threadId + " Request ID " + counter.incrementAndGet());
        long startTime = System.nanoTime();

        return CompletableFuture.supplyAsync(
                () -> memberService.retrieveMembers())
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                    }
                    long endTime = System.nanoTime();
                    logger.info("Thread ID: " + threadId + " total time " + (endTime - startTime) / 1e6);
                    return ResponseEntity.ok(result);
                });
    }

    @Async
    @RequestMapping(value = "/retrieveMember/{flyerID}", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<Member>>> retrieveMember(@PathVariable final String flyerID) {
        long threadId = Thread.currentThread().getId();
        logger.info("Thread ID: " + threadId + " Request ID " + counter.incrementAndGet());
        long startTime = System.nanoTime();

        logger.info("Thread ID " + threadId + "\n");

        return CompletableFuture.supplyAsync(
                () -> memberService.retrieveMember(flyerID))
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                        return null;
                    }
                    long endTime = System.nanoTime();
                    logger.info("Thread ID: " + threadId + " total time " + (endTime - startTime) / 1e6);
                    return ResponseEntity.ok(result);
                });
    }

    @Async
    @PostMapping("/enrollMember")
    public CompletableFuture<ResponseEntity<Boolean>> enrollMember(@RequestBody Member member, @RequestHeader HttpHeaders headers) {
        long threadId = Thread.currentThread().getId();
        logger.info("Thread ID: " + threadId + " Request ID " + counter.incrementAndGet());
        logger.info("Thread ID " + Thread.currentThread().getId() + "\n");

        return CompletableFuture.supplyAsync(
                () -> memberService.enrollMember(member))
                .thenApply(response -> ResponseEntity.ok(response));
    }

    @Async
    @PutMapping("/updateMember")
    public CompletableFuture<ResponseEntity<Boolean>> updateMember(@RequestBody Member member) {
        long threadId = Thread.currentThread().getId();
        logger.info("Request ID " + counter.incrementAndGet());
        logger.info("Thread ID " + Thread.currentThread().getId() + "\n");
        long startTime = System.nanoTime();

        return CompletableFuture.supplyAsync(
                () -> memberService.updateMember(member))
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                        return null;
                    }
                    if (result) {
                        long endTime = System.nanoTime();
                        logger.info("Thread ID: " + threadId + " total time " + (endTime - startTime) / 1e6);
                        return ResponseEntity.ok(result);

                    } else {
                        return null;
                    }
                });
    }

    @Async
    @RequestMapping(value = "/deleteMember/{flyerID}", method = RequestMethod.DELETE)
    public CompletableFuture<ResponseEntity<Boolean>> deleteMember(@PathVariable final String flyerID) {
        long threadId = Thread.currentThread().getId();
        logger.info("Request ID " + counter.incrementAndGet());
        logger.info("Thread ID " + Thread.currentThread().getId() + "\n");
        long startTime = System.nanoTime();

        return CompletableFuture.supplyAsync(
                () -> memberService.deleteMember(flyerID))
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                        return null;
                    }
                    if (result) {
                        long endTime = System.nanoTime();
                        logger.info("Thread ID: " + threadId + " total time " + (endTime - startTime) / 1e6);
                        return ResponseEntity.ok(result);

                    } else {
                        return null;
                    }
                });

    }
}
