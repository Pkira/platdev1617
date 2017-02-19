
-- ===== Setup Databases =====

-- create user
create user usertp with password 'pd1617tp';

-- create database
create database pd1617tp owner = usertp;

-- grant privileges to user
grant all privileges on database pd1617tp to usertp;


-- ===== Create Tables =====

-- category

drop sequence if exists category_seq;


create sequence category_seq;


drop table if exists t_category;


create table t_category (

   id bigint primary key default nextval('category_seq'),

   name varchar(30) not null unique,

   description varchar(100)

);

-- newsletter

drop sequence if exists newsletter_seq;


create sequence newsletter_seq;


drop table if exists t_newsletter;


create table t_newsletter (

   id bigint primary key default nextval('newsletter_seq'),

   message varchar(100) not null,

   creationdate timestamp NOT NULL default CURRENT_TIMESTAMP

);

-- user

drop sequence if exists user_seq;


create sequence user_seq;


drop table if exists t_user;


create table t_user (

   id bigint primary key default nextval('user_seq'),

   username varchar(50) not null,
   
   password varchar(50) not null,
   
   address varchar(100) not null,
   
   balance double precision not null default 0,
   
   account_activation boolean not null default false,
   
   account_suspension boolean not null default false,

   creationdate timestamp NOT NULL default CURRENT_TIMESTAMP

);

-- message

drop sequence if exists message_seq;


create sequence message_seq;


drop table if exists t_message;


create table t_message (

   id bigint primary key default nextval('message_seq'),

   fromid bigint not null references t_user(id),
   
   toid bigint not null references t_user(id),
   
   subject varchar(100) not null,
   
   message varchar(200) not null,

   creationdate timestamp NOT NULL default CURRENT_TIMESTAMP,
   
   status int not null default 0

);

-- notification

drop sequence if exists notification_seq;


create sequence notification_seq;


drop table if exists t_notification;


create table t_notification (

   id bigint primary key default nextval('notification_seq'),

   userid bigint not null references t_user(id),
   
   message varchar(100) not null,

   creationdate timestamp NOT NULL default CURRENT_TIMESTAMP,
   
   status int not null default 0

);

-- item

drop sequence if exists item_seq;


create sequence item_seq;


drop table if exists t_item;


create table t_item (

   id bigint primary key default nextval('item_seq'),
   
   name varchar(50) not null,
   
   categoryid bigint not null references t_category(id),
   
   description varchar(100) not null,
   
   startprice double precision not null default 0,
   
   buynowprice double precision not null default 0,
   
   auctionduration bigint not null,

   ownerid bigint not null references t_user(id),
   
   image varchar(200) not null
   

);

-- auction

drop sequence if exists auction_seq;


create sequence auction_seq;


drop table if exists t_auction;


create table t_auction (

   id bigint primary key default nextval('auction_seq'),
   
   itemid bigint not null references t_item(id),
   
   auctionstate int not null,
   
   itemstate int not null,
   
   startdate timestamp NOT NULL,
   
   enddate timestamp NOT NULL,
   
   lastuserid bigint not null references t_user(id),
   
   sellerid bigint not null references t_user(id)

);

-- auctionlog

drop sequence if exists auctionlog_seq;


create sequence auctionlog_seq;


drop table if exists t_auctionlog;


create table t_auctionlog (

   id bigint primary key default nextval('auctionlog_seq'),
   
   auctionid bigint not null references t_auction(id),
   
   description varchar(100) not null,
   
   creationdate timestamp NOT NULL

);

-- useritem

drop sequence if exists useritem_seq;


create sequence useritem_seq;


drop table if exists t_useritem;


create table t_useritem (

   id bigint primary key default nextval('useritem_seq'),

   userid bigint not null references t_user(id)
   
   itemid bigint not null references t_item(id)
   
   isselling boolean not null default false,
   
   isbuying boolean not null default false,
   
   isfollowing boolean not null default false,

   creationdate timestamp NOT NULL default CURRENT_TIMESTAMP

);























