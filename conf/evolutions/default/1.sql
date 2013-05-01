# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                        integer auto_increment not null,
  name                      varchar(255),
  facebook_token            varchar(255),
  city                      varchar(255),
  phone                     varchar(255),
  birthday                  datetime,
  is_admin                  integer,
  is_active                 integer,
  constraint pk_user primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

