//package com.doxa.oauth2.serviceImpl;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.apache.commons.lang.StringUtils;
//import org.modelmapper.ModelMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import com.doxa.oauth2.DTO.financialInstitution.FiUserDto;
//import com.doxa.oauth2.DTO.financialInstitution.FiUserUpdateDto;
//import com.doxa.oauth2.DTO.financialInstitution.FinancialInstitutionDto;
//import com.doxa.oauth2.DTO.financialInstitution.FinancialInstitutionUpdateDto;
//import com.doxa.oauth2.DTO.financialInstitution.ListFinancialInstitution;
//import com.doxa.oauth2.config.AppConfig;
//import com.doxa.oauth2.config.Configs;
//import com.doxa.oauth2.config.Message;
//import com.doxa.oauth2.enums.FiStatus;
//import com.doxa.oauth2.enums.ProjectStatusFI;
//import com.doxa.oauth2.exceptions.EntityAlreadyExistsException;
//import com.doxa.oauth2.exceptions.FiAlreadyExistException;
//import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
//import com.doxa.oauth2.microservices.DTO.EmailDto;
//import com.doxa.oauth2.microservices.interfaces.IEmailService;
//import com.doxa.oauth2.microservices.interfaces.IMessageService;
//import com.doxa.oauth2.models.authorities.core.Subscription;
//import com.doxa.oauth2.models.financialInstitution.FiCompany;
//import com.doxa.oauth2.models.financialInstitution.FinancialInstitution;
//import com.doxa.oauth2.models.financialInstitution.Project;
//import com.doxa.oauth2.models.financialInstitution.UserFinancialInstitution;
//import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.Role;
//import com.doxa.oauth2.models.user.UserRole;
//import com.doxa.oauth2.models.user.UserSettings;
//import com.doxa.oauth2.repositories.authorities.core.SubscriptionRepository;
//import com.doxa.oauth2.repositories.financialInstitution.FiCompanyRepository;
//import com.doxa.oauth2.repositories.financialInstitution.FiProjectRepository;
//import com.doxa.oauth2.repositories.financialInstitution.FiUserRepository;
//import com.doxa.oauth2.repositories.financialInstitution.FinancialInstitutionRepository;
//import com.doxa.oauth2.repositories.user.LanguageRepository;
//import com.doxa.oauth2.repositories.user.RoleRepository;
//import com.doxa.oauth2.repositories.user.UserRepository;
//import com.doxa.oauth2.repositories.user.UserRoleRepository;
//import com.doxa.oauth2.repositories.user.UserSettingRepository;
//import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.services.IFinancialInstitutionService;
//import com.doxa.oauth2.services.SpecificationService;
//import com.google.common.reflect.TypeToken;
//
//import lombok.extern.slf4j.Slf4j;
//import net.bytebuddy.utility.RandomString;
//
//@Service
//@Slf4j
//public class FinancialInstitutionServiceImpl implements IFinancialInstitutionService {
//
//	private static final Logger LOG = LoggerFactory.getLogger(EntitiesServiceImpl.class);
//
//	@Autowired
//	private AuthorityService authorityService;
//
//	@Autowired
//	private FinancialInstitutionRepository financialInstitutionRepository;
//
//	@Autowired
//	ModelMapper modelMapper;
//
//	@Autowired
//	private PasswordProviderService passwordProviderService;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private FiUserRepository userFinancialInstitutionRepository;
//
//	@Autowired
//	private UserRoleRepository userRoleRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Autowired
//	private IEmailService emailService;
//
//	@Value("${doxa.email.hyperlink-host}")
//	private String connexEmailHyperlinkHost;
//
//	@Autowired
//	private IMessageService messageService;
//
//	@Autowired
//	private LanguageRepository languageRepository;
//
//	@Autowired
//	private MultiFactorService multiFactorService;
//
//	@Autowired
//	private UserSettingRepository settingRepository;
//
//	@Autowired
//	private FiProjectRepository fiProjectRepository;
//
//	@Autowired
//	private FiCompanyRepository fiCompanyRepository;
//
//	@Autowired
//	private SpecificationService specificationService;
//
//	@Autowired
//	private SubscriptionRepository subscriptionRepository;
//
//	@Autowired
//	private FiUserRepository fiUserRepository;
//
//	public static final String ROLE_CODE = "FI_ADMIN";
//
//	@Override
//	@org.springframework.transaction.annotation.Transactional(rollbackFor = { Exception.class, RuntimeException.class,
//			FiAlreadyExistException.class })
//	public ApiResponse createFinancialInstitution(FinancialInstitutionDto financialInstitutionDto) {
//		ApiResponse apiResponse = new ApiResponse();
//		FinancialInstitution savedFi = null;
//		User savedUser = null;
//		String fiUuid = null;
//		User user = new User();
//		String password = null;
//		List<FiUserDto> users = financialInstitutionDto.getUsers();
//		List<User> Users = new ArrayList();
//		try {
//			FinancialInstitution financialInstitution = modelMapper.map(financialInstitutionDto,
//					FinancialInstitution.class);
//			FinancialInstitution fiInstitution = financialInstitutionRepository
//					.findByName(financialInstitutionDto.getFiName());
//			if (fiInstitution == null) {
//				fiUuid = UUID.randomUUID().toString();
//				financialInstitution.setFiUuid(fiUuid);
//				if (financialInstitutionDto.getFiportal()) {
//					for (FiUserDto fiUserDto : users) {
//						if (fiUserDto.getPassword() == null || fiUserDto.getPassword().isBlank()
//								|| fiUserDto.getPassword().isEmpty()) {
//							RandomString randomString = new RandomString(
//									Integer.parseInt(Configs.DEFAULT_PWD_LENGTH.getValue()));
//							password = randomString.nextString();
//						}
//						String encryptPassword = passwordProviderService.passwordEncoder().encode(password);
//						user.setUuid(UUID.randomUUID().toString());
//						user.setPassword(encryptPassword);
//						user.setEmail(fiUserDto.getEmail());
//						user.setName(fiUserDto.getName());
//						user.setActive(true);
//						user.setDesignation(fiUserDto.getDesignation());
//						user.setWorkNumber(fiUserDto.getWorkNumber());
//						user.setRemarks(fiUserDto.getRemarks());
//						user.setFiUuid(fiUuid);
//						user.setCountryCode(fiUserDto.getCountryCode());
//						savedUser = userRepository.save(user);
//						EmailDto emailDTO = new EmailDto(fiUserDto.getEmail(),
//								Message.EMAIL_ACCOUNT_SETUP_SUBJECT.getValue(), null,
//								Message.emailAccountSetupMessages(password, connexEmailHyperlinkHost));
//						emailDTO.getBody().setToName(Collections.singletonList(user.getName()));
//						emailDTO.getBody().setType(null);
//						emailDTO.getBody().setTemplateName(AppConfig.EMAIL_TEMPLATE_NOTIFICATION);
//						emailService.sendEmail(emailDTO);
//						UserSettings userSettings = new UserSettings();
//						userSettings.set2Fa(false);
//						userSettings.setLanguage(
//								languageRepository.findByLanguageCode(Configs.DEFAULT_LANGUAGE.getValue()));
//						userSettings.setMustSetPassword(true);
//						userSettings.setSecret(multiFactorService.generateSecretKey());
//						userSettings.setUser(savedUser);
//						settingRepository.save(userSettings);
//						Users.add(user);
//					}
//					financialInstitution.setUsers(Users);
//					savedFi = financialInstitutionRepository.save(financialInstitution);
//					FinancialInstitutionDto finanInstitutionDto = modelMapper.map(savedFi, FinancialInstitutionDto.class);
//					messageService.notifyFiCreated(finanInstitutionDto);
//					if (financialInstitutionDto.getFeatures() != null
//							&& financialInstitutionDto.getFeatures().size() > 0) {
//						authorityService.assignModuleToFinancialInstitution(savedFi.getFiUuid(),
//								financialInstitutionDto.getFeatures());
//					}
//					FinancialInstitution financeInstitution = financialInstitutionRepository
//							.findByUuid(savedFi.getFiUuid());
//					UserFinancialInstitution newUserFinancialInstitution = new UserFinancialInstitution(savedUser,
//							financeInstitution);
//					UserFinancialInstitution savedUserFinancialInstitution = userFinancialInstitutionRepository
//							.save(newUserFinancialInstitution);
//					for (FiUserDto fiUserDto : users) {
//						Role role = roleRepository.findByRoleCode(fiUserDto.getRole());
//						UserRole newUserRole = new UserRole();
//						newUserRole.setRole(role);
//						newUserRole.setUserFinancialInstitution(savedUserFinancialInstitution);
//						userRoleRepository.save(newUserRole);
//					}
//				}
//			}
//			apiResponse.setStatusCode(200);
//			apiResponse.setStatus(HttpStatus.OK);
//			apiResponse.setData("Financial Institution Successfully");
//		} catch (EntityAlreadyExistsException e) {
//			apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//			apiResponse.setMessage(e.getMessage());
//			apiResponse.setStatusCode(400);
//		}
//		return apiResponse;
//	}
//
//	@Override
//	public ApiResponse updateFinancialInstitution(FinancialInstitutionUpdateDto financialInstitutiondto)
//			throws ObjectDoesNotExistException, Exception {
//		ApiResponse apiResponse = new ApiResponse();
//
//		if (!(financialInstitutiondto.getStatus().equals(FiStatus.ASSOCIATED.getValue())
//				|| financialInstitutiondto.getStatus().equals(FiStatus.DEACTIVATED.getValue()))) {
//			throw new Exception("unknown status");
//		}
//
//		try {
//			FinancialInstitution financialInstitution = financialInstitutionRepository.findByUuid(financialInstitutiondto.getFiUuid());
//					List<Project> projects = new ArrayList<>();
//					List<FiCompany> companies = new ArrayList<>();
//
//					if (financialInstitutiondto.getProjects() != null && !financialInstitutiondto.getProjects().isEmpty()) {
//						projects = modelMapper.map(financialInstitutiondto.getProjects(), new TypeToken<List<Project>>() {}.getType());
//					}
//					if (financialInstitutiondto.getCompanies() != null && !financialInstitutiondto.getCompanies().isEmpty()) {
//						companies = modelMapper.map(financialInstitutiondto.getCompanies(), new TypeToken<List<FiCompany>>() {}.getType());
//					}
//					if (Objects.nonNull(projects)) {
//						projects.forEach(projec -> {
//							Optional<Project> findByProjectCode = fiProjectRepository.findByProjectCode(projec.getProjectCode());
//							if (findByProjectCode.isPresent()) {
//								projec.setId(findByProjectCode.get().getId());
//							} else {
//								fiProjectRepository.save(projec);
//							}
//							if (financialInstitutiondto.getStatus().equals(FiStatus.DEACTIVATED.getValue())) {
//								projec.setStatus(ProjectStatusFI.INACTIVE.getValue());
//								financialInstitution.setStatus(FiStatus.DEACTIVATED.getValue());
//							}
//							if (financialInstitutiondto.getStatus().equals(FiStatus.ASSOCIATED.getValue())) {
//								projec.setStatus(ProjectStatusFI.ACTIVE.getValue());
//								financialInstitution.setStatus(FiStatus.ASSOCIATED.getValue());
//							}
//							fiProjectRepository.save(projec);
//						});
//
//					}
//
//					if (Objects.nonNull(companies)) {
//						companies.forEach(company -> {
//							if (financialInstitutiondto.getStatus().equals(FiStatus.DEACTIVATED.getValue())) {
//								company.setCompanyStatus(ProjectStatusFI.INACTIVE.getValue());
//								company.setFiid(financialInstitution.getId());
//								financialInstitution.setStatus(FiStatus.DEACTIVATED.getValue());
//							}
//							if (financialInstitutiondto.getStatus().equals(FiStatus.ASSOCIATED.getValue())) {
//								company.setCompanyStatus(ProjectStatusFI.ACTIVE.getValue());
//								company.setFiid(financialInstitution.getId());
//								financialInstitution.setStatus(FiStatus.ASSOCIATED.getValue());
//							}
//							fiCompanyRepository.save(company);
//						});
//
//					}
//
//
//			if (Objects.nonNull(financialInstitutiondto.getUsers())) {
//				for (FiUserUpdateDto user : financialInstitutiondto.getUsers()) {
//					Optional<User> systemUser = userRepository.findByUuid(user.getUuid());
//					if (systemUser.isPresent()) {
//						User fiUser = systemUser.get();
//						fiUser.setName(user.getName());
//						fiUser.setWorkNumber(user.getWorkNumber());
//						fiUser.setDesignation(user.getDesignation());
//						fiUser.setCountryCode(user.getCountryCode());
//						fiUser.setEmail(user.getEmail());
//						fiUser.setRemarks(user.getRemarks());
//						if (financialInstitutiondto.getStatus().equals(FiStatus.DEACTIVATED.getValue())) {
//							if (financialInstitution.getFiportal()) {
//								financialInstitution.setFiportal(false);
//								financialInstitution.setStatus(FiStatus.DEACTIVATED.getValue());
//							}
//							fiUser.setActive(false);
//						} else if (financialInstitutiondto.getStatus().equals(FiStatus.ASSOCIATED.getValue())) {
//							financialInstitution.setStatus(FiStatus.ASSOCIATED.getValue());
//							financialInstitution.setFiportal(true);
//							fiUser.setActive(true);
//						}
//						userRepository.save(fiUser);
//					}
//				}
//			}
//
//			if (financialInstitutiondto.getFeatures() != null
//					&& financialInstitutiondto.getFeatures().size() > 0) {
//				authorityService.assignModuleToFinancialInstitution(financialInstitutiondto.getFiUuid(),
//						financialInstitutiondto.getFeatures());
//			}
//
//			financialInstitution.setProjects(projects);
//			FinancialInstitution save = financialInstitutionRepository.save(financialInstitution);
//			FinancialInstitutionDto finanInstitutionDto = modelMapper.map(save, FinancialInstitutionDto.class);
//			messageService.notifyFiUpdated(finanInstitutionDto);
//			apiResponse.setStatusCode(200);
//			apiResponse.setStatus(HttpStatus.OK);
//			apiResponse.setData(save);
//			return apiResponse;
//		} catch (Exception e) {
//			throw new Exception(String.format("exception due to %s", e.getMessage()));
//		}
//	}
//
//	@Override
//	public ListFinancialInstitution findAllWithFilterV(String s, String q, PageRequest pageRequest) throws Exception {
//		try {
//			Specification<FinancialInstitution> specification = null;
//			if (Objects.nonNull(s)) {
//				specification = specificationService.filterUser(s);
//			}
//			if (StringUtils.isNotEmpty(q)) {
//				Specification<FinancialInstitution> globalSpecification = specificationService.filterGlobally(q);
//				specification = specification.and(globalSpecification);
//			}
//			Page<FinancialInstitution> financePage = financialInstitutionRepository.findAll(specification, pageRequest);
//			List<FinancialInstitution> financialInstitutions = financePage.get().collect(Collectors.toList());
//			List<FinancialInstitutionDto> financialInstitutionDtos = financialInstitutionList(financialInstitutions);
//			ListFinancialInstitution financialInstitution = new ListFinancialInstitution();
//			financialInstitution.setTotalElements(financialInstitutionRepository.count(specification));
//			financialInstitution.setNumberOfElements(financialInstitutionDtos.size());
//			financialInstitution.setContent(financialInstitutionDtos);
//			return financialInstitution;
//		} catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}
//	}
//
//
//	private List<FinancialInstitutionDto> financialInstitutionList(
//			List<FinancialInstitution> financialInstitutionList) {
//		List<FinancialInstitutionDto> financialInstitutionDtos = new ArrayList<>();
//		List<User> listUsers = new ArrayList<>();
//	//	FinancialInstitution financialInstitution = null;
//			for (FinancialInstitution financialInstitution : financialInstitutionList) {
//				List<User> userlist = userRepository.findUserByFiUuid(financialInstitution.getFiUuid());
//				if (Objects.nonNull(userlist)) {
//					financialInstitution.setUsers(userlist);
//				}
//				FinancialInstitutionDto financialInstitiondto = new FinancialInstitutionDto();
//				financialInstitiondto.setId(financialInstitution.getId());
//				financialInstitiondto.setFiCode(financialInstitution.getFiCode());
//				financialInstitiondto.setFiName(financialInstitution.getFiName());
//				financialInstitiondto.setStatus(financialInstitution.getStatus());
//				List<User> users = financialInstitution.getUsers();
//				List<FiUserDto> fiUserDto = new ArrayList<FiUserDto>();
//				for (User user : users) {
//					String roleName = userRoleRepository.findRoleByFiUserUuid(ROLE_CODE, user.getUuid());
//					if (Objects.nonNull(roleName) && !roleName.isEmpty()) {
//						FiUserDto fiUser = new FiUserDto();
//						fiUser.setId(user.getId());
//						fiUser.setName(user.getName());
//						fiUser.setEmail(user.getEmail());
//						fiUser.setDesignation(user.getDesignation());
//						fiUser.setRemarks(user.getRemarks());
//						fiUser.setWorkNumber(user.getWorkNumber());
//						fiUser.setFiUuid(user.getFiUuid());
//						fiUser.setRemarks(user.getRemarks());
//						fiUser.setCountryCode(user.getCountryCode());
//						fiUserDto.add(fiUser);
//					}
//				}
//				financialInstitiondto.setUsers(fiUserDto);
//				financialInstitutionDtos.add(financialInstitiondto);
//			}
//		return financialInstitutionDtos;
//	}
//
//
//	private List<FinancialInstitutionDto> getFinancialInstitutionList(
//			List<FinancialInstitution> financialInstitutionList) {
//		List<FinancialInstitutionDto> financialInstitutionDtos = new ArrayList<>();
//		financialInstitutionList.forEach(financialInstitution -> {
//			FinancialInstitutionDto financialInstitiondto = new FinancialInstitutionDto();
//			financialInstitiondto.setId(financialInstitution.getId());
//			financialInstitiondto.setFiCode(financialInstitution.getFiCode());
//			financialInstitiondto.setFiName(financialInstitution.getFiName());
//			financialInstitiondto.setStatus(financialInstitution.getStatus());
//			List<User> users = financialInstitution.getUsers();
//			List<FiUserDto> fiUserDto = new ArrayList<FiUserDto>();
//			for (User user : users) {
//				FiUserDto fiUser = new FiUserDto();
//				String roleName = userRoleRepository.findRoleByFiUserUuid(ROLE_CODE, user.getUuid());
//				fiUser.setName(user.getName());
//				fiUser.setEmail(user.getEmail());
//				fiUser.setDesignation(user.getDesignation());
//				fiUser.setRemarks(user.getRemarks());
//				fiUser.setWorkNumber(user.getWorkNumber());
//				fiUser.setFiUuid(user.getFiUuid());
//				fiUser.setRole(roleName);
//				fiUserDto.add(fiUser);
//			}
//			financialInstitiondto.setUsers(fiUserDto);
//			financialInstitutionDtos.add(financialInstitiondto);
//		});
//		return financialInstitutionDtos;
//	}
//
//	@Override
//	public FinancialInstitutionDto getFIById(Long id) throws Exception {
//		Optional<FinancialInstitution> optinalFinancialInstitution = financialInstitutionRepository.findById(id);
//		if (optinalFinancialInstitution.isPresent()) {
//		FinancialInstitution financialInstitution = optinalFinancialInstitution.get();
//		//if (Objects.nonNull(financialInstitution)) {
//			FinancialInstitutionDto financialInstitutionDto = modelMapper.map(financialInstitution, FinancialInstitutionDto.class);
//			List<User> users = userRepository.findUserByFiUuid(financialInstitution.getFiUuid());
//			if (Objects.nonNull(users) && !users.isEmpty()) {
//				List<FiUserDto> listOfUsers = new ArrayList<>();
//				for (User user : users) {
//					String roleName = userRoleRepository.findRoleByFiUserUuid(ROLE_CODE, user.getUuid());
//					if (Objects.nonNull(roleName) && !roleName.isEmpty()) {
//						FiUserDto userDto = new FiUserDto();
//						userDto.setName(user.getName());
//						userDto.setId(user.getId());
//						userDto.setName(user.getName());
//						userDto.setEmail(user.getEmail());
//						userDto.setWorkNumber(user.getWorkNumber());
//						userDto.setDesignation(user.getDesignation());
//						userDto.setUuid(user.getUuid());
//						userDto.setFiUuid(user.getFiUuid());
//						userDto.setRole(roleName);
//						userDto.setRemarks(user.getRemarks());
//						userDto.setCountryCode(user.getCountryCode());
//						userDto.setCreatedAt(user.getCreatedAt());
//						userDto.setActive(user.isActive());
//						listOfUsers.add(userDto);
//					}
//				}
//				financialInstitutionDto.setUsers(listOfUsers);
//			}
//			List<Subscription> features = subscriptionRepository.findByFiUuid(financialInstitution.getFiUuid());
//			List<String> listfeatures = null;
//			if (Objects.nonNull(features) && !features.isEmpty()) {
//				listfeatures = new ArrayList<>();
//				for (Subscription feature : features) {
//					String featureCode = feature.getFeatureCode();
//					listfeatures.add(featureCode);
//				}
//			}
//			financialInstitutionDto.setFeatures(listfeatures);
//			return financialInstitutionDto;
//		} else {
//			throw new Exception("FinancialInstitution not able to found");
//		}
//	}
//
//	@Override
//	public FinancialInstitutionDto getFIByLoggedInUser(String loggedInUserUuid) throws Exception {
//		FinancialInstitutionDto financialInstitutionDto  = null;
//		Optional<User> loggedInUser = userRepository.findByUuid(loggedInUserUuid);
//		if (loggedInUser.isPresent()) {
//			User User = loggedInUser.get();
//			FinancialInstitution financialInstitution = financialInstitutionRepository.findByUuid(User.getFiUuid());
//			if (Objects.nonNull(financialInstitution)) {
//				financialInstitutionDto = modelMapper.map(financialInstitution, FinancialInstitutionDto.class);
//				List<User> users = userRepository.findUserByFiUuid(financialInstitution.getFiUuid());
//				if (Objects.nonNull(users) && !users.isEmpty()) {
//					List<FiUserDto> listOfUsers = new ArrayList<>();
//					for (User user : users) {
//						FiUserDto userDto = new FiUserDto();
//						userDto.setName(user.getName());
//						userDto.setId(user.getId());
//						userDto.setName(user.getName());
//						userDto.setEmail(user.getEmail());
//						userDto.setWorkNumber(user.getWorkNumber());
//						userDto.setDesignation(user.getDesignation());
//						userDto.setUuid(user.getUuid());
//						userDto.setFiUuid(user.getFiUuid());
//						userDto.setCountryCode(user.getCountryCode());
//						userDto.setRemarks(user.getRemarks());
//						userDto.setCountryCode(user.getCountryCode());
//						userDto.setActive(user.isActive());
//						listOfUsers.add(userDto);
//					}
//					financialInstitutionDto.setUsers(listOfUsers);
//				}
//				List<Subscription> features = subscriptionRepository.findByFiUuid(financialInstitution.getFiUuid());
//				List<String> listfeatures = null;
//				if (Objects.nonNull(features) && !features.isEmpty()) {
//					listfeatures = new ArrayList<>();
//					for (Subscription feature : features) {
//						String featureCode = feature.getFeatureCode();
//						listfeatures.add(featureCode);
//					}
//				}
//				financialInstitutionDto.setFeatures(listfeatures);
//		}
//	}
//		return financialInstitutionDto;
//}
//
//}