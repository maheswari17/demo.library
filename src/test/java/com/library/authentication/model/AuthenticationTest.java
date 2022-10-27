package com.library.authentication.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AuthenticationTest {

    @Test
    void testConstructor() {
        Authentication actualAuthentication = new Authentication();
        actualAuthentication.setLoginId(123L);
        actualAuthentication.setPassword("aaa");
        assertEquals(123L, actualAuthentication.getLoginId());
        assertEquals("aaa", actualAuthentication.getPassword());
    }


    @Test
    void testConstructor2() {
        Authentication actualAuthentication = new Authentication(123L, "aaa");

        assertEquals(123L, actualAuthentication.getLoginId());
        assertEquals("aaa", actualAuthentication.getPassword());
    }
}

