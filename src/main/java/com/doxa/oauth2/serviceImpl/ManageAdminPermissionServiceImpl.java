//package com.doxa.oauth2.serviceImpl;
//
//import com.doxa.oauth2.DTO.manageAdminMatrix.AdministrativesDto;
//import com.doxa.oauth2.DTO.manageAdminMatrix.SubAdminDto;
//import com.doxa.oauth2.DTO.manageAdminMatrix.SubAdminPermissionDto;
//import com.doxa.oauth2.config.Message;
//import com.doxa.oauth2.exceptions.UserDoesNotExistException;
//import com.doxa.oauth2.mapper.SubAdminPermissionMapper;
//import com.doxa.oauth2.models.authorities.Administratives;
//import com.doxa.oauth2.models.authorities.UserAdministratives;
//import com.doxa.oauth2.models.companies.Company;
//import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.UserCompanies;
//import com.doxa.oauth2.repositories.authorities.AdministrativeRepository;
//import com.doxa.oauth2.repositories.authorities.UserAdministrativesRepository;
//import com.doxa.oauth2.repositories.companies.CompaniesRepository;
//import com.doxa.oauth2.repositories.user.UserCompaniesRepository;
//import com.doxa.oauth2.repositories.user.UserRepository;
//import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.services.IManageAdminPermissionService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service
//public class ManageAdminPermissionServiceImpl implements IManageAdminPermissionService {
//    @Autowired
//    UserAdministrativesRepository userAdministrativesRepository;
//
//    @Autowired
//    UserCompaniesRepository userCompaniesRepository;
//
//    @Autowired
//    CompaniesRepository companiesRepository;
//
//    @Autowired
//    SubAdminPermissionMapper subAdminPermissionMapper;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    AdministrativeRepository administrativeRepository;
//
//   @Transactional(rollbackFor = {SQLException.class,Exception.class, RuntimeException.class})
//    @Override
//    public ApiResponse updateSubAdminPermission(SubAdminDto subAdminDto){
//        ApiResponse apiResponse = new ApiResponse();
//        try{
//            // check if the user id exist in this company id
//            Optional<User> user = userRepository.findByUuid(subAdminDto.getUserUuid());
//            if (user.isEmpty())
//                throw new UserDoesNotExistException(Message.USER_NOT_FOUND.getValue());
//            Long userId = user.get().getId();
//
//            Company company = companiesRepository.findByUuid(subAdminDto.getCompanyUuid());
//
//            if (company ==null)
//                throw new UserDoesNotExistException(Message.COMPANY_NOT_FOUND.getValue());
//            Optional<UserCompanies> userCompany = userCompaniesRepository.findUserCompaniesIdByCompaniesIdAndUserId(company.getId(), userId);
//            if (userCompany.isEmpty())
//                throw new UserDoesNotExistException(Message.USER_NOT_FOUND.getValue());
//
//            List<UserAdministratives> userAdministrativesList = userAdministrativesRepository.findSubAdminRows(subAdminDto.getUserUuid());
//            //look into UserAdmin table if there are permission delete all the permission
//            userAdministrativesRepository.deleteInBatch(userAdministrativesList);
//            //for each permission for the permission
//            List<Administratives> administrativesList = new ArrayList<>();
//            for(AdministrativesDto administrativesDto :subAdminDto.getAdministrativesDtoList()){
//                administrativesList.add(administrativeRepository.findIdByAdminCodeAndCategoryCode(administrativesDto.getAdministrativeCode(),administrativesDto.getAdminCategories().getCategoryCode()));
//            }
//            //  create a row and save the
//            for(Administratives administratives:administrativesList){
//                UserAdministratives userAdministratives = new UserAdministratives();
//                userAdministratives.setUserCompanies(userCompany.get());
//                userAdministratives.setAdministratives(administratives);
//                userAdministrativesRepository.save(userAdministratives);
//            }
//            // check if the user does not exist then create new permission
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setData( subAdminPermissionMapper.administrativesDtoList(userAdministrativesRepository.findSubAdminPermission(subAdminDto.getUserUuid())));
//        } catch(Exception e){
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//            apiResponse.setData(e.getMessage());
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//       return apiResponse;
//    }
//
//
//    @Override
//    public ApiResponse listSubAdminPermissions(String company_uuid){
//        ApiResponse apiResponse = new ApiResponse();
//
//        //from the list get the administrative
//        try{
//
//            //from company table get the company id using the company uuid
//            Company company = companiesRepository.findByUuid(company_uuid);
//            if (company ==null)
//                throw new UserDoesNotExistException(Message.COMPANY_NOT_FOUND.getValue());
//            //from the user_company table get all the users with same company id
//            //List<Long> userIdList = userCompaniesRepository.findUserUuidIdByCompaniesId(company.getId());
//
//            //get the userid
//            List<String> userWithAdminList = userAdministrativesRepository.findSubAdminUserList(company_uuid);
//
//            //get the unique userid list
//
//            //for each user get list of admin permission from the user administrative
//            List<SubAdminPermissionDto> subAdminPermissionDtoList = new ArrayList<SubAdminPermissionDto>();
//
//
//            for (String u: userWithAdminList){
//                SubAdminPermissionDto subAdminPermissionDto = new SubAdminPermissionDto();
//                subAdminPermissionDto.setUserUuid(u);
//                subAdminPermissionDto.setAdministrativesDtoList(
//                        subAdminPermissionMapper.administrativesDtoList(userAdministrativesRepository.findSubAdminPermission(u)));
//                subAdminPermissionDtoList.add(subAdminPermissionDto);
//            }
//
//            apiResponse.setStatus(HttpStatus.OK);
//            System.out.println(subAdminPermissionDtoList);
//            apiResponse.setData(subAdminPermissionDtoList);
//        }catch(UserDoesNotExistException e){
//            e.printStackTrace();
//            log.error(e.getMessage());
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//            apiResponse.setData(e.getMessage());
//
//        }catch(Exception e){
//            e.printStackTrace();
//            log.error(e.getMessage());
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setData(e.getMessage());
//
//        }
//        return apiResponse;
//
//    }
//
//    @Override
//    public ApiResponse userSubAdminPermission(String userUuid, String companyUuid){
//        ApiResponse apiResponse = new ApiResponse();
//
//        //from the list get the administrative
//        try{
//
//            //from company table get the company id using the company uuid
//            Company company = companiesRepository.findByUuid(companyUuid);
//            if (company ==null)
//                throw new UserDoesNotExistException(Message.COMPANY_NOT_FOUND.getValue());
//
//            Optional<User> user = userRepository.findByUuid(userUuid);
//            if (user.isEmpty())
//                throw new UserDoesNotExistException(Message.USER_NOT_FOUND.getValue());
//            Long userId = user.get().getId();
//
//            Optional<UserCompanies> userCompany = userCompaniesRepository.findUserCompaniesIdByCompaniesIdAndUserId(company.getId(), userId);
//            if (!userCompany.isPresent())
//                throw new UserDoesNotExistException(Message.USER_NOT_FOUND.getValue());
//
//            List<Administratives> administrativesList = userAdministrativesRepository.findSubAdminPermissionUsingUuidAndCompanyUuid(userUuid,companyUuid);
//            List<AdministrativesDto> administrativesDtoList = subAdminPermissionMapper.administrativesDtoList(administrativesList);
//
//            apiResponse.setStatus(HttpStatus.OK);
//            apiResponse.setData(administrativesDtoList);
//        }catch(UserDoesNotExistException e){
//            apiResponse.setStatus(HttpStatus.NOT_FOUND);
//            apiResponse.setData(e.getMessage());
//
//        }catch(Exception e){
//            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
//            apiResponse.setData(e.getMessage());
//
//        }
//        return apiResponse;
//    }
//}
