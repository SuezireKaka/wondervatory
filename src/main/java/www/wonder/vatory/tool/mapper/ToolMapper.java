package www.wonder.vatory.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.tool.model.CustomEntityVO;
import www.wonder.vatory.tool.model.CustomObjectVO;
import www.wonder.vatory.tool.model.CustomPropertyDTO;
import www.wonder.vatory.tool.model.CustomPropertyVO;
import www.wonder.vatory.tool.model.CustomRelationVO;
import www.wonder.vatory.tool.model.ToolVO;

@Mapper
public interface ToolMapper {
	public long getFoundRows();
	public List<ToolVO> listAllFromSeries(@Param("seriesId") String seriesId, @Param("paging") PagingDTO paging);
	public List<CustomEntityVO> listAllEntity(@Param("toolId") String toolId);
	public List<CustomRelationVO> listAllRelation(@Param("toolId") String toolId);
	public List<CustomPropertyVO> listPropertiesOf(@Param("objectId") String objectId);
	
	public int countPropertiesOf(@Param("objectId") String objectId);
	
	public ToolVO getToolById(@Param("toolId") String toolId);
	public ToolVO getToolByEntity(@Param("entityId") String entityId);
	
	public int insertToSync(@Param("objectId") String objectId, @Param("offset") int offset,
			@Param("insertList") List<CustomPropertyDTO> insertList);
	
	public int updateAllPropsFrom(@Param("objectId") String objectId,
			@Param("updateList") List<CustomPropertyDTO> updateList);
	
	public int deleteToSync(@Param("objectId") String objectId,
			@Param("border") int border);
	
}
