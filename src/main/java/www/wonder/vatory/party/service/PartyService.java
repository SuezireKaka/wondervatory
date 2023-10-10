package www.wonder.vatory.party.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.mapper.PartyMapper;
import www.wonder.vatory.party.model.AccountVO;


@Service
public class PartyService {
	@Autowired(required = false)
	private PartyMapper partyMapper;

	@Autowired(required = false)
	private PasswordEncoder pwdEnc;
	
	/** 회사의 모든 계정 조회 */ 
	public DreamPair<List<AccountVO>, PagingDTO> listAllAccount(String ownerId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<AccountVO> listResult = partyMapper.listAllAccount(ownerId, paging);
		long dataCount = partyMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<AccountVO>, PagingDTO>(listResult, paging);
	}
}
