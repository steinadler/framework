-- 2014-07-14 weinianjie
create database framework;

use framework;

-- demo表 ---
drop table if exists demo;
create table demo(
	field1 varchar(32) not null comment '字段1',
	field2 varchar(32) not null comment '字段2'
) engine=InnoDB default charset=utf8 collate=utf8_bin;
	
-- 用户表 ---
drop table if exists web_user;
create table web_user(
	id varchar(32) not null comment 'UUID',
	showName varchar(128) not null comment '显示名',
	username varchar(64) not null comment '用户名',
	password varchar(64) not null comment '密码',
	cts datetime comment '创建时间',
	uts datetime comment '修改时间',
	primary key (id)
) engine=InnoDB default charset=utf8 collate=utf8_bin;

insert into web_user values('10000', 'admin', 'admin', md5('admin'), now(), now());


-- 菜单表 ---
drop table if exists web_menu;
create table web_menu(
	id varchar(32) not null comment 'UUID',
	menuName varchar(32) not null comment '菜单名',
	parentId varchar(32) not null default '0' comment '父UUID',
	pageUrl varchar(256) not null comment '页面地址',
	priority int(4) default 0 comment '优先级',
	cts datetime comment '创建时间',
	uts datetime comment '修改时间',
	primary key (id)
) engine=InnoDB default charset=utf8 collate=utf8_bin;

insert into web_menu values('10000', 'menu1', '0', 'x1/menu1.action', 0, now(), now());