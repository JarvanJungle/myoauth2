package com.doxa.oauth2.models.user;

import com.doxa.oauth2.models.authorities.core.Feature;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class FeatureDetail {
    private boolean read;
    private boolean write;
    private boolean approve;
    @Field("feature_id")
    private String featureId;
    @DocumentReference(lazy = true, lookup = "{ 'id' : ?#{#self.featureId} }")
    private Feature feature;
}
