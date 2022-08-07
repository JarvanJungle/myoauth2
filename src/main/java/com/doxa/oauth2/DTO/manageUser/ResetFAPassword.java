package com.doxa.oauth2.DTO.manageUser;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetFAPassword {

    @NotNull
    private String uuid;

    private String password;

    private String needResetPassword;

}
