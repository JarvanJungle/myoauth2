alter table authority.features
    add if not exists category varchar(255);

alter table authority.features
    add if not exists profile varchar(100);
alter table authority.features
    add if not exists sub_category varchar(100);

alter table authority.user_privileges
    add if not exists feature_id bigint;

alter table authority.subscription
    add if not exists feature_id bigint;

alter table authority.features
    add column if not exists cat_sequence int;

alter table authority.features
    add column if not exists sub_cat_sequence int;

ALTER TABLE public.users ADD COLUMN if not exists  "country_code" varchar(50) DEFAULT '65';