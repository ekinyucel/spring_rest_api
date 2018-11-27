package com.amadeus.ist.springrest.member;

import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberDTO> retrieveMember(Query query);

    DeleteResult deleteMember(Query query);
}
