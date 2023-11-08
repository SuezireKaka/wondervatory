package www.wonder.vatory.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.work.model.GenreVO;

@Mapper		
public interface GenreMapper {
	List<GenreVO> listAllGenre();
	List<GenreVO> listAllGenreOfSeries(@Param("seriesId") String seriesId);
}
