package www.wonder.vatory.party.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.party.service.PartyService;



@RestController	
@CrossOrigin
@RequestMapping("/party")
public class PartyController {
	@Autowired
	private PartyService partyService;
}
