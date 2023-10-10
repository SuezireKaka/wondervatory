package www.wonder.vatory.tool.controller;

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
import www.wonder.vatory.tool.model.ToolVO;
import www.wonder.vatory.tool.service.ToolService;

@RestController	
@CrossOrigin
@RequestMapping("/tool")
public class ToolController {
	@Autowired
	private ToolService toolService;
	
	// /tool/anonymous/listAllFromSeries/0000/1
	@GetMapping("/anonymous/listAllFromSeries/{seriesId}/{page}")
	public ResponseEntity<DreamPair<List<ToolVO>, PagingDTO>> listAllFromSeries(@PathVariable String seriesId, @PathVariable int page) {
		DreamPair<List<ToolVO>, PagingDTO> result = toolService.listAllFromSeries(seriesId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// /tool/anonymous/getToolById/0000
	@GetMapping("/anonymous/getToolById/{toolId}")
	public ResponseEntity<ToolVO> getToolById(@PathVariable String toolId) {
		ToolVO result = toolService.getToolById(toolId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
