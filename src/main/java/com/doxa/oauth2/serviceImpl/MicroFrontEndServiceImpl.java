package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.DTO.microfrontend.MicroFrontEndDto;
import com.doxa.oauth2.mapper.MicroFrontEndMapper;
import com.doxa.oauth2.models.microfrontend.MicroFrontEnd;
import com.doxa.oauth2.repositories.microfrontend.MicroFrontEndRepository;
import com.doxa.oauth2.services.MicroFrontEndService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MicroFrontEndServiceImpl implements MicroFrontEndService {
	private final MicroFrontEndRepository repository;
	private final MicroFrontEndMapper mapper;
	@Override
	public List<MicroFrontEndDto> fetch() {
		List<MicroFrontEnd> microFrontEnds = repository.findAll();
		return mapper.toDto(microFrontEnds);
	}
}
