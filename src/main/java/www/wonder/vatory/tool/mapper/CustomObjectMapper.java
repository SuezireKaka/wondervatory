package www.wonder.vatory.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import www.wonder.vatory.framework.mapper.WonderMapper;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.tool.model.CustomEntityVO;
import www.wonder.vatory.tool.model.CustomObjectVO;
import www.wonder.vatory.tool.model.CustomPropertyVO;
import www.wonder.vatory.tool.model.CustomRelationVO;
import www.wonder.vatory.tool.model.ToolVO;

@Mapper
public interface CustomObjectMapper extends WonderMapper {
	
	public List<CustomObjectVO> listAllObject(@Param("toolId") String toolId);
	public List<CustomEntityVO> listAllEntity(@Param("toolId") String toolId);
	public List<CustomRelationVO> listAllRelation(@Param("toolId") String toolId);
	
	public List<CustomPropertyVO> listPropertiesOf(@Param("objectId") String objectId);
	
	public int countPropertiesOf(@Param("objectId") String objectId);
	
	public int insertObjectsToSync(@Param("objectId") String objectId, @Param("type") String type,
			@Param("insertList") List<CustomObjectVO> insertList);
	
	public int insertPropsToSync(@Param("objectId") String objectId, @Param("offset") int offset,
			@Param("insertList") List<CustomPropertyVO> insertList);
	
	public int updateAllPropsFrom(@Param("objectId") String objectId,
			@Param("updateList") List<CustomPropertyVO> updateList);
	
	public int deletePropsToSync(@Param("objectId") String objectId,
			@Param("border") int border);
	
}
