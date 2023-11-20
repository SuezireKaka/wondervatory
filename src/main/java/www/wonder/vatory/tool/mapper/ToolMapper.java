package www.wonder.vatory.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.tool.model.ToolVO;

@Mapper
public interface ToolMapper extends WonderMapper {
	
	/*
	public List<ToolVO> listAllFromSeries(
			@Param("seriesId") String seriesId,
			@Param("paging") PagingDTO paging);
	-- deprecated : 아래 매소드로 전부 통합*/
	
	public List<ToolVO> listAllNextTools(@Param("seriesId") String seriesId,
			@Param("idPath") String idPath, @Param("paging") PagingDTO paging);
	
	public ToolVO getToolById(@Param("toolId") String toolId);
	
	public int createToolSkin(@Param("writer") AccountVO writer,
			@Param("toolSkin") ToolVO toolSkin, @Param("seriesId") String seriesId);
	
	public int updateToolSkin(@Param("toolSkin") ToolVO toolSkin);

	public int deleteTool(String id);
	
}
