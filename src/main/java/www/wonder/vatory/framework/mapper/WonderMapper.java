package www.wonder.vatory.framework.mapper;

import org.apache.ibatis.annotations.Select;

public interface WonderMapper {
	@Select("SELECT FOUND_ROWS()")
	public long getFoundRows();
}
