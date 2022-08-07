package com.doxa.oauth2.DTO.financialInstitution;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class FiUserUpdateDto {

	public Long id;
    public String name;
    public String email;
    public String workNumber;
    public String designation;
    public String remarks;
    public String countryCode;
    public String uuid;
    public String role;
    public String companyUuid;
    public String loggedInUserUuid;
    public String fiCode;
    public String fiName;
    public String fiUuid;
    public String fiId;
}
