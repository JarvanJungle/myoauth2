package com.doxa.oauth2.serviceImpl;

import com.doxa.oauth2.models.user.User;
import com.doxa.oauth2.models.user.ResetPassword;
import com.doxa.oauth2.repositories.user.ResetPasswordRepository;
import com.doxa.oauth2.repositories.user.UserRepository;
import com.doxa.oauth2.services.IPasswordResetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class PasswordResetService implements IPasswordResetService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordProviderService passwordProviderService;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetService.class);

    PasswordResetService () {}
    @Override
    public String generateRandomToken(int length) {
        return RandomStringUtils.random(length, 0,0, true, true, null, new SecureRandom());
    }

    @Override
    public User existsInCacheIfNotUpdate(String email, String token) {
        User User = userRepository.findByEmailAndStatus(email, "ACTIVE").get();
        if (User == null) {
            log.info("User not found for email {}", email);
            return null;
        }
        List<ResetPassword> resetPassword = resetPasswordRepository.findByEmail(email, Instant.now());
        if (resetPassword.size() > 0) {
            return null;
        }
        saveResetPassword(token, email);
        return User;
    }

    @Override
    public boolean emailAndTokenExists(String email, String token) throws ExecutionException {
        ResetPassword resetPassword = resetPasswordRepository.findByToken(token, Instant.now());
        if (resetPassword == null) {
            return false;
        }
        if (!resetPassword.getEmail().equals(email)) {
            return false;
        }
        resetPassword.setUsed(true);
        resetPasswordRepository.save(resetPassword);
        return true;
    }

    @Override
    public User resetPassword(String email, String newPassword) {
        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                return null;
            }
            User user = optionalUser.get();
            String pwdEncoded = passwordProviderService.passwordEncoder().encode(newPassword);
            user.setPassword(pwdEncoded);
//            user.getSettings().setMustSetPassword(false);
            userRepository.save(user);
            return user;
        }catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    @Override
    public ResetPassword getToken(String token) {
        return resetPasswordRepository.findByToken(token, Instant.now());
    }

    public ResetPassword saveResetPassword(String token, String email) {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setEmail(email);
        resetPassword.setToken(token);
        resetPassword.setCreatedAt(Instant.now());
        resetPassword.setExpiredIn(Instant.now().plus(5, ChronoUnit.MINUTES));
        return resetPasswordRepository.save(resetPassword);
    }
}
