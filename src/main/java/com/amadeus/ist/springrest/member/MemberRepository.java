package com.amadeus.ist.springrest.member;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<MemberDTO, String>, MemberRepositoryCustom {
}
