package com.doxa.oauth2.models.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "reset_password")
public class ResetPassword {
    @Id
    private Long id;

    private String email;

    private String token;

    private Instant expiredIn;

    private Instant createdAt;

    private boolean used;
}
