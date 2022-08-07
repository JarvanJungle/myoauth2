//package com.doxa.oauth2.repositories.authorities.core;
//
//import com.doxa.oauth2.models.authorities.core.Subscription;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.jpa.repository.Modifying;
//
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Set;
//
//public interface SubscriptionRepository extends MongoRepository<Subscription, Long> {
//    @Query("delete from Subscription s where s.companyUuid = :companyUuid")
//    @Modifying
//    @Transactional
//    void deleteAllByCompanyUuid(@Param("companyUuid") String companyUuid);
//
//    List<Subscription> findByCompanyUuid(String companyUuid);
//
//    @Query("select s from Subscription s where s.companyUuid = :companyUuid and s.featureCode = :featureCode")
//    Subscription findByCompanyUuidAndFeatureCode(String companyUuid, String featureCode);
//
//    @Query("select s from Subscription s where s.companyUuid = :companyUuid and s.featureCode in :features")
//    List<Subscription> findByCompanyUuidAndFeature(@Param("companyUuid") String companyUuid, @Param("features") List<String> features);
//
//    @Query("select s from Subscription s where s.companyUuid = :companyUuid and s.featureCode in :features")
//    List<Subscription> findByCompanyUuidAndFeature(@Param("companyUuid") String companyUuid, @Param("features") Set<String> features);
//
//    @Query("delete from Subscription s where s.fiUuid = :fiUuid")
//    @Modifying
//    @Transactional
//	void deleteAllByFiUuid(@Param("fiUuid") String fiUuid);
//
//	List<Subscription> findByFiUuid(String fiUuid);
//
//	@Query("select s from Subscription s where s.fiUuid = :fiUuid and s.featureCode in :features")
//    List<Subscription> findByFiUuidAndFeature(@Param("fiUuid") String companyUuid, @Param("features") Set<String> features);
//}
