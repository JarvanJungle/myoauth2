create table if not exists public.financial_institution (
	id                       bigserial                  not null,
	fi_code                  varchar(255),
	fi_name                  varchar(255),
	fi_uuid                  varchar(255),
	contact                  varchar(255),
	country_code             varchar(255),
	designation              varchar(255),
	developer_financing      boolean default false,
	email                    varchar(255),
	fi_portal                boolean default false,
	invoice_financing        boolean default false,
	full_name                varchar(255),
	remarks                  varchar(255),
	status                   varchar(255),
	work_phone               varchar(255),
	logo_url                 varchar(255),
	constraint financial_institution_pkey 
	primary key (id)
);

alter table public.users
    add if not exists fi_id int8;

alter table public.users drop constraint if exists fi_id_fk;

alter table public.users
    add constraint fi_id_fk
        foreign key (fi_id) references public.financial_institution(id);

create table if not exists public.fi_company (
	id                        bigserial                  not null,
	is_active                 boolean default false,
	entity_registration_no    varchar(255),
	created_at                timestamp,
	entity_name               varchar(255),
	gst_no                    varchar(255),
	updated_at                timestamp,
	uuid                      varchar(255),
	fi_id                     int8,
	company_status            varchar(255),
	constraint fi_company_pkey 
    primary key (id),
	constraint fk_fi_id
	foreign key (fi_id) references public.financial_institution
);

create table if not exists public.project (
	id                  bigserial                          not null,
	company             varchar(255),
	company_name        varchar(255),
	company_uuid        varchar(255),
	project_code        varchar(255),
	project_status      varchar(255),
	project_title       varchar(255),
	status              varchar(255),
	user_name           varchar(255),
	user_uuid           varchar(255),
	uuid                varchar(255),
	constraint project_pkey primary key (id)
);

create table if not exists public.fi_project (
	fi_id                     int8                           not null,
	project_id                int8                           not null,
	constraint unique_fi_project unique (fi_id,project_id),
	constraint fk_fi_id foreign key (fi_id) references public.financial_institution,
	constraint fk_project_id foreign key (project_id) references public.project
);

create table if not exists public.user_financial_institution (
	id                              bigserial                not null,
	user_financial_institution_uuid varchar(255),
	fi_id                           int8,
	user_id                         int8,
	constraint user_financial_institution_pkey primary key (id), 
	constraint fk_user_id foreign key (user_id) references public.users,
	constraint fk_fi_id foreign key (fi_id) references public.financial_institution
);

alter table authority.user_privileges
    add if not exists fi_uuid varchar(255);
	
alter table authority.subscription
    add if not exists fi_uuid varchar(255);

alter table public.user_roles
    add if not exists user_financial_institution_id int8;
	
alter table public.user_roles drop constraint if exists user_financial_institution_id_fk;

alter table public.user_roles
    add constraint user_financial_institution_id_fk
        foreign key (user_financial_institution_id) references public.user_financial_institution(id);
