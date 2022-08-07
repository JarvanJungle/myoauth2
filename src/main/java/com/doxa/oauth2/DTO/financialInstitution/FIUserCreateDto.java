package com.doxa.oauth2.DTO.financialInstitution;

import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class FIUserCreateDto {

	public String name;
	public String email;
	public String workNumber;
	public String designation;
	public String password;
	public String remarks;
	public String countryCode;
	public String fiCode;
	public String fiName;
    public Instant createdAt;
    public boolean isActive;
    public String fiUuid;
	public String uuid;
}
