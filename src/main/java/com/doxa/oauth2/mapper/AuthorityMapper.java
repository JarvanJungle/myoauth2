package com.doxa.oauth2.mapper;

import com.doxa.oauth2.DTO.authority.CoreFeatureDto;
//import com.doxa.oauth2.DTO.authority.UserPrivilegeDto;
import com.doxa.oauth2.models.authorities.core.Feature;
//import com.doxa.oauth2.models.authorities.core.UserPrivilege;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
//	List<UserPrivilegeDto> fromListUserprivilege(List<UserPrivilege> privileges);
	CoreFeatureDto coreFeatureDto(Feature feature);
}
