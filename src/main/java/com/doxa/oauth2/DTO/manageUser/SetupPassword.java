package com.doxa.oauth2.DTO.manageUser;

import lombok.Data;

import javax.validation.constraints.NotBlank;

//import javax.validation.constraints.NotBlank;

@Data
public class SetupPassword {
    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String email;
}
