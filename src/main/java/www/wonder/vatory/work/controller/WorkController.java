package www.wonder.vatory.work.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.work.model.ReplyVO;
import www.wonder.vatory.work.service.WorkService;


@RestController	
@CrossOrigin
@RequestMapping("/work")
public class WorkController {
	@Autowired
	private WorkService workService;
	
	// /work/anonymous/listAll/0001/1
	@GetMapping("/anonymous/listAll/{boardId}/{page}")
	public ResponseEntity<DreamPair<List<ReplyVO>, PagingDTO>> listAllPost(@PathVariable String boardId, @PathVariable int page) {
		DreamPair<List<ReplyVO>, PagingDTO> result = workService.listAllPost(boardId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	// /work/anonymous/listAll/0001/0001/1
	@GetMapping("/anonymous/search/{boardId}/{search}/{page}")
	public ResponseEntity<DreamPair<List<ReplyVO>, PagingDTO>> search(@PathVariable String boardId, @PathVariable String search, @PathVariable int page) {
		DreamPair<List<ReplyVO>, PagingDTO> result = workService.search(boardId, search, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/** 원글 상세. 첨부파일 목록, 댓글, 대댓 목록이 내부 변수에 채워짐 */
	// /post/anonymous/getPost/p001
	@GetMapping("/anonymous/getPost/{id}")
	public ResponseEntity<ReplyVO> findById(@PathVariable String id) {
		ReplyVO post = workService.findById(id);
		if (post == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	
}
