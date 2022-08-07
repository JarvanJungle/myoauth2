package com.doxa.oauth2.models.authorities.core;

import com.doxa.oauth2.auth.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFeature {
    private String userId;
    private String companyId;
    private String featureCode;
    private Action actions;
}
