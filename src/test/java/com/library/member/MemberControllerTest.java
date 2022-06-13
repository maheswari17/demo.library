package com.library.member;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.member.controller.MemberController;
import com.library.member.dto.MemberDto;
import com.library.member.model.Member;
import com.library.member.service.MemberServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.reflect.Array.get;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@RequestMapping("members")
public class MemberControllerTest {

    private MockMvc mockMvc;
    @MockBean
    MemberServiceImpl memberService;

    @InjectMocks
    MemberController memberController;

    @Test
    public void getAllMembersTest() throws Exception {
        Member member1 = new Member(41, "john", "thomas", "cse", "john123@gmail.com", 999213423);
        Member member2 = new Member(42, "navi", "don", "eee", "navi12@gmail.com", 884232899);

        List<Member> members = new ArrayList<>(Arrays.asList(member1, member2));

        //Mockito.when(memberService.getAllMembers()).thenReturn(invocationOnMock->members);
        doReturn(members).when(memberService).getAllMembers();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/member")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[2].name", is("navi don")));
    }

    @Test
    public void getMemberTest() throws Exception {
        Member member = new Member();
        //given(memberController.get(1)).willReturn(member);
        doReturn(member).when(memberService).getMember(1);
        mockMvc.perform(MockMvcRequestBuilders.get("/member/id" + member.getMemberId()).contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("name", is(member.getFirstName())));
    }

    @Test
    public void saveMemberTest() throws Exception {
        Member member = new Member(41, "john", "thomas", "cse", "john123@gmail.com", 999213423);
        Mockito.when(memberService.saveMember(ArgumentMatchers.any())).thenReturn(member);
       // String json = mapper.writeValueAsString(member);
        mockMvc.perform((RequestBuilder) post("/member").contentType(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.id", Matchers.equalTo(41)))
                .andExpect((ResultMatcher) jsonPath("$.name", Matchers.equalTo("john")));
    }

    @Test
    public void deleteMemberTest() throws Exception {
        Member member=new Member(1,"john","thomas","cse","john123@gmail.com", 999213423);
       // Mockito.when(memberService.deleteMember(ArgumentMatchers.any(1))).thenReturn("member is deleted");
       doReturn("member is deleted").when(memberService).deleteMember(1);
        MvcResult requestResult = mockMvc.perform(delete("/deleteMapping").param("member-id", "1"))
                .andExpect(status().isOk()).andExpect(status().isOk()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        Assertions.assertEquals(result, "member is deleted");

    }

}



