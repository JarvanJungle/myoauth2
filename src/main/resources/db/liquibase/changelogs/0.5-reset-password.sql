create table if not exists reset_password
(
    id bigserial,
    email varchar(1000) not null,
    token varchar(1000),
    expired_in timestamp,
    created_at timestamp,
    used boolean default false
    );

create unique index if not exists reset_password_id_uindex
    on reset_password (id);

alter table reset_password drop constraint if exists reset_password_pk;
alter table reset_password
    add constraint reset_password_pk
        primary key (id);
