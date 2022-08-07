package com.doxa.oauth2.models.companies;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
public class AddressDetail {
    String addressLabel;
    String addressLine;
    String addressId;
    @DocumentReference(lazy = true, lookup = "{ 'id' : ?#{#self.addressId} }")
    Address address;
}
