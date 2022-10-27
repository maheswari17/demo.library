package com.library.authentication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.library.authentication.dto.AuthenticationDto;
import com.library.authentication.model.Authentication;
import com.library.authentication.repository.AuthenticationRepository;
import com.library.exceptions.CustomNotFoundException.AuthenticationNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceImplTest {
    @MockBean
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @MockBean
    private ModelMapper modelMapper;


    @Test
    void testGetAllAuthentications() {
        when(this.authenticationRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.authenticationServiceImpl.getAllAuthentications().isEmpty());
        verify(this.authenticationRepository).findAll();
    }

    @Test
    void testGetAllAuthentications2() {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setLoginId(123L);
        authenticationDto.setPassword("aaa");
        when(this.modelMapper.map((Object) any(), (Class<AuthenticationDto>) any())).thenReturn(authenticationDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Authentication authentication = new Authentication();
        authentication.setLoginId(123L);
        authentication.setPassword("aaa");

        ArrayList<Authentication> authenticationList = new ArrayList<>();
        authenticationList.add(authentication);
        when(this.authenticationRepository.findAll()).thenReturn(authenticationList);
        assertEquals(1, this.authenticationServiceImpl.getAllAuthentications().size());
        verify(this.modelMapper).map((Object) any(), (Class<AuthenticationDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.authenticationRepository).findAll();
    }



    @Test
    void testAddAuthentication() {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new AuthenticationNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setLoginId(123L);
        authenticationDto.setPassword("aaa");
        assertThrows(AuthenticationNotFoundException.class,
                () -> this.authenticationServiceImpl.addAuthentication(authenticationDto));
        verify(this.modelMapper).map((Object) any(), (Class<Authentication>) any());
        verify(this.modelMapper).getConfiguration();
    }



    @Test
    void testGetAuthentication() {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setLoginId(123L);
        authenticationDto.setPassword("aaa");
        when(this.modelMapper.map((Object) any(), (Class<AuthenticationDto>) any())).thenReturn(authenticationDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Authentication authentication = new Authentication();
        authentication.setLoginId(123L);
        authentication.setPassword("aaa");
        Optional<Authentication> ofResult = Optional.of(authentication);
        when(this.authenticationRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(authenticationDto, this.authenticationServiceImpl.getAuthentication(123L));
        verify(this.modelMapper).map((Object) any(), (Class<AuthenticationDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.authenticationRepository).findById((Long) any());
    }


    @Test
    void testGetAuthentication2() {
        when(this.modelMapper.map((Object) any(), (Class<AuthenticationDto>) any()))
                .thenThrow(new AuthenticationNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Authentication authentication = new Authentication();
        authentication.setLoginId(123L);
        authentication.setPassword("aaa");
        Optional<Authentication> ofResult = Optional.of(authentication);
        when(this.authenticationRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(AuthenticationNotFoundException.class, () -> this.authenticationServiceImpl.getAuthentication(123L));
        verify(this.modelMapper).map((Object) any(), (Class<AuthenticationDto>) any());
        verify(this.modelMapper).getConfiguration();
        verify(this.authenticationRepository).findById((Long) any());
    }


    @Test
    void testDeleteAuthentication() {
        doNothing().when(this.authenticationRepository).deleteById((Long) any());
        this.authenticationServiceImpl.deleteAuthentication(123L);
        verify(this.authenticationRepository).deleteById((Long) any());
        assertTrue(this.authenticationServiceImpl.getAllAuthentications().isEmpty());
    }


    @Test
    void testDeleteAuthentication2() {
        doThrow(new AuthenticationNotFoundException("Exception")).when(this.authenticationRepository)
                .deleteById((Long) any());
        assertThrows(AuthenticationNotFoundException.class,
                () -> this.authenticationServiceImpl.deleteAuthentication(123L));
        verify(this.authenticationRepository).deleteById((Long) any());
    }



    @Test
    void testBuildAuthenticationDto() {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setLoginId(123L);
        authenticationDto.setPassword("aaa");
        when(this.modelMapper.map((Object) any(), (Class<AuthenticationDto>) any())).thenReturn(authenticationDto);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Authentication authentication = new Authentication();
        authentication.setLoginId(123L);
        authentication.setPassword("aaa");
        assertSame(authenticationDto, this.authenticationServiceImpl.buildAuthenticationDto(authentication));
        verify(this.modelMapper).map((Object) any(), (Class<AuthenticationDto>) any());
        verify(this.modelMapper).getConfiguration();
    }


    @Test
    void testBuildAuthenticationDto2() {
        when(this.modelMapper.map((Object) any(), (Class<AuthenticationDto>) any()))
                .thenThrow(new AuthenticationNotFoundException("Exception"));
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        Authentication authentication = new Authentication();
        authentication.setLoginId(123L);
        authentication.setPassword("aaa");
        assertThrows(AuthenticationNotFoundException.class,
                () -> this.authenticationServiceImpl.buildAuthenticationDto(authentication));
        verify(this.modelMapper).map((Object) any(), (Class<AuthenticationDto>) any());
        verify(this.modelMapper).getConfiguration();
    }




    @Test
    void testBuildAuthentication() {
        Authentication authentication = new Authentication();
        authentication.setLoginId(123L);
        authentication.setPassword("aaa");
        when(this.modelMapper.map((Object) any(), (Class<Authentication>) any())).thenReturn(authentication);
        when(this.modelMapper.getConfiguration()).thenReturn(new InheritingConfiguration());

        AuthenticationDto authenticationDto = new AuthenticationDto();
        authenticationDto.setLoginId(123L);
        authenticationDto.setPassword("aaa");
        assertSame(authentication, this.authenticationServiceImpl.buildAuthentication(authenticationDto));
        verify(this.modelMapper).map((Object) any(), (Class<Authentication>) any());
        verify(this.modelMapper).getConfiguration();
    }






}

