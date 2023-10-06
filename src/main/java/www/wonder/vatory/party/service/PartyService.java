package www.wonder.vatory.party.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import www.wonder.vatory.party.mapper.PartyMapper;


@Service
public class PartyService {
	@Autowired(required = false)
	private PartyMapper partyMapper;

	@Autowired(required = false)
	private PasswordEncoder pwdEnc;
}
