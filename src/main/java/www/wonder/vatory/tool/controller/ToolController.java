package www.wonder.vatory.tool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.tool.model.ToolVO;
import www.wonder.vatory.tool.service.ToolService;
import www.wonder.vatory.work.model.SeriesVO;

@RestController
@CrossOrigin
@RequestMapping("/tool")
public class ToolController {
	@Autowired
	private ToolService toolService;

	/* // /tool/listAllFromSeries/0000/1
	@GetMapping("/listAllFromSeries/{seriesId}/{page}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	public ResponseEntity<DreamPair<List<ToolVO>, PagingDTO>> listAllFromSeries(@PathVariable String seriesId,
			@PathVariable int page) {
		DreamPair<List<ToolVO>, PagingDTO> result = toolService.listAllFromSeries(seriesId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	} -- deprecated : path 접두사로 아래랑 통합*/

	// /tool/listAllNextTools/path0000/1
	@GetMapping("/listAllNextTools/{idPath}/{page}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	public ResponseEntity<DreamPair<List<ToolVO>, PagingDTO>> listAllNextTools(@PathVariable String idPath,
			@PathVariable int page) {
		DreamPair<List<ToolVO>, PagingDTO> result = toolService.listAllNextTools(idPath, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/*
	 * // /tool/anonymous/listPropertiesOf/0000
	 * 
	 * @GetMapping("/anonymous/listPropertiesOf/{objectId}") public
	 * ResponseEntity<List<CustomPropertyVO>> listPropertiesOf(@PathVariable String
	 * objectId) { List<CustomPropertyVO> result =
	 * toolService.listPropertiesOf(objectId); return new ResponseEntity<>(result,
	 * HttpStatus.OK); } --deprecated : 프론트앤드 테스트 종료
	 */

	// /tool/getToolById/0000
	@GetMapping("/getToolById/{toolId}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	public ResponseEntity<ToolVO> getToolById(@PathVariable String toolId) {
		ToolVO result = toolService.getToolById(toolId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/*
	 * // /tool/anonymous/getToolByEntity/0000
	 * 
	 * @GetMapping("/anonymous/getToolByEntity/{entityId}") public
	 * ResponseEntity<ToolVO> getToolByEntity(@PathVariable String entityId) {
	 * ToolVO result = toolService.getToolByEntity(entityId); return new
	 * ResponseEntity<>(result, HttpStatus.OK); } --deprecated : 안 쓰이게끔 설계를 변경
	 */

	// /tool/syncPropertiesOf/0000
	/*
	 * @PostMapping("/syncPropertiesOf/{objectId}")
	 * 
	 * @PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	 * public ResponseEntity<Integer> syncPropertiesOf(@AuthenticationPrincipal
	 * AccountVO user,
	 * 
	 * @PathVariable String objectId, @RequestBody List<CustomPropertyVO>
	 * requestList) { return
	 * ResponseEntity.ok(toolService.syncPropertiesOf(objectId, requestList)); }
	 * --deprecated : 해당 테스트 종료
	 */

	// /tool/manageToolSkin/0000
	@PostMapping("/manageToolSkin/{seriesId}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	public ResponseEntity<Integer> manageToolSkin(@AuthenticationPrincipal AccountVO owner,
			@RequestBody ToolVO toolSkin, @PathVariable String seriesId) {
		int result = toolService.manageToolSkin(seriesId, toolSkin);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
