//package com.doxa.oauth2.repositories.authorities;
//
//import com.doxa.oauth2.models.authorities.Administratives;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.data.repository.query.Param;
//
//public interface AdministrativeRepository extends MongoRepository <Administratives, Long> {
//
////    @Query("SELECT a FROM Administratives a WHERE a.adminCategories.categoryCode=:categoryCode AND a.administrativeCode =:administrativeCode")
//    Administratives findIdByAdminCodeAndCategoryCode(@Param("administrativeCode") String administrativeCode, @Param("categoryCode") String categoryCode);
//}
