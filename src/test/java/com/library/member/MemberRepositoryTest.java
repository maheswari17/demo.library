package com.library.member;

import com.library.member.model.Member;
import com.library.member.repository.MemberRepository;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void saveMemberTest(){
        Member member=new Member(1,"villu","mani","ece","villu@gmail.com",997839978);
        memberRepository.save(member);
        assertThat(member.getMemberId()).isGreaterThan(0);   }

    @Test
    public void getMemberByIdTest() {
        Optional<Member> member = memberRepository.findById(1l);
        assertThat(member.get()).isEqualTo(1);
    }

    @Test
    public void getAllMembersTest() {
        Member member = new Member();
        memberRepository.save(member);
        List<Member> result = new ArrayList<>();
        memberRepository.findAll().forEach(e -> result.add(e));
        assertEquals(result.size(), 1);
        //List<Member> memberList = (List<Member>) memberRepository.findAll();
        //assertThat(memberList).size().isGreaterThan(0);
    }

    @Test
    @Rollback(false)
    public void deleteTest() {
        Optional<Member> member = memberRepository.findById(2l);
        memberRepository.deleteById(member.get().getMemberId());
        Optional<Member> deletedProduct = memberRepository.findById(2l);
        assertThat(deletedProduct).isNull();

    }

}
