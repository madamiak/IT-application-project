# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table point (
  idpoints                  integer auto_increment not null,
  point_name                varchar(255),
  point_longitude           float,
  point_langitude           float,
  point_type                integer,
  constraint pk_point primary key (idpoints))
;

create table user (
  id                        bigint auto_increment not null,
  login                     varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table point;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

