-- 1. 한 컬럼에 있는 스트링류를 콤마로 이어서 보여주는 쿼리

SELECT group_concat(col SEPARATOR ', ')
  FROM t_example

-- 1-1. 응용으로 한 테이블에 있는 모든 컬럼들을 이어서 보여주기 

SELECT group_concat(COLUMN_NAME SEPARATOR ', ')
  FROM INFORMATION_SCHEMA.COLUMNS
 WHERE TABLE_SCHEMA = 'wondervatory'
   AND TABLE_NAME   = 't_tool'
   
   
   
-- 2. 한 번에 여러 NEXT_PK() 값을 얻고 그 결과를 리턴해보자  // MariaDB가 지원 안 해준다는 듯......
DELIMITER $$
CREATE OR REPLACE FUNCTION NEXT_MULTI_PK(t_NAME VARCHAR(255), summon_cnt tinyint) RETURNS char(4)
BEGIN
	DECLARE unrecorded boolean;	--관리 되고 있는 것인가? 아니면 새로운 것인가? 
	DECLARE old_seed int;	--기존 값 넣어둘 곳
	DECLARE r_sequence char(4);

	select 0 into old_seed; -- old_seed 초기화

	select not exists(select num from t_sequence where NAME = t_NAME) into unrecorded;

	if (unrecorded) then
		-- 새로운 것이면 새롭게 넣자
		INSERT INTO t_sequence(NAME) VALUES (t_NAME);
	else
		-- 아니면 기존 값을 old_seed에 기억해두자
		select num into old_seed from t_sequence where NAME = t_NAME;
 	end if;

	UPDATE t_sequence SET NUM = NUM + summon_cnt  WHERE NAME = t_NAME;

	-- old_seed 가 0이고 count가 3이면 1, 2, 3
	SELECT group_concat(c.SEED SEPARATOR ', ') INTO r_sequence -- 여기서 하나로 만들기
	  FROM t_sequence s, T_IDSEED c
	 WHERE s.NAME = t_NAME
	   AND c.seq > old_seed AND c.seq <= old_seed + summon_cnt;
	
	RETURN r_sequence;
END;
$$
DELIMITER ;

SELECT NEXT_MULTI_PK('S_test', 3);