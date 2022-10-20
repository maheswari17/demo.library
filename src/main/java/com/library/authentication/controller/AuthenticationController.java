package com.library.authentication.controller;
import com.library.authentication.dto.AuthenticationDto;
import com.library.authentication.service.AuthenticationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private AuthenticationServiceImpl authenticationServiceImpl;
    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @GetMapping
    public List<AuthenticationDto> getAll() {
        return authenticationServiceImpl.getAllAuthentications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthenticationDto> get(@PathVariable long id) {
        return new ResponseEntity<>(authenticationServiceImpl.getAuthentication(id), HttpStatus.OK);
    }

    @PostMapping
    public AuthenticationDto add(@RequestBody AuthenticationDto authenticationDto) {
        return authenticationServiceImpl.addAuthentication(authenticationDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(reason = "book deleted",code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        authenticationServiceImpl.deleteAuthentication(id);
    }
}
