package com.library.member.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.member.dto.MemberDto;
import com.library.member.service.MemberServiceImpl;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MemberController.class})
@ExtendWith(SpringExtension.class)
class MemberControllerTest {
    @Autowired
    private MemberController memberController;

    @MockBean
    private MemberServiceImpl memberServiceImpl;

    @Test
    void testGetAllMembers() throws Exception {
        when(this.memberServiceImpl.getAllMembers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/members");
        MockMvcBuilders.standaloneSetup(this.memberController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGet() throws Exception {
        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(9988776655L);
        memberDto.setDepartment("eee");
        memberDto.setEmail("vini@gmail.com");
        memberDto.setFirstName("vini");
        memberDto.setLastName("mahesh");
        memberDto.setMemberId(123L);
        when(this.memberServiceImpl.getMember(anyLong())).thenReturn(memberDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/members/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.memberController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"memberId\":123,\"firstName\":\"vini\",\"lastName\":\"mahesh\",\"department\":\"eee\",\"email\":\"vini@gmail"
                                        + ".com\",\"contactNo\":9988776655}"));
    }

    @Test
    void testAdd() throws Exception {
        when(this.memberServiceImpl.getAllMembers()).thenReturn(new ArrayList<>());

        MemberDto memberDto = new MemberDto();
        memberDto.setContactNo(9911223344L);
        memberDto.setDepartment("ece");
        memberDto.setEmail("avi@gmail.com");
        memberDto.setFirstName("avi");
        memberDto.setLastName("vardhan");
        memberDto.setMemberId(123L);
        String content = (new ObjectMapper()).writeValueAsString(memberDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.memberController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

   
    @Test
    void testDelete() throws Exception {
        doNothing().when(this.memberServiceImpl).deleteMember(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/members/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.memberController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

