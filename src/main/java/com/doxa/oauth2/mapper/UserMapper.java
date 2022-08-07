package com.doxa.oauth2.mapper;

import com.doxa.oauth2.DTO.user.AuditTrailUserDto;
import com.doxa.oauth2.models.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AuditTrailUserDto user(User user);

}
