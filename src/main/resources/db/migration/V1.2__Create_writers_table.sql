create table writers
(
    id         serial,
    firstname varchar(30),
    lastname  varchar(30),
    region_id  bigint not null,
    role       varchar(30),

    primary key (id),
    foreign key (region_id)
        references regions (id)
        on delete cascade
)