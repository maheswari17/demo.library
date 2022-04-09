package com.library.member.service;
import com.library.exceptions.CustomNotFoundException.MemberNotFoundException;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;
import com.library.member.repository.MemberRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    public MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
       Member member= memberRepository.save(convertDtoToEntity(memberDto));
        return convertEntityToDto(member);
    }

    @Override
    public MemberDto getMember(long id) {
        //return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException("member not found"));
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()) {
            return convertEntityToDto(member.get());
        }throw new MemberNotFoundException("member details not found");
    }

    @Override
    public void deleteMember(long id) {
        try {
            memberRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new MemberNotFoundException("member with id:" + id + " does not exist");
        }
    }

    private MemberDto convertEntityToDto(Member member)   {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(member.getMemberId());
        memberDto.setFirstName(member.getFirstName());
        memberDto.setLastName(member.getLastName());
        memberDto.setDepartment(member.getDepartment());
        memberDto.setEmail(member.getEmail());
        memberDto.setContactNo(member.getContactNo());
        return memberDto;
    }

    private Member convertDtoToEntity(MemberDto memberDto)  {
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setFirstName(memberDto.getFirstName());
        member.setLastName(memberDto.getLastName());
        member.setEmail(memberDto.getEmail());
        member.setDepartment(memberDto.getDepartment());
        member.setContactNo(memberDto.getContactNo());
        return member;
    }
}
