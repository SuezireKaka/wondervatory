package www.wonder.vatory.party.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import www.wonder.vatory.party.model.OrganizationVO;
import www.wonder.vatory.party.model.SignUpDto;
import www.wonder.vatory.party.service.PartyService;



@RestController	
@CrossOrigin
@RequestMapping("/party")
public class PartyController {
	@Autowired
	private PartyService partyService;
	
	// /party/anonymous/listAllAccount/0000/1
	@GetMapping("/anonymous/listAllAccount/{ownerId}/{page}")
	public ResponseEntity<DreamPair<List<AccountVO>, PagingDTO>> listAllAccount(@PathVariable String ownerId, @PathVariable int page) {
		DreamPair<List<AccountVO>, PagingDTO> result = partyService.listAllAccount(ownerId, page);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// /party/anonymous/findByNick/홍
	@GetMapping("/anonymous/findByNick/{nick}")
	public ResponseEntity<AccountVO> findByNick(@PathVariable String nick) {
		AccountVO result = partyService.findByNick(nick);
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

	// /party/anonymous/createMember
	@PostMapping("/anonymous/createMember")
	public ResponseEntity<Integer> createMember(SignUpDto signUpRequest) {
		return ResponseEntity.ok(partyService.createMember(signUpRequest));
	}
}
