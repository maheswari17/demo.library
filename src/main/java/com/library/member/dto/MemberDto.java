package com.library.member.dto;
import com.library.book.dto.BookDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto   {
    private long memberId;
    private String firstName;
    private String lastName;
    private String department;
    private String email;
    private long ContactNo;


}
