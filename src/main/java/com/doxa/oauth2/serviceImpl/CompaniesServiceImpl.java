package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.DTO.manageCompany.CompanyDetailDto;
import com.doxa.oauth2.DTO.privateAPI.CompanyDetailsAPIDto;
import com.doxa.oauth2.common.DoxaAuthenticationManager;
import com.doxa.oauth2.config.Message;
import com.doxa.oauth2.exceptions.BadRequestException;
import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
import com.doxa.oauth2.mapper.CompanyListMapper;
import com.doxa.oauth2.mapper.CompanyMapper;
//import com.doxa.oauth2.mapper.DocumentsMetaDataMapper;
import com.doxa.oauth2.microservices.interfaces.IMessageService;
//import com.doxa.oauth2.models.authorities.core.Subscription;
import com.doxa.oauth2.models.authorities.core.Feature;
import com.doxa.oauth2.models.companies.Company;
//import com.doxa.oauth2.models.companies.DocumentsMetaData;
//import com.doxa.oauth2.models.companies.DoxaEntity;
//import com.doxa.oauth2.models.companies.EntityRepresentative;
import com.doxa.oauth2.models.user.FeatureDetail;
import com.doxa.oauth2.models.user.Role;
import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.UserCompanies;
//import com.doxa.oauth2.models.user.UserRole;
import com.doxa.oauth2.repositories.authorities.core.FeatureRepository;
import com.doxa.oauth2.repositories.companies.CompanyRepository;
//import com.doxa.oauth2.repositories.companies.DocumentsMetadataRepository;
//import com.doxa.oauth2.repositories.companies.EntitiesRepository;
import com.doxa.oauth2.repositories.user.RoleRepository;
//import com.doxa.oauth2.repositories.user.UserCompaniesRepository;
import com.doxa.oauth2.repositories.user.UserRepository;
//import com.doxa.oauth2.repositories.user.UserRoleRepository;
import com.doxa.oauth2.responses.ApiResponse;
import com.doxa.oauth2.services.ICompaniesService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CompaniesServiceImpl implements ICompaniesService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    FeatureRepository featureRepository;

//    @Autowired
//    EntitiesRepository entitiesRepository;

    @Autowired
    RoleRepository roleRepository;

//    @Autowired
//    UserRoleRepository userRoleRepository;
//
//    @Autowired
//    UserCompaniesRepository userCompaniesRepository;

//    @Autowired
//    DocumentsMetadataRepository documentsRepository;

    @Autowired
    private CompanyListMapper companyListMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DoxaAuthenticationManager authenticationManager;

//    @Autowired
//    DocumentsMetaDataMapper documentsMetadataMapper;

    @Autowired
    private IMessageService messageService;

//    @Autowired
//    private AuthorityService authorityService;


//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class})
//    public ApiResponse listCompanies() {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            List<DisplayCompanyListDto> companies = new ArrayList<>();
//            Optional<User> optionalUser = getUser();
//            // from userId get companies respectively/correspondingly
//            if (optionalUser.isPresent()) {
//                Company companyList = optionalUser.get().getCompany();
//                companies = companyListMapper.mapDisplayCompanyListDto(companyList);
//            }
//
//
////            if (optionalUser.isPresent()) {
////                Long entityId = optionalUser.get().getEntity().getId();
////                List<Company> companyList = new ArrayList<>(companiesRepository.findCompaniesByEntityId(entityId));
////                companies = companyListMapper.mapDisplayCompanyListDto(companyList);
//////                companies = companies.stream().peek(company -> company.setCreatedAt(company.getCreatedAt().atStartOfDay(ZoneId.systemDefault()).toInstant())).collect(Collectors.toList());
////            }
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setStatusCode(200);
//            apiResponse.setData(companies);
//        } catch (Exception e) {
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            apiResponse.setStatusCode(500);
//            e.printStackTrace();
//        }
//        return apiResponse;
//    }

//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class})
//    public ApiResponse listAllCompaniesUnderEntity(String entityUuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setStatus(HttpStatus.OK);
//        try {
//            DoxaEntity entity = entitiesRepository.findByUuid(entityUuid);
//            if (entity != null) {
//                List<Company> companyList = new ArrayList<>(companiesRepository.findAllCompaniesByEntityId(entity.getId()));
//                apiResponse.setData(companyList);
//            }
//            // else return empty
//        } catch (Exception e) {
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            e.printStackTrace();
//            apiResponse.setStatusCode(500);
//        }
//        return apiResponse;
//    }

//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class, DuplicateObjectException.class,
//            BadRequestException.class})
//    public ApiResponse createCompany(CreateCompanyDetailsDto createCompanyDetailsDto) {
//        Company company = companiesRepository
//                .findByCompanyRegistrationNumber(createCompanyDetailsDto.getCompanyRegistrationNumber());
//        DoxaEntity entity = entitiesRepository
//                .findByCompanyRegistrationNumber(createCompanyDetailsDto.getCompanyRegistrationNumber());
//        Optional<User> optionalUser = getUser();
//        User user = optionalUser.orElse(null);
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            if (company != null) {
//                throw new DuplicateObjectException(Message.COMPANY_EXISTS.getValue());
//            }
//            if (user == null) {
//                throw new BadRequestException(Message.COMPANY_CREATE_FAIL.getValue());
//            }
//            // Company must be one of buyer, supplier, developer
//            if (!createCompanyDetailsDto.isBuyer() && !createCompanyDetailsDto.isSupplier()) {
//                throw new BadRequestException("Company must be one of the following profiles: buyer, supplier");
//            }
//            Company newCompany = companyMapper.createCompany(createCompanyDetailsDto);
//            newCompany.setUuid(UUID.randomUUID().toString());
//            newCompany.setActive(true);
//            newCompany.setCreatedAt(Instant.now());
//            newCompany.setUpdatedAt(Instant.now());
//            newCompany.setOnboardingStatus(OnboardingStatusEnum.APPROVED.toString());
//            if (entity == null) {
//                newCompany.setEntity(user.getEntity());
//                newCompany.setMainCompany(false);
//                newCompany = companiesRepository.save(newCompany);
//                UserCompanies userCompanies = new UserCompanies(user, newCompany);
//                UserCompanies savedUserCompanies = userCompaniesRepository.save(userCompanies);
//                UserRole userRole = new UserRole();
//                userRole.setUserCompanies(savedUserCompanies);
//                userRole.setRole(roleRepository.findByRoleCode(Configs.COMPANY_ADMIN.getValue()));
//                userRoleRepository.save(userRole);
//            } else {
//                newCompany.setEntity(entity);
//                newCompany.setMainCompany(true);
//                List<EntityRepresentative> entityRepList = entity.getEntityRepresentativeList();
//                newCompany = companiesRepository.save(newCompany);
//                for (EntityRepresentative entityRep : entityRepList) {
//                    if (entityRep.getUserRole().equals(Configs.ENTITY_ADMIN_ROLE.getValue())) {
//                        UserCreateDto userDto = new UserCreateDto();
//                        userDto.setName(entityRep.getName());
//                        userDto.setEmail(entityRep.getEmail());
//                        userDto.setCountryCode(entityRep.getCountryCode());
//                        userDto.setWorkNumber(entityRep.getWorkNumber());
//                        userDto.setPassword("");
//                        userDto.setDesignation("");
//                        List<DisplayUserRoleCompanyDto> companiesRoles = new ArrayList<>();
//                        List<SimpleRoleDto> simpleRoleDtos = new ArrayList<>();
//                        SimpleRoleDto role1 = new SimpleRoleDto();
//                        role1.setRoleCode(Configs.ENTITY_ADMIN.getValue());
//                        simpleRoleDtos.add(role1);
//                        SimpleRoleDto role2 = new SimpleRoleDto();
//                        role2.setRoleCode(Configs.COMPANY_ADMIN.getValue());
//                        simpleRoleDtos.add(role2);
//                        SimpleCompanyDto companies = new SimpleCompanyDto();
//                        companies.setEntityName(newCompany.getEntityName());
//                        companies.setUuid(newCompany.getUuid());
//                        DisplayUserRoleCompanyDto userRole1 = new DisplayUserRoleCompanyDto(simpleRoleDtos, companies);
//
//                        companiesRoles.add(userRole1);
//                        userDto.setCompaniesRoles(companiesRoles);
//                        userService.createUserFromUserCreateDto(userDto, entity);
//                    }
//                }
//            }
//
//            for (DocumentsMetaData d : newCompany.getDocumentsMetaDataList()) {
//                d.setCompanyId(newCompany.getId());
//                documentsRepository.save(d);
//            }
//
//            // Assign module
//            if (createCompanyDetailsDto.getFeatures() != null && createCompanyDetailsDto.getFeatures().size() > 0) {
//                authorityService.assignModuleToCompany(newCompany.getUuid(), createCompanyDetailsDto.getFeatures());
//            }
//
//            // Notify other services that new company is created
//            // build the payload
//            CompanyDetailDto companyDetailDto = companyMapper.companyDetailDto(newCompany);
//            companyDetailDto.setCountryCurrency(createCompanyDetailsDto.getCountryCurrency());
//            messageService.notifyNewCompanyCreated(companyDetailDto);
//            apiResponse.setStatusCode(200);
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setMessage(Message.COMPANY_CREATED.getValue() + newCompany.getEntityName());
//            apiResponse.setData(newCompany);
//        } catch (DuplicateObjectException | BadRequestException e) {
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            log.error("createCompany {}: ", e.getMessage());
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatusCode(400);
//        } catch (Exception e) {
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            log.error("createCompany {}: ", e.getMessage());
//            apiResponse.setStatusCode(500);
//        }
//        return apiResponse;
//    }


//
    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class, ObjectDoesNotExistException.class})
    public ApiResponse getCompanyDetails(String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Optional<Company> optionalCompany = companyRepository.findById(id);
            if (!optionalCompany.isPresent()) {
                throw new ObjectDoesNotExistException(Message.COMPANY_NOT_FOUND.getValue());
            }
            Company company = optionalCompany.get();
            CompanyDetailDto companyDetailsDto = companyMapper.companyDetailDto(company);
//            List<EntityRepresentative> entityRepresentativeList = company.getEntity().getEntityRepresentativeList();
//            for (EntityRepresentative entityRepresentative : entityRepresentativeList) {
//                if (entityRepresentative.getUserRole().equals(EntityUserRole.ENTITY_REPRESENTATIVE)) {
//                    companyDetailsDto.setContactPersonEmail(entityRepresentative.getEmail());
//                    companyDetailsDto.setContactPersonWorkNumber(entityRepresentative.getWorkNumber());
//                    companyDetailsDto.setContactPersonName(entityRepresentative.getName());
//                    companyDetailsDto.setCountryCode(entityRepresentative.getCountryCode());
//                    break;
//                }
//            }
//
//            List<Subscription> subscriptions = (List<Subscription>) authorityService.getCompanySubscriptionPrivate(uuid).getData();
//            List<String> features = subscriptions.stream().map(Subscription::getFeatureCode).collect(Collectors.toList());
            List<Role> roleList = roleRepository.findByCompanyId(id);
            List<String> features = new ArrayList<>();
            roleList.stream().forEach(e-> e.getFeatureDetails().stream().forEach(f->features.add(f.getFeature().getName())));
            companyDetailsDto.setFeatures(features);
            apiResponse.setStatus(HttpStatus.OK);
            apiResponse.setStatusCode(200);
            apiResponse.setData(companyDetailsDto);

        } catch (ObjectDoesNotExistException e) {
            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            apiResponse.setMessage(e.getMessage());
            log.error("getCompanyDetails {}: ", e.getMessage());
            apiResponse.setStatusCode(404);
        } catch (AccessDeniedException e) {
            apiResponse.setStatus(HttpStatus.FORBIDDEN);
            apiResponse.setData(e.getMessage());
            log.error("AccessDeniedException {}: ", e.getMessage());
            apiResponse.setStatusCode(403);
        } catch (Exception e) {
            apiResponse.setError(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Exception {}: ", e.getMessage());
            apiResponse.setStatusCode(500);
        }
        return apiResponse;
    }
//
//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class, ObjectDoesNotExistException.class})
//    public ApiResponse updateCompanies(UpdateCompanyDto updateCompanyDto) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Company company = companiesRepository.findByUuid(updateCompanyDto.getUuid());
//            if (company == null) {
//                throw new ObjectDoesNotExistException(Message.COMPANY_NOT_FOUND.getValue());
//            }
//            Optional<DoxaEntity> optionalEntity = entitiesRepository.findById(company.getEntity().getId());
//            DoxaEntity entity = optionalEntity.orElse(null);
//            if (entity == null) {
//                throw new ObjectDoesNotExistException(Message.COMPANY_NOT_FOUND.getValue());
//            }
//            // Company must be one of buyer, supplier, developer
//            if (!updateCompanyDto.isBuyer() && !updateCompanyDto.isSupplier()) {
//                throw new BadRequestException("Company must be one of the following profiles: buyer, supplier");
//            }
//            Company updateCompany = companyMapper.updateCompany(updateCompanyDto);
//            updateCompany.setEntity(entity);
//            updateCompany.setId(company.getId());
//            updateCompany.setActive(true);
//            updateCompany.setCreatedAt(company.getCreatedAt());
//            updateCompany.setUpdatedAt(Instant.now());
//            updateCompany.setOnboardingStatus(company.getOnboardingStatus());
//            updateCompany.setSubscriptionExpiry(company.getSubscriptionExpiry());
//            updateCompany.setMainCompany(
//                    entity.getCompanyRegistrationNumber().equals(company.getCompanyRegistrationNumber()));
//
//            HashMap<String, DocumentsMetaData> documentsFromDb = new HashMap<>();
//            for (DocumentsMetaData d : company.getDocumentsMetaDataList()) {
//                documentsFromDb.put(d.getGuid(), d);
//            }
//
//            HashMap<String, DocumentsMetaData> documentsFromDto = new HashMap<>();
//            for (DocumentsMetaData d : updateCompany.getDocumentsMetaDataList()) {
//                documentsFromDto.put(d.getGuid(), d);
//            }
//
//            HashMap<String, DocumentsMetaData> copyDocumentsFromDb = new HashMap<>(documentsFromDb);
//            HashMap<String, DocumentsMetaData> copyDocumentsFromDto = new HashMap<>(documentsFromDto);
//
////			new documents to be created
//            copyDocumentsFromDto.keySet().removeAll(documentsFromDb.keySet());
//
//            for (DocumentsMetaData d : updateCompany.getDocumentsMetaDataList()) {
//                d.setUpdatedAt(Instant.now());
//                if (documentsFromDb.containsKey(d.getGuid())) {
//                    // update a new document
//                    d.setId(documentsFromDb.get(d.getGuid()).getId());
//                    d.setCreatedAt(documentsFromDb.get(d.getGuid()).getCreatedAt());
//                    d.setCompanyId(documentsFromDb.get(d.getGuid()).getCompanyId());
//
//                } else {
//                    // create file
//                    d.setCreatedAt(Instant.now());
//                    d.setCompanyId(company.getId());
//                }
//                documentsRepository.save(d);
//            }
//
//            // FILES TO BE REMOVED
//            copyDocumentsFromDb.keySet().removeAll(documentsFromDto.keySet());
//            if (!copyDocumentsFromDb.isEmpty()) {
//                for (DocumentsMetaData d : company.getDocumentsMetaDataList()) {
//                    if (copyDocumentsFromDb.containsKey(d.getGuid())) {
//                        d.setUpdatedAt(Instant.now());
//                        d.setCompanyId(null);
//                        documentsRepository.save(d);
//                    }
//                }
//            }
//
//            // Assign module
//            if (updateCompanyDto.getFeatures() != null && updateCompanyDto.getFeatures().size() > 0) {
//                authorityService.assignModuleToCompany(updateCompany.getUuid(), updateCompanyDto.getFeatures());
//            }
//
//            companiesRepository.save(updateCompany);
//
//            apiResponse.setStatusCode(200);
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setData(Message.COMPANY_UPDATED.getValue() + company.getEntityName());
//
//        } catch (ObjectDoesNotExistException e) {
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatusCode(404);
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            apiResponse.setStatus(HttpStatus.FORBIDDEN);
//            apiResponse.setData(e.getMessage());
//            apiResponse.setStatusCode(403);
//        } catch (Exception e) {
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            log.error(e.getMessage());
//            e.printStackTrace();
//            apiResponse.setStatusCode(500);
//        }
//        return apiResponse;
//    }
//
//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class, ObjectDoesNotExistException.class})
//    public ApiResponse markCompanyDeleted(String uuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Company company = companiesRepository.findByUuid(uuid);
//            if (company == null) {
//                throw new ObjectDoesNotExistException(Message.COMPANY_NOT_FOUND.getValue());
//            }
//            company.setActive(false);
//            companiesRepository.save(company);
//            apiResponse.setStatusCode(200);
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setData(Message.COMPANY_DEACTIVATED.getValue() + company.getEntityName());
//
//        } catch (ObjectDoesNotExistException e) {
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatusCode(404);
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            apiResponse.setStatus(HttpStatus.FORBIDDEN);
//            apiResponse.setData(e.getMessage());
//            apiResponse.setStatusCode(403);
//        } catch (Exception e) {
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            apiResponse.setStatusCode(500);
//            e.printStackTrace();
//        }
//        return apiResponse;
//    }
//
//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class, ObjectDoesNotExistException.class})
//    public ApiResponse reactivateCompany(String uuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            Company company = companiesRepository.findByUuid(uuid);
//            if (company == null) {
//                throw new ObjectDoesNotExistException(Message.COMPANY_NOT_FOUND.getValue());
//            }
//
//            company.setActive(true);
//            companiesRepository.save(company);
//            apiResponse.setStatusCode(200);
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setData(Message.COMPANY_REACTIVATED.getValue() + company.getEntityName());
//
//        } catch (ObjectDoesNotExistException e) {
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatusCode(404);
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            apiResponse.setStatus(HttpStatus.FORBIDDEN);
//            apiResponse.setData(e.getMessage());
//            apiResponse.setStatusCode(403);
//        } catch (Exception e) {
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            apiResponse.setStatusCode(500);
//            e.printStackTrace();
//        }
//        return apiResponse;
//    }

    @Override
    public Optional<User> getUser() {
        return userRepository.findById(authenticationManager.getCurrentUserId());
    }

    public ApiResponse findCompanyDetailsFromCompanyUuidOrUEN(String companyUuidOrUEN, boolean isCompanyUuid, String country) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Company company = null;
            if (isCompanyUuid) {
                company = companyRepository.findById(companyUuidOrUEN).get();
            } else {
                company = companyRepository.findByCompanyRegistrationNumberAndCountry(companyUuidOrUEN.toUpperCase(Locale.ROOT), country.toUpperCase(Locale.ROOT));
            }

            if (company == null) {
                apiResponse.setData(null);
            } else {
                CompanyDetailsAPIDto companyDto = modelMapper.map(company, CompanyDetailsAPIDto.class);
                apiResponse.setData(companyDto);
            }
            apiResponse.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            apiResponse.setError(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setStatusCode(500);
        }
        return apiResponse;
    }
    @Override
    public ApiResponse assignFeatureToCompany(String companyUuid, List<String> featureCodes) {
        // get features from DB
        List<Feature> dbFeatures = featureRepository.findFeatureByCodeIn(featureCodes);
        if (dbFeatures.size() != featureCodes.size()) {
            Set<String> dbCodes = dbFeatures.stream().map(Feature::getCode).collect(Collectors.toSet());
            Set<String> invalids = new HashSet<>(featureCodes);
            invalids.removeAll(dbCodes);
            throw new BadRequestException("Invalid feature code: " + String.join(",", invalids));
        }
        Optional<Company> optionalCompany = companyRepository.findById(companyUuid);
        if(!optionalCompany.isPresent()){
            throw new BadRequestException("Not existed this company has id: " + companyUuid);
        }
        Company company = optionalCompany.get();
        List<FeatureDetail> featureList = dbFeatures.stream().map(e->{
           FeatureDetail f = new FeatureDetail();
           f.setFeatureId(e.getCode());
           return f;
        }).collect(Collectors.toList());
        company.setFeatureList(featureList);
        companyRepository.save(company);
        // delete current subscription
//        subscriptionRepository.deleteAllByCompanyUuid(companyUuid);
        // build the subscription model
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
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        return apiResponse;
    }

}
