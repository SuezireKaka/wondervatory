
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
	tgt_id		char(4),
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
	alive		tinyint,
	reg_dt		timestamp default current_timestamp(),
	upt_dt		timestamp default current_timestamp() on update current_timestamp()
);
create index idx_login_id on t_account(login_id);

-- work --
create table t_work(
	id				varchar(255) primary key comment 'series, post, reply 순서',
	writer_id		char(4),
	bb_id			char(4),
	descrim			varchar(16),
	h_level			tinyint,
	title			varchar(255),
	content			text(10000),
	read_cnt		int,
	like_cnt		int,
	dis_cnt			int,
	complete		tinyint,
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
	descrip 	varchar(255)

);

insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '공지사항', '회사공지');
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '자유게시판', '자유게시판');
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '시리즈', '시리즈');
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), 'Q&A', '질문게시판');
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '쇼케이스', '쇼케이스');
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '댓글', '댓글대댓글');


insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("0000", "0001", "0002", "Series", 0, "타이틀" ,"컨텐츠", 10, 5, 100, 1, "유료화", 1);



insert into t_party(id, descrim, name, birth)
	values("0000", "Organization", "회사11", "800110");
insert into t_party(id, descrim, name, birth, sex)
	values("0001", "Person", "홍길동", "900110", "남성");

insert into t_account(id, login_id, pass_word, owner_id, response_id, nick, intro, alive)
	values("0001","aaaa","aaaa","0000","0001","홍", "abc","1" );

insert into t_tool(id, x_tool_size, y_tool_size, name, parent_id, parent_type)
	values("0000", 1024, 768, "처음툴", "0000", "Series");

insert into t_custom_obj(id, descrim, x_pos, y_pos, x_size, y_size, name,
				inner_color, outer_color, parent_id)
	values("0000", "Entity", 0, 0, 100, 200, "Restful City",
				"#123456", "#fedcba", "0000");

insert into t_custom_obj(id, descrim, x_pos, y_pos, x_size, y_size, name,
				inner_color, outer_color, parent_id)
	values("0001", "Entity", 300, 0, 100, 200, "Java City",
				"#123456", "#fedcba", "0000");

insert into t_custom_obj(id, descrim, x_pos, y_pos, x_size, y_size, name,
				inner_color, outer_color, parent_id, one_id, other_id)
	values("0002", "Relation", 300, 300, 50, 50, "Long War",
				"#000000", "#aa7700", "0000", "0000", "0001");


	select SQL_CALC_FOUND_ROWS w.*, p.id p_id, p.descrim p_descrim, p.name w_name, p.birth p_birth, p.sex p_sex, a.id a_id, a.login_id a_login_id, a.response_id a_response_id, a.nick a_nick, a.intro a_intro, a.alive a_alive, a.reg_dt a_reg_dt, a.upt_dt a_upt_dt
	  from T_work w LEFT OUTER JOIN t_party p
	       ON w.writer_id = p.id
		LEFT OUTER JOIN t_account a
	      on p.id = a.owner_id
	where w.bb_id = "0001"
	 order by w.id DESC
	 limit 1 offset 0;



insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000000", "0001", "0001", "Post", 0, "하위글" ,"컨텐츠", 10, 5, 100, 1, "유료화", 1);


insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000001", "0001", "0001", "Post", 0, "하위글" ,"컨텐츠", 10, 5, 100, 1, "유료화", 1);


insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("0001", "0001", "0002", "Post", 0, "상위글" ,"컨텐츠", 10, 5, 100, 1, "유료화", 1);
	
	
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("000000010000", "0001", "0001", "Post", 0, "댓글" ,"컨텐츠", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("0000000100000000", "0001", "0001", "Post", 0, "대댓글" ,"컨텐츠", 10, 5, 100, 1, "유료화", 1);

insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("0002", "0001", "0002", "Series", 0, "야호" ,"신난다", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("0003", "0001", "0002", "Series", 0, "이건" ,"진짜야", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("0004", "0001", "0002", "Series", 0, "몰라" ,"누가 뭐래?", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("0005", "0001", "0002", "Series", 0, "그럼" ,"나중에 보자", 10, 5, 100, 1, "유료화", 1);
	
insert into T_TAG(id, word)
	values("0000", "타이틀");

insert into T_TGT_TAG(tgt_name, tgt_id, tag_id, tf)
	values("T_work", "0000", "0000", 1);
	
insert into t_tool(id, x_tool_size, y_tool_size, name, parent_id, parent_type)
	values("0001", 1024, 768, "자바시티 map", "0001", "Entity");
	
insert into t_custom_obj(id, descrim, x_pos, y_pos, x_size, y_size, name,
				inner_color, outer_color, parent_id)
	values("0003", "Entity", 100, 300, 100, 200, "Class Area",
				"#123456", "#fedcba", "0001");

insert into t_custom_obj(id, descrim, x_pos, y_pos, x_size, y_size, name,
				inner_color, outer_color, parent_id)
	values("0004", "Entity", 600, 400, 100, 200, "Object Area",
				"#123456", "#fedcba", "0001");

insert into t_custom_obj(id, descrim, x_pos, y_pos, x_size, y_size, name,
				inner_color, outer_color, parent_id, one_id, other_id)
	values("0005", "Relation", 600, 120, 50, 80, "Instance Road",
				"#000000", "#aa7700", "0001", "0003", "0004");

	
