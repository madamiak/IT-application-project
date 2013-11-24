# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table point (
  idpoints                  integer not null,
  point_name                varchar(255),
  point_longitude           float,
  point_langitude           float,
  point_type                integer,
  constraint pk_point primary key (idpoints))
;

create table user (
  id                        bigint not null,
  login                     varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;

create sequence point_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists point;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists point_seq;

drop sequence if exists user_seq;

