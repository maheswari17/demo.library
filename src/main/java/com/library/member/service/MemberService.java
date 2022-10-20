package com.library.member.service;
import com.library.member.dto.MemberDto;
import java.util.List;

public interface MemberService {
    List<MemberDto> getAllMembers();
    MemberDto saveMember(MemberDto memberDto);
    MemberDto getMember(long id);
    void deleteMember(long id);
}
