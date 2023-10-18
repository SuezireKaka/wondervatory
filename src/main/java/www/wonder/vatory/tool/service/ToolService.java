package www.wonder.vatory.tool.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.tool.mapper.ToolMapper;
import www.wonder.vatory.tool.model.CustomObjectVO;
import www.wonder.vatory.tool.model.CustomPropertyIncomeDTO;
import www.wonder.vatory.tool.model.PropertyRequestDTO;
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

	public int syncPropertiesOf(CustomPropertyIncomeDTO income) {
		String objectId = income.getObjectId();
		List<PropertyRequestDTO> requestList = income.getRequestList();
		// 먼저 delete로 DB에서 검색할 양을 최소로 만들고
		// 그 상태에서 update를 빠르게 끝내서
		// 더 할 게 없을 때 create 한다
		List<PropertyRequestDTO> deleteList = editTypeToRequest(requestList, "D");
		List<PropertyRequestDTO> updateList = editTypeToRequest(requestList, "U");
		List<PropertyRequestDTO> createList = editTypeToRequest(requestList, "C");
		
		int result = toolMapper.deleteAllPropsFrom(objectId, deleteList);
		
		return 0;
	}

	private List<PropertyRequestDTO> editTypeToRequest(List<PropertyRequestDTO> requestList, String editType) {
		return requestList.stream()
				.filter(req -> {return req.getEditType() == editType;})
				.collect(Collectors.toList());
	}

}
