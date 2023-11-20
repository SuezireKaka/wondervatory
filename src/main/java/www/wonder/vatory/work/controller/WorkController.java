package www.wonder.vatory.work.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.work.model.GenreVO;
import www.wonder.vatory.work.model.PostVO;
import www.wonder.vatory.work.model.ReadVO;
import www.wonder.vatory.work.model.ReplyVO;
import www.wonder.vatory.work.model.SemiPostVO;
import www.wonder.vatory.work.model.SeriesVO;
import www.wonder.vatory.work.service.WorkService;

@RestController
@CrossOrigin
@RequestMapping("/work")
public class WorkController {
	@Autowired
	private WorkService workService;

	// /work/anonymous/listAllSeries/0001/1?genreId=0001
	@GetMapping("/anonymous/listAllSeries/{boardId}/{page}")
	public ResponseEntity<DreamPair<List<SeriesVO>, PagingDTO>> listAllSeries(@PathVariable String boardId,
			@PathVariable int page, String genreId) {
		DreamPair<List<SeriesVO>, PagingDTO> result = workService.listAllSeries(boardId, page, genreId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// /work/anonymous/listAllPost/0000/1
	@GetMapping("/anonymous/listAllPost/{seriesId}/{page}")
	public ResponseEntity<DreamPair<List<PostVO>, PagingDTO>> listAllPost(@PathVariable String seriesId,
			@PathVariable int page) {
		DreamPair<List<PostVO>, PagingDTO> result = workService.listAllPost(seriesId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// /work/anonymous/listUserSeries/id/1
	@GetMapping("/anonymous/listUserSeries/{id}/{page}")
	public ResponseEntity<DreamPair<List<SeriesVO>, PagingDTO>> listUserSeries(@PathVariable String id,
			@PathVariable int page) {
		DreamPair<List<SeriesVO>, PagingDTO> result = workService.listUserSeries(id, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// /work/anonymous/listAllGenre
	@GetMapping("/anonymous/listAllGenre")
	public ResponseEntity<List<GenreVO>> listAllGenre() {
		List<GenreVO> list = workService.listAllGenre();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// /work/anonymous/listRead
	@GetMapping("/anonymous/listRead")
	public ResponseEntity<List<ReadVO>> listRead() {
		List<ReadVO> list = workService.listRead();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	// /work/anonymous/search/0002/타이틀/1?genreId= 검색 아직 안됨
	@GetMapping("/anonymous/search/{boardId}/{search}/{page}")
	public ResponseEntity<DreamPair<List<ReplyVO>, PagingDTO>> search(@PathVariable String boardId,
			@PathVariable String search, @PathVariable int page, String genreId) {
		DreamPair<List<ReplyVO>, PagingDTO> result = workService.search(boardId, search, page, genreId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/** 원글 상세. 첨부파일 목록, 댓글, 대댓 목록이 내부 변수에 채워짐 */
	// /work/anonymous/findById/0000 //4글자나 8글자 전부 찾기 가능 댓글과 대댓글만나오게 조건을 8글자이상으로함
	@GetMapping("/anonymous/findById/{id}")
	public ResponseEntity<ReplyVO> findById(@AuthenticationPrincipal AccountVO askAccount, 
			@PathVariable String id) {
		ReplyVO result = workService.findById(askAccount, id);
		if (result == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// /work/anonymous/getPrevAndNext/0000 //4글자나 8글자 전부 찾기 가능 댓글과 대댓글만나오게 조건을
	// 8글자이상으로함
	@GetMapping("/anonymous/getPrevAndNext/{id}")
	public ResponseEntity<DreamPair<PostVO, PostVO>> getPrevAndNext(@PathVariable String id) {
		DreamPair<PostVO, PostVO> result = workService.getPrevAndNext(id);
		if (result == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// /work/isFavorites/0000
	// 완전 이상한 꼼수
	@GetMapping("/isFavorites/{responseId}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer', 'manager', 'admin')")
	public ResponseEntity<Boolean> isFavorites(@AuthenticationPrincipal AccountVO owner,
			@PathVariable String responseId) {
		boolean result = workService.isFavorites(owner.getId(), responseId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// /work/favoritesAll/0001
	@GetMapping("/favoritesAll/{page}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	public ResponseEntity<DreamPair<List<SeriesVO>, PagingDTO>> favoritesAll(@AuthenticationPrincipal AccountVO owner,
			@PathVariable int page) {
		DreamPair<List<SeriesVO>, PagingDTO> result = workService.favoritesAll(owner.getId(), page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 0000000 /work/toggleFavorites/0000
	@GetMapping("/toggleFavorites/{responseId}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	public ResponseEntity<Integer> toggleFavorites(@AuthenticationPrincipal AccountVO owner,
			@PathVariable String responseId) {
		int result = workService.toggleFavorites(owner.getId(), responseId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 하나로 전부 통합할 것
	// /work/manageWork
	@PostMapping("/manageWork")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	public ResponseEntity<Integer> manageWork(@AuthenticationPrincipal AccountVO user,
			@RequestBody SemiPostVO semiPost) {
		if (user.getId().equals(semiPost.getWriter().getId())) {
			return ResponseEntity.ok(workService.manageWork(user, semiPost));
		}
		return null;
	}

	// /work/anonymous/onLike
	@GetMapping("/anonymous/onLike")
	public ResponseEntity<Integer> onLike(String id) {
		return ResponseEntity.ok(workService.onLike(id));
	}

	// /work/anonymous/onLike
	@GetMapping("/anonymous/onDisLike")
	public ResponseEntity<Integer> onDisLike(String id) {
		return ResponseEntity.ok(workService.onDisLike(id));
	}

	/** hid like로 지우기 */
	// @RequestMapping("/post/{id}")
	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> deleteReply(@PathVariable String id) {
		return new ResponseEntity<>(workService.deleteReply(id), HttpStatus.OK);
	}

}
