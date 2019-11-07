create schema sa;

create table users (
  id bigint primary key not null generated always as identity (start with 1, increment by 1),
  username varchar(128) not null,
  password varchar(128) not null,
  name varchar(255) not null,
  role varchar(10) not null,
  enabled boolean not null
);
