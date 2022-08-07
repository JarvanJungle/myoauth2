create table if not exists authority.authorization_code
(
    id bigint,
    client_id varchar(255),
    scopes varchar(500),
    code varchar(500),
    expiry timestamp ,
    subject varchar(500),
    redirect_uri varchar(1000),
    nonce varchar(500),
    code_challenge varchar(255),
    code_challenge_method varchar(255)
);

