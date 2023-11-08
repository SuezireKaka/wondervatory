package www.wonder.vatory.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.report.model.ReportCodeVO;
import www.wonder.vatory.report.model.ReportVO;
import www.wonder.vatory.work.model.GenreVO;

@Mapper		
public interface GenreMapper extends WonderMapper {
	public List<GenreVO> listAllGenre();
	public List<GenreVO> listAllGenres(@Param("paging") PagingDTO paging);
	public List<GenreVO> listAllGenreOfSeries(@Param("seriesId") String seriesId);

	public int countGenreTypesOf(String id);

	public int createGenre(GenreVO genre);
	public int updateGenre(@Param("report") GenreVO genre);
	
	public int insertToSync(@Param("reportId") String id, @Param("offset") int offset,
			@Param("insertList") List<GenreVO> insertList);
	public int updateAllGenreFrom(@Param("reportId") String id,
			@Param("updateList") List<GenreVO> updateList);
	public int deleteToSync(@Param("reportId") String id,
			@Param("border") int border);
}
