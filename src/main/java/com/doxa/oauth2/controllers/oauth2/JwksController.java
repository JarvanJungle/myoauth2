package com.doxa.oauth2.controllers.oauth2;

import com.doxa.oauth2.components.JwtPki;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping(JwksController.ENDPOINT)
public class JwksController {

  public static final String ENDPOINT = "/jwks";

  private final JwtPki jwtPki;

  public JwksController(JwtPki jwtPki) {
    this.jwtPki = jwtPki;
  }

  @GetMapping
  public Map<String, Object> jwksEndpoint() {
    return jwtPki.getJwkSet().toJSONObject();
  }
}
