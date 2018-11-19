package com.amadeus.ist.springrest.member;

import com.amadeus.ist.springrest.config.interfaces.Service;

import java.util.List;
import java.util.Optional;

public interface MemberService extends Service {
    List<Member> retrieveMembers();
    List<Member> retrieveMember(String flyerID);
    boolean insertMember(Member member);
    boolean updateMember(Member member);
    boolean deleteMember(String flyerID);
}
