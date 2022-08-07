package com.doxa.oauth2.models.companies;

import com.doxa.oauth2.models.authorities.core.Feature;
import com.doxa.oauth2.models.user.FeatureDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collation = "company")
public class Company {
    @Id
    private String id;
    private String name;
    @Field("gst_no")
    private String gstNo;
    @Field("registration_no")
    private String registrationNo;
    @Field("address_list")
    private List<AddressDetail> addressDetails;
    @Field("logo_url")
    private String logoUrl;
    private String status;
    @Field("company_type")
    private String companyType;
    @Field("feature_list")
    private List<FeatureDetail> featureList;

}
