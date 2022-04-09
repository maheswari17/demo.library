package com.library.member.controller;
import com.library.member.dto.MemberDto;
import com.library.member.service.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    MemberServiceImpl memberService;

    public MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public List<MemberDto> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> get(@PathVariable Integer id) {
        return new ResponseEntity<>(memberService.getMember(id),HttpStatus.OK);
    }

    @PostMapping("/")
    public MemberDto add(@RequestBody MemberDto memberDto) {
        return memberService.saveMember(memberDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(reason = "member deleted",code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        memberService.deleteMember(id);
    }

}
