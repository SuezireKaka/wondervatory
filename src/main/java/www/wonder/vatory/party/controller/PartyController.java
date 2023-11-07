package www.wonder.vatory.party.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.OrganizationVO;
import www.wonder.vatory.party.model.SignUpDto;
import www.wonder.vatory.party.service.PartyService;
import www.wonder.vatory.security.anno.ForManagerOrSelf;

@RestController
@CrossOrigin
@RequestMapping("/party")
public class PartyController {
	@Autowired
	private PartyService partyService;

	// /party/listAllAccount/0000/1
	@GetMapping("/listAllAccount/{ownerId}/{page}/{orderColumn}")
	@PreAuthorize("hasAnyAuthority('manager', 'admin')")
	public ResponseEntity<DreamPair<List<AccountVO>, PagingDTO>> listAllAccount(
			@AuthenticationPrincipal AccountVO manager, @PathVariable String ownerId, @PathVariable int page,
			@PathVariable String orderColumn) {
		DreamPair<List<AccountVO>, PagingDTO> result = partyService.listAllAccount(ownerId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// /party/findById/0003
	@GetMapping("/findById/{id}")
	@ForManagerOrSelf
	public ResponseEntity<AccountVO> findById(
			@AuthenticationPrincipal AccountVO account,
			@PathVariable String id) {
		AccountVO result = partyService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/createOrganization")
	public ResponseEntity<Integer> createOrganization(OrganizationVO organization) {
		return ResponseEntity.ok(partyService.createOrganization(organization));
	}

	@PostMapping("/createManager")
	public ResponseEntity<Integer> createManager(@RequestBody List<AccountVO> accountList) {
		return ResponseEntity.ok(partyService.createManager(accountList));
	}

	// /party/anonymous/checkLoginId?loginId=hgghg
	@GetMapping("/anonymous/checkLoginId")
	public ResponseEntity<Boolean> checkLoginId(String loginId) {
		return ResponseEntity.ok(partyService.checkLoginId(loginId));
	}

	// /party/anonymous/checkNick?nick=hgghg
	@GetMapping("/anonymous/checkNick")
	public ResponseEntity<Boolean> checkNick(String nick) {
		return ResponseEntity.ok(partyService.checkNick(nick));
	}

	// /party/anonymous/checkUiqueVal/nick/홍
	@GetMapping("/anonymous/checkUniqueVal/{key}/{val}")
	public ResponseEntity<Boolean> checkUniqueVal(@PathVariable String key, @PathVariable String val) {
		return ResponseEntity.ok(partyService.checkUniqueVal(key, val));
	}

	// /party/anonymous/createMember
	@PostMapping("/anonymous/createMember")
	public ResponseEntity<Integer> createMember(@RequestBody SignUpDto signUpRequest) {
		return ResponseEntity.ok(partyService.mngMember(signUpRequest));
	}

	// /party/updateMember
	@PostMapping("/updateMember")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	// @ForAccountType(accountType = "원더") - 이거 돌아가게 만들고 싶다아
	public ResponseEntity<Integer> updateMember(@AuthenticationPrincipal AccountVO owner,
			@RequestBody SignUpDto signUpRequest) {
		return ResponseEntity.ok(partyService.mngMember(signUpRequest));
	}

	// /party/reRole
	@GetMapping("/reRole/{memberId}/{role}")
	@PreAuthorize("hasAnyAuthority('manager', 'admin')")
	public ResponseEntity<Integer> reRole(@AuthenticationPrincipal AccountVO owner, @PathVariable String memberId,
			@PathVariable String role) {
		return ResponseEntity.ok(partyService.reRole(memberId, role));
	}

	// /party/reRole
	@GetMapping("/updateStatus/{memberId}/{loginResultCode}")
	@PreAuthorize("hasAnyAuthority('manager', 'admin')")
	public ResponseEntity<Integer> updateStatus(@AuthenticationPrincipal AccountVO owner, @PathVariable String memberId,
			@PathVariable String loginResultCode) {
		return ResponseEntity.ok(partyService.updateStatus(memberId, loginResultCode));
	}
	

	
	// /party/deleteMember/닉
	@GetMapping("/deleteMember/{id}")
	@PreAuthorize("hasAnyAuthority('reader', 'writer','manager', 'admin')")
	public ResponseEntity<Integer> deleteMember(@AuthenticationPrincipal AccountVO owner, @PathVariable String id) {
		return ResponseEntity.ok(partyService.deleteMember(id));
	}

}
