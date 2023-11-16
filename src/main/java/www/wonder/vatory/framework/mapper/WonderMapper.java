package www.wonder.vatory.framework.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface WonderMapper {
	@Select("SELECT FOUND_ROWS()")
	public long getFoundRows();
	
	@Select("SELECT NEXT_MULTI_PK(#{tName}, ${summonCnt})")
	public String getNextMultiIdConcat(
			@Param("tName") String tName, @Param("summonCnt") int summonCnt);
}
