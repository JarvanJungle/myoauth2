package com.doxa.oauth2.DTO.authority;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author vuducnoi
 */
@Data
public class GrantUserAuthority {
    @NotEmpty
    private String featureCode;

    @NotNull
    private List<String> actions;
}
