package www.wonder.vatory.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import www.wonder.vatory.mail.service.MailService;

@RestController
@CrossOrigin
@RequestMapping("/mail")
public class MailController {
	@Autowired
	private MailService mailService;
	
	// /mail/anonymous/sendMailToNaver/
	@GetMapping("/anonymous/sendMailToNaver/")
	public ResponseEntity<Boolean> sendMailToNaver() {
		boolean result = mailService.sendMailToNaver();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
