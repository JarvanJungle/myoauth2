package com.doxa.oauth2.services;


import com.doxa.oauth2.DTO.manageAdminMatrix.SubAdminDto;
import com.doxa.oauth2.responses.ApiResponse;

public interface IManageAdminPermissionService {

    public ApiResponse listSubAdminPermissions(String company_id);

    public ApiResponse updateSubAdminPermission(SubAdminDto subAdminDto);

    public ApiResponse userSubAdminPermission(String userUuid, String companyUuid);
}
