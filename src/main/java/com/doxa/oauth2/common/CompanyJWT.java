package com.doxa.oauth2.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vuducnoi
 * Companies object present in access_token
 */
@Getter
@Setter
public class CompanyJWT {
    /*
    Company uuid
     */
    private String uuid;
    /*
    role format: DOXA_ADMIN ENTITY_ADMIN
     */
    private String roles;
    /*
    Authority: users:read users:delete
     */
    private String authorities;
}
