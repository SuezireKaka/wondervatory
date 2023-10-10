package www.wonder.vatory.tool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.tool.mapper.ToolMapper;
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

	public ToolVO getToolById(String toolId) {
		ToolVO result = toolMapper.getToolById(toolId);
		result.getCustomEntityList().addAll(toolMapper.listAllEntity(toolId));
		result.getCustomRelationList().addAll(toolMapper.listAllRelation(toolId));
		return result;
	}

}
