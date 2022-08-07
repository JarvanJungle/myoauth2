create table if not exists authority.role
(
    id           bigserial,
    uuid         varchar(255)  not null,
    name         varchar(1000) not null,
    description  varchar(1000),
    created_by   varchar(255),
    created_at   timestamp with time zone,
    status       varchar(255),
    company_uuid varchar(255)  not null,
    is_deleted   boolean default false,
    constraint role_pk
        primary key (id)
);

create unique index if not exists role_id_uindex
    on authority.role (id);

create table if not exists authority.role_permission
(
    id         bigserial,
    role_id    bigint,
    feature_id bigint,
    read       boolean default false,
    write      boolean default false,
    approve    boolean default false,
    constraint role_permission_pk
        primary key (id),
    constraint role_permission_role_id_fk
        foreign key (role_id) references authority.role,
    constraint role_permission_features_id_fk
        foreign key (feature_id) references authority.features
);

create unique index if not exists role_permission_id_uindex
    on authority.role_permission (id);

create table if not exists public.rbac_user_role
(
    id              bigserial,
    role_id         bigserial,
    user_company_id bigserial,
    constraint rbac_user_role_pk
        primary key (id),
    constraint rbac_user_role_role_id_fk
        foreign key (role_id) references authority.role,
    constraint rbac_user_role_user_companies_id_fk
        foreign key (user_company_id) references public.user_companies
);

create unique index if not exists rbac_user_role_id_uindex
    on public.rbac_user_role (id);

