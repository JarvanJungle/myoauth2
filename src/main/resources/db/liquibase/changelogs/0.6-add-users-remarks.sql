alter table public.users
    add if not exists remarks varchar(1000);