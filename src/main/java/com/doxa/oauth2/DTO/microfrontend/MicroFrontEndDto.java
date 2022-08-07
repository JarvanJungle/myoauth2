package com.doxa.oauth2.DTO.microfrontend;

import lombok.Data;

@Data
public class MicroFrontEndDto {
	private String moduleName;
	private String moduleCode;
	private String environment;
	private String host;
}
