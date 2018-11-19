package com.amadeus.ist.springrest.member;

import com.amadeus.ist.springrest.config.model.Error;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        long startTime = System.nanoTime();
        List<Member> resultList = memberService.retrieveMembers();

        logger.info("Request ID " + counter.incrementAndGet());
        long endTime = System.nanoTime();
        logger.info("total time " + (endTime - startTime) / 1e6);

        logger.info("Thread ID " + Thread.currentThread().getId());
        logger.info("Members " + resultList.size());

        return CompletableFuture.supplyAsync(
                () -> resultList)
                .thenApply(result -> ResponseEntity.ok(resultList))
                .exceptionally(e -> {
                    e.getCause();
                    return null;
                });
    }

    @RequestMapping(value = "/retrieveMember/{flyerID}", method = RequestMethod.GET)
    public ResponseEntity<Object> retrieveMember(@PathVariable final String flyerID) {
        List<Member> member = memberService.retrieveMember(flyerID);

        if (!member.isEmpty()) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        }
        else {
            Error error = new Error("member not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/enrollMember")
    public ResponseEntity<Boolean> enrollMember(@RequestBody Member member){
        boolean result = memberService.insertMember(member);

        if (result)
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/updateMember")
    public ResponseEntity<Boolean> updateMember(@RequestBody Member member) {
        boolean result = memberService.updateMember(member);

        if (result)
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/deleteMember/{flyerID}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteMember(@PathVariable final String flyerID){
        boolean result = memberService.deleteMember(flyerID);

        if (result)
            return new ResponseEntity<>(true, HttpStatus.OK);

        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
