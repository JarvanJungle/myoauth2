//package com.doxa.oauth2.repositories.authorities.core;
//
//import com.doxa.oauth2.models.authorities.core.Module;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface CoreModuleRepository extends MongoRepository<Module, Long> {
////    @Query("select m from Module m where m.moduleCode in (select c.moduleCode from CoreFeature c where c.featureCode in (select s.featureCode from Subscription s where s.companyUuid = :companyUuid)) group by m.id")
//    public List<Module> listSubscription(@Param("companyUuid") String companyUuid);
//}
