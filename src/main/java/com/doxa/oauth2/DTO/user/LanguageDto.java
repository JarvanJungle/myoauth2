package com.doxa.oauth2.DTO.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class LanguageDto {
    public String languageName;
    public String languageCode;
}
