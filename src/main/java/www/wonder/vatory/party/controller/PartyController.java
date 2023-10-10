package www.wonder.vatory.party.controller;

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
import www.wonder.vatory.party.model.AccountVO;
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
}
