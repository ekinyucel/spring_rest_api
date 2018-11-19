package com.amadeus.ist.springrest.member.impl;

import com.amadeus.ist.springrest.config.utils.Commons;
import com.amadeus.ist.springrest.member.Member;
import com.amadeus.ist.springrest.member.MemberService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
class MemberServiceImpl implements MemberService {
    private List<String> fieldList;
    private final List<Member> memberList;
    private Predicate<Member> searchPredicate;

    public MemberServiceImpl(){
        Member member = new Member("1000004587456", "Ali", "Veli", 400, "AC");
        Member member2 = new Member("1000009561558", "Mehmet", "Yeter", 800, "IA");
        memberList = new CopyOnWriteArrayList<>();
        memberList.add(member);
        memberList.add(member2);
    }

    @Override
    public List<Member> retrieveMembers() {

        return memberList;
    }

    @Override
    public List<Member> retrieveMember(String flyerID) {
        if (!flyerID.equals(null)) {
            searchPredicate = member -> member.getFlyerID().equals(flyerID);
            return filter(memberList, searchPredicate);
        }
        return null;
    }

    @Override
    public boolean insertMember(Member member) {
        if (member.equals(null))
            return false;
        searchPredicate = m -> m.getFlyerID().equals(member.getFlyerID());
        if (filter(memberList, searchPredicate) == null)
            return false;

        memberList.add(member);
        return true;
    }

    @Override
    public boolean updateMember(Member member) {
        if (member.equals(null))
            return false;
        searchPredicate = m -> m.getFlyerID().equals(member.getFlyerID());
        if (filter(memberList, searchPredicate) == null)
            return false;

        return true;
    }

    @Override
    public boolean deleteMember(String flyerID) {
        if (flyerID.equals(null))
            return false;
        searchPredicate = member -> member.getFlyerID().equals(flyerID);
        if (filter(memberList, searchPredicate) == null)
            return false;

        memberList.removeIf(member -> member.getFlyerID().equals(flyerID));

        return true;
    }
}
