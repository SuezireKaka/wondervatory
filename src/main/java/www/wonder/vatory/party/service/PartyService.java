package www.wonder.vatory.party.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import www.wonder.vatory.framework.model.DreamPair;
import www.wonder.vatory.framework.model.PagingDTO;
import www.wonder.vatory.party.mapper.PartyMapper;
import www.wonder.vatory.party.model.AccountVO;
import www.wonder.vatory.party.model.OrganizationVO;
import www.wonder.vatory.party.model.PersonVO;


@Service
public class PartyService implements UserDetailsService {
	@Autowired(required = false)
	private PartyMapper partyMapper;

	@Autowired(required = false)
	private PasswordEncoder pswdEnc;
	
	/** 회사의 모든 계정 조회 */ 
	public DreamPair<List<AccountVO>, PagingDTO> listAllAccount(String ownerId, int page) {
		PagingDTO paging = new PagingDTO(page);
		List<AccountVO> listResult = partyMapper.listAllAccount(ownerId, paging);
		long dataCount = partyMapper.getFoundRows();
		paging.buildPagination(dataCount);

		return new DreamPair<List<AccountVO>, PagingDTO>(listResult, paging);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return partyMapper.findByNick(username);
	}

	public int createOrganization(OrganizationVO organization) {
		return partyMapper.createOrganization(organization);
	}

	public int createManager(List<AccountVO> accountList) {
		int res = 1;
		for (AccountVO account : accountList) {
			res = partyMapper.createManager(account);
			if (res == 0) {
				return 0;
			}
		}
		return res;
	}
	
	public boolean checkLoginId(String loginId) {
		return partyMapper.isValidLoginId(loginId);
	}
	public boolean checkNick(String nick) {
		return partyMapper.isValidNick(nick);
	}

	/** 회원 가입 */
	public int createMember(PersonVO person, AccountVO account) {
		// 동일인 검증 어케 하나요 ㅠㅠ
		int cnt = partyMapper.createPerson(person);
		account.encodePswd(pswdEnc);

		partyMapper.createAccount(account);
		return cnt;
	}
}
