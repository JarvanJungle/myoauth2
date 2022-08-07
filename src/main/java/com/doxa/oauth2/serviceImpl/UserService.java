package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.DTO.manageUser.ResetPassword;
import com.doxa.oauth2.DTO.financialInstitution.FIUsersDto;
//import com.doxa.oauth2.DTO.financialInstitution.FiUserDto;
import com.doxa.oauth2.DTO.manageUser.*;
import com.doxa.oauth2.DTO.user.UserDto;
import com.doxa.oauth2.common.DoxaAuthenticationManager;
import com.doxa.oauth2.config.AppConfig;
import com.doxa.oauth2.config.Configs;
import com.doxa.oauth2.config.Message;
import com.doxa.oauth2.exceptions.EntityAlreadyExistsException;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.microservices.DTO.EmailDto;
import com.doxa.oauth2.microservices.interfaces.IEmailService;
import com.doxa.oauth2.microservices.interfaces.IMessageService;
//import com.doxa.oauth2.models.authorities.UserAdministratives;
//import com.doxa.oauth2.models.authorities.core.RbacRole;
//import com.doxa.oauth2.models.companies.DoxaEntity;
//import com.doxa.oauth2.models.financialInstitution.FinancialInstitution;
//import com.doxa.oauth2.models.financialInstitution.UserFinancialInstitution;
import com.doxa.oauth2.models.user.*;
//import com.doxa.oauth2.repositories.authorities.UserAdministrativesRepository;
//import com.doxa.oauth2.repositories.authorities.core.RbacRoleRepository;
import com.doxa.oauth2.repositories.companies.CompanyRepository;
//import com.doxa.oauth2.repositories.companies.EntitiesRepository;
//import com.doxa.oauth2.repositories.financialInstitution.FiUserRepository;
//import com.doxa.oauth2.repositories.financialInstitution.FinancialInstitutionRepository;
import com.doxa.oauth2.repositories.user.*;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.responses.QRCodeResponse;
import com.doxa.oauth2.responses.TwoFactorAuth;
import com.doxa.oauth2.services.IUserService;
//import com.doxa.oauth2.services.SpecificationService;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
//import net.bytebuddy.utility.RandomString;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Expression;
//import javax.persistence.criteria.Path;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UserService implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final DoxaAuthenticationManager adminAuthorization;
    private final MultiFactorService multiFactorService;
    //    private final UserRoleRepository userRoleRepository;
    private final IEmailService emailService;
    private final ModelMapper modelMapper;
    private final PasswordProviderService passwordProviderService;
    //    private final UserAdministrativesRepository userAdministrativesRepository;
//    private final LanguageRepository languageRepository;
    private final RoleRepository roleRepository;
    //    private final RbacRoleRepository rbacRoleRepository;
//    private final UserRbacRoleRepository userRbacRoleRepository;
    private final CompanyRepository companyRepository;
    //    private final UserSettingRepository settingRepository;
//    private final  UserCompaniesRepository userCompaniesRepository;
//    private final EntitiesRepository entitiesRepository;
    private final IMessageService messageService;
//    private final FinancialInstitutionRepository  financialInstitutionRepository;
//	private final FiUserRepository userFinancialInstitutionRepository;
//	private final EntityManager entityManager;
//	private final CriteriaBuilder criteriaBuilder;
//	private final SpecificationService specificationService;

    @Value("${doxa.email.hyperlink-host}")
    private String connexEmailHyperlinkHost;

    public UserService(UserRepository userRepository, DoxaAuthenticationManager adminAuthorization, MultiFactorService multiFactorService,
//                       UserRoleRepository userRoleRepository,
                       IEmailService emailService, ModelMapper modelMapper, PasswordProviderService passwordProviderService,
//                       UserAdministrativesRepository userAdministrativesRepository,
//                       LanguageRepository languageRepository,
                       RoleRepository roleRepository,
//                       RbacRoleRepository rbacRoleRepository,
//                       UserRbacRoleRepository userRbacRoleRepository,
                       CompanyRepository companyRepository,
//                       UserSettingRepository settingRepository, UserCompaniesRepository userCompaniesRepository, EntitiesRepository entitiesRepository,
                       IMessageService messageService
//                       FinancialInstitutionRepository financialInstitutionRepository, FiUserRepository userFinancialInstitutionRepository,
//                       SpecificationService specificationService
    ) {
        this.userRepository = userRepository;
        this.adminAuthorization = adminAuthorization;
        this.multiFactorService = multiFactorService;
//        this.userRoleRepository = userRoleRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
        this.passwordProviderService = passwordProviderService;
//        this.userAdministrativesRepository = userAdministrativesRepository;
//        this.languageRepository = languageRepository;
        this.roleRepository = roleRepository;
//        this.rbacRoleRepository = rbacRoleRepository;
//        this.userRbacRoleRepository = userRbacRoleRepository;
        this.companyRepository = companyRepository;
//        this.settingRepository = settingRepository;
//        this.userCompaniesRepository = userCompaniesRepository;
//        this.entitiesRepository = entitiesRepository;
        this.messageService = messageService;
//        this.financialInstitutionRepository = financialInstitutionRepository;
//        this.userFinancialInstitutionRepository = userFinancialInstitutionRepository;
//        this.entityManager = entityManager;
//    	this.criteriaBuilder = entityManager.getCriteriaBuilder();
//    	this.specificationService = specificationService;
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Setup password for user
     *
     * @param setupPassword
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse updatePassword(SetupPassword setupPassword) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> user = userRepository.findByEmail(setupPassword.getEmail());
            if (user.isPresent()) {
                User currentUser = user.get();
                if (passwordProviderService.passwordEncoder().matches(setupPassword.getCurrentPassword(), currentUser.getPassword())) {
                    String hashedPassword = passwordProviderService.passwordEncoder().encode(setupPassword.getNewPassword());

                    currentUser.setPassword(hashedPassword);
                    userRepository.save(currentUser);
                    // Update settings

//                    UserSettings settings = currentUser.getSettings();
//                    settings.setMustSetPassword(false);
//                    settingRepository.save(settings);
                    apiResponse.setStatus(HttpStatus.OK);
                    return apiResponse;
                }
            }
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse
                    .setMessage(Message.WRONG_PASSWORD.getValue());
            return apiResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(Message.SERVER_ERROR.getValue());
            return apiResponse;
        }
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public boolean checkSignInTOTPCode(TwoFactorAuth twoFactorAuth, String secret) {
        return multiFactorService.validate(secret, twoFactorAuth.getFirstPin());
    }

    public ApiResponse getUserOwnDetails() {
//        Long userId = adminAuthorization.getCurrentUserId();
        String uuid = adminAuthorization.getUserIdentifier();
        return getUserDetails(uuid);
    }

    @Override
    public ApiResponse getQRCode() {
        String userId = adminAuthorization.getCurrentUserId();
        BufferedImage image;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        QRCodeResponse output = new QRCodeResponse();
        Optional<User> optionalUser = userRepository.findById(userId);
        ApiResponse apiResponse = new ApiResponse();
        if (optionalUser.isEmpty()) {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            return apiResponse;
        }
        try {
            User user = optionalUser.get();
//            String secret = user.getSettings().getSecret();
//            if (secret == null) {
//                secret = multiFactorService.generateSecretKey();
//                Optional<UserSettings> userSettings = settingRepository.findById(user.getSettings().getId());
//                UserSettings settings = userSettings.get();
//                settings.setSecret(secret);
//                settingRepository.save(settings);
//            }
            String secret = "aaa";
            image = multiFactorService.createQRCode(user.getEmail(), secret);
            ImageIO.write(image, "png", os);
            output.setBase64(new String("data:image/png;base64," + Base64Utils.encodeToString(os.toByteArray())));
            output.setSecretKey(secret);
            apiResponse.setStatus(HttpStatus.OK);
            apiResponse.setData(output);
            return apiResponse;
        } catch (WriterException ex) {
            log.error(ex.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setMessage(ex.getMessage());
            return apiResponse;
        } catch (IOException ex) {
            log.error(ex.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setMessage(ex.getMessage());
            return apiResponse;
        }
    }
//    public ApiResponse checkTwoFactorCodeForOptIn(TwoFactorAuth twoFactorAuth) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<UserSettings> userSettingsOptional = settingRepository.findByUserId(adminAuthorization.getCurrentUserId());
//            if (userSettingsOptional.isPresent()) {
//                UserSettings settings = userSettingsOptional.get();
//                String secret = settings.getSecret();
//                if (multiFactorService.validate(multiFactorService.getStep() - 1L, secret, twoFactorAuth.getFirstPin())
//                        && multiFactorService.validate(secret, twoFactorAuth.getSecondPin())) {
//                    settings.set2Fa(true);
//                    settingRepository.save(settings);
//                    apiResponse.setStatus(HttpStatus.OK);
//                } else {
//                    apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//                }
//            } else {
//                apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            }
//            return apiResponse;
//        }catch (Exception e) {
//            LOG.error(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(e.getMessage());
//            return apiResponse;
//        }
//
//    }

    //    @Override
    public ApiResponse signin2FA(TwoFactorAuth twoFactorAuth) {
        return new ApiResponse();
    }

    @Override
    public ApiResponse resetPassword(ResetFAPassword resetFAPassword) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> optionalUser = userRepository.findById(resetFAPassword.getUuid());
            if (optionalUser.isEmpty()) {
                apiResponse.setStatus(HttpStatus.NOT_FOUND);
                apiResponse.setMessage(Message.USER_NOT_FOUND.getValue());
                return apiResponse;
            }
            User user = optionalUser.get();
//            if (resetFAPassword.getPassword().isEmpty()) {
//                RandomString randomString = new RandomString(15);
//                resetFAPassword.setPassword(randomString.nextString());
//            }
            //String to, String subject, String title, String message
            EmailDto emailDTO = new EmailDto(user.getEmail(), Message.EMAIL_RESET_PASSWORD_SUBJECT.getValue(), Message.EMAIL_RESET_PASSWORD_TITLE.getValue(), Message.emailResetPasswordMessage(resetFAPassword.getPassword(), connexEmailHyperlinkHost));
            emailService.sendEmail(emailDTO); // TODO: send email
            String pwdEncoded = passwordProviderService.passwordEncoder().encode(resetFAPassword.getPassword());
            user.setPassword(pwdEncoded);
            //set the needs for password updating to true
//            if (resetFAPassword.getNeedResetPassword().equals("true")) {
//                user.getSettings().setMustSetPassword(true);;
//            }
            saveUser(user);
            apiResponse.setStatus(HttpStatus.OK);
            return apiResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setMessage(e.getMessage());
            return apiResponse;
        }

    }

    @Override
    public ApiResponse resetOwnPassword(ResetPassword resetPassword) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            //retrieve the user from the JWT auth
            Optional<User> optionalUser = userRepository.findById(adminAuthorization.getCurrentUserId());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (passwordProviderService.passwordEncoder().matches(resetPassword.getOldPassword(), user.getPassword())) {
                    user.setPassword(passwordProviderService.passwordEncoder().encode(resetPassword.getNewPassword()));
                } else {
                    apiResponse.setStatus(HttpStatus.BAD_REQUEST);
                    apiResponse.setMessage(Message.WRONG_PASSWORD.getValue());
                    return apiResponse;
                }
                saveUser(user);
                apiResponse.setStatus(HttpStatus.OK);
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage(Message.NOT_FOUND.getValue());
            return apiResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setMessage(e.getMessage());
            return apiResponse;
        }
    }

    @Override
    public boolean resetTwoFA(Optional<User> userOptional) {
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        // turn off and reset the 2FA
//        user.getSettings().setSecret(multiFactorService.generateSecretKey());;
//        user.getSettings().set2Fa(false);
        userRepository.save(user);
        return true;
    }

    @Override
    public ApiResponse selfReset2FA() {
        ApiResponse apiResponse = new ApiResponse();
        try {
            User user = userRepository.findById(adminAuthorization.getCurrentUserId()).orElse(null);
            if (user == null) {
                throw new ObjectDoesNotExistException(Message.USER_NOT_FOUND.getValue());
            }
            //retrieve the user based on the userid
            Optional<User> userOptional = userRepository.findById(user.getId());
            if (resetTwoFA(userOptional)) {
                apiResponse.setStatus(HttpStatus.OK);
                return apiResponse;
            }
            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage(Message.NOT_FOUND.getValue());
            return apiResponse;
        } catch (ObjectDoesNotExistException e) {
            LOG.error(e.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setMessage(e.getMessage());
            return apiResponse;
        }

    }

//    @Override
//    public ApiResponse resetTwoFAOther(ResetFAPassword resetFAPassword) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findById(resetFAPassword.getUuid());
//            User loginUser = userRepository.findById(adminAuthorization.getCurrentUserId()).get();
//            List<Role> loginUserRole = userRoleRepository.findRoleByUserId(loginUser.getId());
//            if (optionalUser.isEmpty()) {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.USER_NOT_FOUND.getValue());
//                return apiResponse;
//            }
//            User resetPasswordUser = optionalUser.get();
//            loginUserRole.stream().filter(role -> role.getRoleCode().equals(Configs.DOXA_ADMIN.getValue()));
//            //if there are no DOXA ADMIN under the user's role, check if the resetPassword User has the same entity Id as the login user
//            if (loginUserRole.size() == 0) {
//                if (!loginUser.getEntity().getId().equals(resetPasswordUser.getEntity().getId())) {
//                    apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
//                    apiResponse.setMessage(Message.UNAUTHORIZED.getValue());
//                    return apiResponse;
//                }
//            }
//            if (resetTwoFA(optionalUser)) {
//                User user = userRepository.findByUuid(resetFAPassword.getUuid()).get();
//                //String to, String subject, String title, String message
//                EmailDto emailDTO = new EmailDto(user.getEmail(), Message.EMAIL_RESET_2FA_SUBJECT.getValue(), Message.EMAIL_RESET_2FA_TITLE.getValue(), Message.EMAIL_RESET_2FA_MESSAGES.getValue());
//                emailService.sendEmail(emailDTO); // TODO: Send email
//                apiResponse.setStatus(HttpStatus.OK);
//                return apiResponse;
//            }
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(Message.SERVER_ERROR.getValue());
//            return apiResponse;
//        } catch (Exception e) {
//            LOG.error(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(e.getMessage());
//            return apiResponse;
//        }
//    }

    @Override
    public ApiResponse getUserDetails(String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
//                List<UserCompanies> userCompanies = userCompaniesRepository.findUserCompaniesByUserId(optionalUser.get().getId());
//                List <DisplayUserRoleCompanyDto> userRolesList = new ArrayList<>();
//                setUserRoleList(userCompanies, userRolesList);
                UserDetailsDto userDto = modelMapper.map(optionalUser.get(), UserDetailsDto.class);
//                userDto.setCompanies(userRolesList);
//                List<String> roles = Arrays.asList(optionalUser.get().getRoles().split(" "));
//                userDto.setRoles(roles);
                apiResponse.setData(userDto);
                apiResponse.setStatus(HttpStatus.OK);
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND);
                apiResponse.setMessage(Message.NOT_FOUND.getValue());
            }
            return apiResponse;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setMessage(e.getMessage());
            return apiResponse;
        }
    }

    @Override
    public ApiResponse doLogin(UserDto user) {
        return null;
    }

//    @Override
//    public ApiResponse getUserDetails(String id) {
//        log.info("Getting user detail uuid = " + id);
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findById(id);
//            if (optionalUser.isPresent()) {
//                UserDetailsDto userDto = modelMapper.map(optionalUser.get(), UserDetailsDto.class);
//                List<UserCompanies> userCompanies = userCompaniesRepository.findUserCompaniesByUserId(optionalUser.get().getId());
//                List <DisplayUserRoleCompanyDto> userRolesList = new ArrayList<>();
//                for (UserCompanies userCompany : userCompanies) {
//                    if (!userCompany.getCompanies().isActive()) {
//                        continue;
//                    }
//                    List<Role> roles = userRoleRepository.findRoleByUserCompanyId(userCompany.getId());
//                    List<SimpleRoleDto> rolesDto = new ArrayList<>();
//                    for (Role role : roles) {
//                        SimpleRoleDto roleDto = modelMapper.map(role, SimpleRoleDto.class);
//                        rolesDto.add(roleDto);
//                    }
//                    SimpleCompanyDto simpleCompanyDto = modelMapper.map(userCompany.getCompanies(), SimpleCompanyDto.class);
//                    DisplayUserRoleCompanyDto userRoleCompanyDto = new DisplayUserRoleCompanyDto(rolesDto, simpleCompanyDto);
//                    userRolesList.add(userRoleCompanyDto);
//                }
//                userDto.setCompanies(userRolesList);
//                List<String> roles = Arrays.asList(optionalUser.get().getRoles().split(" "));
//                userDto.setRoles(roles);
//                apiResponse.setData(userDto);
//                apiResponse.setStatus(HttpStatus.OK);
//            } else {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        } catch (Exception e) {
//            LOG.error(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(e.getMessage());
//            return apiResponse;
//        }
//    }

//    @Override
//    public ApiResponse getUserRbacRoles(List<String> userUuids, String companyUuid) {
//        log.info("Getting user rbac role: uuid = " + userUuids);
//        ApiResponse apiResponse = new ApiResponse();
//        Map<String, List<String>> userRbacRoleMap = new HashMap<>();
//        try {
//            //get the rbac role of the user of the company
//            for (String uuid : userUuids) {
//                UserCompanies rbacUserCompany = userCompaniesRepository.findOneByUserUuidAndCompanyUuid(companyUuid, uuid);
//                if (rbacUserCompany != null) {
//                    List<RbacUserRole> userRoles = userRbacRoleRepository.findAllByUserCompanies(rbacUserCompany);
//                    List<RbacRole> rbacRoles = userRoles.stream().filter(e -> Objects.nonNull(e.getRole())).map(RbacUserRole::getRole).collect(Collectors.toList());
//                    List<String> rbacRoleNames = rbacRoles.stream().map(RbacRole::getName).collect(Collectors.toList());
//                    userRbacRoleMap.put(uuid, rbacRoleNames);
//                }
//            }
//            apiResponse.setData(userRbacRoleMap);
//            apiResponse.setStatus(HttpStatus.OK);
//            return apiResponse;
//        } catch (Exception e) {
//            LOG.error(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(e.getMessage());
//            return apiResponse;
//        }
//    }

//    @Override
//    public ApiResponse getUserEmail(List<String> uuids) {
//        log.info("Getting user email: uuids = " + uuids);
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            List<User> users = userRepository.findByUuidIn(uuids);
//            List<UserDetailsAPIDto> userDtos = users.stream().map(u -> {
//                UserDetailsAPIDto dto = new UserDetailsAPIDto();
//                dto.setUuid(u.getUuid());
//                dto.setEmail(u.getEmail());
//                dto.setName(u.getName());
//                return dto;
//            }).collect(Collectors.toList());
//            apiResponse.setData(userDtos);
//            apiResponse.setStatus(HttpStatus.OK);
//            return apiResponse;
//        } catch (Exception e) {
//            LOG.error(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(e.getMessage());
//            return apiResponse;
//        }
//    }

//    @Override
//    public ApiResponse getEntityAdminFromEntityUuid(String entityuuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            String roleCode = Configs.ENTITY_ADMIN.getValue();
//            List<User> users = userRoleRepository.findUserByEntityIdAndRoleCode(roleCode, entityuuid);
//
//            if (!users.isEmpty()) {
//                //there should be only 1 entityadmin
//                User user = users.get(0);
//                apiResponse.setData(user.getUuid());
//                apiResponse.setStatus(HttpStatus.OK);
//                return apiResponse;
//            }
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//            apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            return apiResponse;
//        }catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse createEntityUser(UserCreateDto userDto) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> optionalUser = userRepository.findById(adminAuthorization.getCurrentUserId());
            if (optionalUser.isPresent()) {
//                User entity = optionalUser.get().getEntity();
//                //creating user and attach to the the same entity
//                User User = createUserFromUserCreateDto(userDto, entity);
//                apiResponse.setData(User.getUuid());
                User user = createUserFromUserCreateDto(userDto);
                apiResponse.setData(user.getId());
                apiResponse.setStatus(HttpStatus.OK);
            } else {
                apiResponse.setStatus(HttpStatus.BAD_REQUEST);
                apiResponse.setMessage(Message.NOT_FOUND.getValue());
            }
            return apiResponse;
        } catch (Exception e) {
            log.error(e.getMessage());
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            return apiResponse;
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, EntityAlreadyExistsException.class})
    public User createUserFromUserCreateDto(UserCreateDto userDto
//                                            DoxaEntity entity
    ) throws Exception {
        Optional<User> checkEmail = userRepository.findByEmail(userDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new Exception(Message.NON_UNIQUE_EMAIL.getValue());
        }

        String password;
        if (userDto.getPassword() == null || userDto.getPassword().isBlank() || userDto.getPassword().isEmpty()) {
            RandomString randomString = new RandomString(Integer.parseInt(Configs.DEFAULT_PWD_LENGTH.getValue()));
            password = randomString.nextString();
        } else {
            password = userDto.getPassword();
        }
        password = userDto.getPassword();
        String encryptPassword = passwordProviderService.passwordEncoder().encode(password);
        User user = new User();
        //set the entity of the user
//        user.setEntity(entity);
//        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(encryptPassword);
        user.setEmail(userDto.getEmail().trim());
        user.setName(userDto.getName());
        user.setStatus("ACTIVE");
        user.setPhone(userDto.getPhone());
//        user.setDesignation(userDto.getDesignation());
//        user.setWorkNumber(userDto.getWorkNumber());
//        user.setCountryCode(userDto.getCountryCode());
//        user.setRemarks(userDto.getRemarks());

        user.setCompanyId(userDto.getCompany().getCompanyId());
        user.setRoleList(userDto.getCompany().getRole());
        User savedUser = userRepository.save(user);
//        for (int i = 0; i < userDto.getCompaniesRoles().size(); i++) {
//            Company company = companiesRepository.findByUuid(userDto.getCompaniesRoles().get(i).getCompanyUuid());
//            company.get
//
//            UserCompanies newUserCompanies = new UserCompanies(savedUser, company);
//            UserCompanies savedUserCompanies = userCompaniesRepository.save(newUserCompanies);
//            for (String roleDto : userDto.getCompaniesRoles().get(i).getRole()) {
//                Role role = roleRepository.findByRoleCode(roleDto);
//                UserRole newUserRole = new UserRole();
//                newUserRole.setRole(role);
//                newUserRole.setUserCompanies(savedUserCompanies);
//                userRoleRepository.save(newUserRole);
//            }
//            //Create group for each user company
//            UserDetailsDto userDetailsDto = modelMapper.map(savedUser, UserDetailsDto.class);
//            userDetailsDto.setCompanyUuid(company.getUuid());
//            messageService.notifyNewUserCreated(userDetailsDto);
//        }
        //create the user settings for the user.
//        UserSettings userSettings = new UserSettings();
//        userSettings.set2Fa(false);
//        userSettings.setLanguage(languageRepository.findByLanguageCode(Configs.DEFAULT_LANGUAGE.getValue()));
//        userSettings.setMustSetPassword(true);
//        userSettings.setSecret(multiFactorService.generateSecretKey());
//        userSettings.setUser(savedUser);
//        settingRepository.save(userSettings);
        EmailDto emailDTO = new EmailDto(user.getEmail(), Message.EMAIL_ACCOUNT_SETUP_SUBJECT.getValue(), null, Message.emailAccountSetupMessages(password, connexEmailHyperlinkHost));
        emailDTO.getBody().setToName(Collections.singletonList(user.getName()));
        emailDTO.getBody().setType(null);
        emailDTO.getBody().setTemplateName(AppConfig.EMAIL_TEMPLATE_NOTIFICATION);
        emailService.sendEmail(emailDTO);
        return user;
    }

//    @Override
//    public ApiResponse retrieveEntitiesUsers() {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            String userId = adminAuthorization.getCurrentUserId();
//            Optional<User> optionalUser = userRepository.findById(userId);
//            if (optionalUser.isPresent()) {
//                //find all the users with the same entity id as the entity admin
//                List<User> entityUsers = userRepository.findUserByEntityId(optionalUser.get().getEntity().getId());
//                convertEntityUsersToUserDto(apiResponse, entityUsers);
//            } else {
//                throw new ObjectDoesNotExistException(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        }catch (AccessDeniedException e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.FORBIDDEN);
//        } catch (ObjectDoesNotExistException e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//        }
//        return apiResponse;
//    }

//    private void convertEntityUsersToUserDto(ApiResponse apiResponse, List<User> entityUsers) {
//        List<UserDetailsDto> entityUsersDTO = new ArrayList<>();
//        for (User entityUser : entityUsers) {
//            //converting user into userDTO
//            UserDetailsDto userDto = modelMapper.map(entityUser, UserDetailsDto.class);
//            List<UserCompanies> userCompanies = userCompaniesRepository.findUserCompaniesByUserId(entityUser.getId());
//            List<DisplayUserRoleCompanyDto> userRolesList = new ArrayList<>();
//            for (UserCompanies userCompany : userCompanies) {
//                List<Role> roles = userRoleRepository.findRoleByUserCompanyId(userCompany.getId());
//                List<SimpleRoleDto> rolesDto = new ArrayList<>();
//                for (Role role : roles) {
//                    SimpleRoleDto roleDto = modelMapper.map(role, SimpleRoleDto.class);
//                    rolesDto.add(roleDto);
//                }
//                SimpleCompanyDto simpleCompanyDto = modelMapper.map(userCompany.getCompanies(), SimpleCompanyDto.class);
//                DisplayUserRoleCompanyDto userRoleCompanyDto = new DisplayUserRoleCompanyDto(rolesDto, simpleCompanyDto);
//                userRolesList.add(userRoleCompanyDto);
//            }
//            userDto.setCompanies(userRolesList);
//            entityUsersDTO.add(userDto);
//        }
//        apiResponse.setData(entityUsersDTO);
//        apiResponse.setStatus(HttpStatus.OK);
//    }

//    private void convertCompanyUsersToUserDto(ApiResponse apiResponse, List<User> entityUsers, String companyUuid) {
//        List<UserDetailsDto> entityUsersDTO = new ArrayList<>();
//        for (User entityUser : entityUsers) {
//            //converting user into userDTO
//            UserDetailsDto userDto = modelMapper.map(entityUser, UserDetailsDto.class);
//            List<UserCompanies> userCompanies = userCompaniesRepository.findUserCompaniesByUserId(entityUser.getId());
//            List<DisplayUserRoleCompanyDto> userRolesList = new ArrayList<>();
//            for (UserCompanies userCompany : userCompanies) {
//                List<Role> roles = userRoleRepository.findRoleByUserCompanyId(userCompany.getId());
//                List<SimpleRoleDto> rolesDto = new ArrayList<>();
//                for (Role role : roles) {
//                    SimpleRoleDto roleDto = modelMapper.map(role, SimpleRoleDto.class);
//                    rolesDto.add(roleDto);
//                }
//                SimpleCompanyDto simpleCompanyDto = modelMapper.map(userCompany.getCompanies(), SimpleCompanyDto.class);
//                DisplayUserRoleCompanyDto userRoleCompanyDto = new DisplayUserRoleCompanyDto(rolesDto, simpleCompanyDto);
//                userRolesList.add(userRoleCompanyDto);
//            }
//            //get the rbac role of the user of the company
//            UserCompanies rbacUserCompany = userCompaniesRepository.findOneByUserUuidAndCompanyUuid(companyUuid, entityUser.getUuid());
//            if (rbacUserCompany != null) {
//                List<RbacUserRole> userRoles = userRbacRoleRepository.findAllByUserCompanies(rbacUserCompany);
//                List<RbacRole> rbacRoles = userRoles.stream().filter(e -> Objects.nonNull(e.getRole())).map(RbacUserRole::getRole).collect(Collectors.toList());
//                List<String> rbacRoleNames = rbacRoles.stream().map(RbacRole::getName).collect(Collectors.toList());
//                userDto.setRbacRoles(rbacRoleNames);
//            }
//
//            userDto.setCompanies(userRolesList);
//            entityUsersDTO.add(userDto);
//        }
//        apiResponse.setData(entityUsersDTO);
//        apiResponse.setStatus(HttpStatus.OK);
//    }
//
//    @Override
//    public ApiResponse retrieveCompanyUsers(String companyUuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Company company = companiesRepository.findByUuid(companyUuid);
//            //find all the users with the same entity id as the entity admin
//            List<User> companyUsers = userCompaniesRepository.findUserByCompanyId(company.getId());
//            convertCompanyUsersToUserDto(apiResponse, companyUsers, companyUuid);
//            return apiResponse;
//        }catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }
//
//    @Override
//    public ApiResponse retrieveEntityUsersByEntityUuid(String entityUuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            if (!adminAuthorization.isDoxaAdmin()) {
//                throw new AccessDeniedException("Access denied, user doesn't have permission to access resource");
//            }
//            DoxaEntity doxaEntity = entitiesRepository.findByUuid(entityUuid);
//            if (doxaEntity != null) {
//                List<User> entityUsers = userRepository.findUserByEntityId(doxaEntity.getId());
//                convertEntityUsersToUserDto(apiResponse, entityUsers);
//            } else {
//                throw new ObjectDoesNotExistException(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        } catch (AccessDeniedException e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.FORBIDDEN);
//        } catch (ObjectDoesNotExistException e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//        }
//        return apiResponse;
//    }

//    @Transactional
//    @Override
//    public ApiResponse updateUser(UserDetailsDto userDto) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findById(userDto.getId());
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                if (!user.getEmail().equals(userDto.getEmail())) {
//                    Optional<User> checkEmail = userRepository.findByEmail(userDto.getEmail());
//                    //return error if the new email address is alr exist in the database and not belong to the user
//                    if (checkEmail.isPresent() && !checkEmail.get().getUuid().equals(userDto.getUuid())) {
//                        throw new Exception(Message.NON_UNIQUE_EMAIL.getValue());
//                    }
//                }
//
//                //User is able to update the username, password and companies list
//                user.setName(userDto.getName());
//                user.setEmail(userDto.getEmail());
////                user.setDesignation(userDto.getDesignation());
//                user.setPhone(userDto.getWorkNumber());
////                user.setCountryCode(userDto.getCountryCode());
////                user.setRemarks(userDto.getRemarks());
//                //Cannot delete all usercompanies as user administrative settings will be removed as well
////                List<UserCompanies> userCompaniesList = userCompaniesRepository.findUserCompaniesByUserId(user.getId());
//                HashMap<String, Integer> checkUserCompanies = new HashMap<>();
////                for (UserCompanies userCompany: userCompaniesList){
////                    // 0 means the company is available in DB
////                    checkUserCompanies.put(userCompany.getCompanies().getUuid(), 0);
////                }
//
////                HashMap<UserCompanies, DisplayUserRoleCompanyDto> toEditUserCompanies = new HashMap<>();
//                List<DisplayUserRoleCompanyDto> toAddCompaniesDto = new ArrayList<>();
//                for (DisplayUserRoleCompanyDto inputUserCompanies: userDto.getCompanies()){
//                    if (checkUserCompanies.containsKey(inputUserCompanies.getCompanyUuid())){
//                        // 2 means the company is available in both DB and user selection
//                        checkUserCompanies.put(inputUserCompanies.getCompanyUuid(), 2);
//                        //edit userCompanies with value 2, remove all userroles and add again
//                        for (UserCompanies userCompany: userCompaniesList){
//                            if (userCompany.getCompanies().getUuid().equals(inputUserCompanies.getCompanyUuid())){
//                                toEditUserCompanies.put(userCompany, inputUserCompanies);
//                            }
//                        }
//                    }else{
//                        // 1 means new company attached
//                        checkUserCompanies.put(inputUserCompanies.getCompanyUuid(), 1);
//                        //add new userCompanies with value 1
//                        toAddCompaniesDto.add(inputUserCompanies);
//                    }
//                }
//                //remove all userCompanies with value 0
//                List<UserCompanies> toDeleteUserCompanies = new ArrayList<>();
//
//                for (String key : checkUserCompanies.keySet()) {
//                    if (checkUserCompanies.get(key) == 0) {
//                        for (UserCompanies userCompany : userCompaniesList) {
//                            if (userCompany.getCompanies().getUuid().equals(key)) {
//                                toDeleteUserCompanies.add(userCompany);
//                            }
//                        }
//                    }
//                }
//                //deleting the userCompanies
//                deleteUserCompanies(toDeleteUserCompanies);
//                List<UserRole> toSaveUserRoles = new ArrayList<>();
//                //edit the current userRoles of companies that already in DB
//                //delete all user roles and create again
//                for (UserCompanies userCompany: toEditUserCompanies.keySet()){
//                    List<UserRole> userRolesList = userRoleRepository.findUserRoleByUserCompanyId(userCompany.getId());
//                    userRoleRepository.deleteAll(userRolesList);
//                    DisplayUserRoleCompanyDto displayUserDto = toEditUserCompanies.get(userCompany);
//                    for (String roleDto: displayUserDto.getRole()) {
//                        Role role = roleRepository.findByRoleCode(roleDto);
//                        UserRole newUserRole = new UserRole();
//                        newUserRole.setRole(role);
//                        newUserRole.setUserCompanies(userCompany);
//                        toSaveUserRoles.add(newUserRole);
//                    }
//                }
//
//                for (DisplayUserRoleCompanyDto displayUserDto: toAddCompaniesDto){
//                    Company company = companiesRepository.findByUuid(displayUserDto.getCompanyUuid());
//                    UserCompanies newUserCompanies = new UserCompanies(user, company);
//                    UserCompanies savedUserCompanies = userCompaniesRepository.save(newUserCompanies);
//                    for (String roleDto: displayUserDto.getRole()) {
//                        Role role = roleRepository.findByRoleCode(roleDto);
//                        UserRole newUserRole = new UserRole();
//                        newUserRole.setRole(role);
//                        newUserRole.setUserCompanies(savedUserCompanies);
//                        toSaveUserRoles.add(newUserRole);
//                    }
//                }
//                userRoleRepository.saveAll(toSaveUserRoles);
//                userRepository.save(user);
//                apiResponse.setStatus(HttpStatus.OK);
//            }else {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        }catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }
//
//    @Override
//    public void deleteUserCompanies(List<UserCompanies> userCompanies) {
//        List<UserRole> toDeleteUserRoles = new ArrayList<>();
//        List<UserAdministratives> toDeleteUserAdmins = new ArrayList<>();
//        for (UserCompanies userCompany: userCompanies){
//            List<UserRole> userRolesList = userRoleRepository.findUserRoleByUserCompanyId(userCompany.getId());
//            List<UserAdministratives> userAdminList = userAdministrativesRepository.findUserAdministrativesByUserCompanyId(userCompany.getId());
//            toDeleteUserRoles.addAll(userRolesList);
//            toDeleteUserAdmins.addAll(userAdminList);
//        }
//        userRoleRepository.deleteAll(toDeleteUserRoles);
//        userAdministrativesRepository.deleteAll(toDeleteUserAdmins);
//
//        userCompaniesRepository.deleteAll(userCompanies);
//    }
//
//    @Override
//    public ApiResponse deactivateUser(String uuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findByUuid(uuid);
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                user.setActive(false);
//                userRepository.save(user);
//                apiResponse.setStatus(HttpStatus.OK);
//            }else {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        }catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }
//
//    @Override
//    public ApiResponse activateUser(String uuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findByUuid(uuid);
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                user.setActive(true);
//                userRepository.save(user);
//                apiResponse.setStatus(HttpStatus.OK);
//            }else {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        }catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }
//
//    @Override
//    public ApiResponse markUserAsDelete(String uuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findByUuid(uuid);
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                user.setDeleted(true);
//                user.setActive(false);
//                userRepository.save(user);
//                apiResponse.setStatus(HttpStatus.OK);
//            }else {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }
//
//    @Override
//    public ApiResponse updateOwnAvatar(UpdateAvatarDto updateAvatarDto) {
//        ApiResponse apiResponse = new ApiResponse();
//        String uuid = adminAuthorization.getUserIdentifier();
//        Optional<User> optionalUser = userRepository.findByUuid(uuid);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.setAvatarUrl(updateAvatarDto.getAvatarUrl());
//            userRepository.save(user);
//            apiResponse.setStatus(HttpStatus.OK);
//        } else {
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//            apiResponse.setMessage(Message.NOT_FOUND.getValue());
//        }
//        return apiResponse;
//    }
//
//    private void setUserRoleList(List<UserCompanies> userCompanies, List<DisplayUserRoleCompanyDto> userRolesList) {
//        for (UserCompanies userCompany : userCompanies) {
//            List<Role> userRoles = userRoleRepository.findRoleByUserCompanyId(userCompany.getId());
//            List<SimpleRoleDto> roles = new ArrayList<>();
//            for (Role role : userRoles) {
//                roles.add(modelMapper.map(role, SimpleRoleDto.class));
//            }
//            SimpleCompanyDto company = modelMapper.map(userCompany.getCompanies(), SimpleCompanyDto.class);
//            DisplayUserRoleCompanyDto userRoleCompanyDto = new DisplayUserRoleCompanyDto(roles, company);
//            userRolesList.add(userRoleCompanyDto);
//        }
//    }

//    public Optional<User> findUserByIdentifier(UUID userUuid) {
//        return userRepository.findOneByIdentifier(userUuid.toString());
//    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findValidUser(String email) {
        Optional<User> optUser = userRepository.findByEmailAndStatus(email, "ACTIVE");
        return optUser;
//        if (optUser.isPresent()) {
//            User user = optUser.get();
//            Company mainCoy = companiesRepository.findDefaultCompanyByEntityId(user.getEntity().getId());
//            if (mainCoy != null && mainCoy.isActive()) {
//                return optUser;
//            } else {
//                Optional<User> emptyUser = Optional.empty();
//                return emptyUser;
//            }
//        } else {
//            return optUser;
//        }
    }


//	public ApiResponse findEntityAdminDetails(String companyUuid) {
//		ApiResponse apiResponse = new ApiResponse();
//		try {
//			Company company = companiesRepository.findByUuid(companyUuid);
//			if (company == null) {
//				apiResponse.setData(null);
//			}else {
//				Company defaultCompany = companiesRepository.findDefaultCompanyByEntityId(company.getEntity().getId());
//				//find the entity admin and returns him as the point of contact
//	            List<User> entityAdmin = userRoleRepository.findUsersByCompanyUuidAndRoleCode(defaultCompany.getUuid(), Configs.ENTITY_ADMIN.getValue());
//	            //There should be only one entity admin
//	            UserDetailsAPIDto userDto = modelMapper.map(entityAdmin.get(0), UserDetailsAPIDto.class);
//				apiResponse.setData(userDto);
//			}
//			apiResponse.setStatus(HttpStatus.OK);
//		} catch (Exception e) {
//			apiResponse.setError(e.getMessage());
//			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//			log.error(e.getMessage());
//		}
//		return apiResponse;
//	}
//
//	public ApiResponse findCompaniesAndAdminDetails(List<String> companyUuidList) {
//		ApiResponse apiResponse = new ApiResponse();
//		try {
//			CompanyDetailsUserDetailsListAPIDto returnDto = new CompanyDetailsUserDetailsListAPIDto();
//			List<CompanyDetailsUserDetailsAPIDto> companyAndUserDtoList = new ArrayList<>();
//			for (String companyUuid: companyUuidList) {
//				Company company = companiesRepository.findByUuid(companyUuid);
//				CompanyDetailsUserDetailsAPIDto newDto = new CompanyDetailsUserDetailsAPIDto();
//				if (company == null) {
//					continue;
//				}else {
//					CompanyDetailsAPIDto companyDto = modelMapper.map(company, CompanyDetailsAPIDto.class);
//					newDto.setCompanyDetails(companyDto);
//
//					Company defaultCompany = companiesRepository.findDefaultCompanyByEntityId(company.getEntity().getId());
//					if (defaultCompany == null) {
//					    continue;
//                    }
//					//find the entity admin and returns him as the point of contact
//		            List<User> entityAdmin = userRoleRepository.findUsersByCompanyUuidAndRoleCode(defaultCompany.getUuid(), Configs.ENTITY_ADMIN.getValue());
//		            //There should be only one entity admin
//		            UserDetailsAPIDto userDto = modelMapper.map(entityAdmin.get(0), UserDetailsAPIDto.class);
//					newDto.setUserDetails(userDto);
//				}
//				companyAndUserDtoList.add(newDto);
//			}
//			returnDto.setCompanyUserDetailsList(companyAndUserDtoList);
//			apiResponse.setData(returnDto);
//			apiResponse.setStatus(HttpStatus.OK);
//		} catch (Exception e) {
//			apiResponse.setError(e.getMessage());
//			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//			log.error(e.getMessage());
//		}
//		return apiResponse;
//	}
//
//    public ApiResponse getUserDetailsByEmail(String email, String companyUuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        Company company = companiesRepository.findByUuid(companyUuid);
//        Optional<User> optionalUser = userCompaniesRepository.findUserByCompanyIdAndUserEmail(company.getId(), email);
//
//        if (optionalUser.isPresent()) {
//            UserDetailsDto userDto = modelMapper.map(optionalUser.get(), UserDetailsDto.class);
//            apiResponse.setData(userDto);
//        } else {
//            apiResponse.setData(null);
//        }
//        apiResponse.setStatus(HttpStatus.OK);
//        return apiResponse;
//    }
//
//    @Override
//    public ApiResponse activateCompanyUser(String companyUuid, String userUuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findCompanyUser(companyUuid, userUuid);
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                user.setActive(true);
//                userRepository.save(user);
//                apiResponse.setStatus(HttpStatus.OK);
//            }else {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        }catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }
//
//    @Override
//    public ApiResponse deactivateCompanyUser(String companyUuid, String userUuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findCompanyUser(companyUuid, userUuid);
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                user.setActive(false);
//                userRepository.save(user);
//                apiResponse.setStatus(HttpStatus.OK);
//            }else {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        }catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }
//
//	@Transactional(rollbackFor = Exception.class)
//	public ApiResponse createFiUser(@Valid FiUserDto userDto) {
//		ApiResponse apiResponse = new ApiResponse();
//		try {
//			Optional<User> optionalUser = userRepository.findByUuid(userDto.getLoggedInUserUuid());
//			if (optionalUser.isPresent()) {
//				User User = createUserFromUserCreateDto(userDto);
//				apiResponse.setData(User.getUuid());
//				apiResponse.setStatus(HttpStatus.OK);
//			}
//			return apiResponse;
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			apiResponse.setMessage(e.getMessage());
//			apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//			return apiResponse;
//		}
//	}

//	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, EntityAlreadyExistsException.class })
//	public User createUserFromUserCreateDto(FiUserDto userDto) throws Exception {
//		User User = new User();
//		if (Objects.isNull(userDto.getUuid())) {
//			Optional<User> checkEmail = userRepository.findByEmail(userDto.getEmail());
//			if (checkEmail.isPresent()) {
//				throw new Exception(Message.NON_UNIQUE_EMAIL.getValue());
//			}
//		}
//		if (Objects.nonNull(userDto.getUuid())) {
//			Optional<User> optionalUser = userRepository.findByUuid(userDto.getUuid());
//			User = optionalUser.get();
//		}
//		String password;
//		if (userDto.getPassword() == null || userDto.getPassword().isBlank() || userDto.getPassword().isEmpty()) {
//			RandomString randomString = new RandomString(Integer.parseInt(Configs.DEFAULT_PWD_LENGTH.getValue()));
//			password = randomString.nextString();
//		} else {
//			password = userDto.getPassword();
//		}
//		String encryptPassword = passwordProviderService.passwordEncoder().encode(password);
//		User user = new User();
//		user.setFiUuid(userDto.getFiUuid());
//	    user.setUuid(UUID.randomUUID().toString());
//		user.setPassword(encryptPassword);
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setActive(true);
//		user.setDesignation(userDto.getDesignation());
//		user.setWorkNumber(userDto.getWorkNumber());
//		user.setCountryCode(userDto.getCountryCode());
//		user.setRemarks(userDto.getRemarks());
//		User savedUser = userRepository.save(user);
//		FinancialInstitution financeInstitution = financialInstitutionRepository.findByUuid(userDto.getFiUuid());
//		UserFinancialInstitution newUserFinancialInstitution = new UserFinancialInstitution(savedUser,financeInstitution);
//		UserFinancialInstitution savedUserFinancialInstitution = userFinancialInstitutionRepository.save(newUserFinancialInstitution);
//		Role role = roleRepository.findByRoleCode(userDto.getRole());
//		UserRole newUserRole = new UserRole();
//		newUserRole.setRole(role);
//		newUserRole.setUserFinancialInstitution(savedUserFinancialInstitution);
//		userRoleRepository.save(newUserRole);
//			FIUserCreateDto userDetailsDto = modelMapper.map(savedUser, FIUserCreateDto.class);
//			messageService.notifyFiUserCreated(userDetailsDto);
//		UserSettings userSettings = new UserSettings();
//		userSettings.set2Fa(false);
//		userSettings.setLanguage(languageRepository.findByLanguageCode(Configs.DEFAULT_LANGUAGE.getValue()));
//		userSettings.setMustSetPassword(true);
//		userSettings.setSecret(multiFactorService.generateSecretKey());
//		userSettings.setUser(savedUser);
//		settingRepository.save(userSettings);
//		EmailDto emailDTO = new EmailDto(user.getEmail(), Message.EMAIL_ACCOUNT_SETUP_SUBJECT.getValue(), null,
//				Message.emailAccountSetupMessages(password, connexEmailHyperlinkHost));
//		emailDTO.getBody().setToName(Collections.singletonList(user.getName()));
//		emailDTO.getBody().setType(null);
//		emailDTO.getBody().setTemplateName(AppConfig.EMAIL_TEMPLATE_NOTIFICATION);
//		emailService.sendEmail(emailDTO);
//		return user;
//	}


//	public ApiResponse updateFiUser(FiUserDto userDto) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findByUuid(userDto.getUuid());
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                if (!user.getEmail().equals(userDto.getEmail())) {
//                    Optional<User> checkEmail = userRepository.findByEmail(userDto.getEmail());
//                    if (checkEmail.isPresent() && !checkEmail.get().getUuid().equals(userDto.getUuid())) {
//                        throw new Exception(Message.NON_UNIQUE_EMAIL.getValue());
//                    }
//                }
//                user.setName(userDto.getName());
//                user.setEmail(userDto.getEmail());
//                user.setUuid(userDto.getUuid());
//                user.setFiUuid(userDto.getFiUuid());
//                user.setDesignation(userDto.getDesignation());
//                user.setWorkNumber(userDto.getWorkNumber());
//                user.setCountryCode(userDto.getCountryCode());
//                user.setActive(userDto.isActive());
//                user.setRemarks(userDto.getRemarks());
//                User savedUser = userRepository.save(user);
//        		FIUserCreateDto userDetailsDto = modelMapper.map(savedUser, FIUserCreateDto.class);
//                messageService.notifyFiUserUpdated(userDetailsDto);
//                apiResponse.setStatus(HttpStatus.OK);
//                apiResponse.setData(user);
//            }else {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.NOT_FOUND.getValue());
//            }
//            return apiResponse;
//        }catch (Exception e) {
//            log.error(e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            return apiResponse;
//        }
//    }

//	public ApiResponse resetFATwo(ResetFAPassword resetFAPassword) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Optional<User> optionalUser = userRepository.findByUuid(resetFAPassword.getUuid());
//          //  User loginUser = userRepository.findById(adminAuthorization.getCurrentUserId()).get();
//            User User = optionalUser.get();
//            List<Role> loginUserRole = userRoleRepository.findRoleByFiUserUuid(User.getUuid());
//            if (optionalUser.isEmpty()) {
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                apiResponse.setMessage(Message.USER_NOT_FOUND.getValue());
//                return apiResponse;
//            }
//            User resetPasswordUser = optionalUser.get();
//            loginUserRole.stream().filter(role -> role.getRoleCode().equals(Configs.DOXA_ADMIN.getValue()));
//            //if there are no DOXA ADMIN under the user's role, check if the resetPassword User has the same entity Id as the login user
//            if (loginUserRole.size() == 0) {
//                if (!User.getUuid().equals(resetPasswordUser.getUuid())) {
//                    apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
//                    apiResponse.setMessage(Message.UNAUTHORIZED.getValue());
//                    return apiResponse;
//                }
//            }
//            if (resetTwoFA(optionalUser)) {
//                User user = userRepository.findByUuid(resetFAPassword.getUuid()).get();
//                //String to, String subject, String title, String message
//                EmailDto emailDTO = new EmailDto(user.getEmail(), Message.EMAIL_RESET_2FA_SUBJECT.getValue(), Message.EMAIL_RESET_2FA_TITLE.getValue(), Message.EMAIL_RESET_2FA_MESSAGES.getValue());
//                emailService.sendEmail(emailDTO); // TODO: Send email
//                apiResponse.setStatus(HttpStatus.OK);
//                return apiResponse;
//            }
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(Message.SERVER_ERROR.getValue());
//            return apiResponse;
//        } catch (Exception e) {
//            LOG.error(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(e.getMessage());
//            return apiResponse;
//        }
//    }

//	public ApiResponse getAllFilteredUsers(String s, String q, Integer pageNumber, Integer pageSize, String fiUuid,
//			String orderBy) {
//		ApiResponse apiResponse = new ApiResponse();
//		Pageable pageable = null;
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(User.class);
//		Root root = criteriaQuery.from(User.class);
//		Predicate companyPredicate = criteriaBuilder.equal(root.get("fiUuid"), fiUuid);
//		Predicate predicate = null;
//		if (Objects.nonNull(s)) {
//			predicate = specificationService.getPredicateV1(criteriaBuilder, s, root);
//			criteriaQuery.where(predicate, criteriaBuilder.equal(root.get("fiUuid"), fiUuid));
//		} else {
//			criteriaQuery.where(criteriaBuilder.equal(root.get("fiUuid"), fiUuid));
//		}
//		criteriaQuery.select(root);
//		pageable = PageRequest.of(pageNumber, pageSize);
//		if (StringUtils.isNotEmpty(orderBy)) {
//			String fieldName = orderBy.substring(0, orderBy.indexOf(":"));
//			Path<?> path = null;
//			if (fieldName != null) {
//				path = root.get(fieldName);
//			}
//			criteriaQuery.orderBy(orderBy.contains("asc") ? criteriaBuilder.asc(path) : criteriaBuilder.desc(path));
//		}
//		TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
//		int total = typedQuery.getResultList().size();
//		List<User> results = typedQuery.setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
//		List<FIUsersDto> userDtoList = getUserDtoList(results);
//		apiResponse.setStatus(HttpStatus.OK);
//		apiResponse.setData(new PageImpl<FIUsersDto>(userDtoList, pageable, total));
//		return apiResponse;
//
//	}

//	private List<FIUsersDto> getUserDtoList(List<User> userList) {
//		List<FIUsersDto> userDtoList = new ArrayList<FIUsersDto>();
//		userList.forEach(user -> {
//			FIUsersDto userDto = new FIUsersDto();
//			userDto.setId(user.getId());
//			userDto.setName(user.getName());
//			userDto.setEmail(user.getEmail());
//			userDto.setDesignation(user.getDesignation());
//			userDto.setWorkNumber(user.getWorkNumber());
//			userDto.setActive(user.isActive());
//			userDto.setCreatedAt(user.getCreatedAt());
//			userDto.setRemarks(user.getRemarks());
//			userDto.setUuid(user.getUuid());
//			userDto.setCountryCode(user.getCountryCode());
//			userDto.setFiUuid(user.getFiUuid());
//			userDtoList.add(userDto);
//		});
//		return userDtoList;
//	}

    public ApiResponse getFiUser(String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User fiUser = userOptional.get();
                FIUsersDto userDto = modelMapper.map(fiUser, FIUsersDto.class);
                apiResponse.setData(userDto);
                apiResponse.setStatus(HttpStatus.OK);
            } else {
                apiResponse.setData("Not Found");
                apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            apiResponse.setData(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse retrieveEntitiesUsers() {
        return null;
    }

    @Override
    public ApiResponse updateUser(UserDetailsDto userDto) {
        return null;
    }

    @Override
    public ApiResponse deactivateUser(String uuid) {
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
}
