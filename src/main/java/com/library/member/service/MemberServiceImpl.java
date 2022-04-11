package com.library.member.service;
import com.library.exceptions.CustomNotFoundException.MemberNotFoundException;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;
import com.library.member.repository.MemberRepository;
import com.library.utils.DtoConverter;
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
        return memberRepository.findAll().stream().map(DtoConverter::buildMemberDto).collect(Collectors.toList());
    }

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
       Member member= memberRepository.save(DtoConverter.buildMember(memberDto));
        return DtoConverter.buildMemberDto(member);
    }

    @Override
    public MemberDto getMember(long id) {
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()) {
            return DtoConverter.buildMemberDto(member.get());
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


}
