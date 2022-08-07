package com.doxa.oauth2.repositories.authorities.core;

import com.doxa.oauth2.models.authorities.core.Feature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface FeatureRepository extends MongoRepository<Feature, String> {


//    @Query("select c from CoreFeature c where c.featureCode in :featureCodes")
//    public List<Feature> findAllByFeatureCodes(@Param("featureCodes") List<String> featureCodes);

//    @Query("select c from CoreFeature c where c.featureCode in :featureCodes")
//    public List<Feature> findAllByFeatureCodes(@Param("featureCodes") Set<String> featureCodes);
    List<Feature> findFeatureByCodeIn(List<String> codeList);
}
