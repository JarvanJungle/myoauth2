package com.doxa.oauth2.microservices.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class EmailDtoDetails {

    //In-use with 'type' fields, guide email-service to fetch user email from uuid.
    private static final String EMAIL_RECIPIENTS_TYPE_UUID = "uuid";


    //At least one email address need to be specified in to, cc or bcc
    //message can accept HTML tags inside the String
    private List<String> to;
    private List<String> toName;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String title;
    private String message;

    private String type;
    private String templateName;

    public EmailDtoDetails(String to, String subject, String title, String message) {
        this.to = new ArrayList<>();
        this.cc = new ArrayList<>();
        this.bcc = new ArrayList<>();
        this.to.add(to);
        this.title = title;
        this.subject = subject;
        this.message = message;
    }

    private static EmailDtoDetails createInstance(List<String> to, String subject, String title, String message, String type) {
        EmailDtoDetails dto = new EmailDtoDetails();
        dto.setTo(to);
        dto.setSubject(subject);
        dto.setTitle(title);
        dto.setMessage(message);
        dto.setType(type);
        return dto;
    }

    public static EmailDtoDetails uuidType(List<String> to, String subject, String title, String message) {
        return EmailDtoDetails.createInstance(to, subject, title, message, EMAIL_RECIPIENTS_TYPE_UUID);
    }

    public static EmailDtoDetails uuidType(String to, String subject, String title, String message) {
        return EmailDtoDetails.uuidType(Collections.singletonList(to), subject, title, message);
    }

    public EmailDtoDetails templateName(String templateName) {
        this.templateName = templateName;
        return this;
    }
}


