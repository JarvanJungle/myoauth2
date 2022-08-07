package com.doxa.oauth2.authorization;

//import com.doxa.oauth2.DTO.financialInstitution.FiUserDto;
import com.doxa.oauth2.DTO.manageUser.ResetPassword;
import com.doxa.oauth2.DTO.manageUser.*;
import com.doxa.oauth2.DTO.user.UserDto;
import com.doxa.oauth2.common.DoxaAuthenticationManager;
import com.doxa.oauth2.config.Message;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.models.user.User;
import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.serviceImpl.AuthorityService;
import com.doxa.oauth2.serviceImpl.UserService;
import com.doxa.oauth2.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
//import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceAuthorizationImpl implements IUserService {
    public static final String READ_USER_AUTHORITY = "USER:read";
    public static final String WRITE_USER_AUTHORITY = "USER:write";
    @Autowired
    private final UserService userService;
    private final DoxaAuthenticationManager authenticationManager;
//    @Autowired
//    private AuthorityService authorityService;

    @SneakyThrows
    public ApiResponse getUserDetails(String companyUuid, Long id) {
        return null;
    }

//    @Override
//    public ApiResponse getUserDetails(String id) {
//        return null;
//    }

    @Override
    public ApiResponse doLogin(UserDto user) {
        return null;
    }

    @Override
    public ApiResponse retrieveEntitiesUsers() {
        return null;
    }

    @Override
    public ApiResponse updateUser(UserDetailsDto userDto) {
        return null;
    }

    public ApiResponse retrieveEntitiesUsers(String companyUuid) throws ObjectDoesNotExistException {
        if (authenticationManager.hasAuthority(companyUuid, READ_USER_AUTHORITY))
            return userService.retrieveEntitiesUsers();
        throw new AccessDeniedException(Message.ACCESS_DENIED.getValue() + READ_USER_AUTHORITY);
    }

//    @Override
//    public void deleteUserCompanies(List<UserCompanies> userCompanies) {
//
//    }

    @Override
    public ApiResponse deactivateUser(String uuid) {
        return null;
    }

    @Override
    public ApiResponse getUserDetails(String uuid) {
        return null;
    }

    @Override
    public ApiResponse getUserRbacRoles(List<String> uuids, String companyUuid) {
        return null;
    }

    @Override
    public ApiResponse retrieveCompanyUsers(String companyUuid) {
        return null;
    }

    @Override
    public ApiResponse retrieveEntityUsersByEntityUuid(String entityUuid) {
        return null;
    }

    @Override
    public ApiResponse getUserEmail(List<String> uuids) {
        return null;
    }

    @Override
    public ApiResponse getEntityAdminFromEntityUuid(String companyUuid) {
        return null;
    }

    @Override
    public ApiResponse resetOwnPassword(ResetPassword resetPassword) {
        return null;
    }

    @Override
    public ApiResponse resetPassword(ResetFAPassword resetFAPassword) {
        return null;
    }

    @Override
    public ApiResponse getQRCode() {
        return null;
    }

    @Override
    public boolean resetTwoFA(Optional<User> userOptional) {
        return false;
    }

    @Override
    public ApiResponse selfReset2FA() {
        return null;
    }

    @Override
    public ApiResponse resetTwoFAOther(ResetFAPassword resetFAPassword) {
        return null;
    }

    @Override
    public ApiResponse activateUser(String uuid) {
        return null;
    }

    @Override
    public ApiResponse markUserAsDelete(String uuid) {
        return null;
    }

    @Override
    public ApiResponse updateOwnAvatar(UpdateAvatarDto updateAvatarDto) {
        return null;
    }

    @Override
    public ApiResponse activateCompanyUser(String companyUuid, String userUuid) {
        return null;
    }

    @Override
    public ApiResponse deactivateCompanyUser(String companyUuid, String userUuid) {
        return null;
    }

//	@Override
//	public ApiResponse updateFiUser(FiUserDto userDto) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
