create schema sa;

create table documents (
  id bigint primary key not null generated always as identity (start with 1, increment by 1),
  name varchar(200) not null,
  description varchar(2000) not null,
  file_name varchar(200) not null,
  content blob(5m) not null,
  content_type varchar(255) not null,
  created_date timestamp not null --default current_timestamp,
);