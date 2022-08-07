package com.doxa.oauth2.repositories.user;

import com.doxa.oauth2.models.user.ResetPassword;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.List;

public interface ResetPasswordRepository extends MongoRepository<ResetPassword, Long> {

//    @Query("SELECT r FROM ResetPassword r where r.token=:token AND r.expiredIn >=:now AND r.used is false")
    ResetPassword findByToken(String token, Instant now);

//    @Query("SELECT r FROM ResetPassword r where r.email=:email AND r.expiredIn >=:now AND r.used is false")
    List<ResetPassword> findByEmail(String email, Instant now);
}
