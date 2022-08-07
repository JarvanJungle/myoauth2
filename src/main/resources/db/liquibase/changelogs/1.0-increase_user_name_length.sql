alter table public.users
    alter column name type varchar(100) using name::varchar(100);