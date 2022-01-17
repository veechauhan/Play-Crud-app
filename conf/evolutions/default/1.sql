-- !Ups

create table users (
    id text primary key,
    name text not null,
    password text not null
);

-- !Downs

--drop table users;