package com.doxa.oauth2.models.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "role")
public class Role {
    @Id
    private Long id;
    private String name;
    private String status;
    @Field("feature_list")
    private List<FeatureDetail> featureDetails;
    @Field("company_id")
    private String companyId;
}
