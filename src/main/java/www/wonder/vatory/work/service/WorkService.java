package www.wonder.vatory.work.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.framework.nlpposservice.NounExtractor;
import www.wonder.vatory.framework.property.PropertyExtractor;
import www.wonder.vatory.iis.model.TagRelId;
import www.wonder.vatory.iis.model.TagRelVO;
import www.wonder.vatory.iis.model.TagVO;
import www.wonder.vatory.iis.service.TagService;
import www.wonder.vatory.work.mapper.WorkMapper;
import www.wonder.vatory.work.model.PostVO;
import www.wonder.vatory.work.model.ReplyVO;
import www.wonder.vatory.work.model.SeriesVO;

@Service
public class WorkService {
	@Autowired
	private WorkMapper workMapper;
	@Autowired
	private TagService tagService;
	
	/** 게시판의 모든 원글 목록 조회 */ 
	public DreamPair<List<SeriesVO>, PagingDTO> listAllSeries(String boardId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<SeriesVO> listResult = workMapper.listAllSeries(boardId, paging);
		long dataCount = workMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<SeriesVO>, PagingDTO>(listResult, paging);
	}

	public DreamPair<List<PostVO>, PagingDTO> listAllPost(String seriesId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<PostVO> listResult = workMapper.listAllPost(seriesId, paging);
		long dataCount = workMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<PostVO>, PagingDTO>(listResult, paging);
	}

	public DreamPair<List<ReplyVO>, PagingDTO> search(String boardId, String search, int page) {
		String[] arrSearch = search.split(" ");
		if (arrSearch.length == 0) {
			PagingDTO paging = new PagingDTO(page);
			paging.buildPagination(0);
			return new DreamPair(new ArrayList<>(), paging);
		}
		PagingDTO paging = new PagingDTO(page);
		List<ReplyVO> listResult = workMapper.searchByTfIdf(boardId, arrSearch, paging);
		long dataCount = workMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair(listResult, paging);
	}
	
	public ReplyVO findById(String id) {
		//postMapper.findById(id)는 id의 primary key 특성으로 사전순서가 보장되어 있음
		List<ReplyVO> oneDimList = id.length() == 4 ? workMapper.findSeriesById(id) : workMapper.findPostById(id) ;
		if (oneDimList.isEmpty()) {
			return null;
		}
		
		ReplyVO ret = (ReplyVO) oneDimList.get(0);
		ret.incReadCount();
		workMapper.incReadCount(ret.getId());

		//attachFileService 아직 추가안해서 주석처리
		//List<AttachFileDTO> attachFileList = attachFileService.getAttachFileList(ret);
		//ret.setListAttachFile(attachFileList);
		
		if (id.length() == 8) {
			Map<String, ReplyVO> map = new HashMap<>();
			for (ReplyVO reply : oneDimList) {
				map.put(reply.getId(), reply);
				
				ReplyVO parent = map.get(reply.getParentId());
				//원글이면 null이 됨
				if (parent != null) {
					parent.appendReply(reply);
				}
			}
		}
		
		return ret;
	}
	

	
	private Map<String, Integer> buildTF(PostVO post) {
		//대상이되는 문자열 추출
		List<String> docs = PropertyExtractor.extractProperty(post);

		List<String> listNoun = new ArrayList<>();
		for (String doc : docs) {
			if (doc == null)
				continue;
			//대상이되는 문자열속의 명사 추출
			doc = doc.trim();
			if (!doc.isEmpty())
				listNoun.addAll(NounExtractor.extracteNoun(doc));
		}

		//게시글 수정 처리는 미루어 둘 것임
		Map<String, Integer> mapWordCnt = new TreeMap<>();
		
		for (String noun : listNoun) {
			if (mapWordCnt.containsKey(noun)) {
				mapWordCnt.put(noun, mapWordCnt.get(noun) + 1);
			} else {
				mapWordCnt.put(noun, 1);
			}
		}
		return mapWordCnt;
	}

	@Transactional
	private void createTagRelation(PostVO post) {
		Map<String, Integer> mapTF = buildTF(post);

		//기존 단어 찾음. 기존 단어의 DF는 이 문서에서 나온 단어 출현 횟수를 올려주어야 함. 
		List<TagVO> listExistingTags = tagService.findByWord(mapTF.keySet());
		
		//새 단어 목록 찾기. 성능을 고려한 개발입니다. 따라서 정렬을 도입함
		SortedSet<String> 기존단어목록 = new TreeSet(listExistingTags.stream().map(tagVo->tagVo.getWord()).collect(Collectors.toList()));
		List<String> listNewWords = mapTF.keySet().stream().filter(word->! 기존단어목록.contains(word)).collect(Collectors.toList());
		List<TagVO> listNewTags = listNewWords.stream().map(newWord->
			new TagVO(tagService.getId("s_tag"), newWord, "")).collect(Collectors.toList());
		tagService.saveAllTagVO(listNewTags);
		
		//게시물과 단어 사이의 관계 만들기
		List<TagRelVO> list = listExistingTags.stream().map(tagVo->
			new TagRelVO(new TagRelId("T_reply", post.getId(), tagVo.getId()), 
					mapTF.get(tagVo.getWord()))).collect(Collectors.toList());
		for (TagVO tagVo : listNewTags) {
			TagRelId id = new TagRelId("T_reply", post.getId(), tagVo.getId());
			list.add(new TagRelVO(id, mapTF.get(tagVo.getWord())));
		}
		
		tagService.saveAllTagRelVO(list);
	}
	
	
	
	
	
	
	
	

	/** */
	public int updateReply(ReplyVO reply) {
		return workMapper.updateReply(reply);
	}
	
	/** hid like로 지우기
	 * tf, df 정보 수정도 고려하여야 함.
	*/
	public int deleteReply(String id) {
		return workMapper.deleteReply(id);
	}
	
	
	
}