package com.doxa.oauth2.mapper;

import com.doxa.oauth2.DTO.microfrontend.MicroFrontEndDto;
import com.doxa.oauth2.models.microfrontend.MicroFrontEnd;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MicroFrontEndMapper {
	List<MicroFrontEndDto> toDto(List<MicroFrontEnd> models);
}
