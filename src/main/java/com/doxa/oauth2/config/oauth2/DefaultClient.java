package com.doxa.oauth2.config.oauth2;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class DefaultClient {
    @Value("${default.client_id}")
    private String clientId;
    @Value("${default.redirect_uri}")
    private String redirectUri;
    private String responseType = "code";
    private String scope = "openid";
}
