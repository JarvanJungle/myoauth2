alter table public.company
    add if not exists buyer boolean default true not null;

alter table public.company
    add if not exists supplier boolean default false not null;

alter table public.company
    add if not exists developer boolean default false not null;

