package com.doxa.oauth2.DTO.authority;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author vuducnoi
 */
@Data
public class CreateRoleDto {
	@Size(max = 1000)
	private String name;
	@Size(max = 1000)
	private String description;

	List<GrantUserAuthority> permissions;
}
