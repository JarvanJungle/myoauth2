alter table public.company
    add if not exists remarks varchar(1000);
alter table public.entity_representative
    add if not exists country_code varchar(100);
