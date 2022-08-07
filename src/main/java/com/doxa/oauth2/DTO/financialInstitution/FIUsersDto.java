package com.doxa.oauth2.DTO.financialInstitution;

import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class FIUsersDto {

	public Long id;
    public String name;
    public String email;
    public String workNumber;
    public String designation;
    public String remarks;
    public String countryCode;
    public Instant createdAt;
    public String uuid;
    public String fiUuid;
    public boolean isActive;

}