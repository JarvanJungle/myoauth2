package com.doxa.oauth2.pkce;

public class CodeChallengeError extends Exception {
    public CodeChallengeError() {
        super("PKCE: Code  challenge failed");
    }
}
