package com.library.authentication.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AuthenticationDtoTest {

    @Test
    void testConstructor() {
        AuthenticationDto actualAuthenticationDto = new AuthenticationDto();
        actualAuthenticationDto.setLoginId(123L);
        actualAuthenticationDto.setPassword("aaa");
        assertEquals(123L, actualAuthenticationDto.getLoginId());
        assertEquals("aaa", actualAuthenticationDto.getPassword());
    }
}

