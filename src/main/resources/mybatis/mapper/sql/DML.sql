
insert into t_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '공지사항', '회사공지');
insert into t_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '자유게시판', '자유게시판');
insert into t_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '웹소설', '웹소설 게시판');
insert into t_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '웹툰', '웹툰 게시판');



insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("0000", "0001", "0002", "Series", 0, "타이틀" ,"컨텐츠", 10, 5, 100, 1, "유료화", 1);



insert into t_party(id, descrim, name, birth)
	values("0000", "Organization", "회사11", "800101");
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
	  from t_work w LEFT OUTER JOIN t_party p
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

	
--231012  00 -----------
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000002", "0001", "0001", "Post", 0, "하위글2" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000003", "0001", "0001", "Post", 0, "하위글3" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000004", "0001", "0001", "Post", 0, "하위글4" ,"내용", 10, 5, 100, 1, "유료화", 1);
	
	
	
	
--231012  01 -----------
insert into t_tool(id, x_tool_size, y_tool_size, name, parent_id, parent_type)
	values("0002", 1024, 768, "트레이스 관계도", "0000", "Series");
	
--231012  02 -----------
	
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000005", "0001", "0001", "Post", 0, "하위글5" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000006", "0001", "0001", "Post", 0, "하위글6" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000007", "0001", "0001", "Post", 0, "하위글7" ,"내용", 10, 5, 100, 1, "유료화", 1);	
	insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000008", "0001", "0001", "Post", 0, "하위글8" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000009", "0001", "0001", "Post", 0, "하위글9" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000010", "0001", "0001", "Post", 0, "하위글10" ,"내용", 10, 5, 100, 1, "유료화", 1);	
	insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000011", "0001", "0001", "Post", 0, "하위글11" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000012", "0001", "0001", "Post", 0, "하위글12" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000013", "0001", "0001", "Post", 0, "하위글13" ,"내용", 10, 5, 100, 1, "유료화", 1);		
	insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000014", "0001", "0001", "Post", 0, "하위글14" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000015", "0001", "0001", "Post", 0, "하위글15" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00000016", "0001", "0001", "Post", 0, "하위글16" ,"내용", 10, 5, 100, 1, "유료화", 1);


insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010000", "0001", "0001", "Post", 0, "하위글0" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010001", "0001", "0001", "Post", 0, "하위글1" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010002", "0001", "0001", "Post", 0, "하위글2" ,"내용", 10, 5, 100, 1, "유료화", 1);	
	insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010003", "0001", "0001", "Post", 0, "하위글3" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010004", "0001", "0001", "Post", 0, "하위글4" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010005", "0001", "0001", "Post", 0, "하위글5" ,"내용", 10, 5, 100, 1, "유료화", 1);	
	insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010006", "0001", "0001", "Post", 0, "하위글6" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010007", "0001", "0001", "Post", 0, "하위글7" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010008", "0001", "0001", "Post", 0, "하위글8" ,"내용", 10, 5, 100, 1, "유료화", 1);		
	insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010009", "0001", "0001", "Post", 0, "하위글9" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010010", "0001", "0001", "Post", 0, "하위글10" ,"내용", 10, 5, 100, 1, "유료화", 1);
insert into t_work(id, writer_id, bb_id, descrim, h_level,title, content, like_cnt, dis_cnt, read_cnt, complete, series_status, free)
	values("00010011", "0001", "0001", "Post", 0, "하위글11" ,"내용", 10, 5, 100, 1, "유료화", 1);


insert into t_role(role, account_id)
	values('reader', '0001');
	

insert into T_CODE(Code_type, code_val) values('역할', 'admin');
insert into T_CODE(Code_type, code_val) values('역할', 'reader');
insert into T_CODE(Code_type, code_val) values('역할', 'writer');

insert into T_CODE(Code_type, code_val) values('contect point type', 'phone number');
insert into T_CODE(Code_type, code_val) values('contect point type', 'home address');
insert into T_CODE(Code_type, code_val, validation_re) values('contect point type', 'email', '[a-z0-9]+@[a-z]+\.[a-z]{2,3}');

insert into T_CODE(Code_type, code_val) values('rel target tag', 'post');
insert into T_CODE(Code_type, code_val) values('rel target tag', 'party');

----10 13 01-----------
insert into t_contact_point(owner_id, cp_type, cp_val) values('0001','phone number','010-1234-5678');
insert into t_contact_point(owner_id, cp_type, cp_val) values('0001','home address','서울시 동작구 구로동');
insert into t_contact_point(owner_id, cp_type, cp_val) values('0001','email','aaaa@naver.com');


----10 13 02-----------
insert into t_work(id, writer_id, bb_id, descrim, h_level, content, like_cnt, dis_cnt)
	values("000000160000", "0001", "0002", "Reply", 3,"댓글0", 0, 0);
insert into t_work(id, writer_id, bb_id, descrim, h_level, content, like_cnt, dis_cnt)
	values("000000160001", "0001", "0002", "Reply", 3,"댓글1", 0, 0);
insert into t_work(id, writer_id, bb_id, descrim, h_level, content, like_cnt, dis_cnt)
	values("000000160002", "0001", "0002", "Reply", 3,"댓글2", 0, 0);

insert into t_work(id, writer_id, bb_id, descrim, h_level, content, like_cnt, dis_cnt)
	values("0000001600000000", "0001", "0002", "Reply", 4,"대댓글0", 0, 0);
insert into t_work(id, writer_id, bb_id, descrim, h_level, content, like_cnt, dis_cnt)
	values("0000001600000001", "0001", "0002", "Reply", 4,"대댓글1", 0, 0);


----10 17 02------------

insert into T_custom_property(owner_id, prop_type, prop_val)
	values("0000", 0, "위치", "API");
insert into T_custom_property(owner_id, prop_type, prop_val)
	values("0000", 1, "제조일자", "2023-05-06");
insert into T_custom_property(owner_id, prop_type, prop_val)
	values("0000", 2, "능력", "신체 압축");
insert into T_custom_property(owner_id, prop_type, prop_val)
	values("0001", 0, "위치", "오라클");
insert into T_custom_property(owner_id, prop_type, prop_val)
	values("0001", 1, "제습기", "???");
insert into T_custom_property(owner_id, prop_type, prop_val)
	values("0002", 0, "시작년도", "BC 127");

 
 -----10 23 ------------------------------------
 
insert into t_sys_role(role, level, descrip)
	values("Reader", 0, "계정의 가장 기본적인 역할이며, 모든 계정들은 기본적으로 해당 역할을 가지고 있습니다.");
insert into t_sys_role(role, level, descrip)
	values("Writer", 1, "일정 퀄리티 이상의 소설을 만들었을 때 관리자에 의해 활성화됩니다.");
insert into t_sys_role(role, level, descrip)
	values("Manager", 2, "게시글과 유저 계정을 관리할 수 있는 강력한 권한입니다.");
insert into t_sys_role(role, level, descrip)
	values("Ceo", 3, "매니저를 관리할 수 있다는 전설 속의 권한입니다.");

insert into t_sys_rptype(level, rpt_type, rpt_info)
values (0, "지나친 선정성", "대충 설명 1");
insert into t_sys_rptype(level, rpt_type, rpt_info)
values (1, "지나친 폭력성", "대충 설명 2");
insert into t_sys_rptype(level, rpt_type, rpt_info)
values (2, "명예훼손", "대충 설명 3");
insert into t_sys_rptype(level, rpt_type, rpt_info)
values (3, "아동학대", "대충 설명 4");
insert into t_sys_rptype(level, rpt_type, rpt_info)
values (4, "테러조장", "대충 설명 5");
insert into t_sys_rptype(level, rpt_type, rpt_info)
values (5, "허위광고", "대충 설명 6");
insert into t_sys_rptype(level, rpt_type, rpt_info)
values (6, "저작권 침해", "대충 설명 7");


------10 25 --------------------------------



insert into t_report(id, reporter_id, suspect_id, suspect_table, cause)
	values("0000", "0003", "0008", "t_work", "뭔가 이상해요 암튼 이상함");

insert into t_report_cls(rpt_id, rpt_type)
	values("0000", 0, "지나친 선정성");
insert into t_report_cls(rpt_id, rpt_type)
	values("0000", 1, "지나친 폭력성");
insert into t_report_cls(rpt_id, rpt_type)
	values("0000", 2, "아동학대");

insert into t_report(id, reporter_id, suspect_id, suspect_table, cause)
	values("0001", "000c", "0003", "t_account", "님이 더 이상해요 이 사람아");
	
insert into t_report_cls(rpt_id, rpt_type)
	values("0001", 0, "명예훼손");
	
insert into t_report(id, reporter_id, suspect_id, suspect_table, cause)
	values("0002", "0003", "000c", "t_account", "누구세요");
	
insert into t_report_cls(rpt_id, rpt_type)
	values("0002", 0, "명예훼손");
	
insert into t_report(id, reporter_id, suspect_id, suspect_table, cause)
	values("0003", "0003", "001d000m000g000h", "t_work", "이거 유사관리자 행위죠?");
	
insert into t_report_cls(rpt_id, rpt_type)
	values("0003", 0, "허위광고");
	
insert into t_sequence(NAME, num)
VALUES ('s_report', 4)


-----  10 31 --------------------

insert into t_sys_remocon(remocon_name, key_level, key_name, key_use, key_click_cnt)
	values ("relation_remocon", 0, "선택", "button", 1)
		, ("relation_remocon", 1, "객체 추가", "canvas", 1)
		, ("relation_remocon", 2, "관계 추가", "button", 2)
		, ("relation_remocon", 3, "제거", "button", 1)
		, ("relation_remocon", 4, "복사", "button", 1)
		, ("relation_remocon", 5, "붙여넣기", "canvas", 1)
		
	
 UPDATE t_sys_remocon sr
 	JOIN (
	 	SELECT 0 LEVEL, '커스텀 오브젝트를 한 번 눌러서 선택합니다.' info
	 	UNION All
	 	SELECT 1 , '캔버스를 한 번 눌러서 새로운 커스텀 객체를 소환합니다.'
	 	UNION All
	 	SELECT 2, '두 커스텀 엔티티(같아도 허용)를 눌러서 새로운 커스텀 관계를 소환합니다.'
	 	UNION All
	 	SELECT 3, '커스텀 객체를 눌러서 삭제합니다.\n이때 해당 객체와 직간접적으로 연결된 모든 커스텀 관계도 삭제됩니다.'
	 	UNION All
	 	SELECT 4, '기본적으로 화면에 있는 툴 디테일 전체를 복사합니다. 선택된 객체가 있으면 그것만 복사합니다.'
	 	UNION All
	 	SELECT 5, '복사된 툴 디테일을 현재 화면에 추가합니다.'
	 ) sp ON sr.key_level = sp.LEVEL
   SET sr.key_info = sp.info
 WHERE sr.remocon_name = 'relation_remocon';

---  11-07 -------

-- 서브툴 하나 추가
insert into t_tool(id, h_tier, x_tool_size, y_tool_size, NAME, parent_id, parent_type)
	VALUES("00010002", 1, 500, 500, "class 내부도", "0003", "Tool");

---  11-08 -------

insert into t_sys_remocon(remocon_name, key_level, key_name, key_info)
	values ("explorer_remocon", 0, "선택", "클릭한 대상을 선택합니다.")
		  ,("explorer_remocon", 1, "생성", "현재 위치에 새로운 대상을 생성합니다.")
	      ,("explorer_remocon", 2, "수정", "선택한 대상의 정보를 수정합니다.")
	      ,("explorer_remocon", 3, "삭제", "선택한 대상을 삭제합니다.")
	      
UPDATE t_sys_remocon sr
 	JOIN (
	 	SELECT 0 LEVEL, "login" auth
	 	UNION All
	 	SELECT 1 , "self"
	 	UNION All
	 	SELECT 2, "self"
	 	UNION All
	 	SELECT 3, "self or manage"
	 	UNION All
	 	SELECT 4, "login"
	 	UNION All
	 	SELECT 5, "self"
	 ) sp ON sr.key_level = sp.LEVEL
   SET sr.key_auth = sp.auth
 WHERE sr.remocon_name = 'relation_remocon';
 
UPDATE t_sys_remocon sr
 	JOIN (
	 	SELECT 0 LEVEL, "login" code
	 	UNION All
	 	SELECT 1 , "self"
	 	UNION All
	 	SELECT 2, "self"
	 	UNION All
	 	SELECT 3, "self or manage"
	 ) sp ON sr.key_level = sp.LEVEL
   SET sr.key_auth = sp.auth
 WHERE sr.remocon_name = 'explorer_remocon';
		
		
-- --
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "기타", "대충 설명 0");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "판타지", "대충 설명 1");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "액션", "대충 설명 2");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "일상", "대충 설명 3");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "스릴러", "대충 설명 4");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "개그", "대충 설명 5");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "드라마", "대충 설명 6");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "스포츠", "대충 설명 7");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "현대판타지", "대충 설명 8");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "퓨전", "대충 설명 9");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "무협", "대충 설명 10");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "대체역사", "대충 설명 11");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "로맨스", "대충 설명 12");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "게임", "대충 설명 13");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "힐링", "대충 설명 14");
insert into t_sys_genre(id, genre, info)
values(NEXT_PK('s_genre'), "아포칼립스", "대충 설명 15");

-- 2023-11-13 ------------------

-- t_read.id 생성 쿼리
SELECT concat(ids_q.SEED, ids_r.SEED) id
  FROM t_idseed ids_q, t_idseed ids_r,
      /* 다음 수의 몫과 나머지를 구하기 */
  		(select FLOOR(next_num.val / total_num.val) + 1 front,
  					next_num.val % total_num.val + 1 back
		   FROM
		   	(SELECT ifnull(COUNT(id), 0) + 1 val
  					FROM t_read rid) next_num,
  				(SELECT COUNT(SEQ) val
  					FROM t_idseed) total_num
		) countid
 WHERE ids_q.SEQ = countid.front
   AND ids_r.SEQ = countid.back
   
-- 근데 생각해보니까 t_read에 id가 왜 필요하지???



-- 시간 명시
INSERT into t_read(reader_id, readee_id, TIME)
VALUES ('0002', '0006', '2023-10-03 12:12:12'),
('0002', '0006000f', '2023-10-03 12:12:46'),
('0003', '0006', '2023-10-03 14:09:33'),
('0006', '0006', '2023-10-04 07:27:33'),
('0006', '001k', '2023-10-04 18:01:25'),
('000g', '001k', '2023-10-04 19:16:51')

-- 시간 없음
INSERT into t_read(reader_id, readee_id)
VALUES ('000c', '003x')


-- 백업용
SELECT GROUP_CONCAT(tuple.val SEPARATOR ', ')
	FROM (SELECT CONCAT('(\'', reader_id, '\',\'', readee_id, '\',\'', TIME, '\')') val
	  FROM t_read) tuple
	  
	  
INSERT into t_read(reader_id, readee_id, TIME)
VALUES
('0002','0006','2023-10-03 12:12:12'), ('0003','0006','2023-10-03 14:09:33'), ('0006','0006','2023-10-04 07:27:33'), ('0006','001k','2023-10-04 18:01:25'), ('000g','001k','2023-10-04 19:16:51'), ('000c','003x','2023-11-16 18:19:01'), ('0001','0006','2023-10-01 12:12:12'), ('0001','001k','2023-10-30 19:16:51'), ('0001','003z','2023-10-15 14:09:33'), ('0001','0041','2023-10-20 07:27:33'), ('0001','0042','2023-10-25 18:01:25'), ('0001','0043','2023-10-30 19:16:51'), ('0001','0044','2023-11-03 12:12:12'), ('0001','0045','2023-10-11 12:12:46'), ('0001','0046','2023-11-16 14:09:33'), ('0001','0047','2023-11-21 07:27:33'), ('0001','0048','2023-10-04 12:12:12'), ('0001','0049','2023-10-11 12:12:46'), ('0001','004a','2023-10-16 14:09:33'), ('0001','004b','2023-10-21 07:27:33'), ('0001','004c','2023-10-26 18:01:25'), ('0001','004d','2023-10-30 19:16:51'), ('0001','004e','2023-11-13 12:12:12'), ('0001','004f','2023-10-11 12:12:46'), ('0001','004g','2023-11-16 14:09:33'), ('0002','0035','2023-10-02 12:12:12'), ('0002','0034','2023-10-12 12:12:46'), ('0002','0037','2023-10-17 14:09:33'), ('0002','0041','2023-10-22 07:27:33'), ('0002','0045','2023-10-27 18:01:25'), ('0002','0049','2023-10-12 19:16:51'), ('0002','0050','2023-11-02 12:12:12'), ('0002','005a','2023-10-12 12:12:46'), ('0002','0058','2023-11-17 14:09:33'), ('0002','005f','2023-11-27 07:27:33'), ('0003','005h','2023-10-03 12:12:12'), ('0003','0006','2023-10-20 07:27:33'), ('0003','001k','2023-10-25 18:01:25'), ('0003','005i','2023-10-30 19:16:51'), ('001x','0006','2023-10-01 12:12:12'), ('000t','003f','2023-10-04 12:12:46'), ('000t','003e','2023-10-09 14:09:33'), ('000t','003a','2023-10-14 07:27:33'), ('000t','003x','2023-10-19 07:27:33'), ('000t','0041','2023-10-29 18:01:25'), ('000t','004s','2023-10-24 19:16:51'), ('000t','004v','2023-10-09 14:09:33'), ('000t','004z','2023-10-14 07:27:33'), ('000t','005c','2023-10-19 07:27:33'), ('000t','005p','2023-10-29 18:01:25')
('0002','0006','2023-10-03 12:12:12'), ('0003','0006','2023-10-03 14:09:33'), ('0006','0006','2023-10-04 07:27:33'), ('0006','001k','2023-10-04 18:01:25'), ('000g','001k','2023-10-04 19:16:51'), ('000c','003x','2023-11-16 18:19:01'), ('0001','0006','2023-10-01 12:12:12'), ('0001','001k','2023-10-30 19:16:51'), ('0001','003z','2023-10-15 14:09:33'), ('0001','0041','2023-10-20 07:27:33'), ('0001','0042','2023-10-25 18:01:25'), ('0001','0043','2023-10-30 19:16:51'), ('0001','0044','2023-11-03 12:12:12'), ('0001','0045','2023-10-11 12:12:46'), ('0001','0046','2023-11-16 14:09:33'), ('0001','0047','2023-11-21 07:27:33'), ('0001','0048','2023-10-04 12:12:12'), ('0001','0049','2023-10-11 12:12:46'), ('0001','004a','2023-10-16 14:09:33'), ('0001','004b','2023-10-21 07:27:33'), ('0001','004c','2023-10-26 18:01:25'), ('0001','004d','2023-10-30 19:16:51'), ('0001','004e','2023-11-13 12:12:12'), ('0001','004f','2023-10-11 12:12:46'), ('0001','004g','2023-11-16 14:09:33'), ('0002','0035','2023-10-02 12:12:12'), ('0002','0034','2023-10-12 12:12:46'), ('0002','0037','2023-10-17 14:09:33'), ('0002','0041','2023-10-22 07:27:33'), ('0002','0045','2023-10-27 18:01:25'), ('0002','0049','2023-10-12 19:16:51'), ('0002','0050','2023-11-02 12:12:12'), ('0002','005a','2023-10-12 12:12:46'), ('0002','0058','2023-11-17 14:09:33'), ('0002','005f','2023-11-27 07:27:33'), ('0003','005h','2023-10-03 12:12:12'), ('0003','0006','2023-10-20 07:27:33'), ('0003','001k','2023-10-25 18:01:25'), ('0003','005i','2023-10-30 19:16:51'), ('001x','0006','2023-10-01 12:12:12'), ('000t','003f','2023-10-04 12:12:46'), ('000t','003e','2023-10-09 14:09:33'), ('000t','003a','2023-10-14 07:27:33'), ('000t','003x','2023-10-19 07:27:33'), ('000t','0041','2023-10-29 18:01:25'), ('000t','004s','2023-10-24 19:16:51'), ('000t','004v','2023-10-09 14:09:33'), ('000t','004z','2023-10-14 07:27:33'), ('000t','005c','2023-10-19 07:27:33'), ('000t','005p','2023-10-29 18:01:25'), ('0002','0006','2023-10-03 12:12:12'), ('0003','0006','2023-10-03 14:09:33'), ('0006','0006','2023-10-04 07:27:33'), ('0006','001k','2023-10-04 18:01:25'), ('000g','001k','2023-10-04 19:16:51'), ('000c','003x','2023-11-16 18:19:01'), ('0001','0006','2023-10-01 12:12:12'), ('0001','001k','2023-10-30 19:16:51'), ('0001','003z','2023-10-15 14:09:33'), ('0001','0041','2023-10-20 07:27:33'), ('0001','0042','2023-10-25 18:01:25'), ('0001','0043','2023-10-30 19:16:51'), ('0001','0044','2023-11-03 12:12:12'), ('0001','0045','2023-10-11 12:12:46'), ('0001','0046','2023-11-16 14:09:33'), ('0001','0047','2023-11-21 07:27:33'), ('0001','0048','2023-10-04 12:12:12'), ('0001','0049','2023-10-11 12:12:46'), ('0001','004a','2023-10-16 14:09:33'), ('0001','004b','2023-10-21 07:27:33'), ('0001','004c','2023-10-26 18:01:25'), ('0001','004d','2023-10-30 19:16:51'), ('0001','004e','2023-11-13 12:12:12'), ('0001','004f','2023-10-11 12:12:46'), ('0001','004g','2023-11-16 14:09:33'), ('0002','0035','2023-10-02 12:12:12'), ('0002','0034','2023-10-12 12:12:46'), ('0002','0037','2023-10-17 14:09:33'), ('0002','0041','2023-10-22 07:27:33'), ('0002','0045','2023-10-27 18:01:25'), ('0002','0049','2023-10-12 19:16:51'), ('0002','0050','2023-11-02 12:12:12'), ('0002','005a','2023-10-12 12:12:46'), ('0002','0058','2023-11-17 14:09:33'), ('0002','005f','2023-11-27 07:27:33'), ('0003','005h','2023-10-03 12:12:12'), ('0003','0006','2023-10-20 07:27:33'), ('0003','001k','2023-10-25 18:01:25'), ('0003','005i','2023-10-30 19:16:51'), ('001x','0006','2023-10-01 12:12:12'), ('000t','003f','2023-10-04 12:12:46'), ('000t','003e','2023-10-09 14:09:33'), ('000t','003a','2023-10-14 07:27:33'), ('000t','003x','2023-10-19 07:27:33'), ('000t','0041','2023-10-29 18:01:25'), ('000t','004s','2023-10-24 19:16:51'), ('000t','004v','2023-10-09 14:09:33'), ('000t','004z','2023-10-14 07:27:33'), ('000t','005c','2023-10-19 07:27:33'), ('000t','005p','2023-10-29 18:01:25')



