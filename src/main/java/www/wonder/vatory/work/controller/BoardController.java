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

import www.wonder.vatory.work.model.BoardVO;
import www.wonder.vatory.work.service.BoardService;

@RestController		//Container에 담기도록 지정
@RequestMapping("/bb")
@CrossOrigin(origins = "*")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	// /bb/anonymous/listAll  //ㅁㄴㅇㄹㅁㄴㅇ
	@GetMapping("/anonymous/listAll")
	public ResponseEntity<List<BoardVO>> listAll() {
		List<BoardVO> list = boardService.listAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BoardVO> findById(@PathVariable String id) {
		BoardVO board = boardService.findById(id);
		return ResponseEntity.ok(board);
	}
}
