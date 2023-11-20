package www.wonder.vatory.framework.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface WonderMapper {
	@Select("SELECT FOUND_ROWS()")
	public long getFoundRows();
	
	@Select("SELECT NEXT_MULTI_PK(#{tName}, ${summonCnt})")
	public String getNextMultiIdConcat(
			@Param("tName") String tName, @Param("summonCnt") int summonCnt);
	
	@Select("SELECT count(*)"
			+ " from t_alive"
			+ " where target_id = #{id}"
			+ " and target_table = #{target_table}"
			+ " and alive = 1")
	public boolean isAlive(
			@Param("id") String id, @Param("target_table") String target_table);
}
