package www.wonder.vatory.tool.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.Entity;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.tool.mapper.CustomObjectMapper;
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
	
	@Autowired(required = false)
	private CustomObjectMapper customObjectMapper;

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
		List<CustomEntityVO> resEntityList = customObjectMapper.listAllEntity(toolId);
		resEntityList.stream()
			.forEach(entity -> {entity
				.getCustomPropertiesList()
				.addAll(customObjectMapper.listPropertiesOf(entity.getId()));
			}
		);
		result.getCustomEntityList().addAll(resEntityList);
		
		List<CustomRelationVO> resRelationList = customObjectMapper.listAllRelation(toolId);
		resRelationList.stream()
			.forEach(relation -> {relation
				.getCustomPropertiesList()
				.addAll(customObjectMapper.listPropertiesOf(relation.getId()));
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
		// 일단 툴을 해부해서 필요한 걸 꺼내보자
		String toolId = toolData.getId();
		int newXToolSize = toolData.getXToolSize();
		int newYToolSize = toolData.getYToolSize();
		
		List<CustomEntityVO> entityList = toolData.getCustomEntityList();
		List<CustomEntityVO> newEntityList = entityList.stream()
				.filter(entity -> entity.getId().startsWith("----"))
				.collect(Collectors.toList());

		List<CustomRelationVO> relationList = toolData.getCustomRelationList();
		List<CustomRelationVO> newRelationList = relationList.stream()
				.filter(relation -> relation.getId().startsWith("----"))
				.collect(Collectors.toList());
		// 해부되어 나온 애들을 또 해부해서 담아보자
		List<List<CustomPropertyVO>> entityPropList =
				entityList.stream().map(entity -> {return entity.getCustomPropertiesList();})
				.collect(Collectors.toList());
		List<List<CustomPropertyVO>> relationPropList =
				relationList.stream().map(relation -> {return relation.getCustomPropertiesList();})
				.collect(Collectors.toList());
		
		grantNewIds(newEntityList, newRelationList);
		// 먼저 foreach로 entityList, relationList의 싱크를 맞춰서 아이디를 얻고
		//syncObjetsOf(toolId, "Entity", entityList);
		
		//syncObjetsOf(toolId, "Relation", relationList);
		// 해당 정보를 syncPropertiesOf에 활용한다
		return toolData;
	}
	
	private void grantNewIds(List<CustomEntityVO> newEntityList,
			List<CustomRelationVO> newRelationList) {
		// relationList의 순서에 맞는 oneList와 otherList를 추출
		
		// 먼저 새롭게 t_custom_object로 들어갈 애들의 개수를 세고
		int newEntityCount = newEntityList.size();
		int newRelationCount = newRelationList.size();
		int summonCount = newEntityCount + newRelationCount;
		// 그 개수만큼 아이디를 뽑아온다
		String[] newIdArray = customObjectMapper
				.getNextMultiIdConcat("s_object", summonCount)
				.split(", ");
		
		// entityList와 relationList를 합친 List<CustomObjectVO>의 n번째 원소에 대해
		List<CustomObjectVO> newObjectList = new ArrayList<>();
		newObjectList.addAll(newEntityList);
		newObjectList.addAll(newRelationList);
		String memoId = "";
		for (int n = 0; n < summonCount; n++) {
			String grantId = newIdArray[n];
			CustomObjectVO target = newObjectList.get(n);
			// 얘 n번째 녀석의 id를 memoId에 기억한다
			memoId = target.getId();
			// target
			// relation 위치면 릴레이션으로 간주하고 처리
			if (n >= newEntityCount) {
				// n번째 녀석에 해당되는 object의 id를 바꾸고
				newRelationList.get(n - newEntityCount)
					.setId(newIdArray[n]);
			}
			// 아니면 엔티티로 간주하고 처리
			else {
				// n번째 녀석에 해당되는 object의 id를 바꾸고
				newEntityList.get(n)
					.setId(grantId);
			}
			// relationList 각각에 대해
			for (CustomRelationVO rel : newRelationList) {
				// one과 other의 id가 memoId와 같으면 걔들도 다 바꾼다
				if (rel.getOne().getId().equals(memoId)) {
					rel.getOne().setId(grantId);
				}
				if (rel.getOther().getId().equals(memoId)) {
					rel.getOther().setId(grantId);
				}
			}
			
			
			
		}
		
		int iiii = 0;
		
	}
	
	private int syncObjetsOf(String toolId, String type,
			List<CustomObjectVO> objectList) {

		Optional<Boolean> isAllNew = objectList.stream()
				.map(obj -> obj.getId().startsWith("----"))
				.reduce((isNew1, isNew2) -> isNew1 && isNew2);
		// 다 새로우면 아래 과정은 할 필요 없음
		if (! isAllNew.get()) {
			// 해당 툴의 객체들을 전부 가져오고
			List<CustomObjectVO> prevObjectList = customObjectMapper.listAllObject(toolId);
			// 새 엔티티 리스트에 없는 애들을 deleteList로 
			List<String> objectIdList = objectList.stream()
					.map(obj -> obj.getId())
					.collect(Collectors.toList());
			List<CustomObjectVO> deleteList = prevObjectList.stream()
					.filter(obj -> ! objectIdList.contains(obj.getId()))
					.collect(Collectors.toList());
			List<CustomObjectVO> updateList = prevObjectList.stream()
					.filter(obj -> ! objectIdList.contains(obj.getId()))
					.collect(Collectors.toList());
		}
		// objectList에 있는 애들 중 아이디가 ----로 시작하는 애들 찾아서 insertList 생성
		List<CustomObjectVO> insertList = objectList.stream()
				.filter(obj -> obj.getId().startsWith("----"))
				.collect(Collectors.toList());
		
		customObjectMapper.insertObjectsToSync(toolId, type, insertList);
		
		return 0;
	}

	private int syncPropertiesOf(String objectId, List<CustomPropertyVO> requestList) {
		int result = 0;
		int requestCount = requestList.size();
		// 현재 들어있는 개수랑 비교해서 판단
		int prevCount = customObjectMapper.countPropertiesOf(objectId);
		
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
		    
		    result = customObjectMapper.updateAllPropsFrom(objectId, updateList)
		    	& customObjectMapper.insertPropsToSync(objectId, updateList.size(), insertList);
		}
		else {
			result = customObjectMapper.deletePropsToSync(objectId, requestCount)
				& customObjectMapper.updateAllPropsFrom(objectId, requestList);
		}
		
		return result;
	}

}
