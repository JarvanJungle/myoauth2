package com.doxa.oauth2.microservices.interfaces;

import com.doxa.oauth2.DTO.financialInstitution.FIUserCreateDto;
//import com.doxa.oauth2.DTO.financialInstitution.FinancialInstitutionDto;

public interface IMessageService {
    void sendToEmailQueue(Object event);

    void sendToUserUpdatedExchange(Object event);

    boolean notifyNewCompanyCreated(Object event);

    boolean notifyNewUserCreated(Object event);

	boolean notifyFiUserCreated(Object event);

	boolean notifyFiUserUpdated(Object event);
	
	boolean notifyFiCreated(Object event);

	boolean notifyFiUpdated(Object event);
}
