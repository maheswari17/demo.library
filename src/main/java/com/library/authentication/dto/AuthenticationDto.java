package com.library.authentication.dto;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthenticationDto {
    @NotNull
    private long loginId;
    @NotNull
    private String password;
}