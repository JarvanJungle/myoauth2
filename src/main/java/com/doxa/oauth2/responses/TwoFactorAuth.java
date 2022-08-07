package com.doxa.oauth2.responses;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TwoFactorAuth {

    @NotBlank
    private String firstPin;

    private String secondPin;
}
