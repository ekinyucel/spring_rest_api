package com.amadeus.ist.springrest.member.impl;

import com.amadeus.ist.springrest.member.MemberDTO;
import com.amadeus.ist.springrest.member.MemberRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final MongoOperations mongoOperations;

    @Autowired
    public MemberRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<MemberDTO> retrieveMember(String flyerID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("flyerID").is(flyerID));
        return mongoOperations.find(query, MemberDTO.class);
    }
}
