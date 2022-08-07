package com.doxa.oauth2.controllers.oauth2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MultiFactorController {

    @GetMapping("/verify")
    public String verify() {
        return "multifactor";
    }
}
