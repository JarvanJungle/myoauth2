package com.doxa.oauth2.controllers;

import com.doxa.oauth2.common.UserAgentHelper;
import com.doxa.oauth2.config.AppConfig;
import com.doxa.oauth2.config.Message;
import com.doxa.oauth2.microservices.DTO.EmailDto;
import com.doxa.oauth2.microservices.EmailService;
import com.doxa.oauth2.models.user.User;
import com.doxa.oauth2.models.user.ResetPassword;
import com.doxa.oauth2.serviceImpl.PasswordProviderService;
import com.doxa.oauth2.serviceImpl.PasswordResetService;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@Slf4j
public class ResetPasswordController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private UserAgentHelper userAgentHelpert;
    @Autowired
    private PasswordProviderService passwordProviderService;
    @Value("${passwordreset.url}")
    private String url;

    @GetMapping("forgotpassword")
    public String forgotpassword(ModelMap model) throws IOException, GeoIp2Exception {
        model.addAttribute("email", "");
        return "forgotpassword";
    }

    @GetMapping("resetpassword-email")
    public String resetPasswordEmail(@RequestParam("email") String email, ModelMap model) throws Exception {
        try {
            String token = passwordResetService.generateRandomToken(256);

            // Send email only if it's not send before in the last 5 mins
            User user = passwordResetService.existsInCacheIfNotUpdate(email, token);
            if (user != null) {
                EmailDto emailDTO = new EmailDto(email, user.getName(), Message.EMAIL_RESET_PASSWORD_SUBJECT.getValue(), Message.EMAIL_RESET_PASSWORD.getValue(), "<p>To reset your password, click on the link below:</p><p><a href='" + url + "/resetpassword-token?token=" + token + "'>Click here to reset your password</a></p>");
                emailService.sendEmail(emailDTO);
            }
            model.addAttribute("message_s", "The password reset request has been successfully generated. Please check you mail.");
            return "forgotpassword";
        } catch (Exception e) {
            model.addAttribute("message_s", "The password reset request has been successfully generated. Please check you mail.");
            return "forgotpassword";
        }

    }

    @GetMapping("resetpassword-token")
    public String resetPasswordToken(@RequestParam("token") String token, ModelMap model) throws Exception {
        token = token.replaceAll("\\s", "");
        ResetPassword resetPassword = passwordResetService.getToken(token);
        if (resetPassword != null) {
            model.addAttribute("token", token);
            model.addAttribute("email", resetPassword.getEmail());
            return "resetPassword";
        }
        model.addAttribute("message_f", "The Token is invalid or has expired.");
        return "resetPassword";
    }

    @PostMapping(value = "resetpassword-submit")
    public String resetPasswordSubmit(@RequestParam HashMap<String, String> formData, ModelMap model, @RequestHeader(value = "User-Agent") String agent, HttpServletRequest request) throws Exception {
        // send back email and token incase the input is invalid
        String email = formData.get("email");
        model.addAttribute("email", email);
        model.addAttribute("token", formData.get("token"));
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        String location = userAgentHelpert.getIpAddress(request);
        if (!passwordResetService.emailAndTokenExists(formData.get("email"), formData.get("token").replaceAll("\\s", ""))) {
            model.addAttribute("message_f", "The Token is invalid or has expired.");
            return "resetPassword";
        }

        if ((8 > formData.get("password1").length()) && (8 > formData.get("password2").length()) && (formData.get("password2").length() > 255) && (formData.get("password2").length() > 255)) {
            model.addAttribute("error", "Password length must be greater than 8 characters");
            return "resetPassword";
        }

        if (!formData.get("password1").equals(formData.get("password2"))) {
            model.addAttribute("error", "Passwords should be the same");
            return "resetPassword";
        }
        User user = passwordResetService.resetPassword(email, formData.get("password1"));

        if (user != null) {
            model.addAttribute("message_s", "Password has been successfully reset.");
            String message = "<p>Your password has been successfully reset on: "
                    + LocalDateTime.now()
                    + "<p/> <p>Device Information: <strong>"
                    + agent + "</strong></p>"
                    + "<p/> <p>Device IP address: <strong>"
                    + location + "</strong></p>" +
                    "<p>If you didn't do this, please contact Doxa Team for the support!</p>";
            EmailDto emailDto = new EmailDto(email, user.getName(), "Password has been successfully reset", "", message);
            emailDto.getBody().setTemplateName(AppConfig.EMAIL_TEMPLATE_NOTIFICATION);
            emailService.sendEmail(emailDto);
            log.info("Send email notification successfully");
        } else {
            model.addAttribute("message_f", "Password could not be reset, please try generating another request.");
        }
        return "resetPassword";
    }
}
