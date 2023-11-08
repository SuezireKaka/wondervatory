package www.wonder.vatory.tool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.tool.mapper.ToolMapper;
import www.wonder.vatory.tool.model.CustomEntityVO;
import www.wonder.vatory.tool.model.CustomPropertyVO;
import www.wonder.vatory.tool.model.CustomRelationVO;
import www.wonder.vatory.tool.model.ToolVO;
import www.wonder.vatory.work.model.SeriesVO;

@Service
public class ToolService {
	@Autowired(required = false)
	private ToolMapper toolMapper;

	/** 시리즈의 모든 툴 직접 붙어있는 조회 */
	public DreamPair<List<ToolVO>, PagingDTO> listAllFromSeries(String seriesId, int page) {
		PagingDTO paging = new PagingDTO(page);
		
		List<ToolVO> resultList = toolMapper.listAllFromSeries(seriesId, paging);
		
		long dataCount = toolMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<ToolVO>, PagingDTO>(resultList, paging);
	}
	
	public DreamPair<List<ToolVO>, PagingDTO> listAllNextTools(String idPath, int page) {
		PagingDTO paging = new PagingDTO(page);
		
		List<ToolVO> resultList = toolMapper.listAllNextTools(idPath, paging);
		
		long dataCount = toolMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<ToolVO>, PagingDTO>(resultList, paging);
	}
	
	/* public List<CustomPropertyVO> listPropertiesOf(String objectId) {
		return toolMapper.listPropertiesOf(objectId);
	}
	--deprecated : 프론트앤드 테스트 종료 */

	public ToolVO getToolById(String toolId) {
		ToolVO result = toolMapper.getToolById(toolId);
		List<CustomEntityVO> resEntityList = toolMapper.listAllEntity(toolId);
		resEntityList.stream()
			.forEach(entity -> {entity
				.getCustomPropertiesList()
				.addAll(toolMapper.listPropertiesOf(entity.getId()));
			}
		);
		result.getCustomEntityList().addAll(resEntityList);
		
		List<CustomRelationVO> resRelationList = toolMapper.listAllRelation(toolId);
		resRelationList.stream()
			.forEach(relation -> {relation
				.getCustomPropertiesList()
				.addAll(toolMapper.listPropertiesOf(relation.getId()));
			}
		);
		result.getCustomRelationList().addAll(resRelationList);
		return result;
	}
	
	public int mngToolSkin(DreamPair<SeriesVO, ToolVO> request) {
		String seriesId = request.getFirstVal().getId();
		ToolVO toolSkin = request.getSecondVal();
		int result = 1;
		// 툴스킨의 id가 없으면 새로 만들기 있으면 업데이트
		if (ObjectUtils.isEmpty(toolSkin.getId())) {
			result &= toolMapper.createToolSkin(toolSkin, seriesId);
		}
		else {
			result &= toolMapper.updateToolSkin(toolSkin);
		}
		return result;
	}

	public int syncPropertiesOf(String objectId, List<CustomPropertyVO> requestList) {
		int result = 0;
		int requestCount = requestList.size();
		// 현재 들어있는 개수랑 비교해서 판단
		int prevCount = toolMapper.countPropertiesOf(objectId);
		
		if (requestCount > prevCount) {
			// 늘어났으면 prevCount만큼 리스트를 분할해서 업데이트 이후 추가
			int border = prevCount;
			
		    List<List<CustomPropertyVO>> listOfLists = new ArrayList<>(
		    	requestList.stream()
		    	.collect(Collectors.groupingBy(s -> requestList.indexOf(s) >= border))
		    	.values()
		    );
		    
		    List<CustomPropertyVO> updateList = listOfLists.get(0);
		    List<CustomPropertyVO> insertList = listOfLists.get(1);
		    
		    result = toolMapper.updateAllPropsFrom(objectId, updateList)
		    	& toolMapper.insertToSync(objectId, updateList.size(), insertList);
		}
		else {
			result = toolMapper.deleteToSync(objectId, requestCount)
				& toolMapper.updateAllPropsFrom(objectId, requestList);
		}
		
		return result;
	}

}
