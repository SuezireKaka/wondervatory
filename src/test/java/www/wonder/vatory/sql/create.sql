-- party --
create table t_party(
	id			char(4) primary key,
	descrim		varchar(16) comment 'organi or person',
	name		varchar(255),
	birth		date,
	sex			varchar(16)
);

create table t_account(
	id			char(4) primary key,
	login_id	varchar(255),
	pass_word	varchar(255),
	owner_id	char(4),
	response_id	char(4),
	nick		varchar(255),
	intro		varchar(255),
	alive		tinyint,
	reg_dt		timestamp default current_timestamp(),
	upt_dt		timestamp default current_timestamp() on update current_timestamp()
);
create index idx_login_id on t_account(login_id);

-- work --
create table t_work(
	id				varchar(16) primary key comment 'series, post, reply 순서',
	writer_id		char(4),
	h_level			tinyint,
	content			text(10000),
	like_cnt		int,
	dis_cnt			int,
	read_cnt		int,
	complete		tinyint,
	series_status	varchar(16),
	free			tinyint,
	showcase_name	varchar(255),
	reg_dt			timestamp default current_timestamp(),
	upt_dt			timestamp default current_timestamp() on update current_timestamp()
);
-- create table t_shocase --

-- tool --
create table t_tool(
	id				char(4) primary key,
	x_tool_size		int,
	y_tool_size		int,
	name			varchar(255),
	reg_dt			timestamp default current_timestamp(),
	upt_dt			timestamp default current_timestamp() on update current_timestamp(),
	parent_id		char(4),
	parent_type		varchar(16)
);


create table t_custom_obj(
	id				char(4) primary key,
	descrim			varchar(16),
	x_pos			int,
	y_pos			int,
	x_size			int,
	y_size			int,
	name			varchar(255),
	inner_color		char(7),
	outer_color		char(7),
	parent_id		char(4),
	one_id			char(4),
	other_id		char(4)
);



















