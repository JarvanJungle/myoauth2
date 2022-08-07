package com.doxa.oauth2.controllers.oauth2;


import com.doxa.oauth2.serviceImpl.RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class RegisteredClientController {

  private final RegisteredClientService registeredClientService;

  @Autowired
  public RegisteredClientController(RegisteredClientService registeredClientService) {
    this.registeredClientService = registeredClientService;
  }

  @GetMapping("/login")
  public String login(Model model, String error, String logout) {
    log.info(model.toString());
    log.error("error 1", error);
    if (error != null )
      model.addAttribute("error", "Your username and password is invalid.");

    log.error("logout 1", error);
    if (logout != null)
      model.addAttribute("message", "You have been logged out successfully.");

    return "login";
  }

}
