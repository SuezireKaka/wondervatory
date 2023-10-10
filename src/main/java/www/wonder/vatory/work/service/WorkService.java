package www.wonder.vatory.work.service;

import java.util.List;

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
}