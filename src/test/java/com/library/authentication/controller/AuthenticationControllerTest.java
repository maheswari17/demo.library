package com.library.authentication.controller;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.authentication.dto.AuthenticationDto;
import com.library.authentication.service.AuthenticationServiceImpl;
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

@ContextConfiguration(classes = {AuthenticationController.class})
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {
    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private AuthenticationServiceImpl authenticationServiceImpl;

    @Test
    void testGet() throws Exception {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setLoginId(123L);
        authenticationDto.setPassword("aaa");
        when(this.authenticationServiceImpl.getAuthentication(anyLong())).thenReturn(authenticationDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/authentication/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"loginId\":123,\"password\":\"aaa\"}"));
    }


    @Test
    void testGetAll() throws Exception {
        when(this.authenticationServiceImpl.getAllAuthentications()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/authentication");
        MockMvcBuilders.standaloneSetup(this.authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

   @Test
    void testAdd() throws Exception {
        when(this.authenticationServiceImpl.getAllAuthentications()).thenReturn(new ArrayList<>());

        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setLoginId(123L);
        authenticationDto.setPassword("aaa");
        String content = (new ObjectMapper()).writeValueAsString(authenticationDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testDelete() throws Exception {
        doNothing().when(this.authenticationServiceImpl).deleteAuthentication(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/authentication/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.authenticationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}

