package www.wonder.vatory.tool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.tool.mapper.ToolMapper;
import www.wonder.vatory.tool.model.CustomEntityVO;
import www.wonder.vatory.tool.model.CustomObjectVO;
import www.wonder.vatory.tool.model.CustomPropertyVO;
import www.wonder.vatory.tool.model.CustomRelationVO;
import www.wonder.vatory.tool.model.ToolVO;

@Service
public class ToolService {
	@Autowired(required = false)
	private ToolMapper toolMapper;

	/* 시리즈의 모든 툴 직접 붙어있는 조회 
	public DreamPair<List<ToolVO>, PagingDTO> listAllFromSeries(String seriesId, int page) {
		PagingDTO paging = new PagingDTO(page);
		
		List<ToolVO> resultList = toolMapper.listAllFromSeries(seriesId, paging);
		
		long dataCount = toolMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<ToolVO>, PagingDTO>(resultList, paging);
	} -- deprecated : path 접두사로 아래와 통합 */
	
	public DreamPair<List<ToolVO>, PagingDTO> listAllNextTools(
			String seriesId, String idPath, int page) {
		
		PagingDTO paging = new PagingDTO(page);
		String id = idPath.substring(4);
		
		List<ToolVO> resultList = toolMapper.listAllNextTools(seriesId, id, paging);
		
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
	
	public ToolVO manageToolSkin(AccountVO writer, String seriesId, ToolVO toolSkin) {
		// 툴스킨의 id가 ----로 끝나면 create 아니면 update
		String toolSkinId = toolSkin.getId();
		if (toolSkinId.endsWith("----")) {
			toolSkin.setId(toolSkin.getParentId());
			toolMapper.createToolSkin(writer, toolSkin, seriesId);
		}
		else {
			toolMapper.updateToolSkin(toolSkin);
		}
		return toolSkin;
	}
	
	public ToolVO saveToolDetails(AccountVO writer, ToolVO toolData) {
		// 일단 툴을 해부해서 바뀔 것만 꺼내보자
		String toolId = toolData.getId();
		List<CustomEntityVO> entityList = toolData.getCustomEntityList();
		List<CustomRelationVO> relationList = toolData.getCustomRelationList();
		// 해부되어 나온 애들을 또 해부해서 담아보자
		List<List<CustomPropertyVO>> entityPropList =
				entityList.stream().map(entity -> {return entity.getCustomPropertiesList();})
				.collect(Collectors.toList());
		List<List<CustomPropertyVO>> relationPropList =
				relationList.stream().map(relation -> {return relation.getCustomPropertiesList();})
				.collect(Collectors.toList());
		// 먼저 foreach로 entityList, relationList의 싱크를 맞춰서 아이디를 얻고
		entityList.stream().forEach(entity -> {
			syncEntitiesOf(toolId, entityList);
		});
		// 해당 정보를 syncPropertiesOf에 활용한다
		return null;
	}
	
	private int syncEntitiesOf(String toolId, List<CustomEntityVO> entityList) {
		// requestList에 있는 애들 중 아이디가 ----로 시작하는 애들 찾아서 createList 생성
		// 해당 툴의 객체들을 전부 가져오고 requestList랑 비교해서 delete리스트 생성
		// 나머지는 그냥 업데이트 한다고 생각하자
		return 0;
	}
	
	private int syncRelationsOf(String toolId, List<CustomRelationVO> entityList) {
		// requestList에 있는 애들 중 아이디가 ----로 시작하는 애들 찾아서 createList 생성
		// 해당 툴의 객체들을 전부 가져오고 requestList랑 비교해서 delete리스트 생성
		// 나머지는 그냥 업데이트 한다고 생각하자
		return 0;
	}

	private int syncPropertiesOf(String objectId, List<CustomPropertyVO> requestList) {
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
