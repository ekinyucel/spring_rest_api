package com.amadeus.ist.springrest.member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberDTO> retrieveMember(String flyerID);
}
