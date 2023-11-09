package www.wonder.vatory.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.work.model.GenreVO;
import www.wonder.vatory.work.model.SemiPostVO;

@Mapper		
public interface GenreMapper extends WonderMapper {
	public List<GenreVO> listAllGenre();
	public List<GenreVO> listAllGenreOfSeries(@Param("seriesId") String seriesId);

	public int insertToSync(@Param("workId") String genreId,
			@Param("insertList") List<GenreVO> insertList);
	public int deleteToSync(@Param("workId") String id,
			@Param("deleteList") List<GenreVO> deleteList);
}
