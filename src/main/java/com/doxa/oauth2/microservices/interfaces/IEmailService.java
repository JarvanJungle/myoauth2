package com.doxa.oauth2.microservices.interfaces;


import com.doxa.oauth2.microservices.DTO.EmailDto;

public interface IEmailService {

	public void sendEmail(EmailDto emailDTO);
}
