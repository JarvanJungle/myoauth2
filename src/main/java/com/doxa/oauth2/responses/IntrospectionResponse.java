package com.doxa.oauth2.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IntrospectionResponse {

    /**
     * REQUIRED. Boolean indicator of whether or not the presented token is currently active. The
     * specifics of a token's "active" state will vary depending on the implementation of the
     * authorization server and the information it keeps about its tokens, but a "true" value return
     * for the "active" property will generally indicate that a given token has been issued by this
     * authorization server, has not been revoked by the resource owner, and is within its given time
     * window of validity (e.g., after its issuance time and before its expiration time).
     */
    private boolean active;

    /**
     * OPTIONAL. A JSON string containing a space-separated list of scopes associated with this token,
     * in the format described in Section 3.3 of OAuth 2.0 [RFC6749].
     */
    private String scope;

    /**
     * OPTIONAL. Client identifier for the OAuth 2.0 client that requested this token.
     */
    private String client_id;

    /**
     * OPTIONAL. Human-readable identifier for the resource owner who authorized this token.
     */
    private String username;

    /**
     * OPTIONAL. Type of the token as defined in Section 5.1 of OAuth 2.0 [RFC6749].
     */
    private String token_type;

    /**
     * OPTIONAL. Integer timestamp, measured in the number of seconds since January 1 1970 UTC,
     * indicating when this token will expire, as defined in JWT [RFC7519].
     */
    private long exp;

    /**
     * OPTIONAL. Integer timestamp, measured in the number of seconds since January 1 1970 UTC,
     * indicating when this token was originally issued, as defined in JWT [RFC7519].
     */
    private long iat;

    /**
     * OPTIONAL. Integer timestamp, measured in the number of seconds since January 1 1970 UTC,
     * indicating when this token is not to be used before, as defined in JWT [RFC7519].
     */
    private long nbf;

    /**
     * OPTIONAL. Subject of the token, as defined in JWT [RFC7519]. Usually a machine-readable
     * identifier of the resource owner who authorized this token.
     */
    private String sub;

    /**
     * OPTIONAL. Service-specific string identifier or list of string identifiers representing the
     * intended audience for this token, as defined in JWT [RFC7519].
     */
    private List<String> aud;

    /**
     * OPTIONAL. String representing the issuer of this token, as defined in JWT [RFC7519].
     */
    private String iss;

    /**
     * OPTIONAL. String identifier for the token, as defined in JWT [RFC7519].
     */
    private String jti;

    private String error1;

    public IntrospectionResponse() {
    }

    public IntrospectionResponse(boolean active) {
        this.active = active;
    }

    public IntrospectionResponse(String error) {
        this.error1 = error;
    }


    @Override
    public String toString() {
        return "IntrospectionResponse{"
                + "active="
                + active
                + ", scope='"
                + scope
                + '\''
                + ", client_id='"
                + client_id
                + '\''
                + ", username='"
                + username
                + '\''
                + ", token_type='"
                + token_type
                + '\''
                + ", exp="
                + exp
                + ", iat="
                + iat
                + ", nbf="
                + nbf
                + ", sub='"
                + sub
                + '\''
                + ", aud="
                + aud
                + ", iss='"
                + iss
                + '\''
                + ", jti='"
                + jti
                + '\''
                + ", error='"
                + error1
                + '\''
                + '}';
    }
}
