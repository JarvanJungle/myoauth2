package com.doxa.oauth2.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Discovery {

    // URL using the https scheme with no query or fragment component that the OP asserts as its
    // Issuer Identifier
    private String issuer;

    // URL of the OP's OAuth 2.0 Authorization Endpoint
    private String authorization_endpoint;

    // URL of the OP's OAuth 2.0 Token Endpoint
    private String token_endpoint;

    // URL of the OP's UserInfo Endpoint
    private String userinfo_endpoint;

    // URL of the OP's JSON Web Key Set [JWK] document
    private String jwks_uri;

    // URL of the OP's Dynamic Client Registration Endpoint
    private String registration_endpoint;

    // URL of OAuth2 introspection endpoint
    private String introspection_endpoint;

    // URL of OAuth2 revocation endpoint
    private String revocation_endpoint;

    // URL of OAuth2 device authorization endpoint
    private String device_authorization_endpoint;

    // URL of OAuth2 request object endpoint
    private String request_object_endpoint;

    // URL of OAuth2 pushed authorization request endpoint
    private String pushed_authorization_request_endpoint;

    // JSON array containing a list of the OAuth 2.0 [RFC6749] scope values that this server supports
    private List<String> scopes_supported = new ArrayList<>();

    // JSON array containing a list of the OAuth 2.0 response_type values that this OP supports.
    // Dynamic OpenID Providers MUST support the code, id_token, and the token id_token Response Type
    // values.
    private List<String> response_types_supported = new ArrayList<>();

    // JSON array containing a list of the OAuth 2.0 response_mode values that this OP supports, as
    // specified in OAuth 2.0 Multiple Response Type Encoding Practices [OAuth.Responses]. If omitted,
    // the default for Dynamic OpenID Providers is ["query", "fragment"].
    private List<String> response_modes_supported = new ArrayList<>();

    // JSON array containing a list of the OAuth 2.0 Grant Type values that this OP supports. Dynamic
    // OpenID Providers MUST support the authorization_code and implicit Grant Type values and MAY
    // support other Grant Types. If omitted, the default value is ["authorization_code", "implicit"].
    private List<String> grant_types_supported = new ArrayList<>();

    // JSON array containing a list of the Authentication Context Class References that this OP
    // supports.
    private List<String> acr_values_supported = new ArrayList<>();

    // JSON array containing a list of the Subject Identifier types that this OP supports. Valid types
    // include pairwise and public.
    private List<String> subject_types_supported = new ArrayList<>();

    // JSON array containing a list of the JWS signing algorithms (alg values) supported by the OP for
    // the ID Token to encode the Claims in a JWT [JWT]. The algorithm RS256 MUST be included. The
    // value none MAY be supported, but MUST NOT be used unless the Response Type used returns no ID
    // Token from the Authorization Endpoint (such as when using the Authorization Code Flow).
    private List<String> id_token_signing_alg_values_supported = new ArrayList<>();

    // JSON array containing a list of Client Authentication methods supported by this Token Endpoint.
    // The options are client_secret_post, client_secret_basic, client_secret_jwt, and
    // private_key_jwt, as described in Section 9 of OpenID Connect Core 1.0 [OpenID.Core]. Other
    // authentication methods MAY be defined by extensions. If omitted, the default is
    // client_secret_basic -- the HTTP Basic Authentication Scheme specified in Section 2.3.1 of OAuth
    // 2.0 [RFC6749].
    private List<String> token_endpoint_auth_methods_supported = new ArrayList<>();

    // JSON array containing a list of the JWS signing algorithms (alg values) supported by the Token
    // Endpoint for the signature on the JWT [JWT] used to authenticate the Client at the Token
    // Endpoint for the private_key_jwt and client_secret_jwt authentication methods. Servers SHOULD
    // support RS256. The value none MUST NOT be used.
    private List<String> token_endpoint_auth_signing_alg_values_supported = new ArrayList<>();

    // JSON array containing a list of the Claim Names of the Claims that the OpenID Provider MAY be
    // able to supply values for. Note that for privacy or other reasons, this might not be an
    // exhaustive list.
    private List<String> claims_supported = new ArrayList<>();

    // Hashing algorithms supported for PKCE challenge
    private List<String> code_challenge_methods_supported = new ArrayList<>();

}
