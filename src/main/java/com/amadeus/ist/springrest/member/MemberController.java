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
    private static final String REQUEST_ID = "Request ID {} ";
    private static final String THREAD_ID = "Thread ID {}  \n";

    private final MemberService memberService;
    private final AtomicLong counter = new AtomicLong(0);

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Async
    @GetMapping(value = "/retrieveMembers")
    public CompletableFuture<ResponseEntity<List<Member>>> retrieveMembers() {
        long threadId = Thread.currentThread().getId();
        logger.info("Thread ID {} Request ID {} ", threadId, counter.incrementAndGet());
        long startTime = System.nanoTime();

        return CompletableFuture.supplyAsync(
                memberService::retrieveMembers)
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                    }
                    long endTime = System.nanoTime();
                    logger.info("Thread ID: {} total time {} ", threadId, (endTime - startTime) / 1e6);
                    return ResponseEntity.ok(result);
                });
    }

    @Async
    @GetMapping(value = "/retrieveMember/{flyerID}")
    public CompletableFuture<ResponseEntity<List<Member>>> retrieveMember(@PathVariable final String flyerID) {
        long threadId = Thread.currentThread().getId();
        logger.info(REQUEST_ID, counter.incrementAndGet());
        long startTime = System.nanoTime();

        logger.info(THREAD_ID, Thread.currentThread().getId());

        return CompletableFuture.supplyAsync(
                () -> memberService.retrieveMember(flyerID))
                .handle((result, ex) -> {
                    if (ex != null) {
                        ex.getCause();
                        logger.info(ex.getMessage());
                        return null;
                    }
                    long endTime = System.nanoTime();
                    logger.info("Thread ID: {} total time {} ", threadId, (endTime - startTime) / 1e6);
                    return ResponseEntity.ok(result);
                });
    }

    @Async
    @PostMapping("/enrollMember")
    public CompletableFuture<ResponseEntity<Boolean>> enrollMember(@RequestBody Member member, @RequestHeader HttpHeaders headers) {
        logger.info(REQUEST_ID, counter.incrementAndGet());
        logger.info(THREAD_ID, Thread.currentThread().getId());

        return CompletableFuture.supplyAsync(
                () -> memberService.enrollMember(member))
                .thenApply(ResponseEntity::ok);
    }

    @SuppressWarnings("Duplicates")
    @Async
    @PutMapping("/updateMember")
    public CompletableFuture<ResponseEntity<Boolean>> updateMember(@RequestBody Member member) {
        long threadId = Thread.currentThread().getId();
        logger.info(REQUEST_ID, counter.incrementAndGet());
        logger.info(THREAD_ID, Thread.currentThread().getId());
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
                        logger.info("Thread ID: {} total time {} ", threadId, (endTime - startTime) / 1e6);
                        return ResponseEntity.ok(true);

                    } else {
                        return null;
                    }
                });
    }

    @SuppressWarnings("Duplicates")
    @Async
    @DeleteMapping(value = "/deleteMember/{flyerID}")
    public CompletableFuture<ResponseEntity<Boolean>> deleteMember(@PathVariable final String flyerID) {
        long threadId = Thread.currentThread().getId();
        logger.info(REQUEST_ID, counter.incrementAndGet());
        logger.info(THREAD_ID, Thread.currentThread().getId());
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
                        logger.info("Thread ID: {} total time {} ", threadId, (endTime - startTime) / 1e6);
                        return ResponseEntity.ok(true);

                    } else {
                        return null;
                    }
                });

    }
}
