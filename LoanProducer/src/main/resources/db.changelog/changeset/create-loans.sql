-- liquibase formatted sql

-- changeset Author:AyselS

create table if not exists loans(
                                     id bigserial primary key not null,
                                     amount numeric           not null,
                                     term int                 not null,
                                     income numeric           not null,
                                     dsti numeric             not null,
                                     rating int               not null,
                                     status varchar(20)       not null

);
