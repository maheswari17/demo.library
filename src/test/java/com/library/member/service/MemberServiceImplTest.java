package com.library.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.library.exceptions.CustomNotFoundException.MemberNotFoundException;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;
import com.library.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.internal.InheritingConfiguration;
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

    @MockBean
    private ModelMapper modelMapper;


    @Test
    void testGetAllMembers() {
        when(this.memberRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.memberServiceImpl.getAllMembers().isEmpty());
        verify(this.memberRepository).findAll();
    }


    @Test
    void testGetAllMembers2() {
        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("cse");
        memberDto.setEmail("ram@gmail");
        memberDto.setFirstName("ram");
        memberDto.setLastName("kumar");
        memberDto.setMemberId(123L);
        when(this.modelMapper.map((Object) any(), (Class<MemberDto>) any())).thenReturn(memberDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("cse");
        member.setEmail("ram@gmail");
        member.setFirstName("ram");
        member.setLastName("kumar");
        member.setMemberId(123L);

        ArrayList<Member> memberList = new ArrayList<>();
        memberList.add(member);
        when(this.memberRepository.findAll()).thenReturn(memberList);
        assertEquals(1, this.memberServiceImpl.getAllMembers().size());
        verify(this.modelMapper).map((Object) any(), (Class<MemberDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.memberRepository).findAll();
    }

    @Test
    void testGetAllMembers3() {
        when(this.modelMapper.map((Object) any(), (Class<MemberDto>) any()))
                .thenThrow(new MemberNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("cse");
        member.setEmail("ram@gmail");
        member.setFirstName("ram");
        member.setLastName("kumar");
        member.setMemberId(123L);

        ArrayList<Member> memberList = new ArrayList<>();
        memberList.add(member);
        when(this.memberRepository.findAll()).thenReturn(memberList);
        assertThrows(MemberNotFoundException.class, () -> this.memberServiceImpl.getAllMembers());
        verify(this.modelMapper).map((Object) any(), (Class<MemberDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.memberRepository).findAll();
    }
    @Test
    void testSaveMember() {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new MemberNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("cse");
        memberDto.setEmail("venkat@gmail.com");
        memberDto.setFirstName("venkat");
        memberDto.setLastName("kumar");
        memberDto.setMemberId(123L);
        assertThrows(MemberNotFoundException.class, () -> this.memberServiceImpl.saveMember(memberDto));
        verify(this.modelMapper).map((Object) any(), (Class<Member>) any());
        verify(this.modelMapper).getConfiguration();
    }

    @Test
    void testSaveMember2() {
        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("Department");
        member.setEmail("jane.doe@example.org");
        member.setFirstName("Jane");
        member.setLastName("Doe");
        member.setMemberId(123L);
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(member);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Member member1 = new Member();
        member1.setContactNo(1L);
        member1.setDepartment("eee");
        member1.setEmail("janu@gmail.com");
        member1.setFirstName("janu");
        member1.setLastName("ram");
        member1.setMemberId(123L);
        when(this.memberRepository.save((Member) any())).thenReturn(member1);

        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("eee");
        memberDto.setEmail("janu@gmail.com");
        memberDto.setFirstName("janu");
        memberDto.setLastName("ram");
        memberDto.setMemberId(123L);
        assertSame(member, this.memberServiceImpl.saveMember(memberDto));
        verify(this.modelMapper, atLeast(1)).map((Object) any(), (Class<Object>) any());
        verify(this.modelMapper, atLeast(1)).getConfiguration();
        verify(this.memberRepository).save((Member) any());
    }

    @Test
    void testGetMember() {
        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("Department");
        memberDto.setEmail("jane.doe@example.org");
        memberDto.setFirstName("Jane");
        memberDto.setLastName("Doe");
        memberDto.setMemberId(123L);
        when(this.modelMapper.map((Object) any(), (Class<MemberDto>) any())).thenReturn(memberDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("Department");
        member.setEmail("jane.doe@example.org");
        member.setFirstName("Jane");
        member.setLastName("Doe");
        member.setMemberId(123L);
        Optional<Member> ofResult = Optional.of(member);
        when(this.memberRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(memberDto, this.memberServiceImpl.getMember(123L));
        verify(this.modelMapper).map((Object) any(), (Class<MemberDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.memberRepository).findById((Long) any());
    }

    @Test
    void testGetMember2() {
        when(this.modelMapper.map((Object) any(), (Class<MemberDto>) any()))
                .thenThrow(new MemberNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("Department");
        member.setEmail("jane.doe@example.org");
        member.setFirstName("Jane");
        member.setLastName("Doe");
        member.setMemberId(123L);
        Optional<Member> ofResult = Optional.of(member);
        when(this.memberRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(MemberNotFoundException.class, () -> this.memberServiceImpl.getMember(123L));
        verify(this.modelMapper).map((Object) any(), (Class<MemberDto>) any());
        verify(this.modelMapper).getConfiguration();
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
        doThrow(new MemberNotFoundException("Exception")).when(this.memberRepository).deleteById((Long) any());
        assertThrows(MemberNotFoundException.class, () -> this.memberServiceImpl.deleteMember(123L));
        verify(this.memberRepository).deleteById((Long) any());
    }


    @Test
    void testBuildMemberDto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("Department");
        memberDto.setEmail("jane.doe@example.org");
        memberDto.setFirstName("Jane");
        memberDto.setLastName("Doe");
        memberDto.setMemberId(123L);
        when(this.modelMapper.map((Object) any(), (Class<MemberDto>) any())).thenReturn(memberDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("Department");
        member.setEmail("jane.doe@example.org");
        member.setFirstName("Jane");
        member.setLastName("Doe");
        member.setMemberId(123L);
        assertSame(memberDto, this.memberServiceImpl.buildMemberDto(member));
        verify(this.modelMapper).map((Object) any(), (Class<MemberDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildMemberDto2() {
        when(this.modelMapper.map((Object) any(), (Class<MemberDto>) any()))
                .thenThrow(new MemberNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("Department");
        member.setEmail("jane.doe@example.org");
        member.setFirstName("Jane");
        member.setLastName("Doe");
        member.setMemberId(123L);
        assertThrows(MemberNotFoundException.class, () -> this.memberServiceImpl.buildMemberDto(member));
        verify(this.modelMapper).map((Object) any(), (Class<MemberDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildMember() {
        Member member = new Member();
        member.setContactNo(1L);
        member.setDepartment("Department");
        member.setEmail("jane.doe@example.org");
        member.setFirstName("Jane");
        member.setLastName("Doe");
        member.setMemberId(123L);
        when(this.modelMapper.map((Object) any(), (Class<Member>) any())).thenReturn(member);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("Department");
        memberDto.setEmail("jane.doe@example.org");
        memberDto.setFirstName("Jane");
        memberDto.setLastName("Doe");
        memberDto.setMemberId(123L);
        assertSame(member, this.memberServiceImpl.buildMember(memberDto));
        verify(this.modelMapper).map((Object) any(), (Class<Member>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildMember2() {
        when(this.modelMapper.map((Object) any(), (Class<Member>) any()))
                .thenThrow(new MemberNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(1L);
        memberDto.setDepartment("Department");
        memberDto.setEmail("jane.doe@example.org");
        memberDto.setFirstName("Jane");
        memberDto.setLastName("Doe");
        memberDto.setMemberId(123L);
        assertThrows(MemberNotFoundException.class, () -> this.memberServiceImpl.buildMember(memberDto));
        verify(this.modelMapper).map((Object) any(), (Class<Member>) any());
        verify(this.modelMapper).getConfiguration();
    }


}

