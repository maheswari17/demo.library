package com.library.authentication.service;
import com.library.authentication.dto.AuthenticationDto;
import java.util.List;

public interface AuthenticationService {
    List<AuthenticationDto> getAllAuthentications();
    AuthenticationDto addAuthentication(AuthenticationDto authenticationDto);
    AuthenticationDto getAuthentication(long id);
    void deleteAuthentication(long id);
}