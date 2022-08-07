package com.doxa.oauth2.services;


import com.doxa.oauth2.DTO.manageEntity.CreateEntityDetailsDto;
import com.doxa.oauth2.DTO.manageEntity.UpdateEntityDto;
import com.doxa.oauth2.responses.ApiResponse;

public interface IEntitiesService {

    public ApiResponse listEntities();

    public ApiResponse createEntities(CreateEntityDetailsDto createEntityDetailsDto);

    public ApiResponse updateEntities(UpdateEntityDto updateEntityDto);

    public ApiResponse getEntityDetails(String uuid);

    public ApiResponse listEntityType();

    public ApiResponse listIndustryType();

    public ApiResponse markEntityDeleted(String uuid);

    public ApiResponse reactivateEntity(String uuid);
}
