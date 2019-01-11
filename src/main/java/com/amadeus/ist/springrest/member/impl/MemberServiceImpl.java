package com.amadeus.ist.springrest.member.impl;

import com.amadeus.ist.springrest.member.Member;
import com.amadeus.ist.springrest.member.MemberDTO;
import com.amadeus.ist.springrest.member.MemberRepository;
import com.amadeus.ist.springrest.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
class MemberServiceImpl implements MemberService {
    private final Logger logger = Logger.getLogger(MemberServiceImpl.class.getName());

    private final List<Member> memberList;
    private Predicate<Member> searchPredicate;

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        memberList = new CopyOnWriteArrayList<>();
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Member> retrieveMembers() {
        List<MemberDTO> memberDTOS = memberRepository.findAll();
        return memberDTOS.stream().map(MemberDTO::toConvertMember).collect(Collectors.toList());
    }

    @Override
    public List<Member> retrieveMember(String flyerID) {
        if (!flyerID.isEmpty()) {
            Query query = new Query();
            query.addCriteria(Criteria.where("flyerID").is(flyerID));
            return memberRepository.retrieveMember(query).stream()
                    .map(MemberDTO::toConvertMember)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean enrollMember(Member member) {
        if (member == null || (member.getFlyerID().isEmpty() && member.getName().isEmpty()))
            return false;
        searchPredicate = m -> m.equals(member);
        if (!filter(memberList, searchPredicate).isEmpty())
            return false;

        memberRepository.save(member.toConvertMemberDTO());
        return true;
    }

    @Override
    public boolean updateMember(Member member) {
        if (member == null)
            return false;
        searchPredicate = m -> m.getFlyerID().equals(member.getFlyerID());
        if (filter(memberList, searchPredicate) == null)
            return false;

        // TODO change the logic
        memberRepository.save(member.toConvertMemberDTO());
        return true;
    }

    @Override
    public boolean deleteMember(String flyerID) {
        if (flyerID.isEmpty())
            return false;

        Query query = new Query();
        query.addCriteria(Criteria.where("flyerID").is(flyerID));

        // TODO change the logic of checking whether member exist or not

        logger.log(Level.WARNING, () -> "test: deleteresult of removal - " + memberRepository.deleteMember(query));
        memberRepository.deleteMember(query);

        return true;
    }
}
