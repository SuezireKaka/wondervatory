package www.wonder.vatory.work.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import www.wonder.vatory.work.mapper.BoardMapper;
import www.wonder.vatory.work.model.BoardVO;

@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;

	public List<BoardVO> listAll() {
		return boardMapper.listAll();
	}
	
	public BoardVO findById(String id) {
		return boardMapper.findById(id);
	}

}
