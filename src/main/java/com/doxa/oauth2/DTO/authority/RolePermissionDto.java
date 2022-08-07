package com.doxa.oauth2.DTO.authority;

import lombok.Data;

/**
 * @author vuducnoi
 */
@Data
public class RolePermissionDto {
	private boolean read;

	private boolean write;

	private boolean approve;

	private CoreFeatureDto feature;
}
