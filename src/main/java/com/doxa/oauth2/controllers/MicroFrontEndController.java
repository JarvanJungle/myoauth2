package com.doxa.oauth2.controllers;

import com.doxa.oauth2.DTO.microfrontend.MicroFrontEndDto;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.serviceImpl.MicroFrontEndServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/micro-fe")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MicroFrontEndController {
	private final MicroFrontEndServiceImpl service;

	@GetMapping
	public ResponseEntity<ApiResponse> fetch(@RequestHeader("origin") String origin) {
		List<MicroFrontEndDto> frontEndDtos = service.fetch();
		log.info(origin);
		// filter out the request origin
		if (frontEndDtos != null && !frontEndDtos.isEmpty()) {
			frontEndDtos = frontEndDtos.stream().filter(e -> !StringUtils.equals(e.getHost(), origin)).collect(Collectors.toList());
		}
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setData(frontEndDtos);
		return ResponseEntity.ok().body(apiResponse);
	}
}
