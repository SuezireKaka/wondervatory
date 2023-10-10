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
	
	// /work/anonymous/listAllSeries/0001/1
	@GetMapping("/anonymous/listAllSeries/{boardId}/{page}")
	public ResponseEntity<DreamPair<List<ReplyVO>, PagingDTO>> listAllSeries(@PathVariable String boardId, @PathVariable int page) {
		DreamPair<List<ReplyVO>, PagingDTO> result = workService.listAllSeries(boardId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/*
	// /post/anonymous/listAll/000n
	@GetMapping("/anonymous/listAll/{boardId}/{page}")
	public ResponseEntity<DreamPair<List<PostVO>, PagingDTO>> listAllPost(@PathVariable String boardId, @PathVariable int page) {
		DreamPair<List<PostVO>, PagingDTO> result = workService.listAllPost(boardId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}*/
}
