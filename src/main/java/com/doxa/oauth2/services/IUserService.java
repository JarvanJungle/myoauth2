package com.doxa.oauth2.services;

//import com.doxa.oauth2.DTO.financialInstitution.FiUserDto;
import com.doxa.oauth2.DTO.manageUser.ResetFAPassword;
import com.doxa.oauth2.DTO.manageUser.ResetPassword;
import com.doxa.oauth2.DTO.manageUser.UpdateAvatarDto;
import com.doxa.oauth2.DTO.manageUser.UserDetailsDto;
import com.doxa.oauth2.DTO.user.UserDto;
import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.UserCompanies;
import com.doxa.oauth2.responses.ApiResponse;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public ApiResponse getUserDetails(String id);

    public ApiResponse doLogin(UserDto user);

    public ApiResponse retrieveEntitiesUsers();

    //	public ApiResponse login(SignInRequest request);
    public ApiResponse updateUser(UserDetailsDto userDto);

//    void deleteUserCompanies(List<UserCompanies> userCompanies);

    ApiResponse deactivateUser(String uuid);

//    ApiResponse getUserDetails(String uuid);

    ApiResponse getUserRbacRoles(List<String> uuids, String companyUuid);

    ApiResponse retrieveCompanyUsers(String companyUuid);

    ApiResponse retrieveEntityUsersByEntityUuid(String entityUuid);

    ApiResponse getUserEmail(List<String> uuids);

    ApiResponse getEntityAdminFromEntityUuid(String companyUuid);

    ApiResponse resetOwnPassword(ResetPassword resetPassword);

    ApiResponse resetPassword(ResetFAPassword resetFAPassword);

    ApiResponse getQRCode();

    //	ApiResponse checkTwoFactorCodeForOptIn(UserDto user, TwoFactorAuth twoFactorAuth);
//	ApiResponse signin2FA(TwoFactorAuth twoFactorAuth);
    boolean resetTwoFA(Optional<User> userOptional);

    ApiResponse selfReset2FA();

    ApiResponse resetTwoFAOther(ResetFAPassword resetFAPassword);

    ApiResponse activateUser(String uuid);

    ApiResponse markUserAsDelete(String uuid);

    ApiResponse updateOwnAvatar(UpdateAvatarDto updateAvatarDto);

    ApiResponse activateCompanyUser(String companyUuid, String userUuid);

    ApiResponse deactivateCompanyUser(String companyUuid, String userUuid);
    
//    public ApiResponse updateFiUser(FiUserDto userDto) ;
}
