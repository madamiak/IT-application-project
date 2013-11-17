# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table destination (
  id                        bigint not null,
  value                     varchar(255),
  constraint pk_destination primary key (id))
;

create table user (
  id                        bigint not null,
  login                     varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;

create sequence destination_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists destination;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists destination_seq;

drop sequence if exists user_seq;

