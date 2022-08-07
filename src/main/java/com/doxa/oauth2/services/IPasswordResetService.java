package com.doxa.oauth2.services;

import com.doxa.oauth2.models.user.User;
import com.doxa.oauth2.models.user.ResetPassword;

import java.util.concurrent.ExecutionException;

public interface IPasswordResetService {
    public String generateRandomToken(int length);
    public User existsInCacheIfNotUpdate(String email, String token) throws ExecutionException;
    public boolean emailAndTokenExists(String email, String token) throws ExecutionException;
    public User resetPassword(String email, String newPassword);

    ResetPassword getToken(String token);
}
