package com.library.member;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.library.exceptions.CustomNotFoundException.MemberNotFoundException;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;
import com.library.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.Optional;
import com.library.member.service.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MemberServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MemberServiceImplTest {
    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberServiceImpl memberServiceImpl;

    @Test
    void testGetAllMembers() {
        when(this.memberRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.memberServiceImpl.getAllMembers().isEmpty());
        verify(this.memberRepository).findAll();
    }

    @Test
    void testGetAllMembers2() {
        Member member = new Member();
        member.setContactNo(9911223344L);
        member.setDepartment("EEE");
        member.setEmail("mani@gmail.com");
        member.setFirstName("mani");
        member.setLastName("kumar");
        member.setMemberId(123L);

        Member member1 = new Member();
        member1.setContactNo(9988776655L);
        member1.setDepartment("CSE");
        member1.setEmail("hari@gmail.com");
        member1.setFirstName("hari");
        member1.setLastName("kumar");
        member1.setMemberId(123L);

        ArrayList<Member> memberList = new ArrayList<>();
        memberList.add(member1);
        memberList.add(member);
        when(this.memberRepository.findAll()).thenReturn(memberList);
        assertEquals(2, this.memberServiceImpl.getAllMembers().size());
        verify(this.memberRepository).findAll();
    }

    @Test
    void testSaveMember() {
        Member member = new Member();
        member.setContactNo(9922334455L);
        member.setDepartment("CSE");
        member.setEmail("vishnu@gmail.com");
        member.setFirstName("vishnu");
        member.setLastName("kumar");
        member.setMemberId(123L);
        when(this.memberRepository.save((Member) any())).thenReturn(member);

        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(9922334455L);
        memberDto.setDepartment("CSE");
        memberDto.setEmail("vishnu@gmail.com");
        memberDto.setFirstName("vishnu");
        memberDto.setLastName("kumar");
        memberDto.setMemberId(123L);
        MemberDto actualSaveMemberResult = this.memberServiceImpl.saveMember(memberDto);
        assertEquals(9922334455L, actualSaveMemberResult.getContactNo());
        assertEquals(123L, actualSaveMemberResult.getMemberId());
        assertEquals("kumar", actualSaveMemberResult.getLastName());
        assertEquals("vishnu", actualSaveMemberResult.getFirstName());
        assertEquals("vishnu@gmail.com", actualSaveMemberResult.getEmail());
        assertEquals("CSE", actualSaveMemberResult.getDepartment());
        verify(this.memberRepository).save((Member) any());
    }


    @Test
    void testGetMember() {
        Member member = new Member();
        member.setContactNo(9988776655L);
        member.setDepartment("EEE");
        member.setEmail("ravi@gmail.com");
        member.setFirstName("ravi");
        member.setLastName("kumar");
        member.setMemberId(123L);
        Optional<Member> ofResult = Optional.of(member);
        when(this.memberRepository.findById((Long) any())).thenReturn(ofResult);
        MemberDto actualMember = this.memberServiceImpl.getMember(123L);
        assertEquals(9988776655L, actualMember.getContactNo());
        assertEquals(123L, actualMember.getMemberId());
        assertEquals("kumar", actualMember.getLastName());
        assertEquals("ravi", actualMember.getFirstName());
        assertEquals("ravi@gmail.com", actualMember.getEmail());
        assertEquals("EEE", actualMember.getDepartment());
        verify(this.memberRepository).findById((Long) any());
    }

    @Test
    void testGetMember2() {
        when(this.memberRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(MemberNotFoundException.class, () -> this.memberServiceImpl.getMember(123L));
        verify(this.memberRepository).findById((Long) any());
    }

    @Test
    void testDeleteMember() {
        doNothing().when(this.memberRepository).deleteById((Long) any());
        this.memberServiceImpl.deleteMember(123L);
        verify(this.memberRepository).deleteById((Long) any());
        assertTrue(this.memberServiceImpl.getAllMembers().isEmpty());
    }

    @Test
    void testDeleteMember2() {
        doThrow(new EmptyResultDataAccessException(3)).when(this.memberRepository).deleteById((Long) any());
        assertThrows(MemberNotFoundException.class, () -> this.memberServiceImpl.deleteMember(123L));
        verify(this.memberRepository).deleteById((Long) any());
    }
}

