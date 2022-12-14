package com.doxa.oauth2.DTO.manageUser;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}