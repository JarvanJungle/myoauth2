package com.doxa.oauth2.models.companies;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collation = "address")
public class Address {
    @Id
    private String id;
    private String city;
    private String state;
    private String country;
    @Field("postal_code")
    private String postalCode;

}
