package com.amadeus.ist.springrest.member.impl;

import com.amadeus.ist.springrest.member.Member;
import com.amadeus.ist.springrest.member.MemberDTO;
import com.amadeus.ist.springrest.member.MemberRepository;
import com.amadeus.ist.springrest.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
class MemberServiceImpl implements MemberService {
    private final List<Member> memberList;
    private Predicate<Member> searchPredicate;

    @Autowired
    private MemberRepository memberRepository;

    public MemberServiceImpl(){
        /*Member member = Member.MemberBuilder.newInstance()
                .setObjectId(new ObjectId("5bfb0d500000000000000000")).setFlyerID("1000004587456").setName("Ali").setLastname("Veli").setFlyerPoints(400).setStatus("AC").build();
        Member member2 = Member.MemberBuilder.newInstance()
                .setObjectId(new ObjectId("5bfb0d500000000000000000")).setFlyerID("1000009561558").setName("Mehmet").setLastname("Yeter").setFlyerPoints(800).setStatus("IA").build();
        */
        memberList = new CopyOnWriteArrayList<>();
        /*memberList.add(member);
        memberList.add(member2);*/
    }

    @Override
    public List<Member> retrieveMembers() {
        List<MemberDTO> memberDTOS = memberRepository.findAll();
        return memberDTOS.stream().map(MemberDTO::toConvertMember).collect(Collectors.toList());
    }

    @Override
    public List<Member> retrieveMember(String flyerID) {
        if (!flyerID.equals(null)) {
            return memberRepository.retrieveMember(flyerID).stream()
                    .map(MemberDTO::toConvertMember)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public boolean enrollMember(Member member) {
        if (member.equals(null) || (member.getFlyerID().equals(null) && member.getName().equals(null)))
            return false;
        searchPredicate = m -> m.equals(member);
        if (!filter(memberList, searchPredicate).isEmpty())
            return false;

        memberRepository.save(member.toConvertMemberDTO());
        return true;
    }

    @Override
    public boolean updateMember(Member member) {
        if (member.equals(null))
            return false;
        searchPredicate = m -> m.getFlyerID().equals(member.getFlyerID());
        if (filter(memberList, searchPredicate) == null)
            return false;

        memberList.removeIf(m -> m.getFlyerID().equals(member.getFlyerID()));
        memberList.add(member);
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
