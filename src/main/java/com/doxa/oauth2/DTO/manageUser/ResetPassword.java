package com.doxa.oauth2.DTO.manageUser;

import lombok.Data;
import lombok.NonNull;

@Data
public class ResetPassword {
    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;
}
