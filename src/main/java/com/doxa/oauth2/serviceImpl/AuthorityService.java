package com.doxa.oauth2.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.doxa.oauth2.auth.Action;
import com.doxa.oauth2.auth.Authority;
import com.doxa.oauth2.mapper.AuthorityMapper;
import com.doxa.oauth2.models.authorities.core.Module;
import com.doxa.oauth2.models.authorities.core.UserFeature;
import com.doxa.oauth2.models.companies.Company;
import com.doxa.oauth2.models.user.FeatureDetail;
import com.doxa.oauth2.models.user.Role;
import com.doxa.oauth2.repositories.companies.CompanyRepository;
import com.doxa.oauth2.repositories.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doxa.oauth2.DTO.authority.CoreModuleDto;
import com.doxa.oauth2.DTO.authority.GrantUserAuthority;
import com.doxa.oauth2.common.CompanyJWT;
import com.doxa.oauth2.common.DoxaAuthenticationManager;
import com.doxa.oauth2.config.CacheConfiguration;
import com.doxa.oauth2.config.Message;
import com.doxa.oauth2.exceptions.AccessDeniedException;
import com.doxa.oauth2.exceptions.BadRequestException;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
//import com.doxa.oauth2.models.financialInstitution.UserFinancialInstitution;
import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.RbacUserRole;
//import com.doxa.oauth2.models.user.UserCompanies;
//import com.doxa.oauth2.models.user.UserRole;
import com.doxa.oauth2.repositories.authorities.core.FeatureRepository;
//import com.doxa.oauth2.repositories.authorities.core.CoreModuleRepository;
//import com.doxa.oauth2.repositories.authorities.core.SubscriptionRepository;
//import com.doxa.oauth2.repositories.authorities.core.UserPrivilegeRepository;
//import com.doxa.oauth2.repositories.financialInstitution.FiUserRepository;
//import com.doxa.oauth2.repositories.user.UserCompaniesRepository;
//import com.doxa.oauth2.repositories.user.UserRbacRoleRepository;
import com.doxa.oauth2.repositories.user.UserRepository;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.services.IAuthorityService;

@Service
public class AuthorityService implements IAuthorityService {
//
//    @Autowired
//    private CoreModuleRepository moduleRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private UserPrivilegeRepository userPrivilegeRepository;
//
//    @Autowired
//    private SubscriptionRepository subscriptionRepository;
//
//    @Autowired
//    private UserCompaniesRepository userCompaniesRepository;

//    @Autowired
//    private DoxaAuthenticationManager authenticationManager;

    @Autowired
    private CacheServiceImpl cacheService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private UserRbacRoleRepository userRbacRoleRepository;
//
//    @Autowired
//    private FiUserRepository fiUserRepository;


    /**
     * Role Allowed: DOXA_ADMIN
     * Get all the core modules
     *
     * @return ApiResponse
     */
//    @Override
//    public ApiResponse getAllCoreModules() {
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setStatus(HttpStatus.OK);
//        List<Module> coreModules = moduleRepository.findAll();
//        apiResponse.setData(coreModules);
//        return apiResponse;
//    }
//
//    @Override
//    public ApiResponse getCompanyModules(String companyUuid) throws AccessDeniedException {
//        ApiResponse apiResponse = new ApiResponse();
//        List<CoreModuleDto> modules = new ArrayList<>();
//        List<Module> companyModules = moduleRepository.listSubscription(companyUuid);
//        for (Module module : companyModules) {
//            CoreModuleDto coreModuleDto = new CoreModuleDto();
////            coreModuleDto.setModuleCode(module.getModuleCode());
////            coreModuleDto.setModuleName(module.getModuleName());
////            coreModuleDto.setUuid(module.getUuid());
//            modules.add(coreModuleDto);
//        }
//        apiResponse.setData(modules);
//        return apiResponse;
//    }

    /**
     * Role Allowed: DOXA_ADMIN
     * Assign modules to company
     *
     * @param companyUuid  company is assigned
     * @param featureCodes list modules code
     * @return
     */
//    @Override
//    @Transactional
//    public ApiResponse assignModuleToCompany(String companyUuid, List<String> featureCodes) {
//        // get features from DB
//        List<Feature> dbFeatures = featureRepository.findAllByFeatureCodes(featureCodes);
//        if (dbFeatures.size() != featureCodes.size()) {
//            Set<String> dbCodes = dbFeatures.stream().map(Feature::getFeatureCode).collect(Collectors.toSet());
//            Set<String> invalids = new HashSet<>(featureCodes);
//            invalids.removeAll(dbCodes);
//            throw new BadRequestException("Invalid feature code: " + String.join(",", invalids));
//        }
//        // delete current subscription
//        subscriptionRepository.deleteAllByCompanyUuid(companyUuid);
//        // build the subscription model
//        List<Subscription> subscriptions = dbFeatures.stream().map(code -> {
//            Subscription subscription = new Subscription();
//            subscription.setCompanyUuid(companyUuid);
////            subscription.setStartDate(); // ignore for now
//            subscription.setFeature(code);
//            subscription.setFeatureName(code.getFeatureName());
//            subscription.setFeatureCode(code.getFeatureCode());
//            subscription.setModuleCode(code.getModuleCode());
//            subscription.setUuid(UUID.randomUUID().toString());
//            return subscription;
//        }).collect(Collectors.toList());
//
//        subscriptionRepository.saveAll(subscriptions);
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setStatus(HttpStatus.OK);
//        return apiResponse;
//    }

//    public ApiResponse getCompanySubscriptionPrivate(String companyUuid) {
//        List<Subscription> subscriptions = subscriptionRepository.findByCompanyUuid(companyUuid);
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setStatus(HttpStatus.OK);
//        apiResponse.setData(subscriptions);
//        return apiResponse;
//    }
//
//    @Override
//    public ApiResponse getCompanySubscription(String companyUuid) throws AccessDeniedException {
//        if (!authenticationManager.isDoxaAdmin()) {
//            final String userUuid = authenticationManager.getByKey("sub");
//            User user = userRepository.findByUuid(userUuid).orElseThrow(() -> new AccessDeniedException(Message.ACCESS_DENIED.getValue()));
//            if (user.getCompanies().stream().noneMatch(companyAuthority -> companyAuthority.getCompanies().getUuid().equals(companyUuid))) {
//                throw new AccessDeniedException(Message.ACCESS_DENIED.getValue());
//            }
//        }
//
//        List<Subscription> subscriptions = subscriptionRepository.findByCompanyUuid(companyUuid);
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setStatus(HttpStatus.OK);
//        apiResponse.setData(subscriptions);
//        return apiResponse;
//    }
    @Override
    public ApiResponse getUserAuthority(String companyUuid, String userUuid) throws ObjectDoesNotExistException {
        List<Authority> rbacPrivileges = findAuthorityByUserUuid(companyUuid, userUuid);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setData(rbacPrivileges);
        return apiResponse;
    }

//    private List<Authority> getAdminPrivilege(String companyUuid, String userUuid) {
//        List<UserPrivilege> privileges = new ArrayList<>();
//        List<Subscription> subscriptions = subscriptionRepository.findByCompanyUuid(companyUuid);
//        for (Subscription subscription : subscriptions) {
//            UserPrivilege privilege = new UserPrivilege();
//            privilege.setCompanyUuid(companyUuid);
//            privilege.setFeatureCode(subscription.getFeatureCode());
//            privilege.setUserUuid(userUuid);
//            privilege.setFeature(subscription.getFeature());
//            UserPrivilegeAction actions = new UserPrivilegeAction();
//            actions.setAdminPrivilege();
//            privilege.setActions(actions);
//            privileges.add(privilege);
//        }
//        return privileges;
//    }

    @Transactional
    @Override
    public ApiResponse grantAuthorityToUser(String companyId, String userId, List<GrantUserAuthority> features) {
        Set<String> featureCodes = features.stream().map(GrantUserAuthority::getFeatureCode).collect(Collectors.toSet());

        // Check if user is under company
//        UserCompanies userCompanies = userCompaniesRepository.findOneByUserUuidAndCompanyUuid(companyUuid, userUuid);

//        if (userCompanies == null) {
//            throw new BadRequestException("User not found");
//        }
//
//        List<Feature> dbFeatures = featureRepository.findAllByFeatureCodes(featureCodes);
//        if (dbFeatures.size() != featureCodes.size()) {
//            Set<String> dbCodes = dbFeatures.stream().map(Feature::getFeatureCode).collect(Collectors.toSet());
//            Set<String> invalids = new HashSet<>(featureCodes);
//            invalids.removeAll(dbCodes);
//            throw new BadRequestException("Invalid feature code: " + String.join(",", invalids));
//        }
//
//        Map<String, Feature> featureMap = new HashMap<>();
//        for (Feature f : dbFeatures) {
//            featureMap.put(f.getFeatureCode(), f);
//        }
//
//        // check if company has been assigned those features
//        List<Subscription> compSubscription = subscriptionRepository.findByCompanyUuidAndFeature(companyUuid, featureCodes);
//        if (compSubscription.size() != featureCodes.size()) {
//            throw new BadRequestException("Company has insufficient permissions");
//        }
//        userPrivilegeRepository.deleteAllByUserUuidAndCompanyUuid(companyUuid, userUuid);
//
//        List<UserPrivilege> userPrivileges = features.stream().map(f -> {
//            UserPrivilege userPrivilege = new UserPrivilege();
//            userPrivilege.setUserUuid(userUuid);
//            userPrivilege.setCompanyUuid(companyUuid);
//            userPrivilege.setFeature(featureMap.get(f.getFeatureCode()));
//            userPrivilege.setFeatureCode(f.getFeatureCode());
//            userPrivilege.setUuid(UUID.randomUUID().toString());
//            UserPrivilegeAction action = new UserPrivilegeAction();
//            action.setUuid(UUID.randomUUID().toString());
//            for (String act : f.getActions()) {
//                switch (act) {
//                    case "APPROVE":
//                        action.setApprove(true);
//                        break;
//                    case "READ":
//                        action.setRead(true);
//                        break;
//                    case "WRITE":
//                        action.setWrite(true);
//                        break;
//                    default:
//                }
//            }
//            userPrivilege.setActions(action);
//            action.setUserPrivilege(userPrivilege);
//            return userPrivilege;
//        }).collect(Collectors.toList());
//        userPrivilegeRepository.saveAll(userPrivileges);
        // check does this company has role list?
        companyRepository.findById(companyId);
        List<Role> roleList = roleRepository.findByCompanyId(companyId);
        Map<String, String> featureMap = new HashMap<>();


        String cacheKey = "authority" + companyId + userId;
        cacheService.evictCache(CacheConfiguration.AUTHORITY_CACHE, cacheKey);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage(Message.CREATE_SUCCESSFUL.getValue());
        return apiResponse;
    }

    /**
     * Called privately
     * Check if user has permission on feature
     *
     * @param companyId   Which user belongs to
     * @param userId      User's uuid
     * @param featureCode feature which we want to check
     * @return ApiResponse
     */
    @Override
    public ApiResponse checkUserPermission(String companyId, String userId, String featureCode) {
//        Subscription subscription = subscriptionRepository.findByCompanyUuidAndFeatureCode(companyUuid, featureCode);
        //check has featureCode been assigned for companyId
        Action action = new Action();
        List<Role> roleList = roleRepository.findByCompanyId(companyId);
        boolean assigned = checkAssignedFeature(roleList, featureCode, action);
        if (assigned == false)
            throw new BadRequestException("This feature " + featureCode + " hasn't been assigned for the company: " + companyId);
        // check has featureCode been assigned for userId
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent())
            throw new BadRequestException("No existed user: " + userId);
        User user = optionalUser.get();
        List<Role> roles = user.getRoles();
        assigned = checkAssignedFeature(roles, featureCode, action);
        if (assigned == false)
            throw new BadRequestException("This feature " + featureCode + " hasn't been assigned for the user: " + userId);
        UserFeature userFeature = new UserFeature(userId, companyId, featureCode, action);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userFeature);
        apiResponse.setStatus(HttpStatus.NOT_FOUND);
        return apiResponse;
    }

    private boolean checkAssignedFeature(List<Role> roleList, String featureCode, Action action) {
        boolean assigned = false;
        for (Role r : roleList) {
            List<FeatureDetail> featureDetailList = r.getFeatureDetails();
            for (FeatureDetail f : featureDetailList) {
                if (f.getFeature().getCode().equals(featureCode)) {
                    assigned = true;
                    action = new Action(f.isRead(), f.isWrite(), f.isApprove());
                    break;
                }
            }
            if (assigned == true) break;
        }
        return assigned;
    }


    public List<CompanyJWT> getAuthorities(User user) {
        List<CompanyJWT> listCompanies = new ArrayList<>();
        Optional<Company> optionalCompany = companyRepository.findById(user.getCompanyId());
        if (!optionalCompany.isPresent()) {
//            return listCompanies;
        } else {
            Company company = optionalCompany.get();
            List<String> featureStr = company.getFeatureList().stream().map(e -> e.getFeature().getCode()).collect(Collectors.toList());
            CompanyJWT companyJWT = new CompanyJWT();
            String key = user.getCompanyId();
            companyJWT.setUuid(key);
            companyJWT.setRoles(String.join(",", featureStr));
            listCompanies.add(companyJWT);
        }

//        List<UserCompanies> companies = user.getCompanies();
//        for (UserCompanies userCompany : companies) {
//            // ignore inactive company
//            if (!userCompany.getCompanies().isActive()) {
//                continue;
//            }
//            CompanyJWT companyJWT = new CompanyJWT();
//            String key = userCompany.getCompanies().getUuid();
//            companyJWT.setUuid(key);
//            List<UserFeature> roleList = userCompany.getUserRoles();
//            if (!user.isDoxaAdmin()) {
//                List<String> roles = new ArrayList<>();
//                for (UserFeature role : roleList) {
//                    roles.add(role.getRole().getRoleCode());
//                }
//                companyJWT.setRoles(String.join(",", roles));
//            } else {
//                companyJWT.setAuthorities("");
//                companyJWT.setRoles("DOXA_ADMIN");
//            }
//            listCompanies.add(companyJWT);
//        }

        return listCompanies;
    }

//    private String buildAdminAuthorities(String companyUuid) {
//        List<String> actions = new ArrayList<>() {{
//            add("read");
//            add("write");
//            add("approve");
//        }};
//        Set<String> authorities = new HashSet<>();
//        List<Subscription> subscriptions = subscriptionRepository.findByCompanyUuid(companyUuid);
//        for (Subscription subscription : subscriptions) {
//            if (isEntitySettings(subscription.getModuleCode())) {
//                authorities.add(subscription.getFeatureCode() + ":all");
//                continue;
//            }
//            for (String action : actions) {
//                authorities.add(subscription.getFeatureCode() + ":" + action);
//            }
//        }
//        return String.join(" ", authorities);
//    }
//
//    private boolean isEntitySettings(String module) {
//        return module.equals("TRANSACTION_SETTING") || module.equals("ENTITY_SETTING");
//    }
//
//    private String buildAuthority(List<UserPrivilege> userPrivileges) {
//        Set<String> authorities = new HashSet<>();
//        for (UserPrivilege userPrivilege : userPrivileges) {
//            UserPrivilegeAction action = userPrivilege.getActions();
//            String featureCode = userPrivilege.getFeatureCode();
//            if (isEntitySettings(userPrivilege.getFeature().getModuleCode())) {
//                authorities.add(featureCode + ":all");
//            } else {
//                if (action.isApprove()) {
//                    authorities.add(featureCode + ":approve");
//                }
//                if (action.isRead()) {
//                    authorities.add(featureCode + ":read");
//                }
//                if (action.isWrite()) {
//                    authorities.add(featureCode + ":write");
//                }
//            }
//        }
//        return String.join(" ", authorities);
//    }

    public List<Authority> findAuthorityByUserUuid(String companyId, String userId) throws ObjectDoesNotExistException {
//        List<UserPrivilege> privileges;
        Optional<User> User = userRepository.findById(userId);
        if (User.isEmpty()) {
            throw new ObjectDoesNotExistException("Invalid user");
        }
        List<Authority> authorityList = new ArrayList<>();
        User user = User.get();
        List<Role> roleList = user.getRoles();
        for (Role r : roleList) {
            if (r.getStatus().equals("INACTIVE")) continue;
            for (FeatureDetail f : r.getFeatureDetails()) {
                Action action = new Action(f.isRead(), f.isWrite(), f.isApprove());
                authorityList.add(new Authority(f.getFeature().getCode(), action));
            }
        }
        return authorityList;
    }

    public boolean checkAuthority(List<Authority> rbacPrivileges, String authority) {
        String[] spliced = authority.split(":");
        String featureCode = spliced[0];
        String action = spliced[1];
        for (Authority userPrivilegeDto : rbacPrivileges) {
            if (userPrivilegeDto.getFeatureCode().equalsIgnoreCase(featureCode))
                switch (action.toLowerCase()) {
                    case "read":
                        if (userPrivilegeDto.getActions().isRead())
                            return true;
                    case "write":
                        if (userPrivilegeDto.getActions().isWrite())
                            return true;
                    case "approve":
                        if (userPrivilegeDto.getActions().isApprove())
                            return true;
                }
        }
        return false;
    }

    public boolean checkAuthority(String companyUuid, String userUuid, String... authorities) throws ObjectDoesNotExistException {
        List<Authority> rbacPrivileges = findAuthorityByUserUuid(companyUuid, userUuid);
        for (String authority : authorities) {
            if (!checkAuthority(rbacPrivileges, authority)) return false;
        }
        return true;
    }

    /**
     * Role Allowed: DOXA_ADMIN
     * Assign modules to company
     *
     * @param companyUuid  company is assigned
     * @param featureCodes list modules code
     * @return
     */
//    @Override
//    @Transactional
//    public ApiResponse assignModuleToFinancialInstitution(String fiUuid, List<String> featureCodes) {
//        // get features from DB
//        List<Feature> dbFeatures = featureRepository.findAllByFeatureCodes(featureCodes);
//        if (dbFeatures.size() != featureCodes.size()) {
//            Set<String> dbCodes = dbFeatures.stream().map(Feature::getFeatureCode).collect(Collectors.toSet());
//            Set<String> invalids = new HashSet<>(featureCodes);
//            invalids.removeAll(dbCodes);
//            throw new BadRequestException("Invalid feature code: " + String.join(",", invalids));
//        }
//        // delete current subscription
//        subscriptionRepository.deleteAllByFiUuid(fiUuid);
//        // build the subscription model
//        List<Subscription> subscriptions = dbFeatures.stream().map(code -> {
//            Subscription subscription = new Subscription();
//            subscription.setFiUuid(fiUuid);
//            subscription.setFeature(code);
//            subscription.setFeatureName(code.getFeatureName());
//            subscription.setFeatureCode(code.getFeatureCode());
//            subscription.setModuleCode(code.getModuleCode());
//            subscription.setUuid(UUID.randomUUID().toString());
//            return subscription;
//        }).collect(Collectors.toList());
//
//        subscriptionRepository.saveAll(subscriptions);
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setStatus(HttpStatus.OK);
//        return apiResponse;
//    }

//	public ApiResponse grantUserAuthority(String fiUuid, String userUuid, List<GrantUserAuthority> features) {
//        Set<String> featureCodes = features.stream().map(GrantUserAuthority::getFeatureCode).collect(Collectors.toSet());
//        // Check if user is under financialInstitution
//        UserFinancialInstitution userFinancialInstitution = fiUserRepository.findOneByUserUuidAndfiUuid(fiUuid, userUuid);
//        if (userFinancialInstitution == null) {
//            throw new BadRequestException("User not found");
//        }
//
//        List<Feature> dbFeatures = featureRepository.findAllByFeatureCodes(featureCodes);
//        if (dbFeatures.size() != featureCodes.size()) {
//            Set<String> dbCodes = dbFeatures.stream().map(Feature::getFeatureCode).collect(Collectors.toSet());
//            Set<String> invalids = new HashSet<>(featureCodes);
//            invalids.removeAll(dbCodes);
//            throw new BadRequestException("Invalid feature code: " + String.join(",", invalids));
//        }
//
//        Map<String, Feature> featureMap = new HashMap<>();
//        for (Feature f : dbFeatures) {
//            featureMap.put(f.getFeatureCode(), f);
//        }
//
//        // check if company has been assigned those features
//        List<Subscription> compSubscription = subscriptionRepository.findByFiUuidAndFeature(fiUuid, featureCodes);
//        if (compSubscription.size() != featureCodes.size()) {
//            throw new BadRequestException("Company has insufficient permissions");
//        }
//        userPrivilegeRepository.deleteAllByUserUuidAndfiUuid(fiUuid, userUuid);
//
//        List<UserPrivilege> userPrivileges = features.stream().map(f -> {
//            UserPrivilege userPrivilege = new UserPrivilege();
//            userPrivilege.setUserUuid(userUuid);
//            userPrivilege.setFiUuid(fiUuid);
//            userPrivilege.setFeature(featureMap.get(f.getFeatureCode()));
//            userPrivilege.setFeatureCode(f.getFeatureCode());
//            userPrivilege.setUuid(UUID.randomUUID().toString());
//            UserPrivilegeAction action = new UserPrivilegeAction();
//            action.setUuid(UUID.randomUUID().toString());
//            for (String act : f.getActions()) {
//                switch (act) {
//                    case "APPROVE":
//                        action.setApprove(true);
//                        break;
//                    case "READ":
//                        action.setRead(true);
//                        break;
//                    case "WRITE":
//                        action.setWrite(true);
//                        break;
//                    default:
//                }
//            }
//            userPrivilege.setActions(action);
//            action.setUserPrivilege(userPrivilege);
//            return userPrivilege;
//        }).collect(Collectors.toList());
//        userPrivilegeRepository.saveAll(userPrivileges);
//        String cacheKey = "authority" + fiUuid + userUuid;
//        cacheService.evictCache(CacheConfiguration.AUTHORITY_CACHE, cacheKey);
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setStatus(HttpStatus.OK);
//        apiResponse.setMessage(Message.CREATE_SUCCESSFUL.getValue());
//        return apiResponse;
//    }

//	@Override
//	public ApiResponse getFiSubscription(String fiUuid) throws AccessDeniedException {;
//        List<Subscription> subscriptions = subscriptionRepository.findByFiUuid(fiUuid);
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setStatus(HttpStatus.OK);
//        apiResponse.setData(subscriptions);
//        return apiResponse;
//    }
}
