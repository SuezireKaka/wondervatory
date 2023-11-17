package www.wonder.vatory.tool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.DreamUtility;
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
		String toolId = toolData.getId();
		String seriesId = toolData.getSeries().getId();
		
		// 툴스킨 먼저 처리
		manageToolSkin(writer, seriesId, toolData);
		
		// 커스텀 오브젝트들 중 새로운 애들에 ID 부여
		List<CustomEntityVO> entityList = toolData.getCustomEntityList();
		List<CustomEntityVO> newEntityList = entityList.stream()
				.filter(entity -> entity.getId().startsWith("----"))
				.collect(Collectors.toList());
		List<CustomEntityVO> editedEntityList = entityList.stream()
				.filter(entity -> entity.isEdited())
				.collect(Collectors.toList());

		List<CustomRelationVO> relationList = toolData.getCustomRelationList();
		List<CustomRelationVO> newRelationList = relationList.stream()
				.filter(relation -> relation.getId().startsWith("----"))
				.collect(Collectors.toList());
		List<CustomRelationVO> editedRelationList = relationList.stream()
				.filter(relation -> relation.isEdited())
				.collect(Collectors.toList());
		
		if (newEntityList.size() + newRelationList.size() > 0) {
			grantNewIds(newEntityList, newRelationList);
		}

		// 판단 기준은 id 비교로
		List<String> bringIdList = Entity.getMultiId(DreamUtility.upcastList(entityList));
		bringIdList.addAll(Entity.getMultiId(DreamUtility.upcastList(relationList)));
				
		// 기존 커스텀 오브젝트에 있던 애가 없어졌으면 deleteList
		List<CustomObjectVO> oldObjectList = customObjectMapper.listAllObject(toolId);
		List<CustomObjectVO> deleteList = oldObjectList.stream()
				.filter(obj -> ! bringIdList.contains(obj.getId()))
				.collect(Collectors.toList());
		
		// 먼저 delete
		if (deleteList.size() > 0) {
			customObjectMapper.deleteObjectsToSync(deleteList);
		}
		
		// 그 다음 update
		if (editedEntityList.size() + editedRelationList.size() > 0) {
			List<CustomObjectVO> updateList = DreamUtility.upcastList(editedEntityList);
			updateList.addAll(editedRelationList);
			customObjectMapper.updateAllObjectsFrom(toolId, updateList);
		}
		
		// 마지막으로 insert
		insertNewObjects(toolId, newEntityList, newRelationList);
		
		// 해부되어 나온 애들을 또 해부해서 담아보자
		List<List<CustomPropertyVO>> entityPropList =
				entityList.stream().map(entity -> {return entity.getCustomPropertiesList();})
				.collect(Collectors.toList());
		List<List<CustomPropertyVO>> relationPropList =
				relationList.stream().map(relation -> {return relation.getCustomPropertiesList();})
				.collect(Collectors.toList());
		
		return toolData;
	}

	private void insertNewObjects(String toolId, List<CustomEntityVO> newEntityList,
			List<CustomRelationVO> newRelationList) {
		if (newEntityList.size() > 0) {
			customObjectMapper.insertObjectsToSync(toolId, "Entity",
					newEntityList.stream()
					.map(CustomObjectVO.class::cast)
					.collect(Collectors.toList()));
		}
		
		if (newRelationList.size() > 0) {
			customObjectMapper.insertObjectsToSync(toolId, "Relation",
				newRelationList.stream()
				.map(CustomObjectVO.class::cast)
				.collect(Collectors.toList()));
		}
	}
	
	private String[] grantNewIds(List<CustomEntityVO> newEntityList,
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
			// relation 위치면 릴레이션으로 간주하고 처리
			if (n >= newEntityCount) {
				// n번째 녀석에 해당되는 object의 id를 바꾸고
				newRelationList.get(n - newEntityCount)
					.setId(grantId);
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
				if (rel.getOneId().equals(memoId)) {
					rel.setOneId(grantId);
				}
				if (rel.getOtherId().equals(memoId)) {
					rel.setOtherId(grantId);
				}
			}
			
		}
		
		return newIdArray;
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
