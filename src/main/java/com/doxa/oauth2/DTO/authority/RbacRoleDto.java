package com.doxa.oauth2.DTO.authority;

import com.doxa.oauth2.config.AppConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.List;

/**
 * @author vuducnoi
 */
@Data
public class RbacRoleDto {
	private String uuid;
	private String name;
	private String companyUuid;
	private String description;
	private String status;
	private String createdBy;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AppConfig.DEFAULT_DATE_TIME_FORMAT, timezone = AppConfig.DEFAULT_TIMEZONE)
	private Instant createdAt;
	private List<RolePermissionDto> permissions;
}
