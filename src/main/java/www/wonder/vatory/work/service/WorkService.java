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
import org.springframework.util.ObjectUtils;

import www.wonder.vatory.fileattachment.model.dto.AttachFileDTO;
import www.wonder.vatory.fileattachment.service.AttachFileService;
import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.framework.nlpposservice.NounExtractor;
import www.wonder.vatory.framework.property.PropertyExtractor;
import www.wonder.vatory.iis.model.TagRelId;
import www.wonder.vatory.iis.model.TagRelVO;
import www.wonder.vatory.iis.model.TagVO;
import www.wonder.vatory.iis.service.TagService;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.report.model.ReportCodeVO;
import www.wonder.vatory.report.model.ReportVO;
import www.wonder.vatory.work.mapper.GenreMapper;
import www.wonder.vatory.work.mapper.WorkMapper;
import www.wonder.vatory.work.model.GenreVO;
import www.wonder.vatory.work.model.PostVO;
import www.wonder.vatory.work.model.ReplyVO;
import www.wonder.vatory.work.model.SemiPostVO;
import www.wonder.vatory.work.model.SeriesVO;

@Service
public class WorkService {
	@Autowired
	private WorkMapper workMapper;
	@Autowired
	private GenreMapper genreMapper;
	@Autowired
	private TagService tagService;
	@Autowired
	private AttachFileService attachFileService;
	
	
	/** 게시판의 모든 원글 목록 조회 */ 
	public DreamPair<List<SeriesVO>, PagingDTO> listAllSeries(String boardId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<SeriesVO> listResult = workMapper.listAllSeries(boardId, paging);
		long dataCount = workMapper.getFoundRows();
		paging.buildPagination(dataCount);
		
		// listResult의 각각에 썸네일 뿌리기
		for (SeriesVO series : listResult) {
			List<AttachFileDTO> attachFileList = attachFileService.getAttachFileList(series);
			series.setListAttachFile(attachFileList);
		}

		return new DreamPair<List<SeriesVO>, PagingDTO>(listResult, paging);
	}

	public DreamPair<List<PostVO>, PagingDTO> listAllPost(String seriesId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<PostVO> listResult = workMapper.listAllPost(seriesId, paging);
		long dataCount = workMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<PostVO>, PagingDTO>(listResult, paging);
	}

	/** 한사람의 원글 목록 조회 */ 
	public DreamPair<List<SeriesVO>, PagingDTO> listUserSeries(String id, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<SeriesVO> listResult = workMapper.listUserSeries(id, paging);
		long dataCount = workMapper.getFoundRows();
		paging.buildPagination(dataCount);
		
		// listResult의 각각에 썸네일 뿌리기
		for (SeriesVO series : listResult) {
			List<AttachFileDTO> attachFileList = attachFileService.getAttachFileList(series);
			series.setListAttachFile(attachFileList);
		}

		return new DreamPair<List<SeriesVO>, PagingDTO>(listResult, paging);
	}
	
	public List<GenreVO> listAllGenre() {
		return genreMapper.listAllGenre();
	}

	public DreamPair<List<GenreVO>, PagingDTO> listAllGenres(int page) {
		PagingDTO paging = new PagingDTO(page);
		List<GenreVO> listResult = genreMapper.listAllGenres(paging);
		long dataCount = genreMapper.getFoundRows();
		paging.buildPagination(dataCount);
		
		for (GenreVO genre : listResult) {
			List<GenreVO> types = genreMapper.listAllGenreOfSeries(genre.getId());
		}
		return new DreamPair<List<GenreVO>, PagingDTO>(listResult, paging);
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
	
	public boolean isFavorites(String ownerId, String responseId) {
		return workMapper.isFavorites(ownerId, responseId);
	}

	public int onLike(String id) {
		return workMapper.onLike(id);
	}
	
	public int onDisLike(String id) {
		return workMapper.onDisLike(id);
	}
	
	public DreamPair<List<SeriesVO>, PagingDTO> favoritesAll(String ownerId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<SeriesVO> listResult = workMapper.favoritesAll(ownerId, paging);
		long dataCount = workMapper.getFoundRows();
		paging.buildPagination(dataCount);
		
		// listResult의 각각에 썸네일 뿌리기
		for (SeriesVO series : listResult) {
			List<AttachFileDTO> attachFileList = attachFileService.getAttachFileList(series);
			series.setListAttachFile(attachFileList);
		}

		return new DreamPair<List<SeriesVO>, PagingDTO>(listResult, paging);
	}
	
	public int toggleFavorites(String ownerId, String responseId) {
		//좋아하는게 있는지 검사 = responseId
		boolean isFirstFavorites = workMapper.isFirstFavorites(ownerId, responseId);
		int ret = 0;
		
		//없으면 create 있으면 업데이트
		if(isFirstFavorites) {
			ret = workMapper.firstFavorites(ownerId, responseId);
		} else {
			ret = workMapper.toggleFavorites(ownerId, responseId);
		}
		return ret;
	}
	
	public ReplyVO findById(String id) {
		//postMapper.findById(id)는 id의 primary key 특성으로 사전순서가 보장되어 있음
		List<ReplyVO> oneDimList = id.length() == 4 ? workMapper.findSeriesById(id) : workMapper.findPostById(id) ;
		if (oneDimList.isEmpty()) {
			return null;
		}
		
		ReplyVO ret = (ReplyVO) oneDimList.get(0);
		
		if (ret.getHTier() == 0) {
			((SeriesVO) ret).setGenreList(new ArrayList<GenreVO>());
		}
		
		ret.incReadCount();
		workMapper.incReadCount(ret.getId());
		
		
		
		List<AttachFileDTO> attachFileList = attachFileService.getAttachFileList(ret);
		ret.setListAttachFile(attachFileList);
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
	
	public DreamPair<PostVO, PostVO> getPrevAndNext(String id) {
		String parentId = id.substring(0, 4);
		PostVO prev = workMapper.getPrev(parentId, id);
		PostVO next = workMapper.getNext(parentId, id);
		return new DreamPair<>(prev, next);
	}
	
	public int manageWork(AccountVO user, SemiPostVO semiPost) {
		//parent의 hTier가 0보다 작으면 시리즈
		String type = semiPost.getHTier() < 0 ? "Series"
				//parent의 hTier가 0이면 포스트
				: semiPost.getHTier() == 0 ? "Post"
				// 아니면 리플라이
				: "Reply";
		int cnt = 0;
		String semiPostId = semiPost.getId();
		// 세미포스트 id가 ----로 끝나면 create 아니면 uodate
		if (semiPostId.endsWith("----")) {
			semiPost.setWriter(user);
			semiPost.setId(semiPostId.substring(0, semiPostId.length() - 4));
			cnt = workMapper.createSemiPost(semiPost, type);
			cnt += genreMapper.createGenre(semiPost);
			createTagRelation(semiPost);
			attachFileService.createAttachFiles(semiPost);

		}
		// 그렇지 않으면 수정
		else {
			attachFileService.deleteAttachFiles(semiPost);
			cnt = workMapper.updateSemiPost(semiPost, type);
			cnt += genreMapper.updateGenre(semiPost);
			createTagRelation(semiPost);
			attachFileService.createAttachFiles(semiPost);
	
		}

		return cnt;
	}
	

	private int syncRptTypes(String id, List<GenreVO> genreTypesList) {
		int result = 0;
		int requestCount = genreTypesList.size();
		// 현재 들어있는 개수랑 비교해서 판단
		int prevCount = genreMapper.countGenreTypesOf(id);
		
		if (requestCount > prevCount) {
			// 늘어났으면 prevCount만큼 리스트를 분할해서 업데이트 이후 추가
			int border = prevCount;
			
		    List<List<GenreVO>> listOfLists = new ArrayList<>(
		    		genreTypesList.stream()
			    	.collect(Collectors.groupingBy(s -> genreTypesList.indexOf(s) >= border))
			    	.values()
		    );
		    // 둘로 쪼개는데 한 쪽이 없으면 알잘딱깔센 [[], [0, 1, 2]] 해야 할 거 아니야 ㅡ.ㅡ
		    // 근데 못 해서 일단 마지막을 insertList로 두고
		    int size = listOfLists.size();
		    List<GenreVO> insertList = listOfLists.get(size - size);
		    
		    //사이즈에 따라 updateList를 다르게
		    if (listOfLists.size() == 1) {
		    	result = genreMapper.insertToSync(id, 0, insertList);
		    }
		    else {
		    	List<GenreVO> updateList = listOfLists.get(0);
		    	result = genreMapper.updateAllGenreFrom(id, updateList)
		    		& genreMapper.insertToSync(id, updateList.size(), insertList);
		    }
		    
		}
		else {
			result = genreMapper.deleteToSync(id, requestCount)
				& genreMapper.updateAllGenreFrom(id, genreTypesList);
		}
		
		return result;
	}

	
	private Map<String, Integer> buildTF(SemiPostVO post) {
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
	private void createTagRelation(SemiPostVO post) {
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
			new TagRelVO(new TagRelId("T_work", post.getId(), tagVo.getId()), 
					mapTF.get(tagVo.getWord()))).collect(Collectors.toList());
		for (TagVO tagVo : listNewTags) {
			TagRelId id = new TagRelId("T_work", post.getId(), tagVo.getId());
			list.add(new TagRelVO(id, mapTF.get(tagVo.getWord())));
		}
		
		tagService.saveAllTagRelVO(list);
	}
	
	
	
	
	
	
	

	
	/** hid like로 지우기
	 * tf, df 정보 수정도 고려하여야 함.
	*/
	public int deleteReply(String id) {
		return workMapper.deleteReply(id);
	}
	
	
	
}