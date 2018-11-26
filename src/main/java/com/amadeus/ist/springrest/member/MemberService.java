package com.amadeus.ist.springrest.member;

import com.amadeus.ist.springrest.config.interfaces.Service;

import java.util.List;

public interface MemberService extends Service {
    List<Member> retrieveMembers();
    List<Member> retrieveMember(String flyerID);

    boolean enrollMember(Member member);
    boolean updateMember(Member member);
    boolean deleteMember(String flyerID);
}
