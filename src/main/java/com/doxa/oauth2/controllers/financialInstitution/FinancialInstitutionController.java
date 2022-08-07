//package com.doxa.oauth2.controllers.financialInstitution;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.doxa.oauth2.DTO.financialInstitution.FinancialInstitutionDto;
//import com.doxa.oauth2.DTO.financialInstitution.FinancialInstitutionUpdateDto;
//import com.doxa.oauth2.DTO.financialInstitution.ListFinancialInstitution;
//import com.doxa.oauth2.common.ControllerPath;
//import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
//import com.doxa.oauth2.responses.ApiResponse;
//import com.doxa.oauth2.services.IFinancialInstitutionService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequestMapping(ControllerPath.FINANCIAL_INSTITUTION)
//@Slf4j
//public class FinancialInstitutionController {
//
//	 @Autowired
//	 private IFinancialInstitutionService financialInstitutionService;
//
//	@PostMapping(ControllerPath.CREATE)
//	public ResponseEntity<ApiResponse> createFinancialInstitution(@Valid @RequestBody FinancialInstitutionDto financialInstitutionDto) {
//		ApiResponse apiResponse = financialInstitutionService.createFinancialInstitution(financialInstitutionDto);
//		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//	}
//
//	@PutMapping(ControllerPath.UPDATE)
//	public ResponseEntity<ApiResponse> updateFinancialInstitution(@Valid @RequestBody FinancialInstitutionUpdateDto financialInstitutionDto) throws ObjectDoesNotExistException, Exception {
//        financialInstitutionService.updateFinancialInstitution(financialInstitutionDto);
//        ApiResponse response = new ApiResponse();
//        response.setMessage("FinancialInstitution updated successfully");
//        response.setStatus(HttpStatus.OK);
//        return ResponseEntity.status(response.getStatus()).body(response);
//	}
//
//	@GetMapping(ControllerPath.GET_FINANCIALINSTITUTION)
//    public ResponseEntity<ApiResponse> getFI(@RequestParam(required = false) String s, @RequestParam(required = false) String q, @RequestParam int pageNumber, @RequestParam int pageSize) throws Exception{
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            ListFinancialInstitution findAllWithFilterNew = financialInstitutionService.findAllWithFilterV(s, q, PageRequest.of(pageNumber, pageSize));
//            if (findAllWithFilterNew != null) {
//                apiResponse.setData(findAllWithFilterNew);
//                apiResponse.setStatus(HttpStatus.OK);
//                return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//            } else {
//                apiResponse.setData("Data Not Found");
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }
//    @GetMapping(ControllerPath.GET_FINANCIALINSTITUTION_BYID)
//    public ResponseEntity<ApiResponse> getFIById(@PathVariable("id") Long id) throws Exception{
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            FinancialInstitutionDto financialInstitutionDto = financialInstitutionService.getFIById(id);
//            if (financialInstitutionDto != null) {
//                apiResponse.setData(financialInstitutionDto);
//                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
//            } else {
//                apiResponse.setData("Data Not Found");
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }
//
//    @GetMapping(ControllerPath.GET_FINANCIALINSTITUTION_BYLOGGEDINUSER)
//    public ResponseEntity<ApiResponse> getFIByLoggedInUserUuid(@PathVariable("loggedInUserUuid") String loggedInUserUuid) throws Exception{
//        ApiResponse apiResponse = new ApiResponse();
//        try {
//            FinancialInstitutionDto financialInstitutionDto = financialInstitutionService.getFIByLoggedInUser(loggedInUserUuid);
//            if (financialInstitutionDto != null) {
//                apiResponse.setData(financialInstitutionDto);
//                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
//            } else {
//                apiResponse.setData("Data Not Found");
//                apiResponse.setStatus(HttpStatus.NOT_FOUND);
//                return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
//            }
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }
//}
