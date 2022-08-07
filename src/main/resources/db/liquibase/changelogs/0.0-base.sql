create schema if not exists authority;

create sequence if not exists public.hibernate_sequence;

create table if not exists public.registered_client
(
    id                  serial not null,
    identifier          varchar(50),
    client_id           varchar(50),
    client_secret       varchar(255),
    confidential        boolean default false,
    access_token_format varchar default 'jwt'::character varying,
    created_at          date    default now(),
    redirect_uris       text,
    cors_uris           text,
    grant_types         varchar(255),
    constraint oauth_client_pk
    primary key (id),
    constraint oauth_client_pk_2
    unique (client_id)
    );

create index if not exists oauth_client_client_id_index
    on public.registered_client (client_id);

create table if not exists public.redirect_uris
(
    id           serial      not null,
    identifier   varchar(50) not null,
    client_id    varchar(50),
    redirect_uri text,
    constraint client_redirect_uri_pk
    primary key (id),
    constraint client_redirect_uri_oauth_client_client_id_fk
    foreign key (client_id) references public.registered_client (client_id)
    on update cascade on delete cascade
    );

create unique index if not exists client_redirect_uri_id_uindex
    on public.redirect_uris (id);

create unique index if not exists client_redirect_uri_identifier_uindex
    on public.redirect_uris (identifier);

create table if not exists public.token
(
    value         text,
    expiry        date,
    revoked       boolean,
    "accessToken" text
);

create table if not exists public.jsonwebtoken
(
    access_token boolean,
    expiry       date,
    value        text,
    revoked      boolean default false,
    id           serial not null,
    constraint jsonwebtoken_pk
    primary key (id)
    );

create unique index if not exists jsonwebtoken_id_uindex
    on public.jsonwebtoken (id);

create table if not exists public.opaque_token
(
    id            serial not null,
    subject       text,
    client_id     varchar(50),
    issuer        varchar(255),
    issued_at     date    default now(),
    not_before    date    default now(),
    refresh_token boolean default false,
    expiry        date,
    value         text,
    revoked       boolean,
    scope         text,
    constraint opaque_token_pk
    primary key (id)
    );

create unique index if not exists opaque_token_id_uindex
    on public.opaque_token (id);

create table if not exists public.opaque_token_scope
(
    id              serial  not null,
    opaque_token_id integer not null,
    scope           varchar(250),
    constraint opaque_token_scope_pk
    primary key (id)
    );

create unique index if not exists opaque_token_scope_id_uindex
    on public.opaque_token_scope (id);

create table if not exists public.entities
(
    id                     bigserial    not null,
    entity_registration_no varchar(255) not null,
    uuid                   varchar(50),
    constraint entities_pkey
    primary key (id)
    );

create index if not exists entities_uuid_index
    on public.entities (uuid);

create index if not exists entities_entityregistrationnumber_index
    on public.entities (entity_registration_no);

create table if not exists public.company
(
    id                     bigserial                              not null,
    entity_name            varchar(255),
    gst_no                 varchar(25),
    is_active              boolean,
    entity_registration_no varchar(255)                           not null,
    entity_type            varchar(255)                           not null,
    industry_type          varchar(255)                           not null,
    is_gst_applicable      boolean,
    country                varchar(50),
    onboarding_status      varchar(50),
    uuid                   varchar(50),
    subscription_expiry    timestamp                default (now() + '1 year'::interval),
    created_at             timestamp with time zone default now() not null,
    updated_at             timestamp with time zone default now() not null,
    entity_id              bigint                                 not null,
    is_main                boolean,
    constraint companies_pkey
    primary key (id),
    constraint fk_entity_id
    foreign key (entity_id) references public.entities
    );

create index if not exists companies_uuid_index
    on public.company (uuid);

create index if not exists companies_ismain_entity_index
    on public.company (is_main, entity_id);

create table if not exists public.users
(
    id              bigserial                              not null,
    email           varchar(50)                            not null,
    name            varchar(50),
    password_salt   varchar(50),
    hashed_password varchar(255),
    is_active       boolean                  default true,
    is_deleted      boolean                  default true,
    entity_id       bigint,
    created_at      timestamp with time zone default now() not null,
    updated_at      timestamp with time zone default now() not null,
    designation     varchar(255),
    work_number     varchar(255)                           not null,
    uuid            varchar(255)                           not null,
    country_code    varchar(50)              default '65'::character varying,
    constraint users_pkey
    primary key (id),
    constraint fk_entity_id
    foreign key (entity_id) references public.entities
    );

create index if not exists users_name_index
    on public.users (name);

create index if not exists users_uuid_index
    on public.users (uuid);

create unique index if not exists users_email_uindex
    on public.users (email);

create table if not exists public.user_companies
(
    id                  bigserial    not null,
    user_id             bigint,
    companies_id        bigint,
    user_companies_uuid varchar(255) not null,
    constraint user_companies_pkey
    primary key (id),
    constraint unique_user_companies
    unique (user_id, companies_id),
    constraint fk_user_id
    foreign key (user_id) references public.users,
    constraint fk_companies_id
    foreign key (companies_id) references public.company
    );

create table if not exists public.roles
(
    id        bigserial   not null,
    role_name varchar(25) not null,
    role_code varchar(25) not null,
    constraint roles_pkey
    primary key (id)
    );

create table if not exists public.user_roles
(
    id                bigserial not null,
    role_id           bigint,
    user_companies_id bigint,
    constraint user_roles_pkey
    primary key (id),
    constraint fk_role_id
    foreign key (role_id) references public.roles,
    constraint fk_user_companies_id
    foreign key (user_companies_id) references public.user_companies
    );

create table if not exists public.language
(
    id            bigserial not null,
    language_name varchar(20),
    language_code varchar(10),
    constraint language_pkey
    primary key (id),
    constraint language_language_code_key
    unique (language_code)
    );

create table if not exists public.user_settings
(
    id                bigserial not null,
    is_2fa            boolean default false,
    language          bigint,
    user_id           bigint,
    two_fa_secret     varchar(50),
    must_set_password boolean default false,
    constraint user_settings_pkey
    primary key (id),
    constraint fk_language
    foreign key (language) references public.language,
    constraint fk_user_id
    foreign key (user_id) references public.users
    );

create index if not exists usersettings_user_index
    on public.user_settings (user_id);

create table if not exists public.entity_representative
(
    id          bigserial not null,
    name        varchar(50),
    email       varchar(50),
    work_number varchar(50),
    user_role   varchar(50),
    entity_id   bigint,
    constraint entity_representative_pkey
    primary key (id),
    constraint fk_entity_id
    foreign key (entity_id) references public.entities
    );

create table if not exists public.industry_type
(
    id            bigserial    not null,
    industry_type varchar(100) not null,
    constraint industry_type_pkey
    primary key (id)
    );

create table if not exists public.entity_type
(
    id          bigserial    not null,
    entity_type varchar(100) not null,
    constraint entity_type_pkey
    primary key (id)
    );

create table if not exists public.documents_meta_data
(
    id          bigserial                              not null,
    guid        varchar(255)                           not null,
    title       varchar(255)                           not null,
    file_name   varchar(255)                           not null,
    created_at  timestamp with time zone default now() not null,
    updated_at  timestamp with time zone default now() not null,
    company_id  bigint,
    delete_from bigint,
    constraint documents_meta_data_pkey
    primary key (id),
    constraint fk_company_id
    foreign key (company_id) references public.company
    );

create table if not exists public.admin_categories
(
    id            bigserial   not null,
    category_name varchar(50) not null,
    category_code varchar(50) not null,
    constraint admin_categories_pkey
    primary key (id)
    );

create table if not exists public.administratives
(
    id                  bigserial   not null,
    administrative_name varchar(50) not null,
    administrative_code varchar(50) not null,
    admin_categories_id bigint,
    constraint administratives_pkey
    primary key (id),
    constraint fk_admin_categories_id
    foreign key (admin_categories_id) references public.admin_categories
    );

create table if not exists public.user_administratives
(
    id                 bigserial not null,
    user_companies_id  bigint,
    administratives_id bigint,
    constraint user_administratives_pkey
    primary key (id),
    constraint fk_user_companies_id
    foreign key (user_companies_id) references public.user_companies,
    constraint fk_administratives_id
    foreign key (administratives_id) references public.administratives
    );

-- authority
create table if not exists authority.modules
(
    id          bigserial not null,
    uuid        varchar(100),
    module_name varchar(255),
    module_code varchar(50),
    constraint modules_pk
    primary key (id)
    );

create unique index if not exists modules_id_uindex
    on authority.modules (id);

create unique index if not exists modules_module_code_uindex
    on authority.modules (module_code);

create table if not exists authority.features
(
    id           bigserial not null,
    uuid         varchar(100),
    module_code  varchar(50),
    feature_name varchar(255),
    feature_code varchar(100),
    category     varchar(255),
    profile      varchar(100),
    sub_category varchar(100),
    constraint features_pk
    primary key (id),
    constraint features_module_code_fkey
    foreign key (module_code) references authority.modules (module_code)
    );

create unique index if not exists features_feature_code_uindex
    on authority.features (feature_code);

create unique index if not exists features_id_uindex
    on authority.features (id);

create table if not exists authority.subscription
(
    id           bigserial not null,
    uuid         varchar(100),
    module_code  varchar(50),
    feature_code varchar(50),
    start_date   date,
    end_date     date,
    company_uuid varchar(100),
    feature_name varchar(255),
    feature_id   bigint,
    constraint subscription_pk
    primary key (id),
    constraint subscription_module_code_fkey
    foreign key (module_code) references authority.modules (module_code),
    constraint subscription_feature_code_fkey
    foreign key (feature_code) references authority.features (feature_code)
    );

create unique index if not exists subscription_id_uindex
    on authority.subscription (id);

create table if not exists authority.user_privileges
(
    id                  bigserial not null,
    uuid                varchar(100),
    user_uuid           varchar(100),
    feature_code        varchar(50),
    company_uuid        varchar(100),
    privilege_action_id bigint,
    feature_id          bigint,
    constraint user_privileges_pk
    primary key (id),
    constraint user_privileges_feature_code_fkey
    foreign key (feature_code) references authority.features (feature_code)
    );

create unique index if not exists user_privileges_id_uindex
    on authority.user_privileges (id);

create table if not exists authority.user_privilege_action
(
    id                bigserial not null,
    uuid              varchar(100),
    user_privilege_id bigint,
    read              boolean,
    write             boolean,
    approve           boolean,
    constraint user_privilege_action_pk
    primary key (id),
    constraint user_privilege_action_user_privilege_id_fkey
    foreign key (user_privilege_id) references authority.user_privileges
    on delete cascade
    );

alter table authority.user_privileges drop constraint if exists user_privileges_user_privilege_action_id_fk;

alter table authority.user_privileges
    add constraint user_privileges_user_privilege_action_id_fk
        foreign key (privilege_action_id) references authority.user_privilege_action
            on update cascade on delete cascade;

create unique index if not exists user_privilege_action_id_uindex
    on authority.user_privilege_action (id);

