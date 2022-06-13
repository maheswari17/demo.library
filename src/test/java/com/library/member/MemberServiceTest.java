package com.library.member;

import com.library.member.dto.MemberDto;
import com.library.member.model.Member;
import com.library.member.repository.MemberRepository;
import com.library.member.service.MemberService;
import com.library.member.service.MemberServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MemberServiceTest {
    @InjectMocks
    MemberServiceImpl memberService;
    @Mock
    MemberRepository memberRepository;
    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void getAllMemberTest(){
        List<Member> list=new ArrayList<Member>();
        Member member1=new Member(41,"john","thomas","cse","john123@gmail.com", 999213423);
        Member member2=new Member(42,"navi","don","eee","navi12@gmail.com",884232899);

        list.add(member1);
        list.add(member2);
        when(memberRepository.findAll()).thenReturn(list);
        assertEquals(2,memberService.getAllMembers().size());

    }

     @Test
    public void saveMemberTest(){
        Member member=new Member(43,"villu","mani","ece","villu@gmail.com",997839978);
        when(memberRepository.save(member)).thenReturn(member);
        // Member member1 = memberRepository.save(member);
         assertThat(member.getFirstName()).isNotNull();
        assertEquals(member,memberService.saveMember(member));
    }

    @Test
    public  void getMemberByIdTest() throws Exception{
        List<Member> list=new ArrayList<Member>();
        Member member1=new Member(1,"john","thomas","cse","john123@gmail.com", 999213423);
        Member member2=new Member(2,"navi","don","eee","navi12@gmail.com",884232899);
        int memberId=1;
        Mockito.when(memberRepository.findById(1l)).thenReturn(Optional.ofNullable(member1));
        assertEquals(memberId,memberService.getMember(memberId).getMemberId());
    }

    @Test
    public void deleteMemberTest(){
        Member member=new Member(1,"john","thomas","cse","john123@gmail.com", 9992134231l);
        memberService.deleteMember(member.getMemberId());
        verify(memberRepository,times(1)).deleteById(member.getMemberId());
    }
}
