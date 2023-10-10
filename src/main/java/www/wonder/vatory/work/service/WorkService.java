package www.wonder.vatory.work.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.work.mapper.WorkMapper;
import www.wonder.vatory.work.model.ReplyVO;

@Service
public class WorkService {
	@Autowired
	private WorkMapper workMapper;

	/** 게시판의 모든 원글 목록 조회 */ 
	public DreamPair<List<ReplyVO>, PagingDTO> listAllSeries(String boardId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<ReplyVO> listResult = workMapper.listAllSeries(boardId, paging);
		long dataCount = workMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<ReplyVO>, PagingDTO>(listResult, paging);
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
		List<ReplyVO> oneDimList = workMapper.findById(id);
		if (oneDimList.isEmpty()) {
			return null;
		}
		
		ReplyVO ret = (ReplyVO) oneDimList.get(0);
		ret.incReadCount();
		workMapper.incReadCount(ret.getId());

		//attachFileService
		//List<AttachFileDTO> attachFileList = attachFileService.getAttachFileList(ret);
		//ret.setListAttachFile(attachFileList);
		
		Map<String, ReplyVO> map = new HashMap<>();
		for (ReplyVO reply : oneDimList) {
			map.put(reply.getId(), reply);
			
			ReplyVO parent = map.get(reply.getParentId());
			//원글이면 null이 됨
			if (parent != null) {
				parent.appendReply(reply);
			}
		}
		return ret;
	}
	
}