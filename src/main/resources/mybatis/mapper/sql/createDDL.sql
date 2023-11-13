
create table T_I1(id varchar(4));


insert into T_I1(id) values ('a');
insert into T_I1(id) values ('b');
insert into T_I1(id) values ('c');
insert into T_I1(id) values ('d');
insert into T_I1(id) values ('e');
insert into T_I1(id) values ('f');
insert into T_I1(id) values ('g');
insert into T_I1(id) values ('h');
insert into T_I1(id) values ('i');
insert into T_I1(id) values ('j');
insert into T_I1(id) values ('k');
insert into T_I1(id) values ('l');
insert into T_I1(id) values ('m');
insert into T_I1(id) values ('n');
insert into T_I1(id) values ('o');
insert into T_I1(id) values ('p');
insert into T_I1(id) values ('q');
insert into T_I1(id) values ('r');
insert into T_I1(id) values ('s');
insert into T_I1(id) values ('t');
insert into T_I1(id) values ('u');
insert into T_I1(id) values ('v');
insert into T_I1(id) values ('w');
insert into T_I1(id) values ('x');
insert into T_I1(id) values ('y');
insert into T_I1(id) values ('z');
insert into T_I1(id) values ('0');
insert into T_I1(id) values ('1');
insert into T_I1(id) values ('2');
insert into T_I1(id) values ('3');
insert into T_I1(id) values ('4');
insert into T_I1(id) values ('5');
insert into T_I1(id) values ('6');
insert into T_I1(id) values ('7');
insert into T_I1(id) values ('8');
insert into T_I1(id) values ('9');




 
create table T_I2(id varchar(4));
create table T_I4(id varchar(4));





--2글자짜리 만들기
INSERT INTO T_I2(id)
SELECT CONCAT(a.id, b.id)
FROM T_I1 a, T_I1 b
;
--4글자짜리 만들기
INSERT INTO T_I4(id)
SELECT CONCAT(a.id, b.id)
FROM T_I2 a, T_I2 b
;


create table T_IDSEED(
   SEQ integer primary key,
   SEED char(4)
);
	
-- 문자랑 순번을  T_ID_SEED테이블에 집어넣기	
insert into T_IDSEED(SEED, SEQ)
	SELECT tid.id iid, @ROWNUM:=@ROWNUM+1 AS rowNum
	FROM T_I4 as tid, (SELECT @ROWNUM:=0) AS R
	order by iid asc;

	

CREATE TABLE t_sequence (
	NAME VARCHAR(255) PRIMARY KEY,
	NUM INT NOT NULL DEFAULT 0
);

--INSERT INTO t_sequence (NAME) VALUES ('S_bulitine_board');--

DELIMITER $$
CREATE OR REPLACE FUNCTION NEXT_PK(t_NAME VARCHAR(255)) RETURNS char(4)
BEGIN
	DECLARE unrecorded boolean;	
	DECLARE r_sequence char(4);

	select not exists(select num from t_sequence where NAME = t_NAME) into unrecorded;

	if (unrecorded) then

		INSERT INTO t_sequence(NAME) VALUES (t_NAME);
 	end if;

	UPDATE t_sequence SET NUM = NUM + 1  WHERE NAME = t_NAME;


	SELECT c.SEED INTO r_sequence
	  FROM t_sequence s, T_IDSEED c
	 WHERE s.NAME = t_NAME
	   and s.NUM = c.SEQ;
	
	RETURN r_sequence;
END;
$$
DELIMITER ;





create table T_CODE(
	Code_type	varchar(255) not null,
	code_val	varchar(255),
	validation_re varchar(255)
);

create table T_contact_Point(
	owner_id	char(4),
	cp_type		varchar(255),
	cp_val		varchar(255),
	primary key(owner_id, cp_type)
);

create table T_TAG(
	id			char(4) primary key,
	word		varchar(255),
	description	TEXT(65000)
);

create table T_TGT_TAG(
	tgt_name	varchar(255),	/* post, party */
	tgt_id		varchar(255),
	tag_id		char(4),
	tf			int,	primary key(tgt_name, tag_id, tgt_id) /* post*/
);

create index idx_tgt_tag on T_TGT_TAG(tgt_name, tgt_id, tag_id);



create table T_attach(
	owner_type		varchar(255),
	owner_id		varchar(255),
	uuid			char(32) PRIMARY KEY,
	path			varchar(2000),
	name			varchar(500),
	type_name		varchar(100)

);

create index idx_attach_owner on T_attach(owner_id,uuid);



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
	alive		TINYINT(4) DEFAULT '1',
	reg_dt		timestamp default current_timestamp(),
	upt_dt		timestamp default current_timestamp() on update current_timestamp()
);
create unique index idx_login_id on t_account(login_id);
create unique index idx_login_id on t_account(nick);

-- work --
create table t_work(
	id				varchar(255) primary key comment 'series, post, reply 순서',
	writer_id		char(4),
	bb_id			char(4),
	descrim			varchar(16),
	h_tier			tinyint,
	title			varchar(255),
	content			text(10000),
	read_cnt		int default 0,
	like_cnt		int default 0,
	dis_cnt			int default 0,
	complete		tinyint default 1,
	series_status	varchar(16),
	free			tinyint,
	reg_dt			timestamp default current_timestamp(),
	upt_dt			timestamp default current_timestamp() on update current_timestamp()
);
-- create table t_shocase --

--화수표시도 필요할거같은--

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

create table T_bb(
	id			char(4) primary key,
	name		varchar(255) not null,
	descrip 	varchar(255),
	post_cnt	int
);



create table t_role(
	role		varchar(16) comment 'from t_sys_role',
	account_id	char(4)
);

	
-----10 18 ----------------------------------
	--다시--
create table T_favorites(
	owner_id	char(4) comment '어카운트id',
	response_id	varchar(255) comment '시리즈id',
	favorites	tinyint DEFAULT 1,
	primary key(owner_id, response_id)
);

create index idx_prop_type on T_custom_property(prop_type);

-----10 23 ------------------------------------

/* 업데이트 - 서버에 업데이트하고 주석 지워서 push해주세요 */

create table t_sys_role(
	level	tinyint primary key,
	role	varchar(16),
	descrip varchar(255)
);

create table t_sys_act(
	act			varchar(16) primary key,
	descrip 	varchar(255)
);

create table t_sys_allow(
	role	varchar(16),
	act		varchar(16)
);

create table t_report(
	id				char(4) primary key,
	reporter_id		char(4), /* 비로그인 유저의 신고 처리는? */
	suspect_id		varchar(16),
	suspect_table	varchar(10) comment '계정 -> t_account, 작품 또는 댓글 -> t_work',
	cause			text(5000) comment '유저가 대상을 신고한 이유 자세히 듣기',
	reg_dt			timestamp default current_timestamp(),
	upt_dt			timestamp default current_timestamp()
);
create index idx_time on t_report(reported_upt);

create table t_report_cls(
	rpt_id		char(4),
	rpt_level   tinyint,
	rpt_type	varchar(16)
);
create index idx_rpt_id on t_report_cls(rpt_id);

create table t_sys_rptype(
	level		tinyint primary key,
	rpt_type	varchar(16),
	rpt_info	varchar(255)
);
create index idx_rpt on t_sys_rptype(rpt_type);



----10 17 예전에 바꿨던------------

create table T_custom_property(
	owner_id	char(4),
	level		MEDIUMINT,
	prop_type	varchar(255),
	prop_val	varchar(255),
	primary key(owner_id, level)
);

---- 10 26 ------------------

create table t_rpt_process(
	id				char(4) primary key,
	origin_id		char(4),
	complete		tinyint default 0 comment '처리 완료시 1',
	processer_id	char(4),
	process_when	timestamp,
	process_msg		varchar(255) comment '매니저가 처리한 내용을 요약해서 정리'
);

-----  10 31 --------------------

create table t_sys_remocon(
	remocon_name	varchar(32) not null,
	key_level		tinyint not null,
	key_name		varchar(16) not null,
	key_use			varchar(8) not null,
	key_click_cnt	tinyint default 1,
	primary key(remocon_name, key_level)
);


-----  11 02 ---------------------

/** create table T_kakao_account(
	id			char(4) primary key,
	kakao_id	int,
	owner_id	char(4),
	response_id	char(4),
	nick		varchar(255),
	alive		TINYINT(4) DEFAULT '1',
	reg_dt		timestamp default current_timestamp(),
	upt_dt		timestamp default current_timestamp() on update current_timestamp()
);
Top 전략으로 변경함에 따라 폐기*/
create table t_account(
	id			char(4) primary key,
	login_id	varchar(255),
	kakao_id	long,
	descrim		varchar(255),
	pass_word	varchar(255),
	owner_id	char(4),
	response_id	char(4),
	nick		varchar(255),
	kakao_nick	varchar(255),
	intro		varchar(255),
	login_code	TINYINT(4) DEFAULT '1',
	alive		TINYINT(4) DEFAULT '1',
	reg_dt		timestamp default current_timestamp(),
	upt_dt		timestamp default current_timestamp() on update current_timestamp()
);
create unique index idx_login_id on t_account(login_id);
create unique index idx_kakao_id on t_account(kakao_id);
create unique index idx_nick on t_account(nick);
create index idx_login_id on t_account(nick);

ALTER TABLE t_account MODIFY kakao_id bigint -- 인덱스 아웃오브 에러 ㅡ.ㅡ

ALTER TABLE t_sys_remocon
 ADD COLUMN key_info VARCHAR(255);

 
 ---  11-07  -------------
 
 ALTER TABLE t_tool
MODIFY id VARCHAR(255); -- 컴포즈 패턴 형식으로 수정
 ALTER TABLE t_tool
   ADD COLUMN h_tier tinyint AFTER id; -- 컴포즈 패턴화에 따른 레벨 처리
 ALTER TABLE t_tool
Modify h_tier TINYINT DEFAULT 0; -- 기본 레벨은 0
create table t_tool(
	id				VARCHAR(255) primary key,
	h_tier			tinyint DEFAULT 0,
	x_tool_size		int,
	y_tool_size		int,
	name			varchar(255),
	reg_dt			timestamp default current_timestamp(),
	upt_dt			timestamp default current_timestamp() on update current_timestamp(),
	parent_id		char(4),
	parent_type		varchar(16)
);

 ALTER TABLE t_custom_obj
MODIFY parent_id VARCHAR(255)  -- 부모가 컴포즈 패턴에 따라 아이디가 길어졌으니 자식도 맞추기
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
	parent_id 		VARCHAR(255),
	one_id			char(4),
	other_id		char(4)
);

 ALTER TABLE t_sys_remocon
   ADD COLUMN key_auth VARCHAR(64)  -- 프론트에서 보고 안 맞으면 아예 표시도 안 되도록
create table t_sys_remocon(
	remocon_name	varchar(32) not null,
	key_level		tinyint not null,
	key_name		varchar(16) not null,
	key_use			varchar(8) not null,
	key_click_cnt	tinyint default 1,
	key_auth		VARCHAR(64),
	primary key(remocon_name, key_level)
);


create table t_sys_genre(
	id			char(4) primary key,
	login_id	varchar(255),
	kakao_id	long,
	descrim		varchar(255),
	pass_word	varchar(255),
	owner_id	char(4),
	response_id	char(4),
	nick		varchar(255),
	kakao_nick	varchar(255),
	intro		varchar(255),
	login_code	TINYINT(4) DEFAULT '1',
	reg_dt		timestamp default current_timestamp(),
	upt_dt		timestamp default current_timestamp() on update current_timestamp()
);

create table t_sys_genre(
	id			char(4) primary key,
	genre_type	varchar(16),
	genre_info	varchar(255)
);
create index idx_genre_type on t_sys_genre(genre_type);

create table t_genre_work(
	work_id		char(4),
	genre_id	char(4)
);
create index idx_work_search on t_genre_work(work_id);

 ALTER TABLE t_sys_genre Change genre_type genre VARCHAR(255)
 ALTER TABLE t_sys_genre Change genre_info info VARCHAR(255)
create table t_sys_genre(
	id			char(4) primary key,
	genre		varchar(16),
	info		varchar(255)
);


 Alter table t_sys_remocon
   ADD COLUMN key_immedi TINYINT NOT null DEFAULT 0

create table t_sys_remocon(
	remocon_name	varchar(32) not null,
	key_level		tinyint not null,
	key_name		varchar(16) not null,
	key_use			varchar(8) not null,
	key_click_cnt	tinyint default 1,
	key_auth		VARCHAR(64),
	key_immedi 		TINYINT NOT null DEFAULT 0,
	primary key(remocon_name, key_level)
);

 ALTER TABLE t_tool drop parent_id, drop parent_type
 Alter table t_tool add column series_id char(4) after id
 Alter table t_tool add column writer_id char(4) after series_id
create table t_tool(
	id				VARCHAR(255) primary key,
	series_id		char(4),
	writer_id		char(4),
	h_tier			 tinyint DEFAULT 0,
	x_tool_size		int,
	y_tool_size		int,
	name			varchar(255),
	reg_dt			timestamp default current_timestamp(),
	upt_dt			timestamp default current_timestamp() on update current_timestamp()
);

-- 2023-11-13 -----------------------------

create table t_read(
	id			char(8) primary key, /* t_squence 두 개를 이용 */
	reader_id	char(4),
	readee_id	varchar(8),
	time		timestamp default current_timestamp()
);

-- 1. id가 필요한가? 2. 아예 엘라스틱 서치에다 넣는 게 낫지 않나?
-- 1. 필요 없어보임 2. 이게 수입이랑 엮인다고 생각하면 무결성이 엄청 중요해져서 DB가 나은 듯

 alter table t_read drop column id
create table t_read(
	reader_id	char(4),
	readee_id	varchar(8),
	time		timestamp default current_timestamp()
);





