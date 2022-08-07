//package com.doxa.oauth2.serviceImpl;
//
//import com.doxa.oauth2.DTO.authority.CreateRoleDto;
//import com.doxa.oauth2.DTO.authority.GrantUserAuthority;
//import com.doxa.oauth2.DTO.authority.RbacRoleDto;
//import com.doxa.oauth2.DTO.authority.RolePermissionDto;
//import com.doxa.oauth2.common.DoxaAuthenticationManager;
//import com.doxa.oauth2.config.AppConfig;
//import com.doxa.oauth2.config.CacheConfiguration;
//import com.doxa.oauth2.config.Message;
//import com.doxa.oauth2.enums.AuthorityAction;
//import com.doxa.oauth2.enums.RbacRoleStatus;
//import com.doxa.oauth2.exceptions.AccessDeniedException;
//import com.doxa.oauth2.exceptions.BadRequestException;
//import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
//import com.doxa.oauth2.mapper.RbacMapper;
//import com.doxa.oauth2.models.authorities.core.Feature;
//import com.doxa.oauth2.models.authorities.core.RbacRole;
//import com.doxa.oauth2.models.authorities.core.RolePermission;
//import com.doxa.oauth2.models.authorities.core.Subscription;
//import com.doxa.oauth2.models.user.RbacUserRole;
//import com.doxa.oauth2.models.user.UserCompanies;
//import com.doxa.oauth2.repositories.authorities.core.CoreFeatureRepository;
//import com.doxa.oauth2.repositories.authorities.core.RbacRoleRepository;
//import com.doxa.oauth2.repositories.authorities.core.RolePermissionRepository;
//import com.doxa.oauth2.repositories.authorities.core.SubscriptionRepository;
//import com.doxa.oauth2.repositories.user.UserCompaniesRepository;
//import com.doxa.oauth2.repositories.user.UserRbacRoleRepository;
//import com.doxa.oauth2.repositories.user.UserRepository;
//import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.services.RbacService;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * @author vuducnoi
// */
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class RbacServiceImpl implements RbacService {
//	private final RbacRoleRepository roleRepository;
//	private final SubscriptionRepository subscriptionRepository;
//	private final CoreFeatureRepository featureRepository;
//	private final RolePermissionRepository rolePermissionRepository;
//	private final DoxaAuthenticationManager authenticationManager;
//	private final UserCompaniesRepository userCompaniesRepository;
//	private final UserRbacRoleRepository userRbacRoleRepository;
//	private final UserRepository userRepository;
//	private final CacheServiceImpl cacheService;
//	private final RbacMapper rbacMapper;
//
//	/**
//	 * Create new role
//	 *
//	 * @param companyUuid Current company
//	 * @param roleDto     Role details
//	 *
//	 * @return ApiResponse
//	 */
//	@Override
//	@Transactional
//	public ApiResponse createRole(final String companyUuid, final CreateRoleDto roleDto) {
//		final String requestorName = authenticationManager.getByKey("name");
//		List<GrantUserAuthority> authorities = roleDto.getPermissions();
//		List<String> featureCodes = authorities.stream().map(GrantUserAuthority::getFeatureCode).collect(Collectors.toList());
//		List<Feature> dbFeatures = checkValidFeatures(companyUuid, featureCodes);
//		checkSubscription(companyUuid, featureCodes);
//		Map<String, Feature> featureMap = new HashMap<>(dbFeatures.size());
//		for (Feature f : dbFeatures) {
//			featureMap.put(f.getFeatureCode(), f);
//		}
//		final RbacRole role = new RbacRole();
//		role.setCreatedBy(requestorName);
//		role.setDescription(roleDto.getDescription());
//		role.setUuid(UUID.randomUUID().toString());
//		role.setCreatedAt(Instant.now());
//		role.setName(roleDto.getName());
//		role.setCompanyUuid(companyUuid);
//		role.setStatus(RbacRoleStatus.CREATED);
//		final RbacRole savedRole = roleRepository.save(role);
//		List<RolePermission> rolePermissions = buildPermissions(authorities, savedRole, featureMap);
//		rolePermissionRepository.saveAll(rolePermissions);
//		return ApiResponse.getSuccessResponse();
//	}
//
//	private List<RolePermission> buildPermissions(List<GrantUserAuthority> authorities, RbacRole role, Map<String, Feature> featureMap) {
//		return authorities.stream().map(e -> {
//			RolePermission rolePermission = new RolePermission();
//			rolePermission.setFeature(featureMap.get(e.getFeatureCode()));
//			rolePermission.setRole(role);
//			for (String action : e.getActions()) {
//				AuthorityAction act = AuthorityAction.valueOf(action.toUpperCase(Locale.ROOT));
//				switch (act) {
//					case APPROVE:
//						rolePermission.setApprove(true);
//						break;
//					case READ:
//						rolePermission.setRead(true);
//						break;
//					case WRITE:
//						rolePermission.setWrite(true);
//						break;
//					default:
//						log.info("Invalid action {}", act);
//				}
//			}
//			return rolePermission;
//		}).collect(Collectors.toList());
//	}
//
//	private List<Feature> checkValidFeatures(String companyUuid, List<String> featureCodes) {
//		List<Feature> dbFeatures = featureRepository.findAllByFeatureCodes(featureCodes);
//		if (dbFeatures.size() != featureCodes.size()) {
//			throw new BadRequestException("Invalid feature code");
//		}
//		return dbFeatures;
//	}
//
//	private boolean checkSubscription(String companyUuid, List<String> featureCodes) {
//		if (companyUuid.equals(AppConfig.DOXA_COMPANY_UUID)) {
//			return true;
//		}
//		// check if company has been assigned those features
//		List<Subscription> compSubscription = subscriptionRepository.findByCompanyUuidAndFeature(companyUuid, featureCodes);
//		if (compSubscription.size() != featureCodes.size()) {
//			Set<String> dbCodes = compSubscription.stream().map(Subscription::getFeatureCode).collect(Collectors.toSet());
//			Set<String> invalids = new HashSet<>(featureCodes);
//			invalids.removeAll(dbCodes);
//			throw new BadRequestException("Company has insufficient permissions, missing following features: " + String.join(",", invalids));
//		}
//		return true;
//	}
//
//	/**
//	 * Update an existing role
//	 *
//	 * @param companyUuid Current company
//	 * @param roleUuid    Role uuid to update
//	 * @param roleDto     Role details
//	 *
//	 * @return ApiResponse
//	 */
//	@SneakyThrows
//	@Override
//	@Transactional
//	public ApiResponse updateRole(String companyUuid, String roleUuid, CreateRoleDto roleDto) {
//		ApiResponse apiResponse = new ApiResponse();
//		RbacRole role = validateRole(companyUuid, roleUuid);
//
//		if (role.getStatus().equals(RbacRoleStatus.RESERVED) && !authenticationManager.isDoxaAdmin()) {
//			throw new BadRequestException("Edit reserved role isn't allowed");
//		}
//		role.setDescription(roleDto.getDescription());
//		role.setName(roleDto.getName());
//		List<GrantUserAuthority> authorities = roleDto.getPermissions();
//		List<String> featureCodes = authorities.stream().map(GrantUserAuthority::getFeatureCode).collect(Collectors.toList());
//		List<Feature> dbFeatures = checkValidFeatures(companyUuid, featureCodes);
//		checkSubscription(companyUuid, featureCodes);
//		Map<String, Feature> featureMap = new HashMap<>(dbFeatures.size());
//		for (Feature f : dbFeatures) {
//			featureMap.put(f.getFeatureCode(), f);
//		}
//		for (Feature f : dbFeatures) {
//			featureMap.put(f.getFeatureCode(), f);
//		}
//
//		List<RolePermission> permissions = buildPermissions(authorities, role, featureMap);
//		rolePermissionRepository.removeAllByRole(role.getId());
//		rolePermissionRepository.saveAll(permissions);
//		roleRepository.save(role);
//		List<RbacUserRole> userRoles = userRbacRoleRepository.findAllByRole(role);
//		for (RbacUserRole rbacUserRole : userRoles) {
//			String cacheKey = "authority" + companyUuid + rbacUserRole.getUserCompanies().getUser().getUuid();
//			cacheService.evictCache(CacheConfiguration.AUTHORITY_CACHE, cacheKey);
//		}
//		apiResponse.setMessage("Saved successfully");
//		return apiResponse;
//	}
//
//	/**
//	 * List all roles under the company
//	 *
//	 * @param companyUuid Current company
//	 *
//	 * @return List of Roles under company
//	 */
//	@Override
//	public ApiResponse listRoles(String companyUuid) {
//		List<RbacRole> rbacRoles = roleRepository.findAllByCompanyUuidAndDeletedIsFalse(companyUuid);
//		List<RbacRole> defaultRole = roleRepository.findAllByCompanyUuidAndDeletedIsFalse(AppConfig.DOXA_COMPANY_UUID);
//		rbacRoles.addAll(defaultRole);
//		List<RbacRoleDto> rbacRoleDtos = rbacMapper.rbacRoles(rbacRoles);
//		ApiResponse apiResponse = new ApiResponse();
//		apiResponse.setData(rbacRoleDtos);
//		return apiResponse;
//	}
//
//	/**
//	 * Get role details
//	 *
//	 * @param companyUuid Current company
//	 * @param roleUuid    Current role
//	 *
//	 * @return ApiResponse
//	 */
//	@SneakyThrows
//	@Override
//	public ApiResponse roleDetails(String companyUuid, String roleUuid) {
//		ApiResponse apiResponse = new ApiResponse();
//		RbacRole role = validateRole(companyUuid, roleUuid);
//		List<RolePermission> permissions = rolePermissionRepository.findAllByRole(role);
//		RbacRoleDto roleDto = rbacMapper.rbacRoleDetail(role);
//		List<RolePermissionDto> rolePermissionDtos = rbacMapper.rolePermissionDto(permissions);
//		roleDto.setPermissions(rolePermissionDtos);
//		apiResponse.setData(roleDto);
//		return apiResponse;
//	}
//
//	/**
//	 * Delete a role, if role is being used then it can't be deleted
//	 *
//	 * @param companyUuid Current company
//	 * @param roleUuid    Role uuid to delete
//	 *
//	 * @return ApiResponse
//	 */
//	@SneakyThrows
//	@Override
//	public ApiResponse deleteRole(String companyUuid, String roleUuid) {
//		RbacRole role = validateRole(companyUuid, roleUuid);
//		ApiResponse apiResponse = new ApiResponse();
//		if (role.getStatus().equals(RbacRoleStatus.RESERVED) && !authenticationManager.isDoxaAdmin()) {
//			throw new BadRequestException("Permission Denied! You don't have permission to delete default role");
//		}
//		List<RbacUserRole> userRoles = userRbacRoleRepository.findAllByRole(role);
//		if (!userRoles.isEmpty()) {
//			throw new BadRequestException("Role is being assigned to user(s).");
//		}
//		role.setDeleted(true);
//		roleRepository.save(role);
//		apiResponse.setMessage("Deleted successfully");
//		return apiResponse;
//	}
//
//	@SneakyThrows
//	private RbacRole validateRole(String companyUuid, String roleUuid) {
//		RbacRole role = roleRepository.findByCompanyUuidAndUuidAndDeletedIsFalse(companyUuid, roleUuid);
//		if (role == null) {
//			role = roleRepository.findByCompanyUuidAndUuidAndDeletedIsFalse(AppConfig.DOXA_COMPANY_UUID, roleUuid);
//		}
//		if (role == null) {
//			throw new ObjectDoesNotExistException(Message.NOT_FOUND.getValue());
//		}
//		return role;
//	}
//
//	/**
//	 * Assign list of role to user
//	 *
//	 * @param companyUuid Curren company
//	 * @param userUuid    User to assign
//	 * @param rbacRoles   List of role uuid
//	 *
//	 * @return ApiResponse
//	 */
//	@SneakyThrows
//	@Transactional
//	@Override
//	public ApiResponse assignRoleToUser(String companyUuid, String userUuid, List<String> rbacRoles) {
//		ApiResponse apiResponse = new ApiResponse();
//		List<RbacRole> roles = roleRepository.findAllByCompanyUuidAndUuidInAndDeletedIsFalse(companyUuid, rbacRoles);
//		List<RbacRole> defaultRole = roleRepository.findAllByCompanyUuidAndUuidInAndDeletedIsFalse(AppConfig.DOXA_COMPANY_UUID, rbacRoles);
//		roles.addAll(defaultRole);
//		if ((roles.size()) != rbacRoles.size()) {
//			throw new BadRequestException("Invalid role");
//		}
//
//		userRepository.findByUuid(userUuid).orElseThrow(() -> new ObjectDoesNotExistException("User doesn't exists"));
//
//		UserCompanies userCompanies = userCompaniesRepository.findOneByUserUuidAndCompanyUuid(companyUuid, userUuid);
//		if (userCompanies == null) {
//			throw new BadRequestException("User doesn't belong to this company");
//		}
//		userRbacRoleRepository.deleteAllByUserCompanies(userCompanies);
//		List<RbacUserRole> userRoles = roles.stream().map(e -> {
//			RbacUserRole userRole = new RbacUserRole();
//			userRole.setUserCompanies(userCompanies);
//			userRole.setRole(e);
//			return userRole;
//		}).collect(Collectors.toList());
//		userRbacRoleRepository.saveAll(userRoles);
//		String cacheKey = "authority" + companyUuid + userUuid;
//		cacheService.evictCache(CacheConfiguration.AUTHORITY_CACHE, cacheKey);
//		apiResponse.setMessage("Updated successfully");
//		return apiResponse;
//	}
//
//	/**
//	 * List all roles assigned to user
//	 *
//	 * @param companyUuid Current company
//	 * @param userUuid    User
//	 *
//	 * @return ApiResponse
//	 */
//	@Override
//	public ApiResponse listRoleOfUser(String companyUuid, String userUuid) {
//		UserCompanies userCompanies = userCompaniesRepository.findOneByUserUuidAndCompanyUuid(companyUuid, userUuid);
//		if (userCompanies == null) {
//			throw new BadRequestException("User doesn't belong to this company");
//		}
//		List<RbacUserRole> userRoles = userRbacRoleRepository.findAllByUserCompanies(userCompanies);
//		List<RbacRoleDto> rolePermissionDtos = new ArrayList<>(userRoles.size());
//		for (RbacUserRole role : userRoles) {
//			rolePermissionDtos.add(rbacMapper.rbacRoleDetail(role.getRole()));
//		}
//		ApiResponse apiResponse = new ApiResponse();
//		apiResponse.setData(rolePermissionDtos);
//		return apiResponse;
//	}
//
//	/**
//	 * Doxa admin will define the list of default role that can be used by all the entities under the system
//	 * @return ApiResponse
//	 */
//	@SneakyThrows
//	@Override
//	public ApiResponse createDefaultRoles(CreateRoleDto roleDto) {
//		if (!authenticationManager.isDoxaAdmin()) {
//			throw new AccessDeniedException("You don't have permission");
//		}
//		List<GrantUserAuthority> authorities = roleDto.getPermissions();
//		List<String> featureCodes = authorities.stream().map(GrantUserAuthority::getFeatureCode).collect(Collectors.toList());
//		List<Feature> dbFeatures = featureRepository.findAllByFeatureCodes(featureCodes);
//		Map<String, Feature> featureMap = new HashMap<>(dbFeatures.size());
//		for (Feature f : dbFeatures) {
//			featureMap.put(f.getFeatureCode(), f);
//		}
//		final RbacRole role = new RbacRole();
//		role.setCreatedBy(AppConfig.DOXA_ADMIN_NAME);
//		role.setDescription(roleDto.getDescription());
//		role.setUuid(UUID.randomUUID().toString());
//		role.setCreatedAt(Instant.now());
//		role.setName(roleDto.getName());
//		// 0 means it's created for all entities under company
//		role.setCompanyUuid(AppConfig.DOXA_COMPANY_UUID);
//		role.setStatus(RbacRoleStatus.RESERVED);
//		final RbacRole savedRole = roleRepository.save(role);
//		List<RolePermission> rolePermissions = buildPermissions(authorities, savedRole, featureMap);
//		rolePermissionRepository.saveAll(rolePermissions);
//		return ApiResponse.getSuccessResponse();
//	}
//
//	private boolean isValidRole(String companyUuid, CreateRoleDto roleDto) {
//		return true;
//	}
//
//	/**
//	 * List all default roles
//	 *
//	 * @param companyUuid Doxa company
//	 *
//	 * @return List of default role
//	 */
//	@Override
//	public ApiResponse listDefaultRole(String companyUuid) {
//		List<RbacRole> rbacRoles = roleRepository.findAllByStatusAndDeletedIsFalse(RbacRoleStatus.RESERVED);
//		List<RbacRoleDto> rbacRoleDtos = rbacMapper.rbacRoles(rbacRoles);
//		ApiResponse apiResponse = new ApiResponse();
//		apiResponse.setData(rbacRoleDtos);
//		return apiResponse;
//	}
//}
