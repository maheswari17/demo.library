package com.library.member.service;
import com.library.exceptions.CustomNotFoundException.MemberNotFoundException;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;
import com.library.member.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;
    private ModelMapper modelMapper;

    public MemberServiceImpl(MemberRepository memberRepository,ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<MemberDto> getAllMembers() {
        return memberRepository.findAll().stream().map(this::buildMemberDto).collect(Collectors.toList());
    }

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
       Member member= memberRepository.save(buildMember(memberDto));
        return buildMemberDto(member);
    }

    @Override
    public MemberDto getMember(long id) {
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()) {
            return buildMemberDto(member.get());
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

    public MemberDto buildMemberDto(Member member)   {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        MemberDto memberDto = modelMapper.map(member,MemberDto.class);
        return memberDto;
   }

    public Member buildMember(MemberDto memberDto)  {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Member member = modelMapper.map(memberDto,Member.class);
        return member;
   }

}
