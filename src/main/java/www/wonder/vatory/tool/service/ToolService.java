package www.wonder.vatory.tool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.tool.mapper.ToolMapper;
import www.wonder.vatory.tool.model.CustomObjectVO;
import www.wonder.vatory.tool.model.CustomPropertyDTO;
import www.wonder.vatory.tool.model.ToolVO;

@Service
public class ToolService {
	@Autowired(required = false)
	private ToolMapper toolMapper;

	/** 시리즈의 모든 툴 조회 */ 
	public DreamPair<List<ToolVO>, PagingDTO> listAllFromSeries(String seriesId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<ToolVO> listResult = toolMapper.listAllFromSeries(seriesId, paging);
		long dataCount = toolMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<ToolVO>, PagingDTO>(listResult, paging);
	}
	
	public List<CustomObjectVO> listPropertiesOf(String objectId) {
		return toolMapper.listPropertiesOf(objectId);
	}

	public ToolVO getToolById(String toolId) {
		ToolVO result = toolMapper.getToolById(toolId);
		result.getCustomEntityList().addAll(toolMapper.listAllEntity(toolId));
		result.getCustomRelationList().addAll(toolMapper.listAllRelation(toolId));
		return result;
	}

	public ToolVO getToolByEntity(String entityId) {
		ToolVO result = toolMapper.getToolByEntity(entityId);
		String toolId = result.getId();
		result.getCustomEntityList().addAll(toolMapper.listAllEntity(toolId));
		result.getCustomRelationList().addAll(toolMapper.listAllRelation(toolId));
		return result;
	}

	public int syncPropertiesOf(String objectId, List<CustomPropertyDTO> requestList) {
		int result = 0;
		int requestCount = requestList.size();
		// 현재 들어있는 개수랑 비교해서 판단
		int prevCount = toolMapper.countPropertiesOf(objectId);
		
		if (requestCount > prevCount) {
			// 늘어났으면 prevCount만큼 리스트를 분할해서 업데이트 이후 추가
			int border = prevCount;
			
		    List<List<CustomPropertyDTO>> listOfLists = new ArrayList<>(
		    	requestList.stream()
		    	.collect(Collectors.groupingBy(s -> requestList.indexOf(s) >= border))
		    	.values()
		    );
		    
		    List<CustomPropertyDTO> updateList = listOfLists.get(0);
		    List<CustomPropertyDTO> insertList = listOfLists.get(1);
		    
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
