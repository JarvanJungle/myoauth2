//package com.doxa.oauth2.serviceImpl;
//
//import com.doxa.oauth2.DTO.manageCompany.CreateCompanyDetailsDto;
//import com.doxa.oauth2.DTO.manageCompany.DocumentsMetaDataDto;
//import com.doxa.oauth2.DTO.manageCompany.UpdateCompanyDto;
//import com.doxa.oauth2.DTO.manageEntity.*;
//import com.doxa.oauth2.common.DoxaAuthenticationManager;
//import com.doxa.oauth2.config.Configs;
//import com.doxa.oauth2.config.Message;
//import com.doxa.oauth2.exceptions.BadRequestException;
//import com.doxa.oauth2.exceptions.EntityAlreadyExistsException;
//import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
//import com.doxa.oauth2.mapper.*;
//import com.doxa.oauth2.models.companies.Company;
//import com.doxa.oauth2.models.companies.DoxaEntity;
//import com.doxa.oauth2.models.companies.EntityRepresentative;
//import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.Role;
//import com.doxa.oauth2.repositories.companies.*;
//import com.doxa.oauth2.repositories.user.RoleRepository;
//import com.doxa.oauth2.repositories.user.UserRepository;
//import com.doxa.oauth2.repositories.user.UserRoleRepository;
//import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.services.ICompaniesService;
//import com.doxa.oauth2.services.IEntitiesService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Locale;
//import java.util.UUID;
//
//@Service
//public class EntitiesServiceImpl implements IEntitiesService {
//    private static final Logger LOG = LoggerFactory.getLogger(EntitiesServiceImpl.class);
//
//    @Autowired
//    private AuthorityService authorityService;
//
//    @Autowired
//    private DoxaAuthenticationManager authenticationManager;
//
//    @Autowired
//    EntityTypeMapper entityTypeMapper;
//
//    @Autowired
//    IndustryTypeMapper industryTypeMapper;
//
//    @Autowired
//    EntitiesRepository entitiesRepository;
//
//    @Autowired
//    EntityTypeRepository entityTypeRepository;
//
//    @Autowired
//    IndustryTypeRepository industryTypeRepository;
//
//    @Autowired
//    EntityRepresentativeRepository entRepRepository;
//
//    @Autowired
//    private DisplayEntityListMapper displayEntityListMapper;
//
//    @Autowired
//    private DoxaEntityMapper doxaEntityMapper;
//
//    @Autowired
//    private DocumentsMetaDataMapper documentsMetaDataMapper;
//
//    @Autowired
//    private EntityRepresentativeMapper entityRepresentativeMapper;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Autowired
//    UserRoleRepository userRoleRepository;
//
//    private ICompaniesService companiesService;
//
//    @Autowired
//    private CompaniesRepository companiesRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    public void setCompaniesService(ICompaniesService companiesService) {
//        this.companiesService = companiesService;
//    }
//
//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class})
//    public ApiResponse listEntities() {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            if (!authenticationManager.isDoxaAdmin()) {
//                throw new AccessDeniedException("Access denied, lack of scope (\"SCOPE_users:read\")");
//            }
//            List<DoxaEntity> entities = entitiesRepository.findAll();
//            List<DisplayEntityListDto> entitiesDtoList = displayEntityListMapper
//                    .mapDisplayEntityListDto(entities);
//            for (DisplayEntityListDto d : entitiesDtoList) {
//                Company defaultCompany = companiesRepository
//                        .findByCompanyRegistrationNumber(d.getCompanyRegistrationNumber().toUpperCase(Locale.ROOT));
//                if (defaultCompany != null) {
//                    d.setCountry(defaultCompany.getCountry());
//                    d.setCreatedAt(defaultCompany.getCreatedAt());
//                    d.setEntityName(defaultCompany.getEntityName());
//                    d.setActive(defaultCompany.isActive());
//                    d.setOnboardingStatus(defaultCompany.getOnboardingStatus());
//                    d.setSubscriptionExpiry(defaultCompany.getSubscriptionExpiry());
//                }
//            }
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setData(entitiesDtoList);
//            apiResponse.setStatusCode(200);
//        } catch (AccessDeniedException e) {
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatus(HttpStatus.FORBIDDEN);
//            LOG.error(e.getMessage());
//        }
//        return apiResponse;
//    }
//
//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class})
//    public ApiResponse getEntityDetails(String uuid) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            DoxaEntity entity = entitiesRepository.findByUuid(uuid);
//            if (entity == null) {
//                throw new ObjectDoesNotExistException(Message.NOT_FOUND.getValue());
//            }
//            if (entity.getCompanyRegistrationNumber() == null) {
//                LOG.error("Company registration number not found, entity uuid= " + uuid);
//                throw new ObjectDoesNotExistException(Message.NOT_FOUND.getValue());
//            }
//            Company company = companiesRepository.findByCompanyRegistrationNumber(entity.getCompanyRegistrationNumber().toUpperCase(Locale.ROOT));
//
//            if (company == null) {
//                LOG.error("Entity doesn't have default company, uuid= " + uuid);
//                throw new ObjectDoesNotExistException(Message.NOT_FOUND.getValue());
//            }
//
//            DoxaEntityDetailDto entityDto = doxaEntityMapper.doxaEntityDetailDto(entity);
//            entityDto.setCountry(company.getCountry());
//            entityDto.setDocumentsMetaDataList(
//                    documentsMetaDataMapper.mapDocumentsMetaDataDto(company.getDocumentsMetaDataList()));
//            entityDto.setEntityName(company.getEntityName());
//            entityDto.setEntityType(company.getEntityType());
//            entityDto.setGstApplicable(company.isGstApplicable());
//            entityDto.setActive(company.isActive());
//            entityDto.setGstNo(company.getGstNo());
//            entityDto.setIndustryType(company.getIndustryType());
//            entityDto.setBuyer(company.isBuyer());
//            entityDto.setSupplier(company.isSupplier());
//            entityDto.setDeveloper(company.isDeveloper());
//            entityDto.setRemarks(company.getRemarks());
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setStatusCode(200);
//            apiResponse.setData(entityDto);
//        } catch (ObjectDoesNotExistException e) {
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatusCode(404);
//        } catch (Exception e) {
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            LOG.error(e.getMessage());
//            apiResponse.setStatusCode(500);
//        }
//        return apiResponse;
//    }
//
//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class})
//    public ApiResponse listEntityType() {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            List<EntityTypeDto> entityTypeList = entityTypeMapper.entityTypeDtoList(entityTypeRepository.findAll());
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setData(entityTypeList);
//            apiResponse.setStatusCode(200);
//        } catch (Exception e) {
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            LOG.error(e.getMessage());
//            apiResponse.setStatusCode(500);
//        }
//        return apiResponse;
//    }
//
//    ;
//
//    @Override
//    @Transactional(rollbackOn = {Exception.class, RuntimeException.class})
//    public ApiResponse listIndustryType() {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            List<IndustryTypeDto> industryTypeList = industryTypeMapper
//                    .industryTypeDto(industryTypeRepository.findAll());
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setStatusCode(200);
//            apiResponse.setData(industryTypeList);
//        } catch (Exception e) {
//            apiResponse.setError(e.getMessage());
//            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//            LOG.error(e.getMessage());
//            apiResponse.setStatusCode(500);
//        }
//        return apiResponse;
//    }
//
//    ;
//
//    @Override
//    @org.springframework.transaction.annotation.Transactional(rollbackFor = {Exception.class, RuntimeException.class, EntityAlreadyExistsException.class})
//    public ApiResponse createEntities(CreateEntityDetailsDto createEntityDetailsDto) {
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            DoxaEntity entity = entitiesRepository
//                    .findByCompanyRegistrationNumber(createEntityDetailsDto.getCompanyRegistrationNumber().toUpperCase(Locale.ROOT));
//            if (entity == null) {
//                // create and save the entitites
//                DoxaEntity doxaEntity = new DoxaEntity();
//                doxaEntity.setUuid(UUID.randomUUID().toString());
//                doxaEntity.setCompanyRegistrationNumber(createEntityDetailsDto.getCompanyRegistrationNumber().toUpperCase(Locale.ROOT));
//                doxaEntity.setEntityRepresentativeList(entityRepresentativeMapper
//                        .entityRepresentativeList(createEntityDetailsDto.getEntityRepresentativeList()));
//                doxaEntity = entitiesRepository.save(doxaEntity);
//
//                for (EntityRepresentative e : doxaEntity.getEntityRepresentativeList()) {
//                    e.setEntityId(doxaEntity.getId());
//                    entRepRepository.save(e);
//                }
//
////				create default company
//                CreateCompanyDetailsDto createCompanyDetailsDto = new CreateCompanyDetailsDto();
//                createCompanyDetailsDto
//                        .setCompanyRegistrationNumber(createEntityDetailsDto.getCompanyRegistrationNumber());
//                createCompanyDetailsDto.setCountry(createEntityDetailsDto.getCountry());
//                List<DocumentsMetaDataDto> documentsDto = createEntityDetailsDto.getDocumentsMetaDataList();
//                createCompanyDetailsDto.setDocumentsMetaDataList(documentsDto);
//                createCompanyDetailsDto.setEntityName(createEntityDetailsDto.getEntityName());
//                createCompanyDetailsDto.setEntityType(createEntityDetailsDto.getEntityType());
//                createCompanyDetailsDto.setGstApplicable(createEntityDetailsDto.isGstApplicable());
//                createCompanyDetailsDto.setGstNo(createEntityDetailsDto.getGstNo());
//                createCompanyDetailsDto.setIndustryType(createEntityDetailsDto.getIndustryType());
//                createCompanyDetailsDto.setBuyer(createEntityDetailsDto.isBuyer());
//                createCompanyDetailsDto.setSupplier(createEntityDetailsDto.isSupplier());
//                createCompanyDetailsDto.setDeveloper(createEntityDetailsDto.isDeveloper());
//                createCompanyDetailsDto.setRemarks(createEntityDetailsDto.getRemarks());
//                createCompanyDetailsDto.setCountryCurrency(createEntityDetailsDto.getCountryCurrency());
//
//                ApiResponse createResp = companiesService.createCompany(createCompanyDetailsDto);
//                if(createResp.getStatusCode()!=200)
//                    throw new BadRequestException(createResp.getMessage());
//                // Assign module
//                if (createEntityDetailsDto.getFeatures() != null && createEntityDetailsDto.getFeatures().size() > 0) {
//                    Company createdCompany = (Company) createResp.getData();
//                    authorityService.assignModuleToCompany(createdCompany.getUuid(), createEntityDetailsDto.getFeatures());
//                }
//                apiResponse.setStatusCode(200);
//                apiResponse.setStatus(HttpStatus.OK);
//                apiResponse.setData(Message.ENTITY_CREATED.getValue() + createEntityDetailsDto.getEntityName());
//            } else {
//                throw new EntityAlreadyExistsException(Message.ENTITY_EXISTS.getValue());
//            }
//        } catch (EntityAlreadyExistsException e) {
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setMessage(e.getMessage());
//            apiResponse.setStatusCode(400);
//        }
//        return apiResponse;
//    }
//
//	@Override
//	@Transactional(rollbackOn = { Exception.class, RuntimeException.class, ObjectDoesNotExistException.class })
//	public ApiResponse updateEntities(UpdateEntityDto updateEntityDto) {
//		ApiResponse apiResponse = new ApiResponse();
//		try {
//			DoxaEntity updateEntity = entitiesRepository.findByUuid(updateEntityDto.getUuid());
//			if (updateEntity == null) {
//				throw new ObjectDoesNotExistException(Message.NOT_FOUND.getValue());
//			}
//			Company updateCompany = companiesRepository.findDefaultCompanyByEntityId(updateEntity.getId());
//			if (updateCompany != null) {
//
//				updateEntity.setCompanyRegistrationNumber(updateEntityDto.getCompanyRegistrationNumber().toUpperCase(Locale.ROOT));
//
//                // UPDATE THE REPRESENTATIVE
//                for (EntityRepresentative r : updateEntity.getEntityRepresentativeList()) {
//                    for (EntityRepresentative s : entityRepresentativeMapper
//                            .entityRepresentativeList(updateEntityDto.getEntityRepresentativeList())) {
//                        if (s.getUserRole().equals(Configs.ENTITY_ADMIN_ROLE.getValue())) {
//                            Role role = roleRepository.findByRoleCode(Configs.ENTITY_ADMIN.getValue());
//                            User user = userRoleRepository.findUserByRoleIdCompanyId(role.getId(),
//                                    updateCompany.getId());
//                            if (!user.getEmail().equals(r.getEmail()) && userRepository.existsByEmail(r.getEmail()))
//                                throw new BadRequestException(String.format("Email: %s has existed", r.getEmail()));
//                            user.setEmail(r.getEmail());
//                            user.setName(r.getName());
//                            user.setWorkNumber(r.getWorkNumber());
//                            userRepository.save(user);
//                        }
//                        if (s.getUserRole().equals(r.getUserRole())) {
//                            s.setEntityId(updateEntity.getId());
//                            s.setId(r.getId());
//                            entRepRepository.save(s);
//                            break;
//                        }
//                    }
//                }
//                // UPDATE THE REPRESENTATIVE
//                for (EntityRepresentative r : updateEntity.getEntityRepresentativeList()) {
//                    for (EntityRepresentative s : entityRepresentativeMapper
//                            .entityRepresentativeList(updateEntityDto.getEntityRepresentativeList())) {
//                        System.out.println(s.getUserRole());
//                        if (s.getUserRole().equals(Configs.ENTITY_ADMIN_ROLE.getValue())) {
//                            Role role = roleRepository.findByRoleCode(Configs.ENTITY_ADMIN.getValue());
//                            User user = userRoleRepository.findUserByRoleIdCompanyId(role.getId(),
//                                    updateCompany.getId());
//                            if (!user.getEmail().equals(r.getEmail()) && userRepository.existsByEmail(r.getEmail()))
//                                throw new BadRequestException(String.format("Email: %s has existed", r.getEmail()));
//                            user.setEmail(r.getEmail());
//                            user.setName(r.getName());
//                            user.setWorkNumber(r.getWorkNumber());
//                            userRepository.save(user);
//                        }
//                        if (s.getUserRole().equals(r.getUserRole())) {
//                            s.setEntityId(updateEntity.getId());
//                            s.setId(r.getId());
//                            entRepRepository.save(s);
//                            break;
//                        }
//                    }
//                }
//
//				entitiesRepository.save(updateEntity);
//
//				UpdateCompanyDto updateCompanyDto = new UpdateCompanyDto();
//				updateCompanyDto.setEntityName(updateEntityDto.getEntityName());
//				updateCompanyDto.setUuid(updateCompany.getUuid());
//				updateCompanyDto.setCompanyRegistrationNumber(updateEntityDto.getCompanyRegistrationNumber());
//				updateCompanyDto.setGstNo(updateEntityDto.getGstNo());
//				updateCompanyDto.setEntityType(updateEntityDto.getEntityType());
//				updateCompanyDto.setIndustryType(updateEntityDto.getIndustryType());
//				updateCompanyDto.setGstApplicable(updateEntityDto.isGstApplicable());
//				updateCompanyDto.setCountry(updateEntityDto.getCountry());
//				updateCompanyDto.setDocumentsMetaDataList(updateEntityDto.getDocumentsMetaDataList());
//				updateCompanyDto.setBuyer(updateEntityDto.isBuyer());
//				updateCompanyDto.setSupplier(updateEntityDto.isSupplier());
//				updateCompanyDto.setDeveloper(updateEntityDto.isDeveloper());
//				updateCompanyDto.setRemarks(updateEntityDto.getRemarks());
//				companiesService.updateCompanies(updateCompanyDto);
//				// Re-assign subscription
//				if (updateEntityDto.getFeatures() != null && updateEntityDto.getFeatures().size() > 0) {
//					authorityService.assignModuleToCompany(updateCompany.getUuid(), updateEntityDto.getFeatures());
//				}
//				apiResponse.setStatusCode(200);
//				apiResponse.setStatus(HttpStatus.OK);
//				apiResponse.setData("Entity updated for: " + updateEntityDto.getEntityName());
//			} else {
//				throw new ObjectDoesNotExistException(Message.ENTITY_NOT_FOUND.getValue());
//			}
//		} catch (ObjectDoesNotExistException e) {
//			apiResponse.setStatus(HttpStatus.NOT_FOUND);
//			apiResponse.setMessage(e.getMessage());
//			apiResponse.setStatusCode(404);
//		}
//		return apiResponse;
//	}
//
//	@Override
//	@Transactional(rollbackOn = { Exception.class, RuntimeException.class, ObjectDoesNotExistException.class })
//	public ApiResponse markEntityDeleted(String uuid) {
//		ApiResponse apiResponse = new ApiResponse();
//		try {
//			DoxaEntity entity = entitiesRepository.findByUuid(uuid);
//			if (entity == null) {
//				throw new ObjectDoesNotExistException(Message.ENTITY_NOT_FOUND.getValue());
//			}
//			Company defaultCompany = companiesRepository.findDefaultCompanyByEntityId(entity.getId());
//			if (defaultCompany == null) {
//				throw new ObjectDoesNotExistException(Message.ENTITY_NOT_FOUND.getValue());
//			}
//
//			companiesService.markCompanyDeleted(defaultCompany.getUuid());
//			apiResponse.setStatusCode(200);
//			apiResponse.setStatus(HttpStatus.OK);
//			apiResponse.setData(Message.COMPANY_DEACTIVATED.getValue() + defaultCompany.getEntityName());
//
//		} catch (ObjectDoesNotExistException e) {
//			apiResponse.setStatus(HttpStatus.NOT_FOUND);
//			apiResponse.setMessage(e.getMessage());
//			apiResponse.setStatusCode(404);
//		} catch (Exception e) {
//			apiResponse.setError(e.getMessage());
//			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//			System.out.println(e);
//			apiResponse.setStatusCode(500);
//		}
//		return apiResponse;
//	}
//
//	@Override
//	@Transactional(rollbackOn = { Exception.class, RuntimeException.class, ObjectDoesNotExistException.class })
//	public ApiResponse reactivateEntity(String uuid) {
//		ApiResponse apiResponse = new ApiResponse();
//		try {
//
//			DoxaEntity entity = entitiesRepository.findByUuid(uuid);
//			if (entity == null) {
//				throw new ObjectDoesNotExistException(Message.ENTITY_NOT_FOUND.getValue());
//			}
//			Company defaultCompany = companiesRepository.findDefaultCompanyByEntityId(entity.getId());
//			if (defaultCompany == null) {
//				throw new ObjectDoesNotExistException(Message.ENTITY_NOT_FOUND.getValue());
//			}
//			companiesService.reactivateCompany(defaultCompany.getUuid());
//			apiResponse.setStatus(HttpStatus.OK);
//			apiResponse.setData(Message.COMPANY_REACTIVATED.getValue() + defaultCompany.getEntityName());
//			apiResponse.setStatusCode(200);
//		} catch (ObjectDoesNotExistException e) {
//			apiResponse.setStatus(HttpStatus.NOT_FOUND);
//			apiResponse.setMessage(e.getMessage());
//			apiResponse.setStatusCode(404);
//		} catch (Exception e) {
//			apiResponse.setError(e.getMessage());
//			apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//			System.out.println(e);
//			apiResponse.setStatusCode(500);
//		}
//		return apiResponse;
//	}
//}
