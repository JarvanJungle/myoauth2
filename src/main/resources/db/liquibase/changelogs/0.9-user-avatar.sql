alter table public.users
    add if not exists avatar_url varchar(500);