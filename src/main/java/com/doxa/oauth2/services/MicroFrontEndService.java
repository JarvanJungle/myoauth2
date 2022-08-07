package com.doxa.oauth2.services;

import com.doxa.oauth2.DTO.microfrontend.MicroFrontEndDto;

import java.util.List;

public interface MicroFrontEndService {
	List<MicroFrontEndDto> fetch();
}
