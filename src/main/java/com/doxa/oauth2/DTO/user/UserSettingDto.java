package com.doxa.oauth2.DTO.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UserSettingDto {
    public boolean is2FA;
    public boolean mustSetPassword;
    public LanguageDto language;
}
