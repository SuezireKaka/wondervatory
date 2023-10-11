package www.wonder.vatory.work.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.work.model.PostVO;
import www.wonder.vatory.work.model.ReplyVO;
import www.wonder.vatory.work.model.SeriesVO;
import www.wonder.vatory.work.service.WorkService;


@RestController	
@CrossOrigin
@RequestMapping("/work")
public class WorkController {
	@Autowired
	private WorkService workService;
	
	// /work/anonymous/listAllSeries/0001/1
	@GetMapping("/anonymous/listAllSeries/{boardId}/{page}")
	public ResponseEntity<DreamPair<List<SeriesVO>, PagingDTO>> listAllSeries(@PathVariable String boardId, @PathVariable int page) {
		DreamPair<List<SeriesVO>, PagingDTO> result = workService.listAllSeries(boardId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// /work/anonymous/listAllSeries/0000/1
	@GetMapping("/anonymous/listAllPost/{seriesId}/{page}")
	public ResponseEntity<DreamPair<List<PostVO>, PagingDTO>> listAllPost(@PathVariable String seriesId, @PathVariable int page) {
		DreamPair<List<PostVO>, PagingDTO> result = workService.listAllPost(seriesId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	// /work/anonymous/listAll/0001/타이틀/1         검색 아직 안됨
	@GetMapping("/anonymous/search/{boardId}/{search}/{page}")
	public ResponseEntity<DreamPair<List<ReplyVO>, PagingDTO>> search(@PathVariable String boardId, @PathVariable String search, @PathVariable int page) {
		DreamPair<List<ReplyVO>, PagingDTO> result = workService.search(boardId, search, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/** 원글 상세. 첨부파일 목록, 댓글, 대댓 목록이 내부 변수에 채워짐 */
	// /work/anonymous/getPost/0000           //4글자나 8글자 전부 찾기 가능 댓글과 대댓글만나오게 조건을 8글자이상으로함
	@GetMapping("/anonymous/getPost/{id}")
	public ResponseEntity<ReplyVO> findById(@PathVariable String id) {
		ReplyVO post = workService.findById(id);
		if (post == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	

	/** */
	@PutMapping("/updateReply")
	public ResponseEntity<Integer> updateReply(ReplyVO reply) {
		return new ResponseEntity<>(workService.updateReply(reply), HttpStatus.OK);
	}

	/** hid like로 지우기 */
	//@RequestMapping("/post/{id}")
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deleteReply(@PathVariable String id) {
		return new ResponseEntity<>(workService.deleteReply(id), HttpStatus.OK);
	}
	
}
