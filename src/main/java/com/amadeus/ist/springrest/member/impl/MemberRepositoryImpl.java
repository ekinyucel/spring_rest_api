package com.amadeus.ist.springrest.member.impl;

import com.amadeus.ist.springrest.member.MemberDTO;
import com.amadeus.ist.springrest.member.MemberRepositoryCustom;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final MongoOperations mongoOperations;

    @Autowired
    public MemberRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<MemberDTO> retrieveMember(Query query) {
        return mongoOperations.find(query, MemberDTO.class);
    }

    @Override
    public DeleteResult deleteMember(Query query) {
        return mongoOperations.remove(query);
    }
}
