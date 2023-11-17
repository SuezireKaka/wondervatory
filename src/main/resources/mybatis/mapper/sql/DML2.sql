
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '공지사항', '회사공지');
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '자유게시판', '자유게시판');
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '웹소설', '웹소설 게시판');
insert into T_bb(id, name, descrip)
values(NEXT_PK('S_bb'), '웹툰', '웹툰 게시판');

insert into t_sys_remocon(remocon_name, key_level, key_name, key_info, key_auth, key_immedi )
	values ("explorer_remocon", 0, "선택", "클릭한 대상을 선택합니다.","login")
		  ,("explorer_remocon", 1, "생성", "현재 위치에 새로운 대상을 생성합니다.","self",1)
	      ,("explorer_remocon", 2, "수정", "선택한 대상의 정보를 수정합니다.","self")
	      ,("explorer_remocon", 3, "삭제", "선택한 대상을 삭제합니다.","self or manage")

insert into t_sys_remocon(remocon_name, key_level, key_name, key_info, key_auth, key_immedi)
	values ("relation_remocon", 0, "선택", "커스텀 오브젝트를 한 번 눌러서 선택합니다.","login", 0)
		, ("relation_remocon", 1, "객체 추가", "캔버스를 한 번 눌러서 새로운 커스텀 객체를 소환합니다.","self", 0)
		, ("relation_remocon", 2, "관계 추가", "두 커스텀 오브젝트(같아도 허용)를 눌러서 새로운 커스텀 관계를 소환합니다.","self", 0)
		, ("relation_remocon", 3, "제거", "커스텀 객체를 눌러서 삭제합니다.이때 해당 객체와 직간접적으로 연결된 모든 커스텀 관계도 삭제됩니다.","self or manage", 0)
		, ("relation_remocon", 4, "복사", "화면에 있는 툴 디테일 전체를 복사합니다.","login", 1)
		, ("relation_remocon", 5, "붙여넣기", "복사된 툴 디테일을 현재 화면에 추가합니다.","self", 1)
		
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













